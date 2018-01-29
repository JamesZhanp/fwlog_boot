package com.fwlog.james.service;

import com.fwlog.james.repository.LoginRepository;
import com.fwlog.james.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoginService {
    @Autowired
    private LoginRepository loginRepository;

    public boolean verifyLogin(User user){
        List<User> userList = loginRepository.findByNameAndPassword(user.getName(),user.getPassword());
        return userList.size() > 0;
    }
}
