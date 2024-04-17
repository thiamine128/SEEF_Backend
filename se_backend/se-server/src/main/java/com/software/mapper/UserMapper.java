package com.software.mapper;

import com.software.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

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
