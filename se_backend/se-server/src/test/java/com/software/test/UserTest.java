package com.software.test;

import com.software.dto.UserEmailLoginDTO;
import com.software.dto.UserLoginDTO;
import com.software.dto.UserRegisterDTO;
import com.software.dto.UserUpdateDTO;
import com.software.entity.User;
import com.software.exception.AccountNotFoundException;
import com.software.exception.PasswordErrorException;
import com.software.service.EmailService;
import com.software.service.UserService;
import com.software.vo.UserProfileVO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
public class UserTest {
    @Autowired
    private UserService userService;
    @Test
    public void notNull() {
        assertThat(userService).isNotNull();
    }
    @Test
    public void successLogin() {
        UserLoginDTO userLoginDTO = new UserLoginDTO();
        userLoginDTO.setName("21377001");
        userLoginDTO.setPassword("123456");
        Assertions.assertThat(userService.login(userLoginDTO)).isNotNull();
    }
    @Test
    public void emailLogin() {
        UserEmailLoginDTO userLoginDTO = new UserEmailLoginDTO();
        userLoginDTO.setEmail("zhangsan@stu");
        userLoginDTO.setPassword("123456");
        Assertions.assertThat(userService.eLogin(userLoginDTO)).isNotNull();
    }
    @Test
    public void noSuchUser() {
        UserLoginDTO userLoginDTO = new UserLoginDTO();
        userLoginDTO.setName("213770011");
        userLoginDTO.setPassword("123456");
        assertThatThrownBy(() -> {
            userService.login(userLoginDTO);
        }).isInstanceOf(AccountNotFoundException.class);
    }
    @Test
    public void wrongPassword() {
        UserLoginDTO userLoginDTO = new UserLoginDTO();
        userLoginDTO.setName("21377001");
        userLoginDTO.setPassword("123456?");
        assertThatThrownBy(() -> {
            userService.login(userLoginDTO);
        }).isInstanceOf(PasswordErrorException.class);
    }

    @Test
    public void userProfile() {
        UserLoginDTO userLoginDTO = new UserLoginDTO();
        userLoginDTO.setName("21377001");
        userLoginDTO.setPassword("123456");
        User user = userService.login(userLoginDTO);

        assertThat(userService.getProfile(user.getId(), user.getId()).getName()).isEqualTo("21377001");
        assertThat(userService.getProfile(user.getId(), user.getId()).getId()).isEqualTo(user.getId());
    }

    @Test
    public void updateProfile() {
        UserUpdateDTO userUpdateDTO = new UserUpdateDTO();
        UserLoginDTO userLoginDTO = new UserLoginDTO();
        userLoginDTO.setName("21377001");
        userLoginDTO.setPassword("123456");
        User user = userService.login(userLoginDTO);
        userUpdateDTO.setId(user.getId());
        String str = UUID.randomUUID().toString();
        userUpdateDTO.setProfile(str);
        userService.updateUserMessage(userUpdateDTO);
        UserProfileVO profile = userService.getProfile(user.getId(), user.getId());
        assertThat(profile.getProfile()).isEqualTo(str);
    }
}
