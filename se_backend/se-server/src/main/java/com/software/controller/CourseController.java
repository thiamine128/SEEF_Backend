package com.software.controller;

import com.alibaba.druid.util.StringUtils;
import com.software.annotation.AuthCheck;
import com.software.config.OssConfiguration;
import com.software.constant.JwtClaimsConstant;
import com.software.constant.MessageConstant;
import com.software.constant.PasswordConstant;
import com.software.constant.RoleConstant;
import com.software.dto.*;
import com.software.entity.Course;
import com.software.entity.CourseClass;
import com.software.entity.User;
import com.software.exception.InvalidParameterException;
import com.software.exception.InvalidUserException;
import com.software.exception.PermissionDeniedException;
import com.software.result.PageResult;
import com.software.result.Result;
import com.software.service.CourseService;
import com.software.service.UserService;
import com.software.utils.AliOssUtil;
import com.software.utils.BaseContext;
import com.software.vo.CourseClassVO;
import com.software.vo.CourseVO;
import com.software.vo.OSSPostSignatureVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.software.controller.admin.AdminController.isRowEmpty;

@Tag(name = "课程接口")
@RestController
@RequestMapping("/api/course")
@Slf4j
public class CourseController {
    @Autowired
    private CourseService courseService;
    @Autowired
    private UserService userService;
    @Autowired
    private AliOssUtil aliOssUtil;

    @PostMapping("/create")
    @Operation(summary = "创建课程")
    @AuthCheck(mustRole = {RoleConstant.TEACHER,RoleConstant.ADMIN})
    public Result createCourse(@RequestBody CourseCreateDto courseCreateDto) {
        Map<String,Object> currentUser = BaseContext.getCurrentUser();
        String role = currentUser.get(JwtClaimsConstant.USER_ROLE).toString();
       // if (!role.equals(RoleConstant.TEACHER)) throw new PermissionDeniedException(MessageConstant.PERMISSION_DENIED);
        if(courseCreateDto.getName()==null||courseCreateDto.getName().isBlank()||courseCreateDto.getEvaluation().isBlank())
            throw new InvalidParameterException(MessageConstant.PARAMETER_BLANK);
        return Result.success(courseService.createCourse(courseCreateDto));
    }

    @PostMapping("/updateCourse")
    @Operation(summary = "更新课程信息")
    public Result updateCourse(@RequestBody CourseUpdateDto courseUpdateDto) {
        Map<String,Object> currentUser = BaseContext.getCurrentUser();
        String role = currentUser.get(JwtClaimsConstant.USER_ROLE).toString();
        if (!role.equals(RoleConstant.TEACHER)) throw new PermissionDeniedException(MessageConstant.PERMISSION_DENIED);
        if (!courseService.hasPermission(courseUpdateDto.getId())) throw new PermissionDeniedException(MessageConstant.PERMISSION_DENIED);
        courseService.updateCourse(courseUpdateDto);
        return Result.success();
    }

    @PostMapping("/updateClass")
    @Operation(summary = "更新教学班信息")
    public Result updateClass(@RequestBody ClassUpdateDto classUpdateDto) {
        Map<String,Object> currentUser = BaseContext.getCurrentUser();
        String role = currentUser.get(JwtClaimsConstant.USER_ROLE).toString();
        if (!role.equals(RoleConstant.TEACHER)) throw new PermissionDeniedException(MessageConstant.PERMISSION_DENIED);
        if (!courseService.checkClassPermission(classUpdateDto.getId())) throw new PermissionDeniedException(MessageConstant.PERMISSION_DENIED);
        courseService.updateClass(classUpdateDto);
        return Result.success();
    }

    @PostMapping("/addClass")
    @Operation(summary = "添加教学班")
    public Result<CourseClass> addClass(@RequestBody ClassCreateDto classCreateDto) {
        Map<String,Object> currentUser = BaseContext.getCurrentUser();
        String role = currentUser.get(JwtClaimsConstant.USER_ROLE).toString();
        if(classCreateDto.getName().isBlank()||classCreateDto.getLocation().isBlank()||classCreateDto.getTime().isBlank()) throw new InvalidParameterException(MessageConstant.PARAMETER_BLANK);
        if (!role.equals(RoleConstant.TEACHER)) throw new PermissionDeniedException(MessageConstant.PERMISSION_DENIED);
        if (!courseService.hasPermission(classCreateDto.getCourseId())) throw new PermissionDeniedException(MessageConstant.PERMISSION_DENIED);
        return Result.success(courseService.addClass(classCreateDto));
    }

    @GetMapping("/listClass")
    @Operation(summary = "查询课程教学班")
    public Result<List<CourseClassVO>> pagedList(@ParameterObject ClassQueryDto classQueryDto) {
        return Result.success(courseService.getClasses(classQueryDto));
    }

    @GetMapping("/listTeacherClass")
    @Operation(summary = "获取教师的教学班")
    public Result<List<CourseClassVO>> teacherClass(@RequestParam Long teacherId){
        List<CourseClassVO> result = courseService.getTeacherClass(teacherId);
        return Result.success(result);
    }

