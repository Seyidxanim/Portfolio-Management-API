package com.springproject.internintelligence_portfoliomanagementapi.service.concrete;

import com.springproject.internintelligence_portfoliomanagementapi.dao.entity.Portfolio;
import com.springproject.internintelligence_portfoliomanagementapi.dao.entity.User;
import com.springproject.internintelligence_portfoliomanagementapi.dao.repository.PortfolioRepository;
import com.springproject.internintelligence_portfoliomanagementapi.dao.repository.UserRepository;
import com.springproject.internintelligence_portfoliomanagementapi.exception.ForbiddenException;
import com.springproject.internintelligence_portfoliomanagementapi.exception.NotFoundException;
import com.springproject.internintelligence_portfoliomanagementapi.mapper.PortfolioMapper;
import com.springproject.internintelligence_portfoliomanagementapi.model.request.PortfolioRequest;
import com.springproject.internintelligence_portfoliomanagementapi.model.response.PortfolioResponse;
import com.springproject.internintelligence_portfoliomanagementapi.service.abstraction.PortfolioService;
import com.springproject.internintelligence_portfoliomanagementapi.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.springproject.internintelligence_portfoliomanagementapi.model.enums.ExceptionConstant.*;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class PortfolioServiceImpl implements PortfolioService {
    private final PortfolioRepository portfolioRepository;
    private final PortfolioMapper portfolioMapper;
    private final UserRepository userRepository;

    @CacheEvict(value = "user_portfolios", key = "#userId")
    @Override
    public PortfolioResponse create(Long userId, PortfolioRequest request) {
        log.info("Creating portfolio for user id: {}", userId);
        User currentUser = getCurrentUser();
        if (!currentUser.getId().equals(userId)) {
            log.warn("User {} tried to create portfolio for user {}",
                    currentUser.getId(), userId);

            throw new ForbiddenException(FORBIDDEN.getMessage(),
                    FORBIDDEN.getCode());
        }

        var entity = portfolioMapper.toEntity(request);
        entity.setUser(currentUser);
        portfolioRepository.save(entity);
        log.info("Portfolio created successfully for user id: {}", userId);
        return portfolioMapper.toResponse(entity);
    }
    @Cacheable(value = "user_portfolios", key = "#userId")
    @Transactional(readOnly = true)
    @Override
    public List<PortfolioResponse> getAllByUser(Long userId) {

        log.info("Fetching portfolios for user id: {}", userId);

        return portfolioRepository.findAllByUserId(userId)
                .stream()
                .map(portfolioMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "portfolios", key = "#id")
    @Override
    public PortfolioResponse getById(Long id) {

        log.info("Fetching portfolio with id: {}", id);

        Portfolio portfolio = portfolioRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Portfolio not found with id: {}", id);

                    return new NotFoundException(
                            PORTFOLIO_NOT_FOUND.getCode(),
                            PORTFOLIO_NOT_FOUND.getMessage().formatted(id)
                    );
                });

        return portfolioMapper.toResponse(portfolio);
    }

    @CacheEvict(value = {"portfolios", "user_portfolios"}, allEntries = true)
    @Override
    public PortfolioResponse update(Long id, PortfolioRequest request) {
        log.info("Updating portfolio with id: {}", id);
        var portfolio = checkPortfolioOwnership(id);
        portfolioMapper.updatePortfolio(portfolio, request);
        var updatedPortfolio = portfolioRepository.save(portfolio);
        log.info("Portfolio with id {} updated successfully", id);
        return portfolioMapper.toResponse(updatedPortfolio);
    }

    @CacheEvict(value = "portfolios", key = "#id")
    @Override
    public void delete(Long id) {
        log.info("Deleting portfolio with id: {}", id);
        Portfolio portfolio = checkPortfolioOwnership(id);
        portfolioRepository.delete(portfolio);
        log.info("Portfolio deleted successfully with id: {}", id);
    }


    private User getCurrentUser() {
        String email = SecurityUtils.getCurrentUserEmail();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException(
                        USER_NOT_FOUND.getCode(),
                        USER_NOT_FOUND.getMessage().formatted(email)
                ));
    }

    private Portfolio checkPortfolioOwnership(Long portfolioId) {
        Portfolio portfolio = portfolioRepository.findById(portfolioId)
                .orElseThrow(() -> new NotFoundException(
                        PORTFOLIO_NOT_FOUND.getCode(),
                        PORTFOLIO_NOT_FOUND.getMessage().formatted(portfolioId)
                ));

        User currentUser = getCurrentUser();
        if (!portfolio.getUser().getId().equals(currentUser.getId())) {
            throw new ForbiddenException(FORBIDDEN.getMessage(), FORBIDDEN.getCode());
        }
        return portfolio;
    }
}
