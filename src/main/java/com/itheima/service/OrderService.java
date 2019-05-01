package com.itheima.service;

import com.itheima.domain.OrderItem;
import com.itheima.domain.Orders;
import com.itheima.domain.PageBean;

import java.util.List;

public interface OrderService {
    void addOrder(Orders orders, List<OrderItem> list);

    PageBean<Orders> getPageBean(int pageNumber,int pageSize,String uid);

    Orders getInfoByOid(String oid);

    void updateOrder(String name,String address,String telephone,String oid);

    void updateOrderState(String oid);
}
