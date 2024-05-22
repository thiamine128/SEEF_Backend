package com.software.mapper;

import com.software.entity.Message;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MessageMapper {
    @Insert("insert into message (id, content, send, `to`, `read`, date) values (#{id}, #{content}, #{send}, #{to}, #{read}, #{date})")
    void createMessage(Message message);

    @Select("select * from message where `to`=#{to}")
    List<Message> getMessages(Long to);

    @Delete("delete from message where send=#{send} and `to`=#{to}")
    void markRead(Long send, Long to);

    @Delete("delete from message where `to`=#{to}")
    void delete(Long to);

    @Select("select cnt from message_cnt where `to`=#{to}")
    Long getCount(Long to);
}
