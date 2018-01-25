package com.fwlog.james.analysis.eventAnalysis;

import com.fwlog.james.entity.Destip;
import com.fwlog.james.service.DestipService;
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
    private final long[] VARIANCE_THRESOLD = new long[]{100,100,100,100,100,100,
                                                        100,100,100,100,100,100,
                                                        100,100,100,100,100,100,
                                                        100,100,100,100,100,100};
    private final long[] AVERAGE_THRESHOLD = new long[]{500,500,500,500,500,500,
                                                        500,500,500,500,500,500,
                                                        500,500,500,500,500,500,
                                                        500,500,500,500,500,500};

    private void subspectedEvents(){
        //根据时间的不同，选取不同的阈值
        DestipService destipService = (DestipService) SpringUtil.getBean("destipService");
        List<Destip> destipList = destipService.findAll();
//        找到最后一个条数据
        Destip destip = destipList.get(destipList.size() - 1);

        String time = String.valueOf(destip.getEtime());
        time = time.substring(time.indexOf(" ") + 1, time.indexOf(" ") + 3);
        int hour = Integer.parseInt(time);
//        for debug
//        System.out.println(hour);
        //通过阈值进行判断是否异常访问
        long average_interval = AVERAGE_THRESHOLD[hour];
        long variance_interval = VARIANCE_THRESOLD[hour];


        //访问异常的情况保存至数据库
    }

    private void eventsMakeSure(){
        //通过异常访问分析攻击类型

        //将确认的访问事件保存至数据库

    }

    public void eventAnalysis(){
        subspectedEvents();
    }



}
