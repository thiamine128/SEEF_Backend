package com.software.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CourseUpdateDto {
    private Long id;
    private String name;
    private String introduction;
    private BigDecimal credit;
    private String syllabus;
    private String evaluation;
}
