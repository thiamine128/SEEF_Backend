package com.software.service;

import com.software.dto.AdminDTO;
import com.software.dto.TeacherDTO;

/**
 * @author
 * @Description：
 * @date
 */
public interface AdminService {
    void addAdmin(AdminDTO adminDTO);

    void addTeacher(TeacherDTO teacherDTO);
}
