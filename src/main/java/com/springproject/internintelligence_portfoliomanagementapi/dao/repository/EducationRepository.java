package com.springproject.internintelligence_portfoliomanagementapi.dao.repository;

import com.springproject.internintelligence_portfoliomanagementapi.dao.entity.Education;
import com.springproject.internintelligence_portfoliomanagementapi.model.enums.EducationDegree;
import jdk.dynalink.linker.LinkerServices;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EducationRepository extends JpaRepository<Education,Long> {
    List<Education> findAllByPortfolioId(Long id);

    boolean existsByPortfolioIdAndInstitutionAndDegree(Long portfolioId,
                                                  String school, EducationDegree degree);
}
