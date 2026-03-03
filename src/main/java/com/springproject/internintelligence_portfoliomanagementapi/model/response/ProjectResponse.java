package com.springproject.internintelligence_portfoliomanagementapi.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProjectResponse {
    Long id;
    String name;
    String description;
    String url;
}
