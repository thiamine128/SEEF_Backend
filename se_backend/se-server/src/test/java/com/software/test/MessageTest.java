package com.software.test;

import com.software.constant.JwtClaimsConstant;
import com.software.dto.SendMessageDTO;
import com.software.entity.Message;
import com.software.service.MessageService;
import com.software.utils.BaseContext;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MessageTest {
    @Autowired
    private MessageService messageService;
    @Test
    @Order(0)
    public void setup() {
        HashMap<String, Object> usr = new HashMap<>();
        usr.put(JwtClaimsConstant.USER_ID, 1L);
        usr.put(JwtClaimsConstant.USER_ROLE, "student");
        BaseContext.setCurrentUser(usr);
    }
    @Test
    @Order(1)
    public void sendMessage1() {
        SendMessageDTO sendMessageDTO = new SendMessageDTO();
        sendMessageDTO.setContent("Hello");
        sendMessageDTO.setTo(34L);
        messageService.sendMessage(sendMessageDTO, 1L);
    }

    @Test
    @Order(2)
    public void receiveMessage1() {
        List<Message> messages = messageService.getMessages(34L);
        int h = 0;
        for (var msg : messages) {
            if (msg.getContent().equals("Hello")) {
                h = 1;
            }
        }
        assertThat(h).isEqualTo(1);
    }

    @Test
    @Order(3)
    public void sendMessage2() {
        SendMessageDTO sendMessageDTO = new SendMessageDTO();
        sendMessageDTO.setContent("Hi");
        sendMessageDTO.setTo(1L);
        messageService.sendMessage(sendMessageDTO, 34L);
    }

    @Test
    @Order(4)
    public void receiveMessage2() {
        List<Message> messages = messageService.getMessages(1L);
        int h = 0;
        for (var msg : messages) {
            if (msg.getContent().equals("Hi")) {
                h = 1;
            }
        }
        assertThat(h).isEqualTo(1);
    }

    @Test
    @Order(5)
    public void sendMessage3() {
        SendMessageDTO sendMessageDTO = new SendMessageDTO();
        sendMessageDTO.setContent("HelloWorld");
        sendMessageDTO.setTo(34L);
        messageService.sendMessage(sendMessageDTO, 1L);
    }

    @Test
    @Order(6)
    public void receiveMessage3() {
        List<Message> messages = messageService.getMessages(34L);
        int h = 0;
        for (var msg : messages) {
            if (msg.getContent().equals("HelloWorld")) {
                h = 1;
            }
        }
        assertThat(h).isEqualTo(1);
    }
}
