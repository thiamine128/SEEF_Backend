package com.software.controller;

import com.software.annotation.AuthCheck;
import com.software.config.OssConfiguration;
import com.software.constant.JwtClaimsConstant;
import com.software.constant.MessageConstant;
import com.software.constant.RoleConstant;
import com.software.dto.BlogCreateDTO;
import com.software.dto.CommentPageQueryDto;
import com.software.exception.NoSuchTopicException;
import com.software.exception.PermissionDeniedException;
import com.software.result.PageResult;
import com.software.result.Result;
import com.software.service.BlogService;
import com.software.utils.AliOssUtil;
import com.software.utils.BaseContext;
import com.software.vo.BlogVO;
import com.software.vo.OSSPostSignatureVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.UUID;

@Tag(name = "博客接口")
@RestController
@RequestMapping("/api/blog")
@Slf4j
public class BlogController {
    @Autowired
    private BlogService blogService;
    @Autowired
    private AliOssUtil aliOssUtil;

    @PostMapping("/create")
    @Operation(summary = "创建博客")
    public Result createBlog(@RequestBody BlogCreateDTO blogCreateDTO) {
        blogService.create(blogCreateDTO);
        return Result.success();
    }

    @GetMapping("/detail")
    @Operation(summary = "浏览博客")
    public Result<BlogVO> getBlog(Long blogId) {
        return Result.success(BlogVO.fromBlog(blogService.getDetail(blogId),blogService.isLike(blogId),blogService.isFavor(blogId)));
    }

    @GetMapping("/viewComments")
    @Operation(summary = "获取评论")
    public Result getComments(@RequestParam int page,@RequestParam int pageSize, @RequestParam Long blogId) {
        CommentPageQueryDto commentPageQueryDto = new CommentPageQueryDto(page, pageSize, blogId);
        return Result.success(blogService.viewComments(commentPageQueryDto));
    }

    @DeleteMapping("/deleteBlog")
    @Operation(summary = "删除博客")
    @AuthCheck(mustRole = {RoleConstant.ADMIN,RoleConstant.TEACHER,RoleConstant.STUDENT})
    public Result deleteBlogs(@RequestParam  Long blogId) {
        Map<String, Object> currentUser = BaseContext.getCurrentUser();
        String role = currentUser.get(JwtClaimsConstant.USER_ROLE).toString();
        Long id = Long.parseLong(currentUser.get(JwtClaimsConstant.USER_ID).toString());
        if (role.equals(RoleConstant.STUDENT) && !blogService.getDetail(blogId).getUserId().equals(id))
            throw new PermissionDeniedException(MessageConstant.PERMISSION_DENIED);
        blogService.deleteblog(blogId);
        return Result.success();
    }

    @PostMapping("/like")
    @Operation(summary = "（取消）点赞博客")
    public Result likeBlog(@RequestParam Long blogId){
        blogService.like(blogId);
        return Result.success();
    }

    @PostMapping("/favor")
    @Operation(summary = "（取消）收藏博客")
    public Result favorBlog(@RequestParam Long blogId){
        blogService.favor(blogId);
        return Result.success();
    }

    @GetMapping("/listFavor")
    @Operation(summary = "获取我的收藏")
    public Result<PageResult> listFavor(@RequestParam int page,@RequestParam int pageSize,@RequestParam int previewLength){
        Map<String,Object> currentUser = BaseContext.getCurrentUser();
        Long id = Long.parseLong(currentUser.get(JwtClaimsConstant.USER_ID).toString());
        List<Long> ids = blogService.getfavorBlogIds(id);
        PageResult pageResult = blogService.listFavor(ids,page,pageSize, previewLength);
        return Result.success(pageResult);
    }

}
