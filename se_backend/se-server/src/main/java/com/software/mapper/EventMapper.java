package com.software.mapper;

import com.software.entity.Event;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface EventMapper {
    @Insert("insert into events (subscriber, id, type, subject, object) values (#{subscriber}, #{id}, #{type}, #{subject}, #{object})")
    void newEvent(Event event);

    @Select("select * from events where subscriber=#{id}")
    List<Event> getEvents(Long id);

    @Delete("delete from events where subscriber=#{id}")
    void removeEvents(Long id);

    @Select("select cnt from event_cnt where subscriber=#{subscriber}")
    Long getCount(Long to);
}
