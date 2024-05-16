package com.software.controller;

import com.software.constant.JwtClaimsConstant;
import com.software.result.Result;
import com.software.service.SubscribeService;
import com.software.utils.BaseContext;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "关注接口")
@RestController
@RequestMapping("/api/event")
@Slf4j
public class SubscribeController {
    @Autowired
    SubscribeService subscribeService;

    @PostMapping("/subscribe")
    @Operation(summary = "关注")
    public Result subscribe(Long user) {
        Long id = Long.parseLong(BaseContext.getCurrentUser().get(JwtClaimsConstant.USER_ID).toString());
        subscribeService.subscribe(id, user);
        return Result.success();
    }

    @PostMapping("/unsubscribe")
    @Operation(summary = "取消关注")
    public Result unsubscribe(Long user) {
        Long id = Long.parseLong(BaseContext.getCurrentUser().get(JwtClaimsConstant.USER_ID).toString());
        subscribeService.unsubscribe(id, user);
        return Result.success();
    }

    @PostMapping("/subscribed")
    @Operation(summary = "关注列表")
    public Result subscribed() {
        Long id = Long.parseLong(BaseContext.getCurrentUser().get(JwtClaimsConstant.USER_ID).toString());
        return Result.success(subscribeService.getSubscribed(id));
    }

    @PostMapping("/subscribers")
    @Operation(summary = "粉丝列表")
    public Result subscribers() {
        Long id = Long.parseLong(BaseContext.getCurrentUser().get(JwtClaimsConstant.USER_ID).toString());
        return Result.success(subscribeService.getSubscribers(id));
    }
}
