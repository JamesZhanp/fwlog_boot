package com.fwlog.james.service;

import com.fwlog.james.entity.Events;
import com.fwlog.james.repository.EventsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventsService {
    @Autowired
    private EventsRepository eventsRepository;
    public Events save(Events events){
        eventsRepository.save(events);
        return events;
    }
}
