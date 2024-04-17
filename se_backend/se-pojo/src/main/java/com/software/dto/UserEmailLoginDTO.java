package com.software.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

/**
 * @author
 * @Description：
 * @date
 */
@Data
public class UserEmailLoginDTO {
    @NotNull
    @Email
    private String userEmail;
    @NotNull
    private String password;

}
