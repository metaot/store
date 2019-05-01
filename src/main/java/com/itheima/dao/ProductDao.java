package com.itheima.dao;

import com.itheima.domain.Product;

import java.sql.SQLException;
import java.util.List;

public interface ProductDao {

    List<Product> queryIsNew() throws SQLException;

    List<Product> quertIsHot() throws SQLException;

    Product queryByid(String id)throws SQLException;

    List<Product> queryByPage(String cid,int pageNumber,int  pageSize)throws SQLException;

    long queryByCid(String cid)throws SQLException;

    List<Product> queryByPname(String pname)throws SQLException;

    Product queryInfo(String pname)throws SQLException;

    List<Product> queryAllByPage(int pageNumber,int pageSize)throws SQLException;

    long queryAllCount()throws SQLException;

    void addProduct(Product product)throws SQLException;
}
