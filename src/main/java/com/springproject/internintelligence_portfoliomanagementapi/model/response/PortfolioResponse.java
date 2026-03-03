package com.springproject.internintelligence_portfoliomanagementapi.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PortfolioResponse {
    Long id;
    String aboutMe;
    String title;
}