    @GetMapping("/list")
    @Operation(summary = "根据名字查询课程")
    public Result<PageResult> pagedList(@ParameterObject CoursePageQueryDto coursePageQueryDto) {
        PageResult pageResult = courseService.pageQuery(coursePageQueryDto);//后绪步骤定义
        return Result.success(pageResult);
    }
    @GetMapping("/getCourseById")
    @Operation(summary = "根据id查询课程")
    public Result<CourseVO> getCourseById(@RequestParam Long id) {
        Course course = courseService.getCourseById(id);
        if(course == null)
            return Result.success(null);
        return Result.success(CourseVO.fromCourse(course, courseService.hasPermission(id)));
    }

    @GetMapping("/teachers")
    @Operation(summary = "查询课程组")
    public Result<List<Long>> getTeachers(@RequestParam Long courseId) {
        return Result.success(courseService.getTeachers(courseId));
    }

    @PostMapping("/addTeacher")
    @Operation(summary = "添加教师到课程组")
    @AuthCheck(mustRole = {RoleConstant.ADMIN,RoleConstant.TEACHER})
    public Result addTeacher(@RequestParam String teacherAccount, @RequestParam Long courseId) {
        User u = userService.getByACCount(teacherAccount);
        Long teacherId = u.getId();
        if (!courseService.hasPermission(courseId)) throw new PermissionDeniedException(MessageConstant.PERMISSION_DENIED);
        if (!userService.isTeacher(teacherId)) throw new InvalidUserException(MessageConstant.INVALID_USER);
        courseService.addTeacherToCourse(teacherId, courseId);
        return Result.success();
    }

    @PostMapping("/addTeacherToClass")
    @Operation(summary = "分配教师到教学班")
    @AuthCheck(mustRole = {RoleConstant.ADMIN,RoleConstant.TEACHER})
    public Result addTeacherToClass(@RequestParam String teacherAccount, @RequestParam Long classId) {
        Long courseId = courseService.getCourseByClass(classId);
        User u = userService.getByACCount(teacherAccount);
        Long teacherId = u.getId();
        if (!courseService.hasPermission(courseId)) throw new PermissionDeniedException(MessageConstant.PERMISSION_DENIED);
        if (!userService.isTeacher(teacherId)) throw new InvalidUserException(MessageConstant.INVALID_USER);
        courseService.addTeacherToClass(teacherId, classId);
        return Result.success();
    }

    @DeleteMapping("/deleteTeacherFromClass")
    @Operation(summary = "取消教学班教师分配")
    @AuthCheck(mustRole = {RoleConstant.ADMIN,RoleConstant.TEACHER})
    public Result deleteTeacherFromClass(@RequestParam String teacherAccount, @RequestParam Long classId) {
        User u = userService.getByACCount(teacherAccount);
        Long teacherId = u.getId();
        Long courseId = courseService.getCourseByClass(classId);
        if (!courseService.hasPermission(courseId)) throw new PermissionDeniedException(MessageConstant.PERMISSION_DENIED);
        courseService.removeTeacherFromClass(teacherId, classId);
        return Result.success();
    }

    @GetMapping("/getTeachersInClass")
    @Operation(summary = "查询教学班教师")
    public Result<List<Long>> getTeachersInClass(@RequestParam Long classId) {
        return Result.success(courseService.getTeachersInClass(classId));
    }

    @PostMapping("/requestJoinClass")
    @Operation(summary = "向老师申请加入课程")
    @AuthCheck(mustRole = {RoleConstant.STUDENT})
    public Result requestJoinClass(@RequestBody RequestJoinClassDto requestJoinClassDto) {
        Long id = Long.parseLong(BaseContext.getCurrentUser().get(JwtClaimsConstant.USER_ID).toString());
        courseService.requestJoinClass(id, requestJoinClassDto.getClassId());
        return Result.success();
    }

    @PostMapping("/pendJoinClassRequest")
    @Operation(summary = "批量处理加入课程请求")
    @AuthCheck(mustRole = {RoleConstant.ADMIN,RoleConstant.TEACHER})
    public Result pendJoinClassRequest(@RequestBody JoinClassRequestPendDto joinClassRequestPendDto) {
        for (String id : joinClassRequestPendDto.getIds()) {
            courseService.pendJoinClassRequest(id, joinClassRequestPendDto.getState());
        }
        return Result.success();
    }

    @GetMapping("/getJoinClassRequests")
    @Operation(summary = "获取加入课程请求列表")
    @AuthCheck(mustRole = {RoleConstant.ADMIN,RoleConstant.TEACHER})
    public Result<PageResult> getJoinClassRequests(@ParameterObject JoinClassRequestPageQueryDto joinClassRequestPageQueryDto) {
        return Result.success(courseService.listJoinClassRequest(joinClassRequestPageQueryDto));
    }

