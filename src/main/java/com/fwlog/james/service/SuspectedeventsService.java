package com.fwlog.james.service;

import com.fwlog.james.entity.Suspectedevents;
import com.fwlog.james.repository.SuspectedeventsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SuspectedeventsService {
    @Autowired
    private SuspectedeventsRepository repository;

    public Suspectedevents save(Suspectedevents suspectedevents){
        repository.save(suspectedevents);
        return suspectedevents;
    }

    public List<Suspectedevents> findAll(){
        List<Suspectedevents> suspectedevents = repository.findAll();
        return suspectedevents;
    }

    public List<Suspectedevents> findByStimeAndEtime(Date stime,Date etime){
        List<Suspectedevents> suspectedevents = repository.findByStimeAfterAndEtimeBefore(stime, etime);
        return suspectedevents;
    }

}
