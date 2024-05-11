package com.software.controller;

import com.software.config.OssConfiguration;
import com.software.constant.JwtClaimsConstant;
import com.software.constant.MessageConstant;
import com.software.constant.RoleConstant;
import com.software.dto.AssignmentPublishDto;
import com.software.dto.AssignmentClassQueryDto;
import com.software.dto.AssignmentSubmitDto;
import com.software.exception.PermissionDeniedException;
import com.software.result.Result;
import com.software.service.AssignmentService;
import com.software.service.CourseService;
import com.software.utils.AliOssUtil;
import com.software.utils.BaseContext;
import com.software.vo.OSSPostSignatureVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
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

    @PostMapping("/uploadAttachment")
    @Operation(summary = "上传作业附件")
    public Result uploadAttachment() throws UnsupportedEncodingException {
        String objectName = "assignment/" + UUID.randomUUID().toString();
        AliOssUtil.PostSignature postSignature = aliOssUtil.generatePostSignature(objectName, System.currentTimeMillis() + OssConfiguration.EXPIRE_SEC * 1000, 52428800);
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
        if (!courseService.hasPermission(courseService.getCourse(assignmentPublishDto.getClassId()))) throw new PermissionDeniedException(MessageConstant.PERMISSION_DENIED);
        assignmentService.publishAssignment(assignmentPublishDto);
        return Result.success();
    }

    @GetMapping("/listByClass")
    @Operation(summary = "班级作业")
    public Result getAssignmentsInClass(@ParameterObject AssignmentClassQueryDto assignmentQueryDto) {
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
}
