package com.software.controller;

import com.software.annotation.AuthCheck;
import com.software.constant.JwtClaimsConstant;
import com.software.constant.MessageConstant;
import com.software.constant.RoleConstant;
import com.software.dto.*;
import com.software.entity.Course;
import com.software.exception.InvalidUserException;
import com.software.exception.PermissionDeniedException;
import com.software.result.PageResult;
import com.software.result.Result;
import com.software.service.CourseService;
import com.software.service.UserService;
import com.software.utils.AliOssUtil;
import com.software.utils.BaseContext;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Tag(name = "课程接口")
@RestController
@RequestMapping("/api/course")
@Slf4j
public class CourseController {
    @Autowired
    private CourseService courseService;
    @Autowired
    private UserService userService;
    @Autowired
    private AliOssUtil aliOssUtil;

    @PostMapping("/create")
    @Operation(summary = "创建课程")
    public Result createCourse(@RequestBody CourseCreateDto courseCreateDto) {
        Map<String,Object> currentUser = BaseContext.getCurrentUser();
        String role = currentUser.get(JwtClaimsConstant.USER_ROLE).toString();
        if (!role.equals(RoleConstant.TEACHER)) throw new PermissionDeniedException(MessageConstant.PERMISSION_DENIED);
        return Result.success(courseService.createCourse(courseCreateDto));
    }

    @PostMapping("/addClass")
    @Operation(summary = "添加教学班")
    public Result addClass(@RequestBody ClassCreateDto classCreateDto) {
        Map<String,Object> currentUser = BaseContext.getCurrentUser();
        String role = currentUser.get(JwtClaimsConstant.USER_ROLE).toString();
        if (!role.equals(RoleConstant.TEACHER)) throw new PermissionDeniedException(MessageConstant.PERMISSION_DENIED);
        if (!courseService.hasPermission(classCreateDto.getCourseId())) throw new PermissionDeniedException(MessageConstant.PERMISSION_DENIED);
        courseService.addClass(classCreateDto);
        return Result.success();
    }

    @GetMapping("/listClass")
    @Operation(summary = "查询教学班")
    public Result pagedList(@ParameterObject ClassQueryDto classQueryDto) {
        return Result.success(courseService.getClasses(classQueryDto));
    }

    @GetMapping("/list")
    @Operation(summary = "根据名字查询课程")
    public Result pagedList(@ParameterObject CoursePageQueryDto coursePageQueryDto) {
        PageResult pageResult = courseService.pageQuery(coursePageQueryDto);//后绪步骤定义
        return Result.success(pageResult);
    }
    @GetMapping("/getCourseById")
    @Operation(summary = "根据名字查询课程")
    public Result<Course> getCourseById(@RequestParam Long id) {
        Course course = courseService.getCourseById(id);
        return Result.success(course);
    }


    @GetMapping("/teachers")
    @Operation(summary = "查询课程组")
    public Result getTeachers(@RequestParam Long courseId) {
        return Result.success(courseService.getTeachers(courseId));
    }

    @PostMapping("/addTeacher")
    @Operation(summary = "添加教师到课程组")
    @AuthCheck(mustRole = {RoleConstant.ADMIN,RoleConstant.TEACHER})
    public Result addTeacher(@RequestParam Long teacherId, @RequestParam Long courseId) {
        if (!courseService.hasPermission(courseId)) throw new PermissionDeniedException(MessageConstant.PERMISSION_DENIED);
        if (!userService.isTeacher(teacherId)) throw new InvalidUserException(MessageConstant.INVALID_USER);
        courseService.addTeacherToCourse(teacherId, courseId);
        return Result.success();
    }

    @PostMapping("/addTeacherToClass")
    @Operation(summary = "分配教师到教学班")
    @AuthCheck(mustRole = {RoleConstant.ADMIN,RoleConstant.TEACHER})
    public Result addTeacherToClass(@RequestParam Long teacherId, @RequestParam Long classId) {
        Long courseId = courseService.getCourseByClass(classId);
        if (!courseService.hasPermission(courseId)) throw new PermissionDeniedException(MessageConstant.PERMISSION_DENIED);
        if (!userService.isTeacher(teacherId)) throw new InvalidUserException(MessageConstant.INVALID_USER);
        courseService.addTeacherToClass(teacherId, classId);
        return Result.success();
    }

    @DeleteMapping("/deleteTeacherFromClass")
    @Operation(summary = "取消教学班教师分配")
    @AuthCheck(mustRole = {RoleConstant.ADMIN,RoleConstant.TEACHER})
    public Result deleteTeacherFromClass(@RequestParam Long teacherId, @RequestParam Long classId) {
        Long courseId = courseService.getCourseByClass(classId);
        if (!courseService.hasPermission(courseId)) throw new PermissionDeniedException(MessageConstant.PERMISSION_DENIED);
        courseService.removeTeacherFromClass(teacherId, classId);
        return Result.success();
    }

    @GetMapping("/getTeachersInClass")
    @Operation(summary = "查询教学班教师")
    public Result getTeachersInClass(@RequestParam Long classId) {
        return Result.success(courseService.getTeachersInClass(classId));
    }
}
