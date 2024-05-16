package com.software.service;

import com.software.entity.Event;

import java.util.List;

public interface EventService {
    void newEvent(Event event);
    List<Event> pullEvents(Long id);
    Long getCnt(Long id);
}
