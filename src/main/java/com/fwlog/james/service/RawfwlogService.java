package com.fwlog.james.service;

import com.fwlog.james.entity.Rawfwlog;
import com.fwlog.james.repository.RawfwlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class RawfwlogService {
    @Autowired
    private RawfwlogRepository rawfwlogRepository;

    public Rawfwlog save(Rawfwlog rawfwlog){
        try{
            rawfwlogRepository.save(rawfwlog);
        }catch (Exception e){}
        return rawfwlog;
    }
}
