package com.software.service;

import java.util.List;

public interface SubscribeService {
    void subscribe(Long subscriber, Long subscribed);
    void unsubscribe(Long subscriber, Long subscribed);
    List<Long> getSubscribed(Long id);
    List<Long> getSubscribers(Long id);
}
