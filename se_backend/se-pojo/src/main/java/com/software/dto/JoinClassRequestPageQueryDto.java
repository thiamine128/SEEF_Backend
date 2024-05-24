package com.software.dto;

import lombok.Data;

@Data
public class JoinClassRequestPageQueryDto {
    private int page;
    private int pageSize;
    private Long courseId;
}
