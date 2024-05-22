package com.software.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @author
 * @Description：
 * @date
 */
@Data
@AllArgsConstructor
public class TopicPageQueryDTO implements Serializable {
    private int page;

    private int pageSize;

    private String name;


}
