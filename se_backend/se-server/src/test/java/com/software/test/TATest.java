package com.software.test;

import com.software.service.TAService;
import lombok.experimental.Accessors;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author
 * @Description：
 * @date
 */
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TATest {
    @Autowired
    private TAService tAService;
    @Test
    public void testAddTa(){
        tAService.addTA("22371179",51L,34L);
    }
    @Test
    public void testGetMyClass(){
        Assertions.assertThat(tAService.getMyClass(34L)).isNotNull();
    }
    @Test
    public void testDeleteTA(){
        tAService.deleteTA(34L,51L,34L);
    }
    @Test
    public void testGetCourseTA(){
        Assertions.assertThat(tAService.getCourseTA(34L)).isNotNull();
    }

}
