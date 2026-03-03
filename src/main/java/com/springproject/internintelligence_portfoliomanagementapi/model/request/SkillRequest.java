package com.springproject.internintelligence_portfoliomanagementapi.model.request;

import com.springproject.internintelligence_portfoliomanagementapi.model.enums.SkillsLevel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SkillRequest {

    @NotBlank(message = "Skill name is required")
    @Size(max = 60,message = "Skill name must not exceed 60 characters")
    String name;

    @NotNull(message = "Skill level is required")
    SkillsLevel level;
}
