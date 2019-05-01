package com.itheima.domain;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Cart {
    private double total;//总金额
    private Map<String,CartItem> itemMap = new HashMap<>();//购物项

    public Cart() {
    }

    public void clearItem(){
        itemMap.clear();
        total=0;
    }

    public void removeItem(String pid){
        CartItem cartItem = itemMap.remove(pid);

        total-=cartItem.getSubTotal();
    }

    public void addItem(CartItem cartItem){
        String pid = cartItem.getProduct().getPid();

        if(itemMap.containsKey(pid)){
            CartItem olditem = itemMap.get(pid);

            int count = olditem.getCount();

            count+=cartItem.getCount();

            olditem.setCount(count);

            itemMap.put(pid,olditem);
        }else {

            itemMap.put(pid,cartItem);
        }

        total+=cartItem.getSubTotal();
    }

    public Cart(double total, Map<String, CartItem> itemMap) {
        this.total = total;
        this.itemMap = itemMap;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Collection<CartItem> getItemMap() {
        return itemMap.values();
    }

    public void setItemMap(Map<String, CartItem> itemMap) {
        this.itemMap = itemMap;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "total=" + total +
                ", itemMap=" + itemMap +
                '}';
    }
}
