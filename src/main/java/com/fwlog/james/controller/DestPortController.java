package com.fwlog.james.controller;

import com.fwlog.james.entity.Fwlog;
import com.fwlog.james.mode.PortEntity;
import com.fwlog.james.service.FwlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class DestPortController {
    @Autowired
    private FwlogService fwlogService;

    private Map<Integer,List<Fwlog>> listToMap(List<Fwlog> fwlogList){
        Map<Integer,List<Fwlog>> map = new HashMap<>();
        for (Fwlog fwlog:fwlogList){
            List<Fwlog> tempList = map.get(fwlog.getDestport());
            if (tempList == null){
                tempList = new ArrayList<>();
                tempList.add(fwlog);
                map.put(fwlog.getDestport(),tempList);
            }else{
                tempList.add(fwlog);
            }
        }
        return map;
    }

    @RequestMapping(value = "/getDestPort",method = RequestMethod.GET)
    public List<PortEntity> getDestPort(HttpServletResponse response) throws IOException{
        PrintWriter out = response.getWriter();
        List<Fwlog> fwlogs = fwlogService.getAll();
        List<PortEntity> portEntities = new ArrayList<>();
        Map<Integer,List<Fwlog>> fwlogMap = listToMap(fwlogs);
        for (Integer value:fwlogMap.keySet()){
//            按照端口号进行分类
            List<Fwlog> fwlogs1 = fwlogMap.get(value);
//            统计访问次数
            int num = fwlogs.size();

            PortEntity portEntity = new PortEntity();
            portEntity.setPortNum(fwlogs1.get(0).getDestport());
            portEntity.setAccessNum(num);

            portEntities.add(portEntity);
        }
        return portEntities;
    }
}
