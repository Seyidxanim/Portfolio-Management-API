package com.springproject.internintelligence_portfoliomanagementapi.dao.entity;

import com.springproject.internintelligence_portfoliomanagementapi.model.enums.EducationDegree;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Builder
@EqualsAndHashCode(of = "id")
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "education")
public class Education {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String institution;

    @Enumerated(EnumType.STRING)
    private EducationDegree degree;

    private String specialty;

    private LocalDate startTime;

    private LocalDate endTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "portfolio_id",nullable = false)
    private Portfolio portfolio;
}
