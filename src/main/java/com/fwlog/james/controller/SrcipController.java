package com.fwlog.james.controller;

import com.fwlog.james.entity.Srcip;
import com.fwlog.james.mode.IPEntity;
import com.fwlog.james.service.SrcipService;
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
public class SrcipController  {
    @Autowired
    private SrcipService srcipService;

    private Map<Long,List<Srcip>> listToMap(List<Srcip> srcips){
        Map<Long,List<Srcip>> map = new HashMap<>();
        for (Srcip srcip:srcips){
            List<Srcip> tempList = map.get(srcip.getValue());
            if (tempList == null){
                tempList = new ArrayList<>();
                tempList.add(srcip);
                map.put(srcip.getValue(),tempList);
            }else {
                tempList.add(srcip);
            }
        }
        return map;
    }

    @RequestMapping(value = "/getSrcip",method = RequestMethod.GET)
    public List<IPEntity> getSrcip(){
        int totalNum = 0;
        List<Srcip> srcipList = srcipService.findAll();
        List<IPEntity> ipEntities = new ArrayList<>();
        Map<Long,List<Srcip>> srcMap = listToMap(srcipList);
        for (Long value:srcMap.keySet()){
            List<Srcip> srcipList1 = srcMap.get(value);
//            统计总的访问次数
            for (Srcip srcip:srcipList1){
                totalNum += srcip.getNumber();
            }
            IPEntity ipEntity = new IPEntity();
            ipEntity.setIpAdress(new IPChangeUtil().ipLongToStr(srcipList1.get(0).getValue()));
            ipEntity.setAccessNum(totalNum);
            totalNum = 0;
            ipEntities.add(ipEntity);
        }
        return ipEntities;
    }

}
