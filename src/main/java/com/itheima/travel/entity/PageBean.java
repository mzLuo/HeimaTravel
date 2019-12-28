package com.itheima.travel.entity;

import java.util.List;

public class PageBean<T> {
    //从数据库中得到
    private List<T> data;//这页的数据
    private int count;//总记录数

    //由用户提交
    private int current;//当前页
    private int size;//页面大小

    //由其他属性计算
    private int first;//第一页
    private int previous;//上一页
    private int next;//下一页
    private int total;//总页数


    public PageBean() {
    }

    public PageBean(List<T> data, int count, int current, int size) {
        this.data = data;
        this.count = count;
        this.current = current;
        this.size = size;
    }

    /**
     * 获取 从数据库中得到
     * @return data 从数据库中得到
     */
    public List<T> getData() {
        return data;
    }

    /**
     * 设置 从数据库中得到
     * @param data 从数据库中得到
     */
    public void setData(List<T> data) {
        this.data = data;
    }

    /**
     * 获取
     * @return count
     */
    public int getCount() {
        return count;
    }

    /**
     * 设置
     * @param count
     */
    public void setCount(int count) {
        this.count = count;
    }

    /**
     * 获取 由用户提交
     * @return current 由用户提交
     */
    public int getCurrent() {
        return current;
    }

    /**
     * 设置 由用户提交
     * @param current 由用户提交
     */
    public void setCurrent(int current) {
        this.current = current;
    }

    /**
     * 获取
     * @return size
     */
    public int getSize() {
        return size;
    }

    /**
     * 设置
     * @param size
     */
    public void setSize(int size) {
        this.size = size;
    }

    /**
     * 获取 由其他属性计算
     * @return first 由其他属性计算
     */
    public int getFirst() {
        return first;
    }

    /**
     * 设置 由其他属性计算
     * @param first 由其他属性计算
     */
    public void setFirst(int first) {
        this.first = first;
    }

    /**
     * 如果当前页大于第一页，上一页等于当前页-1
     * 获取
     * @return previous
     */
    public int getPrevious() {
        if (getCurrent() > 1) {
            return getCurrent()-1;
        } else {
            return 1;
        }
    }

    /**
     * 设置
     * @param previous
     */
    public void setPrevious(int previous) {
        this.previous = previous;
    }

    /**
     * 如果当前页小于最后一页，下一页等于当前页+1
     * 获取
     * @return next
     */
    public int getNext() {
        if (getCurrent() < getTotal()) {
            return getCurrent() + 1;
        } else {
            return getTotal();//最后一页
        }
    }

    /**
     * 设置
     * @param next
     */
    public void setNext(int next) {
        this.next = next;
    }

    /**
     * 计算总页数
     * 获取
     * @return total
     */
    public int getTotal() {
        //如果能整除，正好是这么多页
        if (getCount() % getSize() == 0) {
            return getCount() / getSize();
        } else {//不能整除就+1
            return getCount() / getSize() + 1;
        }
    }

    /**
     * 设置
     * @param total
     */
    public void setTotal(int total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "PageBean{" +
                "data = " + getData() +
                ", count = " + getCount() +
                ", current = " + getCurrent() +
                ", size = " + getSize() +
                ", first = " + getFirst() +
                ", previous = " + getPrevious() +
                ", next = " + getNext() +
                ", total = " + getTotal() +
                "}";
    }
}
