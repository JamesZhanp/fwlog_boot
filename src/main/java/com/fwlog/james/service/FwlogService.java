package com.fwlog.james.service;

import com.fwlog.james.entity.Fwlog;
import com.fwlog.james.repository.FwlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FwlogService {
    @Autowired
    private FwlogRepository fwlogRepository;

    public Fwlog save(Fwlog fwlog){
        try{
            fwlogRepository.save(fwlog);
        }catch (Exception e){
//            e.printStackTrace();
        }
        return fwlog;
    }

    public List<Fwlog> getAll(){
        List<Fwlog> fwlogs = fwlogRepository.findAll();
        return fwlogs;
    }
}
