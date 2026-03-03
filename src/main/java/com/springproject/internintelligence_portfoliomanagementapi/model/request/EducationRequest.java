package com.springproject.internintelligence_portfoliomanagementapi.model.request;

import com.springproject.internintelligence_portfoliomanagementapi.model.enums.EducationDegree;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
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
public class EducationRequest {

    @NotBlank(message = "Institution is required")
    String institution;
    EducationDegree degree;
    @NotBlank(message = "Specialty ir required")
    String specialty;
    @PastOrPresent
    LocalDate startTime;
    @FutureOrPresent
    LocalDate endTime;
}
