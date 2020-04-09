package com.yxm.mygank.controller.fragments;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.wang.avi.AVLoadingIndicatorView;
import com.yxm.mygank.R;
import com.yxm.mygank.common.base.BaseFragment;
import com.yxm.mygank.common.event.RepeatTabEvent;
import com.yxm.mygank.controller.adapter.ViewPagerAdapter;
import com.yxm.mygank.model.CategoriesModel;
import com.yxm.mygank.model.bean.CategoryBean;
import com.yxm.mygank.network.constants.Constants;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.widget.ViewPager2;

/**
 * Created by yxm on 2020/4/4
 *
 * @function 文章fragment
 */
public class ArticleFragment extends BaseFragment implements CategoriesModel.OnGetCategoriesListener {

    /**
     * UI
     */
    private ViewPager2 mViewpager2;
    private TabLayout mTabLayout;
    /**
     * data
     */
    private ViewPagerAdapter mAdapter;
    private ArrayList<BaseFragment> fragments = new ArrayList<>();
    private CategoriesModel mModel = new CategoriesModel(this);

    public static ArticleFragment newInstance() {
        return new ArticleFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_article;
    }

    @Override
    public void initView(View view) {
        mViewpager2 = view.findViewById(R.id.article_vp2);
        mViewpager2.setUserInputEnabled(true);
        mViewpager2.setOffscreenPageLimit(1);
        mTabLayout = view.findViewById(R.id.tabLayout);
    }

    @Override
    public void initListener() {

    }

    @Override
    public void lazyLoad() {
        showLoading();
        mModel.getCategories(Constants.ARTICLE);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void tabReClickEvent(RepeatTabEvent event) {

    }

    @Override
    public void onGetCategories(List<CategoryBean> data) {
        disLoading();
        for (int i = 0; i < data.size() - 1; i++) {
            fragments.add(ArticleSortFragment.newInstance(data.get(i).getType()));
        }
        mAdapter = new ViewPagerAdapter((FragmentActivity) mContext, fragments);
        mViewpager2.setAdapter(mAdapter);

        new TabLayoutMediator(mTabLayout, mViewpager2, (tab, position) -> {
            tab.setText(data.get(position).getTitle());
        }).attach();
    }

    @Override
    public void onGetCategoriesFailure(String content) {
        disLoading();
        showToast(content + "获取文章目录失败");
    }
}
