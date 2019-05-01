package com.itheima.service.Impl;

import com.itheima.dao.ProductDao;
import com.itheima.domain.PageBean;
import com.itheima.domain.Product;
import com.itheima.service.ProductService;
import com.itheima.utils.BeanFactory;

import java.sql.SQLException;
import java.util.List;

public class ProductServiceImpl implements ProductService {
    private ProductDao dao= BeanFactory.newInstance(ProductDao.class);

    @Override
    public List<Product> findIsNew() {
        List<Product> list=null;
        try {
            list=dao.queryIsNew();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Product> findIsHot() {
        List<Product> list=null;
        try {
            list=dao.quertIsHot();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Product findById(String id) {
        Product product=null;
        try {
            product=dao.queryByid(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product;
    }

    @Override
    public PageBean<Product> getPageBean(String cid, int pageNumber, int pageSize) {
        PageBean<Product> bean=null;
        try {
            bean=new PageBean<>();
            bean.setPageNumber(pageNumber);

            bean.setPageSize(pageSize);

            bean.setTotalPage(bean.getTotalPage());

            bean.setTotal((int)dao.queryByCid(cid));

            bean.setData(dao.queryByPage(cid,pageNumber,pageSize));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bean;
    }

    @Override
    public List<Product> findByPname(String pname) {
        List<Product> list=null;
        try {
           list=dao.queryByPname(pname);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Product findInfo(String pname) {
        Product product=null;
        try {
            product=dao.queryInfo(pname);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product;
    }

    @Override
    public PageBean<Product> getPBean(int pageNumber, int pageSize) {
        PageBean<Product> bean=null;

        try {
            bean=new PageBean<>();

            bean.setPageNumber(pageNumber);

            bean.setPageSize(pageSize);

            bean.setTotalPage(bean.getTotalPage());

            bean.setData(dao.queryAllByPage(pageNumber,pageSize));

            bean.setTotal((int)dao.queryAllCount());

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return bean;
    }

    @Override
    public void addProduct(Product product) {
        try {
            dao.addProduct(product);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
