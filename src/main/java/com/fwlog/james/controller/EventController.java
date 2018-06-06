package com.fwlog.james.controller;

/**
 * 事件控制器
 * created by jamesZhan on 2018/01/29
 * 这里需要的controller为：
 * 遭受攻击最多的IP地址（ip地址，被攻击的次数），发起攻击最多的IP地址（ip地址，攻击的次数）
 * 最新的攻击事件（始末时间，攻击类型，被攻击的IP地址）
 * 当日遭受的攻击事件类型以及数量
 * 一周内的遭遇的攻击事件的趋势（每天对应的攻击事件的数量）
 * 对于内网公司会有记录，即这个在特定范围内的IP地址认为是内网IP，其余为外网IP
 */

import com.fwlog.james.entity.Events;
import com.fwlog.james.entity.Srcip;
import com.fwlog.james.entity.Suspectedevents;
import com.fwlog.james.mode.*;
import com.fwlog.james.service.AdviceService;
import com.fwlog.james.service.EventsService;
import com.fwlog.james.service.SuspectedeventsService;
import com.fwlog.james.utils.IPChangeUtil;
import com.fwlog.james.utils.WeekUtil;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("fwlog/events")
public class EventController {
    private Date todayTime;
//    将时间转变成时间戳的样子
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private long NEXT_DAY_INTERVAL = 1000 * 24 * 60 * 60;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    @Autowired
    private EventsService eventsService;
    @Autowired
    private SuspectedeventsService suspectedeventsService;
    @Autowired
    private AdviceService adviceService;

//    局域网内的IP地址相同的IP开头
    private long destIpMin = 3396061;

//    保存攻击与被攻击的IP地址
    private List<IPEntity> srcIP = new ArrayList<>();
    private List<IPEntity> destIP = new ArrayList<>();
    /**
     * 获取确认攻击的事件的所有信息
     * @return 确认攻击事件的信息数组
     */
    private List<EventEntity> getEventEntities(){
        List<EventEntity> eventEntities = new ArrayList<>();
        List<Events> eventsList = eventsService.getAll();
        List<Suspectedevents> suspectedevents = suspectedeventsService.findAll();
        for (Events events:eventsList){
            for (Suspectedevents suspectedevents1: suspectedevents){
                if (events.getId() == suspectedevents1.getId()){
                    EventEntity eventEntity = new EventEntity();
                    eventEntity.setStarTime(suspectedevents1.getStime());
                    eventEntity.setEndTime(suspectedevents1.getEtime());
                    eventEntity.setDesc(events.getDescription());
                    eventEntity.setIpAddress(new IPChangeUtil().ipLongToStr(suspectedevents1.getValue()));
                    eventEntity.setAttackType(events.getAttackType());
//                    String advice = "nasjbjsa";
                    String advice = adviceService.findByType(events.getAttackType());
                    eventEntity.setAdvice(advice);
                    eventEntities.add(eventEntity);
                }
            }
        }
        return eventEntities;
    }

    /**
     * 将IP地址划分为域内地址和域外地址
     */
    private void deleteIpAddr(){
        List<EventEntity> eventEntities = getEventEntities();
        int eventLen = eventEntities.size();
        for (int i = 0 ; i < eventLen ; i++){
            String ip = eventEntities.get(i).getIpAddress();
            Long ipLong = new IPChangeUtil().ipStrToLong(ip);
            ipLong = ipLong / 1000;
//            域内IP地址
            if (ipLong == destIpMin){
                if (srcIP.size() == 0){
                    IPEntity ipEntity = new IPEntity();
                    ipEntity.setIpAdress(ip);
                    ipEntity.setAccessNum(1);
                    srcIP.add(ipEntity);
                }else{
                    for (int k = 0 ; k < srcIP.size() ; k++){
//                        数据链表当中存在该IP地址
                        if (srcIP.get(k).getIpAdress().equals(ip)){
                            srcIP.get(k).setAccessNum(srcIP.get(k).getAccessNum() + 1);
                        }else{
                            IPEntity ipEntity = new IPEntity(ip,1);
                            srcIP.add(ipEntity);
                        }
                    }
                }
            }else{
                if (destIP.size() == 0){
                    IPEntity ipEntity = new IPEntity(ip,1);
                    destIP.add(ipEntity);
                }else{
                    for (int k = 0 ; k < destIP.size() ; k++){
                        if (destIP.get(k).getIpAdress().equals(ip)){
                            destIP.get(k).setAccessNum(destIP.get(k).getAccessNum() + 1);
                        }else{
                            destIP.add(new IPEntity(ip,1));
                        }
                    }
                }
            }
        }
    }
    //将list转换为map方便后续对于进行排序等操作,按照攻击类型进行分类
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

