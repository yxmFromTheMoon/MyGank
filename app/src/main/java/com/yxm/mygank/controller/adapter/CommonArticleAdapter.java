package com.yxm.mygank.controller.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yxm.mygank.R;
import com.yxm.mygank.imageloader.ImageLoaderManager;
import com.yxm.mygank.model.bean.ContentBean;

import androidx.annotation.LayoutRes;

/**
 * Created by yxm on 2020/4/8
 *
 * @function
 */
public class CommonArticleAdapter extends BaseQuickAdapter<ContentBean, BaseViewHolder> {

    public CommonArticleAdapter(@LayoutRes int layoutId) {
        super(layoutId);
    }

    @Override
    protected void convert(BaseViewHolder helper, ContentBean item) {
        helper.setText(R.id.article_desc, item.getDesc());
        helper.setText(R.id.author, "作者:" + item.getAuthor());
        helper.setText(R.id.publish_time, "发布时间:" + item.getPublishedAt().substring(0, 10));
        helper.setText(R.id.type, "分类:" + item.getType());
        if (!item.getImages().isEmpty()) {
            ImageLoaderManager.getInstance().displayWithDefaultPlaceHolder(helper.getView(R.id.article_cover), item.getImages().get(0));
        }
    }
}
