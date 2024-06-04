package com.software.vo;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.software.dto.ClassTime;
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
    private Boolean hasPermission;
    private JSONArray timeData;
    private String courseName;

    public static CourseClassVO fromCourseClass(CourseClass courseClass, List<Long> teachers, Boolean hasPermission, String courseName) {
        return new CourseClassVOBuilder().id(courseClass.getId())
                .courseId(courseClass.getCourseId())
                .name(courseClass.getName())
                .time(courseClass.getTime())
                .location(courseClass.getLocation())
                .teachers(teachers)
                .hasPermission(hasPermission)
                .timeData(courseClass.getTimeData())
                .courseName(courseName).build();
    }
}