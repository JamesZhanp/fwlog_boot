package com.fwlog.james.analysis.statisticsAnalysis;

import com.fwlog.james.analysis.preAnalysis.Pretreatment;
import com.fwlog.james.entity.Fwlog;
import com.fwlog.james.service.FwlogService;
import com.fwlog.james.utils.SpringUtil;
import org.springframework.context.annotation.Configuration;

import javax.naming.ldap.SortResponseControl;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * 统计处理，要做的事情，统计各个源IP地址和目的IP地址的访问情况
 * 一个完成的访问事件中的源IP和目的IP的访问的平均值和方差，与阈值相比较，得出异常访问的记录
 * 文件之间直接传参，尽量减少IO请求
 * @author jamesZhan
 * @Date 2018-01-23
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
//    在动态数组当中查找是否存在该元素
    private static boolean flag = false;
    private static List<IP> destIpList = new LinkedList<>();
//    private  List<Fwlog> fwlogList = Pretreatment.fwlogList;
    private static void statisticsAnalysis(Fwlog fwlog) throws Exception{

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
//        当动态数组为空时，插入首条数据
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
        for (IP ip1 : srcIpList){
            //寻找是否有IP地址一样，时间一样的访问
            if ((ip1.getValue().equals(ip[0])) &&
                    (ip1.getsTime().getTime() == lastTime.getTime())){
                flag = true;
                Integer time = ip1.getCount();
                time += 1;
                ip1.setCount(time);
            }
        }
//        动态数组当中不存在该IP情况
        if (!flag){
            IP ip2 = new IP();
            ip2.setCount(fwlog.getNumber());
            ip2.setValue(ip[0]);
            ip2.setsTime(lastTime);
            ip2.seteTime(new Date(lastTime.getTime() + MIN_INTERVAL));
            srcIpList.add(ip2);
        }
//        统计每秒的目的IP的访问情况
        for (IP ip1 : destIpList){
            if ((ip1.getValue().equals(ip[1])) &&
                    ip1.getsTime().getTime() == lastTime.getTime()){
                Integer time = ip1.getCount();
                time += 1;
                ip1.setCount(time);
                flag = true;
            }
        }
        if (!flag){
            IP ip2 = new IP();
            ip2.setCount(fwlog.getNumber());
            ip2.setValue(ip[1]);
            ip2.setsTime(lastTime);
            ip2.seteTime(new Date(lastTime.getTime() + MIN_INTERVAL));
            destIpList.add(ip2);
        }
        //保存至数据库
    }

    public void analysis(){
        FwlogService fwlogService = (FwlogService) SpringUtil.getBean("fwlogService");

        List<Fwlog> fwlogs = fwlogService.getAll();
        for (Fwlog fwlog : fwlogs){
            try{
                 StatisticsAnalysis.statisticsAnalysis(fwlog);
//                 每次循环结束之后，将flag置为false
                 flag = false;
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        //当动态数组为空时插入数据，此处进行回原
        srcIpList.get(0).setCount(srcIpList.get(0).getCount() - 1);
        destIpList.get(0).setCount(destIpList.get(0).getCount() - 1);

//        for debug
//        System.out.println(srcIpList.size());
//        System.out.println(destIpList.size());
        //在计算出这个访问事件下的各个IP的访问的均值，方差等

    }
}