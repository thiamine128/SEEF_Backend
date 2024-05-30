package com.software.mapper;

import com.github.pagehelper.Page;
import com.software.dto.CoursePageQueryDto;
import com.software.dto.CourseUpdateDto;
import com.software.dto.DeleteStudentReqDTO;
import com.software.entity.Course;
import com.software.entity.CourseClass;
import com.software.entity.Enrollment;
import org.apache.ibatis.annotations.*;

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

    @Update("update courses set name=#{name}, introduction=#{introduction}, credit=#{credit}, syllabus=#{syllabus}, evaluation=#{evaluation} where id=#{id}")
    void updateCourse(CourseUpdateDto courseUpdateDto);
    @Update("update courses set cover=#{s} where id=#{courseId}")
    void updateCover(String s, Long courseId);
    @Select("select * from courses where name =#{courseName}")
    Course getCourseByName(String courseName);

    void addButchStudents(List<Enrollment> students);

    @Delete("delete from enrollments where student_id=#{studentId} and class_id =#{classId}")
    void deleteStudent(Long studentId, Long classId);
}
