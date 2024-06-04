package com.software.entity;

import lombok.Data;

@Data
public class JoinClassRequest {
    private String id;
    private Long studentId;
    private Long classId;
    private Integer state;
}
