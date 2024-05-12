package com.software.controller;

import com.software.constant.JwtClaimsConstant;
import com.software.constant.MessageConstant;
import com.software.constant.RoleConstant;
import com.software.dto.ClassCreateDto;
import com.software.dto.ClassQueryDto;
import com.software.dto.CourseCreateDto;
import com.software.dto.CoursePageQueryDto;
import com.software.exception.PermissionDeniedException;
import com.software.result.PageResult;
import com.software.result.Result;
import com.software.service.CourseService;
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

    @PostMapping("/create")
    @Operation(summary = "创建课程")
    public Result createCourse(@RequestBody CourseCreateDto courseCreateDto) {
        Map<String,Object> currentUser = BaseContext.getCurrentUser();
        String role = currentUser.get(JwtClaimsConstant.USER_ROLE).toString();
        if (!role.equals(RoleConstant.TEACHER)) throw new PermissionDeniedException(MessageConstant.PERMISSION_DENIED);
        courseService.createCourse(courseCreateDto);
        return Result.success();
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
    @Operation(summary = "查询课程")
    public Result pagedList(@ParameterObject CoursePageQueryDto coursePageQueryDto) {
        PageResult pageResult = courseService.pageQuery(coursePageQueryDto);//后绪步骤定义
        return Result.success(pageResult);
    }



}
