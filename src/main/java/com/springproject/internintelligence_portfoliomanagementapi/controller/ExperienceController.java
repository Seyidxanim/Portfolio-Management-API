package com.springproject.internintelligence_portfoliomanagementapi.controller;

import com.springproject.internintelligence_portfoliomanagementapi.model.request.ExperienceRequest;
import com.springproject.internintelligence_portfoliomanagementapi.model.response.ExperienceResponse;
import com.springproject.internintelligence_portfoliomanagementapi.service.abstraction.ExperienceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("/api/experiences")
@RequiredArgsConstructor
public class ExperienceController {
    private final ExperienceService experienceService;


    @ResponseStatus(CREATED)
    @PostMapping("/portfolio/{portfolioId}")
    public ExperienceResponse createExperience(@PathVariable Long portfolioId,
                                               @Valid @RequestBody ExperienceRequest request) {
        return experienceService.createExperience(portfolioId, request);
    }

    @GetMapping("/portfolio/{portfolioId}")
    public List<ExperienceResponse> getAll(@PathVariable Long portfolioId) {
        return experienceService.getAllByPortfolio(portfolioId);
    }

    @PutMapping("{id}")
    public ExperienceResponse updateExperience(@PathVariable Long id,
                                               @Valid @RequestBody ExperienceRequest request) {
        return experienceService.updateExperience(id, request);
    }

    @ResponseStatus(NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        experienceService.deleteExperience(id);
    }
}
