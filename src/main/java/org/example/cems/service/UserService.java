package org.example.cems.service;

import org.example.cems.mapper.UserMapper;
import org.example.cems.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserMapper userMapper;

    @Autowired
    PasswordEncoder passwordEncoder;

    public String registerUser(String email,String password,String username,String role){
        if(userMapper.existsByEmail(email)){
            throw  new RuntimeException("此信箱已被註冊");
        }

        String encodeedPassword = passwordEncoder.encode(password);
        User user = new User();
        user.setEmail(email);
        user.setPassword(encodeedPassword);
        user.setRole(role);
        user.setUsername(username);
        userMapper.insertUser(user);
        return "註冊成功";

    }

    public User findByUserId(long userId){
        return userMapper.findByUserId(userId);
    }


}
