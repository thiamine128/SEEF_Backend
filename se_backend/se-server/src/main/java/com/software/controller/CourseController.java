package com.software.controller;

import com.software.dto.CourseCreateDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "课程接口")
@RestController
@RequestMapping("/api/course")
@Slf4j
public class CourseController {
    @PostMapping("/create")
    @Operation(summary = "创建课程")
    public void createCourse(@RequestBody CourseCreateDto courseCreateDto) {

    }
}
