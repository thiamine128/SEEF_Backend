package com.software.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * @author
 * @Description：
 * @date
 */
@Data
@AllArgsConstructor
public class BlogUpdateDTO {
    private Long blogId;
    private String title;
    private String context;
    private Long topicId;
    private Long category_id;
    private List<String> tags;
}
