package com.software.test;

import com.software.constant.JwtClaimsConstant;
import com.software.dto.TopicCreateDto;
import com.software.dto.TopicPageQueryDTO;
import com.software.result.PageResult;
import com.software.service.TopicService;
import com.software.utils.BaseContext;
import com.software.vo.TopicVO;
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

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
public class TopicTest {
    @Autowired
    private TopicService topicService;

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
    public void createTopic1() {
        TopicCreateDto topicCreateDto = new TopicCreateDto();
        topicCreateDto.setName("test topic 1");
        topicCreateDto.setIntroduction("int 1");
        topicService.createTopic(topicCreateDto);
    }

    @Test
    @Order(2)
    public void fail2createTopic2() {
        TopicCreateDto topicCreateDto = new TopicCreateDto();
        topicCreateDto.setName("test topic 1");
        topicCreateDto.setIntroduction("int 1");
        assertThatThrownBy(() -> {
            topicService.createTopic(topicCreateDto);
        }).isNotNull();
    }

    @Test
    @Order(3)
    public void createTopic3() {
        TopicCreateDto topicCreateDto = new TopicCreateDto();
        topicCreateDto.setName("test topic 3");
        topicCreateDto.setIntroduction("int 3");
        topicService.createTopic(topicCreateDto);
    }

    @Test
    @Order(4)
    public void listTopicsTest() {
        TopicPageQueryDTO topicPageQueryDTO = new TopicPageQueryDTO(1, 100, null);
        PageResult pageResult = topicService.pageQuery(topicPageQueryDTO);
        var ls = (List<TopicVO>) pageResult.getRecords();
        int cnt = 0;
        for (var t : ls) {
            if (t.getName().equals("test topic 1")) cnt += 1;
            if (t.getName().equals("test topic 3")) cnt += 1;
        }
        assertThat(cnt).isEqualTo(2);
    }

    @Test
    @Order(5)
    public void deleteTopic1() {
        TopicPageQueryDTO topicPageQueryDTO = new TopicPageQueryDTO(1, 100, null);
        PageResult pageResult = topicService.pageQuery(topicPageQueryDTO);
        var ls = (List<TopicVO>) pageResult.getRecords();
        for (var t : ls) {
            if (t.getName().equals("test topic 1")) topicService.deleteTopic(Math.toIntExact(t.getId()));
        }
        pageResult = topicService.pageQuery(topicPageQueryDTO);
        ls = (List<TopicVO>) pageResult.getRecords();
        int h = 0;
        for (var t : ls) {
            if (t.getName().equals("test topic 1")) h = 1;
        }
        assertThat(h).isZero();
    }

    @Test
    @Order(6)
    public void deleteTopic3() {
        TopicPageQueryDTO topicPageQueryDTO = new TopicPageQueryDTO(1, 100, null);
        PageResult pageResult = topicService.pageQuery(topicPageQueryDTO);
        var ls = (List<TopicVO>) pageResult.getRecords();
        for (var t : ls) {
            if (t.getName().equals("test topic 3")) topicService.deleteTopic(Math.toIntExact(t.getId()));
        }
        pageResult = topicService.pageQuery(topicPageQueryDTO);
        ls = (List<TopicVO>) pageResult.getRecords();
        int h = 0;
        for (var t : ls) {
            if (t.getName().equals("test topic 3")) h = 1;
        }
        assertThat(h).isZero();
    }
}
