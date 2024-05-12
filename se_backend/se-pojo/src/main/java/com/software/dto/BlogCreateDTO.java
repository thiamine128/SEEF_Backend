package com.software.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
public class BlogCreateDTO {
    private String title;
    private String context;
    private Long topicId;
    private List<String> tags;
}
