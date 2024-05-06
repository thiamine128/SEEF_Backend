package com.software.service.impl;

import com.software.constant.JwtClaimsConstant;
import com.software.dto.CommentCreateDto;
import com.software.mapper.CommentMapper;
import com.software.service.CommentService;
import com.software.utils.BaseContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Slf4j
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentMapper commentMapper;

    @Override
    public void makeComment(CommentCreateDto commentCreateDto) {
        Map<String,Object> currentUser = BaseContext.getCurrentUser();
        Long id =(long) currentUser.get(JwtClaimsConstant.USER_ID);
        commentMapper.makeComment(commentCreateDto.getContent(), commentCreateDto.getBlogId(), id);
    }
}
