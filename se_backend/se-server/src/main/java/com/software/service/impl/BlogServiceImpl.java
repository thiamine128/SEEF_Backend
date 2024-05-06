package com.software.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.software.constant.JwtClaimsConstant;
import com.software.dto.BlogCreateDTO;
import com.software.dto.CommentPageQueryDto;
import com.software.entity.Blog;
import com.software.entity.Comment;
import com.software.mapper.BlogMapper;
import com.software.mapper.CommentMapper;
import com.software.mapper.ReplyMapper;
import com.software.result.PageResult;
import com.software.service.BlogService;
import com.software.utils.BaseContext;
import com.software.vo.CommentSummaryVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class BlogServiceImpl implements BlogService {
    @Autowired
    private BlogMapper blogMapper;
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private ReplyMapper replyMapper;

    @Override
    public void create(BlogCreateDTO blogCreateDTO) {
        Map<String,Object> currentUser = BaseContext.getCurrentUser();
        Long id =(long) currentUser.get(JwtClaimsConstant.USER_ID);
        blogMapper.createBlog(blogCreateDTO.getTitle(), blogCreateDTO.getContext(), id, blogCreateDTO.getTopicId());
    }

    @Override
    public Blog getDetail(Long blogId) {
        return blogMapper.getBlog(blogId);
    }

    @Override
    public PageResult viewComments(CommentPageQueryDto commentPageQueryDto) {
        PageHelper.startPage(commentPageQueryDto.getPage(), commentPageQueryDto.getPageSize());
        Page page = (Page) commentMapper.getComments(commentPageQueryDto.getBlogId());
        List list = page.getResult().stream().map(comment -> CommentSummaryVO.fromComment((Comment) comment, replyMapper.getReplies(((Comment) comment).getId()))).toList();
        return new PageResult(page.getTotal(), list);
    }
}
