package com.software.controller;

import com.software.constant.JwtClaimsConstant;
import com.software.dto.UserEmailDTO;
import com.software.dto.UserEmailLoginDTO;
import com.software.dto.UserLoginDTO;
import com.software.entity.User;
import com.software.properties.JwtProperties;
import com.software.result.Result;
import com.software.service.UserService;
import com.software.service.impl.EmailUtil;
import com.software.utils.JwtUtil;
import com.software.vo.LoginUserVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author
 * @Description：
 * @date
 */
@Tag(name = "用户相关接口")
@RestController
@RequestMapping("/api/user")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtProperties jwtProperties;

    @Autowired
    private EmailUtil emailUtil;

    @Autowired
    private RedisTemplate redisTemplate;

    @PostMapping("/login")
    @Operation(summary = "用户登录")
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
    @Operation(summary = "用户邮箱登录")
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
    @PostMapping("/sendmail")
    public Result sendMsg(@RequestBody UserEmailDTO userEmailDTO){
        String code = String.valueOf((int)((Math.random() * 9 + 1) * Math.pow(10,5)));
        redisTemplate.opsForValue().set(userEmailDTO.getEmail(), code,3, TimeUnit.MINUTES);
        try{
            emailUtil.sendSimpleMail(userEmailDTO.getEmail(),"主题：验证码","内容："+code+"有效时间3分钟（非本人操作请忽略）");
        }catch (Exception e){
            log.error(e.getMessage());
        }


        return Result.success();
    }


}
