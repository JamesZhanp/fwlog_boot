package com.fwlog.james.analysis.preAnalysis;

import com.fwlog.james.entity.Rawfwlog;
import com.fwlog.james.exception.FileTooLarge;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 实时分析日志文件当有新的日志产生时读取进来
 * @Author jamesZhan
 * @Date 2018-01-19
 */
public class RealTimeReadFile {
    private File logFile = null;
    private static long lastLogFileSize = 0;//上次读取日志的大小
    private static long newLogFileSize = 0;//本次读取日志的大小
    public static List<Rawfwlog> rawfwlogs = new LinkedList<>();

//    确定日志文件的位置，并且读取日志文件的大小
    public RealTimeReadFile(File logFile){
        this.logFile = logFile;
        lastLogFileSize = logFile.length();
    }

    /**
     * 监控日志文件的大小
     */
    public void monitorLogFileIncrease(File logFile){
        lastLogFileSize = logFile.length();
        //创建线程来读取日志文件容量的变化
        new Thread(()->{
            while(true){
//                线程休眠1s
                try{
                    TimeUnit.SECONDS.sleep(1);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
//                再次读取日志文件的大小
                newLogFileSize = logFile.length();
                RandomAccessFile randomFile = null;
                try{
                    randomFile = new RandomAccessFile(logFile,"r");
                    randomFile.seek(lastLogFileSize);
                    while(true){
                        String nextLine = randomFile.readLine();
                        //the nextLine add to the linkList
                        if(nextLine != null && !nextLine.equals("")){
                            //utf-8 的编码格式
                            nextLine = new String(nextLine.getBytes("ISO-8859-1"),"UTF-8");
                            Rawfwlog newFlog = new RawFwlogRegex().ParseRawLog(nextLine);
                            rawfwlogs.add(newFlog);

                            //prevent the fwlogs is so large
                            if(rawfwlogs.size() > 100 && rawfwlogs.size() < 1000){
                                try{
                                    Thread.sleep(100);
                                }catch(InterruptedException e1){
                                    e1.printStackTrace();
                                }
                            }else if(rawfwlogs.size() >= 1000 && rawfwlogs.size() < 10000){
                                try{
                                    Thread.sleep(1000);
                                }catch(InterruptedException e1){
                                    e1.printStackTrace();
                                }
                            }else{
                                throw new FileTooLarge();
                            }
                        }
                    }
                } catch(IOException e){
                    e.printStackTrace();
                }catch (FileTooLarge e){
                    e.printStackTrace();
                }finally{
                    try{
                        if(randomFile != null)
                            randomFile.close();
                    }catch(IOException e){
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
