package com.springproject.internintelligence_portfoliomanagementapi.controller;

import com.springproject.internintelligence_portfoliomanagementapi.dao.repository.PortfolioRepository;
import com.springproject.internintelligence_portfoliomanagementapi.dao.repository.SkillRepository;
import com.springproject.internintelligence_portfoliomanagementapi.mapper.SkillMapper;
import com.springproject.internintelligence_portfoliomanagementapi.model.request.SkillRequest;
import com.springproject.internintelligence_portfoliomanagementapi.model.response.SkillResponse;
import com.springproject.internintelligence_portfoliomanagementapi.service.abstraction.SkillService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("/api/skills")
@RequiredArgsConstructor
public class SkillController {
    private final SkillService skillService;

    @GetMapping("/portfolio/{portfolioId}")
    public List<SkillResponse> getAll(@PathVariable Long portfolioId) {
        return skillService.getAllByPortfolio(portfolioId);
    }

    @ResponseStatus(CREATED)
    @PostMapping("/portfolio/{portfolioId}")
    public SkillResponse createSkill(@PathVariable Long portfolioId,
                                     @Valid @RequestBody SkillRequest request) {
        return skillService.createSkill(portfolioId, request);
    }

    @PutMapping("/{id}")
    public SkillResponse updateSkill(@PathVariable Long id, @Valid @RequestBody SkillRequest request) {
        return skillService.updateSkill(id, request);
    }

    @ResponseStatus(NO_CONTENT)
    @DeleteMapping("{id}")
    public void delete (@PathVariable Long id){
        skillService.deleteSkill(id);
    }
}