    @RequestMapping(value = "/eventsNum",method = RequestMethod.GET)
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


    /**
     * 返回发起攻击最多的IP地址
     * @return json[{ip:;次数:;}]
     */
    @PostMapping(value = "/getAttackMostIpAddr.do")
    @ResponseBody
    public String getAttackMostIpAddr(HttpServletRequest request,HttpServletResponse response) throws Exception{
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        deleteIpAddr();
        //排序，取出访问前五多的数据
        Collections.sort(srcIP, new Comparator<IPEntity>() {
            @Override
            public int compare(IPEntity o1, IPEntity o2) {
                int num1 = o1.getAccessNum();
                int num2 = o2.getAccessNum();
                if (num1 < num2)
                    return 1;
                else if (num1 == num2)
                    return 0;
                else
                    return -1;
            }
        });
        List<IPEntity> newIPList = srcIP.subList(0,5);
        Gson gson = new Gson();
        String json = gson.toJson(newIPList);
        out.write(json);
        out.flush();
        out.close();
        return null;
    }

    /**
     * 返回被攻击最多的IP地址的前五位
     * @return json[ip:;times：；]
     */
    @PostMapping(value = "/getAttackedMostIpAddr.do")
    public String getAttackedMostIpAddr(HttpServletResponse response,HttpServletRequest request) throws Exception{
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        deleteIpAddr();
        Collections.sort(destIP, new Comparator<IPEntity>() {
            @Override
            public int compare(IPEntity o1, IPEntity o2) {
                int num1 = o1.getAccessNum();
                int num2 = o2.getAccessNum();
                if (num1 < num2)
                    return 1;
                else if (num1 == num2)
                    return 0;
                else
                    return -1;
            }
        });
        List<IPEntity> newIPList = destIP.subList(0,5);
        Gson gson = new Gson();
        String json = gson.toJson(newIPList);
        out.write(json);
        out.flush();
        out.close();
        return null;
    }

    /**
     * 返回最新的五件攻击事件
     * 注意考虑空指针的问题
     * @return json[{开始时间：；结束时间：；攻击的类型：；被攻击的IP地址：；}]
     */
    @GetMapping(value = "/getNewestEvent.do")
    @ResponseBody
    public String getNewestEvent(HttpServletResponse response, HttpServletRequest request) throws  Exception{
        response.setContentType("text/html;charset = utf-8");
        PrintWriter out = response.getWriter();
        //获取事件
        List<EventEntity> eventList = getEventEntities();
//        System.out.println(eventList.size());
//        System.out.println(eventList.get(0).getAdvice());
        //获取事件链表的大小
        int eventLen = eventList.size();
        List<NewAttackEvent> newAttackEventList = new ArrayList<>();
//        当事件数量大于5的时候取最新的五个事件，若没有，则将数据全都取出
        if (eventLen <= 5){
            for (int i = 0 ; i < eventLen ; i++){
                NewAttackEvent attackEvent = new NewAttackEvent();
                attackEvent.setStartTime(sdf.format(eventList.get(i).getStarTime()));
                attackEvent.setEndTime(sdf.format(eventList.get(i).getEndTime()));
                attackEvent.setIpAddress(eventList.get(i).getIpAddress());
                attackEvent.setAttackType(eventList.get(i).getAttackType());
                newAttackEventList.add(attackEvent);
            }
        }else{
            for (int i = eventLen - 1 ; i >= 0 ; i--){
                NewAttackEvent attackEvent = new NewAttackEvent();
                attackEvent.setStartTime(sdf.format(eventList.get(i).getStarTime()));
                attackEvent.setEndTime(sdf.format(eventList.get(i).getEndTime()));
                attackEvent.setIpAddress(eventList.get(i).getIpAddress());
                attackEvent.setAttackType(eventList.get(i).getAttackType());
                newAttackEventList.add(attackEvent);
            }
        }
//        将数据由list转化成json数据模式
        Gson gson = new Gson();
        String json = gson.toJson(newAttackEventList);
//        使数据的格式满足layui所需数据的模式
        String layuiJson = "{\"count\":100,\"code\":0,\"msg\":\"\",\"data\":" + json + "}";

        out.write(layuiJson);
        out.flush();
        out.close();
//        System.out.println(layuiJson);
        return null;
    }

