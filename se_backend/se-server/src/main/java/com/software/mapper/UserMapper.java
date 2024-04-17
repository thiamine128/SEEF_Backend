package com.software.mapper;

import com.software.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
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
    @Select("select * from users where user_account= #{account}")
    User getByAccount(String account);

    @Select("select * from users where email= #{userEmail}")
    User getByEmail(String userEmail);
}
