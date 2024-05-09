package com.software.service;

import com.software.dto.BlogCreateDTO;
import com.software.dto.CommentPageQueryDto;
import com.software.entity.Blog;
import com.software.exception.NoSuchTopicException;
import com.software.result.PageResult;

public interface BlogService {
    void create(BlogCreateDTO blogCreateDTO);
    Blog getDetail(Long blogId);
    PageResult viewComments(CommentPageQueryDto commentPageQueryDto);

    void deleteblog(Long blogId);

    void deleteMyBlog(Long blogId, Long uid);
}
