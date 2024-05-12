package com.software.mapper;

import com.software.entity.Comment;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CommentMapper {
    @Insert("insert into comments (content, blog_id, user_id) values (#{content}, #{blogId}, #{userId})")
    void makeComment(String content, Long blogId, Long userId);

    @Select("select * from comments where blog_id=#{blogId}")
    List<Comment> getComments(Long blogId);

    @Delete("delete from comments where id=#{id}")
    void deleteComment(Long id);

    @Select("select * from comments where id=#{id}")
    Comment getComment(Long id);
}
