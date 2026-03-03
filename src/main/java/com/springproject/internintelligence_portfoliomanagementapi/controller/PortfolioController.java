package com.springproject.internintelligence_portfoliomanagementapi.controller;

import com.springproject.internintelligence_portfoliomanagementapi.model.request.PortfolioRequest;
import com.springproject.internintelligence_portfoliomanagementapi.model.response.PortfolioResponse;
import com.springproject.internintelligence_portfoliomanagementapi.service.abstraction.PortfolioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("/api/portfolios")
@RequiredArgsConstructor
public class PortfolioController {//update yoxdur

    public final PortfolioService portfolioService;

    @ResponseStatus(CREATED)
    @PostMapping("/{userId}")
    public PortfolioResponse createPortfolio(@PathVariable Long userId,
                                    @Valid @RequestBody PortfolioRequest request) {
        return portfolioService.create(userId, request);
    }

    @GetMapping("/user/{userId}")
    public List<PortfolioResponse> getAllByUser(@PathVariable Long userId) {
        return portfolioService.getAllByUser(userId);
    }

    @GetMapping("/{id}")
    public PortfolioResponse getById(@PathVariable Long id) {
        return portfolioService.getById(id);
    }

    @ResponseStatus(NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        portfolioService.delete(id);
    }
}
