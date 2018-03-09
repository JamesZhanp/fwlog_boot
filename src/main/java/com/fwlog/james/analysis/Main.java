package com.fwlog.james.analysis;

import com.fwlog.james.analysis.eventAnalysis.EventAnalysis;
import com.fwlog.james.analysis.preAnalysis.LogRead;
import com.fwlog.james.analysis.preAnalysis.Pretreatment;
import com.fwlog.james.analysis.preAnalysis.RealTimeReadFile;
import com.fwlog.james.analysis.statisticsAnalysis.StatisticsAnalysis;
import com.fwlog.james.entity.Fwlog;
import com.fwlog.james.entity.Rawfwlog;
import com.fwlog.james.service.RawfwlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.util.List;

/**
 * 主程序，这里控制整个项目的进程
 * 分为两类：上传日志文件和实时读取日志文件
 *
 */
@Controller
@RequestMapping(value = "/fwlog")
public class Main {
    @Autowired
    private RawfwlogService rawfwlogService;
    private List<Rawfwlog> rawfwlogs = LogRead.rawfwlogs;
    private List<Rawfwlog> rawfwlogsRealTime = RealTimeReadFile.rawfwlogs;
    private List<Fwlog> fwlogList = Pretreatment.fwlogList;
    private boolean flag = true;

    @RequestMapping("/logFileRead")
    @ResponseBody
    public void readLogFileAnalysis(){
         new LogRead().readFile(new File("E://log//log1.txt"));
         while(flag){
             if (rawfwlogs.size() > 0){
                 Rawfwlog rawfwlog = rawfwlogs.get(0);
//                 把原始日志信息保存到数据表单中
                 rawfwlogService.save(rawfwlog);
//                 数据预处理之后将该信息保存到预处理表单当中
                 new Pretreatment().pretreatment(rawfwlog);
                 rawfwlogs.remove(0);
             }else{
                 flag = false;
             }
         }
         try{
//             System.out.println(fwlogList.size());
//             if (fwlogList.size() > 0){
//                 new StatisticsAnalysis().statisticsAnalysis(fwlogList.get(0));
//                 fwlogList.remove(0);
//             }
             new StatisticsAnalysis().analysis();
             new EventAnalysis().eventAnalysis();
         }catch (Exception e){
             e.printStackTrace();
         }

    }


    //监控文件的大小
    //往文件里面写入内容
    @RequestMapping("/timeFileRead")
    @ResponseBody
    public void timeFileRead(){
        //实时监控
        new RealTimeReadFile(new File("E://log//log1.txt")).monitorLogFileIncrease(new File("E://log//log1.txt"));
        while(flag){
            if (rawfwlogsRealTime.size() > 0){}
        }
    }

}
