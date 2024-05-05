package com.software.controller;

import com.software.dto.TopicPageQueryDTO;
import com.software.result.PageResult;
import com.software.result.Result;
import com.software.service.TopicService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Tag(name = "板块相关接口")
@RestController
@RequestMapping("/api/topic")
@Slf4j
public class TopicController {
    @Autowired
    private TopicService topicService;

    @PostMapping("/create")
    @Operation(summary = "创建板块")
    public Result createTopic(@RequestBody String name) {
        topicService.createTopic(name);
        return Result.success();
    }

    @GetMapping("/pagedList")
    @Operation(summary = "板块分页查询")
    public Result<PageResult> getPagedList  (@RequestBody TopicPageQueryDTO topicPageQueryDTO){
        log.info("topic分页查询:{}", topicPageQueryDTO);
        PageResult pageResult = topicService.pageQuery(topicPageQueryDTO);//后绪步骤定义
        return Result.success(pageResult);

    }
}
