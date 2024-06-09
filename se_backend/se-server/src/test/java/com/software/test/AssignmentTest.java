package com.software.test;

import com.software.constant.JwtClaimsConstant;
import com.software.dto.*;
import com.software.entity.User;
import com.software.exception.AssignmentOverdueException;
import com.software.service.AdminService;
import com.software.service.AssignmentService;
import com.software.service.CourseService;
import com.software.service.UserService;
import com.software.utils.BaseContext;
import com.software.vo.JoinClassRequestVO;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AssignmentTest {
    @Autowired
    private CourseService courseService;
    @Autowired
    private UserService userService;
    @Autowired
    private AssignmentService assignmentService;
    @Autowired
    private AdminService adminService;
    @Test
    @Order(0)
    public void setup() {
        HashMap<String, Object> usr = new HashMap<>();
        usr.put(JwtClaimsConstant.USER_ID, 2L);
        usr.put(JwtClaimsConstant.USER_ROLE, "teacher");
        BaseContext.setCurrentUser(usr);
    }
    @Test
    @Order(1)
    public void createTestClass() {
        CourseCreateDto courseCreateDto = new CourseCreateDto();
        courseCreateDto.setCredit(new BigDecimal(4));
        courseCreateDto.setName("测试课程test");
        courseCreateDto.setEvaluation("考试");
        courseCreateDto.setSyllabus("大纲");
        User teacher = userService.getByACCount("teacher");
        var course = courseService.createCourse(courseCreateDto, teacher.getId());
        assertThat(course).isNotNull();
        ClassCreateDto classCreateDto = new ClassCreateDto();
        classCreateDto.setCourseId(course.getId());
        classCreateDto.setLocation("1");
        classCreateDto.setName("class 1");
        classCreateDto.setTime("1");
        classCreateDto.setTimeData(null);
        var cls = courseService.addClass(classCreateDto);
        courseService.requestJoinClass(1L, cls.getId());
        JoinClassRequestPageQueryDto joinClassRequestPageQueryDto = new JoinClassRequestPageQueryDto();
        joinClassRequestPageQueryDto.setPageSize(10);
        joinClassRequestPageQueryDto.setPage(1);
        joinClassRequestPageQueryDto.setCourseId(course.getId());
        var ls = (List<JoinClassRequestVO>) courseService.listJoinClassRequest(joinClassRequestPageQueryDto).getRecords();
        courseService.pendJoinClassRequest(ls.get(0).getId(), 1);
    }
    @Test
    @Order(2)
    public void publishAssignment1() {
        AssignmentPublishDto assignmentPublishDto = new AssignmentPublishDto();
        ClassQueryDto classQueryDto = new ClassQueryDto();
        classQueryDto.setCourseId(courseService.getCourseByName("测试课程test").getId());
        var vo = courseService.getClasses(classQueryDto).get(0);
        assignmentPublishDto.setAttachmentUrl("no url");
        assignmentPublishDto.setTitle("Homework 1");
        assignmentPublishDto.setDueDate(new Date(System.currentTimeMillis() + 1000000));
        assignmentPublishDto.setClassId(vo.getId());
        assignmentPublishDto.setDescription("No desc");
        assignmentService.publishAssignment(assignmentPublishDto);
    }
    @Test
    @Order(3)
    public void publishAssignment2() {
        AssignmentPublishDto assignmentPublishDto = new AssignmentPublishDto();
        ClassQueryDto classQueryDto = new ClassQueryDto();
        classQueryDto.setCourseId(courseService.getCourseByName("测试课程test").getId());
        var vo = courseService.getClasses(classQueryDto).get(0);
        assignmentPublishDto.setAttachmentUrl("no url");
        assignmentPublishDto.setTitle("Homework 2");
        assignmentPublishDto.setDueDate(new Date(System.currentTimeMillis() - 1000));
        assignmentPublishDto.setClassId(vo.getId());
        assignmentPublishDto.setDescription("No desc");
        assignmentService.publishAssignment(assignmentPublishDto);
    }
    @Test
    @Order(4)
    public void successSubmitAssignment() {
        AssignmentSubmitDto assignmentSubmitDto = new AssignmentSubmitDto();
        ClassQueryDto classQueryDto = new ClassQueryDto();
        classQueryDto.setCourseId(courseService.getCourseByName("测试课程test").getId());
        var vo = courseService.getClasses(classQueryDto).get(0);
        var assignment = assignmentService.getAssignmentsInClass(vo.getId()).stream().filter(a -> a.getTitle().equals("Homework 1")).toList().get(0);
        assignmentSubmitDto.setAssignmentId(assignment.getId());
        assignmentSubmitDto.setAssignmentFile("no file");
        assignmentSubmitDto.setAssignmentContext("hello");
        assignmentService.submitAssignment(1L, assignmentSubmitDto);
    }
    @Test
    @Order(5)
    public void outdatedSubmitAssignment() {
        AssignmentSubmitDto assignmentSubmitDto = new AssignmentSubmitDto();
        ClassQueryDto classQueryDto = new ClassQueryDto();
        classQueryDto.setCourseId(courseService.getCourseByName("测试课程test").getId());
        var vo = courseService.getClasses(classQueryDto).get(0);
        var assignment = assignmentService.getAssignmentsInClass(vo.getId()).stream().filter(a -> a.getTitle().equals("Homework 2")).toList().get(0);
        assignmentSubmitDto.setAssignmentId(assignment.getId());
        assignmentSubmitDto.setAssignmentFile("no file");
        assignmentSubmitDto.setAssignmentContext("hello");
        assertThatThrownBy(() -> {
            assignmentService.submitAssignment(1L, assignmentSubmitDto);
        }).isInstanceOf(AssignmentOverdueException.class);

    }
    @Test
    @Order(6)
    public void deadlineTest() {
        ClassQueryDto classQueryDto = new ClassQueryDto();
        classQueryDto.setCourseId(courseService.getCourseByName("测试课程test").getId());
        var vo = courseService.getClasses(classQueryDto).get(0);
        var ls = assignmentService.getAllAssignments(new AssignmentQueryDto(false, 1L));
        for (var a : ls) {
            System.out.println(a.getTitle() + " " + a.getDueDate().toString());
            assertThat(a.getDueDate().after(new Date())).isTrue();
        }
    }
    @Test
    @Order(7)
    public void judgeAssignmentTest() {
        HomeWorkFeedBackDTO homeWorkFeedBackDTO = new HomeWorkFeedBackDTO();
        ClassQueryDto classQueryDto = new ClassQueryDto();
        classQueryDto.setCourseId(courseService.getCourseByName("测试课程test").getId());
        var vo = courseService.getClasses(classQueryDto).get(0);
        var assignment = assignmentService.getAssignmentsInClass(vo.getId()).stream().filter(a -> a.getTitle().equals("Homework 1")).toList().get(0);
        homeWorkFeedBackDTO.setAssignmentId(assignment.getId());
        homeWorkFeedBackDTO.setStudentId(1L);
        homeWorkFeedBackDTO.setGrade(100.0f);
        homeWorkFeedBackDTO.setFeedback("Good");
        assignmentService.markHw(homeWorkFeedBackDTO);

        assertThat(assignmentService.getStudentAssignments(assignment.getId()).get(0).getFeedback()).isEqualTo("Good");
    }
    @Test
    @Order(8)
    public void listAssignmentTest() {
        ClassQueryDto classQueryDto = new ClassQueryDto();
        classQueryDto.setCourseId(courseService.getCourseByName("测试课程test").getId());
        var vo = courseService.getClasses(classQueryDto).get(0);
        var assignments = assignmentService.getAssignmentsInClass(vo.getId());
        assertThat(assignments.size()).isEqualTo(2);
    }

    @Test
    @Order(9)
    public void deleteTestCourse() {
        adminService.deleteCourse(courseService.getCourseByName("测试课程test").getId());
    }
}
