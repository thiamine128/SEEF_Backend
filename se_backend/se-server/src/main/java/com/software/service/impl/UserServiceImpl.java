package com.software.service.impl;

import com.software.constant.JwtClaimsConstant;
import com.software.constant.MessageConstant;
import com.software.constant.RoleConstant;
import com.software.constant.StatusConstant;
import com.software.dto.*;
import com.software.entity.User;
import com.software.exception.AccountLockedException;
import com.software.exception.AccountNotFoundException;
import com.software.exception.IncorrectVerificationCode;
import com.software.exception.PasswordErrorException;
import com.software.mapper.UserMapper;
import com.software.service.UserService;
import com.software.utils.BaseContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.List;
import java.util.Map;

/**
 * @author
 * @Description：
 * @date
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Qualifier("redisTemplate")
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public User login(UserLoginDTO userLoginDTO) {
        String userAccount = userLoginDTO.getName();
        String password = userLoginDTO.getPassword();

        //1、根据用户名查询数据库中的数据
        User user = userMapper.getByAccount(userAccount);

        //2、处理各种异常情况（用户名不存在、密码不对、账号被锁定）
        if (user == null) {
            //账号不存在
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        //密码比对
        // 进行md5加密，然后再进行比对
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!password.equals(user.getPassword())) {
            //密码错误
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }

        if (user.getRole().equals(StatusConstant.DISABLE)) {
            //账号被锁定
            throw new AccountLockedException(MessageConstant.ACCOUNT_LOCKED);
        }

        //3、返回实体对象
        return user;
    }

    @Override
    public User eLogin(UserEmailLoginDTO userEmailLoginDTO) {
        String userEmail = userEmailLoginDTO.getEmail();
        String password = userEmailLoginDTO.getPassword();

        //1、根据用户名查询数据库中的数据
        User user = userMapper.getByEmail(userEmail);

        //2、处理各种异常情况（用户名不存在、密码不对、账号被锁定）
        if (user == null) {
            //账号不存在
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        //密码比对
        // 进行md5加密，然后再进行比对
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!password.equals(user.getPassword())) {
            //密码错误
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }

        if (user.getRole().equals(StatusConstant.DISABLE)) {
            //账号被锁定
            throw new AccountLockedException(MessageConstant.ACCOUNT_LOCKED);
        }

        //3、返回实体对象
        return user;


    }

    @Override
    public void register(UserRegisterDTO userRegisterDTO) {
        verifyCodeOrThrow(userRegisterDTO.getEmail(), userRegisterDTO.getVerificationCode());
        User user = new User();
        user.setName(userRegisterDTO.getName());
        user.setPassword(DigestUtils.md5DigestAsHex(userRegisterDTO.getPassword().getBytes()));
        user.setEmail(userRegisterDTO.getEmail());
        user.setRole(RoleConstant.STUDENT);
        userMapper.insert(user);
        redisTemplate.delete(userRegisterDTO.getEmail());
    }

    @Override
    public String getUsername(long id) {
        return userMapper.getName(id);
    }

    @Override
    public void updateAvatar(String avatarUrl) {
        Map<String,Object> currentUser = BaseContext.getCurrentUser();
        Long id =(long) currentUser.get(JwtClaimsConstant.USER_ID);
        userMapper.updateAvatar(avatarUrl, id);
    }

    @Override
    public void updateUserMessage(UserUpdateDTO userUpdateDTO) {
        Map<String,Object> currentUser = BaseContext.getCurrentUser();
        Long id =(long) currentUser.get(JwtClaimsConstant.USER_ID);
        userUpdateDTO.setId(id);
        userMapper.update(userUpdateDTO);
    }

    @Override
    public void resetPassword(ResetPasswordDto resetPasswordDto) {
        User user = userMapper.getByEmail(resetPasswordDto.getEmail());
        if (user == null)
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        verifyCodeOrThrow(user.getEmail(), resetPasswordDto.getVerificationCode());
        userMapper.updatePassword(user.getEmail(), DigestUtils.md5DigestAsHex(resetPasswordDto.getPassword().getBytes()));
        redisTemplate.delete(user.getEmail());
    }

    @Override
    public User getTA(Long studentId, Long courseId) {
        return userMapper.getTA(studentId, courseId);
    }

    @Override
    public User checkTeacher(Long teacherId, Long courseId) {return userMapper.checkTeacher(teacherId, courseId);}


    private void verifyCodeOrThrow(String email, String code) {
        if (Boolean.FALSE.equals(redisTemplate.hasKey(email)))
            throw new IncorrectVerificationCode(MessageConstant.INVALID_CODE);
        String correctCode = (String) redisTemplate.opsForValue().get(email);
        if (!code.equals(correctCode)) {
            throw new IncorrectVerificationCode(MessageConstant.INVALID_CODE);
        }
    }


}
