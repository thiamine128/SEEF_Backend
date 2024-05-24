package com.software.vo;

import lombok.Builder;
import lombok.Data;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.software.entity.Blog;

@Data
@Builder
public class BlogPreviewVO {
    private Long id;
    private String title;
    private String preview;
    private List<String> tags;
    private String thumbNum;
    private String favourNum;
    private Long userId;
    private Date createTime;
    private Date updateTIme;
    private boolean isDeleted;
    private Long topicId;
    private Long isLike;
    private Long isFavor;
    private double popularity;

    public static BlogPreviewVO fromBlog(Blog blog, int len, Long isLike, Long favor) {
        int sub = Math.min(blog.getContent().length(), len);
        String tags = blog.getTags();
        if (tags != null && !tags.isEmpty()) {
            tags = blog.getTags().substring(1);
        }
        double popularity = blog.getPopularity();
        double delta = (System.currentTimeMillis() - blog.getCreateTime().getTime()) / 60 / 60 / 1000;
        popularity *= Math.exp(-delta / 10000);
        return new BlogPreviewVOBuilder()
                .id(blog.getId())
                .title(blog.getTitle())
                .preview(blog.getContent().substring(0, sub))
                .tags(tags == null ? List.of() : Arrays.stream(tags.split(";")).toList())
                .thumbNum(blog.getThumbNum())
                .favourNum(blog.getFavourNum())
                .userId(blog.getUserId())
                .createTime(blog.getCreateTime())
                .updateTIme(blog.getUpdateTIme())
                .isDeleted(blog.isDeleted())
                .topicId(blog.getTopicId())
                .isLike(isLike)
                .isFavor(favor)
                .popularity(popularity).build();
    }
}
