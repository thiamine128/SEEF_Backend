package com.software.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.software.constant.JwtClaimsConstant;
import com.software.dto.BlogPreviewPageQueryDTO;
import com.software.dto.TopicCreateDto;
import com.software.dto.TopicPageQueryDTO;
import com.software.entity.Blog;
import com.software.mapper.BlogMapper;
import com.software.mapper.TopicMapper;
import com.software.result.PageResult;
import com.software.service.TopicService;
import com.software.utils.BaseContext;
import com.software.vo.BlogPreviewVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

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
    public PageResult getBlogs(BlogPreviewPageQueryDTO blogPageQueryDto) {
        PageHelper.startPage(blogPageQueryDto.getPage(), blogPageQueryDto.getPageSize());
        Page page = (Page) blogMapper.getBlogsInTopic(blogPageQueryDto.getTopicId());
        Map<String,Object> currentUser = BaseContext.getCurrentUser();
        Long id =(long) currentUser.get(JwtClaimsConstant.USER_ID);
        return new PageResult(page.getTotal(), page.getResult().stream().map(blog ->{
            return BlogPreviewVO.fromBlog((Blog) blog, blogPageQueryDto.getPreviewLength(), blogMapper.isLiked(((Blog) blog).getId(),id),blogMapper.isFavor(((Blog) blog).getId(),id));
        }).toList());
    }

    @Override
    public void deleteTopic(Integer topicId) {
        topicMapper.deleteTopic(topicId);
    }
}
