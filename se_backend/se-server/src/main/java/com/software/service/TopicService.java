package com.software.service;

import com.software.dto.TopicPageQueryDTO;
import com.software.result.PageResult;

public interface TopicService {
    void createTopic(String name);

    PageResult pageQuery(TopicPageQueryDTO topicPageQueryDTO);
}
