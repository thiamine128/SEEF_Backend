package com.software.service;

import com.software.dto.*;
import com.software.entity.Course;
import com.software.entity.CourseClass;
import com.software.result.PageResult;
import com.software.vo.CourseClassVO;

import java.util.List;

public interface CourseService {
    Course createCourse(CourseCreateDto courseCreateDto);
    void updateCourse(CourseUpdateDto courseUpdateDto);
    CourseClass addClass(ClassCreateDto classCreateDto);
    PageResult pageQuery(CoursePageQueryDto coursePageQueryDto);
    List<CourseClassVO> getClasses(ClassQueryDto classQueryDto);
    boolean hasPermission(Long courseId);
    boolean checkClassPermission(Long classId);
    Long getCourseByClass(Long classId);
    void addTeacherToClass(Long teacherId, Long classId);
    void addTeacherToCourse(Long teacherId, Long courseId);
    void removeTeacherFromClass(Long teacherId, Long classId);
    List<Long> getTeachers(Long courseId);
    List<Long> getTeachersInClass(Long classId);
    List<CourseClassVO> getUserClasses(Long userId);
    List<Long> getAllUserClasses(Long userId);
    void updateClass(ClassUpdateDto classUpdateDto);
    Course getCourseById(Long id);
    void requestJoinClass(Long studentId, Long classId);
    void pendJoinClassRequest(String id, Integer state);
    PageResult listJoinClassRequest(JoinClassRequestPageQueryDto joinClassRequestPageQueryDto);

    void updateCover(String s, Long courseId);
    CourseClassVO getClass(Long classId);

    Course getCourseByName(String name);

    Long getClassIdByName(String name);
    void addButchStudents(List<JoinClassDTO> students);
}
