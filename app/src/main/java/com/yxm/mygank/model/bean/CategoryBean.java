package com.yxm.mygank.model.bean;

import java.io.Serializable;

/**
 * Created by yxm on 2020/4/2
 *
 * @function 分类bean
 */
public class CategoryBean implements Serializable {
    /**
     * "_id": "5e59ec146d359d60b476e621",
     * "coverImageUrl": "http://gank.io/images/b9f867a055134a8fa45ef8a321616210",
     * "desc": "Always deliver more than expected.（Larry Page）",
     * "title": "Android",
     * "type": "Android"
     */
    private String _id;
    private String coverImageUrl;
    private String desc;
    private String title;
    private String type;

    public String get_id() {
        return _id;
    }

    public String getCoverImageUrl() {
        return coverImageUrl;
    }

    public String getDesc() {
        return desc;
    }

    public String getTitle() {
        return title;
    }

    public String getType() {
        return type;
    }
}
