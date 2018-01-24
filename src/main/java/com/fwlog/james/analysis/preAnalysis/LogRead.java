package com.fwlog.james.analysis.preAnalysis;

/**
 * 读历史日志文件操作
 * @author jamesZhan
 * @date 2018-01-19
 */

import com.fwlog.james.entity.Rawfwlog;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

public class LogRead {
    public static List<Rawfwlog> rawfwlogs = new LinkedList<>();
    public void readFile(File file){
        BufferedReader reader = null;
        try{
            //to read the chinese
            InputStreamReader read = new InputStreamReader(new FileInputStream(file),"gbk");

            reader = new BufferedReader(read);//读文件
            String tempString = null;
            //has next line
            while((tempString = reader.readLine())!=null) {

//                System.out.println(new RawFwlogRegex().ParseRawLog(tempString));
                rawfwlogs.add(new RawFwlogRegex().ParseRawLog(tempString));
//
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if (reader != null){
                try{
                    reader.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
//        new LogRead().readFile(new File("E:\\log\\log1.txt"));
    }
}
