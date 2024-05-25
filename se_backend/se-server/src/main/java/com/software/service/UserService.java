package com.software.service;

import com.software.dto.*;
import com.software.entity.Course;
import com.software.entity.TClass;
import com.software.entity.User;
import com.software.vo.UserProfileVO;

import java.util.List;

/**
 * @author
 * @Description：
 * @date
 */
public interface UserService {
    User login(UserLoginDTO userLoginDTO);
    User eLogin(UserEmailLoginDTO userEmailLoginDTO);

    void register(UserRegisterDTO userRegisterDTO);

    String getUsername(long id);

    void updateAvatar(String avatarUrl);

    void updateUserMessage(UserUpdateDTO userUpdateDTO);

    void resetPassword(ResetPasswordDto resetPasswordDto);

    Long getTA(Long studentId, Long courseId);

    Long checkTeacher(Long teacherId, Long courseId);

    boolean isTeacher(Long id);

    List<Course> getCourses(List<Long> courseIds);

    List<Long> getCourseIds(String role,Long id);

    List<Long> getClassIds(Long id);

    List<TClass> getClasses(List<Long> classIds);

    UserProfileVO getProfile(Long userId, Long id);

    List<User> getUserByName(String userName);
}
