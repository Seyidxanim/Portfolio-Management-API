package com.springproject.internintelligence_portfoliomanagementapi.model.response;

import com.springproject.internintelligence_portfoliomanagementapi.model.enums.EducationDegree;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EducationResponse {
    Long id;
    String institution;
    EducationDegree degree;
    String specialty;
    LocalDate startTime;
    LocalDate endTime;
}
