package com.yxm.mygank.network.core;

/**
 * Created by yxm on 2020/4/1
 *
 * @function 自定义回调，网络请求成功，失败回调
 */
public interface RetrofitListener<T> {

    /**
     * 回调成功
     */
    void onSuccess(T data);

    /**
     * 回调失败
     */
    void onFailure(String description,int errorCode);
}
