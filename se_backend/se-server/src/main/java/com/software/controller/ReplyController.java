package com.software.controller;

import com.software.annotation.AuthCheck;
import com.software.constant.JwtClaimsConstant;
import com.software.constant.MessageConstant;
import com.software.constant.RoleConstant;
import com.software.dto.ReplyCreateDto;
import com.software.exception.PermissionDeniedException;
import com.software.result.Result;
import com.software.service.ReplyService;
import com.software.utils.BaseContext;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Tag(name = "回复相关接口")
@RestController
@RequestMapping("/api/reply")
@Slf4j
public class ReplyController {
    @Autowired
    private ReplyService replyService;
    @PostMapping("/create")
    @Operation(summary = "发表回复")
    public Result makeReply(@RequestBody ReplyCreateDto replyCreateDto) {
        replyService.makeReply(replyCreateDto);
        return Result.success();
    }
    @DeleteMapping("/deleteReply")
    @Operation(summary = "删除回复")
    public Result deleteReply(@RequestParam  Long replyId){
        Map<String,Object> currentUser = BaseContext.getCurrentUser();
        String role = currentUser.get(JwtClaimsConstant.USER_ROLE).toString();
        Long id = Long.parseLong(currentUser.get(JwtClaimsConstant.USER_ID).toString());
        if (role.equals(RoleConstant.STUDENT) && !replyService.getReply(replyId).getUserId().equals(id)) throw new PermissionDeniedException(MessageConstant.PERMISSION_DENIED);
        replyService.deleteReply(replyId);
        return Result.success();
    }
}
