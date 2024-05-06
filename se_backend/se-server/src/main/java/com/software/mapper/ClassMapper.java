package com.software.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ClassMapper {
    @Insert("insert into classes (course_id, name) values (#{course}, #{name})")
    void addClass(Long course, String name);

    @Insert("insert into teacher_class (class_id, teacher_id) values (#{classId}, #{teacher})")
    void addTeacher(Long classId, Long teacher);
}
