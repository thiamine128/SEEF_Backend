package com.software.service;

import com.software.dto.Category;

/**
 * @author
 * @Description：
 * @date
 */
public interface SpaceService {
    void createCategory(String category);

    void deleteCategory(String category);


    void updateCategory(String category, Long categoryId);

    Category getCategoryById(Long categoryId);
}
