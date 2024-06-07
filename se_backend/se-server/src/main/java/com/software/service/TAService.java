package com.software.service;

import com.software.dto.AddBatchTADTO;
import com.software.entity.User;

import java.util.List;

/**
 * @author
 * @Description：
 * @date
 */
public interface TAService {

    void addTA(String studentId, Long classId, Long courseId);
    
    void deleteTA(Long studentId, Long classId, Long courseId);

    List<User> getCourseTA(Long courseId);

    List<Long> getMyClass(long id);

    void addButchTA(List<AddBatchTADTO> taList);
}
