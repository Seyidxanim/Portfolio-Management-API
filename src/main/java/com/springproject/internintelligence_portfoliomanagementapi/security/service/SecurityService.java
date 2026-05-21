package com.springproject.internintelligence_portfoliomanagementapi.security.service;

import com.springproject.internintelligence_portfoliomanagementapi.dao.entity.Portfolio;
import com.springproject.internintelligence_portfoliomanagementapi.dao.entity.User;
import com.springproject.internintelligence_portfoliomanagementapi.dao.repository.PortfolioRepository;
import com.springproject.internintelligence_portfoliomanagementapi.dao.repository.UserRepository;
import com.springproject.internintelligence_portfoliomanagementapi.exception.ForbiddenException;
import com.springproject.internintelligence_portfoliomanagementapi.exception.NotFoundException;
import com.springproject.internintelligence_portfoliomanagementapi.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.springproject.internintelligence_portfoliomanagementapi.model.enums.ExceptionConstant.*;
import static com.springproject.internintelligence_portfoliomanagementapi.model.enums.ExceptionConstant.FORBIDDEN;

@Service
@RequiredArgsConstructor
public class SecurityService {

    private final UserRepository userRepository;
    private final PortfolioRepository portfolioRepository;

    public User getCurrentUser() {
        String email = SecurityUtils.getCurrentUserEmail();

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException(
                        USER_NOT_FOUND.getCode(),
                        USER_NOT_FOUND.getMessage().formatted(email)
                ));
    }


    public Portfolio getPortfolioIfOwner(Long portfolioId) {

        User currentUser = getCurrentUser();

        Portfolio portfolio = portfolioRepository.findById(portfolioId)
                .orElseThrow(() -> new NotFoundException(
                        PORTFOLIO_NOT_FOUND.getCode(),
                        PORTFOLIO_NOT_FOUND.getMessage().formatted(portfolioId)
                ));

        if (!portfolio.getUser().getId().equals(currentUser.getId())) {
            throw new ForbiddenException(
                    FORBIDDEN.getMessage(),
                    FORBIDDEN.getCode()
            );
        }

        return portfolio;
    }

}
