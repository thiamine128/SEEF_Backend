package com.software.dto;

import lombok.Data;

@Data
public class ReplyCreateDto {
    private String content;
    private Long commentId;
    private Long to;
}
