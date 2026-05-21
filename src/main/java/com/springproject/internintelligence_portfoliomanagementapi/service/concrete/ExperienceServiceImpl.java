package com.springproject.internintelligence_portfoliomanagementapi.service.concrete;

import com.springproject.internintelligence_portfoliomanagementapi.dao.entity.Experience;
import com.springproject.internintelligence_portfoliomanagementapi.dao.entity.Portfolio;
import com.springproject.internintelligence_portfoliomanagementapi.dao.entity.User;
import com.springproject.internintelligence_portfoliomanagementapi.dao.repository.ExperienceRepository;
import com.springproject.internintelligence_portfoliomanagementapi.dao.repository.PortfolioRepository;
import com.springproject.internintelligence_portfoliomanagementapi.dao.repository.UserRepository;
import com.springproject.internintelligence_portfoliomanagementapi.exception.ForbiddenException;
import com.springproject.internintelligence_portfoliomanagementapi.exception.NotFoundException;
import com.springproject.internintelligence_portfoliomanagementapi.mapper.ExperienceMapper;
import com.springproject.internintelligence_portfoliomanagementapi.model.request.ExperienceRequest;
import com.springproject.internintelligence_portfoliomanagementapi.model.response.ExperienceResponse;
import com.springproject.internintelligence_portfoliomanagementapi.security.service.SecurityService;
import com.springproject.internintelligence_portfoliomanagementapi.service.abstraction.ExperienceService;
import com.springproject.internintelligence_portfoliomanagementapi.util.SecurityUtils;
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
public class ExperienceServiceImpl implements ExperienceService {
    private final ExperienceRepository experienceRepository;
    private final ExperienceMapper experienceMapper;
    private final SecurityService securityService;

    @CacheEvict(value = "experiences", allEntries = true)
    @Override
    public ExperienceResponse createExperience(Long portfolioId, ExperienceRequest request) {
        log.info("Creating experience for portfolio id: {}", portfolioId);
        var portfolio = securityService.getPortfolioIfOwner(portfolioId);
        var experience = experienceMapper.toEntity(request);
        experience.setPortfolio(portfolio);
        var save = experienceRepository.save(experience);
        log.info("Experience created successfully for portfolio id {}", portfolioId);
        return experienceMapper.toResponse(save);
    }

    @Cacheable(value = "experiences", key = "#portfolioId")
    @Transactional(readOnly = true)
    @Override
    public List<ExperienceResponse> getAllByPortfolio(Long portfolioId) {
        log.info("Fetching experiences for portfolio id: {}", portfolioId);
        return experienceRepository.findAllByPortfolioId(portfolioId)
                .stream()
                .map(experienceMapper::toResponse)
                .toList();
    }

    @CacheEvict(value = "experiences", allEntries = true)
    @Override
    public ExperienceResponse updateExperience(Long id, ExperienceRequest request) {
        log.info("Updating experience with id: {}", id);
        var experience = fetchExperienceIfExist(id);
        checkExperienceOwnership(experience);
        experienceMapper.updateExperience(experience, request);
        var update = experienceRepository.save(experience);
        log.info("Experience with id {} updated successfully", id);
        return experienceMapper.toResponse(update);
    }
    @CacheEvict(value = "experiences", allEntries = true)
    @Override
    public void deleteExperience(Long id) {
        log.info("Deleting experience with id {}", id);
        var experience = fetchExperienceIfExist(id);
        checkExperienceOwnership(experience);
        experienceRepository.delete(experience);
        log.info("Experience with id {} deleted successfully", id);
    }

    private void checkExperienceOwnership(Experience experience) {
        User currentUser = securityService.getCurrentUser();
        if (!experience.getPortfolio().getUser().getId().equals(currentUser.getId())) {

            log.warn("User {} tried to access another user's experience with id: {}",
                    currentUser.getId(),
                    experience.getId());

            throw new ForbiddenException(
                    FORBIDDEN.getMessage(),
                    FORBIDDEN.getCode()
            );
        }
    }

    private Experience fetchExperienceIfExist(Long id) {
        return experienceRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Experience not found with id: {}", id);

                    return new NotFoundException(
                            EXPERIENCE_NOT_FOUND.getCode(),
                            EXPERIENCE_NOT_FOUND.getMessage().formatted(id));
                });
    }
}
