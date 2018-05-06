package com.fwlog.james.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;


@Controller
public class FileController {
//    控制台输出日志
    private  static final Logger logger = LoggerFactory.getLogger(FileController.class);
    //确定文件的上传文形式为post
    @RequestMapping(value="/fwlog/upload", method = RequestMethod.GET)
    public @ResponseBody String provideUploadInfo(){
        return "you can upload a file by posting to this same URL!";
    }

    @RequestMapping(value = "/fwlog/upload",method = RequestMethod.POST)
    public @ResponseBody String handleFileUpload(@RequestParam("name") String name, @RequestParam("file")MultipartFile file){
        if (file.isEmpty()){
            return "文件为空";
        }else{
            //获取文件名
            String fileName = file.getOriginalFilename();
            logger.info("上传的文件名为：" + fileName);
            //获取文件的后缀名
            String suffixName = fileName.substring(fileName.lastIndexOf("."));
            logger.info("文件的后缀名为：" + suffixName);
            //文件上传后的路径
            String filePath = "";
            File dest = new File(filePath + fileName);
            //检测是否存在目录
            if (!dest.getParentFile().exists()){
                dest.getParentFile().mkdirs();
            }
            try{
                file.transferTo(dest);
                return "上传成功";
            }catch (IllegalStateException e){
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }
            return "上传失败";
        }
    }

}
