package com.yxm.mygank.model.bean;

import java.util.ArrayList;

/**
 * Created by yxm on 2020/4/2
 *
 * @function 文章列表bean
 */
public class ContentBean {

    private String _id;
    private String author;
    private String category;
    private String createdAt;
    private String desc;
    private ArrayList<String> images;
    private Long likeCounts;
    private String publishedAt;
    private Long stars;
    private String title;
    private String type;
    private String url;
    private Long views;

    public String get_id() {
        return _id;
    }

    public String getAuthor() {
        return author;
    }

    public String getCategory() {
        return category;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getDesc() {
        return desc;
    }

    public ArrayList<String> getImages() {
        return images;
    }

    public Long getLikeCounts() {
        return likeCounts;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public Long getStars() {
        return stars;
    }

    public String getTitle() {
        return title;
    }

    public String getType() {
        return type;
    }

    public String getUrl() {
        return url;
    }

    public Long getViews() {
        return views;
    }
}

/**
 *
 "_id": "5e81c02f4327eb491841d0c2",
 "author": "dkzwm",
 "category": "GanHuo",
 "createdAt": "2020-03-30 17:47:27",
 "desc": "一款支持上下拉刷新、越界回弹、二级刷新、横向刷新、拉伸回弹、平滑滚动、嵌套滚动的多功能刷新控件",
 "images": [
 "https://gank.io/images/4ff6f5d09ba241ddb6ffd066280b51cf",
 "https://gank.io/images/b67db4c6d8964f53bcf5d08b9059c042",
 "https://gank.io/images/dd19d9facd564069a39df20b6b0588b6",
 "https://gank.io/images/83fedd330fe34897b942c33472ddcc37"
 ],
 "likeCounts": 0,
 "publishedAt": "2020-03-30 17:47:27",
 "stars": 5,
 "title": "SmoothRefreshLayout",
 "type": "Android",
 "url": "https://github.com/dkzwm/SmoothRefreshLayout",
 "views": 580
 */
