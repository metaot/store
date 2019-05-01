package com.itheima.dao.Impl;

import com.itheima.dao.UserDao;
import com.itheima.domain.User;
import com.itheima.utils.C3P0Utils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;

public class UserDaoImpl implements UserDao {

    private QueryRunner qr=new QueryRunner(C3P0Utils.getDataSource());

    @Override
    public void insert(User user) throws SQLException {
        String sql="insert into user values(?,?,?,?,?,?,?,?,?,?)";

        Object[] params={user.getUid(),user.getUsername(),user.getPassword(),

                         user.getName(),user.getEmail(),user.getBirthday(),

                         user.getGender(),user.getState(),user.getCode(),

                         user.getRemark()};

        qr.update(sql,params);
    }

    @Override
    public User login(String username, String password) throws SQLException {
        String sql="select * from user where username=? and password=?";

        Object[] params={username,password};

        return qr.query(sql,new BeanHandler<>(User.class),params);
    }

    @Override
    public long cheackusername(String username) throws SQLException {
        String sql="select count(*) from user where username= ?";

        Object[] params={username};

        return qr.query(sql,new ScalarHandler<>(),params);
    }
}
