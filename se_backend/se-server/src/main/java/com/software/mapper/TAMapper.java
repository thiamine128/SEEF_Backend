package com.software.mapper;

import com.software.entity.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author
 * @Description：
 * @date
 */
@Mapper
public interface TAMapper {
    @Insert("insert into student_class_management (student_id, class_id, course_id) values (#{studentId}, #{classId}, #{courseId})")
    void addTA(Long studentId, Long classId, Long courseId);
    @Delete("delete from student_class_management where student_id = #{studentId} and class_id = #{classId} and course_id = #{courseId}")
    void removeTA(Long studentId, Long classId, Long courseId);
    @Select("SELECT *  FROM student_class_management scm , users u WHERE scm.student_id = u.id and scm.course_id =#{courseId}")
    List<User> getCourseTA(Long courseId);
}
