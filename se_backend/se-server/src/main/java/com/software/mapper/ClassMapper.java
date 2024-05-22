package com.software.mapper;

import com.software.entity.Course;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ClassMapper {
    @Insert("insert into classes (course_id, name, time, location) values (#{course}, #{name}, #{time}, #{location})")
    void addClass(Long course, String name, String time, String location);
    @Delete("delete from teacher_class where teacher_id=#{teacherId} and class_id=#{classId}")
    void deleteTeacher(Long classId, Long teacherId);
    @Insert("insert into teacher_class (class_id, teacher_id) values (#{classId}, #{teacher})")
    void addTeacher(Long classId, Long teacher);

    @Select("select course_id from classes where id=#{classId}")
    Long getCourse(Long classId);

    @Select("select class_id from enrollments where student_id=#{studentId}")
    List<Long> getUserCourses(Long studentId);

    @Select("select teacher_id from teacher_class where class_id=#{classId}")
    List<Long> getTeachers(Long classId);
    @Select("select * from courses where id=#{id}")
    Course getCourseById(Long id);
}
