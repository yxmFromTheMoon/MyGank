package com.yxm.mygank.network.core;

import com.yxm.mygank.model.BannerBean;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by yxm on 2020/4/1
 *
 * @function api接口
 */
public interface ApiService {

    //获取轮播图
    @GET("v2/banners")
    Call<BannerBean> getBanners();

}
