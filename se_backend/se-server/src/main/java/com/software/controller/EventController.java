package com.software.controller;

import com.software.constant.JwtClaimsConstant;
import com.software.result.Result;
import com.software.service.EventService;
import com.software.utils.BaseContext;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "事件接口")
@RestController
@RequestMapping("/api/event")
@Slf4j
public class EventController {
    @Autowired
    EventService eventService;

    @GetMapping("/pull")
    @Operation(summary = "拉取事件")
    public Result pullEvents() {
        Long id = Long.parseLong(BaseContext.getCurrentUser().get(JwtClaimsConstant.USER_ID).toString());
        return Result.success(eventService.pullEvents(id));
    }

    @GetMapping("/eventCount")
    @Operation(summary = "事件数量")
    public Result getCount() {
        Long id = Long.parseLong(BaseContext.getCurrentUser().get(JwtClaimsConstant.USER_ID).toString());
        return Result.success(eventService.getCnt(id));
    }
}
