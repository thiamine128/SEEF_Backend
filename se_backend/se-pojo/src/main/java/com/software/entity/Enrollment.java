package com.software.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author
 * @Description：
 * @date
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Enrollment {

    Long studentId;
    Long grade;
    Long classId;
    Long courseId;
}
