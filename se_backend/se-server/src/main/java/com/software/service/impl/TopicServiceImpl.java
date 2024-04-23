package com.software.service.impl;

import com.software.mapper.TopicMapper;
import com.software.service.TopicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TopicServiceImpl implements TopicService {

    @Autowired
    private TopicMapper topicMapper;

    @Override
    public void createTopic(String name) {
        topicMapper.createTopic(name);
    }
}
