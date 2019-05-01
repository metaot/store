package com.itheima.service;

import com.itheima.domain.User;

public interface UserService {

    void save(User user);

    User login(String username,String password);

    long checkUsername(String username);
}
