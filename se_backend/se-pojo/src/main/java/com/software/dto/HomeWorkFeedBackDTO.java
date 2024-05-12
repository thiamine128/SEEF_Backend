package com.software.dto;

import lombok.Data;

/**
 * @author
 * @Description：
 * @date
 */
@Data
public class HomeWorkFeedBackDTO {
    Long studentId;
    Long assignmentId;
    Float grade;
    String feedback;
}
