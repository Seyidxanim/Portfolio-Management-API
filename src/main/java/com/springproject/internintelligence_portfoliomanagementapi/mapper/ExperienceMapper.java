package com.springproject.internintelligence_portfoliomanagementapi.mapper;

import com.springproject.internintelligence_portfoliomanagementapi.dao.entity.Experience;
import com.springproject.internintelligence_portfoliomanagementapi.model.request.ExperienceRequest;
import com.springproject.internintelligence_portfoliomanagementapi.model.response.ExperienceResponse;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface ExperienceMapper {

    Experience toEntity(ExperienceRequest request);

    ExperienceResponse toResponse(Experience experience);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateExperience(@MappingTarget Experience experience, ExperienceRequest request);
}
