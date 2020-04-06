package com.yxm.mygank.model;

import com.yxm.mygank.model.bean.BannerBean;
import com.yxm.mygank.network.core.RetrofitCallback;
import com.yxm.mygank.network.core.RetrofitManager;

import java.util.List;

/**
 * Created by yxm on 2020/4/1
 *
 * @function
 */
public class BannerModel extends RetrofitCallback<List<BannerBean>> {

    private BannerListener mBannerListener;

    public BannerModel(BannerListener mBannerListener) {
        this.mBannerListener = mBannerListener;
    }

    /**
     * 获取轮播图
     */
    public void getBanners() {
        RetrofitManager.Api().getBanners().enqueue(this);
    }

    @Override
    public void onSuccess(List<BannerBean> data) {
        mBannerListener.setBanner(data);
    }

    @Override
    public void onFailure(int errorCode, String description) {
        mBannerListener.onBannerFailure(description);
    }

    public interface BannerListener {
        void setBanner(List<BannerBean> data);

        void onBannerFailure(String des);
    }
}
