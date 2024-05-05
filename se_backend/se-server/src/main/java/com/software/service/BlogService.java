package com.software.service;

import com.software.exception.NoSuchTopicException;

public interface BlogService {
    void create(String title, String content, Long topicId);
}
