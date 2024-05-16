package com.software.service;

import com.software.dto.BlogCreateDTO;
import com.software.dto.CommentPageQueryDto;
import com.software.entity.Blog;
import com.software.result.PageResult;

import java.util.List;

public interface BlogService {
    void create(BlogCreateDTO blogCreateDTO);
    Blog getDetail(Long blogId);
    PageResult viewComments(CommentPageQueryDto commentPageQueryDto);

    void deleteblog(Long blogId);
    void requestImage();

    void like(Long blogId);

    Boolean isLike(Long blogId);

    void favor(Long blogId);
    Boolean isFavor(Long blogId);

    List<Long> getfavorBlogIds(Long id);

    PageResult listFavor(List<Long> ids,int page,int pageSize,int previewLength);
}
