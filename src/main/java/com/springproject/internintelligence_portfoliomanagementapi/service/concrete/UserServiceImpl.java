package com.springproject.internintelligence_portfoliomanagementapi.service.concrete;

import com.springproject.internintelligence_portfoliomanagementapi.dao.entity.User;
import com.springproject.internintelligence_portfoliomanagementapi.dao.repository.UserRepository;
import com.springproject.internintelligence_portfoliomanagementapi.exception.AlreadyExistsException;
import com.springproject.internintelligence_portfoliomanagementapi.exception.NotFoundException;
import com.springproject.internintelligence_portfoliomanagementapi.mapper.UserMapper;
import com.springproject.internintelligence_portfoliomanagementapi.model.enums.Role;
import com.springproject.internintelligence_portfoliomanagementapi.model.request.user_request.LoginRequest;
import com.springproject.internintelligence_portfoliomanagementapi.model.request.user_request.RegisterRequest;
import com.springproject.internintelligence_portfoliomanagementapi.model.request.user_request.UpdateUserRequest;
import com.springproject.internintelligence_portfoliomanagementapi.model.response.UserResponse;
import com.springproject.internintelligence_portfoliomanagementapi.service.abstraction.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.springproject.internintelligence_portfoliomanagementapi.model.enums.ExceptionConstant.EMAIL_ALREADY_EXISTS;
import static com.springproject.internintelligence_portfoliomanagementapi.model.enums.ExceptionConstant.USER_NOT_FOUND;


@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {//changepasvord met yaz
    private final UserRepository userRepository;
    private final UserMapper userMapper;


    @Override
    public UserResponse register(RegisterRequest request) {

        if (!userRepository.existsByEmail(request.getEmail())){
            throw  new AlreadyExistsException(
                    EMAIL_ALREADY_EXISTS.getCode(),
                    EMAIL_ALREADY_EXISTS.getMessage());
        }
        var entity = userMapper.toEntity(request);
        entity.setRole(Role.USER);
        userRepository.save(entity);
        return userMapper.toResponse(entity);
    }

    @Transactional(readOnly = true)
    @Override
    public UserResponse getById(Long id) {
        var user = fetchUserIfExist(id);
        return userMapper.toResponse(user);

    }

    @Override
    public List<UserResponse> getUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(userMapper::toResponse)
                .toList();

    }

    @Override
    public UserResponse update(Long id, UpdateUserRequest request) {
        var user = fetchUserIfExist(id);
        userMapper.updateUser(user, request);
        userRepository.save(user);
        return userMapper.toResponse(user);
    }

//    @Override
//    public String login(LoginRequest request) {
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
//        );
//
//        User user = userRepository.findByEmail(request.getEmail())
//                .orElseThrow(() -> new NotFoundException("User not found", "USER_NOT_FOUND"));
//
//        return jwtService.generateToken(user);
//    }

    @Override
    public void delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw new NotFoundException(
                    USER_NOT_FOUND.getMessage().formatted(id),
                    USER_NOT_FOUND.getCode());
        }
        userRepository.deleteById(id);
    }


    private User fetchUserIfExist(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        USER_NOT_FOUND.getCode(),
                        USER_NOT_FOUND.getMessage().formatted(id)));
    }
}
