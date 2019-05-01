package com.itheima.domain;

public class OrderItem {
    //订单项数量
    private Integer count;
    //订单项小计
    private Double subTotal;
    private String pid;
    private String oid;

    public OrderItem() {
    }

    public OrderItem(Integer count, Double subTotal, String pid, String oid) {
        this.count = count;
        this.subTotal = subTotal;
        this.pid = pid;
        this.oid = oid;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(Double subTotal) {
        this.subTotal = subTotal;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "count=" + count +
                ", subTotal=" + subTotal +
                ", pid='" + pid + '\'' +
                ", oid='" + oid + '\'' +
                '}';
    }
}
