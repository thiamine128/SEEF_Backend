package com.software.service;

import com.software.dto.*;
import com.software.entity.CourseClass;
import com.software.result.PageResult;

import java.util.List;

public interface CourseService {
    void createCourse(CourseCreateDto courseCreateDto);
    void addClass(ClassCreateDto classCreateDto);
    PageResult pageQuery(CoursePageQueryDto coursePageQueryDto);
    List<CourseClass> getClasses(ClassQueryDto classQueryDto);
    boolean hasPermission(Long courseId);
    Long getCourse(Long classId);
    PageResult getUserClasses(UserClassesPageQueryDto userClassesPageQueryDto, Long userId);
}
