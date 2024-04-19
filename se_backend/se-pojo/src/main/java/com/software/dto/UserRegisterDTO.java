package com.software.dto;

import lombok.Data;

/**
 * @author
 * @Description：
 * @date
 */
@Data
public class UserRegisterDTO {
    private String userAccount;

    private String userEmail;

    private String verificationCode;

    private String userPassword;


}
