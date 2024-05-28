package com.software.service.impl;

import com.software.entity.User;
import com.software.mapper.TAMapper;
import com.software.service.TAService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author
 * @Description：
 * @date
 */
@Service
public class TAServiceImpl implements TAService {
    @Autowired
    TAMapper taMapper;
    @Override
    public void addTA(Long studentId, Long classId, Long courseId) {
        taMapper.addTA(studentId, classId, courseId);
    }

    @Override
    public void deleteTA(Long studentId, Long classId, Long courseId) {
        taMapper.removeTA(studentId, classId, courseId);
    }

    @Override
    public List<User> getCourseTA(Long courseId) {
        return taMapper.getCourseTA(courseId);
    }

    @Override
    public List<Long> getMyClass(long id) {
       return taMapper.getMyClassIds(id);
    }
}
