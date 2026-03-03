package com.springproject.internintelligence_portfoliomanagementapi.service.abstraction;

import com.springproject.internintelligence_portfoliomanagementapi.model.request.PortfolioRequest;
import com.springproject.internintelligence_portfoliomanagementapi.model.response.PortfolioResponse;

import java.util.List;

public interface PortfolioService {

    PortfolioResponse create(Long userId, PortfolioRequest request);

    List<PortfolioResponse> getAllByUser(Long userId);
    PortfolioResponse getById(Long id);
    void delete(Long id);
}
