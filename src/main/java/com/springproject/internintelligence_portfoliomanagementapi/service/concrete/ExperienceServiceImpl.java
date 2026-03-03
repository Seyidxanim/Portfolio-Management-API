package com.springproject.internintelligence_portfoliomanagementapi.service.concrete;

import com.springproject.internintelligence_portfoliomanagementapi.dao.entity.Experience;
import com.springproject.internintelligence_portfoliomanagementapi.dao.entity.Portfolio;
import com.springproject.internintelligence_portfoliomanagementapi.dao.repository.ExperienceRepository;
import com.springproject.internintelligence_portfoliomanagementapi.dao.repository.PortfolioRepository;
import com.springproject.internintelligence_portfoliomanagementapi.exception.NotFoundException;
import com.springproject.internintelligence_portfoliomanagementapi.mapper.ExperienceMapper;
import com.springproject.internintelligence_portfoliomanagementapi.model.request.ExperienceRequest;
import com.springproject.internintelligence_portfoliomanagementapi.model.response.ExperienceResponse;
import com.springproject.internintelligence_portfoliomanagementapi.service.abstraction.ExperienceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.springproject.internintelligence_portfoliomanagementapi.model.enums.ExceptionConstant.EXPERIENCE_NOT_FOUND;
import static com.springproject.internintelligence_portfoliomanagementapi.model.enums.ExceptionConstant.PORTFOLIO_NOT_FOUND;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class ExperienceServiceImpl implements ExperienceService {
    private final ExperienceRepository experienceRepository;
    private final ExperienceMapper experienceMapper;
    private final PortfolioRepository portfolioRepository;

    @Override
    public ExperienceResponse createExperience(Long portfolioId, ExperienceRequest request) {
        var portfolio = portfolioRepository.findById(portfolioId)
                .orElseThrow(() -> new NotFoundException(
                        PORTFOLIO_NOT_FOUND.getCode(),
                        PORTFOLIO_NOT_FOUND.getMessage().formatted(portfolioId)));

        var experience = experienceMapper.toEntity(request);
        experience.setPortfolio(portfolio);//bax bura bu setir ne is gorur
        var save = experienceRepository.save(experience);
        return experienceMapper.toResponse(save);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ExperienceResponse> getAllByPortfolio(Long portfolioId) {
        return experienceRepository.findAllByPortfolioId(portfolioId)
                .stream()
                .map(experienceMapper::toResponse)
                .toList();
    }

    @Override
    public ExperienceResponse updateExperience(Long id, ExperienceRequest request) {
        var experience = fetchExperienceIfExist(id);
        experienceMapper.updateExperience(experience, request);//bura bax
        var update = experienceRepository.save(experience);
        return experienceMapper.toResponse(update);
    }

    @Override
    public void deleteExperience(Long id) {
        var experience = fetchExperienceIfExist(id);
        experienceRepository.delete(experience);
    }

    private Experience fetchExperienceIfExist(Long id) {
        return experienceRepository.findById(id)
                .orElseThrow(() ->
                        new NotFoundException(
                                EXPERIENCE_NOT_FOUND.getCode(),
                                EXPERIENCE_NOT_FOUND.getMessage().formatted(id)));
    }
}
