package com.fwlog.james.controller;

/**
 * created by jamesZhan on 2018/01/29
 * 登录控制器
 */

import com.fwlog.james.login.WebSecurityConfig;
import com.fwlog.james.entity.User;
import com.fwlog.james.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {
    @Autowired
    private LoginService loginService;

    @PostMapping("/loginVerify")
    public String loginVerify(String name, String password, HttpSession session){
        User user = new User();
        user.setName(name);
        user.setPassword(password);

        boolean verify = loginService.verifyLogin(user);
        if (verify){
            session.setAttribute(WebSecurityConfig.SESSION_KEY,name);
            return "index";
        }else{
            return "loginError";
//            return "redirect:/login";
        }
    }

}
