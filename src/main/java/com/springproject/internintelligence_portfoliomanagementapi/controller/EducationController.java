package com.springproject.internintelligence_portfoliomanagementapi.controller;


import com.springproject.internintelligence_portfoliomanagementapi.model.request.EducationRequest;
import com.springproject.internintelligence_portfoliomanagementapi.model.response.EducationResponse;
import com.springproject.internintelligence_portfoliomanagementapi.service.abstraction.EducationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("/api/educations")
@RequiredArgsConstructor
public class EducationController {
    private final EducationService educationService;


    @ResponseStatus(CREATED)
    @PostMapping("/portfolio/{portfolioId}")
    public EducationResponse createEducation(@PathVariable Long portfolioId,
                                                             @Valid @RequestBody EducationRequest request){
        return educationService.createEducation(portfolioId,request);
    }

    @GetMapping("/portfolio/{portfolioId}")
    public List<EducationResponse> getAll(@PathVariable Long portfolioId){
        return educationService.getAllByPortfolio(portfolioId);
    }

    @PutMapping("/{id}")
    public EducationResponse updateEducation(@PathVariable Long id,
                                    @Valid @RequestBody EducationRequest request){
        return educationService.updateEducation(id, request);
    }

    @ResponseStatus(NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        educationService.deleteEducation(id);
    }

}
