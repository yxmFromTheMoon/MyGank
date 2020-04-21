package com.yxm.mygank.controller.adapter;

import android.view.Gravity;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yxm.mygank.R;
import com.yxm.mygank.imageloader.ImageLoaderManager;
import com.yxm.mygank.model.bean.CategoryBean;

import androidx.annotation.LayoutRes;

/**
 * Created by yxm on 2020/4/6
 *
 * @function 干货目录 adapter
 */
public class CategoriesAdapter extends BaseQuickAdapter<CategoryBean, BaseViewHolder> {

    public CategoriesAdapter(@LayoutRes int layoutId) {
        super(layoutId);
    }

    @Override
    protected void convert(BaseViewHolder helper, CategoryBean item) {
        TextView textView = helper.getView(R.id.picture_description);
        textView.setGravity(Gravity.CENTER_HORIZONTAL);
        textView.setText(item.getDesc());

        ImageView imageView = helper.getView(R.id.picture);
        ImageLoaderManager.getInstance().displayWithDefaultPlaceHolder(imageView, item.getCoverImageUrl());
        helper.addOnClickListener(R.id.picture);
    }
}
