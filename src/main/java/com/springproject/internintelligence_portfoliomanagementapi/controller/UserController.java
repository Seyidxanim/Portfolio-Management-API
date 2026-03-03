package com.springproject.internintelligence_portfoliomanagementapi.controller;

import com.springproject.internintelligence_portfoliomanagementapi.model.request.user_request.RegisterRequest;
import com.springproject.internintelligence_portfoliomanagementapi.model.request.user_request.UpdateUserRequest;
import com.springproject.internintelligence_portfoliomanagementapi.model.response.UserResponse;
import com.springproject.internintelligence_portfoliomanagementapi.service.abstraction.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @ResponseStatus(CREATED)
    @PostMapping
    public UserResponse register(@Valid @RequestBody RegisterRequest request){
       return userService.register(request);
    }
    @GetMapping("/{id}")
    public UserResponse getById(@PathVariable Long id){
        return userService.getById(id);
    }

    @PutMapping("/{id}")
    public UserResponse update(@PathVariable Long id,
                              @Valid @RequestBody UpdateUserRequest request){
        return userService.update(id,request);
    }

    @ResponseStatus(NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        userService.delete(id);
    }
}
