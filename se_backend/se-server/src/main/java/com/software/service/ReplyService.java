package com.software.service;

import com.software.dto.ReplyCreateDto;
import com.software.entity.Reply;

public interface ReplyService {
    void makeReply(ReplyCreateDto replyCreateDto);

    void deleteReply(Long replyId);
    Reply getReply(Long id);
}
