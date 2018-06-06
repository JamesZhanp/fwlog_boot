package com.fwlog.james.controller;

import com.fwlog.james.entity.Fwlog;
import com.fwlog.james.entity.Rawfwlog;
import com.fwlog.james.mode.IPEntity;
import com.fwlog.james.service.FwlogService;
import com.fwlog.james.service.RawfwlogService;
import com.fwlog.james.utils.AddressUtils;
import com.fwlog.james.utils.IPChangeUtil;
import com.google.gson.Gson;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 原始日志控制器
 * 本月通过日志的流量
 * 访问的来源T10
 */

@RestController
@RequestMapping("fwlog/FwlogAnalysis")
public class FwlogController {

    private Logger logger = LoggerFactory.getLogger(FwlogController.class);
    @Autowired
    private FwlogService fwlogService;
    @Autowired
    private RawfwlogService rawfwlogService;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    /**
     * 返回本月的出入防火墙的流量情况
     * @return json[time:;flow:;]
     */
    @GetMapping(value = "getMonthIOFlow.do")
    @ResponseBody
    public String getMonthIOFlow(HttpServletResponse response,HttpServletRequest request) throws Exception{
        response.setContentType("html/text;charset=utf-8");
        PrintWriter out = response.getWriter();
        String time = sdf.format(new Date());
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH,0);
        cal.set(Calendar.DAY_OF_MONTH,1);
        String firstDay = sdf.format(cal.getTime());
        String time1 = "2016-07-01 00:00:00";
//        通过最原始的日志数据分析得知本月份出入防护墙的流量
//        数据库中默认server->outside为0，反之为1
//        入防火墙的数据
        List<Rawfwlog> fwlogs = rawfwlogService.findByTime(sdf.parse(time1),0);
//        出防火墙的数据
        List<Rawfwlog> rawfwlogs = rawfwlogService.findByTime(sdf.parse(time1),1);
        System.out.println(firstDay);
//        System.out.println(fwlogs.size());
//        System.out.println(rawfwlogs.size());

        Gson gson = new Gson();

        return null;
    }

    /**
     * 返回访问的数据来源
     * @return json[area:;number]
     */
    @GetMapping(value = "/getAccessSource.do")
    @ResponseBody
    @Test
    public void getAccessSource(HttpServletRequest request, HttpServletResponse response) throws Exception{
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        //获取所有的访问的数据
        List<Fwlog> fwlogList = fwlogService.getAll();
        //保存城市的访问数值
        List<IPEntity> cityList = new ArrayList<>();
        //通过调用数据获取访问城市
        int fwlogLen = fwlogList.size();
        for (int i = 0 ; i < fwlogLen ; i++){
            long ipAddr = fwlogList.get(i).getSrcip();
            String ipAddress = new IPChangeUtil().ipLongToStr(ipAddr);
            String city = new AddressUtils().getAddresses("ip=" + ipAddress,"utf-8");
            if(city.equals("内部IP")){
                city = "杭州";
            }
//            查找这个城市的是否已经存在，若存在将访问的数量刷新，反之将这个城市加入list
            logger.info("IP地址为" + ipAddress + "城市为：" + city);
            boolean isFind = false;
            for (int k = 0 ; k < cityList.size() ; k++){
                if (cityList.size() == 0){
                    cityList.add(new IPEntity(city,1));
                }else{
                    if (cityList.get(k).getIpAdress().equals(city)){
                        cityList.get(k).setAccessNum(cityList.get(k).getAccessNum() + 1);
                        isFind = true;
                        break;
                    }
                }
            }
//            没有在list中找到该城市
            if (isFind == false){
                cityList.add(new IPEntity(city,1));
            }
        }
        if (cityList.size() > 10){
            //获取访问数量前10的城市
            cityList = cityList.subList(0,10);
        }
        Gson gson = new Gson();
        String json = gson.toJson(cityList);
        System.out.println(json);
//        out.write(json);
//        out.flush();
//        out.close();
//        return null;
    }
}
