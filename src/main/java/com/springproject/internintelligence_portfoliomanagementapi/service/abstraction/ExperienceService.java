package com.springproject.internintelligence_portfoliomanagementapi.service.abstraction;

import com.springproject.internintelligence_portfoliomanagementapi.dao.entity.Experience;
import com.springproject.internintelligence_portfoliomanagementapi.model.request.ExperienceRequest;
import com.springproject.internintelligence_portfoliomanagementapi.model.response.ExperienceResponse;

import java.util.List;

public interface ExperienceService {

    ExperienceResponse createExperience(Long portfolioId, ExperienceRequest request);

    List<ExperienceResponse> getAllByPortfolio(Long portfolioId);

    ExperienceResponse updateExperience(Long id, ExperienceRequest request);

    void deleteExperience(Long id);
}
