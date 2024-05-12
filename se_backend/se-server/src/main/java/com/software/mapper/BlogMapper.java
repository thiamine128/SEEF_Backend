package com.software.mapper;

import com.software.entity.Blog;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BlogMapper {
    @Insert("insert into blogs (title, content, user_id, topic_id, tags) values (#{title}, #{content}, #{userId}, #{topicId}, #{tags})")
    void createBlog(String title, String content, Long userId, Long topicId, String tags);

    @Select("select * from blogs where topic_id=#{topicId}")
    List<Blog> getBlogsInTopic(Long topicId);

    @Select("select * from blogs where id=#{blogId} ")
    Blog getBlog(Long blogId);
    @Update("update blogs set is_deleted=true where id=#{blogId}")
    void deleteBlog(Long blogId);


}