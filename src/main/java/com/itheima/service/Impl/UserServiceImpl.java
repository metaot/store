package com.itheima.service.Impl;

import com.itheima.dao.UserDao;
import com.itheima.domain.User;
import com.itheima.service.UserService;
import com.itheima.utils.BeanFactory;

import java.sql.SQLException;

public class UserServiceImpl implements UserService {

    private UserDao dao= BeanFactory.newInstance(UserDao.class);

    @Override
    public void save(User user) {

        try {
            dao.insert(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public User login(String username, String password) {
        User user=null;

        try {
            user=dao.login(username,password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public long checkUsername(String username) {
        long count=0L;

        try {
           count=dao.cheackusername(username);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }
}
