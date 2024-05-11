package com.software.service;

import com.software.dto.*;
import com.software.entity.User;

import java.util.List;

/**
 * @author
 * @Description：
 * @date
 */
public interface UserService {
    User login(UserLoginDTO userLoginDTO);

    User eLogin(UserEmailLoginDTO userEmailLoginDTO);

    void register(UserRegisterDTO userRegisterDTO);

    String getUsername(long id);

    void updateAvatar(String avatarUrl);

    void updateUserMessage(UserUpdateDTO userUpdateDTO);


    void resetPassword(ResetPasswordDto resetPasswordDto);

    User getTA(Long studentId,Long courseId);

    User checkTeacher(Long teacherId,Long courseId);

}
