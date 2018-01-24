package com.fwlog.james.analysis.statisticsAnalysis;

import com.fwlog.james.entity.Fwlog;
import com.fwlog.james.entity.Srcip;
import com.fwlog.james.service.FwlogService;
import com.fwlog.james.service.SrcipService;
import com.fwlog.james.utils.SpringUtil;
import com.fwlog.james.utils.VarianceUtil;
import org.springframework.context.annotation.Configuration;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 统计处理，要做的事情，统计各个源IP地址和目的IP地址的访问情况
 * 一个完成的访问事件中的源IP和目的IP的访问的平均值和方差，与阈值相比较，得出异常访问的记录
 * 文件之间直接传参，尽量减少IO请求
 * @author jamesZhan
 * @Date 2018-01-23
 */
@Configuration
public class StatisticsAnalysis {
    //统计单位时间内的访问次数使用的时间戳
    private static Date lastTime = null;

    //统计访问事件当中使用的时间戳
    private static Date earlyTime = null;
    private static Date laterTime = null;
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
            ip1.seteTime(new Timestamp(lastTime.getTime() + MIN_INTERVAL));
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
//        在SrcIP处理完之后就重新fu原始值
        flag = false;
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
            ip2.seteTime(new Timestamp(lastTime.getTime() + MIN_INTERVAL));
            destIpList.add(ip2);
        }

        flag = false;
    }

//    求srcIp方差和均值的函数，之后保存
    private static void dataSrcIpAnalysis(){
//        注入bean
        SrcipService srcipService = (SrcipService) SpringUtil.getBean("srcipService");
//        根据value值进行分组，在list中进行分组
        Map<Long,List<IP>> IPMap = new HashMap<>();
        for (IP ip1 : srcIpList){
            List<IP> tempList = IPMap.get(ip1.getValue());
//            如果取不到数据，直接new一个新的ArrayList
            if (tempList == null){
                tempList = new ArrayList<>();
                tempList.add(ip1);
                IPMap.put(ip1.getValue(),tempList);
            }else{
                tempList.add(ip1);
            }
        }
//        for (Long value : IPMap.keySet()){
//            System.out.println(IPMap.get(value));
//        }
//        统计事件中的总的访问次数
        int totalNum = 0;
        for (Long value : IPMap.keySet()){

//            根据IP值的不同进行分组
            List<IP> ipGroup = IPMap.get(value);
//            保存单位时间的该ip的访问值
            ArrayList count = new ArrayList();
            int srcIpListLength = ipGroup.size();
            if (srcIpListLength >= 2){
                earlyTime = ipGroup.get(0).getsTime();
                for (int i = 0 ; i < srcIpListLength ; i ++){
//                    动态数组的最后一个访问单独考虑
                    if (i == srcIpListLength - 1){
                        if (ipGroup.get(i).getsTime().getTime() == ipGroup.get(i - 1).geteTime().getTime()){
                            totalNum += ipGroup.get(i).getCount();
                            count.add(ipGroup.get(i).getCount());
                            laterTime = ipGroup.get(i).geteTime();
                            Srcip srcip = new Srcip();
                            srcip.setEtime(laterTime);
                            srcip.setStime(earlyTime);
                            srcip.setValue(ipGroup.get(i).getValue());
                            srcip.setNumber(totalNum);
                            srcip.setAverage(VarianceUtil.keepTwoDecimalPlaces(VarianceUtil.getAverage(count)));
                            srcip.setVariance(VarianceUtil.keepTwoDecimalPlaces(VarianceUtil.getVariance(count)));

                            srcipService.save(srcip);
                            totalNum = 0;
                            count.clear();
                        }
                    }else{
                        totalNum += ipGroup.get(i).getCount();
                        count.add(ipGroup.get(i).getCount());
//                        连续访问事件
                        if (ipGroup.get(i + 1).getsTime().getTime() > ipGroup.get(i).geteTime().getTime()){
                            laterTime = ipGroup.get(i).geteTime();
                            Srcip srcip = new Srcip();
                            srcip.setEtime(laterTime);
                            srcip.setStime(earlyTime);
                            srcip.setValue(ipGroup.get(i).getValue());
                            srcip.setNumber(totalNum);
                            srcip.setAverage(VarianceUtil.keepTwoDecimalPlaces(VarianceUtil.getAverage(count)));
                            srcip.setVariance(VarianceUtil.keepTwoDecimalPlaces(VarianceUtil.getVariance(count)));
                            //只有一秒的访问事件不保存至数据库
                            if (srcip.getEtime().getTime() - srcip.getStime().getTime() > 1000){
                                srcipService.save(srcip);
                            }

                            earlyTime = ipGroup.get(i + 1).getsTime();
                            totalNum = 0;
                            count.clear();
                        }
                    }
                }
            }
//          else{
//                Srcip srcip = new Srcip();
//                srcip.setValue(ipGroup.get(0).getValue());
//                srcip.setAverage(ipGroup.get(0).getCount() * 1.00);
//                srcip.setVariance(0.00);
//                srcip.setNumber(ipGroup.get(0).getCount());
//                srcip.setStime(ipGroup.get(0).getsTime());
//                srcip.setEtime(ipGroup.get(0).geteTime());
//                srcipService.save(srcip);
//
//            }
        }
    }

//    求destIp在访问事件中的均值和方差
    public void analysis(){
        FwlogService fwlogService = (FwlogService) SpringUtil.getBean("fwlogService");

        List<Fwlog> fwlogs = fwlogService.getAll();
        for (Fwlog fwlog : fwlogs){
            try{
                 StatisticsAnalysis.statisticsAnalysis(fwlog);
//                 每次循环结束之后，将flag置为false
            }catch (Exception e){
                e.printStackTrace();
            }
        }

//        for (int i = 0 ; i < 1000 ; i ++){
//            try{
//                StatisticsAnalysis.statisticsAnalysis(fwlogs.get(i));
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//        }

        //当动态数组为空时插入数据，此处进行回原
        srcIpList.get(0).setCount(srcIpList.get(0).getCount() - 1);
        destIpList.get(0).setCount(destIpList.get(0).getCount() - 1);

//        for debug
        System.out.println(srcIpList.size());
        System.out.println(destIpList.size());
        //再计算出这个访问事件下的各个IP的访问的均值，方差等
//        求出一个访问事件当中的源IP和目的IP地址的均值和方差
             dataSrcIpAnalysis();
    }
}