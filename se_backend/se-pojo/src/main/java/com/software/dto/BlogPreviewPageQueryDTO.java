package com.software.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
public class BlogPreviewPageQueryDTO implements Serializable {
    private int page;
    private int pageSize;
    private String keyword;
    private Long userId;
    private List<Long> topicIds;
    private List<String> tags;
    private int previewLength;
    private String orderBy;
    private String sort;
}
