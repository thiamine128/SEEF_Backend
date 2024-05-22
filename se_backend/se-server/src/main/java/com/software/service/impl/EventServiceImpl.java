package com.software.service.impl;

import com.alibaba.fastjson.JSON;
import com.software.entity.Event;
import com.software.mapper.EventMapper;
import com.software.service.EventService;
import com.software.websocket.WebSocketServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventServiceImpl implements EventService {
    @Autowired
    private EventMapper eventMapper;

    @Override
    public void newEvent(Event event) {
        try {
            WebSocketServer.sendInfo("event" + JSON.toJSONString(event), event.getSubscriber());
        } catch (Exception ignored) {

        }
        eventMapper.newEvent(event);
    }

    @Override
    public List<Event> pullEvents(Long id) {
        List<Event> events = eventMapper.getEvents(id);
        eventMapper.removeEvents(id);
        return events;
    }

    @Override
    public Long getCnt(Long id) {
        return eventMapper.getCount(id);
    }
}
