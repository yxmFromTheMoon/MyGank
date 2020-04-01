package com.yxm.mygank.common;

import android.app.Application;

import com.yxm.mygank.network.core.RetrofitManager;

/**
 * Created by yxm on 2020/4/1
 *
 * @function application
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        RetrofitManager.getInstance().initRetrofit();
    }
}
