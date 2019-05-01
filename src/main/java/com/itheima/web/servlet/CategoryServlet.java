package com.itheima.web.servlet;

import com.itheima.domain.Category;
import com.itheima.service.CategoryService;
import com.itheima.utils.BeanFactory;
import com.itheima.web.view.Result;
import net.sf.json.JSONObject;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/category")
public class CategoryServlet extends BaseServlet {

    private CategoryService service= BeanFactory.newInstance(CategoryService.class);

    public void findAll(HttpServletRequest request,HttpServletResponse response)
            throws IOException {
        List<Category> list = service.find();

        Result result = new Result(Result.SUCCESS, "查询成功", list);

        String s = JSONObject.fromObject(result).toString();

        response.getWriter().write(s);
    }

    public void findcname(HttpServletRequest request,HttpServletResponse response) throws IOException {
        String pid = request.getParameter("pid");

        String cname = service.findcname(pid);

        Result result = new Result(Result.SUCCESS, "查询成功", cname);

        String s = JSONObject.fromObject(result).toString();

        response.getWriter().write(s);
    }
}
