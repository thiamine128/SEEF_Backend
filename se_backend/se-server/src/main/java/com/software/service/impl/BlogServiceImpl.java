package com.software.service.impl;

import com.software.constant.JwtClaimsConstant;
import com.software.constant.MessageConstant;
import com.software.exception.NoSuchTopicException;
import com.software.mapper.BlogMapper;
import com.software.mapper.TopicMapper;
import com.software.service.BlogService;
import com.software.utils.BaseContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Slf4j
public class BlogServiceImpl implements BlogService {
    @Autowired
    private BlogMapper blogMapper;
    @Autowired
    private TopicMapper topicMapper;

    @Override
    public void create(String title, String content, Long topicId) {
        Map<String,Object> currentUser = BaseContext.getCurrentUser();
        Long id =(long) currentUser.get(JwtClaimsConstant.USER_ID);
        if (topicMapper.getById(topicId) == null) {
            throw new NoSuchTopicException(MessageConstant.NO_SUCH_TOPIC);
        }
        blogMapper.createBlog(title, content, id, topicId);
    }
}
