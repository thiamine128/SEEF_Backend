package com.software.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.software.constant.JwtClaimsConstant;
import com.software.constant.MessageConstant;
import com.software.dto.BlogCreateDTO;
import com.software.dto.CommentPageQueryDto;
import com.software.entity.Blog;
import com.software.entity.Comment;
import com.software.exception.EmptyTagException;
import com.software.exception.InvalidCharacterException;
import com.software.exception.PermissionDeniedException;
import com.software.mapper.BlogMapper;
import com.software.mapper.CommentMapper;
import com.software.mapper.ReplyMapper;
import com.software.result.PageResult;
import com.software.service.BlogService;
import com.software.utils.BaseContext;
import com.software.vo.CommentVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BlogServiceImpl implements BlogService {
    @Autowired
    private BlogMapper blogMapper;
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private ReplyMapper replyMapper;

    @Override
    public void create(BlogCreateDTO blogCreateDTO) {
        Map<String,Object> currentUser = BaseContext.getCurrentUser();
        Long id =(long) currentUser.get(JwtClaimsConstant.USER_ID);
        blogCreateDTO.getTags().forEach(str -> {
            if (str.contains(";")) {
                throw new InvalidCharacterException(";", str);
            }
            if (str.isEmpty()) {
                throw new EmptyTagException(MessageConstant.EMPTY_TAG);
            }
        });
        String tags = blogCreateDTO.getTags().stream().map(str -> str + ";").collect(Collectors.joining());
        blogMapper.createBlog(blogCreateDTO.getTitle(), blogCreateDTO.getContext(), id, blogCreateDTO.getTopicId(), tags);
    }

    @Override
    public Blog getDetail(Long blogId) {
        return blogMapper.getBlog(blogId);
    }

    @Override
    public PageResult viewComments(CommentPageQueryDto commentPageQueryDto) {
        PageHelper.startPage(commentPageQueryDto.getPage(), commentPageQueryDto.getPageSize());
        Page page = (Page) commentMapper.getComments(commentPageQueryDto.getBlogId());
        List list = page.getResult().stream().map(comment -> CommentVO.fromComment((Comment) comment, replyMapper.getReplies(((Comment) comment).getId()))).toList();
        return new PageResult(page.getTotal(), list);
    }

    @Override
    public void deleteblog(Long blogId) {
        blogMapper.deleteBlog(blogId);
    }
}
