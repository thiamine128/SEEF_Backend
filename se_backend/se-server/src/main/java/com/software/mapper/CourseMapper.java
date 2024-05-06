package com.software.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CourseMapper {
    @Insert("insert into courses (name) values (#{name})")
    void create(String name);

    @Insert("insert into teacher_course (teacher_id, course_id) values (#{teacher}, #{course})")
    void addTeacher(Long course, Long teacher);
}