    @PostMapping("/addCourseCover")
    @Operation(summary = "添加课程封面")
    public Result<OSSPostSignatureVO> addCourseCover(@RequestParam Long CourseId) throws UnsupportedEncodingException {

        String objectName = "CourseCover/" + CourseId;
        AliOssUtil.PostSignature postSignature = aliOssUtil.generatePostSignature(objectName, System.currentTimeMillis() + OssConfiguration.EXPIRE_SEC * 1000, 52428800);
        OSSPostSignatureVO ossPostSignatureVO = OSSPostSignatureVO.builder()
                .accessKeyId(postSignature.getAccessKeyId())
                .objectName(postSignature.getObjectName())
                .encodedPolicy(postSignature.getEncodedPolicy())
                .postSignature(postSignature.getPostSignature())
                .host(postSignature.getHost()).build();
        courseService.updateCover(aliOssUtil.buildPathFromObjectName(objectName),CourseId);
        return Result.success(ossPostSignatureVO);
    }

    @GetMapping("/getClass")
    @Operation(summary = "获取教学班信息")
    public Result<CourseClassVO> getCourse(@RequestParam Long classId) {
        return Result.success(courseService.getClass(classId));
    }

    @GetMapping("/getMyClasses")
    @Operation(summary = "获取我的教学班")
    public Result<List<CourseClassVO>> getMyClasses() {
        Long id = Long.parseLong(BaseContext.getCurrentUser().get(JwtClaimsConstant.USER_ID).toString());
        return Result.success(courseService.getUserClasses(id));
    }

    @PostMapping("/addButchStudentToClass")
    @Operation(summary = "批量加入学生到课程中")
    public Result addStudents(MultipartFile file) throws IOException {
        if(file == null){
            return Result.error("File cannot be null");
        }
        InputStream is = file.getInputStream();
        String fileName=file.getOriginalFilename();
        Map<String,Object> currentUser = BaseContext.getCurrentUser();

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
        List<JoinClassDTO> studentList = new ArrayList<>();
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
                            //User teacher = new User();
                            JoinClassDTO student= new JoinClassDTO();
                            Row row = sheet.getRow(i);
                            //首行  提取注解
                            if (firstRow) {
                                if (row.getCell(0).getStringCellValue().equals("学号")
                                        && row.getCell(1).getStringCellValue().equals("课程名")
                                        && row.getCell(2).getStringCellValue().equals("班级名")
                                )
                                {log.info("success");}
                                else {
                                    log.info(row.getCell(1).getStringCellValue());
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
                                        return Result.error("导入失败(第" + theRow + "行,不能为空)");

                                    } else {
                                        if (!nameList.isEmpty() && nameList.size() > 0) {
                                            //判断是否重复
                                            if (nameList.contains(name)) {
                                                isThrow = true;
                                                return Result.error("导入失败(第" + theRow + "行,学号" + name + "有重复)");

                                            } else {
                                                nameList.add(name);
                                                User u =userService.getByACCount(name);
                                                if(u==null)
                                                  return  Result.error("导入失败(第" + theRow + "行,学生" + name + "不存在)");
                                                 student.setUserId(u.getId());
                                            }
                                        } else {
                                            nameList.add(name);
                                            User u =userService.getByACCount(name);
                                            if(u==null)
                                                Result.error("导入失败(第" + theRow + "行,学号" + name + "不存在)");
                                            student.setUserId(u.getId());
                                        }
                                    }
                                } else {
                                    log.info("2");
                                    isThrow = true;
                                    return Result.error("导入失败(第" + theRow + "行,姓名不能为空)");

                                }

                                if (row.getCell(1) != null) {
                                    row.getCell(1).setCellType(CellType.STRING);
                                    String course = row.getCell(1).getStringCellValue();
                                    if (StringUtils.isEmpty(course)) {
                                        isThrow = true;
                                        return Result.error("导入失败(第" + theRow + "行,课程不能为空)");
                                    } else {
                                            Course c = courseService.getCourseByName(course);
                                            if(c==null)
                                                return Result.error("导入失败(第" + theRow + "行,课程不存在)");
                                            student.setCourseId(c.getId());
                                    }
                                } else {
                                    isThrow = true;
                                    return Result.error("导入失败(第" + theRow + "行,课程不能为空)");
                                }

                                if (row.getCell(2) != null) {
                                    row.getCell(2).setCellType(CellType.STRING);
                                    String SClass = row.getCell(2).getStringCellValue();
                                    if (StringUtils.isEmpty(SClass)) {
                                        isThrow = true;
                                        return Result.error("导入失败(第" + theRow + "行,班级不能为空)");
                                    } else {
                                        Long classId = courseService.getClassIdByName(SClass);
                                        if(classId==null)
                                            return Result.error("导入失败(第" + theRow + "行,班级" + SClass + "不存在)");
                                        student.setClassId(classId);
                                    }
                                } else {
                                    isThrow = true;
                                    return Result.error("导入失败(第" + theRow + "行,班级不能为空)");
                                }
                                studentList.add(student);

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
        if( studentList.size()>500){
            return Result.error("表格过大");
        }
        log.info("SUCCESSFUL");
        courseService.addButchStudents(studentList);
        return Result.success();
    }

    @PostMapping
    @Operation(summary = "批量删除教学班中学生")
    public Result deleteButchStudents(List<DeleteStudentReqDTO> students){
        courseService.deleteBathcStudents(students);

        return  Result.success();
    }
}