package com.fwlog.james.controller;

import com.fwlog.james.entity.Destip;
import com.fwlog.james.entity.Fwlog;
import com.fwlog.james.entity.Srcip;
import com.fwlog.james.mode.IPEntity;
import com.fwlog.james.service.DestipService;
import com.fwlog.james.service.FwlogService;
import com.fwlog.james.service.SrcipService;
import com.fwlog.james.utils.IPChangeUtil;
import com.fwlog.james.utils.ListMapUtil;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.*;

/**
 * IP控制器
 * created by jamesZhan on 2018/03/19
 * 控制器的内容：
 * 发起访问最多的IP地址（IP地址，次数）
 * 被访问最多的IP地址（IP地址，次数）
 * 协议组的访问情况（协议组内容，访问次数）
 */
@RestController
@RequestMapping("fwlog/accessAnalysis")
public class IpController {

    @Autowired
    private DestipService destipService;
    @Autowired
    private SrcipService srcipService;
    @Autowired
    private FwlogService fwlogService;
    public void sortIP(List<IPEntity> ipEntities){
        Collections.sort(ipEntities, new Comparator<IPEntity>() {
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
    }
    /**
     *返回发起访问最多的IP地址
     * @return json[{ip:;time:;}]
     */
    @PostMapping(value = "/getAccessMostIpAddr.do")
    @ResponseBody
    public String getAccessMostIpAddr(HttpServletResponse response, HttpServletRequest request) throws Exception{
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        List<Srcip> srcipList = srcipService.findAll();
        //根据IP地址进行分组查询
        String value = "value";
        Map<Long,List<Srcip>> stringListMap = new ListMapUtil().makeEntityListMap(srcipList,value);
        //排序 取前五位的IP地址以及访问的数量
        List<IPEntity> ipEntities = new ArrayList<>();

        for (Long ipAdd:stringListMap.keySet()){
            int sum = 0;
            //获取该列的数据
            List<Srcip> srcips = stringListMap.get(ipAdd);
            for (int i = 0 ; i < srcips.size() ; i++){
                sum += srcips.get(i).getNumber();
            }
            String ip = new IPChangeUtil().ipLongToStr(ipAdd);
            ipEntities.add(new IPEntity(ip,sum));
        }

        sortIP(ipEntities);
        ipEntities = ipEntities.subList(0,5);
        for (int i = 0 ; i < ipEntities.size() ; i++){
            System.out.println(ipEntities.get(i).getIpAdress() + "   " + ipEntities.get(i).getAccessNum());
        }
        Gson gson = new Gson();
        String json = gson.toJson(ipEntities);
        out.write(json);
        out.flush();
        out.close();
        //返回上述内容
        return null;
    }

    /**
     * 返回被访问最多的IP地址
     * @return json[{ip:;times:;}]
     */
    @PostMapping(value = "/getAccessedMostIpAddr.do")
    @ResponseBody
    public String getAccessedMostIpAddr(HttpServletResponse response,HttpServletRequest request) throws Exception{
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        List<Destip> destipList = destipService.findAll();
        Map<Long,List<Destip>> destIpMap = new ListMapUtil().makeEntityListMap(destipList,"value");
        List<IPEntity> ipEntities = new ArrayList<>();
        for (Long ipAddr:destIpMap.keySet()){
            List<Destip> destipList1 = destIpMap.get(ipAddr);
            int sum = 0;
            for (int i = 0 ; i < destipList1.size() ; i++){
                sum += destipList1.get(i).getNumber();
            }
            String ipAdd = new IPChangeUtil().ipLongToStr(ipAddr);
            ipEntities.add(new IPEntity(ipAdd,sum));
        }
        sortIP(ipEntities);
        ipEntities = ipEntities.subList(0,5);
        Gson gson = new Gson();
        String json = gson.toJson(ipEntities);
        out.write(json);
        out.flush();
        out.close();
        return null;
    }

    /**
     * 返回协议组被访问的情况
     * @return json[{protocol:;times:;}]
     */
    @PostMapping(value = "/getProtocolAccessed.do")
    @ResponseBody
    public String getProtocolAccessed(HttpServletRequest request, HttpServletResponse response) throws Exception{
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        List<Fwlog> fwlogList = fwlogService.getAll();
        Map<Integer,List<Fwlog>> fwlogListMap = new ListMapUtil().makeEntityListMap(fwlogList,"destport");
        List<IPEntity> portList = new ArrayList<>();
        for (Integer destPort:fwlogListMap.keySet()){
            List<Fwlog> fwlogs = fwlogListMap.get(destPort);
            int sum = 0;
            for (int i = 0 ; i < fwlogs.size() ; i++){
                sum++;
            }
            String port = String.valueOf(destPort);
            portList.add(new IPEntity(port,sum));
        }
        sortIP(portList);
        portList = portList.subList(0,5);
        for (int i = 0 ; i < portList.size() ; i++){
            System.out.println(portList.get(i).getIpAdress() + "    " + portList.get(i).getAccessNum());
        }
        Gson gson = new Gson();
        String json = gson.toJson(portList);
        out.write(json);
        out.flush();
        out.close();
        return null;
    }
}
