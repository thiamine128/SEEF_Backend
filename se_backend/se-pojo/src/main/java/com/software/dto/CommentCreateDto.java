package com.software.dto;

import lombok.Data;

@Data
public class CommentCreateDto {
    private String content;
    private Long blogId;
}
