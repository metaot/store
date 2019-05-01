package com.itheima.web.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/*")
public class AxajFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {

        HttpServletRequest request=(HttpServletRequest) req;

        HttpServletResponse response=(HttpServletResponse)resp;

        response.setHeader("Access-Control-Allow-Origin", "http://www.itheima335.com:8020");

        response.setHeader("Access-Control-Allow-Credentials","true");

        chain.doFilter(request,response);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
