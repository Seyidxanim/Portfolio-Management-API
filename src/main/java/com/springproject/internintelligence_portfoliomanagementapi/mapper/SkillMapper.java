package com.springproject.internintelligence_portfoliomanagementapi.mapper;

import com.springproject.internintelligence_portfoliomanagementapi.dao.entity.Skill;
import com.springproject.internintelligence_portfoliomanagementapi.model.request.SkillRequest;
import com.springproject.internintelligence_portfoliomanagementapi.model.response.SkillResponse;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface SkillMapper {

    Skill toEntity(SkillRequest request);

    SkillResponse toResponse(Skill skill);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateSkill(@MappingTarget Skill skill, SkillRequest request);
}
