package com.springproject.internintelligence_portfoliomanagementapi.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {
    Long id;
    String firstName;
    String lastName;
    String email;
    String about;
    String phone;
}
