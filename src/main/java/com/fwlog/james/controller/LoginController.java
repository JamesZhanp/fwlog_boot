package com.fwlog.james.controller;

/**
 * created by jamesZhan on 2018/01/29
 * 登录控制器
 */

import com.fwlog.james.login.WebSecurityConfig;
import com.fwlog.james.entity.User;
import com.fwlog.james.service.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {
    @Autowired
    private LoginService loginService;
    private static Logger logger = LoggerFactory.getLogger(LoginController.class);
    @PostMapping("/fwlog/index")
    public String loginVerify(String name, String password, HttpSession session){
        User user = new User();
        user.setName(name);
        user.setPassword(password);
        logger.info("登录的用户名：" + name + "密码为：" + password);
        boolean verify = loginService.verifyLogin(user);
        if (verify){
            session.setAttribute(WebSecurityConfig.SESSION_KEY,name);
            return "pages/index";
        }else{
            return "loginError";
//            return "redirect:/login";
        }
    }

}
