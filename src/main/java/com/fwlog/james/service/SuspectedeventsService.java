package com.fwlog.james.service;

import com.fwlog.james.entity.Suspectedevents;
import com.fwlog.james.repository.SuspectedeventsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SuspectedeventsService {
    @Autowired
    private SuspectedeventsRepository repository;

    public Suspectedevents save(Suspectedevents suspectedevents){
        repository.save(suspectedevents);
        return suspectedevents;
    }
}
