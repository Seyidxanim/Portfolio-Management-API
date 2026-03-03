package com.springproject.internintelligence_portfoliomanagementapi.model.response;

import com.springproject.internintelligence_portfoliomanagementapi.model.enums.SkillsLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SkillResponse {
    Long id;
    String name;
    SkillsLevel level;
}
