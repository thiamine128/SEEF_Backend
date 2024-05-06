package com.software.dto;

import lombok.Data;

@Data
public class CommentPageQueryDto {
    private int page;
    private int pageSize;
    private Long blogId;
}
