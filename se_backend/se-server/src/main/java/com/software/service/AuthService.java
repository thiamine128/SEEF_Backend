package com.software.service;

/**
 * @author
 * @Description：
 * @date
 */
public interface AuthService {
    public  void authCheck(String[] mustRole, Long ... id);

    public void classCheck(Long userId, Long classId);
}
