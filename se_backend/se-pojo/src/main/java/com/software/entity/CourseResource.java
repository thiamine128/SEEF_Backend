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
public class CourseResource {
    Long id;

    String url;

    Long courseId;

    String name;
}
