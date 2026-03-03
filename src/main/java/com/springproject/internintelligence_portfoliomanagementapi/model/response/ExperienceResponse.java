package com.springproject.internintelligence_portfoliomanagementapi.model.response;

import com.springproject.internintelligence_portfoliomanagementapi.model.enums.JobType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExperienceResponse {
    Long id;
    String companyName;
    String position;
    LocalDate startTime;
    LocalDate endTime;
    JobType type;
}
