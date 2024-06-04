package com.software.controller;

import com.software.config.OssConfiguration;
import com.software.constant.JwtClaimsConstant;
import com.software.constant.MessageConstant;
import com.software.constant.RoleConstant;
import com.software.dto.MakeDirectoryDTO;
import com.software.exception.PermissionDeniedException;
import com.software.result.Result;
import com.software.service.CourseResourceService;
import com.software.utils.AliOssUtil;
import com.software.utils.BaseContext;
import com.software.vo.OSSPostSignatureVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

/**
 * @author
 * @Description：
 * @date
 */
@Tag(name = "课程资料接口")
@RestController
@RequestMapping("/api/courseResource")
@Slf4j
public class CourseResourceController {
    @Autowired
    private CourseResourceService courseResourceService;
    @Autowired
    private AliOssUtil aliOssUtil;
    @GetMapping("/list")
    @Operation(summary = "获取资料")
    public Result<List<String>> getDocuments(@RequestParam String currentDirectory, @RequestParam Long courseId){
        String id = courseId.toString();
        if(!currentDirectory.startsWith("/") || !currentDirectory.endsWith("/")){
            throw new IllegalArgumentException("目录必须以/开头，以/结束");
        }
        String path = "course_resource/"+id+currentDirectory;
        List<String> result = aliOssUtil.getDocument(path);
        return Result.success(result);
    }
    @PostMapping("/upload")
    @Operation(summary = "上传资料")
    public Result<OSSPostSignatureVO> upload(@RequestParam String currentDirectory,@RequestParam Long courseId,@RequestParam String filename) throws UnsupportedEncodingException {
        String id = courseId.toString();
        Map<String,Object> currentUser = BaseContext.getCurrentUser();
        String role = currentUser.get(JwtClaimsConstant.USER_ROLE).toString();
        if ((!role.equals(RoleConstant.ADMIN))&&(!role.equals(RoleConstant.TEACHER)))
        {throw new PermissionDeniedException(MessageConstant.PERMISSION_DENIED);}
        if(!currentDirectory.startsWith("/") || !currentDirectory.endsWith("/")){
            throw new IllegalArgumentException("目录必须以/开头，以/结束");
        }
        String objectName = "course_resource/"+id+currentDirectory+filename;
        AliOssUtil.PostSignature postSignature = aliOssUtil.generatePostSignature(objectName, System.currentTimeMillis() + OssConfiguration.EXPIRE_SEC * 1000, 52428800);
        OSSPostSignatureVO ossPostSignatureVO = OSSPostSignatureVO.builder()
                .accessKeyId(postSignature.getAccessKeyId())
                .objectName(postSignature.getObjectName())
                .encodedPolicy(postSignature.getEncodedPolicy())
                .postSignature(postSignature.getPostSignature())
                .host(postSignature.getHost()).build();
        return Result.success(ossPostSignatureVO);
    }
    @DeleteMapping("/delete")
    @Operation(summary = "删除资料")
    public Result delete(@RequestParam String currentDirectory,@RequestParam Long courseId,@RequestParam String filename){
        String id = courseId.toString();
        Map<String,Object> currentUser = BaseContext.getCurrentUser();
        String role = currentUser.get(JwtClaimsConstant.USER_ROLE).toString();
        if ((!role.equals(RoleConstant.ADMIN))&&(!role.equals(RoleConstant.TEACHER)))
        {throw new PermissionDeniedException(MessageConstant.PERMISSION_DENIED);}
        if(!currentDirectory.startsWith("/") || !currentDirectory.endsWith("/")){
            throw new IllegalArgumentException("目录必须以/开头，以/结束");
        }
        String objectName = "course_resource/"+id+currentDirectory+filename;
        aliOssUtil.delete(objectName);
        return Result.success();
    }

    @PostMapping("/mkdir")
    @Operation(summary = "新建课程资源目录")
    public Result mkDirectory(@RequestBody MakeDirectoryDTO makeDirectoryDTO){
//        Map<String,Object> currentUser = BaseContext.getCurrentUser();
//        String role = currentUser.get(JwtClaimsConstant.USER_ROLE).toString();
//        if ((!role.equals(RoleConstant.ADMIN))&&(!role.equals(RoleConstant.TEACHER)))
//        {throw new PermissionDeniedException(MessageConstant.PERMISSION_DENIED);}
        String id = makeDirectoryDTO.getCourseId().toString();
        if (makeDirectoryDTO.getNewDirectory().isEmpty()) {
            throw new IllegalArgumentException("目录名不能为空");
        }
        if (makeDirectoryDTO.getNewDirectory().contains("/")) {
            throw new IllegalArgumentException("新目录名不能包含/");
        }
        if(!makeDirectoryDTO.getCurrentDirectory().startsWith("/") || !makeDirectoryDTO.getCurrentDirectory().endsWith("/")){
            throw new IllegalArgumentException("目录必须以/开头，以/结束");
        }
        String path = "course_resource/"+id+makeDirectoryDTO.getCurrentDirectory()+makeDirectoryDTO.getNewDirectory()+"/";
        aliOssUtil.mkdir(path);
        return Result.success();
    }
}
