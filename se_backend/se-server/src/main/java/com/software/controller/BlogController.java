package com.software.controller;

import com.software.constant.MessageConstant;
import com.software.dto.BlogCreateDTO;
import com.software.dto.CommentPageQueryDto;
import com.software.exception.NoSuchTopicException;
import com.software.result.Result;
import com.software.service.BlogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Tag(name = "博客接口")
@RestController
@RequestMapping("/api/blog")
@Slf4j
public class BlogController {
    @Autowired
    private BlogService blogService;

    @PostMapping("/create")
    @Operation(summary = "创建博客")
    public Result createBlog(@RequestBody BlogCreateDTO blogCreateDTO) {
        blogService.create(blogCreateDTO);
        return Result.success();
    }

    @GetMapping("/detail")
    @Operation(summary = "浏览博客")
    public Result getBlog(Long blogId) {
        return Result.success(blogService.getDetail(blogId));
    }

    @GetMapping("/viewComments")
    @Operation(summary = "获取评论")
    public Result getComments(@ParameterObject CommentPageQueryDto commentPageQueryDto) {
        return Result.success(blogService.viewComments(commentPageQueryDto));
    }
}
