package com.springproject.internintelligence_portfoliomanagementapi.service.abstraction;

import com.springproject.internintelligence_portfoliomanagementapi.model.request.user_request.LoginRequest;
import com.springproject.internintelligence_portfoliomanagementapi.model.request.user_request.RegisterRequest;
import com.springproject.internintelligence_portfoliomanagementapi.model.request.user_request.UpdateUserRequest;
import com.springproject.internintelligence_portfoliomanagementapi.model.response.UserResponse;

import java.util.List;

public interface UserService {

    UserResponse getById(Long id);

    UserResponse update(Long id, UpdateUserRequest request);

    void delete(Long id);

    List<UserResponse> getUsers();
}
