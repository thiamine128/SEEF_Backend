package com.software.test;

import com.software.constant.JwtClaimsConstant;
import com.software.entity.Event;
import com.software.service.EventService;
import com.software.service.SubscribeService;
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
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SubscribeTest {
    @Autowired
    private SubscribeService subscribeService;
    @Autowired
    private EventService eventService;
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
    public void subscribeUser1() {
        subscribeService.subscribe(1L, 34L);
    }
    @Test
    @Order(2)
    public void subscribeUser2() {
        subscribeService.subscribe(1L, 22371185L);
    }
    @Test
    @Order(3)
    public void subscribeNotification1() {
        List<Event> events = eventService.pullEvents(34L);
        int h = 0;
        for (Event e : events) {
            if (e.getType().equals("subscribe") && e.getSubject().equals(1L)) {
                h = 1;
            }
        }
        assertThat(h).isEqualTo(1);
    }
    @Test
    @Order(4)
    public void subscribeNotification2() {
        List<Event> events = eventService.pullEvents(22371185L);
        int h = 0;
        for (Event e : events) {
            if (e.getType().equals("subscribe") && e.getSubject().equals(1L)) {
                h = 1;
            }
        }
        assertThat(h).isEqualTo(1);
    }
    @Test
    @Order(5)
    public void listSubscribersTest() {
        List<Long> subscribers = subscribeService.getSubscribers(34L);
        assertThat(subscribers.contains(1L)).isTrue();
        subscribers = subscribeService.getSubscribers(22371185L);
        assertThat(subscribers.contains(1L)).isTrue();
    }
    @Test
    @Order(6)
    public void listSubscribedUsersTest() {
        var ls = subscribeService.getSubscribed(1L);
        assertThat(ls.contains(34L)).isTrue();
        assertThat(ls.contains(22371185L)).isTrue();
    }
    @Test
    @Order(7)
    public void subscribeFailTest() {
        assertThatThrownBy(() -> {
            subscribeService.subscribe(1L, 34L);
        }).isNotNull();
    }
    @Test
    @Order(8)
    public void subScribeFailTest2() {
        assertThatThrownBy(() -> {
            subscribeService.subscribe(1L, 22371185L);
        }).isNotNull();
    }
    @Test
    @Order(9)
    public void unsubscribeTest() {
        subscribeService.unsubscribe(1L, 34L);
        List<Long> subscribers = subscribeService.getSubscribers(34L);
        assertThat(subscribers.contains(1L)).isFalse();

    }
    @Test
    @Order(10)
    public void unsubscribeTest2() {
        subscribeService.unsubscribe(1L, 22371185L);
        List<Long> subscribers = subscribeService.getSubscribers(22371185L);
        assertThat(subscribers.contains(1L)).isFalse();
    }
}
