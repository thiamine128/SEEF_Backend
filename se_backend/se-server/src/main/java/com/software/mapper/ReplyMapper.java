package com.software.mapper;

import com.software.entity.Reply;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ReplyMapper {
    @Insert("insert into replies (content, comment_id, user_id, to_id) values (#{content}, #{commentId}, #{userId}, #{to})")
    void makeReply(String content, Long commentId, Long userId, Long to);

    @Select("select * from replies where comment_id=#{commentId}")
    List<Reply> getReplies(Long commentId);
    @Delete("delete from replies where id=#{replyId}")
    void deleteReply(Long replyId);
    @Select("select * from replies where id=#{replyId}")
    Reply getReplieById(Long replyId);
}
