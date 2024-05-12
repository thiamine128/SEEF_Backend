package com.software.service;

import com.software.dto.CommentCreateDto;
import com.software.entity.Comment;

public interface CommentService {
    void makeComment(CommentCreateDto commentCreateDto);
    void deleteComment(Long id);
    Comment getComment(Long id);
}
