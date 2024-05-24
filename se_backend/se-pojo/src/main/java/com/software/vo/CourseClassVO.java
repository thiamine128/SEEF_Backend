package com.software.vo;

import com.software.entity.CourseClass;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CourseClassVO {
    private Long id;
    private Long courseId;
    private String name;
    private String time;
    private String location;
    private List<Long> teachers;

    public static CourseClassVO fromCourseClass(CourseClass courseClass, List<Long> teachers) {
        return new CourseClassVOBuilder().id(courseClass.getId())
                .courseId(courseClass.getCourseId())
                .name(courseClass.getName())
                .time(courseClass.getTime())
                .location(courseClass.getLocation())
                .teachers(teachers).build();
    }
}