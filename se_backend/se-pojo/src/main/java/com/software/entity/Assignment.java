package com.software.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Assignment {
    private Long id;
    private String title;
    private String description;
    private Date dueDate;
    private String attachmentUrl;
    private Long classId;
}
