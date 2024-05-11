package com.software.mapper;

import com.software.dto.UserUpdateDTO;
import com.software.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import java.util.List;

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

    @Insert("insert into users (name, password, email, role) values (#{name}, #{password}, #{email}, #{role})")
    void insert(User user);

    @Update("update users set avatar=#{avatarUrl},update_time=now() where id=#{id}")
    void updateAvatar(String avatarUrl, Long id);
    @Select("select avatar from users where id=#{id}")
    String getAvatar(Long id);
    @Select("select name from users where id=#{id}")
    String getName(Long id);

    @Update("update users set password=#{password}, update_time=now() where email=#{email}")
    void updatePassword(String email, String password);
    void update(UserUpdateDTO userUpdateDTO);

    void addButchUser(List<User> users);
    @Select("select * from student_class_management where student_id =#{studentId} and course_id =#{courseId}")
    User getTA(Long studentId,Long courseId);
    @Select("select * from student_class_management where teacher_id =#{teacherId} and course_id =#{courseId}")
    User checkTeacher(Long teacherId, Long courseId);
}
