package com.software.controller;

import com.software.result.Result;
import com.software.service.TopicService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "板块相关接口")
@RestController
@RequestMapping("/api/topic")
@Slf4j
public class TopicController {
    @Autowired
    private TopicService topicSerice;

    @PostMapping("/create")
    @Operation(summary = "创建板块")
    public Result createTopic(@RequestBody String name) {
        topicSerice.createTopic(name);
        return Result.success();
    }
}
