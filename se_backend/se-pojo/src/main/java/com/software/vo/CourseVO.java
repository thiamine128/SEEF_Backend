package com.software.vo;

import com.software.entity.Course;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CourseVO {
    private Long id;
    private String name;
    private String introduction;
    private String cover;
    private BigDecimal credit;
    private String syllabus;
    private String evaluation;
    private Boolean hasPermission;

    public static CourseVO fromCourse(Course course, boolean hasPermission) {
        return new CourseVOBuilder()
                .id(course.getId())
                .name(course.getName())
                .introduction(course.getIntroduction())
                .cover(course.getCover())
                .credit(course.getCredit())
                .syllabus(course.getSyllabus())
                .evaluation(course.getEvaluation())
                .hasPermission(hasPermission).build();
    }
}
