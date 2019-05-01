package com.itheima.domain;

import java.util.List;

public class PageBean<T> {
    // 当前页码
    private int pageNumer;
    //每页条数
    private int pageSize;
    // 总条数
    private int total;// 数据库中查询的
    // 总页数
    private int totalPage;// 根据自己算出来  Math.ceil(total/pageSize)
    // 当前页的数据
    private List<T> data;

    public PageBean() {
    }

    public PageBean(int pageNumer, int pageSize, int total, int totalPage,
                    List<T> data) {
        this.pageNumer = pageNumer;
        this.pageSize = pageSize;
        this.total = total;
        this.totalPage = totalPage;
        this.data = data;
    }

    public int getPageNumber() {
        return pageNumer;
    }

    public void setPageNumber(int pageNumer) {
        this.pageNumer = pageNumer;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotalPage() {
        return (int)Math.ceil(total*1.0/pageSize);
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "PageBean{" +
               "pageNumer=" + pageNumer +
               ", pageSize=" + pageSize +
               ", total=" + total +
               ", totalPage=" + totalPage +
               ", data=" + data +
               '}';
    }
}
