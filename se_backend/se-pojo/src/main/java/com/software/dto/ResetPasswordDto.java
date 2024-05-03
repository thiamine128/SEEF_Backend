package com.software.dto;

import lombok.Data;

@Data
public class ResetPasswordDto {
    private String email;

    private String verificationCode;

    private String password;
}
