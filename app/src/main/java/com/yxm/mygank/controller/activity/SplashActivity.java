package com.yxm.mygank.controller.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;

import com.yxm.mygank.R;
import com.yxm.mygank.common.base.BaseActivity;

import java.lang.ref.WeakReference;

import androidx.annotation.NonNull;

/**
 * Created by yxm on 2020/4/12
 *
 * @function 闪屏页
 */
public class SplashActivity extends BaseActivity {

    private MyHandler mHandler = new MyHandler(this);

    @Override
    public int getLayoutId() {
        return R.layout.fragment_splash1;
    }

    @Override
    public void initView() {
        mHandler.postDelayed(() -> {
            Message message = Message.obtain();
            message.what = 0x01;
            mHandler.sendMessage(message);
        }, 2000);
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void onBackPressed() {

    }

    static class MyHandler extends Handler{
        private final WeakReference<SplashActivity> mActivity;

        private MyHandler(SplashActivity activity){
            this.mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            SplashActivity splashActivity = mActivity.get();
            if(splashActivity != null){
                if (msg.what == 0x01) {
                    splashActivity.startActivity(new Intent(splashActivity, MainActivity.class));
                    splashActivity.finish();
                }
            }
        }
    }

}