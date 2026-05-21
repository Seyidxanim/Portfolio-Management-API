package com.springproject.internintelligence_portfoliomanagementapi.security.service;

import com.springproject.internintelligence_portfoliomanagementapi.dao.entity.User;
import com.springproject.internintelligence_portfoliomanagementapi.dao.repository.UserRepository;
import com.springproject.internintelligence_portfoliomanagementapi.exception.AlreadyExistsException;
import com.springproject.internintelligence_portfoliomanagementapi.exception.NotFoundException;
import com.springproject.internintelligence_portfoliomanagementapi.model.enums.Role;
import com.springproject.internintelligence_portfoliomanagementapi.model.request.user_request.LoginRequest;
import com.springproject.internintelligence_portfoliomanagementapi.model.request.user_request.RegisterRequest;
import com.springproject.internintelligence_portfoliomanagementapi.security.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public String register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new AlreadyExistsException(
                    "EMAIL_ALREADY_EXISTS",
                    "Email already exists"
            );
        }

        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .role(Role.USER)
                .build();

        userRepository.save(user);

        return jwtService.generateToken(user);
    }

    public String login(LoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->new NotFoundException(
                        "User not found",
                        "USER_NOT_FOUND"));

        return jwtService.generateToken(user);
    }
}