package com.fwlog.james.service;

import com.fwlog.james.entity.Destip;
import com.fwlog.james.repository.DestipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DestipService {
    @Autowired
    private DestipRepository destipRepository;

    public Destip save(Destip destip){
        destipRepository.save(destip);
        return destip;
    }

    public List<Destip> findAll(){
        List<Destip> destipList = new ArrayList<>();
        destipList = destipRepository.findAll();
        return destipList;
    }
}
