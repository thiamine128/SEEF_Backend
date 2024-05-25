package com.software.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AssignmentQueryDto {
    private boolean showOutdated;
    private Long userId;
}
