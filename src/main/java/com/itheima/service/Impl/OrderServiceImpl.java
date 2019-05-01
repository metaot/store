package com.itheima.service.Impl;

import com.itheima.dao.OrderDao;
import com.itheima.domain.OrderItem;
import com.itheima.domain.Orders;
import com.itheima.domain.PageBean;
import com.itheima.service.OrderService;
import com.itheima.utils.BeanFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class OrderServiceImpl implements OrderService{

    private OrderDao dao= BeanFactory.newInstance(OrderDao.class);

    @Override
    public void addOrder(Orders orders, List<OrderItem> list) {
        Connection conn=null;

        try {
           conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/store335",
                    "root","root");

           conn.setAutoCommit(false);

           dao.insertOrder(conn,orders);

            for (OrderItem orderItem : list) {
                dao.insertOrderItem(conn,orderItem);
            }

            conn.commit();
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public PageBean<Orders> getPageBean(int pageNumber, int pageSize, String uid) {
        PageBean<Orders> bean=null;

        try {
            bean=new PageBean<>();

            bean.setPageSize(pageSize);

            bean.setPageNumber(pageNumber);

            bean.setData(dao.myOrderWithPage(pageNumber,pageSize,uid));

            bean.setTotal((int)dao.getOidCount(uid));

            bean.setTotalPage(bean.getTotalPage());

            System.out.println(bean.getTotal());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return bean;
    }

    @Override
    public Orders getInfoByOid(String oid) {
        Orders orders=null;

        try {
            orders= dao.getInfoByOid(oid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    @Override
    public void updateOrder(String name, String address, String telephone, String oid) {
        try {
            dao.updateOrder(name,address,telephone,oid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateOrderState(String oid) {
        try {
            dao.updateOrderState(oid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
