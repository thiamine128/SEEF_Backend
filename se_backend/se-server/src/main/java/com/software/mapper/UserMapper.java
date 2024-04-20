package com.software.mapper;

import com.software.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

/**
 * @author
 * @Description：
 * @date
 */
@Mapper
public interface UserMapper {
    @Select("select * from users where name= #{username}")
    User getByAccount(String username);

    @Select("select * from users where email= #{email}")
    User getByEmail(String email);

    @Insert("insert into users (name, password, email) values (#{name}, #{password}, #{email})")
    void insert(User user);
}
