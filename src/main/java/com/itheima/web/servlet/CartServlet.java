package com.itheima.web.servlet;

import com.itheima.domain.Cart;
import com.itheima.domain.CartItem;
import com.itheima.domain.Product;
import com.itheima.service.ProductService;
import com.itheima.utils.BeanFactory;
import com.itheima.web.view.Result;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/cart")
public class CartServlet extends BaseServlet {
    private ProductService service= BeanFactory.newInstance(ProductService.class);

    public void addCart(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String pid = request.getParameter("pid");

        int count = Integer.parseInt(request.getParameter("count"));

        Product product = service.findById(pid);

        CartItem cartItem = new CartItem();

        cartItem.setCount(count);

        cartItem.setProduct(product);

        cartItem.setSubTotal(cartItem.getSubTotal());

        Cart cart = getCart(request);

        cart.addItem(cartItem);

        Result result = new Result(Result.SUCCESS, "添加购物车成功");

        String s = JSONObject.fromObject(result).toString();

        response.getWriter().write(s);
    }

    public Cart getCart(HttpServletRequest request){
        Cart cart = (Cart)request.getSession().getAttribute("cart");

        if(cart==null){

            cart=new Cart();

            request.getSession().setAttribute("cart",cart);

            return cart;
        }
        return cart;
    }
    public void showCart(HttpServletRequest request,HttpServletResponse response) throws IOException {
        Cart cart = getCart(request);

        Result result = new Result(Result.SUCCESS, "购物车查询成功", cart);

        String s = JSONObject.fromObject(result).toString();

        response.getWriter().write(s);
    }

    public void removeItem(HttpServletRequest request,HttpServletResponse response) throws IOException {
        String pid = request.getParameter("pid");

        Cart cart = getCart(request);

        cart.removeItem(pid);

        Result result = new Result(Result.SUCCESS, "删除成功");

        String s = JSONObject.fromObject(result).toString();

        response.getWriter().write(s);
    }

    public void clearItem(HttpServletRequest request,HttpServletResponse response) throws IOException {
        Cart cart = getCart(request);

        cart.clearItem();

        Result result = new Result(Result.SUCCESS, "购物车已经清空");

        String s = JSONObject.fromObject(result).toString();

        response.getWriter().write(s);
    }
}
