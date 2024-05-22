package com.software.service.impl;

import com.software.constant.JwtClaimsConstant;
import com.software.mapper.SpaceMapper;
import com.software.service.SpaceService;
import com.software.utils.BaseContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author
 * @Description：
 * @date
 */
@Service
public class SpaceServiceImpl implements SpaceService {
    @Autowired
    SpaceMapper spaceMapper;
    @Override
    public void createCategory(String category) {
        Map<String,Object> currentUser = BaseContext.getCurrentUser();
        Long id =(long) currentUser.get(JwtClaimsConstant.USER_ID);
        spaceMapper.createCategory(category,id);
    }

    @Override
    public void deleteCategory(String category) {
        Map<String,Object> currentUser = BaseContext.getCurrentUser();
        Long id =(long) currentUser.get(JwtClaimsConstant.USER_ID);
        spaceMapper.deleteCategory(category,id);
    }
}
