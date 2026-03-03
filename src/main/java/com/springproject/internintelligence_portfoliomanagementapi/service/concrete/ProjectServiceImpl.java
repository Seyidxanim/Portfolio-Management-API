package com.springproject.internintelligence_portfoliomanagementapi.service.concrete;

import com.springproject.internintelligence_portfoliomanagementapi.dao.entity.Portfolio;
import com.springproject.internintelligence_portfoliomanagementapi.dao.entity.Project;
import com.springproject.internintelligence_portfoliomanagementapi.dao.repository.PortfolioRepository;
import com.springproject.internintelligence_portfoliomanagementapi.dao.repository.ProjectRepository;
import com.springproject.internintelligence_portfoliomanagementapi.exception.NotFoundException;
import com.springproject.internintelligence_portfoliomanagementapi.mapper.ProjectMapper;
import com.springproject.internintelligence_portfoliomanagementapi.model.request.ProjectRequest;
import com.springproject.internintelligence_portfoliomanagementapi.model.response.ProjectResponse;
import com.springproject.internintelligence_portfoliomanagementapi.service.abstraction.ProjectService;
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
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;
    private final PortfolioRepository portfolioRepository;

    @Override
    public ProjectResponse createProject(Long portfolioId, ProjectRequest request) {
        var portfolio = portfolioRepository.findById(portfolioId)
                .orElseThrow(() -> new NotFoundException(
                        PORTFOLIO_NOT_FOUND.getCode(),
                        PORTFOLIO_NOT_FOUND.getMessage().formatted(portfolioId)));

        var project = projectMapper.toEntity(request);
        project.setPortfolio(portfolio);
        var save = projectRepository.save(project);
        return projectMapper.toResponse(save);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ProjectResponse> getAllByPortfolio(Long portfolioId) {
        return projectRepository.findAllByPortfolioId(portfolioId)
                .stream()
                .map(projectMapper::toResponse)
                .toList();
    }

    @Override
    public ProjectResponse updateProject(Long id, ProjectRequest request) {
        var project = fetchProjectIfExist(id);

        projectMapper.updateProject(project, request);
        var update = projectRepository.save(project);
        return projectMapper.toResponse(update);
    }

    @Override
    public void deleteProject(Long id) {
        var project = fetchProjectIfExist(id);
        projectRepository.delete(project);
    }

    private Project fetchProjectIfExist(Long id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        PROJECT_NOT_FOUND.getCode(),
                        PORTFOLIO_NOT_FOUND.getMessage().formatted(id)));
    }
}
