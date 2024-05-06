package com.software.vo;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
import com.software.entity.Blog;

@Data
@Builder
public class BlogSummaryVO {
    private Long id;
    private String title;
    private String summary;
    private String tags;
    private String thumbNum;
    private String favourNum;
    private Long userId;
    private Date createTime;
    private Date updateTIme;
    private boolean isDeleted;
    private Long topicId;

    public static BlogSummaryVO fromBlog(Blog blog, int len) {
        int sub = Math.min(blog.getContent().length(), len);
        return new BlogSummaryVOBuilder()
                .id(blog.getId())
                .title(blog.getTitle())
                .summary(blog.getContent().substring(0, sub))
                .tags(blog.getTags())
                .thumbNum(blog.getThumbNum())
                .favourNum(blog.getFavourNum())
                .userId(blog.getUserId())
                .createTime(blog.getCreateTime())
                .updateTIme(blog.getUpdateTIme())
                .isDeleted(blog.isDeleted())
                .topicId(blog.getTopicId()).build();
    }
}
