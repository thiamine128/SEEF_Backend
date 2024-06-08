package com.software.test;

import com.software.dto.*;
import com.software.entity.Course;
import com.software.entity.CourseClass;
import com.software.entity.User;
import com.software.mapper.ClassMapper;
import com.software.mapper.CourseMapper;
import com.software.result.PageResult;
import com.software.service.AdminService;
import com.software.service.CourseService;
import com.software.service.UserService;
import com.software.vo.CourseClassVO;
import com.software.vo.JoinClassRequestVO;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CourseTest {
    @Autowired
    private CourseService courseService;
    @Autowired
    private AdminService adminService;
    @Autowired
    private UserService userService;
    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private ClassMapper classMapper;

    @Test
    public void notNull() {
        assertThat(courseService).isNotNull();
    }
    @Test
    @Order(1)
    public void createCourse() {
        CourseCreateDto courseCreateDto = new CourseCreateDto();
        courseCreateDto.setCredit(new BigDecimal(4));
        courseCreateDto.setName("测试课程test");
        courseCreateDto.setEvaluation("考试");
        courseCreateDto.setSyllabus("大纲");
        User teacher = userService.getByACCount("teacher");
        assertThat(courseService.createCourse(courseCreateDto, teacher.getId())).isNotNull();
    }
    @Test
    @Order(2)
    public void identifyCourse() {
        Course course = courseService.getCourseByName("测试课程test");
        assertThat(course).isNotNull();
        assertThat(courseService.getCourseByName("sdadsahvd had")).isNull();
    }
    @Test
    @Order(3)
    public void createClass1() {
        ClassCreateDto classCreateDto = new ClassCreateDto();
        Course course = courseService.getCourseByName("测试课程test");
        classCreateDto.setCourseId(course.getId());
        classCreateDto.setName("班级1");
        classCreateDto.setLocation("地点1");
        classCreateDto.setTime("时间1");
        classCreateDto.setTimeData(null);
        courseService.addClass(classCreateDto);
    }

    @Test
    @Order(4)
    public void createClassDuplicate() {
        ClassCreateDto classCreateDto = new ClassCreateDto();
        Course course = courseService.getCourseByName("测试课程test");
        classCreateDto.setCourseId(course.getId());
        classCreateDto.setName("班级1");
        classCreateDto.setLocation("地点1");
        classCreateDto.setTime("时间1");
        classCreateDto.setTimeData(null);
        assertThatThrownBy(() -> {
            courseService.addClass(classCreateDto);
        }).isNotNull();
    }

    @Test
    @Order(5)
    public void createClass2() {
        ClassCreateDto classCreateDto = new ClassCreateDto();
        Course course = courseService.getCourseByName("测试课程test");
        classCreateDto.setCourseId(course.getId());
        classCreateDto.setName("班级2");
        classCreateDto.setLocation("地点2");
        classCreateDto.setTime("时间2");
        classCreateDto.setTimeData(null);
        courseService.addClass(classCreateDto);
    }

    @Test
    @Order(6)
    public void updateCourseInfo() {
        Course course = courseService.getCourseByName("测试课程test");
        CourseUpdateDto courseUpdateDto = new CourseUpdateDto();
        courseUpdateDto.setCredit(new BigDecimal(128));
        courseUpdateDto.setEvaluation("up");
        courseUpdateDto.setId(course.getId());
        courseUpdateDto.setSyllabus("up");
        courseUpdateDto.setName(course.getName());
        courseUpdateDto.setIntroduction("简介");
        courseService.updateCourse(courseUpdateDto);
        course = courseService.getCourseById(course.getId());
        assertThat(course.getEvaluation()).isEqualTo("up");
    }

    @Test
    @Order(7)
    public void updateClassInfo() {
        Course course = courseService.getCourseByName("测试课程test");
        List<CourseClass> classes = courseMapper.getClasses(course.getId());
        ClassUpdateDto classUpdateDto = new ClassUpdateDto();
        Long id = classes.get(0).getId();
        classUpdateDto.setId(id);
        classUpdateDto.setLocation("111");
        classUpdateDto.setTime("222");
        classUpdateDto.setTimeData(null);
        classUpdateDto.setName("upd");
        courseService.updateClass(classUpdateDto);
        boolean d = false;
        for (CourseClass vo : courseMapper.getClasses(course.getId())) {
            if (vo.getName().equals("upd")) d = true;
        }
        assertThat(d).isTrue();
    }

    @Test
    @Order(8)
    public void addTeacher() {
        Course course = courseService.getCourseByName("测试课程test");
        List<CourseClass> classes = courseMapper.getClasses(course.getId());
        courseService.addTeacherToCourse(22L, course.getId());
        courseService.addTeacherToCourse(23L, course.getId());
        List<Long> teachers = courseService.getTeachers(course.getId());
        assertThat(teachers.contains(22L)).isTrue();
        assertThat(teachers.contains(23L)).isTrue();
    }

    @Test
    @Order(9)
    public void addTeacherToClass() {
        Course course = courseService.getCourseByName("测试课程test");
        List<CourseClass> classes = courseMapper.getClasses(course.getId());
        courseService.addTeacherToClass(22L, classes.get(0).getId());
        courseService.addTeacherToClass(23L, classes.get(1).getId());
        assertThat(courseService.getTeachersInClass(classes.get(0).getId()).contains(22L)).isTrue();
        assertThat(courseService.getTeachersInClass(classes.get(1).getId()).contains(23L)).isTrue();
    }

    @Test
    @Order(9)
    public void removeTeacherFromClass() {
        Course course = courseService.getCourseByName("测试课程test");
        List<CourseClass> classes = courseMapper.getClasses(course.getId());
        courseService.removeTeacherFromClass(22L, classes.get(0).getId());
        courseService.removeTeacherFromClass(23L, classes.get(1).getId());
        assertThat(courseService.getTeachersInClass(classes.get(0).getId()).contains(22L)).isFalse();
        assertThat(courseService.getTeachersInClass(classes.get(1).getId()).contains(23L)).isFalse();
    }

    @Test
    @Order(10)
    public void requestJoinTest() {
        Course course = courseService.getCourseByName("测试课程test");
        List<CourseClass> classes = courseMapper.getClasses(course.getId());
        courseService.requestJoinClass(1L, classes.get(0).getId());
    }

    @Test
    @Order(11)
    public void pendRequestJoinTest() {
        Course course = courseService.getCourseByName("测试课程test");
        List<CourseClass> classes = courseMapper.getClasses(course.getId());
        JoinClassRequestPageQueryDto dto = new JoinClassRequestPageQueryDto();
        dto.setPage(1);
        dto.setPageSize(10);
        dto.setCourseId(course.getId());
        PageResult pageResult = courseService.listJoinClassRequest(dto);
        JoinClassRequestVO vo = (JoinClassRequestVO) pageResult.getRecords().get(0);
        courseService.pendJoinClassRequest(vo.getId(), 1);
        assertThat(classMapper.checkStudent(1L, vo.getClassId())).isNotNull();
    }

    @Test
    @Order(12)
    public void createCourse1() {
        CourseCreateDto courseCreateDto = new CourseCreateDto();
        courseCreateDto.setCredit(new BigDecimal(4));
        courseCreateDto.setName("测试课程test1");
        courseCreateDto.setEvaluation("考试");
        courseCreateDto.setSyllabus("大纲");
        User teacher = userService.getByACCount("teacher");
        assertThat(courseService.createCourse(courseCreateDto, teacher.getId())).isNotNull();
    }

    @Test
    @Order(13)
    public void createCourse2() {
        CourseCreateDto courseCreateDto = new CourseCreateDto();
        courseCreateDto.setCredit(new BigDecimal(4));
        courseCreateDto.setName("测试课程test2");
        courseCreateDto.setEvaluation("考试");
        courseCreateDto.setSyllabus("大纲");
        User teacher = userService.getByACCount("teacher");
        assertThat(courseService.createCourse(courseCreateDto, teacher.getId())).isNotNull();
    }


    @Test
    @Order(14)
    public void createCourse3() {
        CourseCreateDto courseCreateDto = new CourseCreateDto();
        courseCreateDto.setCredit(new BigDecimal(4));
        courseCreateDto.setName("测试课程test3");
        courseCreateDto.setEvaluation("考试");
        courseCreateDto.setSyllabus("大纲");
        User teacher = userService.getByACCount("teacher");
        assertThat(courseService.createCourse(courseCreateDto, teacher.getId())).isNotNull();
    }


    @Test
    @Order(40)
    public void removeClass() {
        Course course = courseService.getCourseByName("测试课程test");

        for (CourseClass vo : courseMapper.getClasses(course.getId())) {
            adminService.deleteClass(vo.getId());
        }
        assertThat(courseMapper.getClasses(course.getId()).size()).isEqualTo(0);
    }

    @Test
    @Order(41)
    public void removeCourse() {
        Course course = courseService.getCourseByName("测试课程test");
        adminService.deleteCourse(course.getId());
        assertThat(courseService.getCourseById(course.getId())).isNull();
    }

    @Test
    @Order(42)
    public void removeCourse1() {
        Course course = courseService.getCourseByName("测试课程test1");
        adminService.deleteCourse(course.getId());
        assertThat(courseService.getCourseById(course.getId())).isNull();
    }

    @Test
    @Order(43)
    public void removeCourse2() {
        Course course = courseService.getCourseByName("测试课程test2");
        adminService.deleteCourse(course.getId());
        assertThat(courseService.getCourseById(course.getId())).isNull();
    }

    @Test
    @Order(44)
    public void removeCourse3() {
        Course course = courseService.getCourseByName("测试课程test3");
        adminService.deleteCourse(course.getId());
        assertThat(courseService.getCourseById(course.getId())).isNull();
    }
}
