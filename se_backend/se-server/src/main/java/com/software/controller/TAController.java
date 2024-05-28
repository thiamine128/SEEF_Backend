package com.software.controller;

import com.software.annotation.AuthCheck;
import com.software.constant.JwtClaimsConstant;
import com.software.constant.RoleConstant;
import com.software.dto.AddTADTO;
import com.software.dto.DeleteTADTO;
import com.software.entity.CourseClass;
import com.software.entity.User;
import com.software.result.Result;
import com.software.service.SubscribeService;
import com.software.service.TAService;
import com.software.utils.BaseContext;
import com.software.vo.CourseClassVO;
import com.software.vo.UserProfileVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    @PostMapping("/addTA")
    @Operation(summary = "添加助教")
    @AuthCheck(mustRole = {RoleConstant.TEACHER})
    public Result addTA(@RequestParam Long studentId, @RequestParam Long classId, @RequestParam Long courseId){
        taService.addTA(studentId, classId, courseId);
        return Result.success();
    }
    @PostMapping("/addButchTA")
    @Operation(summary = "批量添加助教")
    @AuthCheck(mustRole = {RoleConstant.TEACHER})
    public Result addButchTA(@RequestBody List<AddTADTO> addTADTOs){
        for(AddTADTO student:addTADTOs)
            taService.addTA(student.getStudentId(), student.getClassId(), student.getCourseId());
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
    @AuthCheck(mustRole = {RoleConstant.TEACHER})
    public Result deleteButchTA(@RequestBody List<DeleteTADTO> deleteTADTOs){
        for(DeleteTADTO student:deleteTADTOs)
        taService.deleteTA(student.getStudentId(), student.getClassId(), student.getCourseId());
        return Result.success();
    }
    @GetMapping("/listCourseTA")
    @Operation(summary = "查询课程助教")
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
    @Operation(summary = "助教管理的课程")
    public Result<Boolean> isMyClass(@RequestParam  Long classId){
        Map<String,Object> currentUser = BaseContext.getCurrentUser();
        long id = (long) currentUser.get(JwtClaimsConstant.USER_ID);
        List<Long> classIds = taService.getMyClass(id);
        if(!classIds.contains(classId))
            return Result.success(false);
        return Result.success(true);
    }


}
