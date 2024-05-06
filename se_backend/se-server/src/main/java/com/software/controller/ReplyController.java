package com.software.controller;

import com.software.dto.ReplyCreateDto;
import com.software.result.Result;
import com.software.service.ReplyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
