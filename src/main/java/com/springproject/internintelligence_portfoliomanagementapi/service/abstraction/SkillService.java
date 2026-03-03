package com.springproject.internintelligence_portfoliomanagementapi.service.abstraction;

import com.springproject.internintelligence_portfoliomanagementapi.model.request.EducationRequest;
import com.springproject.internintelligence_portfoliomanagementapi.model.request.SkillRequest;
import com.springproject.internintelligence_portfoliomanagementapi.model.response.SkillResponse;

import java.util.List;

public interface SkillService {
    SkillResponse createSkill(Long portfolioId, SkillRequest request);
    List<SkillResponse> getAllByPortfolio(Long portfolioId);
    SkillResponse updateSkill(Long id, SkillRequest request);
    void deleteSkill(Long id);
}
