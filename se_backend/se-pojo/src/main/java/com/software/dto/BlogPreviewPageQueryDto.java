package com.software.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class BlogPreviewPageQueryDto implements Serializable {
    private int page;

    private int pageSize;

    private Long topicId;
    private int previewLength;
}
