package com.springproject.internintelligence_portfoliomanagementapi.model.request;

import com.springproject.internintelligence_portfoliomanagementapi.model.enums.JobType;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExperienceRequest {

    @NotBlank(message = "Company name is required")
    String companyName;

    @NotBlank(message = "Position is required")
    String position;
    @PastOrPresent
    LocalDate startTime;
    @FutureOrPresent
    LocalDate endTime;
    JobType type;
}
