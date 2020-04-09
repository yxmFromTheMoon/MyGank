package com.yxm.mygank.common.view;

import android.app.Dialog;
import android.content.Context;

import com.wang.avi.AVLoadingIndicatorView;
import com.yxm.mygank.R;

import androidx.annotation.ColorInt;

/**
 * Created by yxm on 2020/4/8
 *
 * @function 加载中dialog
 */
public class LoadingDialog extends Dialog {

    private AVLoadingIndicatorView mLoadingView;

    public LoadingDialog(Context context) {
        this(context, R.style.MyDialog);
    }

    public LoadingDialog(Context context, int style) {
        super(context, style);
        setContentView(R.layout.dialog_loading);
        mLoadingView = findViewById(R.id.loading_view);
    }

    /**
     * 设置loading颜色
     * @param colorId
     */
    public void setLoadingColor(@ColorInt int colorId){
        mLoadingView.setIndicatorColor(colorId);
    }

    /**
     * 设置loading样式
     * @param style
     */
    public void setLoadingStyle(String style){
        mLoadingView.setIndicator(style);
    }

    @Override
    public void show() {
        super.show();
        if(mLoadingView != null){
            mLoadingView.smoothToShow();
        }
    }

    @Override
    public void dismiss() {
        super.dismiss();
        if(mLoadingView != null){
            mLoadingView.smoothToHide();
        }
    }
}
