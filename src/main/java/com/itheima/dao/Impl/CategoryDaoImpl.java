package com.itheima.dao.Impl;

import com.itheima.dao.CategoryDao;
import com.itheima.domain.Category;
import com.itheima.utils.C3P0Utils;
import com.itheima.utils.UUIDUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.util.List;

public class CategoryDaoImpl implements CategoryDao {
    private QueryRunner qr=new QueryRunner(C3P0Utils.getDataSource());

    @Override
    public List<Category> query() throws SQLException {
      String sql="select * from category";

      return qr.query(sql,new BeanListHandler<>(Category.class));
    }

    @Override
    public String querycname(String pid) throws SQLException {
        String sql="SELECT cname FROM product JOIN category ON category.cid=product.cid WHERE pid=?";
        Object[] params={pid};
        return qr.query(sql,new ScalarHandler<>(),params);
    }

    @Override
    public void addcategory(String cname) throws SQLException {
        String sql="insert into category values (?,?)";

        Object[] params={UUIDUtils.getUUID(),cname};

        qr.update(sql,params);
    }

    @Override
    public Category queryByCid(String cid) throws SQLException {
        String sql="select * from category where cid=?";

        Object[] params={cid};

        return qr.query(sql,new BeanHandler<>(Category.class),params);
    }

    @Override
    public void updateCategory(String cname, String cid) throws SQLException {
        String sql="update category set cname=? where cid=?";

        Object[] params={cname,cid};

        qr.update(sql,params);
    }

    @Override
    public void deleteCategory(String cid) throws SQLException {
        String sql="delete from category where cid=?";

        Object[] params={cid};

        qr.update(sql,params);
    }
}
