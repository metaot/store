package com.itheima.dao;

import com.itheima.domain.Category;

import java.sql.SQLException;
import java.util.List;

public interface CategoryDao {
    List<Category> query()throws SQLException;

    String querycname(String pid)throws SQLException;

    void addcategory(String cname)throws SQLException;

    Category queryByCid(String cid)throws SQLException;

    void updateCategory(String cname,String cid)throws SQLException;

    void deleteCategory(String cid)throws SQLException;
}
