package com.software.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class BlogSummaryPageQueryDto implements Serializable {
    private int page;

    private int pageSize;

    private Long topicId;
    private int summaryLength;
}
