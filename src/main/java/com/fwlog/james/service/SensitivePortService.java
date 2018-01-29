package com.fwlog.james.service;

import com.fwlog.james.entity.Sensitiveport;
import com.fwlog.james.repository.SensitivePortRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SensitivePortService {
    @Autowired
    private SensitivePortRepository sensitivePortRepository;
    public List<Sensitiveport> findByPort(int port){
        List<Sensitiveport> sensitiveports = sensitivePortRepository.findByPort(port);
        return sensitiveports;
    }
}
