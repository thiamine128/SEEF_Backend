package com.software.controller;

import com.software.constant.JwtClaimsConstant;
import com.software.dto.MessageMarkReadDTO;
import com.software.dto.SendMessageDTO;
import com.software.entity.Message;
import com.software.result.Result;
import com.software.service.MessageService;
import com.software.utils.BaseContext;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "私信相关接口")
@RestController
@RequestMapping("/api/message")
@Slf4j
public class MessageController {
    @Autowired
    MessageService messageService;
    @PostMapping("/create")
    @Operation(summary = "发送私信")
    public Result sendMessage(@RequestBody SendMessageDTO sendMessageDTO) {
        Long id = Long.parseLong(BaseContext.getCurrentUser().get(JwtClaimsConstant.USER_ID).toString());
        messageService.sendMessage(sendMessageDTO, id);
        return Result.success();
    }

    @GetMapping("/messageCount")
    @Operation(summary = "私信数量")
    public Result<Long> getCount() {
        Long id = Long.parseLong(BaseContext.getCurrentUser().get(JwtClaimsConstant.USER_ID).toString());
        return Result.success(messageService.getCnt(id));
    }

    @GetMapping("/unread")
    @Operation(summary = "未读消息")
    public Result<List<Message>> getMessages() {
        Long id = Long.parseLong(BaseContext.getCurrentUser().get(JwtClaimsConstant.USER_ID).toString());
        return Result.success(messageService.getMessages(id));
    }
}
