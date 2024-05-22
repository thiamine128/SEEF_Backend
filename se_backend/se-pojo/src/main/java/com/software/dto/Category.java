package com.software.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author
 * @Description：
 * @date
 */
@Data
@AllArgsConstructor
public class Category {
    private Long id;
    private String categoryName;
    private Long userId;
}
