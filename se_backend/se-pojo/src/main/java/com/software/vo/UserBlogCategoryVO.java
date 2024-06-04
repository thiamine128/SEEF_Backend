package com.software.vo;

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
public class UserBlogCategoryVO {
    String categoryName;
    Long userId;
    String userName;
}
