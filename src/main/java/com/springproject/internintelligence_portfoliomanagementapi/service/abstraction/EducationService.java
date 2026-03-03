package com.springproject.internintelligence_portfoliomanagementapi.service.abstraction;

import com.springproject.internintelligence_portfoliomanagementapi.model.request.EducationRequest;
import com.springproject.internintelligence_portfoliomanagementapi.model.response.EducationResponse;

import java.util.List;

public interface EducationService {


    EducationResponse createEducation(Long portfolioId, EducationRequest request);

    List<EducationResponse> getAllByPortfolio(Long portfolioId);

    EducationResponse updateEducation(Long id, EducationRequest request);

    void deleteEducation(Long id);
}
