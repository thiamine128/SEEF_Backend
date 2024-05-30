package com.software.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentAssignment {
    private Long studentId;
    private Long assignmentId;
    private String assignmentFile;
    private double grade;
    private Date submissionTime;
    private String attachmentContext;
    private String feedback;
}