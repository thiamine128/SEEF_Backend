package com.software.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssignmentVO {
    private Long id;
    private String title;
    private Date dueDate;
    private Long classId;
    private Long courseId;
    private Date submissionTime;
    private String courseName;
}
