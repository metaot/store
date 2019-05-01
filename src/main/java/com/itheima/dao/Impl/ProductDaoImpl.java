package com.itheima.dao.Impl;

import com.itheima.dao.ProductDao;
import com.itheima.domain.Product;
import com.itheima.utils.C3P0Utils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.util.List;

public class ProductDaoImpl implements ProductDao {
    private QueryRunner qr=new QueryRunner(C3P0Utils.getDataSource());
    @Override
    public List<Product> queryIsNew() throws SQLException {
        String sql="select * from product where pflag=? order by pdate desc limit ?";
        Object[] params={0,9};
        return qr.query(sql,new BeanListHandler<>(Product.class),params);
    }

    @Override
    public List<Product> quertIsHot() throws SQLException {
        String sql="select * from product where pflag=? and is_hot=? order by pdate desc limit ?";
        Object[] params={0,1,9};
        return qr.query(sql,new BeanListHandler<>(Product.class),params);
    }

    @Override
    public Product queryByid(String id) throws SQLException {
        String sql="select * from product where pid=?";
        Object[] params={id};
        return qr.query(sql,new BeanHandler<>(Product.class),params);
    }

    @Override
    public List<Product> queryByPage(String cid,int pageNumber, int pageSize) throws SQLException {
        String sql="select * from product where cid=? limit ?,?";
        Object[] params={cid,(pageNumber-1)*pageSize,pageSize};
        return qr.query(sql,new BeanListHandler<>(Product.class),params);
    }

    @Override
    public long queryByCid(String cid) throws SQLException {
        String sql=" select count(*) from product where cid=?";
        Object[] params={cid};
        return  qr.query(sql,new ScalarHandler<>(),params);
    }

    @Override
    public List<Product> queryByPname(String pname) throws SQLException {
        String sql="select * from product where pname like ? limit ?";

        Object[] params={"%"+pname+"%",3};

        return qr.query(sql,new BeanListHandler<>(Product.class),params);
    }

    @Override
    public Product queryInfo(String pname) throws SQLException {
        String sql="select * from product where pname=?";

        Object[] params={pname};

        return qr.query(sql,new BeanHandler<>(Product.class),params);
    }

    @Override
    public List<Product> queryAllByPage(int pageNumber, int pageSize) throws SQLException {
        String sql="select * from product limit ?,?";

        Object[] params={(pageNumber-1)*pageSize,pageSize};

        return qr.query(sql,new BeanListHandler<>(Product.class),params);
    }

    @Override
    public long queryAllCount() throws SQLException {
        String sql="select count(*) from product";

        return qr.query(sql,new ScalarHandler<>());
    }

    @Override
    public void addProduct(Product product) throws SQLException {
        String sql="insert into product values (?,?,?,?,?,?,?,?,?,?)";

        Object[] params={product.getPid(),product.getPname(),product.getMarket_price(),
                         product.getShop_price(),product.getPimage(),product.getPdate(),
                         product.getIs_hot(),product.getPdesc(),product.getPflag(),product.getCid()};

        qr.update(sql,params);
    }
}
