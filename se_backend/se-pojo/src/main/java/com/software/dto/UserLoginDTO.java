package com.software.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class UserLoginDTO implements Serializable {

    @NotNull
    private String userAccount;

    @NotNull
    private String password;

}
