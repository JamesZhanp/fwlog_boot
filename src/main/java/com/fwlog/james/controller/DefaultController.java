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
    public String index(HttpSession session){
        if(session.getAttribute(WebSecurityConfig.SESSION_KEY) == null){
            return "redirect:/fwlog/login";
        }else{
            return "pages/index";
        }
    }

    @GetMapping("/fwlog/securityAna")
    public String securityAna(HttpSession session){
//        避免出现用户没有登录就直接访问页面的情况
        if(session.getAttribute(WebSecurityConfig.SESSION_KEY) == null){
            return "redirect:/fwlog/login";
        }else{
            return "pages/securityAna";
        }
    }

    @GetMapping("/fwlog/securityTab")
    public String securityTab(HttpSession session){
        if(session.getAttribute(WebSecurityConfig.SESSION_KEY) == null){
            return "redirect:/fwlog/login";
        }else{
            return "pages/securityTab";
        }
    }

    @GetMapping("/fwlog/trafficAna")
    public String trafficAna(HttpSession session){
        if(session.getAttribute(WebSecurityConfig.SESSION_KEY) == null){
            return "redirect:/fwlog/login";
        }else{
            return "pages/trafficAna";
        }
    }
    @GetMapping("/fwlog/fileLoad")
    public String fileLoad(HttpSession session){
//        if(session.getAttribute(WebSecurityConfig.SESSION_KEY) == null){
//            return "redirect:/fwlog/login";
//        }else{
            return "pages/fileLoad";
//        }
    }

    @GetMapping("/fwlog/logout")
    public String logout(HttpSession session){
//        清除session内部的数据
        session.removeAttribute(WebSecurityConfig.SESSION_KEY);
        return "redirect:/fwlog/login";
    }
}
