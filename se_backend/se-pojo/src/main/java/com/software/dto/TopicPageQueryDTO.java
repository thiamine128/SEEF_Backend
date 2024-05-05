package com.software.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author
 * @Description：
 * @date
 */
@Data
public class TopicPageQueryDTO implements Serializable {
    private int page;

    private int pageSize;

    private String name;


}
