package com.yxm.mygank.controller.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import com.yxm.mygank.MainActivity;
import com.yxm.mygank.R;
import com.yxm.mygank.common.base.BaseActivity;

import androidx.annotation.NonNull;

/**
 * Created by yxm on 2020/4/12
 *
 * @function 闪屏页
 */
public class SplashActivity extends BaseActivity {

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0x01) {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
            }
        }
    };

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
}