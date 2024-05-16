package com.software.service.impl;

import com.software.constant.JwtClaimsConstant;
import com.software.constant.MessageConstant;
import com.software.dto.ReplyCreateDto;
import com.software.entity.Comment;
import com.software.entity.Event;
import com.software.entity.Reply;
import com.software.exception.PermissionDeniedException;
import com.software.mapper.ReplyMapper;
import com.software.service.CommentService;
import com.software.service.EventService;
import com.software.service.ReplyService;
import com.software.utils.BaseContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Slf4j
public class ReplyServiceImpl implements ReplyService {
    @Autowired
    private ReplyMapper replyMapper;
    @Autowired
    private EventService eventService;
    @Autowired
    private CommentService commentService;
    @Override
    public void makeReply(ReplyCreateDto replyCreateDto) {
        Map<String,Object> currentUser = BaseContext.getCurrentUser();
        Long id =(long) currentUser.get(JwtClaimsConstant.USER_ID);
        Reply reply = new Reply();
        reply.setCommentId(replyCreateDto.getCommentId());
        reply.setContent(replyCreateDto.getContent());
        reply.setToId(replyCreateDto.getTo());
        reply.setUserId(id);
        replyMapper.makeReply(reply);
        Comment comment = commentService.getComment(replyCreateDto.getCommentId());
        Event event = null;
        if (replyCreateDto.getTo() == null) {
            event = Event.replyComment(id, replyCreateDto.getCommentId(), comment.getUserId(), reply.getId());
        } else {
            event = Event.reply(id, replyCreateDto.getCommentId(), replyCreateDto.getTo(), reply.getId());
        }
        eventService.newEvent(event);
    }

    @Override
    public void deleteReply(Long replyId) {
        replyMapper.deleteReply(replyId);
    }

    @Override
    public Reply getReply(Long id) {
        return replyMapper.getReply(id);
    }
}
