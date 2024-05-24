package com.software.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CourseCreateDto {
    private String name;
    private String introduction;
    private BigDecimal credit;
    private String summary;
    private String evaluation;
}
