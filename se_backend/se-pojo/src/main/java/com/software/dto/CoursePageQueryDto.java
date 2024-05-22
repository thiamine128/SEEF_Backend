package com.software.dto;

import lombok.Data;

@Data
public class CoursePageQueryDto {
    private int page;

    private int pageSize;

    private String name;
}
