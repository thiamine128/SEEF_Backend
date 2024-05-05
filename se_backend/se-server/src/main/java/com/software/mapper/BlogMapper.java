package com.software.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BlogMapper {
    @Insert("insert into blogs (title, content, user_id, topic_id) values (#{title}, #{content}, #{user_id}, #{topic_id})")
    void createBlog(String title, String content, Long userId, Long topicId);
}
