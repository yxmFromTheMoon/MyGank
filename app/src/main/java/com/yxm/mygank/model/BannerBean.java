package com.yxm.mygank.model;

import android.support.annotation.NonNull;

import java.util.List;

/**
 * Created by yxm on 2020/4/1
 *
 * @function banner
 */
public class BannerBean {
    private List<BannerData> data;
    private int status;

    public int getStatus() {
        return status;
    }

    public List<BannerData> getData() {
        return data;
    }

    private static class BannerData {
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

    @NonNull
    @Override
    public String toString() {
        return "status" + status + "List" + data.size();
    }
}
