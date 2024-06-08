package com.software.controller;

import com.software.config.OssConfiguration;
import com.software.constant.JwtClaimsConstant;
import com.software.constant.MessageConstant;
import com.software.constant.RoleConstant;
import com.software.dto.*;
import com.software.entity.Assignment;
import com.software.entity.StudentAssignment;
import com.software.entity.User;
import com.software.exception.InvalidParameterException;
import com.software.exception.PermissionDeniedException;
import com.software.result.PageResult;
import com.software.result.Result;
import com.software.service.AssignmentService;
import com.software.service.CourseService;
import com.software.service.UserService;
import com.software.utils.AliOssUtil;
import com.software.utils.BaseContext;
import com.software.vo.AssignmentVO;
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
import java.util.UUID;

@Tag(name = "作业接口")
@RestController
@RequestMapping("/api/assignment")
@Slf4j
public class AssignmentController {
    @Autowired
    private AssignmentService assignmentService;
    @Autowired
    private CourseService courseService;
    @Autowired
    AliOssUtil aliOssUtil;
    @Autowired
    private UserService userService;

    @PostMapping("/uploadDescAttachment")
    @Operation(summary = "老师上传作业附件")
    public Result<OSSPostSignatureVO> uploadDescAttachment() throws UnsupportedEncodingException {
        String objectName = "assignmentDesc/" + UUID.randomUUID().toString();
        AliOssUtil.PostSignature postSignature = aliOssUtil.generatePostSignatureNoFilename(objectName, System.currentTimeMillis() + OssConfiguration.EXPIRE_SEC * 1000, 524288000);
        OSSPostSignatureVO ossPostSignatureVO = OSSPostSignatureVO.builder()
                .accessKeyId(postSignature.getAccessKeyId())
                .objectName(postSignature.getObjectName())
                .encodedPolicy(postSignature.getEncodedPolicy())
                .postSignature(postSignature.getPostSignature())
                .host(postSignature.getHost()).build();
        return Result.success(ossPostSignatureVO);
    }
    @PostMapping("/uploadAttachment")
    @Operation(summary = "学生上传作业附件")
    public Result<OSSPostSignatureVO> uploadAttachment(Long assignmentId) throws UnsupportedEncodingException {
        Long id = Long.parseLong(BaseContext.getCurrentUser().get(JwtClaimsConstant.USER_ID).toString());
        User user = userService.getByACCount(userService.getUsername(id));
        Assignment assignment = assignmentService.getAssignment(assignmentId);
        String objectName = "assignment/" + assignmentId + "/" + user.getName() + "_" + user.getRealName() + "_" + assignment.getTitle();
        AliOssUtil.PostSignature postSignature = aliOssUtil.generatePostSignature(objectName, System.currentTimeMillis() + OssConfiguration.EXPIRE_SEC * 1000, 524288000);
        OSSPostSignatureVO ossPostSignatureVO = OSSPostSignatureVO.builder()
                .accessKeyId(postSignature.getAccessKeyId())
                .objectName(postSignature.getObjectName())
                .encodedPolicy(postSignature.getEncodedPolicy())
                .postSignature(postSignature.getPostSignature())
                .host(postSignature.getHost()).build();
        return Result.success(ossPostSignatureVO);
    }

    @PostMapping("/publish")
    @Operation(summary = "发布作业")
    public Result publishAssignment(@RequestBody AssignmentPublishDto assignmentPublishDto) {
        Map<String,Object> currentUser = BaseContext.getCurrentUser();
        String role = currentUser.get(JwtClaimsConstant.USER_ROLE).toString();
        if (!role.equals(RoleConstant.TEACHER)) throw new PermissionDeniedException(MessageConstant.PERMISSION_DENIED);
        if (!courseService.hasPermission(courseService.getCourseByClass(assignmentPublishDto.getClassId()))) throw new PermissionDeniedException(MessageConstant.PERMISSION_DENIED);
        if(assignmentPublishDto.getTitle().isBlank()) throw new InvalidParameterException(MessageConstant.PARAMETER_BLANK);
        assignmentService.publishAssignment(assignmentPublishDto);
        return Result.success();
    }

    @GetMapping("/all")
    @Operation(summary = "获取全部作业")
    public Result<List<AssignmentVO>> getAssignments(@RequestParam(required = false) Boolean showOutdated) {
        Long id = Long.parseLong(BaseContext.getCurrentUser().get(JwtClaimsConstant.USER_ID).toString());
        AssignmentQueryDto assignmentQueryDto = new AssignmentQueryDto(showOutdated != null && showOutdated, id);
        return Result.success(assignmentService.getAllAssignments(assignmentQueryDto));
    }

    @GetMapping("/listByClass")
    @Operation(summary = "班级作业")
    public Result<List<Assignment>> getAssignmentsInClass(@ParameterObject AssignmentClassQueryDto assignmentQueryDto) {
        return Result.success(assignmentService.getAssignmentsInClass(assignmentQueryDto.getClassId()));
    }

    @PostMapping("/submit")
    @Operation(summary = "提交作业")
    public Result submitAssignment(@ParameterObject AssignmentSubmitDto assignmentSubmitDto) {
        Map<String,Object> currentUser = BaseContext.getCurrentUser();
        Long id =(long) currentUser.get(JwtClaimsConstant.USER_ID);

        assignmentService.submitAssignment(id, assignmentSubmitDto);
        return Result.success();
    }

    @PostMapping("/markAssignment")
    @Operation(summary = "批改作业")
    public Result markHw(@RequestBody HomeWorkFeedBackDTO homeWorkFeedBackDTO) {
        assignmentService.markHw(homeWorkFeedBackDTO);
        return Result.success();
    }

    @GetMapping("/studentAssignment")
    @Operation(summary = "获取学生作业")
    public Result<List<StudentAssignmentDTO>> studentAssignment(@RequestParam Long assignmentId) {
        List<StudentAssignmentDTO> result = assignmentService.getStudentAssignments(assignmentId);
        return Result.success(result);
    }
}
