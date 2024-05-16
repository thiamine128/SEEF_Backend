package com.software.service;

import com.software.dto.SendMessageDTO;
import com.software.entity.Message;
import com.software.vo.ChatMsgVO;

import java.util.List;

/**
 * @author
 * @Description：
 * @date
 */
public interface MessageService {
    void sendMessage(SendMessageDTO sendMessageDTO, Long send);
    List<Message> getMessages(Long id);
    void markRead(Long sender, Long to);
    Long getCnt(Long id);
}
