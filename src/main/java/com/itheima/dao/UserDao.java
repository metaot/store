package com.itheima.dao;

import com.itheima.domain.User;

import java.sql.SQLException;

public interface UserDao {
    void insert(User user)throws SQLException;

    User login(String username,String password)throws SQLException;

    long cheackusername(String username)throws SQLException;
}
