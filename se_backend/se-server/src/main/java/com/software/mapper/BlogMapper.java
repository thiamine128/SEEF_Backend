package com.software.mapper;

import org.apache.ibatis.annotations.Insert;

public interface BlogMapper {
    @Insert("insert into blogs (title, content, user_id) values (#{title}, #{content}, #{user_id})")
    void createBlog(String title, String content, Long userId);
}
