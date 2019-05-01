package com.itheima.dao;

import com.itheima.domain.OrderItem;
import com.itheima.domain.Orders;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface OrderDao {
    void insertOrder(Connection conn,Orders orders)throws SQLException;

    void insertOrderItem(Connection conn,OrderItem orderItem)throws SQLException;

    List<Orders> myOrderWithPage(int pageNumber,int pageSize,String uid)throws SQLException;

    long getOidCount(String uid)throws SQLException;

    Orders getInfoByOid(String oid)throws SQLException;

    void updateOrder(String name, String address, String telephone, String oid)throws SQLException;

    void updateOrderState(String oid)throws SQLException;
}
