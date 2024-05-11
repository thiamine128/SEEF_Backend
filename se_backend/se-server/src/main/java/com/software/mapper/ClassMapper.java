package com.software.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ClassMapper {
    @Insert("insert into classes (course_id, name) values (#{course}, #{name})")
    void addClass(Long course, String name);

    @Insert("insert into teacher_class (class_id, teacher_id) values (#{classId}, #{teacher})")
    void addTeacher(Long classId, Long teacher);

    @Select("select course_id from classes where id=#{classId}")
    Long getCourse(Long classId);

    @Select("select class_id from enrollments where student_id=#{studentId}")
    List<Long> getUserCourses(Long studentId);
}
