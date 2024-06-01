package com.software.controller.admin;

import com.alibaba.druid.util.StringUtils;
import com.software.annotation.AuthCheck;
import com.software.constant.JwtClaimsConstant;
import com.software.constant.MessageConstant;
import com.software.constant.PasswordConstant;
import com.software.constant.RoleConstant;
import com.software.dto.AdminDTO;
import com.software.dto.TeacherDTO;
import com.software.entity.User;
import com.software.exception.PermissionDeniedException;
import com.software.result.Result;
import com.software.service.AdminService;
import com.software.service.UserService;
import com.software.utils.BaseContext;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    @AuthCheck(mustRole = {RoleConstant.ADMIN})
    public Result addAdmin(@RequestBody AdminDTO adminDTO) {
        //Map<String,Object> currentUser = BaseContext.getCurrentUser();
        //String role = currentUser.get(JwtClaimsConstant.USER_ROLE).toString();
        //if (!role.equals(RoleConstant.ADMIN)) throw new PermissionDeniedException(MessageConstant.PERMISSION_DENIED);
        adminService.addAdmin(adminDTO);
        return Result.success();
    }

    @PostMapping("/addTeacher")
    @Operation(summary = "添加教师")
    @AuthCheck(mustRole = {RoleConstant.ADMIN})
    public Result addTeacher(@RequestBody TeacherDTO teacherDTO) {
//        Map<String,Object> currentUser = BaseContext.getCurrentUser();
//        String role = currentUser.get(JwtClaimsConstant.USER_ROLE).toString();
//        if (!role.equals(RoleConstant.ADMIN)) throw new PermissionDeniedException(MessageConstant.PERMISSION_DENIED);
        adminService.addTeacher(teacherDTO);
        return Result.success();
    }

    @PostMapping("/addButchTeacher")
    @AuthCheck(mustRole = {RoleConstant.ADMIN})
    @Operation(summary = "批量添加老师账号")
    public Result addButchTeacher(MultipartFile file) throws IOException {
        if(file == null){
            return Result.error("File cannot be null");
        }
        InputStream is = file.getInputStream();
        String fileName=file.getOriginalFilename();
        Map<String,Object> currentUser = BaseContext.getCurrentUser();
        String role = currentUser.get(JwtClaimsConstant.USER_ROLE).toString();
        if (!role.equals(RoleConstant.ADMIN)) throw new PermissionDeniedException(MessageConstant.PERMISSION_DENIED);
        boolean notNull = false;
        if (!fileName.matches("^.+\\.(?i)(xls)$") && !fileName.matches("^.+\\.(?i)(xlsx)$")) {
            return Result.error("文件格式不正确");
        }
        Workbook wb = null;
        if (fileName.matches("^.+\\.(?i)(xlsx)$")) {
            //xlsx格式
            wb = new XSSFWorkbook(is);
        } else {
            //xls格式
            wb = new HSSFWorkbook(is);
        }
        List<User> teacherList = new ArrayList<>();
        if (wb != null) {
            //默认读取第一个sheet
            Sheet sheet = wb.getSheetAt(0);
            if (sheet != null) {
                boolean firstRow = true;
                boolean isThrow = false;
                //判断是否重复
                List<String> emailList=new ArrayList<>();
                List<String> nameList=new ArrayList<>();
                try {
                    if (sheet.getLastRowNum() > 0) {
                        for (int i = sheet.getFirstRowNum(); i <= sheet.getLastRowNum(); i++) {
                            //循环行
                            User teacher = new User();
                            teacher.setRole(RoleConstant.TEACHER);
                            Row row = sheet.getRow(i);
                            //首行  提取注解
                            if (firstRow) {
                                if (row.getCell(0).getStringCellValue().equals("工号")
                                        && row.getCell(1).getStringCellValue().equals("邮箱")
                                       )
                                {log.info("success");} else {
                                    return Result.error("格式不正确，请下载模板进行参考");
                                }
                                firstRow = false;
                                log.info("SUCCESSFUL");
                            } else {
                                Cell cell = row.getCell(0);
                                log.info("11SUCCESSFUL");
                                //忽略空白行
                                if (row == null || isRowEmpty(row)) {
                                    log.info("3");
                                    continue;
                                }
                                log.info("11SUCCESSFUL");
                                int theRow = i + 1;
                                log.info("value:{}",theRow);
                                if (row.getCell(0) != null) {
                                    log.info("1");
                                    row.getCell(0).setCellType(CellType.STRING);

                                    String name = row.getCell(0).getStringCellValue();
                                    if (StringUtils.isEmpty(name)) {
                                        isThrow = true;
                                        return Result.error("导入失败(第" + theRow + "行,姓名不能为空)");

                                    } else {
                                        if (!nameList.isEmpty() && nameList.size() > 0) {
                                            //判断是否重复
                                            if (nameList.contains(name)) {
                                                isThrow = true;
                                                return Result.error("导入失败(第" + theRow + "行,name" + name + "有重复)");

                                            } else {
                                                nameList.add(name);
                                                teacher.setName(name);
                                            }
                                        } else {
                                            nameList.add(name);
                                            teacher.setName(name);
                                        }
                                    }
                                } else {
                                    log.info("2");
                                    isThrow = true;
                                    return Result.error("导入失败(第" + theRow + "行,姓名不能为空)");

                                }

                                if (row.getCell(1) != null) {
                                    row.getCell(1).setCellType(CellType.STRING);
                                    String email = row.getCell(1).getStringCellValue();
                                    if (StringUtils.isEmpty(email)) {
                                        isThrow = true;
                                        return Result.error("导入失败(第" + theRow + "行,邮箱不能为空)");
                                    } else {
                                        if (!emailList.isEmpty() && emailList.size() > 0) {
                                            //判断是否重复
                                            if (emailList.contains(email)) {
                                                isThrow = true;
                                                return Result.error("导入失败(第" + theRow + "行,email" + email + "有重复)");

                                            } else {
                                                emailList.add(email);
                                                teacher.setEmail(email);
                                            }
                                        } else {
                                            emailList.add(email);
                                            teacher.setEmail(email);
                                        }
                                    }
                                } else {
                                    isThrow = true;
                                    return Result.error("导入失败(第" + theRow + "行,邮箱不能为空)");
                                }
                                teacher.setPassword(DigestUtils.md5DigestAsHex(PasswordConstant.DEFAULT_PASSWORD.getBytes()));
                                teacherList.add(teacher);
                            }
                            if (isThrow) {
                                break;
                            }
                        }
                    } else {
                        isThrow = true;
                        return Result.error("导入失败,数据为空");
                    }

                } catch (Exception e) {
                    log.info(e.getMessage());
                    throw new RuntimeException(e);
                }
            }
        }
        if(teacherList.size()>500){
            return Result.error("表格过大");
        }
        log.info("SUCCESSFUL");
        adminService.addButchUser(teacherList);
        return Result.success();
    }


    @PostMapping("/addButchStudent")
    @AuthCheck(mustRole = {RoleConstant.ADMIN})
    @Operation(summary = "批量添加学生账号")
    public Result addButchStudent(MultipartFile file) throws IOException {
        if(file == null){
            return Result.error("请上传文件");
        }
        InputStream is = file.getInputStream();
        String fileName=file.getOriginalFilename();
        Map<String,Object> currentUser = BaseContext.getCurrentUser();
        String role = currentUser.get(JwtClaimsConstant.USER_ROLE).toString();
        if (!role.equals(RoleConstant.ADMIN)) throw new PermissionDeniedException(MessageConstant.PERMISSION_DENIED);
        boolean notNull = false;
        if (!fileName.matches("^.+\\.(?i)(xls)$") && !fileName.matches("^.+\\.(?i)(xlsx)$")) {
            return Result.error("文件格式不正确");
        }
        Workbook wb = null;
        if (fileName.matches("^.+\\.(?i)(xlsx)$")) {
            //xlsx格式
            wb = new XSSFWorkbook(is);
        } else {
            //xls格式
            wb = new HSSFWorkbook(is);
        }
        List<User> teacherList = new ArrayList<>();
        if (wb != null) {
            //默认读取第一个sheet
            Sheet sheet = wb.getSheetAt(0);
            if (sheet != null) {
                boolean firstRow = true;
                boolean isThrow = false;
                //判断是否重复
                List<String> emailList=new ArrayList<>();
                List<String> nameList=new ArrayList<>();
                try {
                    if (sheet.getLastRowNum() > 0) {
                        for (int i = sheet.getFirstRowNum(); i <= sheet.getLastRowNum(); i++) {
                            //循环行
                            User teacher = new User();
                            teacher.setRole(RoleConstant.STUDENT);
                            Row row = sheet.getRow(i);
                            //首行  提取注解
                            if (firstRow) {
                                if (row.getCell(0).getStringCellValue().equals("学号")
                                        && row.getCell(1).getStringCellValue().equals("邮箱")
                                )
                                {log.info("success");} else {
                                    return Result.error("格式不正确，请下载模板进行参考");
                                }
                                firstRow = false;
                                log.info("SUCCESSFUL");
                            } else {
                                Cell cell = row.getCell(0);
                                log.info("11SUCCESSFUL");
                                //忽略空白行
                                if (row == null || isRowEmpty(row)) {
                                    log.info("3");
                                    continue;
                                }
                                log.info("11SUCCESSFUL");
                                int theRow = i + 1;
                                log.info("value:{}",theRow);
                                if (row.getCell(0) != null) {
                                    log.info("1");
                                    row.getCell(0).setCellType(CellType.STRING);

                                    String name = row.getCell(0).getStringCellValue();
                                    if (StringUtils.isEmpty(name)) {
                                        isThrow = true;
                                        return Result.error("导入失败(第" + theRow + "行,姓名不能为空)");

                                    } else {
                                        if (!nameList.isEmpty() && nameList.size() > 0) {
                                            //判断是否重复
                                            if (nameList.contains(name)) {
                                                isThrow = true;
                                                return Result.error("导入失败(第" + theRow + "行,name" + name + "有重复)");

                                            } else {
                                                nameList.add(name);
                                                teacher.setName(name);
                                            }
                                        } else {
                                            nameList.add(name);
                                            teacher.setName(name);
                                        }
                                    }
                                } else {
                                    log.info("2");
                                    isThrow = true;
                                    return Result.error("导入失败(第" + theRow + "行,姓名不能为空)");

                                }

                                if (row.getCell(1) != null) {
                                    row.getCell(1).setCellType(CellType.STRING);
                                    String email = row.getCell(1).getStringCellValue();
                                    if (StringUtils.isEmpty(email)) {
                                        isThrow = true;
                                        return Result.error("导入失败(第" + theRow + "行,邮箱不能为空)");
                                    } else {
                                        if (!emailList.isEmpty() && emailList.size() > 0) {
                                            //判断是否重复
                                            if (emailList.contains(email)) {
                                                isThrow = true;
                                                return Result.error("导入失败(第" + theRow + "行,email" + email + "有重复)");

                                            } else {
                                                emailList.add(email);
                                                teacher.setEmail(email);
                                            }
                                        } else {
                                            emailList.add(email);
                                            teacher.setEmail(email);
                                        }
                                    }
                                } else {
                                    isThrow = true;
                                    return Result.error("导入失败(第" + theRow + "行,邮箱不能为空)");
                                }
                                teacher.setPassword(DigestUtils.md5DigestAsHex(PasswordConstant.DEFAULT_PASSWORD.getBytes()));
                                teacherList.add(teacher);
                            }
                            if (isThrow) {
                                break;
                            }
                        }
                    } else {
                        isThrow = true;
                        return Result.error("导入失败,数据为空");
                    }

                } catch (Exception e) {
                    log.info(e.getMessage());
                    throw new RuntimeException(e);
                }
            }
        }
        if(teacherList.size()>500){
            return Result.error("表格过大");
        }
        log.info("SUCCESSFUL");
        adminService.addButchUser(teacherList);
        return Result.success();
    }


    public static boolean isRowEmpty(Row row) {
        for (int c = row.getFirstCellNum(); c < row.getLastCellNum(); c++) {
            Cell cell = row.getCell(c);
            if (cell != null && cell.getCellType() != Cell.CELL_TYPE_BLANK)
                return false;
        }
        return true;
    }


}
