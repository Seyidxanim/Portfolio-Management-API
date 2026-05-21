package com.springproject.internintelligence_portfoliomanagementapi.service.concrete;

import com.springproject.internintelligence_portfoliomanagementapi.dao.entity.Skill;
import com.springproject.internintelligence_portfoliomanagementapi.dao.entity.User;
import com.springproject.internintelligence_portfoliomanagementapi.dao.repository.SkillRepository;
import com.springproject.internintelligence_portfoliomanagementapi.security.service.SecurityService;
import com.springproject.internintelligence_portfoliomanagementapi.exception.AlreadyExistsException;
import com.springproject.internintelligence_portfoliomanagementapi.exception.ForbiddenException;
import com.springproject.internintelligence_portfoliomanagementapi.exception.NotFoundException;
import com.springproject.internintelligence_portfoliomanagementapi.mapper.SkillMapper;
import com.springproject.internintelligence_portfoliomanagementapi.model.request.SkillRequest;
import com.springproject.internintelligence_portfoliomanagementapi.model.response.SkillResponse;
import com.springproject.internintelligence_portfoliomanagementapi.service.abstraction.SkillService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.springproject.internintelligence_portfoliomanagementapi.model.enums.ExceptionConstant.*;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class SkillServiceImpl implements SkillService {
    private final SkillRepository skillRepository;
    private final SkillMapper skillMapper;
    private final SecurityService securityService;

    @CacheEvict(value = "skills", allEntries = true)
    @Override
    public SkillResponse createSkill(Long portfolioId, SkillRequest request) {
        log.info("Creating skill for portfolio id: {}", portfolioId);
        var portfolio = securityService.getPortfolioIfOwner(portfolioId);

        boolean exists = skillRepository.existsByPortfolioIdAndName(portfolioId, request.getName());

        if (exists) {
            log.warn("Skill already exists for portfolio id: {}", portfolioId);

            throw new AlreadyExistsException(
                    SKILL_ALREADY_EXISTS.getCode(),
                    SKILL_ALREADY_EXISTS.getMessage());
        }
        var entity = skillMapper.toEntity(request);
        entity.setPortfolio(portfolio);

        var save = skillRepository.save(entity);
        log.info("Skill created successfully for portfolio id: {}", portfolioId);
        return skillMapper.toResponse(save);
    }

    @Cacheable(value = "skills", key = "#portfolioId")
    @Transactional(readOnly = true)
    @Override
    public List<SkillResponse> getAllByPortfolio(Long portfolioId) {
        log.info("Fetching skills for portfolio id: {}", portfolioId);
        return skillRepository.findAllByPortfolioId(portfolioId)
                .stream()
                .map(skillMapper::toResponse)
                .toList();
    }

    @CacheEvict(value = "skills", allEntries = true)
    @Override
    public SkillResponse updateSkill(Long id, SkillRequest request) {
        log.info("Updating skill with id: {}", id);
        var skill = fetchSkillIfExist(id);
        checkSkillOwnership(skill);

        skillMapper.updateSkill(skill, request);
        var update = skillRepository.save(skill);
        log.info("Skill with id {} updated successfully", id);
        return skillMapper.toResponse(update);
    }

    @CacheEvict(value = "skills", allEntries = true)
    @Override
    public void deleteSkill(Long id) {
        log.info("Deleting skill with id: {}", id);
        var skill = fetchSkillIfExist(id);
        checkSkillOwnership(skill);
        skillRepository.delete(skill);
        log.info("Skill with id {} deleted successfully", id);
    }

    private void checkSkillOwnership(Skill skill) {
        User currentUser = securityService.getCurrentUser();

        if (!skill.getPortfolio().getUser().getId()
                .equals(currentUser.getId())) {
            log.warn("User {} tried to access another user's skill with id: {}",
                    currentUser.getId(),
                    skill.getId());

            throw new ForbiddenException(
                    FORBIDDEN.getMessage(),
                    FORBIDDEN.getCode()
            );
        }
    }

    private Skill fetchSkillIfExist(Long id) {
        return skillRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Skill not found with id: {}", id);

                    return new NotFoundException(
                            SKILL_NOT_FOUND.getCode(),
                            SKILL_NOT_FOUND.getMessage().formatted(id)
                    );
                });
    }
}
