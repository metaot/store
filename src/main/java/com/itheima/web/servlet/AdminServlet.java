package com.itheima.web.servlet;

import com.itheima.Exception.canNotDeleteException;
import com.itheima.domain.Category;
import com.itheima.domain.PageBean;
import com.itheima.domain.Product;
import com.itheima.service.CategoryService;
import com.itheima.service.ProductService;
import com.itheima.utils.BeanFactory;
import com.itheima.utils.UUIDUtils;
import com.itheima.web.view.Result;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/admin")
public class AdminServlet extends BaseServlet {
     private CategoryService service= BeanFactory.newInstance(CategoryService.class);

     private ProductService productService=BeanFactory.newInstance(ProductService.class);

     public void categories(HttpServletRequest request, HttpServletResponse response) throws IOException {
         List<Category> categoryList = service.find();

         Result result = new Result(Result.SUCCESS, "分类信息查询成功", categoryList);

         String s = JSONObject.fromObject(result).toString();

         response.getWriter().write(s);
     }

     public void addCategory(HttpServletRequest request,HttpServletResponse response) throws IOException {
         String cname = request.getParameter("cname");

         service.addcategory(cname);

         Result result = new Result(Result.SUCCESS, "分类信息添加成功");

         String s = JSONObject.fromObject(result).toString();

         response.getWriter().write(s);
     }

     public void findByCid(HttpServletRequest request,HttpServletResponse response) throws IOException {
         String cid = request.getParameter("cid");

         Category category = service.findByCid(cid);

         Result result = new Result(Result.SUCCESS, "数据查询成功", category);

         String s = JSONObject.fromObject(result).toString();

         response.getWriter().write(s);
     }

     public void editCategory(HttpServletRequest request,HttpServletResponse response) throws IOException {
         String cname = request.getParameter("cname");

         String cid = request.getParameter("cid");

         service.editCategory(cname,cid);

         Result result = new Result(Result.SUCCESS, "分类信息修改完毕");

         String s = JSONObject.fromObject(result).toString();

         response.getWriter().write(s);
     }

     public void deleteCategory(HttpServletRequest request,HttpServletResponse response) throws IOException {
         String cid = request.getParameter("cid");

         try {
             service.deleteCategory(cid);

             Result result = new Result(Result.SUCCESS, "分类信息删除成功");

             String s = JSONObject.fromObject(result).toString();

             response.getWriter().write(s);
         } catch (canNotDeleteException e) {
             Result result = new Result(Result.FAILS, e.getMessage());

             System.out.println(e.getMessage());
             String s = JSONObject.fromObject(result).toString();

             response.getWriter().write(s);
         }
     }

     public  void getProductsAndPage(HttpServletRequest request,HttpServletResponse response) throws IOException {
         int pageNumber = Integer.parseInt(request.getParameter("pageNumber"));

         int pageSize=10;

         PageBean<Product> pBean = productService.getPBean(pageNumber, pageSize);

         Result result = new Result(Result.SUCCESS, "查询成功", pBean);

         String s = JSONObject.fromObject(result).toString();

         response.getWriter().write(s);
     }
}
