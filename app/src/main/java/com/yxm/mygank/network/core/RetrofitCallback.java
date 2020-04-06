package com.yxm.mygank.network.core;

import com.yxm.mygank.common.MyApplication;
import com.yxm.mygank.common.util.NetWorkHelper;
import com.yxm.mygank.model.bean.BaseModelBean;

import org.json.JSONException;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

/**
 * Created by yxm on 2020/4/1
 *
 * @function 自定义回调，网络请求成功，失败回调
 */
public abstract class RetrofitCallback<T> implements Callback<BaseModelBean<T>> {

    private static final int SUCCESS_CODE = 100;
    private static final int NETWORK_ERROR = -1;
    private static final int IO_ERROR = -2;
    private static final int OTHER_ERROR = -3;
    private static final int JSON_ERROR = -4;

    public abstract void onSuccess(T data);

    public abstract void onFailure(int errorCode, String description);

    /**
     * 回调成功
     */
    @EverythingIsNonNull
    @Override
    public void onResponse(Call<BaseModelBean<T>> call, Response<BaseModelBean<T>> response) {
        if (NetWorkHelper.isConnected(MyApplication.getInstance())) {
            if (response.body() != null && response.isSuccessful()) {
                if (response.body().getStatus() == SUCCESS_CODE) {
                    onSuccess(response.body().getData());
                } else {
                    onFailure(response.body().getStatus(), "获取数据失败");
                }
            }else {
                onFailure(NETWORK_ERROR,"无网络");
            }
        }
    }

    /**
     * 回调失败
     */
    @EverythingIsNonNull
    @Override
    public void onFailure(Call<BaseModelBean<T>> call, Throwable t) {
        if (t instanceof SocketTimeoutException) {//超时
            onFailure(NETWORK_ERROR, "网络连接超时");
        } else if (t instanceof ConnectException) {//连接错误
            onFailure(NETWORK_ERROR, "网络连接错误");
        } else if (t instanceof UnknownError) { //未找到主机
            onFailure(NETWORK_ERROR, "服务器崩溃啦");
        } else if (t instanceof IOException) {//IO错误
            onFailure(IO_ERROR, "IO异常");
        } else if (t instanceof JSONException) {
            onFailure(JSON_ERROR, "Json格式转换异常");
        } else {
            onFailure(OTHER_ERROR, "其他异常" + t.getMessage());
        }
    }

}
