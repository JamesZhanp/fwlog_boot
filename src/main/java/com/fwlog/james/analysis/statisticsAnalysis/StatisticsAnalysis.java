package com.fwlog.james.analysis.statisticsAnalysis;

import com.fwlog.james.analysis.preAnalysis.Pretreatment;
import com.fwlog.james.entity.Fwlog;
import com.fwlog.james.service.FwlogService;
import com.fwlog.james.utils.SpringUtil;
import org.junit.Test;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * 统计处理，要做的事情，统计各个源IP地址和目的IP地址的访问情况
 * 一个完成的访问事件中的源IP和目的IP的访问的平均值和方差，与阈值相比较，得出异常访问的记录
 * 文件之间直接传参，尽量减少IO请求
 * @author jamesZhan
 * @date 2018-01-23
 */
@Configuration
public class StatisticsAnalysis {
    //保存上次事件的事件
    private static Date lastTime = null;
    //事件间隔
    private static long MIN_INTERVAL = 1000;
    //时间的格式
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    //        保存每秒各个IP地址的访问次数
    private static List<IP> srcIpList = new LinkedList<>();
    private static List<IP> destIpList = new LinkedList<>();
//    private  List<Fwlog> fwlogList = Pretreatment.fwlogList;
    public static void statisticsAnalysis(Fwlog fwlog) throws Exception{

//        for debug
//        System.out.println(fwlogList.size());
        if (MIN_INTERVAL % 1000 != 0){
            throw new Exception("the MIN_INTERVAL must be multiply by 1s");
        }
        Date nowTime = fwlog.getTime();
        if (lastTime == null || nowTime.getTime() - lastTime.getTime() >= MIN_INTERVAL){
            lastTime = fwlog.getTime();
        }
        //计算每一秒各个IP的访问次数
        Long[] ip = new Long[2];
        ip[0] = fwlog.getSrcip();
        ip[1] = fwlog.getDestip();
        if(srcIpList.size() == 0){
            IP ip1 = new IP();
            ip1.setsTime(lastTime);
            ip1.seteTime(new Date(lastTime.getTime() + MIN_INTERVAL));
            ip1.setValue(fwlog.getSrcip());
            ip1.setCount(fwlog.getNumber());
            srcIpList.add(ip1);
        }

        if (destIpList.size() == 0){
            IP ip1 = new IP();
            ip1.setCount(fwlog.getNumber());
            ip1.setsTime(lastTime);
            ip1.seteTime(new Date(lastTime.getTime() + MIN_INTERVAL));
            ip1.setValue(fwlog.getDestip());
            destIpList.add(ip1);
        }
//        统计每秒的原始IP的访问情况
        for (int i = 0 ; i < srcIpList.size() ; i++){
            //寻找是否有IP地址一样，时间一样的访问
            if ((srcIpList.get(i).getValue() == ip[0]) &&
                    (srcIpList.get(i).getsTime().getTime() == lastTime.getTime())){
                Integer time = srcIpList.get(i).getCount();
                time += 1;
                srcIpList.get(i).setCount(time);
            }else{
                IP ip1 = new IP();
                ip1.setCount(fwlog.getNumber());
                ip1.setValue(ip[0]);
                ip1.setsTime(lastTime);
                ip1.seteTime(new Date(lastTime.getTime() + MIN_INTERVAL));
                srcIpList.add(ip1);
            }
        }

//        统计每秒的目的IP的访问情况
        for (int i = 0 ; i < destIpList.size() ; i++){
            if ((destIpList.get(i).getValue() == ip[1]) &&
                    (destIpList.get(i).getsTime().getTime() == lastTime.getTime())){
                Integer time = srcIpList.get(i).getCount();
                time += 1;
                destIpList.get(i).setCount(time);
            }else{
                IP ip1 = new IP();
                ip1.setCount(fwlog.getNumber());
                ip1.setValue(ip[1]);
                ip1.setsTime(lastTime);
                ip1.seteTime(new Date(lastTime.getTime() + MIN_INTERVAL));
                destIpList.add(ip1);
            }
        }

        //保存至数据库

    }

    public void analysis(){
        FwlogService fwlogService = (FwlogService) SpringUtil.getBean("fwlogService");

        List<Fwlog> fwlogs = fwlogService.getAll();
        for (int i = 0 ; i < fwlogs.size() ; i++){
            try{
                 StatisticsAnalysis.statisticsAnalysis(fwlogs.get(i));
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        //在计算出这个访问事件下的各个IP的访问的均值，方差等
        System.out.println(srcIpList.size());
        System.out.println(destIpList.size());
    }
}