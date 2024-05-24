package com.software.mapper;

import com.github.pagehelper.Page;
import com.software.dto.CoursePageQueryDto;
import com.software.dto.CourseUpdateDto;
import com.software.entity.Course;
import com.software.entity.CourseClass;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface CourseMapper {
    void create(Course course);

    @Insert("insert into teacher_course (teacher_id, course_id) values (#{teacher}, #{course})")
    void addTeacher(Long course, Long teacher);
    Page<Course> pageQuery(CoursePageQueryDto coursePageQueryDto);

    @Select("select * from classes where course_id = #{courseId}")
    List<CourseClass> getClasses(Long courseId);

    @Select("select teacher_id from teacher_course where teacher_id=#{teacherId} and course_id=#{courseId}")
    Long checkTeacher(Long teacherId, Long courseId);

    @Select("select teacher_id from teacher_course where course_id=#{courseId}")
    List<Long> getTeachers(Long courseId);

    @Update("update courses set name=#{name}, introduction=#{introduction}, credit=#{credit}, summary=#{summary}, evaluation=#{evaluation} where id=#{id}")
    void updateCourse(CourseUpdateDto courseUpdateDto);
    @Update("update courses set cover=#{s} where id=#{courseId}")
    void updateCover(String s, Long courseId);
}
