package com.yxm.mygank.common;

import android.app.Application;
import android.content.Context;

import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.DefaultRefreshHeaderCreator;
import com.yxm.mygank.network.core.RetrofitManager;

import androidx.annotation.NonNull;

/**
 * Created by yxm on 2020/4/1
 *
 * @function application
 */
public class MyApplication extends Application {

    private static MyApplication mInstance = null;

    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator((context, refreshLayout) ->
                new ClassicsHeader(context)//指定为经典Header，默认是 贝塞尔雷达Header
        );
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator((context, refreshLayout) ->
                //指定为经典Footer，默认是 BallPulseFooter
                new ClassicsFooter(context)
        );
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        RetrofitManager.getInstance().initRetrofit();
    }

    public static MyApplication getInstance() {
        return mInstance;
    }
}
