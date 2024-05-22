package com.software.aop;

import com.software.annotation.AuthCheck;

import com.software.constant.JwtClaimsConstant;
import com.software.entity.User;
import com.software.exception.PermissionDeniedException;
import com.software.service.UserService;
import com.software.utils.BaseContext;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Map;

/**
 * 权限校验 AOP
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
@Aspect
@Component
public class AuthInterceptor {
    @Autowired
    UserService userService;
    /**
     * 执行拦截
     *
     * @param joinPoint
     * @param authCheck
     * @return
     */
    @Around("@annotation(authCheck)")
    public Object doInterceptor(ProceedingJoinPoint joinPoint, AuthCheck authCheck) throws Throwable {
        String[] mustRole = authCheck.mustRole();

        // 当前登录用户
        Map<String,Object> currentUser = BaseContext.getCurrentUser();
        String role = currentUser.get(JwtClaimsConstant.USER_ROLE).toString();

        // 必须有该权限才通过
        if (mustRole.length>0) {
            String userRole = role;
            // 必须相应权限
            if (!Arrays.asList(mustRole).contains(userRole)) {
                System.out.println("权限不足");
                throw new PermissionDeniedException("权限不足");
            }
        }
        // 通过权限校验，放行
        return joinPoint.proceed();
    }
}

