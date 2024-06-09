package com.software.controller;

import com.alibaba.druid.util.StringUtils;
import com.software.annotation.AuthCheck;
import com.software.constant.JwtClaimsConstant;
import com.software.constant.MessageConstant;
import com.software.constant.PasswordConstant;
import com.software.constant.RoleConstant;
import com.software.dto.AddBatchTADTO;
import com.software.dto.AddTADTO;
import com.software.dto.DeleteTADTO;
import com.software.entity.Course;
import com.software.entity.CourseClass;
import com.software.entity.User;
import com.software.exception.PermissionDeniedException;
import com.software.result.Result;
import com.software.service.CourseService;
import com.software.service.SubscribeService;
import com.software.service.TAService;
import com.software.service.UserService;
import com.software.service.impl.CourseServiceImpl;
import com.software.utils.BaseContext;
import com.software.vo.CourseClassVO;
import com.software.vo.UserProfileVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.software.controller.admin.AdminController.isRowEmpty;

/**
 * @author
 * @Description：
 * @date
 */
@Tag(name = "助教接口")
@RestController
@RequestMapping("/api/TA")
@Slf4j
public class TAController {
    @Autowired
    private TAService taService;
    @Autowired
    private SubscribeService subscribeService;
    @Autowired
    private UserService userService;
    @Autowired
    private CourseService courseService;

    @PostMapping("/addTA")
    @Operation(summary = "添加助教")
    @AuthCheck(mustRole = {RoleConstant.TEACHER})
    public Result addTA(@RequestParam String studentAccount, @RequestParam Long classId, @RequestParam Long courseId){
        //User u = userService.getUserByName(account);
        taService.addTA(studentAccount, classId, courseId);
        return Result.success();
    }
    @PostMapping("/addButchTA")
    @Operation(summary = "批量添加助教")
    @Transactional
    @AuthCheck(mustRole = {RoleConstant.TEACHER})
    public Result addButchTA(@RequestBody List<AddTADTO> addTADTOs){
        for(AddTADTO student:addTADTOs)
            taService.addTA(student.getStudentAccount(), student.getClassId(), student.getCourseId());
        return Result.success();
    }
    @DeleteMapping("/deleteTA")
    @Operation(summary = "删除助教")
    @AuthCheck(mustRole = {RoleConstant.TEACHER})
    public Result deleteTA(@RequestParam Long studentId, @RequestParam Long classId, @RequestParam Long courseId){
        taService.deleteTA(studentId, classId, courseId);
        return Result.success();
    }
    @PostMapping("/deleteButchTA")
    @Operation(summary = "批量删除助教")
    @Transactional
    @AuthCheck(mustRole = {RoleConstant.TEACHER})
    public Result deleteButchTA(@RequestBody List<DeleteTADTO> deleteTADTOs){
        for(DeleteTADTO student:deleteTADTOs)
        taService.deleteTA(student.getStudentId(), student.getClassId(), student.getCourseId());
        return Result.success();
    }
    @GetMapping("/listCourseTA")
    @Operation(summary = "查询班级助教")
    public Result<List<UserProfileVO>> getCourseTA(@RequestParam Long courseId){
        Map<String,Object> currentUser = BaseContext.getCurrentUser();
        long id = (long) currentUser.get(JwtClaimsConstant.USER_ID);
        List<UserProfileVO> result = new ArrayList<UserProfileVO>();
        List<User> tas= taService.getCourseTA(courseId);
        for(User user : tas){
            result.add(UserProfileVO.fromUser(user,subscribeService.isSubscribed(user.getId(), id)));
        }
        return Result.success(result);
    }

