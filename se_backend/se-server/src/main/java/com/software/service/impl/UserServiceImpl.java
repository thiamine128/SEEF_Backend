package com.software.service.impl;

import com.software.constant.*;
import com.software.dto.*;
import com.software.entity.Course;
import com.software.entity.TClass;
import com.software.entity.User;
import com.software.exception.*;
import com.software.mapper.UserMapper;
import com.software.service.SubscribeService;
import com.software.service.UserService;
import com.software.utils.BaseContext;
import com.software.vo.UserProfileVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

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
    @Autowired
    private SubscribeService subscribeService;

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
        String pattern = "^[a-zA-Z0-9]+$";
        user.setName(userRegisterDTO.getName());
        user.setPassword(DigestUtils.md5DigestAsHex(userRegisterDTO.getPassword().getBytes()));
        user.setEmail(userRegisterDTO.getEmail());
        user.setRole(RoleConstant.STUDENT);
        user.setNickname(userRegisterDTO.getNickName());
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
    public Long getTA(Long studentId, Long courseId) {
        return userMapper.getTA(studentId, courseId);
    }

    @Override
    public Long checkTeacher(Long teacherId, Long courseId) {return userMapper.checkTeacher(teacherId, courseId);}

    @Override
    public boolean isTeacher(Long id) {
        User user = userMapper.getByID(id);
        if (user == null) return false;
        return user.getRole().equals(RoleConstant.TEACHER);
    }

    @Override
    public List<Course> getCourses(List<Long> courseIds) {
        return userMapper.getCourses(courseIds);
    }

    @Override
    public List<Long> getCourseIds(String role, Long id) {
        if(role == RoleConstant.STUDENT)
            return userMapper.getTACourseIds(id);
        if(role == RoleConstant.TEACHER)
            return userMapper.getTeacherCourseIds(id);
        return null;
    }

    @Override
    public List<Long> getClassIds(Long id) {

        return userMapper.getClassIds(id);
    }

    @Override
    public List<TClass> getClasses(List<Long> classIds) {
        return userMapper.getClasses(classIds);
    }

    @Override
    public UserProfileVO getProfile(Long userId, Long id) {
        User user = userMapper.getByID(userId);
        if (user == null) throw new InvalidUserException(MessageConstant.INVALID_USER);

        return UserProfileVO.fromUser(user, subscribeService.isSubscribed(user.getId(), id));
    }

    @Override
    public List<User> getUserByName(String userName) {
        return userMapper.getUserByName(userName);
    }

    @Override
    public User getByACCount(String userAccount) {
        return userMapper.getByAccount(userAccount);
    }

    @Override
    public List<User> geUsersByIds(List<Long> ids) {
        return userMapper.getUserByIds(ids);
    }


    private void verifyCodeOrThrow(String email, String code) {
        if (Boolean.FALSE.equals(redisTemplate.hasKey(email)))
            throw new IncorrectVerificationCode(MessageConstant.INVALID_CODE);
        String correctCode = (String) redisTemplate.opsForValue().get(email);
        if (!code.equals(correctCode)) {
            throw new IncorrectVerificationCode(MessageConstant.INVALID_CODE);
        }
    }


}
