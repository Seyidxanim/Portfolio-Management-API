package com.springproject.internintelligence_portfoliomanagementapi.service.concrete;

import com.springproject.internintelligence_portfoliomanagementapi.dao.entity.User;
import com.springproject.internintelligence_portfoliomanagementapi.dao.repository.UserRepository;
import com.springproject.internintelligence_portfoliomanagementapi.exception.ForbiddenException;
import com.springproject.internintelligence_portfoliomanagementapi.exception.NotFoundException;
import com.springproject.internintelligence_portfoliomanagementapi.mapper.UserMapper;
import com.springproject.internintelligence_portfoliomanagementapi.model.request.user_request.UpdateUserRequest;
import com.springproject.internintelligence_portfoliomanagementapi.model.response.UserResponse;
import com.springproject.internintelligence_portfoliomanagementapi.service.abstraction.UserService;
import com.springproject.internintelligence_portfoliomanagementapi.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.springproject.internintelligence_portfoliomanagementapi.model.enums.ExceptionConstant.FORBIDDEN;
import static com.springproject.internintelligence_portfoliomanagementapi.model.enums.ExceptionConstant.USER_NOT_FOUND;


@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {//changepasvord met yaz
    private final UserRepository userRepository;
    private final UserMapper userMapper;


    @Cacheable(value = "users", key = "#id")
    @Override
    public UserResponse getById(Long id) {
        log.info("Fetching user with id: {}", id);

        var user = userRepository.findById(id).orElseThrow(() -> {
            log.error("User not found with id: {}", id);
            return new NotFoundException(USER_NOT_FOUND.getCode(), USER_NOT_FOUND.getMessage().formatted(id));
        });

        return userMapper.toResponse(user);

    }

    @Cacheable(value = "users_list")
    @Override
    public List<UserResponse> getUsers() {
        log.info("Fetching all users");
        List<User> users = userRepository.findAll();
        return users.stream().map(userMapper::toResponse).toList();
    }

    @CacheEvict(value = {"users", "users_list"}, allEntries = true)
    @Override
    public UserResponse update(Long id, UpdateUserRequest request) {
        log.info("Updating user with id: {}", id);
        checkUserOwnership(id);
        User currentUser = getCurrentUser();
        userMapper.updateUser(currentUser, request);
        userRepository.save(currentUser);
        log.info("User with id {} updated successfully", id);
        return userMapper.toResponse(currentUser);
    }

    @CacheEvict(value = {"users", "users_list"}, allEntries = true)
    @Override
    public void delete(Long id) {
        log.info("Deleting user with id: {}", id);
        checkUserOwnership(id);
        User user = getCurrentUser();
        userRepository.delete(user);
        log.info("User with id {} deleted successfully", id);
    }

    private User getCurrentUser() {

        String currentEmail = SecurityUtils.getCurrentUserEmail();

        return userRepository.findByEmail(currentEmail).orElseThrow(() -> {

            log.error("Current user not found with email: {}", currentEmail);

            return new NotFoundException(USER_NOT_FOUND.getCode(), "User with email %s not found".formatted(currentEmail));
        });
    }

    private void checkUserOwnership(Long id) {

        User currentUser = getCurrentUser();

        if (!currentUser.getId().equals(id)) {

            log.warn("User {} tried to access another user's account with id: {}", currentUser.getId(), id);

            throw new ForbiddenException(FORBIDDEN.getMessage(), FORBIDDEN.getCode());
        }
    }


}
