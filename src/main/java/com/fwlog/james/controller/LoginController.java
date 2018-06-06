package com.fwlog.james.controller;

/**
 * created by jamesZhan on 2018/01/29
 * 登录控制器
 */

import com.fwlog.james.login.WebSecurityConfig;
import com.fwlog.james.entity.User;
import com.fwlog.james.service.LoginService;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
public class LoginController {
    @Autowired
    private LoginService loginService;
    private static Logger logger = LoggerFactory.getLogger(LoginController.class);
    @PostMapping("/fwlog/login")
    public String loginVerify(Model model, HttpServletResponse response, String name, String password, HttpSession session){
        Map<String,String> map = loginService.login(name, password);
        //登陆成功
        if (map.get("msg").equals("success")){
            session.setAttribute(WebSecurityConfig.SESSION_KEY,name);
            return "redirect:/fwlog/index";
        }else{
            model.addAttribute("msg",map.get("msg"));
            return "login";
        }
    }

}
