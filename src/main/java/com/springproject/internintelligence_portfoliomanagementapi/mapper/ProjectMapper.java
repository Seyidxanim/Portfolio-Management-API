package com.springproject.internintelligence_portfoliomanagementapi.mapper;

import com.springproject.internintelligence_portfoliomanagementapi.dao.entity.Project;
import com.springproject.internintelligence_portfoliomanagementapi.model.request.ProjectRequest;
import com.springproject.internintelligence_portfoliomanagementapi.model.response.ProjectResponse;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface ProjectMapper {

    Project toEntity(ProjectRequest request);

    ProjectResponse toResponse(Project project);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateProject(@MappingTarget Project project, ProjectRequest request);
}
