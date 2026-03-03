package com.springproject.internintelligence_portfoliomanagementapi.service.concrete;

import com.springproject.internintelligence_portfoliomanagementapi.dao.entity.Portfolio;
import com.springproject.internintelligence_portfoliomanagementapi.dao.entity.Skill;
import com.springproject.internintelligence_portfoliomanagementapi.dao.repository.PortfolioRepository;
import com.springproject.internintelligence_portfoliomanagementapi.dao.repository.SkillRepository;
import com.springproject.internintelligence_portfoliomanagementapi.dao.repository.UserRepository;
import com.springproject.internintelligence_portfoliomanagementapi.exception.AlreadyExistsException;
import com.springproject.internintelligence_portfoliomanagementapi.exception.NotFoundException;
import com.springproject.internintelligence_portfoliomanagementapi.mapper.SkillMapper;
import com.springproject.internintelligence_portfoliomanagementapi.model.request.SkillRequest;
import com.springproject.internintelligence_portfoliomanagementapi.model.response.SkillResponse;
import com.springproject.internintelligence_portfoliomanagementapi.service.abstraction.SkillService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    private final PortfolioRepository portfolioRepository;
    private final UserRepository userRepository;

    @Override
    public SkillResponse createSkill(Long portfolioId, SkillRequest request) {
        var portfolio = portfolioRepository.findById(portfolioId)
                .orElseThrow(() -> new NotFoundException(
                        PORTFOLIO_NOT_FOUND.getCode(),
                        PORTFOLIO_NOT_FOUND.getMessage().formatted(portfolioId)));


        if (!skillRepository.existsByPortfolioIdAndName(portfolioId,request.getName())){
            throw new AlreadyExistsException(
                    SKILL_ALREADY_EXISTS.getCode(),
                    SKILL_ALREADY_EXISTS.getMessage());
        }
        var entity = skillMapper.toEntity(request);
        entity.setPortfolio(portfolio);

        Skill save = skillRepository.save(entity);
        return skillMapper.toResponse(save);
    }

    @Transactional(readOnly = true)
    @Override
    public List<SkillResponse> getAllByPortfolio(Long portfolioId) {
        return skillRepository.findAllByPortfolioId(portfolioId)
                .stream()
                .map(skillMapper::toResponse)
                .toList();
    }

    @Override
    public SkillResponse updateSkill(Long id, SkillRequest request) {
        var skill = fetchSkillIfExist(id);

        skillMapper.updateSkill(skill, request);
        var update = skillRepository.save(skill);
        return skillMapper.toResponse(update);
    }

    @Override
    public void deleteSkill(Long id) {
        var skill = fetchSkillIfExist(id);
        skillRepository.delete(skill);
    }

    private Skill fetchSkillIfExist(Long id) {
        return skillRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        SKILL_NOT_FOUND.getCode(),
                        SKILL_NOT_FOUND.getMessage().formatted(id)));
    }
}
