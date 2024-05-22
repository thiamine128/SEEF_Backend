package com.software.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommentPageQueryDto {
    private int page;
    private int pageSize;
    private Long blogId;
}
