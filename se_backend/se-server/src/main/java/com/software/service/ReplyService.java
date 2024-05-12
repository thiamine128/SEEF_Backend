package com.software.service;

import com.software.dto.ReplyCreateDto;

public interface ReplyService {
    void makeReply(ReplyCreateDto replyCreateDto);

    void deleteReply(Long replyId);
}
