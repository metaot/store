package com.itheima.service.Impl;

import com.itheima.Exception.canNotDeleteException;
import com.itheima.dao.CategoryDao;
import com.itheima.dao.ProductDao;
import com.itheima.domain.Category;
import com.itheima.service.CategoryService;
import com.itheima.utils.BeanFactory;
import com.itheima.utils.JedisUtils;
import net.sf.json.JSONArray;
import redis.clients.jedis.Jedis;

import java.sql.SQLException;
import java.util.List;

public class CategoryServiceImpl implements CategoryService {
    private CategoryDao dao= BeanFactory.newInstance(CategoryDao.class);

    private ProductDao productDao=BeanFactory.newInstance(ProductDao.class);

    @Override
    public List<Category> find() {
        Jedis jedis = JedisUtils.getJedis();
        String category = jedis.get("category");
        if(category==null){
            try {
                List<Category> list = dao.query();
                String s = JSONArray.fromObject(list).toString();
                jedis.set("category", s);
                return list;
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }

        }else {
            JSONArray jsonObject = JSONArray.fromObject(category);
            List list = JSONArray.toList(jsonObject, Category.class);
            return list;
        }
    }

    @Override
    public String findcname(String pid){
        String cname=null;
        try {
            cname=dao.querycname(pid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cname;
    }

    @Override
    public void addcategory(String cname) {
        try {
            dao.addcategory(cname);
            clearRedis();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void clearRedis() {
      Jedis jedis=null;

      try {
          jedis=JedisUtils.getJedis();

          jedis.del("category");
      }catch (Exception e){
          e.printStackTrace();
      }finally {
         if(jedis!=null){
             jedis.close();
         }
      }

    }

    @Override
    public Category findByCid(String cid) {
        Category category=null;

        try {
            category=dao.queryByCid(cid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return category;
    }

    @Override
    public void editCategory(String cname, String cid) {
        try {
            dao.updateCategory(cname,cid);
            clearRedis();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteCategory(String cid) throws canNotDeleteException {
        try {
            int count=(int)productDao.queryByCid(cid);
            if(count==0){
                dao.deleteCategory(cid);

                clearRedis();
            }else {

                throw new canNotDeleteException("该分类下有商品,不得删除");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
