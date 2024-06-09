package com.software.test;

import com.software.constant.JwtClaimsConstant;
import com.software.dto.CommentCreateDto;
import com.software.dto.CommentPageQueryDto;
import com.software.dto.ReplyCreateDto;
import com.software.result.PageResult;
import com.software.service.BlogService;
import com.software.service.CommentService;
import com.software.service.ReplyService;
import com.software.utils.BaseContext;
import com.software.vo.CommentVO;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CommentReplyTest {
    @Autowired
    private CommentService commentService;
    @Autowired
    private ReplyService replyService;
    @Autowired
    private BlogService blogService;
    @Test
    @Order(0)
    public void setup() {
        HashMap<String, Object> usr = new HashMap<>();
        usr.put(JwtClaimsConstant.USER_ID, 1L);
        usr.put(JwtClaimsConstant.USER_ROLE, "student");
        BaseContext.setCurrentUser(usr);
    }
    @Test
    @Order(1)
    public void makeComment1() {
        CommentCreateDto commentCreateDto = new CommentCreateDto();
        commentCreateDto.setBlogId(1L);
        commentCreateDto.setContent("Comment 1 from test");
        commentService.makeComment(commentCreateDto);
        CommentPageQueryDto commentPageQueryDto = new CommentPageQueryDto(1, 100, 1L);
        var ls = (List<CommentVO>) blogService.viewComments(commentPageQueryDto).getRecords();
        int h = 0;
        for (var c : ls) {
            if (c.getContent().equals("Comment 1 from test")) {
                h = 1;
            }
        }
        assertThat(h).isEqualTo(1);
    }
    @Test
    @Order(2)
    public void makeComment2() {
        CommentCreateDto commentCreateDto = new CommentCreateDto();
        commentCreateDto.setBlogId(1L);
        commentCreateDto.setContent("Comment 2 from test");
        commentService.makeComment(commentCreateDto);
        CommentPageQueryDto commentPageQueryDto = new CommentPageQueryDto(1, 100, 1L);
        var ls = (List<CommentVO>) blogService.viewComments(commentPageQueryDto).getRecords();
        int h = 0;
        for (var c : ls) {
            if (c.getContent().equals("Comment 2 from test")) {
                h = 1;
            }
        }
        assertThat(h).isEqualTo(1);
    }
    @Test
    @Order(3)
    public void makeReply1() {
        CommentPageQueryDto commentPageQueryDto = new CommentPageQueryDto(1, 100, 1L);
        var ls = (List<CommentVO>) blogService.viewComments(commentPageQueryDto).getRecords();
        Long id = 0L;
        for (var c : ls) {
            if (c.getContent().equals("Comment 1 from test")) {
                id = c.getId();
            }
        }
        assertThat(id).isNotZero();
        ReplyCreateDto replyCreateDto = new ReplyCreateDto();
        replyCreateDto.setCommentId(id);
        replyCreateDto.setContent("Reply to comment");
        replyCreateDto.setTo(null);
        replyService.makeReply(replyCreateDto);
        ls = (List<CommentVO>) blogService.viewComments(commentPageQueryDto).getRecords();
        int h = 0;
        for (var c : ls) {
            if (c.getContent().equals("Comment 1 from test")) {
                var replies = c.getReplies();
                for (var r : replies) {
                    if (r.getContent().equals("Reply to comment")) {
                        h = 1;
                    }
                }
            }
        }
        assertThat(h).isEqualTo(1);
    }
    @Test
    @Order(4)
    public void makeReply2() {
        CommentPageQueryDto commentPageQueryDto = new CommentPageQueryDto(1, 100, 1L);
        var ls = (List<CommentVO>) blogService.viewComments(commentPageQueryDto).getRecords();
        Long id = 0L;
        for (var c : ls) {
            if (c.getContent().equals("Comment 1 from test")) {
                id = c.getId();
            }
        }
        assertThat(id).isNotZero();
        ReplyCreateDto replyCreateDto = new ReplyCreateDto();
        replyCreateDto.setCommentId(id);
        replyCreateDto.setContent("Reply to reply");
        replyCreateDto.setTo(1L);
        replyService.makeReply(replyCreateDto);
        ls = (List<CommentVO>) blogService.viewComments(commentPageQueryDto).getRecords();
        int h = 0;
        for (var c : ls) {
            if (c.getContent().equals("Comment 1 from test")) {
                var replies = c.getReplies();
                for (var r : replies) {
                    if (r.getContent().equals("Reply to reply")) {
                        h = 1;
                    }
                }
            }
        }
        assertThat(h).isEqualTo(1);
    }
    @Test
    @Order(5)
    public void deleteReply1() {
        CommentPageQueryDto commentPageQueryDto = new CommentPageQueryDto(1, 100, 1L);
        var ls = (List<CommentVO>) blogService.viewComments(commentPageQueryDto).getRecords();
        Long id = 0L;
        for (var c : ls) {
            if (c.getContent().equals("Comment 1 from test")) {
                id = c.getId();
            }
        }
        assertThat(id).isNotZero();
        for (var c : ls) {
            if (c.getContent().equals("Comment 1 from test")) {
                var replies = c.getReplies();
                for (var r : replies) {
                    if (r.getContent().equals("Reply to comment")) {
                        id = r.getId();
                    }
                }
            }
        }
        replyService.deleteReply(id);
        ls = (List<CommentVO>) blogService.viewComments(commentPageQueryDto).getRecords();
        int h  = 0;
        for (var c : ls) {
            if (c.getContent().equals("Comment 1 from test")) {
                var replies = c.getReplies();
                for (var r : replies) {
                    if (r.getContent().equals("Reply to comment")) {
                        h = 1;
                    }
                }
            }
        }
        assertThat(h).isEqualTo(0);
    }
    @Test
    @Order(6)
    public void deleteReply2() {
        CommentPageQueryDto commentPageQueryDto = new CommentPageQueryDto(1, 100, 1L);
        var ls = (List<CommentVO>) blogService.viewComments(commentPageQueryDto).getRecords();
        Long id = 0L;
        for (var c : ls) {
            if (c.getContent().equals("Comment 1 from test")) {
                id = c.getId();
            }
        }
        assertThat(id).isNotZero();
        for (var c : ls) {
            if (c.getContent().equals("Comment 1 from test")) {
                var replies = c.getReplies();
                for (var r : replies) {
                    if (r.getContent().equals("Reply to reply")) {
                        id = r.getId();
                    }
                }
            }
        }
        replyService.deleteReply(id);
        ls = (List<CommentVO>) blogService.viewComments(commentPageQueryDto).getRecords();
        int h  = 0;
        for (var c : ls) {
            if (c.getContent().equals("Comment 1 from test")) {
                var replies = c.getReplies();
                for (var r : replies) {
                    if (r.getContent().equals("Reply to reply")) {
                        h = 1;
                    }
                }
            }
        }
        assertThat(h).isEqualTo(0);
    }
    @Test
    @Order(7)
    public void deleteComment1() {
        CommentPageQueryDto commentPageQueryDto = new CommentPageQueryDto(1, 100, 1L);
        var ls = (List<CommentVO>) blogService.viewComments(commentPageQueryDto).getRecords();
        Long id = 0L;
        for (var c : ls) {
            if (c.getContent().equals("Comment 1 from test")) {
                id = c.getId();
            }
        }
        commentService.deleteComment(id);
        int h = 0;
        ls = (List<CommentVO>) blogService.viewComments(commentPageQueryDto).getRecords();
        for (var c : ls) {
            if (c.getContent().equals("Comment 1 from test")) {
                h = 1;
            }
        }
        assertThat(h).isEqualTo(0);
    }
    @Test
    @Order(8)
    public void deleteComment2() {
        CommentPageQueryDto commentPageQueryDto = new CommentPageQueryDto(1, 100, 1L);
        var ls = (List<CommentVO>) blogService.viewComments(commentPageQueryDto).getRecords();
        Long id = 0L;

        for (var c : ls) {
            if (c.getContent().equals("Comment 2 from test")) {
                id = c.getId();
            }
        }
        commentService.deleteComment(id);
        int h = 0;
        ls = (List<CommentVO>) blogService.viewComments(commentPageQueryDto).getRecords();
        for (var c : ls) {
            if (c.getContent().equals("Comment 2 from test")) {
                h = 1;
            }
        }
        assertThat(h).isEqualTo(0);
    }
}
