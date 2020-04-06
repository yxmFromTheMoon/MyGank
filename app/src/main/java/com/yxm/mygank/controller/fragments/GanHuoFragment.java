package com.yxm.mygank.controller.fragments;

import android.view.View;

import com.yxm.mygank.MainActivity;
import com.yxm.mygank.R;
import com.yxm.mygank.common.base.BaseFragment;
import com.yxm.mygank.common.event.RepeatTabEvent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by yxm on 2020/4/4
 *
 * @function 干货fragment
 */
public class GanHuoFragment extends BaseFragment {

    public static GanHuoFragment newInstance() {
        return new GanHuoFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_ganhuo;
    }

    @Override
    public void initView(View views) {

    }

    @Override
    public void initListener() {

    }

    @Override
    public void lazyLoad() {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void tabReClickEvent(RepeatTabEvent event){
        if(event.getIndex() == 0){
            showToast("干货");
        }
    }
}
