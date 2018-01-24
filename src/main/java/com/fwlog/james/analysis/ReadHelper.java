package com.fwlog.james.analysis;

import org.junit.Test;

import java.io.*;
import java.util.Scanner;

/**
 * 读取一个日志文件，并且向另一个日志文件中写入日志
 * @author jamesZhan
 * @date 2018-01-22
 */
public class ReadHelper {
    @Test
    public void readAndWriteFile() throws IOException,InterruptedException{
        File file1 = new File("E://log//log1.txt");
        File file2 = new File("E://log//log_1.txt");
        InputStreamReader read = new InputStreamReader(new FileInputStream(file1),"gbk");
        BufferedReader br = new BufferedReader(read);
        OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(file2),"gbk");
        BufferedWriter writer = new BufferedWriter(write);
        boolean flag = true;
        String line = null;
        int i = 0;
        while (flag){
            line = br.readLine();
            System.out.println(line);
            if (line != null && !line.equals("")){
                i ++;
                if (i % 100 == 0){
                    Thread.sleep(10);
                }
                if (i == 10000){
                    break;
                }
                writer.write(line + "\n");
                writer.flush();
            }else{
                flag = false;
            }

        }
        Scanner scanner = new Scanner(System.in);
        String s = scanner.next();
        new FileOutputStream(file2,false);
        writer.write("");
        writer.close();
    }

    public static void main(String[] args) throws Exception {
        new ReadHelper().readAndWriteFile();
    }
}
