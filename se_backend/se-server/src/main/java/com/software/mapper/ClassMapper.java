package com.software.mapper;

import com.software.dto.ClassUpdateDto;
import com.software.entity.Course;
import com.software.entity.CourseClass;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ClassMapper {
    void addClass(CourseClass courseClass);
    @Delete("delete from teacher_class where teacher_id=#{teacherId} and class_id=#{classId}")
    void deleteTeacher(Long classId, Long teacherId);
    @Insert("insert into teacher_class (class_id, teacher_id) values (#{classId}, #{teacher})")
    void addTeacher(Long classId, Long teacher);

    @Select("select course_id from classes where id=#{classId}")
    Long getCourse(Long classId);

    @Select("select * from classes where id=#{classId}")
    CourseClass getCourseClass(Long classId);

    @Select("select class_id from enrollments where student_id=#{studentId}")
    List<Long> getUserClasses(Long studentId);

    @Select("select teacher_id from teacher_class where class_id=#{classId}")
    List<Long> getTeachers(Long classId);
    @Select("select * from courses where id=#{id}")
    Course getCourseById(Long id);
    void updateClass(ClassUpdateDto classUpdateDto);

    @Insert("insert into enrollments (student_id, class_id, course_id) values (#{studentId}, #{classId}, #{courseId})")
    void addStudent(Long studentId, Long classId, Long courseId);

    @Insert("select teacher_id from teacher_class where teacher_id=#{id} and class_id=#{classId}")
    Long checkTeacher(Long id, Long classId);
}
