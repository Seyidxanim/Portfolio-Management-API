package com.springproject.internintelligence_portfoliomanagementapi.mapper;

import com.springproject.internintelligence_portfoliomanagementapi.dao.entity.Education;
import com.springproject.internintelligence_portfoliomanagementapi.model.request.EducationRequest;
import com.springproject.internintelligence_portfoliomanagementapi.model.response.EducationResponse;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface EducationMapper {

    Education toEntity(EducationRequest request);

    EducationResponse toResponse(Education education);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public void updateEducation(@MappingTarget Education education,EducationRequest request);
}
