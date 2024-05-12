package com.software.service;

import com.software.dto.CommentCreateDto;

public interface CommentService {
    void makeComment(CommentCreateDto commentCreateDto);
    void deleteComment(Long id);
}
