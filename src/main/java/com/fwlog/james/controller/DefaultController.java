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

    @GetMapping("/")
    public String index(){
        return "login";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute(WebSecurityConfig.SESSION_KEY);
        return "redirect:/login";
    }
}
