package com.software.mapper;

import com.github.pagehelper.Page;
import com.software.dto.BlogPreviewPageQueryDTO;
import com.software.dto.Category;
import com.software.entity.Blog;
import com.software.entity.UserBlogOperation;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BlogMapper {
    @Insert("insert into blogs (title, content, user_id, topic_id, tags, category_id) values (#{title}, #{content}, #{userId}, #{topicId}, #{tags}, #{categoryId})")
    void createBlog(String title, String content, Long userId, Long topicId, String tags, Long categoryId);

    List<Blog> pageQuery(BlogPreviewPageQueryDTO blogPreviewPageQueryDTO);

    @Select("select * from blogs where id=#{blogId} ")
    Blog getBlog(Long blogId);
    @Update("update blogs set is_deleted=true where id=#{blogId}")
    void deleteBlog(Long blogId);

    @Insert("insert into blog_thumb (blog_id, user_id) values (#{blogId}, #{id})")
    void like(Long blogId, Long id);
    @Delete("delete from blog_thumb where blog_id=#{blogId} and user_id=#{id}")
    void cancelLike(Long blogId, Long id);
    @Select("select blog_id from blog_thumb where blog_id=#{blogId} and user_id=#{id}")
    Long isLiked(Long blogId, Long id);
    @Update("update blogs set thumb_num = thumb_num + 1 where id = #{blogId}")
    void increaseLikes(Long blogId);
    @Update("update blogs set thumb_num = thumb_num - 1 where id = #{blogId}")
    void decreaseLikes(Long blogId);
    @Insert("insert into blog_favour (blog_id, user_id) values (#{blogId}, #{id})")
    void favor(Long blogId, Long id);
    @Delete("delete from blog_favour where blog_id=#{blogId} and user_id=#{id}")
    void cancelFavor(Long blogId, Long id);
    @Update("update blogs set favour_num = favour_num + 1 where id = #{blogId}")
    void increaseFavors(Long blogId);
    @Update("update blogs set favour_num = favour_num - 1 where id = #{blogId}")
    void decreaseFavors(Long blogId);
    @Select("select blog_id from blog_favour where blog_id=#{blogId} and user_id=#{id}")
    Long isFavor(Long blogId, Long id);
    @Select("select blog_id from blog_favour where user_id=#{id} ")
    List<Long> getFavorBlogIds(Long id);

    Page<Blog> favorPageQuery(List<Long> ids, int pages, int pageSize);
    @Update("update blogs set read_cnt = read_cnt + 1 where id = #{blogId}")
    void increaseReadCnt(Long blogId);

    List<UserBlogOperation> getAllUserPreference();

    List<Blog> recommend(List<Long> ids);
    @Update("update blogs set title =#{title}, content= #{context}, tags=#{tags}, category_id=#{categoryId}, topic_id=#{topicId} where id = #{blogId} and user_id=#{id}")
    void updateBlog(String title, String context, Long id, Long topicId, String tags, Long categoryId, Long blogId);
    @Insert("insert into user_favour_category (category_name, user_id) values (#{category}, #{id})")
    void createFavourCategory(String category, Long id);
    @Delete("delete from user_favour_category where category_name = #{category} and user_id = #{id}")
    void deleteFavourCategory(String category, Long id);
    @Update("update user_favour_category set category_name = #{category} where user_id = #{id} and id = #{categoryId}")
    void updateFavourCategory(String category, Long id, Long categoryId);
    @Select("select * from user_favour_category where user_id = #{userId}")
    List<Category> getFavourCategoryList(Long userId);

    @Update("update blogs set popularity = popularity + #{delta} where id = #{blogId}")
    void updatePopularity(Long blogId, Long delta);

    @Update("update blogs set read_users = read_users + 1 where id = #{blogId}")
    void increaseReadUsers(Long blogId);
}