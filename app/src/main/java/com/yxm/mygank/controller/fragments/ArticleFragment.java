package com.yxm.mygank.controller.fragments;

import android.view.View;
import com.yxm.mygank.R;
import com.yxm.mygank.common.base.BaseFragment;
import com.yxm.mygank.common.event.RepeatTabEvent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by yxm on 2020/4/4
 *
 * @function 文章fragment
 */
public class ArticleFragment extends BaseFragment {

    public static ArticleFragment newInstance() {
        return new ArticleFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_article;
    }

    @Override
    public void initView(View view) {

    }

    @Override
    public void initListener() {

    }

    @Override
    public void lazyLoad() {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void tabReClickEvent(RepeatTabEvent event){
        if(event.getIndex() == 1){
            showToast("文章");
        }
    }
}
