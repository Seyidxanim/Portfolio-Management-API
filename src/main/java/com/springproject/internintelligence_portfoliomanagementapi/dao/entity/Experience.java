package com.springproject.internintelligence_portfoliomanagementapi.dao.entity;


import com.springproject.internintelligence_portfoliomanagementapi.model.enums.JobType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "experience")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Experience {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String companyName;

    private String position;

    private LocalDate startTime;
    private LocalDate endTime;

    @Enumerated(EnumType.STRING)
    private JobType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "portfolio_id", nullable = false)
    private Portfolio portfolio;
}


