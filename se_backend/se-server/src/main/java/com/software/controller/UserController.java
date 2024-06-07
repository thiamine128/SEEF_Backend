package com.software.controller;

import com.software.config.OssConfiguration;
import com.software.config.WebConfiguration;
import com.software.constant.JwtClaimsConstant;
import com.software.constant.MessageConstant;
import com.software.constant.RoleConstant;
import com.software.dto.*;
import com.software.entity.Course;
import com.software.entity.TClass;
import com.software.entity.User;
import com.software.exception.InvalidParameterException;
import com.software.properties.JwtProperties;
import com.software.result.Result;
import com.software.service.SubscribeService;
import com.software.service.TAService;
import com.software.service.UserService;
import com.software.service.impl.EmailUtil;
import com.software.utils.AliOssUtil;
import com.software.utils.BaseContext;
import com.software.utils.JwtUtil;
import com.software.vo.LoginUserVO;
import com.software.vo.OSSPostSignatureVO;
import com.software.vo.UserProfileVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

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

    @Autowired
    private  SubscribeService subscribeService;
    @Autowired
    private TAService taService;
    @PostMapping("/login")
    @Operation(summary = "用户登录")
    public Result<LoginUserVO> login(@RequestBody UserLoginDTO userLoginDTO) {
        log.info("用户登录：{}", userLoginDTO);

        User user = userService.login(userLoginDTO);

        //登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.USER_ID, user.getId());
        claims.put(JwtClaimsConstant.USER_ROLE,user.getRole());
        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims);
        Boolean isTA = false;
        List<Long> classIds = taService.getMyClass(user.getId());
        if(classIds.size()>0)
            isTA = true;
       LoginUserVO loginUserVO = LoginUserVO.builder()
                .id(user .getId())
                .name(user.getName())
                .nickname(user.getNickname())
                .avatar(user.getAvatar())
                .profile(user.getProfile())
                .role(user.getRole())
                .email(user.getEmail())
                .isTA(isTA)
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
        claims.put(JwtClaimsConstant.USER_ROLE,user.getRole());
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
    public Result sendMsg(@RequestBody UserEmailDTO userEmailDTO){
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
            return Result.error(MessageConstant.SEND_CODE_FAIL);
        }
        return Result.success();
    }
    @PostMapping("/register")
    @Operation(summary = "注册")
    public Result register(@RequestBody UserRegisterDTO userRegisterDTO){
        if(userRegisterDTO.getName().isBlank()||userRegisterDTO.getPassword().isBlank()||userRegisterDTO.getNickName().isBlank())throw new InvalidParameterException(MessageConstant.PARAMETER_BLANK);
        String name = userRegisterDTO.getName();
        String pattern = "^[a-zA-Z0-9]+$";
        if(!Pattern.matches(pattern,name))
            return Result.error("账号应只包含数字和英文");
        userService.register(userRegisterDTO);
        return Result.success();
    }

    @PostMapping("/resetPassword")
    @Operation(summary = "邮箱找回密码")
    public Result resetEmailPassword(@RequestBody ResetPasswordDto resetPasswordDto) {
        userService.resetPassword(resetPasswordDto);

        return Result.success();
    }

    @GetMapping()
    @Operation(summary = "获取资料")
    public Result<UserProfileVO> getProfile(@RequestParam Long userId) {
        Long id = Long.parseLong(BaseContext.getCurrentUser().get(JwtClaimsConstant.USER_ID).toString());
        return Result.success(userService.getProfile(userId, id));
    }

    @PostMapping("/requestUploadAvatar")
    @Operation(summary = "请求上传头像")
    public Result<OSSPostSignatureVO> requestUploadAvatar() throws UnsupportedEncodingException {
        Map<String,Object> currentUser = BaseContext.getCurrentUser();
        long id = (long) currentUser.get(JwtClaimsConstant.USER_ID);
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
    @PostMapping("/update")
    @Operation(summary = "更新用户昵称/简介")
    public Result updateUserMessage(@RequestBody UserUpdateDTO userUpdateDTO){
        userService.updateUserMessage(userUpdateDTO);
        return Result.success();
    }

    @GetMapping("/myCourse")
    public Result<List<Course>> getMyCourse(){
        Map<String,Object> currentUser = BaseContext.getCurrentUser();
        long id = (long) currentUser.get(JwtClaimsConstant.USER_ID);
        String role = currentUser.get(JwtClaimsConstant.USER_ROLE).toString();
        List<Long> courseIds = userService.getCourseIds(role,id);
        if(courseIds.isEmpty())
            return Result.success();
        List<Course> courses = userService.getCourses(courseIds);
        return Result.success(courses);
    }

    @GetMapping("/myClass")
    public Result<List<TClass>> getMyClass(){
        Map<String,Object> currentUser = BaseContext.getCurrentUser();
        long id = (long) currentUser.get(JwtClaimsConstant.USER_ID);
        List<Long> classIds = userService.getClassIds(id);
        List<TClass> classes = userService.getClasses(classIds);
        return Result.success(classes);
    }

    @GetMapping("/searchUser")
    @Operation(summary = "根据学/工号查询用户")
    public Result<List<UserProfileVO>> searchUser(@RequestParam String userName){
        List<User> users = userService.getUserByName(userName);
        List<UserProfileVO> result = new ArrayList<UserProfileVO>();
        Map<String,Object> currentUser = BaseContext.getCurrentUser();
        long id = (long) currentUser.get(JwtClaimsConstant.USER_ID);

        for(User user:users){
            result.add(UserProfileVO.fromUser(user,subscribeService.isSubscribed(user.getId(), id)));
        }
        return Result.success(result);
    }


}
