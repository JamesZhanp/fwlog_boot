package com.fwlog.james.service;

import com.fwlog.james.entity.Advice;
import com.fwlog.james.repository.AdviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdviceService {
    @Autowired
    private AdviceRepository adviceRepository;
    public String findByType(String type){
        List<Advice> advices = adviceRepository.findAdviceByType(type);
        if (advices.size() > 0){
            return advices.get(0).getAdvice();
        }else{
            System.out.println("此类为全新的攻击方式，请添加到数据库");
            return null;
        }
    }
}
