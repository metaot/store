package com.itheima.web.servlet;

import com.itheima.domain.PageBean;
import com.itheima.domain.Product;
import com.itheima.service.ProductService;
import com.itheima.utils.BeanFactory;
import com.itheima.web.view.Result;
import net.sf.json.JSON;
import net.sf.json.JSONObject;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@WebServlet("/product")
public class ProductServlet extends BaseServlet {

    private ProductService service= BeanFactory.newInstance(ProductService.class);

    public void findNewAndHot(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Product> isNew = service.findIsNew();

        List<Product> isHot = service.findIsHot();

        HashMap<String, List<Product>> map = new HashMap<>();

        map.put("isnew",isNew);

        map.put("ishot",isHot);

        Result result = new Result(Result.SUCCESS, "最新,热门商品查询完毕", map);

        String s = JSONObject.fromObject(result).toString();

        response.getWriter().write(s);
    }

    public void findById(HttpServletRequest request,HttpServletResponse response) throws IOException {
        String pid = request.getParameter("pid");

        Product product = service.findById(pid);

        Result result = new Result(Result.SUCCESS, "商品详情信息查询完毕", product);

        String s = JSONObject.fromObject(result).toString();

        response.getWriter().write(s);
    }

    public void findByPage(HttpServletRequest request,HttpServletResponse response) throws IOException {
        String pageNumber = request.getParameter("pageNumber");
        String cid = request.getParameter("cid");
        int pageSize=12;
        if(pageNumber==null||"".equals(pageNumber)||"null".equals(pageNumber)){
            pageNumber="1";
        }

        PageBean<Product> pageBean = service.getPageBean(cid, Integer.parseInt(pageNumber), pageSize);

        Result result = new Result(Result.SUCCESS, "查询成功", pageBean);
        String s = JSONObject.fromObject(result).toString();
        response.getWriter().write(s);
    }

    public void search(HttpServletRequest request,HttpServletResponse response) throws IOException {
        String pname = request.getParameter("pname");

        List<Product> productList = service.findByPname(pname);

        Result result = new Result(Result.SUCCESS, "搜索查询成功", productList);

        String s = JSONObject.fromObject(result).toString();

        response.getWriter().write(s);
    }

    public void findinfo(HttpServletRequest request,HttpServletResponse response) throws IOException {
        String input = request.getParameter("input");

        Product product = service.findInfo(input);

        Result result = new Result(Result.SUCCESS, "查询成功", product);

        String s = JSONObject.fromObject(result).toString();

        response.getWriter().write(s);
    }
}
