package com.software.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.software.constant.JwtClaimsConstant;
import com.software.constant.MessageConstant;
import com.software.dto.ClassCreateDto;
import com.software.dto.ClassQueryDto;
import com.software.dto.CourseCreateDto;
import com.software.dto.CoursePageQueryDto;
import com.software.entity.Course;
import com.software.entity.CourseClass;
import com.software.exception.PermissionDeniedException;
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
    public void createCourse(CourseCreateDto courseCreateDto) {
        Course course = new Course();
        course.setName(courseCreateDto.getName());
        courseMapper.create(course);
        Map<String,Object> currentUser = BaseContext.getCurrentUser();
        Long id =(long) currentUser.get(JwtClaimsConstant.USER_ID);
        courseMapper.addTeacher(course.getId(), id);
    }

    @Override
    public void addClass(ClassCreateDto classCreateDto) {
        Map<String,Object> currentUser = BaseContext.getCurrentUser();
        Long id =(long) currentUser.get(JwtClaimsConstant.USER_ID);
        if (courseMapper.checkTeacher(id, classCreateDto.getCourseId()) == null) throw new PermissionDeniedException(MessageConstant.PERMISSION_DENIED);
        classMapper.addClass(classCreateDto.getCourseId(), classCreateDto.getName());
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
}
