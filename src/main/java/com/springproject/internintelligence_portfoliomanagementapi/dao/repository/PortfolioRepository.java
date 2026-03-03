package com.springproject.internintelligence_portfoliomanagementapi.dao.repository;

import com.springproject.internintelligence_portfoliomanagementapi.dao.entity.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PortfolioRepository extends JpaRepository<Portfolio,Long> {

    List<Portfolio> findAllByUserId(Long userId);
}
