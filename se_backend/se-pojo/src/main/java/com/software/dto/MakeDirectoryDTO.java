package com.software.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author
 * @Description：
 * @date
 */
@Data
@AllArgsConstructor
public class MakeDirectoryDTO {
    Long courseId;

    String currentDirectory;

    String newDirectory;
}
