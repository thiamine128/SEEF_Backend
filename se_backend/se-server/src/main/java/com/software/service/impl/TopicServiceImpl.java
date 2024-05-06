package com.software.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.software.dto.BlogPreviewPageQueryDto;
import com.software.dto.TopicCreateDto;
import com.software.dto.TopicPageQueryDTO;
import com.software.entity.Blog;
import com.software.mapper.BlogMapper;
import com.software.mapper.TopicMapper;
import com.software.result.PageResult;
import com.software.service.TopicService;
import com.software.vo.BlogPreviewVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TopicServiceImpl implements TopicService {

    @Autowired
    private TopicMapper topicMapper;
    @Autowired
    private BlogMapper blogMapper;

    @Override
    public void createTopic(TopicCreateDto topicCreateDto) {
        topicMapper.createTopic(topicCreateDto);
    }

    @Override
    public PageResult pageQuery(TopicPageQueryDTO topicPageQueryDTO) {
        PageHelper.startPage(topicPageQueryDTO.getPage(), topicPageQueryDTO.getPageSize());
        Page page = (Page) topicMapper.pageQuery(topicPageQueryDTO);//后绪步骤实现

        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    public PageResult getBlogs(BlogPreviewPageQueryDto blogPageQueryDto) {
        PageHelper.startPage(blogPageQueryDto.getPage(), blogPageQueryDto.getPageSize());
        Page page = (Page) blogMapper.getBlogsInTopic(blogPageQueryDto.getTopicId());
        return new PageResult(page.getTotal(), page.getResult().stream().map(blog -> BlogPreviewVO.fromBlog((Blog) blog, blogPageQueryDto.getPreviewLength())).toList());
    }
}
