package com.software.service.impl;

import com.software.constant.JwtClaimsConstant;
import com.software.constant.MessageConstant;
import com.software.dto.ReplyCreateDto;
import com.software.entity.Reply;
import com.software.exception.PermissionDeniedException;
import com.software.mapper.ReplyMapper;
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
    @Override
    public void makeReply(ReplyCreateDto replyCreateDto) {
        Map<String,Object> currentUser = BaseContext.getCurrentUser();
        Long id =(long) currentUser.get(JwtClaimsConstant.USER_ID);
        replyMapper.makeReply(replyCreateDto.getContent(), replyCreateDto.getCommentId(), id, replyCreateDto.getTo());
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
