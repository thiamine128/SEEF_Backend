package com.software.service.impl;

import com.alibaba.fastjson.JSON;
import com.software.constant.MessageConstant;
import com.software.dto.SendMessageDTO;
import com.software.entity.Message;
import com.software.exception.InvalidUserException;
import com.software.mapper.MessageMapper;
import com.software.mapper.UserMapper;
import com.software.service.MessageService;
import com.software.websocket.WebSocketServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author
 * @Description：
 * @date
 */
@Service
@Slf4j
public class MessageServiceImpl implements MessageService {
    @Autowired
    MessageMapper messageMapper;
    @Autowired
    UserMapper userMapper;
    @Override
    public void sendMessage(SendMessageDTO sendMessageDTO, Long send) {
        if (userMapper.exist(sendMessageDTO.getTo()) == null || sendMessageDTO.getTo().equals(send)) {
            throw new InvalidUserException(MessageConstant.INVALID_USER);
        }
        Message message = new Message(UUID.randomUUID().toString(), send, sendMessageDTO.getTo(), sendMessageDTO.getContent(), new Date(System.currentTimeMillis()), false);

        messageMapper.createMessage(message);
    }

    @Override
    public List<Message> getMessages(Long id) {
        List<Message> messages = messageMapper.getMessages(id);
        messageMapper.delete(id);
        return messages;
    }

    @Override
    public void markRead(Long sender, Long to) {
        messageMapper.markRead(sender, to);
    }

    @Override
    public Long getCnt(Long id) {
        return messageMapper.getCount(id);
    }
}
