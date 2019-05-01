package com.itheima.domain;

public class CartItem {
    private  Product product;
    private  int count;//数量
    private  double subTotal;//小计

    public CartItem() {
    }

    public CartItem(Product product, int count, double subTotal) {
        this.product = product;
        this.count = count;
        this.subTotal = subTotal;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public double getSubTotal() {
        return product.getShop_price()*count;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal=subTotal;
    }

    @Override
    public String toString() {
        return "CartItem{" +
                "product=" + product +
                ", count=" + count +
                ", subTotal=" + subTotal +
                '}';
    }
}
