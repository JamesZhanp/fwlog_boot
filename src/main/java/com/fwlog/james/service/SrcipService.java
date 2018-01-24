package com.fwlog.james.service;

import com.fwlog.james.entity.Srcip;
import com.fwlog.james.repository.SrcipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SrcipService {
    @Autowired
    private SrcipRepository srcipRepository;

    public Srcip save(Srcip srcip){
        try{
            srcipRepository.save(srcip);
        }catch (Exception e){

        }
        return srcip;
    }

    public List<Srcip> findAll(){
        List<Srcip> srcipList =  srcipRepository.findAll();
        return srcipList;
    }
}
