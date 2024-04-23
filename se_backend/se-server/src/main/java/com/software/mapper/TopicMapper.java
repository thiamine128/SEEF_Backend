package com.software.mapper;

import com.software.entity.Topic;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface TopicMapper {
    @Select("select * from topics where id=#{id}")
    Topic getById(Long id);

    @Insert("insert into topics (name) values (#{name})")
    void createTopic(String name);
}
