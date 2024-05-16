package com.software.service.impl;

import com.software.constant.MessageConstant;
import com.software.entity.Event;
import com.software.exception.InvalidUserException;
import com.software.mapper.SubscribeMapper;
import com.software.mapper.UserMapper;
import com.software.service.EventService;
import com.software.service.SubscribeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubscribeServiceImpl implements SubscribeService {
    @Autowired
    SubscribeMapper subscribeMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    EventService eventService;
    @Override
    public void subscribe(Long subscriber, Long subscribed) {
        if (subscribed.equals(subscriber)) throw new InvalidUserException(MessageConstant.INVALID_USER);
        subscribeMapper.subscribe(subscribed, subscriber);
        userMapper.addSubscribers(subscribed);
        eventService.newEvent(Event.subscribe(subscriber, subscribed));
    }

    @Override
    public void unsubscribe(Long subscriber, Long subscribed) {
        if (subscribed.equals(subscriber)) throw new InvalidUserException(MessageConstant.INVALID_USER);
        int result = subscribeMapper.unsubscribe(subscribed, subscriber);
        if (result != 0)
            userMapper.subSubscribers(subscribed);
    }

    @Override
    public List<Long> getSubscribed(Long id) {
        return subscribeMapper.getSubscribed(id);
    }

    @Override
    public List<Long> getSubscribers(Long id) {
        return subscribeMapper.getSubscribers(id);
    }
}
