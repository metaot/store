package com.itheima.service;

import com.itheima.domain.PageBean;
import com.itheima.domain.Product;

import java.util.List;

public interface ProductService {

    List<Product> findIsNew();

    List<Product> findIsHot();

    Product findById(String id);

    PageBean<Product> getPageBean(String cid,int pageNumber,int pageSize);

    List<Product> findByPname(String pname);

    Product findInfo(String pname);

    PageBean<Product> getPBean(int pageNumber,int pageSize);

    void addProduct(Product product);
}
