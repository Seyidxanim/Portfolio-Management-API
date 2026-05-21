package com.springproject.internintelligence_portfoliomanagementapi.dao.repository;

import com.springproject.internintelligence_portfoliomanagementapi.dao.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);
}
