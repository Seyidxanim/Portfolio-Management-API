package com.springproject.internintelligence_portfoliomanagementapi.service.abstraction;

import com.springproject.internintelligence_portfoliomanagementapi.model.request.ProjectRequest;
import com.springproject.internintelligence_portfoliomanagementapi.model.response.ExperienceResponse;
import com.springproject.internintelligence_portfoliomanagementapi.model.response.ProjectResponse;
import jdk.dynalink.linker.LinkerServices;

import java.util.List;

public interface ProjectService {

    ProjectResponse createProject(Long portfolioId, ProjectRequest request);

    List<ProjectResponse> getAllByPortfolio(Long portfolioId);

    ProjectResponse updateProject(Long id, ProjectRequest request);

    void deleteProject(Long id);
}
