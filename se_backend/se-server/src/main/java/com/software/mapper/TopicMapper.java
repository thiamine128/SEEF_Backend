package com.software.mapper;

import com.github.pagehelper.Page;
import com.software.dto.TopicCreateDto;
import com.software.dto.TopicPageQueryDTO;
import com.software.entity.Topic;
import com.software.vo.TopicVO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface TopicMapper {
    @Select("select * from topics where id=#{id}")
    Topic getById(Long id);

    @Insert("insert into topics (name, introduction) values (#{name}, #{introduction})")
    void createTopic(TopicCreateDto topicCreateDto);

    Page<TopicVO> pageQuery(TopicPageQueryDTO topicPageQueryDTO);
}
