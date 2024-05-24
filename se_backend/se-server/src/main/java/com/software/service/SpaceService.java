package com.software.service;

/**
 * @author
 * @Description：
 * @date
 */
public interface SpaceService {
    void createCategory(String category);

    void deleteCategory(String category);


    void updateCategory(String category, Long categoryId);
}
