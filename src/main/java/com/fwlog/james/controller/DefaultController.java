package com.fwlog.james.controller;

import com.fwlog.james.login.WebSecurityConfig;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

/**
 * 默认控制器
 * created by jamesZhan on 2018/01/29
 */

@Controller
public class DefaultController {


    @GetMapping("/fwlog/login")
    public String login(){
        return "login";
    }

    @GetMapping("/fwlog/index")
    public String index(){
        return "pages/index";
    }

    @GetMapping("/fwlog/securityAna")
    public String securityAna(){
        return "pages/securityAna";
    }

    @GetMapping("/fwlog/securityTab")
    public String securityTab(){
        return "pages/securityTab";
    }

    @GetMapping("/fwlog/trafficAna")
    public String trafficAna(){
        return "pages/trafficAna";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
//        清除session内部的数据
        session.removeAttribute(WebSecurityConfig.SESSION_KEY);
        return "redirect:/fwlog/login";
    }
}
