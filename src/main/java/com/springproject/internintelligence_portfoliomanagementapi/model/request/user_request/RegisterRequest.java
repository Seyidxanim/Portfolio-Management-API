package com.springproject.internintelligence_portfoliomanagementapi.model.request.user_request;

import jakarta.validation.constraints.Email;
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
public class RegisterRequest {

    @NotBlank(message = "First name is required")
    @Pattern(regexp = "^[a-zA-Z]+$",
            message = "First name can contain only letters")
    @Size(max = 30, message = "First name must not exceed 30 characters")
    String firstName;

    @NotBlank(message = "Last name is required")
    @Pattern(regexp = "^[a-zA-Z]+$",
            message = "Last name can contain only letters")
    @Size(max = 30, message = "Last name must not exceed max 30 characters")
    String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Size(max = 50, message = "Email must not exceed max 50 character")
    String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 12, message = "Password must be between 8 and 12 characters")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*]).{8,12}$",
            message = "Password must contain upper, lower, digit and special character")
    String password;

    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Invalid phone number")
    String phone;

    @NotBlank(message = "About is required")
    @Size(max = 500, message = "About must not exceed 500 characters")
    String about;
}
