package com.software.service;

import com.software.dto.UserEmailLoginDTO;
import com.software.dto.UserLoginDTO;
import com.software.dto.UserRegisterDTO;
import com.software.dto.UserUpdateDTO;
import com.software.entity.User;

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
}
