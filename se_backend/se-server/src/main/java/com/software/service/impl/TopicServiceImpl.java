package com.software.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.software.dto.TopicPageQueryDTO;
import com.software.mapper.TopicMapper;
import com.software.result.PageResult;
import com.software.service.TopicService;
import com.software.vo.TopicVO;
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

    @Override
    public PageResult pageQuery(TopicPageQueryDTO topicPageQueryDTO) {
        PageHelper.startPage(topicPageQueryDTO.getPage(), topicPageQueryDTO.getPageSize());
        Page<TopicVO> page = topicMapper.pageQuery(topicPageQueryDTO);//后绪步骤实现
        return new PageResult(page.getTotal(), page.getResult());
    }
}
