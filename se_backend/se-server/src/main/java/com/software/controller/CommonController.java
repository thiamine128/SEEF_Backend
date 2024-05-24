package com.software.controller;

import cn.hutool.http.useragent.OS;
import com.software.config.OssConfiguration;
import com.software.constant.JwtClaimsConstant;
import com.software.constant.MessageConstant;
import com.software.constant.RoleConstant;
import com.software.dto.MakeDirectoryDTO;
import com.software.exception.PermissionDeniedException;
import com.software.result.Result;
import com.software.utils.AliOssUtil;
import com.software.utils.BaseContext;
import com.software.vo.OSSPostSignatureVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.UUID;

/**
 * @author
 * @Description：
 * @date
 */
@Tag(name = "通用接口")
@RestController
@RequestMapping("/api/common")
@Slf4j
public class CommonController {
    @Autowired
    private AliOssUtil aliOssUtil;

    @PostMapping("/upload")
    @Operation(deprecated = true, summary = "Forced upload")
    public Result<String> upload(MultipartFile file)  {
        log.info("文件上传 {}",file);
        try {
            String originalFilename = file.getOriginalFilename();
            log.info(originalFilename);
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String objectName = UUID.randomUUID().toString()+extension;
            String filePath = aliOssUtil.upload(file.getBytes(), file.getContentType(), objectName);
            return Result.success(filePath);
        } catch (IOException e) {
           log.error("文件上传失败,{}",e);
        }
        return Result.error(MessageConstant.UPLOAD_FAILED);
    }

    @PostMapping("/uploadWithName")
    @Operation(deprecated = true, summary = "Request Upload")
    public Result<OSSPostSignatureVO> upload(@RequestParam String objectName) throws UnsupportedEncodingException {
        Map<String,Object> currentUser = BaseContext.getCurrentUser();
        String role = currentUser.get(JwtClaimsConstant.USER_ROLE).toString();
        if ((!role.equals(RoleConstant.ADMIN))&&(!role.equals(RoleConstant.TEACHER)))
        {throw new PermissionDeniedException(MessageConstant.PERMISSION_DENIED);}
            AliOssUtil.PostSignature postSignature = aliOssUtil.generatePostSignature(objectName, System.currentTimeMillis() + OssConfiguration.EXPIRE_SEC * 1000, 52428800);
            OSSPostSignatureVO ossPostSignatureVO = OSSPostSignatureVO.builder()
                    .accessKeyId(postSignature.getAccessKeyId())
                    .objectName(postSignature.getObjectName())
                    .encodedPolicy(postSignature.getEncodedPolicy())
                    .postSignature(postSignature.getPostSignature())
                    .host(postSignature.getHost()).build();
            return Result.success(ossPostSignatureVO);

    }

    @PostMapping("/requestUploadImage")
    @Operation(summary = "请求上传图片")
    public Result<OSSPostSignatureVO> requestUploadImage() throws UnsupportedEncodingException {
        String objectName = "image/" + UUID.randomUUID();
        AliOssUtil.PostSignature postSignature = aliOssUtil.generatePostSignature(objectName, System.currentTimeMillis() + OssConfiguration.EXPIRE_SEC * 1000, 524288000);
        OSSPostSignatureVO ossPostSignatureVO = OSSPostSignatureVO.builder()
                .accessKeyId(postSignature.getAccessKeyId())
                .objectName(postSignature.getObjectName())
                .encodedPolicy(postSignature.getEncodedPolicy())
                .postSignature(postSignature.getPostSignature())
                .host(postSignature.getHost()).build();
        return Result.success(ossPostSignatureVO);
    }
}
