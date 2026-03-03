package com.springproject.internintelligence_portfoliomanagementapi.service.concrete;

import com.springproject.internintelligence_portfoliomanagementapi.dao.entity.Portfolio;
import com.springproject.internintelligence_portfoliomanagementapi.dao.entity.User;
import com.springproject.internintelligence_portfoliomanagementapi.dao.repository.PortfolioRepository;
import com.springproject.internintelligence_portfoliomanagementapi.dao.repository.UserRepository;
import com.springproject.internintelligence_portfoliomanagementapi.exception.NotFoundException;
import com.springproject.internintelligence_portfoliomanagementapi.mapper.PortfolioMapper;
import com.springproject.internintelligence_portfoliomanagementapi.model.request.PortfolioRequest;
import com.springproject.internintelligence_portfoliomanagementapi.model.response.PortfolioResponse;
import com.springproject.internintelligence_portfoliomanagementapi.service.abstraction.PortfolioService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.springproject.internintelligence_portfoliomanagementapi.model.enums.ExceptionConstant.PORTFOLIO_NOT_FOUND;
import static com.springproject.internintelligence_portfoliomanagementapi.model.enums.ExceptionConstant.USER_NOT_FOUND;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class PortfolioServiceImpl implements PortfolioService {
    private final PortfolioRepository portfolioRepository;
    private final PortfolioMapper portfolioMapper;
    private final UserRepository userRepository;

    @Override
    public PortfolioResponse create(Long userId, PortfolioRequest request) {

        var user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(
                        USER_NOT_FOUND.getCode(),
                        USER_NOT_FOUND.getMessage().formatted(userId)));
        var entity = portfolioMapper.toEntity(request);
        entity.setUser(user);
        portfolioRepository.save(entity);
        return portfolioMapper.toResponse(entity);
    }

    @Transactional(readOnly = true)
    @Override
    public List<PortfolioResponse> getAllByUser(Long userId) {
        return portfolioRepository
                .findAllByUserId(userId)
                .stream()
                .map(portfolioMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    @Override
    public PortfolioResponse getById(Long id) {
        var portfolio = portfolioRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        PORTFOLIO_NOT_FOUND.getCode(),
                        PORTFOLIO_NOT_FOUND.getMessage().formatted(id)));
        return portfolioMapper.toResponse(portfolio);
    }

    @Override
    public void delete(Long id) {
        portfolioRepository.deleteById(id);
    }
}
