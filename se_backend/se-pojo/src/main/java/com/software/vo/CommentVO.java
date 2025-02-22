package com.software.vo;

import com.software.entity.Comment;
import com.software.entity.Reply;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Builder
public class CommentVO {
    private Long id;
    private String content;
    private Date createTime;
    private Date updateTime;
    private Long blogId;
    private Long userId;
    private List<Reply> replies;

    public static CommentVO fromComment(Comment comment, List<Reply> replies) {
        return new CommentVOBuilder().id(comment.getId())
                .content(comment.getContent())
                .createTime(comment.getCreateTime())
                .updateTime(comment.getCreateTime())
                .blogId(comment.getBlogId())
                .userId(comment.getUserId())
                .replies(replies).build();
    }
}
