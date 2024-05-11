package com.software.dto;

import lombok.Data;

import java.util.Date;

@Data
public class AssignmentPublishDto {
    private String title;
    private String description;
    private Date dueDate;
    private String attachmentUrl;
    private Long classId;
}
