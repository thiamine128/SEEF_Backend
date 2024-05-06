package com.software.dto;

import lombok.Data;

import java.util.List;

@Data
public class CourseCreateDto {
    private String name;
    private List<Long> teachers;
    private List<ClassCreateDto> classes;
}
