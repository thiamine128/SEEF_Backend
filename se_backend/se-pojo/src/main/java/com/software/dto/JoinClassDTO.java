package com.software.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author
 * @Description：老师将学生加入课程班级
 * @date
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JoinClassDTO {
    Long userId;
    Long classId;
    Long courseId;

}
