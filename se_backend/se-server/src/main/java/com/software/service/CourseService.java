package com.software.service;

import com.software.dto.ClassCreateDto;
import com.software.dto.ClassQueryDto;
import com.software.dto.CourseCreateDto;
import com.software.dto.CoursePageQueryDto;
import com.software.entity.CourseClass;
import com.software.result.PageResult;

import java.util.List;

public interface CourseService {
    void createCourse(CourseCreateDto courseCreateDto);
    void addClass(ClassCreateDto classCreateDto);
    PageResult pageQuery(CoursePageQueryDto coursePageQueryDto);
    List<CourseClass> getClasses(ClassQueryDto classQueryDto);
}
