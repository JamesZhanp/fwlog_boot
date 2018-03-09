package com.fwlog.james.analysis.eventAnalysis;

import com.fwlog.james.entity.*;
import com.fwlog.james.service.*;
import com.fwlog.james.utils.IPChangeUtil;
import com.fwlog.james.utils.SpringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 事件处理类，作用是异常访问的情况转变成为攻击事件的过程
 * 异常事件的确定分两步
 * 1.根据阈值的问题判断出访问异常的源IP地址和被访问异常的目的IP地址
 * 2.将得到的数据返回到原始的日志数据当中，判断是否真的为异常访问事件
 */
public class EventAnalysis {
    //阈值根据时间范围的不同而不同,我们这边将其分为24类
    private final double[] AVERAGE_THRESOLD = new double[]{100.0,100.0,100.0,100.0,100.0,100.0,
            100.0,100.0,100.0,100.0,100.0,100.0,
            100.0,100.0,100.0,100.0,100.0,100.0,
            100.0,100.0,100.0,100.0,100.0,100.0};
    private final double[] VARIANCE_THRESHOLD = new double[]{500,500,500,500,500,500,
                                                        500,500,500,500,500,500,
                                                        500,500,500,500,500,500,
                                                        500,500,500,500,500,500};

//    公认端口号 + 注册端口号
    private final int PORT_NUM = 49153;
//    主机的数量
    private final int IP_INTERVAL = 30;

    private void subspectedEvents(){
        //根据时间的不同，选取不同的阈值
        //bean注入
        SrcipService srcipService = (SrcipService) SpringUtil.getBean("srcipService");
        DestipService destipService = (DestipService) SpringUtil.getBean("destipService");
        SuspectedeventsService suspectedeventsService = (SuspectedeventsService) SpringUtil.getBean("suspectedeventsService");
        List<Destip> destipList = destipService.findAll();
//        找到最后一个条数据
        Destip destip = destipList.get(destipList.size() - 1);

        String time = String.valueOf(destip.getEtime());
        time = time.substring(time.indexOf(" ") + 1, time.indexOf(" ") + 3);
        int hour = Integer.parseInt(time);
//        for debug
//        System.out.println(hour);
        //通过阈值进行判断是否异常访问
        double average_interval = AVERAGE_THRESOLD[hour] * 1.5;
        double variance_interval = VARIANCE_THRESHOLD[hour] * 1.5;

        for (Destip destip1 : destipList){
            double average = destip1.getAverage();
            double variance = destip1.getVariance();
            if ((average > average_interval) && (variance > variance_interval)){
                Suspectedevents suspectedevents = new Suspectedevents();
                suspectedevents.setStime(destip1.getStime());
                suspectedevents.setEtime(destip1.getEtime());
                suspectedevents.setValue(destip1.getValue());
                suspectedevents.setType(2);

                suspectedeventsService.save(suspectedevents);
            }
        }

        List<Srcip> srcipList = srcipService.findAll();
        Srcip srcip = srcipList.get(srcipList.size() - 1);
        String time1 = String.valueOf(srcip.getStime());
        time1 = time1.substring(time1.indexOf(" ") + 1 , time1.indexOf(" ") + 3);
        int hour1 = Integer.parseInt(time1);

        double average_interval1 = AVERAGE_THRESOLD[hour1];
        double variance_interval1 = VARIANCE_THRESHOLD[hour1];

        for (Srcip srcip1 : srcipList){
            if ((srcip1.getAverage() > (average_interval1 * 1.5)) && (srcip1.getVariance() > (variance_interval1 * 1.5))){
                Suspectedevents suspectedevents = new Suspectedevents();
                suspectedevents.setStime(srcip1.getStime());
                suspectedevents.setEtime(srcip1.getEtime());
                suspectedevents.setValue(srcip1.getValue());
                suspectedevents.setType(1);

                suspectedeventsService.save(suspectedevents);
            }
        }
    }

    private Map<Long,List<Fwlog>> listToMap(List<Fwlog> fwlogList){
        Map<Long,List<Fwlog>> fwlogMap = new HashMap<>();
        for(Fwlog fwlog:fwlogList){
            List<Fwlog> tempList = fwlogMap.get(fwlog.getSrcip());
            if (tempList == null){
                tempList = new ArrayList<>();
                tempList.add(fwlog);
                fwlogMap.put(fwlog.getSrcip(),tempList);
            }else{
                tempList.add(fwlog);
            }
        }
        return fwlogMap;
    }

    private Map<Integer,List<Fwlog>> portListToMap(List<Fwlog> fwlogList){
        Map<Integer,List<Fwlog>> fwlogMap = new HashMap<>();
        for (Fwlog fwlog:fwlogList){
            List<Fwlog> tempList = fwlogMap.get(fwlog.getDestport());
            if (tempList == null){
                tempList = new ArrayList<>();
                tempList.add(fwlog);
                fwlogMap.put(fwlog.getDestport(),tempList);
            }else{
                tempList.add(fwlog);
            }
        }
        return fwlogMap;
    }

