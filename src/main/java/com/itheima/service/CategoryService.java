package com.itheima.service;

import com.itheima.Exception.canNotDeleteException;
import com.itheima.domain.Category;

import java.util.List;

public interface CategoryService {
    List<Category> find();

    String findcname(String pid);

    void addcategory(String cname);

    void clearRedis();

    Category findByCid(String uid);

    void editCategory(String cname,String cid);

    void deleteCategory(String cid)throws canNotDeleteException;
}
