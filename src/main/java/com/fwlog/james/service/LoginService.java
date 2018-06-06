package com.fwlog.james.service;

import com.fwlog.james.repository.LoginRepository;
import com.fwlog.james.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LoginService {
    @Autowired
    private LoginRepository loginRepository;

    public Map<String,String> login(String name,String password){
        Map<String,String> map = new HashMap<>();
        if (name.isEmpty() || password.isEmpty()){
            map.put("msg","用户名和密码不能为空");
            return map;
        }
        User user = findUserByName(name);
        if (user == null){
            map.put("msg","用户不存在");
            return map;
        }
        if (!password.equals(user.getPassword())){
            map.put("msg","密码错误");
            return map;
        }

        map.put("msg","success");
        return map;

    }
    public User findUserByName(String name){
        return loginRepository.findByName(name);
    }
    public boolean verifyLogin(User user){
        List<User> userList = loginRepository.findByNameAndPassword(user.getName(),user.getPassword());
        return userList.size() > 0;
    }


}
