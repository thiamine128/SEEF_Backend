package com.software.test;

import com.software.constant.JwtClaimsConstant;
import com.software.service.BlogService;
import com.software.service.SpaceService;
import com.software.utils.BaseContext;
import com.software.vo.CategoryVO;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SpaceTest {
    @Autowired
    private SpaceService spaceService;
    @Autowired
    private BlogService blogService;

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
    public void createCat1() {
        spaceService.createCategory("Cat 1");
        var ls = blogService.getCategoryList(1L);
        int h = 0;
        for (CategoryVO vo : ls) {
            if (vo.getCategoryName().equals("Cat 1"))
                h = 1;
        }
        assertThat(h).isEqualTo(1);
    }

    @Test
    @Order(2)
    public void createCat2() {
        spaceService.createCategory("Cat 2");
        var ls = blogService.getCategoryList(1L);
        int h = 0;
        for (CategoryVO vo : ls) {
            if (vo.getCategoryName().equals("Cat 2"))
                h = 1;
        }
        assertThat(h).isEqualTo(1);
    }

    @Test
    @Order(3)
    public void updateCat1() {
        var ls = blogService.getCategoryList(1L);
        Long id = 0L;
        for (CategoryVO vo : ls) {
            if (vo.getCategoryName().equals("Cat 1"))
                id = vo.getId();
        }
        spaceService.updateCategory("Category 1", id);
        ls = blogService.getCategoryList(1L);
        int h = 0;
        for (CategoryVO vo : ls) {
            if (vo.getCategoryName().equals("Category 1"))
                h = 1;
        }
        assertThat(h).isEqualTo(1);
    }

    @Test
    @Order(4)
    public void updateCat2() {
        var ls = blogService.getCategoryList(1L);
        Long id = 0L;
        for (CategoryVO vo : ls) {
            if (vo.getCategoryName().equals("Cat 2"))
                id = vo.getId();
        }
        spaceService.updateCategory("Category 2", id);
        ls = blogService.getCategoryList(1L);
        int h = 0;
        for (CategoryVO vo : ls) {
            if (vo.getCategoryName().equals("Category 2"))
                h = 1;
        }
        assertThat(h).isEqualTo(1);
    }

    @Test
    @Order(5)
    public void deleteCat1() {
        spaceService.deleteCategory("Category 1");
        var ls = blogService.getCategoryList(1L);
        int h = 0;
        for (CategoryVO vo : ls) {
            if (vo.getCategoryName().equals("Category 1"))
                h = 1;
        }
        assertThat(h).isEqualTo(0);
    }

    @Test
    @Order(6)
    public void deleteCat2() {
        spaceService.deleteCategory("Category 2");
        var ls = blogService.getCategoryList(1L);
        int h = 0;
        for (CategoryVO vo : ls) {
            if (vo.getCategoryName().equals("Category 2"))
                h = 1;
        }
        assertThat(h).isEqualTo(0);
    }
}
