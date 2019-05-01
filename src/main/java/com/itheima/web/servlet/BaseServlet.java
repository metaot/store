package com.itheima.web.servlet;

import com.itheima.Annotction.UserAnnotction;
import com.itheima.domain.User;
import com.itheima.web.view.Result;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

public class BaseServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html;charset=utf-8");

        try {
            String md = request.getParameter("method");

            Class clazz = this.getClass();

            Method method = clazz.getMethod(md, HttpServletRequest.class, HttpServletResponse.class);

            boolean present = method.isAnnotationPresent(UserAnnotction.class);

            if(present){

                User user = (User) request.getSession().getAttribute("user");

                if(user==null){

                    Result result = new Result(Result.LOGOUT,"您尚未登陆");

                    response.getWriter().write(JSONObject.fromObject(result).toString());

                    return;
                }
            }

            method.invoke(this,request,response);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
