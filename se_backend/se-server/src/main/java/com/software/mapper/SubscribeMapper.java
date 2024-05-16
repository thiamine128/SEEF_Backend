package com.software.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SubscribeMapper {
    @Insert("insert into subscribe (user, subscriber) values (#{user}, #{subscriber})")
    void subscribe(Long user, Long subscriber);

    @Select("select subscriber from subscribe where user=#{id}")
    List<Long> getSubscribers(Long id);

    @Select("select user from subscribe where subscriber=#{id}")
    List<Long> getSubscribed(Long id);

    @Delete("delete from subscribe where subscriber=#{subscriber} and user=#{user}")
    int unsubscribe(Long user, Long subscriber);
}
