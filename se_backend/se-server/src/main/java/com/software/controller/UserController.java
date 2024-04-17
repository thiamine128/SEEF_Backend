package com.software.controller;

import com.software.constant.JwtClaimsConstant;
import com.software.dto.UserEmailLoginDTO;
import com.software.dto.UserLoginDTO;
import com.software.entity.User;
import com.software.properties.JwtProperties;
import com.software.result.Result;
import com.software.service.UserService;
import com.software.utils.JwtUtil;
import com.software.vo.LoginUserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author
 * @Description：
 * @date
 */
@RestController
@RequestMapping("/user/user")
@Api(tags = "用户相关接口")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtProperties jwtProperties;

    @PostMapping("/login")
    @ApiOperation(value = "用户登录")
    public Result<LoginUserVO> login(@RequestBody UserLoginDTO userLoginDTO) {
        log.info("员工登录：{}", userLoginDTO);

        User user = userService.login(userLoginDTO);

        //登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.USER_ID, user.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims);

       LoginUserVO loginUserVO = LoginUserVO.builder()
                .id(user .getId())
                .userAccount(user.getUserAccount())
                .userName(user.getUserName())
                .userAvatar(user.getUserAvatar())
                .userProfile(user.getUserProfile())
                .userRole(user.getUserRole())
                .email(user.getEmail())
                .token(token)
                .build();

        return Result.success(loginUserVO);
    }


    @PostMapping("/eLogin")
    @ApiOperation(value = "用户邮箱登录")
    public Result<LoginUserVO> eLogin(@RequestBody  UserEmailLoginDTO userEmailLoginDTO) {
        log.info("用户邮箱登录：{}", userEmailLoginDTO);

        User user = userService.eLogin(userEmailLoginDTO);

        //登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.USER_ID, user.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims);

        LoginUserVO loginUserVO = LoginUserVO.builder()
                .id(user .getId())
                .userAccount(user.getUserAccount())
                .userName(user.getUserName())
                .userAvatar(user.getUserAvatar())
                .userProfile(user.getUserProfile())
                .userRole(user.getUserRole())
                .email(user.getEmail())
                .token(token)
                .build();

        return Result.success(loginUserVO);
    }


}
