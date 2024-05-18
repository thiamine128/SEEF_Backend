package com.software.mapper;

import com.github.pagehelper.Page;
import com.software.dto.BlogPreviewPageQueryDTO;
import com.software.entity.Blog;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BlogMapper {
    @Insert("insert into blogs (title, content, user_id, topic_id, tags) values (#{title}, #{content}, #{userId}, #{topicId}, #{tags})")
    void createBlog(String title, String content, Long userId, Long topicId, String tags);

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
}