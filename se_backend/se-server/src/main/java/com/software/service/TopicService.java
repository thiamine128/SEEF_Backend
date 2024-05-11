package com.software.service;

import com.software.dto.BlogPreviewPageQueryDTO;
import com.software.dto.TopicCreateDto;
import com.software.dto.TopicPageQueryDTO;
import com.software.result.PageResult;

public interface TopicService {
    void createTopic(TopicCreateDto topicCreateDto);

    PageResult pageQuery(TopicPageQueryDTO topicPageQueryDTO);

    PageResult getBlogs(BlogPreviewPageQueryDTO blogPageQueryDto);

    void deleteTopic(Integer topicId);
}
