package com.software.service.impl;

import com.software.constant.MessageConstant;
import com.software.constant.StatusConstant;
import com.software.dto.UserLoginDTO;
import com.software.entity.User;
import com.software.exception.AccountLockedException;
import com.software.exception.AccountNotFoundException;
import com.software.exception.PasswordErrorException;
import com.software.mapper.UserMapper;
import com.software.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Objects;

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
    @Override
    public User login(UserLoginDTO userLoginDTO) {
        String userAccount = userLoginDTO.getUserAccount();
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
        //password = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!password.equals(user.getUserPassword())) {
            //密码错误
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }

        if (user.getUserRole().equals(StatusConstant.DISABLE)) {
            //账号被锁定
            throw new AccountLockedException(MessageConstant.ACCOUNT_LOCKED);
        }

        //3、返回实体对象
        return user;
    }
}
