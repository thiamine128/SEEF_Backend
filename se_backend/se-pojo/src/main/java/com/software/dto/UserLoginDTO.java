package com.software.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class UserLoginDTO implements Serializable {


    private String userAccount;


    private String password;

}
