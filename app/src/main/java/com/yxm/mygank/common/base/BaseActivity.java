package com.yxm.mygank.common.base;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;
import com.yxm.mygank.common.view.LoadingDialog;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


/**
 * Created by yxm on 2020/4/1
 *
 * @function baseActivity
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected Context mContext;


    protected String TAG = getClass().getSimpleName();

    protected LoadingDialog dialog;

    public abstract int getLayoutId();

    public abstract void initView();

    public abstract void initListener();

    public abstract void initData();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getLayoutId() != -1) {
            setContentView(getLayoutId());
        }
        mContext = this;
        dialog = new LoadingDialog(mContext);
        initDataBeforeView();
        initView();
        initListener();
        initData();
    }

    protected void showToast(String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }

    protected void initDataBeforeView(){}

    protected void showLoading(){
         dialog.show();
    }

    protected void disLoading(){
        dialog.dismiss();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @NonNull
    @Override
    public String toString() {
        return TAG;
    }

}
