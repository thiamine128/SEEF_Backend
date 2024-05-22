package com.software.dto;

import lombok.Data;

import java.util.List;

@Data
public class ClassCreateDto {
    private String name;
    private Long courseId;
    private String time;
    private String location;
}
