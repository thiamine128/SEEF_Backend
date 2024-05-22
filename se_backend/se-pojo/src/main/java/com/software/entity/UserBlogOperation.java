package com.software.entity;

import lombok.Data;

/**
 * @author
 * @Description：
 * @date
 */
@Data
public class UserBlogOperation {
    private Integer id;
    private Integer userId;
    private Integer  blogId;
    private Integer operationType;

    private Integer value;
}
