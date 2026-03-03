package com.springproject.internintelligence_portfoliomanagementapi.model.enums;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.HashMap;
import java.util.Map;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum SkillsLevel {

    BEGINNER,
    INTERMEDIATE,
    ADVANCED,
    EXPERT;


    private static final Map<String, SkillsLevel> MAP = new HashMap<>();

    static {
        for (SkillsLevel level : values()) {
            MAP.put(level.name().toLowerCase(), level);
        }
    }

    @JsonCreator
    public static SkillsLevel fromString(String value) {
        if (value == null) {
            return null;
        }
        SkillsLevel level = MAP.get(value.toLowerCase());

        if (level == null) {
            throw new IllegalArgumentException("Invalid skill level: " + value);
        }
        return level;
    }

}
