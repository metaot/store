package com.itheima.web.servlet;

import com.itheima.Annotction.UserAnnotction;
import com.itheima.Constant.Constant;
import com.itheima.domain.*;
import com.itheima.service.OrderService;
import com.itheima.utils.BeanFactory;
import com.itheima.utils.PaymentUtil;
import com.itheima.utils.UUIDUtils;
import com.itheima.web.view.Result;
import net.sf.json.JSONObject;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@WebServlet("/order")
public class OrderServlet extends BaseServlet {
    private OrderService service= BeanFactory.newInstance(OrderService.class);

    @UserAnnotction
    public void submitOrder(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user = (User) request.getSession().getAttribute("user");
//
//        if(user==null){
//
//            Result result = new Result(Result.LOGOUT, "你尚未登录");
//
//            String s = JSONObject.fromObject(result).toString();
//
//            response.getWriter().write(s);
//
//            return;
//        }

        Cart cart = (Cart) request.getSession().getAttribute("cart");

        if(cart.getItemMap().size()==0){

            Result result = new Result(Result.FAILS, "购物车总空空如也,请添加后提交");

            String s = JSONObject.fromObject(result).toString();

            response.getWriter().write(s);

            return;
        }

        Orders orders = new Orders();

        orders.setOid(UUIDUtils.getUUID());

        orders.setTotal(cart.getTotal());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String date = sdf.format(new Date());

        orders.setOrdertime(date);

        orders.setState(Constant.WEI_FU_KUAn);

        orders.setUid(user.getUid());

        Collection<CartItem> itemMap = cart.getItemMap();

        List<OrderItem> orderitems = new ArrayList<>();

        for (CartItem cartItem : itemMap) {
            OrderItem orderItem = new OrderItem();

            orderItem.setPid(cartItem.getProduct().getPid());

            orderItem.setOid(orders.getOid());

            orderItem.setCount(cartItem.getCount());

            orderItem.setSubTotal(cartItem.getSubTotal());

            orderitems.add(orderItem);
        }

        service.addOrder(orders,orderitems);

        cart.clearItem();

        Result result = new Result(Result.SUCCESS, "订单已提交,后天来付款");

        String s = JSONObject.fromObject(result).toString();

        response.getWriter().write(s);
    }

    @UserAnnotction
    public void getOrderPageBean(HttpServletRequest request,HttpServletResponse response) throws IOException {

        User user = (User) request.getSession().getAttribute("user");
//
//        if(user==null){
//            Result result = new Result(Result.LOGOUT, "你尚未登录,请先登录");
//
//            String s = JSONObject.fromObject(result).toString();
//
//            response.getWriter().write(s);
//        }

        int pageNumber = Integer.parseInt(request.getParameter("pageNumber"));

        int pageSize=3;

        String uid = user.getUid();

        PageBean<Orders> pageBean = service.getPageBean(pageNumber, pageSize, uid);

        Result result = new Result(Result.SUCCESS, "查询成功", pageBean);

        String s = JSONObject.fromObject(result).toString();

        response.getWriter().write(s);
    }

    @UserAnnotction
    public void getInfoByOid(HttpServletRequest request,HttpServletResponse response) throws IOException {
//        User user = (User) request.getSession().getAttribute("user");
//
//        if(user==null){
//
//            Result result = new Result(Result.LOGOUT,"您尚未登陆");
//
//            response.getWriter().write(JSONObject.fromObject(result).toString());
//
//            return;
//        }

        String oid = request.getParameter("oid");

        Orders orders = service.getInfoByOid(oid);

        Result result = new Result(Result.SUCCESS, "订单详情查询成功", orders);

        String s = JSONObject.fromObject(result).toString();

        response.getWriter().write(s);
    }

