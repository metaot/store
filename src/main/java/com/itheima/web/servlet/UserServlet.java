package com.itheima.web.servlet;

import com.itheima.domain.User;
import com.itheima.service.Impl.UserServiceImpl;
import com.itheima.service.UserService;
import com.itheima.utils.BeanFactory;
import com.itheima.utils.UUIDUtils;
import com.itheima.web.view.Result;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/user")
public class UserServlet extends BaseServlet {
    private UserService userService= BeanFactory.newInstance(UserService.class);

    public void regist(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, String[]> map = request.getParameterMap();

        User user=new User();

        try {
            BeanUtils.populate(user,map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        String id = UUIDUtils.getUUID();

        user.setUid(id);

        user.setState(1);

        user.setCode("wead-00062-wth");

        userService.save(user);

        Result result = new Result(Result.SUCCESS,"注册成功");

        String json = JSONObject.fromObject(result).toString();

        response.getWriter().write(json);
    }

    public void login(HttpServletRequest request,HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        User user = userService.login(username, password);
        if(user==null){
            Result result = new Result(Result.FAILS, "登录失败了,兄嘚~~~");
            String json = JSONObject.fromObject(result).toString();
            response.getWriter().write(json);
        }else {
            request.getSession().setAttribute("user",user);
            Cookie cookie = new Cookie("username",username);
            cookie.setPath(request.getContextPath());
            cookie.setMaxAge(60*10);
            cookie.setDomain("itheima335.com");
            response.addCookie(cookie);
            Result result = new Result(Result.SUCCESS,"登录成功~~");

            String s = JSONObject.fromObject(result).toString();

            response.getWriter().write(s);
        }
    }

    public void logout(HttpServletRequest request,HttpServletResponse response) throws IOException {
        request.getSession().invalidate();

        Cookie cookie = new Cookie("username", null);

        cookie.setPath(request.getContextPath());
        cookie.setMaxAge(-0);
        cookie.setDomain("itheima335.com");
        response.addCookie(cookie);

        Result result = new Result(Result.SUCCESS, "退出陈宫");
        String json = JSONObject.fromObject(result).toString();
        response.getWriter().write(json);
    }

    public void checkUsername(HttpServletRequest request,HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");

        long count = userService.checkUsername(username);

        if(count==0) {
            Result result = new Result(Result.SUCCESS, "这个名字还没有人使用,要抓紧呀");

            String s = JSONObject.fromObject(result).toString();

            response.getWriter().write(s);
        }else {
            Result result = new Result(Result.FAILS, "这个名字已经有人使用了,需要换个名字");

            String s = JSONObject.fromObject(result).toString();

            response.getWriter().write(s);
        }
    }
}
