package com.fwlog.james.controller;

/**
 * 事件控制器
 * created by jamesZhan on 2018/01/29
 */

import com.fwlog.james.entity.Events;
import com.fwlog.james.entity.Suspectedevents;
import com.fwlog.james.mode.EventEntity;
import com.fwlog.james.mode.EventNumEntity;
import com.fwlog.james.service.EventsService;
import com.fwlog.james.service.SuspectedeventsService;
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
public class EventController {

    @Autowired
    private EventsService eventsService;
    @Autowired
    private SuspectedeventsService suspectedeventsService;

    private List<EventEntity> getEventEntities(){
        List<EventEntity> eventEntities = new ArrayList<>();
        List<Events> eventsList = eventsService.getAll();
        List<Suspectedevents> suspectedevents = suspectedeventsService.findAll();
        for (Events events:eventsList){
            for (Suspectedevents suspectedevents1: suspectedevents){
                if (events.getId() == suspectedevents1.getId()){
                    EventEntity eventEntity = new EventEntity();
                    eventEntity.setId(events.getId());
                    eventEntity.setStarTime(suspectedevents1.getStime());
                    eventEntity.setEndTime(suspectedevents1.getEtime());
                    eventEntity.setDesc(events.getDescription());
                    eventEntity.setIpAddress(new IPChangeUtil().ipLongToStr(suspectedevents1.getValue()));
                    eventEntity.setAttackType(events.getAttackType());
                    eventEntity.setAdvice("巴拉巴拉");
                    eventEntities.add(eventEntity);
                }
            }
        }
        return eventEntities;
    }

    //将list转换为map方便后续对于进行排序等操作
    private Map<String,List<EventEntity>> listToMap(List<EventEntity> eventEntities){
        Map<String,List<EventEntity>> map = new HashMap<>();
        for (EventEntity eventEntity:eventEntities){
            List<EventEntity> tempList = map.get(eventEntity.getAttackType());
            if (tempList == null){
                tempList = new ArrayList<>();
                tempList.add(eventEntity);
                map.put(eventEntity.getAttackType(),tempList);
            }else{
                tempList.add(eventEntity);
            }
        }
        return map;
    }
    /**
     * spring boot默认使用的json解析框架是jackson
     * 返回详细的攻击事件的列表，初始时间，攻击类型，攻击建议等
     * @return
     */
    @RequestMapping(value = "/getEvents",method = RequestMethod.GET)
    public List<EventEntity> getEvents(){
        List<EventEntity> eventEntities = getEventEntities();
//        for (EventEntity eventEntity:eventEntities){
//            System.out.println(eventEntity);
//        }
        return eventEntities;
    }

    @RequestMapping(value = "eventsNum",method = RequestMethod.GET)
    public List<EventNumEntity> eventNum(){
        //获取所有的攻击事件
        List<EventEntity> eventEntities = getEventEntities();
        List<EventNumEntity> eventNumEntities = new ArrayList<>();
//       将list转换成map
        Map<String,List<EventEntity>> eventMap = listToMap(eventEntities);
//        根据攻击类型进行分组
        for (String type:eventMap.keySet()){
            EventNumEntity eventNumEntity = new EventNumEntity();
            List<EventEntity> eventEntities1 = eventMap.get(type);
            eventNumEntity.setAttackName(eventEntities.get(0).getAttackType());
            eventNumEntity.setAttackNum(eventNumEntities.size());

            eventNumEntities.add(eventNumEntity);
        }
        return eventNumEntities;
    }

}
