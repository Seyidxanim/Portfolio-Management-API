package com.springproject.internintelligence_portfoliomanagementapi.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PortfolioRequest {
    @NotBlank(message = "About me is required")
    @Size(max = 3000,message = "AboutMe must not exceed 3000 characters")
    String aboutMe;

    @NotBlank(message = "Title is required")
    @Size(max = 500, message = "Title must not exceed 500 character")
    String title;
}