    @GetMapping("/listMyClass")
    @Operation(summary = "助教管理的课程")
    public Result<List<Long>> getMyClass(@RequestParam(required = false) Long userId){
        Map<String,Object> currentUser = BaseContext.getCurrentUser();
        long id = (long) currentUser.get(JwtClaimsConstant.USER_ID);
        if(userId!=null)
            id = userId;
        List<Long> classIds = taService.getMyClass(id);
        return Result.success(classIds);
    }
    @GetMapping("/myClass")
    @Operation(summary = "是否是我管理的课程（助教）")
    public Result<Boolean> isMyClass(@RequestParam  Long classId){
        Map<String,Object> currentUser = BaseContext.getCurrentUser();
        long id = (long) currentUser.get(JwtClaimsConstant.USER_ID);
        List<Long> classIds = taService.getMyClass(id);
        if(!classIds.contains(classId))
            return Result.success(false);
        return Result.success(true);
    }
    @PostMapping("/addButchTAExcel")
    @Operation(summary = "批量添加助教（excel）")
    @Transactional
    @AuthCheck(mustRole = {RoleConstant.TEACHER})
    public Result addButchTAExcel(MultipartFile file) throws IOException {
        if(file == null){
            return Result.error("File cannot be null");
        }
        InputStream is = file.getInputStream();
        String fileName=file.getOriginalFilename();
        Map<String,Object> currentUser = BaseContext.getCurrentUser();
        String role = currentUser.get(JwtClaimsConstant.USER_ROLE).toString();
        //if (!role.equals(RoleConstant.ADMIN)) throw new PermissionDeniedException(MessageConstant.PERMISSION_DENIED);
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
        List<AddBatchTADTO> TAList = new ArrayList<>();
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
                            AddBatchTADTO TA = new AddBatchTADTO();
                            Row row = sheet.getRow(i);
                            //首行  提取注解
                            if (firstRow) {
                                if (!row.getCell(0).getStringCellValue().isBlank()&&!row.getCell(1).getStringCellValue().isBlank()&&!row.getCell(2).getStringCellValue().isBlank()
                                        &&row.getCell(0).getStringCellValue().equals("学号")
                                        && row.getCell(1).getStringCellValue().equals("课程")
                                        && row.getCell(2).getStringCellValue().equals("班级")
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

                                    String account = row.getCell(0).getStringCellValue();
                                    if (StringUtils.isEmpty(account)) {
                                        isThrow = true;
                                        return Result.error("导入失败(第" + theRow + "行,学号不能为空)");

                                    } else {
                                            nameList.add(account);
                                            User user = userService.getByACCount(account);
                                            if(user == null) {
                                                return Result.error("导入失败(第" + theRow + "行,学号" + account + "不存在)");
                                            }
                                            TA.setStudentId(user.getId());
                                    }
                                } else {
                                    log.info("2");
                                    isThrow = true;
                                    return Result.error("导入失败(第" + theRow + "行,学号不能为空)");

                                }

                                String course;
                                if (row.getCell(1) != null) {
                                    row.getCell(1).setCellType(CellType.STRING);
                                    course = row.getCell(1).getStringCellValue();
                                    if (StringUtils.isEmpty(course)) {
                                        isThrow = true;
                                        return Result.error("导入失败(第" + theRow + "行,课程不能为空)");
                                    } else {
                                        Course courseId = courseService.getCourseByName(course);
                                        if(courseId == null) {
                                            return Result.error("导入失败(第" + theRow + "行课程不存在)");
                                        }
                                        TA.setCourseId(courseId.getId());
                                    }
                                } else {
                                    isThrow = true;
                                    return Result.error("导入失败(第" + theRow + "行,课程不能为空)");
                                }
                                if (row.getCell(2) != null) {
                                    row.getCell(2).setCellType(CellType.STRING);
                                    String className = row.getCell(2).getStringCellValue();
                                    if (StringUtils.isEmpty(className)) {
                                        isThrow = true;
                                        return Result.error("导入失败(第" + theRow + "行,班级不能为空)");
                                    } else {
                                        Long classId = courseService.getClassIdByName(className, course);
                                        if(classId == null) {
                                            return Result.error("导入失败(第" + theRow + "行,班级不存在)");
                                        }
                                        TA.setClassId(classId);
                                    }
                                } else {
                                    isThrow = true;
                                    return Result.error("导入失败(第" + theRow + "行,班级不能为空)");
                                }
                                TAList.add(TA);
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
        if(TAList.size()>500){
            return Result.error("表格过大");
        }
        log.info("SUCCESSFUL");
        taService.addButchTA(TAList);
        return Result.success();

    }

}
