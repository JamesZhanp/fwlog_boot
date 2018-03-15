package com.fwlog.james.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/sys/user")
public class TestController {
    @RequestMapping("login")
    public Map<String,String> login(){
        Map<String,String> hashMap = new HashMap<>();
        hashMap.put("msg","登陆成功");
        return hashMap;
    }
}
