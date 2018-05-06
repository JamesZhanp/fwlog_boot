package com.fwlog.james.analysis.preAnalysis;

import com.fwlog.james.entity.Fwlog;
import com.fwlog.james.entity.Rawfwlog;
import com.fwlog.james.service.FwlogService;
import com.fwlog.james.utils.SpringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedList;
import java.util.List;


/**
 * 预处理防火墙日志
 * 数据清理：删除日志中防火墙deny的日志信息，删除防火墙日志中的server->outside部分的数据
 * 循环删除list中的多个元素，应该使用迭代器iterator
 * @Author：jamesZhan
 * @Date：2018-01-18
 */
@Configuration
public class    Pretreatment {
//    @Autowired
//    private FwlogService fwlogService;
    //将防火墙deny的以及server->outside的数据全都删除

    public static List<Fwlog> fwlogList = new LinkedList<>();
    public void pretreatment(Rawfwlog rawfwlog){
        //数据清理
//        bean 注入
        FwlogService fwlogService = (FwlogService) SpringUtil.getBean("fwlogService");

        if (rawfwlog.getAction() != 0 && rawfwlog.getSafetydomain() != 0){
            Fwlog fwlog = new Fwlog();
            fwlog.setTime(rawfwlog.getProducetime());
            fwlog.setSrcip(rawfwlog.getOriginalsrcip());
            fwlog.setSrcport(rawfwlog.getOriginalsrcport());
            fwlog.setDestip(rawfwlog.getConverteddestip());
            fwlog.setDestport(rawfwlog.getConverteddestport());
            fwlog.setNumber(1);
//            System.out.println(fwlog);
            fwlogList.add(fwlog);
            if (fwlogService == null){
                System.out.println("fwlogService is null!!!");
            }
            fwlogService.save(fwlog);
        }
    }
}
