package com.software.service.impl;

import com.software.constant.MessageConstant;
import com.software.dto.AddBatchTADTO;
import com.software.entity.User;
import com.software.exception.InvalidParameterException;
import com.software.mapper.TAMapper;
import com.software.mapper.UserMapper;
import com.software.service.TAService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author
 * @Description：
 * @date
 */

@Service
@Slf4j
public class TAServiceImpl implements TAService {
    @Autowired
    TAMapper taMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    public void addTA(String studentAccount, Long classId, Long courseId) {
        Long id = userMapper.getIdByName(studentAccount);
        if(id == null) {
            throw new InvalidParameterException(MessageConstant.ACCOUNT_NOT_FOUND);
        }
        taMapper.addTA(id, classId, courseId);
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

    @Override
    public void addButchTA(List<AddBatchTADTO> tas) {
        taMapper.addButchTA(tas);
    }
}
