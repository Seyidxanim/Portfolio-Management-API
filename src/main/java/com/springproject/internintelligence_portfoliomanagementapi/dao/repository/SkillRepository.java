package com.springproject.internintelligence_portfoliomanagementapi.dao.repository;

import com.springproject.internintelligence_portfoliomanagementapi.dao.entity.Skill;
import com.springproject.internintelligence_portfoliomanagementapi.model.response.SkillResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SkillRepository extends JpaRepository<Skill, Long> {
    List<Skill> findAllByPortfolioId(Long id);

    boolean existsByPortfolioIdAndName (Long portfolioId,String name);

}
