package com.software.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author
 * @Description：
 * @date
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentAssignmentDTO {
    private String studentId;
    private Long assignmentId;
    private String assignmentFile;
    private double grade;
    private Date submissionTime;
    private String attachmentContext;
    private String feedback;
}
