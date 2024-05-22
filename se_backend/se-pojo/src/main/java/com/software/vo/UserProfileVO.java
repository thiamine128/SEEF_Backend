package com.software.vo;

import com.software.entity.User;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class UserProfileVO {
    private Long id;
    private String name;
    private String email;
    private String nickname;
    private String avatar;
    private String profile;
    private String role;
    private Date createTime;
    private Long subscribers;
    private Boolean subscribed;

    public static UserProfileVO fromUser(User user, Boolean subscribed) {
        return new UserProfileVOBuilder()
                .profile(user.getProfile())
                .avatar(user.getAvatar())
                .name(user.getName())
                .subscribed(subscribed)
                .email(user.getEmail())
                .createTime(user.getCreateTime())
                .nickname(user.getNickname())
                .id(user.getId())
                .subscribers(user.getSubscribers())
                .role(user.getRole()).build();
    }
}
