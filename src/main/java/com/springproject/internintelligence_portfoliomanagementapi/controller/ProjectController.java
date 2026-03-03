package com.springproject.internintelligence_portfoliomanagementapi.controller;

import com.springproject.internintelligence_portfoliomanagementapi.model.request.ProjectRequest;
import com.springproject.internintelligence_portfoliomanagementapi.model.response.ProjectResponse;
import com.springproject.internintelligence_portfoliomanagementapi.service.abstraction.ProjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;

    @ResponseStatus(CREATED)
    @PostMapping("/portfolio/{portfolioId}")
    public ProjectResponse createProject(@PathVariable Long portfolioId,
                                         @Valid @RequestBody ProjectRequest request) {
        return projectService.createProject(portfolioId, request);
    }

    @GetMapping("/portfolio/{portfolioId}")
    public List<ProjectResponse> getAll(@PathVariable Long portfolioId) {
        return projectService.getAllByPortfolio(portfolioId);
    }

    @PutMapping("/{id}")
    public ProjectResponse updateProject(@PathVariable Long id,
                                         @Valid @RequestBody ProjectRequest request) {
        return projectService.updateProject(id, request);
    }

    @ResponseStatus(NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        projectService.deleteProject(id);
    }
}
