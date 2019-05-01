package com.itheima.dao.Impl;

import com.itheima.Constant.Constant;
import com.itheima.dao.OrderDao;
import com.itheima.domain.OrderItem;
import com.itheima.domain.Orders;
import com.itheima.utils.C3P0Utils;
import com.itheima.web.view.OrderItemView;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class OrderDaoImpl implements OrderDao {
    private QueryRunner qr=new QueryRunner();

    private QueryRunner qr1=new QueryRunner(C3P0Utils.getDataSource());

    @Override
    public void insertOrder(Connection conn, Orders orders) throws SQLException {
      String sql="insert into orders (oid,ordertime,total,state,uid) values (?,?,?,?,?)";

      Object[] params={orders.getOid(),orders.getOrdertime(),orders.getTotal(),orders.getState(),orders.getUid()};

      qr.update(conn,sql,params);
    }

    @Override
    public void insertOrderItem(Connection conn, OrderItem orderItem) throws SQLException {
      String sql="insert into orderitem value (?,?,?,?)";

      Object[] params={orderItem.getCount(),orderItem.getSubTotal(),orderItem.getPid(),orderItem.getOid()};

      qr.update(conn,sql,params);
    }

    @Override
    public List<Orders> myOrderWithPage(int pageNumber, int pageSize, String uid) throws SQLException {
        String sql="select * from orders where uid = ? limit ?,?";

        Object[] params={uid,(pageNumber-1)*pageSize,pageSize};

        List<Orders> ordersList = qr1.query(sql, new BeanListHandler<>(Orders.class), params);

        for (Orders orders : ordersList) {

            String sql2="SELECT p.pid,p.pname,p.pimage,p.shop_price,o.count,o.subtotal FROM product p,orderitem o WHERE p.pid=o.pid AND oid=?";

            Object[] paramss={orders.getOid()};

            List<OrderItemView> list = qr1.query(sql2, new BeanListHandler<>(OrderItemView.class), paramss);

            orders.setOrderItemViews(list);
        }

        return ordersList;
    }

    @Override
    public long getOidCount(String uid) throws SQLException {
        String sql="select count(*) from orders where uid=?";

        Object[] params={uid};

        return qr1.query(sql,new ScalarHandler<>(),params);
    }

    @Override
    public Orders getInfoByOid(String oid) throws SQLException {
            String sql="select * from orders where oid=?";

            Object[] params={oid};

            Orders orders = qr1.query(sql, new BeanHandler<>(Orders.class), params);

            String sql2="SELECT p.pid,p.pname,p.pimage,p.shop_price,o.count,o.subtotal FROM product p,orderitem o WHERE p.pid=o.pid AND oid=?";

            Object[] paramss={orders.getOid()};

            List<OrderItemView> list = qr1.query(sql2, new BeanListHandler<>(OrderItemView.class), paramss);

            orders.setOrderItemViews(list);

            return orders;
    }

    @Override
    public void updateOrder(String name, String address, String telephone, String oid) throws SQLException {
        String sql="update orders set name=?,address=?,telephone=? where oid=?";

        Object[] params={name,address,telephone,oid};

        qr1.update(sql,params);
    }

    @Override
    public void updateOrderState(String oid) throws SQLException {
       String sql="update orders set state=? where oid=?";

       Object[] params={Constant.YI_FU_KUAN,oid};

       qr1.update(sql,params);
    }

}
