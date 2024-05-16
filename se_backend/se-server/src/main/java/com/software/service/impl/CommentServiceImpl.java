package com.software.service.impl;

import com.software.constant.JwtClaimsConstant;
import com.software.dto.CommentCreateDto;
import com.software.entity.Blog;
import com.software.entity.Comment;
import com.software.entity.Event;
import com.software.mapper.CommentMapper;
import com.software.service.BlogService;
import com.software.service.CommentService;
import com.software.service.EventService;
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
    @Autowired
    private EventService eventService;
    @Autowired
    private BlogService blogService;

    @Override
    public void makeComment(CommentCreateDto commentCreateDto) {
        Map<String,Object> currentUser = BaseContext.getCurrentUser();
        Long id = (long) currentUser.get(JwtClaimsConstant.USER_ID);
        Comment comment = new Comment();
        comment.setBlogId(commentCreateDto.getBlogId());
        comment.setContent(commentCreateDto.getContent());
        comment.setUserId(id);
        commentMapper.makeComment(comment);
        Blog blog = blogService.getDetail(commentCreateDto.getBlogId());
        eventService.newEvent(Event.comment(id, commentCreateDto.getBlogId(), blog.getUserId(), comment.getId()));
    }

    @Override
    public void deleteComment(Long id) {
        commentMapper.deleteComment(id);
    }

    @Override
    public Comment getComment(Long id) {
        return commentMapper.getComment(id);
    }
}