    /**
     * 当日遭受攻击的数量与比例
     * @return json[{攻击事件：；数量：；}]
     */
    @PostMapping(value = "/getTodayEvents.do")
    @ResponseBody
    public String getTodayEvents(HttpServletResponse response, HttpServletRequest request) throws Exception{
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        //获取系统时间
        String date = simpleDateFormat.format(new Date());
        List<EventEntity> eventEntityList = getEventEntities();
        List<EventTypeEntity> eventTypeEntities = new ArrayList<>();
        int lenght = eventEntityList.size();
        if (lenght > 0){
            for (int i = 0 ; i < lenght ; i++){
//                本次攻击时间发生在今天
                if(eventEntityList.get(i).getStarTime().getTime() > simpleDateFormat.parse(date).getTime()){
                    if (eventTypeEntities.size() == 0){
                        eventTypeEntities.add(new EventTypeEntity(eventEntityList.get(i).getAttackType(),1));
                    }else{
                        for (int k = 0 ; k < eventTypeEntities.size() ; k++){
                            if (eventEntityList.get(i).getAttackType().equals(eventTypeEntities.get(k).getAttackType())){
                                eventTypeEntities.get(k).setTimes(eventTypeEntities.get(k).getTimes() + 1);
                            }else{
                                eventTypeEntities.add(new EventTypeEntity(eventEntityList.get(i).getAttackType(),1));
                            }//else
                        }//for
                    }//else
                }//if
            }//for
        }//if
        Gson gson = new Gson();
        String json = gson.toJson(eventTypeEntities);
        out.write(json);
        out.flush();
        out.close();
        return null;
    }

    /**
     * 本周的攻击事件的趋势
     * @return
     */
    @GetMapping(value = "/getWeekEventsTrend.do")
    @ResponseBody
    public String getWeekEventsTrend(HttpServletRequest request,HttpServletResponse response) throws Exception{
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        Calendar cal = Calendar.getInstance();
        String date = simpleDateFormat.format(new Date());
        Date todayDate = simpleDateFormat.parse(date);
//        周几
        String weekDay = new WeekUtil().dateToWeek(date);
//        System.out.println(weekDay);
//        获取本周的零点时间
        cal.set(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH),0,0,0);
        cal.set(Calendar.DAY_OF_WEEK,Calendar.SUNDAY);

        String beginTime = sdf.format(cal.getTime());
//        System.out.println(beginTime);
//        获取截至到今天的每天的攻击时间的数量
        List<Integer> eventNum = new ArrayList<>();
        Date startTime = sdf.parse(beginTime);
        String finishTime = sdf.format((startTime.getTime() + NEXT_DAY_INTERVAL));
        Date endTime = sdf.parse(finishTime);
        for (int i = 0 ; i < 7 ; i++){
            //获取这个时间段内的疑似访问事件
            if (endTime.getTime() <= todayDate.getTime()){
                List<Suspectedevents> suspectedevents = suspectedeventsService.findByStimeAndEtime(startTime,endTime);
                eventNum.add(suspectedevents.size());
                startTime = simpleDateFormat.parse(simpleDateFormat.format(startTime.getTime() + NEXT_DAY_INTERVAL));
                endTime = simpleDateFormat.parse(simpleDateFormat.format(endTime.getTime() + NEXT_DAY_INTERVAL));
            }
//            若不满足跳出循环
            else
                break;
        }
        List<Events> eventsList = eventsService.getAll();
        return null;
    }


    /**
     * 获取所有的攻击事件
     * @param request
     * @param response
     * @return
     */
    @PostMapping("/getAllEvents")
    @ResponseBody
    public String getAllEvents(HttpServletRequest request,HttpServletResponse response){
//        response.setCharacterEncoding();
        return null;
    }
}
