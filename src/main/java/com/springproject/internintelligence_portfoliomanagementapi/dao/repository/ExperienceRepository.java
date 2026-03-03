package com.springproject.internintelligence_portfoliomanagementapi.dao.repository;

import com.springproject.internintelligence_portfoliomanagementapi.dao.entity.Experience;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExperienceRepository extends JpaRepository<Experience,Long> {

    List<Experience> findAllByPortfolioId(Long portfolioId);
}