    public void callback(HttpServletRequest request,HttpServletResponse response) throws Exception {
        String p1_MerId = request.getParameter("p1_MerId");
        String r0_Cmd = request.getParameter("r0_Cmd");
        String r1_Code = request.getParameter("r1_Code");
        String r2_TrxId = request.getParameter("r2_TrxId");
        String r3_Amt = request.getParameter("r3_Amt");
        String r4_Cur = request.getParameter("r4_Cur");
        String r5_Pid = request.getParameter("r5_Pid");
        String r6_Order = request.getParameter("r6_Order");
        String r7_Uid = request.getParameter("r7_Uid");
        String r8_MP = request.getParameter("r8_MP");
        String r9_BType = request.getParameter("r9_BType");
        String rb_BankId = request.getParameter("rb_BankId");
        String ro_BankOrderId = request.getParameter("ro_BankOrderId");
        String rp_PayDate = request.getParameter("rp_PayDate");
        String rq_CardNo = request.getParameter("rq_CardNo");
        String ru_Trxtime = request.getParameter("ru_Trxtime");
        // 身份校验 --- 判断是不是支付公司通知你
        String hmac = request.getParameter("hmac");
        String keyValue = ResourceBundle.getBundle("merchantInfo").getString(
                "keyValue");

        // 自己对上面数据进行加密 --- 比较支付公司发过来hamc
        boolean isValid = PaymentUtil.verifyCallback(hmac, p1_MerId, r0_Cmd,
                r1_Code, r2_TrxId, r3_Amt, r4_Cur, r5_Pid, r6_Order, r7_Uid,
                r8_MP, r9_BType, keyValue);
        if (isValid) {
            // 响应数据有效
            if (r9_BType.equals("1")) {
                // 浏览器重定向
                System.out.println("111");
                request.setAttribute("msg", "您的订单号为:" + r6_Order + ",金额为:" + r3_Amt + "已经支付成功,等待发货~~");

                request.setAttribute("oid", r6_Order);
                request.getRequestDispatcher("/success.jsp").forward(request, response);
            } else if (r9_BType.equals("2")) {
                // 服务器点对点 --- 支付公司通知你
                System.out.println("付款成功！222");
                // 修改订单状态 为已付款
                // 回复支付公司
                response.getWriter().print("success");
            }
            //订单状态,修改为已经付款  传递某个订单
            service.updateOrderState(r6_Order);

        } else {
            // 数据无效
            System.out.println("数据被篡改！");
        }
    }

    public void pay(HttpServletRequest request,HttpServletResponse response) throws Exception{
        //接受参数
        String address=request.getParameter("address");
        String name=request.getParameter("name");
        String telephone=request.getParameter("telephone");
        String oid=request.getParameter("oid");



        //业务层方法,修改订单信息
        service.updateOrder(name,address,telephone,oid);


        // 组织发送支付公司需要哪些数据
        String pd_FrpId = request.getParameter("pd_FrpId");
        String p0_Cmd = "Buy";
        String p1_MerId = ResourceBundle.getBundle("merchantInfo").getString("p1_MerId");
        String p2_Order = oid;
        String p3_Amt = "0.01";
        String p4_Cur = "CNY";
        String p5_Pid = "";
        String p6_Pcat = "";
        String p7_Pdesc = "";
        // 支付成功回调地址 ---- 第三方支付公司会访问、用户访问
        // 第三方支付可以访问网址
        String p8_Url = ResourceBundle.getBundle("merchantInfo").getString("responseURL");
        String p9_SAF = "";
        String pa_MP = "";
        String pr_NeedResponse = "1";
        // 加密hmac 需要密钥
        String keyValue = ResourceBundle.getBundle("merchantInfo").getString("keyValue");
        String hmac = PaymentUtil.buildHmac(p0_Cmd, p1_MerId, p2_Order, p3_Amt,
                p4_Cur, p5_Pid, p6_Pcat, p7_Pdesc, p8_Url, p9_SAF, pa_MP,
                pd_FrpId, pr_NeedResponse, keyValue);


        //发送给第三方
        StringBuffer sb = new StringBuffer("https://www.yeepay.com/app-merchant-proxy/node?");
        sb.append("p0_Cmd=").append(p0_Cmd).append("&");
        sb.append("p1_MerId=").append(p1_MerId).append("&");
        sb.append("p2_Order=").append(p2_Order).append("&");
        sb.append("p3_Amt=").append(p3_Amt).append("&");
        sb.append("p4_Cur=").append(p4_Cur).append("&");
        sb.append("p5_Pid=").append(p5_Pid).append("&");
        sb.append("p6_Pcat=").append(p6_Pcat).append("&");
        sb.append("p7_Pdesc=").append(p7_Pdesc).append("&");
        sb.append("p8_Url=").append(p8_Url).append("&");
        sb.append("p9_SAF=").append(p9_SAF).append("&");
        sb.append("pa_MP=").append(pa_MP).append("&");
        sb.append("pd_FrpId=").append(pd_FrpId).append("&");
        sb.append("pr_NeedResponse=").append(pr_NeedResponse).append("&");
        sb.append("hmac=").append(hmac);

        //        respone.sendRedirect(sb.toString());
        Result re = new Result(Result.SUCCESS,"",sb.toString());
        response.getWriter().print(JSONObject.fromObject(re));

    }

}
