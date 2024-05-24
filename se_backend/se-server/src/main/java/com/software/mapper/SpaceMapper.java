package com.software.mapper;

import com.software.dto.Category;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author
 * @Description：
 * @date
 */
@Mapper
public interface SpaceMapper {
    @Insert("insert into user_blog_category (category_name, user_id) values (#{category}, #{id})")
    void createCategory(String category, Long id);
    @Delete("delete from user_blog_category where category_name = #{category} and user_id = #{id}")
    void deleteCategory(String category, Long id);
    @Select("select * from user_blog_category where user_id = #{userId}")
    List<Category> getCategoryList(Long userId);
    @Update("update user_blog_category set category_name = #{category} where user_id = #{id} and id = #{categoryId}")
    void updateCategory(String category, Long id, Long categoryId);
}
