package com.springproject.internintelligence_portfoliomanagementapi.model.request.user_request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateUserRequest {
    @NotBlank(message = "First name is required")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "First name can contain only letters")
    @Size(max = 30, message = "First name must not exceed 30 characters")
    String firstName;

    @NotBlank(message = "Last name is required")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Last name can contain only letters")
    @Size(max = 30, message = "Last name must not exceed 30 characters")
    String lastName;

    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Invalid phone number")
    String phone;

    @NotBlank(message = "About is required")
    @Size(max = 500,message = "About must not exceed 500 characters")
    String about;
}
