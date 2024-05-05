package com.software.controller;

import com.software.constant.MessageConstant;
import com.software.exception.NoSuchTopicException;
import com.software.result.Result;
import com.software.service.BlogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "博客接口")
@RestController
@RequestMapping("/api/blog")
@Slf4j
public class BlogController {
    @Autowired
    private BlogService blogService;

    @PostMapping("/create")
    @Operation(summary = "创建博客")
    public Result createBlog(@RequestBody String title, @RequestBody String content, @RequestBody Long topicId) {
        blogService.create(title, content, topicId);
        return Result.success();
    }
}
