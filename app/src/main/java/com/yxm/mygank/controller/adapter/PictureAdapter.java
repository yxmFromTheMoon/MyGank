package com.yxm.mygank.controller.adapter;

import android.view.ViewGroup;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yxm.mygank.R;
import com.yxm.mygank.common.util.ScreenHelper;
import com.yxm.mygank.imageloader.ImageLoaderManager;
import com.yxm.mygank.model.bean.ContentBean;

import androidx.annotation.LayoutRes;

/**
 * Created by yxm on 2020/4/6
 *
 * @function 图片adapter
 */
public class PictureAdapter extends BaseQuickAdapter<ContentBean, BaseViewHolder> {

    public PictureAdapter(@LayoutRes int layoutId) {
        super(layoutId);
    }

    @Override
    protected void convert(BaseViewHolder helper, ContentBean item) {
        helper.setText(R.id.picture_description, item.getDesc());
        ImageView imageView = helper.getView(R.id.picture);
        displayScaleImage(imageView.getLayoutParams(), imageView, item.getUrl());
        helper.addOnClickListener(R.id.picture);
    }

    private void displayScaleImage(ViewGroup.LayoutParams layoutParams, ImageView imageView, String url) {
        int screenWidth = ScreenHelper.getScreenWidth(imageView.getContext());
        layoutParams.width = (screenWidth - 5 * 4) / 2;
        layoutParams.height = ScreenHelper.getScreenHeight(imageView.getContext()) / 3;
        imageView.setLayoutParams(layoutParams);
        ImageLoaderManager.getInstance().displayWithNewWidthAndHeight(imageView, url, layoutParams.width, layoutParams.height);
    }
}
