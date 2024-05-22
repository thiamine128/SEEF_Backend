package com.software.dto;

import lombok.Data;

/**
 * @author
 * @Description：
 * @date
 */
@Data
public class UserRegisterDTO {
    private String name;

    private String email;

    private String verificationCode;

    private String password;
}
