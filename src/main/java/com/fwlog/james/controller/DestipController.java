package com.fwlog.james.controller;

import com.fwlog.james.entity.Destip;
import com.fwlog.james.mode.IPEntity;
import com.fwlog.james.service.DestipService;
import com.fwlog.james.utils.IPChangeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class DestipController {
    @Autowired
    private DestipService destipService;

    private Map<Long,List<Destip>> listToMap(List<Destip> destips){
        Map<Long,List<Destip>> map = new HashMap<>();
        for (Destip destip:destips){
            List<Destip> tempList = map.get(destip.getValue());
            if (tempList == null){
                tempList = new ArrayList<>();
                tempList.add(destip);
                map.put(destip.getValue(),tempList);
            }else {
                tempList.add(destip);
            }
        }
        return map;
    }

    @RequestMapping(value = "/getDestIP",method = RequestMethod.GET)
    public List<IPEntity> getDestip(){
        int totalNum = 0;
        List<Destip> destipList = destipService.findAll();
        List<IPEntity> ipEntities = new ArrayList<>();
        Map<Long,List<Destip>> destIpMap = listToMap(destipList);
        for (Long value:destIpMap.keySet()){
            List<Destip> destipList1 = destIpMap.get(value);
            for (Destip destip : destipList1){
                totalNum += destip.getNumber();
            }
            IPEntity ipEntity = new IPEntity(new IPChangeUtil().ipLongToStr(destipList1.get(0).getValue()),totalNum);
            totalNum = 0;
            ipEntities.add(ipEntity);
        }
        return ipEntities;
    }
}
