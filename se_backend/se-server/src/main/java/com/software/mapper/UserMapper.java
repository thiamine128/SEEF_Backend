package com.software.mapper;

import com.software.dto.UserUpdateDTO;
import com.software.entity.Course;
import com.software.entity.TClass;
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

    @Select("select * from users where id= #{id}")
    User getByID(Long id);

    @Select("select * from users where email= #{email}")
    User getByEmail(String email);

    @Insert("insert into users (id, name, password, email, role, nickname) values (#{id}, #{name}, #{password}, #{email}, #{role}, #{nickname})")
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
    @Select("select course_id from student_class_management where student_id =#{studentId} and course_id =#{courseId}")
    Long getTA(Long studentId,Long courseId);
    @Select("select course_id from teacher_course where teacher_id =#{teacherId} and course_id =#{courseId}")
    Long checkTeacher(Long teacherId, Long courseId);
    @Select("select course_id from teacher_course where teacher_id =#{teacherId}")
    List<Long> getTeacherCourseIds(Long teacherId);

    @Select("select course_id from student_class_management where student_id =#{studentId}")
    List<Long> getTACourseIds(Long studentId);

    List<Course> getCourses(List<Long> ids);

    @Select("select  class_id from enrollments where student_id =#{id}  ")
    List<Long> getClassIds(Long id);

    List<TClass> getClasses(List<Long> ids);
    @Select("select id from users where id=#{id}")
    Long exist(Long id);

    @Update("update users set subscribers=subscribers+1 where id=#{id}")
    void addSubscribers(Long id);
    @Update("update users set subscribers=subscribers-1 where id=#{id}")
    void subSubscribers(Long id);
    @Select("select * from users where name like CONCAT('%',#{username},'%')")
    List<User> getUserByName(String userName);
    @Select("select id from users where name = #{studentAccount}")
    Long getIdByName(String studentAccount);
}
