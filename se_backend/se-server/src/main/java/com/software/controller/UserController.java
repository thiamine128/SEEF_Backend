package com.software.controller;

import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.PolicyConditions;
import com.software.config.OssConfiguration;
import com.software.config.WebConfiguration;
import com.software.constant.JwtClaimsConstant;
import com.software.dto.UserEmailDTO;
import com.software.dto.UserEmailLoginDTO;
import com.software.dto.UserLoginDTO;
import com.software.dto.UserRegisterDTO;
import com.software.entity.User;
import com.software.exception.IncorrectFileFormatException;
import com.software.properties.JwtProperties;
import com.software.result.Result;
import com.software.service.UserService;
import com.software.service.impl.EmailUtil;
import com.software.utils.AliOssUtil;
import com.software.utils.BaseContext;
import com.software.utils.JwtUtil;
import com.software.vo.LoginUserVO;
import com.software.vo.OSSPostSignatureVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
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
    @Autowired
    private AliOssUtil aliOssUtil;

    @PostMapping("/login")
    @Operation(summary = "用户登录")
    public Result<LoginUserVO> login(@RequestBody UserLoginDTO userLoginDTO) {
        log.info("用户登录：{}", userLoginDTO);

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
                .name(user.getName())
                .nickname(user.getNickname())
                .avatar(user.getAvatar())
                .profile(user.getProfile())
                .role(user.getRole())
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
                .name(user.getName())
                .nickname(user.getNickname())
                .avatar(user.getAvatar())
                .profile(user.getProfile())
                .role(user.getRole())
                .email(user.getEmail())
                .token(token)
                .build();

        return Result.success(loginUserVO);
    }
    @PostMapping("/sendmail")
    @Operation(summary = "请求验证码")
    public Result SendMsg(@RequestBody UserEmailDTO userEmailDTO){
        String code = String.valueOf((int)((Math.random() * 9 + 1) * Math.pow(10,5)));
        if (redisTemplate.hasKey(userEmailDTO.getEmail())) {
            long remain = redisTemplate.getExpire(userEmailDTO.getEmail(), TimeUnit.SECONDS);
            if (remain > WebConfiguration.VALIDATION_CODE_RESEND_SEC) {
                return Result.error("请在" + (remain - WebConfiguration.VALIDATION_CODE_RESEND_SEC) + "秒后再次请求发送验证码");
            }
        }
        redisTemplate.opsForValue().set(userEmailDTO.getEmail(), code, WebConfiguration.VALIDATION_EXPIRE_SEC, TimeUnit.SECONDS);
        try{
            emailUtil.sendSimpleMail(userEmailDTO.getEmail(),"主题：验证码","内容："+code+"有效时间3分钟（非本人操作请忽略）");
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return Result.success();
    }
    @PostMapping("/register")
    @Operation(summary = "注册")
    public Result Register(@RequestBody UserRegisterDTO userRegisterDTO){

        userService.register(userRegisterDTO);

        return Result.success();
    }

    @PostMapping("/requestUploadAvatar")
    @Operation(summary = "请求上传头像")
    public Result requestUploadAvatar() throws UnsupportedEncodingException {
        long id = BaseContext.getCurrentId();
        String username = userService.getUsername(id);
        String objectName = "avatar/" + username;
        AliOssUtil.PostSignature postSignature = aliOssUtil.generatePostSignature(objectName, System.currentTimeMillis() + OssConfiguration.EXPIRE_SEC * 1000, 52428800);
        OSSPostSignatureVO ossPostSignatureVO = OSSPostSignatureVO.builder()
                .accessKeyId(postSignature.getAccessKeyId())
                .objectName(postSignature.getObjectName())
                .encodedPolicy(postSignature.getEncodedPolicy())
                .postSignature(postSignature.getPostSignature())
                .host(postSignature.getHost()).build();
        userService.updateAvatar(aliOssUtil.buildPathFromObjectName(objectName));
        return Result.success(ossPostSignatureVO);
    }
}
