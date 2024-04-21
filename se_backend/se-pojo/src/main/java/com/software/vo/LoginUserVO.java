package com.software.vo;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * 已登录用户视图（脱敏）
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 **/
@Data
@Builder
public class LoginUserVO implements Serializable {

    /**
     * 用户 id
     */
    private Long id;
    /**
     * 用户账号
     */
    private String name;
    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 用户简介
     */
    private String profile;

    /**
     * 用户角色：user/admin/ban
     */
    private String role;

    /**
     * 用户昵称
     */
    private String email;

    private String token;

    private static final long serialVersionUID = 1L;
}