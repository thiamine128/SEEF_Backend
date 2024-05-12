package com.software.dto;

import lombok.Data;

@Data
public class SendMessageDTO {
    private Long to;
    private String content;
}
