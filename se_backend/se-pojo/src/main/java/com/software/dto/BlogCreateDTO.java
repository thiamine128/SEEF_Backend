package com.software.dto;

import lombok.Data;

import java.util.Date;

@Data
public class BlogCreateDTO {
    private String title;
    private String context;
    private String tags;
    private Long topicId;
}
