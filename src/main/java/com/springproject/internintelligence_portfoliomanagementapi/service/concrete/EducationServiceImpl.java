package com.springproject.internintelligence_portfoliomanagementapi.service.concrete;

import com.springproject.internintelligence_portfoliomanagementapi.dao.entity.Education;
import com.springproject.internintelligence_portfoliomanagementapi.dao.entity.User;
import com.springproject.internintelligence_portfoliomanagementapi.dao.repository.EducationRepository;
import com.springproject.internintelligence_portfoliomanagementapi.exception.AlreadyExistsException;
import com.springproject.internintelligence_portfoliomanagementapi.exception.ForbiddenException;
import com.springproject.internintelligence_portfoliomanagementapi.exception.NotFoundException;
import com.springproject.internintelligence_portfoliomanagementapi.mapper.EducationMapper;
import com.springproject.internintelligence_portfoliomanagementapi.model.request.EducationRequest;
import com.springproject.internintelligence_portfoliomanagementapi.model.response.EducationResponse;
import com.springproject.internintelligence_portfoliomanagementapi.security.service.SecurityService;
import com.springproject.internintelligence_portfoliomanagementapi.service.abstraction.EducationService;
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
public class EducationServiceImpl implements EducationService {
    private final EducationMapper educationMapper;
    private final EducationRepository educationRepository;
    private final SecurityService securityService;

    @CacheEvict(value = "educations", allEntries = true)
    @Override
    public EducationResponse createEducation(Long portfolioId, EducationRequest request) {
        log.info("Education creation started for portfolio id: {}", portfolioId);
        var portfolio = securityService.getPortfolioIfOwner(portfolioId);

        boolean exists = educationRepository
                .existsByPortfolioIdAndInstitutionAndDegree(
                        portfolioId,
                        request.getInstitution(),
                        request.getDegree()
                );
        if (exists) {
            log.warn("Education already exists for portfolio id: {}", portfolioId);
            throw new AlreadyExistsException(
                    EDUCATION_ALREADY_EXISTS.getCode(),
                    EDUCATION_ALREADY_EXISTS.getMessage());
        }

        var education = educationMapper.toEntity(request);
        education.setPortfolio(portfolio);

        var save = educationRepository.save(education);
        log.info("Education created successfully for portfolio id: {}", portfolioId);
        return educationMapper.toResponse(save);
    }

    @Cacheable(value = "educations", key = "#portfolioId")
    @Transactional(readOnly = true)
    @Override
    public List<EducationResponse> getAllByPortfolio(Long portfolioId) {
        log.info("Fetching educations for portfolio id: {}", portfolioId);
        return educationRepository.findAllByPortfolioId(portfolioId)
                .stream()
                .map(educationMapper::toResponse)
                .toList();
    }
    @CacheEvict(value = "educations", allEntries = true)
    @Override
    public EducationResponse updateEducation(Long id, EducationRequest request) {
        log.info("Updating education with id: {}", id);
        var education = fetchEducationIfExist(id);
        checkEducationOwnership(education);
        educationMapper.updateEducation(education, request);
        var update = educationRepository.save(education);
        log.info("Education with id {} updated successfully", id);
        return educationMapper.toResponse(update);
    }
    @CacheEvict(value = "educations", allEntries = true)
    @Override
    public void deleteEducation(Long id) {
        log.info("Deleting education with id: {}", id);
        var education = fetchEducationIfExist(id);
        checkEducationOwnership(education);
        educationRepository.delete(education);
        log.info("Education with id {} deleted successfully", id);
    }

    private void checkEducationOwnership(Education education) {
        User currentUser = securityService.getCurrentUser();
        if (!education.getPortfolio().getUser().getId()
                .equals(currentUser.getId())) {

            log.warn("User {} tried to access another user's education with id: {}",
                    currentUser.getId(),
                    education.getId());

            throw new ForbiddenException(
                    FORBIDDEN.getMessage(),
                    FORBIDDEN.getCode()
            );
        }
    }

    private Education fetchEducationIfExist(Long id) {
        return educationRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Education not found with id: {}", id);

                    return new NotFoundException(
                            EDUCATION_NOT_FOUND.getCode(),
                            EDUCATION_NOT_FOUND.getMessage().formatted(id));
                });
    }

}
