package com.yxm.mygank.network.core;

import com.yxm.mygank.model.bean.BannerBean;
import com.yxm.mygank.model.bean.BaseModelBean;
import com.yxm.mygank.model.bean.CategoryBean;
import com.yxm.mygank.model.bean.ContentBean;
import com.yxm.mygank.model.bean.DetailsBean;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by yxm on 2020/4/1
 *
 * @function api接口
 */
public interface ApiService {

    //获取轮播图
    @GET("v2/banners")
    Call<BaseModelBean<List<BannerBean>>> getBanners();

    //获取分类，category可取值GanHuo干货分类、Article专题分类、Girl妹纸图片
    @GET("v2/categories/{category_type}")
    Call<BaseModelBean<List<CategoryBean>>> getCategories(@Path("category_type") String category);

    //获取数据，传参1、category可取值GanHuo干货分类、Article专题分类、Girl妹纸图片2、type(分类接口中获取)type字段
    //3、page页码 4、每一页的数量.0 <count <=50
    @GET("v2/data/category/{category}/type/{type}/page/{page}/count/{count}")
    Call<BaseModelBean<List<ContentBean>>> getContent(@Path("category") String category, @Path("type") String type,
                                                      @Path("page") int page, @Path("count") int count);
    //详情.
    @GET("v2/post/{id}")
    Call<BaseModelBean<DetailsBean>> getDetails(@Path("id") String id);

}
