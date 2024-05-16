package com.software.vo;

import lombok.Builder;
import lombok.Data;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.software.entity.Blog;

@Data
@Builder
public class BlogVO {
    private Long id;
    private String title;
    private String context;
    private List<String> tags;
    private String thumbNum;
    private String favourNum;
    private Long userId;
    private Date createTime;
    private Date updateTIme;
    private boolean isDeleted;
    private Long topicId;
    private Boolean isLike;
    private Boolean isFavor;

    public static BlogVO fromBlog(Blog blog,Boolean isLike,Boolean isFavor) {
        return new BlogVOBuilder()
                .id(blog.getId())
                .title(blog.getTitle())
                .context(blog.getContent())
                .tags(blog.getTags() == null ? List.of() : Arrays.stream(blog.getTags().split(";")).toList())
                .thumbNum(blog.getThumbNum())
                .favourNum(blog.getFavourNum())
                .userId(blog.getUserId())
                .createTime(blog.getCreateTime())
                .updateTIme(blog.getUpdateTIme())
                .isDeleted(blog.isDeleted())
                .topicId(blog.getTopicId())
                .isLike(isLike)
                .isFavor(isFavor).build();
    }
}
