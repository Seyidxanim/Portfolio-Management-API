package com.springproject.internintelligence_portfoliomanagementapi.dao.entity;

import jakarta.persistence.*;
import lombok.*;

import javax.sound.sampled.Port;
import java.time.LocalDate;

@Entity
@Builder
@Getter
@Setter
@Table(name = "project")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    private String description;
    @Column(nullable = false)
    private String url;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "portfolio_id",nullable = false)
    private Portfolio portfolio;
}
