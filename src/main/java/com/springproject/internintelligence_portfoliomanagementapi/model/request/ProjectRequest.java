package com.springproject.internintelligence_portfoliomanagementapi.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProjectRequest {
    @NotBlank(message = "Project name is required")
    String name;
    @Size(max = 3000, message = "Description must not exceed 3000 characters")
    String description;
    @NotBlank(message = "URL is required")
    @URL(message = "Invalid url format")
    String url;
}
