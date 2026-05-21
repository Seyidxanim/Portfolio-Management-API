package com.springproject.internintelligence_portfoliomanagementapi.service.concrete;

import com.springproject.internintelligence_portfoliomanagementapi.dao.entity.Portfolio;
import com.springproject.internintelligence_portfoliomanagementapi.dao.entity.Project;
import com.springproject.internintelligence_portfoliomanagementapi.dao.entity.User;
import com.springproject.internintelligence_portfoliomanagementapi.dao.repository.PortfolioRepository;
import com.springproject.internintelligence_portfoliomanagementapi.dao.repository.ProjectRepository;
import com.springproject.internintelligence_portfoliomanagementapi.exception.ForbiddenException;
import com.springproject.internintelligence_portfoliomanagementapi.exception.NotFoundException;
import com.springproject.internintelligence_portfoliomanagementapi.mapper.ProjectMapper;
import com.springproject.internintelligence_portfoliomanagementapi.model.request.ProjectRequest;
import com.springproject.internintelligence_portfoliomanagementapi.model.response.ProjectResponse;
import com.springproject.internintelligence_portfoliomanagementapi.security.service.SecurityService;
import com.springproject.internintelligence_portfoliomanagementapi.service.abstraction.ProjectService;
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
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;
    private final PortfolioRepository portfolioRepository;
    private final SecurityService securityService;

    @CacheEvict(value = "projects", allEntries = true)
    @Override
    public ProjectResponse createProject(Long portfolioId, ProjectRequest request) {
        log.info("Creating project for portfolio id: {}", portfolioId);
        var portfolio = securityService.getPortfolioIfOwner(portfolioId);

        var project = projectMapper.toEntity(request);
        project.setPortfolio(portfolio);
        var save = projectRepository.save(project);
        log.info("Project created successfully for portfolio id: {}", portfolioId);
        return projectMapper.toResponse(save);
    }

    @Cacheable(value = "projects", key = "#portfolioId")
    @Transactional(readOnly = true)
    @Override
    public List<ProjectResponse> getAllByPortfolio(Long portfolioId) {
        log.info("Fetching projects for portfolio id: {}", portfolioId);
        return projectRepository.findAllByPortfolioId(portfolioId)
                .stream()
                .map(projectMapper::toResponse)
                .toList();
    }

    @CacheEvict(value = "projects", allEntries = true)
    @Override
    public ProjectResponse updateProject(Long id, ProjectRequest request) {
        log.info("Updating project with id: {}", id);
        var project = fetchProjectIfExist(id);

        checkProjectOwnership(project);

        projectMapper.updateProject(project, request);
        var update = projectRepository.save(project);
        log.info("Project with id {} updated successfully", id);
        return projectMapper.toResponse(update);
    }

    @CacheEvict(value = "projects", allEntries = true)
    @Override
    public void deleteProject(Long id) {
        log.info("Deleting project with id: {}", id);
        var project = fetchProjectIfExist(id);
        checkProjectOwnership(project);
        projectRepository.delete(project);
        log.info("Project with id {} deleted successfully", id);
    }

    private void checkProjectOwnership(Project project) {
        User currentUser =securityService.getCurrentUser();
        if (!project.getPortfolio().getUser().getId().equals(currentUser.getId())) {
            log.warn("User {} tried to access another user's project with id: {}",
                    currentUser.getId(),
                    project.getId());

            throw new ForbiddenException(
                    FORBIDDEN.getMessage(),
                    FORBIDDEN.getCode()
            );
        }
    }

    private Project fetchProjectIfExist(Long id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> {

                    log.error("Project not found with id: {}", id);

                    return new NotFoundException(
                            PROJECT_NOT_FOUND.getCode(),
                            PROJECT_NOT_FOUND.getMessage().formatted(id)
                    );
                });
    }
}

