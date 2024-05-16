package com.software.mapper;

import com.software.entity.Reply;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ReplyMapper {
    void makeReply(Reply reply);

    @Select("select * from replies where comment_id=#{commentId}")
    List<Reply> getReplies(Long commentId);
    @Delete("delete from replies where id=#{replyId}")
    void deleteReply(Long replyId);
    @Select("select * from replies where id=#{replyId}")
    Reply getReply(Long replyId);
}
