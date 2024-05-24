package com.software.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.software.constant.JwtClaimsConstant;
import com.software.dto.*;
import com.software.entity.Course;
import com.software.entity.CourseClass;
import com.software.mapper.ClassMapper;
import com.software.mapper.CourseMapper;
import com.software.result.PageResult;
import com.software.service.CourseService;
import com.software.utils.BaseContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private ClassMapper classMapper;
    @Override
    public Course createCourse(CourseCreateDto courseCreateDto) {
        Course course = new Course();
        course.setName(courseCreateDto.getName());
        course.setCredit(courseCreateDto.getCredit());
        course.setIntroduction(courseCreateDto.getIntroduction());
        course.setSummary(courseCreateDto.getSummary());
        course.setEvaluation(courseCreateDto.getEvaluation());
        courseMapper.create(course);
        Map<String,Object> currentUser = BaseContext.getCurrentUser();
        Long id =(long) currentUser.get(JwtClaimsConstant.USER_ID);
        courseMapper.addTeacher(course.getId(), id);
        return course;
    }

    @Override
    public void updateCourse(CourseUpdateDto courseUpdateDto) {
        courseMapper.updateCourse(courseUpdateDto);
    }

    @Override
    public CourseClass addClass(ClassCreateDto classCreateDto) {
        CourseClass courseClass = new CourseClass();
        courseClass.setCourseId(classCreateDto.getCourseId());
        courseClass.setName(classCreateDto.getName());
        courseClass.setTime(classCreateDto.getTime());
        courseClass.setLocation(classCreateDto.getLocation());
        classMapper.addClass(courseClass);
        return courseClass;
    }

    @Override
    public PageResult pageQuery(CoursePageQueryDto coursePageQueryDto) {
        PageHelper.startPage(coursePageQueryDto.getPage(), coursePageQueryDto.getPageSize());
        Page page = (Page) courseMapper.pageQuery(coursePageQueryDto);//后绪步骤实现

        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    public List<CourseClass> getClasses(ClassQueryDto classQueryDto) {
        return courseMapper.getClasses(classQueryDto.getCourseId());
    }

    @Override
    public boolean hasPermission(Long courseId) {
        Map<String,Object> currentUser = BaseContext.getCurrentUser();
        Long id =(long) currentUser.get(JwtClaimsConstant.USER_ID);
        return courseMapper.checkTeacher(id, courseId) != null;
    }

    @Override
    public Long getCourseByClass(Long classId) {
        return classMapper.getCourse(classId);
    }

    @Override
    public void addTeacherToClass(Long teacherId, Long classId) {
        classMapper.addTeacher(classId, teacherId);
    }

    @Override
    public void addTeacherToCourse(Long teacherId, Long courseId) {
        courseMapper.addTeacher(courseId, teacherId);
    }

    @Override
    public void removeTeacherFromClass(Long teacherId, Long classId) {
        classMapper.deleteTeacher(classId, teacherId);
    }

    @Override
    public List<Long> getTeachers(Long courseId) {
        return courseMapper.getTeachers(courseId);
    }

    @Override
    public List<Long> getTeachersInClass(Long classId) {
        return classMapper.getTeachers(classId);
    }

    @Override
    public PageResult getUserClasses(UserClassesPageQueryDto userClassesPageQueryDto, Long userId) {
        PageHelper.startPage(userClassesPageQueryDto.getPage(), userClassesPageQueryDto.getPageSize());
        Page page = (Page) classMapper.getUserCourses(userId);//后绪步骤实现
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    public void updateClass(ClassUpdateDto classUpdateDto) {
        classMapper.updateClass(classUpdateDto);
    }

    @Override
    public Course getCourseById(Long id) {
        return classMapper.getCourseById(id);
    }
}
