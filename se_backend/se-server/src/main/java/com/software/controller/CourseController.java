package com.software.controller;

import com.software.annotation.AuthCheck;
import com.software.config.OssConfiguration;
import com.software.constant.JwtClaimsConstant;
import com.software.constant.MessageConstant;
import com.software.constant.RoleConstant;
import com.software.dto.*;
import com.software.entity.Course;
import com.software.entity.CourseClass;
import com.software.exception.InvalidUserException;
import com.software.exception.PermissionDeniedException;
import com.software.result.PageResult;
import com.software.result.Result;
import com.software.service.CourseService;
import com.software.service.UserService;
import com.software.utils.AliOssUtil;
import com.software.utils.BaseContext;
import com.software.vo.CourseClassVO;
import com.software.vo.OSSPostSignatureVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.List;
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

    @PostMapping("/updateCourse")
    @Operation(summary = "更新课程信息")
    public Result updateCourse(@RequestBody CourseUpdateDto courseUpdateDto) {
        Map<String,Object> currentUser = BaseContext.getCurrentUser();
        String role = currentUser.get(JwtClaimsConstant.USER_ROLE).toString();
        if (!role.equals(RoleConstant.TEACHER)) throw new PermissionDeniedException(MessageConstant.PERMISSION_DENIED);
        if (!courseService.hasPermission(courseUpdateDto.getId())) throw new PermissionDeniedException(MessageConstant.PERMISSION_DENIED);
        courseService.updateCourse(courseUpdateDto);
        return Result.success();
    }

    @PostMapping("/updateClass")
    @Operation(summary = "更新教学班信息")
    public Result updateClass(@RequestBody ClassUpdateDto classUpdateDto) {
        Map<String,Object> currentUser = BaseContext.getCurrentUser();
        String role = currentUser.get(JwtClaimsConstant.USER_ROLE).toString();
        if (!role.equals(RoleConstant.TEACHER)) throw new PermissionDeniedException(MessageConstant.PERMISSION_DENIED);
        if (!courseService.checkClassPermission(classUpdateDto.getId())) throw new PermissionDeniedException(MessageConstant.PERMISSION_DENIED);
        courseService.updateClass(classUpdateDto);
        return Result.success();
    }

    @PostMapping("/addClass")
    @Operation(summary = "添加教学班")
    public Result<CourseClass> addClass(@RequestBody ClassCreateDto classCreateDto) {
        Map<String,Object> currentUser = BaseContext.getCurrentUser();
        String role = currentUser.get(JwtClaimsConstant.USER_ROLE).toString();
        if (!role.equals(RoleConstant.TEACHER)) throw new PermissionDeniedException(MessageConstant.PERMISSION_DENIED);
        if (!courseService.hasPermission(classCreateDto.getCourseId())) throw new PermissionDeniedException(MessageConstant.PERMISSION_DENIED);
        return Result.success(courseService.addClass(classCreateDto));
    }

    @GetMapping("/listClass")
    @Operation(summary = "查询课程教学班")
    public Result<List<CourseClassVO>> pagedList(@ParameterObject ClassQueryDto classQueryDto) {
        return Result.success(courseService.getClasses(classQueryDto));
    }

    @GetMapping("/list")
    @Operation(summary = "根据名字查询课程")
    public Result<PageResult> pagedList(@ParameterObject CoursePageQueryDto coursePageQueryDto) {
        PageResult pageResult = courseService.pageQuery(coursePageQueryDto);//后绪步骤定义
        return Result.success(pageResult);
    }
    @GetMapping("/getCourseById")
    @Operation(summary = "根据id查询课程")
    public Result<Course> getCourseById(@RequestParam Long id) {
        Course course = courseService.getCourseById(id);
        return Result.success(course);
    }

    @GetMapping("/teachers")
    @Operation(summary = "查询课程组")
    public Result<List<Long>> getTeachers(@RequestParam Long courseId) {
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
    public Result<List<Long>> getTeachersInClass(@RequestParam Long classId) {
        return Result.success(courseService.getTeachersInClass(classId));
    }

    @PostMapping("/requestJoinClass")
    @Operation(summary = "向老师申请加入课程")
    @AuthCheck(mustRole = {RoleConstant.STUDENT})
    public Result requestJoinClass(@RequestBody RequestJoinClassDto requestJoinClassDto) {
        Long id = Long.parseLong(BaseContext.getCurrentUser().get(JwtClaimsConstant.USER_ID).toString());
        courseService.requestJoinClass(id, requestJoinClassDto.getClassId());
        return Result.success();
    }

    @PostMapping("/pendJoinClassRequest")
    @Operation(summary = "批量处理加入课程请求")
    @AuthCheck(mustRole = {RoleConstant.ADMIN,RoleConstant.TEACHER})
    public Result pendJoinClassRequest(@RequestBody JoinClassRequestPendDto joinClassRequestPendDto) {
        for (String id : joinClassRequestPendDto.getIds()) {
            courseService.pendJoinClassRequest(id, joinClassRequestPendDto.getState());
        }
        return Result.success();
    }

    @GetMapping("/getJoinClassRequests")
    @Operation(summary = "获取加入课程请求列表")
    @AuthCheck(mustRole = {RoleConstant.ADMIN,RoleConstant.TEACHER})
    public Result<PageResult> getJoinClassRequests(@ParameterObject JoinClassRequestPageQueryDto joinClassRequestPageQueryDto) {
        return Result.success(courseService.listJoinClassRequest(joinClassRequestPageQueryDto));
    }

    @PostMapping("/addCourseCover")
    @Operation(summary = "添加课程封面")
    public Result<OSSPostSignatureVO> addCourseCover(@RequestParam Long CourseId) throws UnsupportedEncodingException {

        String objectName = "CourseCover/" + CourseId;
        AliOssUtil.PostSignature postSignature = aliOssUtil.generatePostSignature(objectName, System.currentTimeMillis() + OssConfiguration.EXPIRE_SEC * 1000, 52428800);
        OSSPostSignatureVO ossPostSignatureVO = OSSPostSignatureVO.builder()
                .accessKeyId(postSignature.getAccessKeyId())
                .objectName(postSignature.getObjectName())
                .encodedPolicy(postSignature.getEncodedPolicy())
                .postSignature(postSignature.getPostSignature())
                .host(postSignature.getHost()).build();
        courseService.updateCover(aliOssUtil.buildPathFromObjectName(objectName),CourseId);
        return Result.success(ossPostSignatureVO);
    }

    @GetMapping("/getClass")
    @Operation(summary = "获取教学班信息")
    public Result<CourseClassVO> getCourse(@RequestParam Long classId) {
        return Result.success(courseService.getClass(classId));
    }

    @GetMapping("/getMyClasses")
    @Operation(summary = "获取我的教学班")
    public Result<List<CourseClassVO>> getMyClasses() {
        Long id = Long.parseLong(BaseContext.getCurrentUser().get(JwtClaimsConstant.USER_ID).toString());
        return Result.success(courseService.getUserClasses(id));
    }
}