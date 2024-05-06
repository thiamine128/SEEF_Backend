package com.software.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reply {
    private Long id;
    private String content;
    private Date createTime;
    private Date updateTime;
    private Long commentId;
    private Long userId;
    private Long to;
}
