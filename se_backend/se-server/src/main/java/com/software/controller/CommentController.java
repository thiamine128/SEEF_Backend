package com.software.controller;

import com.software.dto.CommentCreateDto;
import com.software.result.Result;
import com.software.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "评论相关接口")
@RestController
@RequestMapping("/api/comment")
@Slf4j
public class CommentController {
    @Autowired
    private CommentService commentService;
    @PostMapping("/create")
    @Operation(summary = "发表评论")
    public Result makeComment(@RequestBody CommentCreateDto commentCreateDto) {
        commentService.makeComment(commentCreateDto);
        return Result.success();
    }

}
