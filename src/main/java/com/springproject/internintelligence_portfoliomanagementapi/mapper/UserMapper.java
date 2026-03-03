package com.springproject.internintelligence_portfoliomanagementapi.mapper;

import com.springproject.internintelligence_portfoliomanagementapi.dao.entity.User;
import com.springproject.internintelligence_portfoliomanagementapi.model.request.user_request.RegisterRequest;
import com.springproject.internintelligence_portfoliomanagementapi.model.request.user_request.UpdateUserRequest;
import com.springproject.internintelligence_portfoliomanagementapi.model.response.UserResponse;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(RegisterRequest request);

    UserResponse toResponse(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUser(@MappingTarget User user, UpdateUserRequest request);
}
