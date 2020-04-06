package com.yxm.mygank.model.bean;


import androidx.annotation.NonNull;

/**
 * Created by yxm on 2020/4/1
 *
 * @function banner
 */
public class BannerBean {

    private String image;
    private String title;
    private String url;

    public String getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    @NonNull
    @Override
    public String toString() {
        return "image" + image + "title" + title + "url" + url;
    }
}
