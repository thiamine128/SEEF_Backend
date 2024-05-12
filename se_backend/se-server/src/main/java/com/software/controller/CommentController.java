package com.software.controller;

import com.software.constant.JwtClaimsConstant;
import com.software.constant.MessageConstant;
import com.software.constant.RoleConstant;
import com.software.dto.CommentCreateDto;
import com.software.exception.PermissionDeniedException;
import com.software.result.Result;
import com.software.service.CommentService;
import com.software.utils.BaseContext;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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

    @DeleteMapping("/deleteComment")
    @Operation(summary = "删除评论")
    public Result deleteBlogs(@RequestParam  Long commentId){
        Map<String,Object> currentUser = BaseContext.getCurrentUser();
        String role = currentUser.get(JwtClaimsConstant.USER_ROLE).toString();
        if ((!role.equals(RoleConstant.ADMIN))&&(!role.equals(RoleConstant.TEACHER)))throw new PermissionDeniedException(MessageConstant.PERMISSION_DENIED);
        commentService.deleteComment(commentId);
        return Result.success();
    }
}
