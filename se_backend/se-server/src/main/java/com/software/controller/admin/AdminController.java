package com.software.controller.admin;

import com.software.dto.AdminDTO;
import com.software.dto.TeacherDTO;
import com.software.result.Result;
import com.software.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author
 * @Description：
 * @date
 */
@Tag(name = "管理员接口")
@RestController
@RequestMapping("/api/admin")
@Slf4j
public class AdminController {
    @Autowired
    private AdminService adminService;
    @PostMapping("/addAdmin")
    @Operation(summary = "添加管理员")
    public Result addAdmin(@RequestBody AdminDTO adminDTO) {
        adminService.addAdmin(adminDTO);
        return Result.success();
    }

    @PostMapping("/addTeacher")
    @Operation(summary = "添加管理员")
    public Result addTeacher(@RequestBody TeacherDTO teacherDTO) {
        adminService.addTeacher(teacherDTO);
        return Result.success();
    }


}
