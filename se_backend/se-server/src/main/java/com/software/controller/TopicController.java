package com.software.controller;

import com.software.constant.JwtClaimsConstant;
import com.software.constant.MessageConstant;
import com.software.constant.RoleConstant;
import com.software.dto.BlogPreviewPageQueryDTO;
import com.software.dto.TopicCreateDto;
import com.software.dto.TopicPageQueryDTO;
import com.software.exception.PermissionDeniedException;
import com.software.result.PageResult;
import com.software.result.Result;
import com.software.service.TopicService;
import com.software.utils.BaseContext;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Tag(name = "板块相关接口")
@RestController
@RequestMapping("/api/topic")
@Slf4j
public class TopicController {
    @Autowired
    private TopicService topicService;

    @PostMapping("/create")
    @Operation(summary = "创建板块")
    public Result createTopic(@RequestBody TopicCreateDto topicCreateDto) {
        topicService.createTopic(topicCreateDto);
        return Result.success();
    }

    @GetMapping("/pagedList")
    @Operation(summary = "板块分页查询")
    public Result<PageResult> getPagedList(@ParameterObject TopicPageQueryDTO topicPageQueryDTO){
        //TopicPageQueryDTO topicPageQueryDTO = new TopicPageQueryDTO(page, pageSize, name);
        log.info("topic分页查询:{}", topicPageQueryDTO);
        PageResult pageResult = topicService.pageQuery(topicPageQueryDTO);//后绪步骤定义
        return Result.success(pageResult);

    }

    @GetMapping("/viewBlogs")
    @Operation(summary = "板块博客")
    public Result<PageResult> viewBlogs(@RequestParam int page, @RequestParam int pageSize, @RequestParam Long topicId, @RequestParam int previewLength) {
        BlogPreviewPageQueryDTO blogPageQueryDto = new BlogPreviewPageQueryDTO(page, pageSize, topicId, previewLength);
        PageResult pageResult = topicService.getBlogs(blogPageQueryDto);
        return Result.success(pageResult);
    }

    @DeleteMapping("/deleteTopic")
    @Operation(summary = "删除板块")
    public Result deleteBlogs(@RequestParam  Integer topicId){
        Map<String,Object> currentUser = BaseContext.getCurrentUser();
        String role = currentUser.get(JwtClaimsConstant.USER_ROLE).toString();
        if ((!role.equals(RoleConstant.ADMIN))&&(!role.equals(RoleConstant.TEACHER)))throw new PermissionDeniedException(MessageConstant.PERMISSION_DENIED);
        topicService.deleteTopic(topicId);
        return Result.success();
    }
}
