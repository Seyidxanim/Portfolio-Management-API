package com.springproject.internintelligence_portfoliomanagementapi.service.concrete;

import com.springproject.internintelligence_portfoliomanagementapi.dao.entity.Education;
import com.springproject.internintelligence_portfoliomanagementapi.dao.entity.Portfolio;
import com.springproject.internintelligence_portfoliomanagementapi.dao.repository.EducationRepository;
import com.springproject.internintelligence_portfoliomanagementapi.dao.repository.PortfolioRepository;
import com.springproject.internintelligence_portfoliomanagementapi.exception.AlreadyExistsException;
import com.springproject.internintelligence_portfoliomanagementapi.exception.NotFoundException;
import com.springproject.internintelligence_portfoliomanagementapi.mapper.EducationMapper;
import com.springproject.internintelligence_portfoliomanagementapi.model.request.EducationRequest;
import com.springproject.internintelligence_portfoliomanagementapi.model.response.EducationResponse;
import com.springproject.internintelligence_portfoliomanagementapi.service.abstraction.EducationService;
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
public class EducationServiceImpl implements EducationService {
    private final EducationMapper educationMapper;
    private final EducationRepository educationRepository;
    private final PortfolioRepository portfolioRepository;


    @Override
    public EducationResponse createEducation(Long portfolioId, EducationRequest request) {
        var portfolio = portfolioRepository.findById(portfolioId).
                orElseThrow(() ->
                        new NotFoundException(
                                PORTFOLIO_NOT_FOUND.getCode(),
                                PORTFOLIO_NOT_FOUND.getMessage().formatted(portfolioId)));

        boolean exists = educationRepository
                .existsByPortfolioIdAndInstitutionAndDegree(
                        portfolioId,
                        request.getInstitution(),
                        request.getDegree()
                );
        if (!exists){
            throw new AlreadyExistsException(
                    EDUCATION_ALREADY_EXISTS.getCode(),
                    EDUCATION_ALREADY_EXISTS.getMessage());
        }

        var education = educationMapper.toEntity(request);
        education.setPortfolio(portfolio);

        var save = educationRepository.save(education);
        return educationMapper.toResponse(save);
    }


    @Transactional(readOnly = true)
    @Override
    public List<EducationResponse> getAllByPortfolio(Long portfolioId) {
        return educationRepository.findAllByPortfolioId(portfolioId)
                .stream()
                .map(educationMapper::toResponse)
                .toList();
    }

    @Override
    public EducationResponse updateEducation(Long id, EducationRequest request) {
        var education = fetchEducationIfExist(id);

        educationMapper.updateEducation(education, request);
        var update = educationRepository.save(education);
        return educationMapper.toResponse(update);
    }

    @Override
    public void deleteEducation(Long id) {
        var education = fetchEducationIfExist(id);
        educationRepository.delete(education);
    }

    private Education fetchEducationIfExist(Long id) {
        return educationRepository.findById(id)
                .orElseThrow(() ->
                        new NotFoundException(
                                EDUCATION_NOT_FOUND.getCode(),
                                EDUCATION_NOT_FOUND.getMessage().formatted(id)));
    }

}
