package com.software.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author
 * @Description：
 * @date
 */
@Data
@NoArgsConstructor
public class AddBatchTADTO {
    private Long studentId;
    private  Long classId;
    private Long courseId;
}
