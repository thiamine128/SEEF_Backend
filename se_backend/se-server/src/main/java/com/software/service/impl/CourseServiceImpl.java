package com.software.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.software.constant.JwtClaimsConstant;
import com.software.constant.MessageConstant;
import com.software.dto.*;
import com.software.entity.Course;
import com.software.entity.CourseClass;
import com.software.entity.Enrollment;
import com.software.entity.JoinClassRequest;
import com.software.exception.InvalidRequestException;
import com.software.exception.RequestSentException;
import com.software.mapper.ClassMapper;
import com.software.mapper.CourseMapper;
import com.software.mapper.JoinClassRequestMapper;
import com.software.result.PageResult;
import com.software.service.CourseService;
import com.software.utils.BaseContext;
import com.software.vo.CourseClassVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private ClassMapper classMapper;
    @Autowired
    private JoinClassRequestMapper joinClassRequestMapper;
    @Autowired
    private CourseService courseService;
    @Override
    public Course createCourse(CourseCreateDto courseCreateDto) {
        Course course = new Course();
        course.setName(courseCreateDto.getName());
        course.setCredit(courseCreateDto.getCredit());
        course.setIntroduction(courseCreateDto.getIntroduction());
        course.setSyllabus(courseCreateDto.getSyllabus());
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
        courseClass.setTimeData(JSONObject.parseArray(JSON.toJSONString(classCreateDto.getTimeData())));
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
    public List<CourseClassVO> getClasses(ClassQueryDto classQueryDto) {
        Long id = Long.parseLong(BaseContext.getCurrentUser().get(JwtClaimsConstant.USER_ID).toString());
        return courseMapper.getClasses(classQueryDto.getCourseId()).stream().map(courseClass -> {
            return CourseClassVO.fromCourseClass(courseClass, classMapper.getTeachers(courseClass.getId()), checkClassPermission(courseClass.getId()), getCourseById(courseClass.getCourseId()).getName());
        }).toList();
    }

    @Override
    public boolean hasPermission(Long courseId) {
        Map<String,Object> currentUser = BaseContext.getCurrentUser();
        Long id =(long) currentUser.get(JwtClaimsConstant.USER_ID);
        return courseMapper.checkTeacher(id, courseId) != null;
    }

    @Override
    public boolean checkClassPermission(Long classId) {
        return hasPermission(getCourseByClass(classId));
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
    public List<CourseClassVO> getUserClasses(Long userId) {
        List<Long> ids = classMapper.getUserClasses(userId);
        return ids.stream().map(id -> classMapper.getCourseClass(id)).map(courseClass -> {
            return CourseClassVO.fromCourseClass(courseClass, classMapper.getTeachers(courseClass.getId()), checkClassPermission(courseClass.getId()), courseService.getCourseById(courseClass.getCourseId()).getName());
        }).toList();
    }

    @Override
    public List<Long> getAllUserClasses(Long userId) {
        return classMapper.getUserClasses(userId);
    }

    @Override
    public void updateClass(ClassUpdateDto classUpdateDto) {
        classMapper.updateClass(classUpdateDto);
    }

    @Override
    public Course getCourseById(Long id) {
        return classMapper.getCourseById(id);
    }

    @Override
    public void requestJoinClass(Long studentId, Long classId) {
        if (joinClassRequestMapper.canRequest(studentId, classId) == null) {
            joinClassRequestMapper.newRequest(UUID.randomUUID().toString(), studentId, classId);
        } else {
            throw new RequestSentException(MessageConstant.REQUEST_SENT);
        }
    }

    @Transactional
    @Override
    public void pendJoinClassRequest(String id, Integer state) {
        if (state == null || !(state.equals(1) || state.equals(2)) || joinClassRequestMapper.updateState(id, state) == 0) {
            throw new InvalidRequestException(MessageConstant.INVALID_REQUEST);
        } else if (state.equals(1)) {
            JoinClassRequest joinClassRequest = joinClassRequestMapper.getRequest(id);
            Long courseId = classMapper.getCourse(joinClassRequest.getClassId());
            classMapper.addStudent(joinClassRequest.getStudentId(), joinClassRequest.getClassId(), courseId);
        }
    }


    @Override
    public PageResult listJoinClassRequest(JoinClassRequestPageQueryDto joinClassRequestPageQueryDto) {
        PageHelper.startPage(joinClassRequestPageQueryDto.getPage(), joinClassRequestPageQueryDto.getPageSize());
        Page page = (Page) joinClassRequestMapper.pageQuery(joinClassRequestPageQueryDto.getCourseId());
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    public void updateCover(String s, Long courseId) {
        courseMapper.updateCover(s,courseId);
    }

    @Override
    public CourseClassVO getClass(Long classId) {
        CourseClass courseClass = classMapper.getCourseClass(classId);
        return CourseClassVO.fromCourseClass(courseClass, classMapper.getTeachers(classId), checkClassPermission(classId), getCourseById(courseClass.getCourseId()).getName());
    }

    @Override
    public Course getCourseByName(String name) {
        return courseMapper.getCourseByName(name);
    }

    @Override
    public Long getClassIdByName(String name) {
        return classMapper.getClassIdByName(name);
    }

    @Override
    public void addButchStudents(List<JoinClassDTO> students) {
        List<Enrollment> enrollments = new ArrayList<>();
        for(JoinClassDTO req:students)
        {
            Enrollment e = new Enrollment(req.getUserId(),null,req.getClassId(),req.getCourseId());
            enrollments.add(e);
        }
        courseMapper.addButchStudents(enrollments);
    }

    @Override
    public void deleteBathcStudents(List<DeleteStudentReqDTO> students) {
        for(DeleteStudentReqDTO student:students){
        courseMapper.deleteStudent(student.getStudentId(),student.getClassId());
        }
    }

    @Override
    public List<CourseClassVO> getTeacherClass(Long teacherId) {
        List<Long> classIds = courseMapper.getClassesId(teacherId);
        if(classIds.isEmpty())
            return null;
        return courseMapper.getTeacherClasses(classIds).stream().map(courseClass -> {
            return CourseClassVO.fromCourseClass(courseClass, classMapper.getTeachers(courseClass.getId()), true, getCourseById(courseClass.getCourseId()).getName());
        }).toList();
    }
}
