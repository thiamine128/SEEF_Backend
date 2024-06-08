package com.software.service;

import com.software.dto.AdminDTO;
import com.software.dto.TeacherDTO;
import com.software.entity.User;

import java.util.List;

/**
 * @author
 * @Description：
 * @date
 */
public interface AdminService {
    void addAdmin(AdminDTO adminDTO);

    void addTeacher(TeacherDTO teacherDTO);

    void addButchUser(List<User> userList);

    void deleteClass(Long classId);
}
