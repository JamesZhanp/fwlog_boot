package com.fwlog.james.controller;

import com.fwlog.james.login.WebSecurityConfig;
import com.fwlog.james.entity.User;
import com.fwlog.james.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("fwlog")
public class LoginController {
    @Autowired
    private LoginService loginService;

    @GetMapping("/")
    public String index(@SessionAttribute(WebSecurityConfig.SESSION_KEY)String account, Model model){
        return "index";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

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
            return "redirect:/login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute(WebSecurityConfig.SESSION_KEY);
        return "redirect:/login";
    }
}