    private Map<Long,List<Fwlog>> ipListToMap(List<Fwlog> fwlogList){
        Map<Long,List<Fwlog>> fwlogMap = new HashMap<>();
        for (Fwlog fwlog:fwlogList){
            List<Fwlog> tempList = fwlogMap.get(fwlog.getDestip());
            if (tempList == null){
                tempList = new ArrayList<>();
                tempList.add(fwlog);
                fwlogMap.put(fwlog.getDestip(),tempList);
            }else{
                tempList.add(fwlog);
            }
        }
        return fwlogMap;
    }
    private void eventsMakeSure(){
        SuspectedeventsService suspectedeventsService = (SuspectedeventsService) SpringUtil.getBean("suspectedeventsService");
        FwlogService fwlogService = (FwlogService) SpringUtil.getBean("fwlogService");
        SensitivePortService sensitivePortService = (SensitivePortService) SpringUtil.getBean("sensitivePortService");
        EventsService eventsService = (EventsService) SpringUtil.getBean("eventsService");
        //通过异常访问分析攻击类型
        List<Fwlog> fwlogList = fwlogService.getAll();
        List<Suspectedevents> suspectedeventsList = suspectedeventsService.findAll();

        //遍历查找所有疑似攻击事件
        for (Suspectedevents events : suspectedeventsList){
            //根据是SrcIP or destIP 来采取不容的判定方法、
//            srcIP
            if (events.getType() == 1){
                List<Fwlog> fwlogs = new ArrayList<>();

                for (Fwlog fwlog : fwlogList){
                    if (fwlog.getTime().getTime() >= events.getStime().getTime() &&
                            fwlog.getTime().getTime() <= events.getEtime().getTime() &&
                            fwlog.getDestip().equals(events.getValue())){
//                        满足该条件直接保存至动态数组之中
                        fwlogs.add(fwlog);
                    }
                }
                //求出在本次事件范围内的访问次数
                int srcIPAccessNum = fwlogs.size();
                //访问的目的IP的数量
                int destIpNum = 0;
                //访问最多的端口即协议
                int mostAccessedPort = 0;

                Map<Long,List<Fwlog>> ipMap = ipListToMap(fwlogs);
                for(Long value : ipMap.keySet()){
//                    求出访问的IP的数量
                    destIpNum ++;
                }
                String desc = null;
                String attackType = null;
                if (destIpNum >= IP_INTERVAL * 2/3){
                    attackType = "扫描攻击";
                }
                desc = "从" + events.getStime() + "到" + events.getEtime() +
                        ",ip地址：" + new IPChangeUtil().ipLongToStr(events.getValue()) +
                        ",一共访问了" + destIpNum + "个域内IP地址" + "访问了" + srcIPAccessNum + "次。" ;
                if (srcIPAccessNum % (events.getEtime().getTime() - events.getStime().getTime()) * 100 > 200){
                    Events events1 = new Events();
                    events1.setId(events.getId());
                    events1.setDescription(desc);
                    events1.setAttackType(attackType);
                    eventsService.save(events1);
                }
            }
//            destIP
            else{
                List<Fwlog> fwlogs = new ArrayList<>();

                for (Fwlog fwlog : fwlogList){
                    if (fwlog.getTime().getTime() >= events.getStime().getTime() &&
                            fwlog.getTime().getTime() <= events.getEtime().getTime() &&
                            fwlog.getDestip().equals(events.getValue())){
//                        满足该条件直接保存至动态数组之中
                        fwlogs.add(fwlog);
                    }
                }
//                循环结束后求需要的值
//                求出被申请访问的次数
                int totalNum = fwlogs.size();
//                申请访问的IP地址的数量
                int srcIpNum = 0;
//                访问的端口的数量
                int destPortNum = 0;
                int accessedMostPort = 0;
                int thisPortNum = 0;
                Map<Long,List<Fwlog>> fwlogMap = listToMap(fwlogs);
                for (Long value : fwlogMap.keySet()){
                    srcIpNum ++;
                }

//                该端口下的被访问最多的端口
                Map<Integer,List<Fwlog>> portMap = portListToMap(fwlogs);
                for (Integer value : portMap.keySet()){
                    destPortNum ++;
                    List<Fwlog> fwlogs1 = portMap.get(value);
                    if (fwlogs1.size() > thisPortNum){
                        accessedMostPort = fwlogs1.get(0).getDestport();
                        thisPortNum = fwlogs1.size();
                    }
                }
                String attackType = null;
                String desc = "从" + events.getStime() + "到" + events.getEtime() +
                        ",ip地址：" + new IPChangeUtil().ipLongToStr(events.getValue()) +
                        ",被" + srcIpNum + "个不同的IP地址申请" + "访问了" + totalNum + "次。" +
                        "一共访问了该ip地址下的" + destPortNum + "个端口。" + "被访问最多的端口为" +
                        accessedMostPort + "被访问了" + thisPortNum + "次。";

                attackType = "DDoS攻击";
                if (destPortNum >= PORT_NUM){
                    attackType = "端口扫描攻击";
                }


                List<Sensitiveport> sensitiveports = sensitivePortService.findByPort(accessedMostPort);
                if (sensitiveports.size() > 0){
                    desc += sensitiveports.get(0).getDesc();
                    //保存至数据库
                    Events events1 = new Events();
                    events1.setId(events.getId());
                    events1.setDescription(desc);
                    events1.setAttackType(attackType);
                    eventsService.save(events1);
                }
                System.out.println(desc);
            }
        }
        //将确认的访问事件保存至数据库

    }

    public void eventAnalysis(){
        subspectedEvents();
        eventsMakeSure();
    }



}
