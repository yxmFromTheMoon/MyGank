package com.yxm.mygank.model.bean;

/**
 * Created by yxm on 2020/4/1
 *
 * @function 实体基类(实际上这一个类就够了)
 */
public class BaseModelBean<T> {

    private T data; //实体类
    private int status;//状态码
    private int page;//当前是第几页
    private int page_count;//总页数
    private int total_counts;//数据总条数
    private int count;//查询的个数

    public int getStatus() {
        return status;
    }

    public int getPage() {
        return page;
    }

    public int getPageCount() {
        return page_count;
    }

    public int getTotalCounts() {
        return total_counts;
    }

    public T getData() {
        return data;
    }

    public int getCount() {
        return count;
    }
}
