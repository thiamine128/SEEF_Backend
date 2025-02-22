package com.software.mapper;

import com.software.entity.Comment;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CommentMapper {
    void makeComment(Comment comment);

    @Select("select * from comments where blog_id=#{blogId}")
    List<Comment> getComments(Long blogId);

    @Delete("delete from comments where id=#{id}")
    void deleteComment(Long id);

    @Select("select * from comments where id=#{id}")
    Comment getComment(Long id);
}
