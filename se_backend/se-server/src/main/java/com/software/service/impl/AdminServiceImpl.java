package com.software.service.impl;

import com.software.constant.PasswordConstant;
import com.software.constant.RoleConstant;
import com.software.constant.UserConstant;
import com.software.dto.AdminDTO;
import com.software.dto.TeacherDTO;
import com.software.entity.User;
import com.software.mapper.AdminMapper;
import com.software.mapper.UserMapper;
import com.software.service.AdminService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.List;

/**
 * @author
 * @Description：
 * @date
 */
@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private UserMapper userMapper;
    @Override
    public void addAdmin(AdminDTO adminDTO) {
        User user = new User();
        BeanUtils.copyProperties(adminDTO,user);
        user.setPassword(DigestUtils.md5DigestAsHex(PasswordConstant.DEFAULT_PASSWORD.getBytes()));
        user.setRole(RoleConstant.ADMIN);
        user.setNickname(UserConstant.DEFAULT_NICKNAME);;
        userMapper.insert(user);
    }

    @Override
    public void addTeacher(TeacherDTO teacherDTO) {
        User user = new User();
        BeanUtils.copyProperties(teacherDTO,user);
        user.setPassword(DigestUtils.md5DigestAsHex(PasswordConstant.DEFAULT_PASSWORD.getBytes()));
        user.setRole(RoleConstant.TEACHER);
        user.setNickname(UserConstant.DEFAULT_NICKNAME);
        userMapper.insert(user);
    }
    @Transactional
    @Override
    public void addButchUser(List<User> userList) {
        userMapper.addButchUser(userList);
    }
}
