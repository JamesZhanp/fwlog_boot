package com.fwlog.james.service;

import com.fwlog.james.entity.Events;
import com.fwlog.james.repository.EventsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

@Service
public class EventsService {
    @Autowired
    private EventsRepository eventsRepository;
    public Events save(Events events){
        eventsRepository.save(events);
        return events;
    }

    public List<Events> getAll(){
        List<Events> eventsList = eventsRepository.findAll();
        return eventsList;
    }
}
