package com.software.dto;

import lombok.Data;

@Data
public class AssignmentSubmitDto {
    private Long assignmentId;
    private String assignmentContext;
    private String assignmentFile;
}
