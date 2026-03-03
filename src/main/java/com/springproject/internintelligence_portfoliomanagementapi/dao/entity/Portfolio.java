package com.springproject.internintelligence_portfoliomanagementapi.dao.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@Table(name = "portfolio")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Portfolio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String aboutMe;

    @Column(nullable = false)
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "portfolio",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Skill> skills;

    @OneToMany(mappedBy = "portfolio",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Project> projects;

    @OneToMany(mappedBy = "portfolio",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Education> educations;

    @OneToMany(mappedBy = "portfolio",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Experience> experiences;
}
