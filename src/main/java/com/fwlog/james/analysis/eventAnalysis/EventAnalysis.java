package com.fwlog.james.analysis.eventAnalysis;

import com.fwlog.james.entity.Destip;
import com.fwlog.james.entity.Srcip;
import com.fwlog.james.entity.Suspectedevents;
import com.fwlog.james.service.DestipService;
import com.fwlog.james.service.SrcipService;
import com.fwlog.james.service.SuspectedeventsService;
import com.fwlog.james.utils.SpringUtil;

import java.util.List;

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
                suspectedevents.setType(1);

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
                suspectedevents.setType(2);

                suspectedeventsService.save(suspectedevents);
            }
        }
    }

    private void eventsMakeSure(){
        //通过异常访问分析攻击类型

        //将确认的访问事件保存至数据库

    }

    public void eventAnalysis(){
        subspectedEvents();
    }



}
