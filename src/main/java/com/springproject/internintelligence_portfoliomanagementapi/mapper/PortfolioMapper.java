package com.springproject.internintelligence_portfoliomanagementapi.mapper;

import com.springproject.internintelligence_portfoliomanagementapi.dao.entity.Portfolio;
import com.springproject.internintelligence_portfoliomanagementapi.model.request.PortfolioRequest;
import com.springproject.internintelligence_portfoliomanagementapi.model.response.PortfolioResponse;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface PortfolioMapper {

    Portfolio toEntity(PortfolioRequest request);

    PortfolioResponse toResponse(Portfolio portfolio);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updatePortfolio(@MappingTarget Portfolio portfolio, PortfolioRequest request);
}
