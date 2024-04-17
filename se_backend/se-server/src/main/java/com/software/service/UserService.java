package com.software.service;

import com.software.dto.UserEmailLoginDTO;
import com.software.dto.UserLoginDTO;
import com.software.entity.User;

/**
 * @author
 * @Description：
 * @date
 */
public interface UserService {
    User login(UserLoginDTO userLoginDTO);

    User eLogin(UserEmailLoginDTO userEmailLoginDTO);
}
