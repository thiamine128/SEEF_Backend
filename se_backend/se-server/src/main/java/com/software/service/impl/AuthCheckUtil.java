package com.software.service.impl;

import com.software.constant.JwtClaimsConstant;
import com.software.exception.PermissionDeniedException;
import com.software.service.AuthService;
import com.software.service.UserService;
import com.software.utils.BaseContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Map;
/**
 * @author
 * @Description：
 * @date
 */
@Service
@Slf4j
public class AuthCheckUtil implements AuthService {
    @Autowired
    UserService userService;
    public  void authCheck(String[] mustRole, Long ...id){
        Map<String,Object> currentUser = BaseContext.getCurrentUser();
        String role = currentUser.get(JwtClaimsConstant.USER_ROLE).toString();
        Long userId = Long.valueOf(String.valueOf( currentUser.get(JwtClaimsConstant.USER_ID)));
        if(!Arrays.asList(mustRole).contains(role)){
            if(id.length>0){
                if(userService.getTA(userId,id[0])==null||userService.checkTeacher(userId,id[0])==null){
                    throw new PermissionDeniedException("权限不足");
                }
            }else{
                throw new PermissionDeniedException("权限不足");
            }
        }


        throw new PermissionDeniedException("权限不足");
    }
}
