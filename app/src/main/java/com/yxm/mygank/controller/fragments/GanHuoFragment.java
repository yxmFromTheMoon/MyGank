package com.yxm.mygank.controller.fragments;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;

import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.wang.avi.AVLoadingIndicatorView;
import com.yxm.mygank.R;
import com.yxm.mygank.common.base.BaseFragment;
import com.yxm.mygank.common.event.HideBottomViewEvent;
import com.yxm.mygank.common.event.RepeatTabEvent;
import com.yxm.mygank.controller.activity.BigPictureActivity;
import com.yxm.mygank.controller.activity.GanHuoDetailActivity;
import com.yxm.mygank.controller.adapter.CategoriesAdapter;
import com.yxm.mygank.imageloader.ImageLoaderManager;
import com.yxm.mygank.model.BannerModel;
import com.yxm.mygank.model.CategoriesModel;
import com.yxm.mygank.model.bean.BannerBean;
import com.yxm.mygank.model.bean.CategoryBean;
import com.yxm.mygank.network.constants.Constants;
import com.zhpan.bannerview.BannerViewPager;
import com.zhpan.bannerview.constants.IndicatorGravity;
import com.zhpan.bannerview.constants.PageStyle;
import com.zhpan.bannerview.holder.ViewHolder;
import com.zhpan.indicator.enums.IndicatorSlideMode;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by yxm on 2020/4/4
 *
 * @function 干货fragment
 */
public class GanHuoFragment extends BaseFragment implements CategoriesModel.OnGetCategoriesListener, BannerModel.BannerListener {

    /**
     * UI
     */
    private BannerViewPager<BannerBean, MyBannerViewHolder> mBanner;
    private RecyclerView mRecyclerView;
    private SmartRefreshLayout mRefreshLayout;

    private CategoriesAdapter mAdapter;

    private CategoriesModel mModel = new CategoriesModel(this);
    private BannerModel mBannerModel = new BannerModel(this);

    public static GanHuoFragment newInstance() {
        return new GanHuoFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_ganhuo;
    }

    @Override
    public void initView(View views) {
        mBanner = views.findViewById(R.id.banner);
        mRecyclerView = views.findViewById(R.id.categories_rv);
        mAdapter = new CategoriesAdapter(R.layout.item_picture);
        LinearLayoutManager lm = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(lm);
        mRecyclerView.setAdapter(mAdapter);

        mRefreshLayout = views.findViewById(R.id.refreshLayout);

    }

    @Override
    public void initListener() {
        mRefreshLayout.setEnableLoadMore(false);
        mRefreshLayout.setOnRefreshListener(refreshLayout -> {
            lazyLoad();
        });

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                HideBottomViewEvent event = new HideBottomViewEvent(true);
                //上滑
                if (dy > 0) {
                    event.setHide(true);
                    EventBus.getDefault().post(event);
                } else if (dy < 0) {
                    event.setHide(false);
                    EventBus.getDefault().post(event);
                } else {
                    super.onScrolled(recyclerView, dx, dy);
                }
            }
        });

        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            if (view.getId() == R.id.picture) {
                GanHuoDetailActivity.start(mContext, (CategoryBean) adapter.getItem(position),
                        (ImageView) adapter.getViewByPosition(mRecyclerView, position, R.id.picture));
            }
        });

    }

    @Override
    public void lazyLoad() {
        showLoading();
        mBannerModel.getBanners();
        mModel.getCategories(Constants.GANHUO);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void tabReClickEvent(RepeatTabEvent event) {
        if (event.getIndex() == 0) {
            mRecyclerView.scrollToPosition(0);
            mRefreshLayout.autoRefresh();
        }
    }

    @Override
    public void onGetCategories(List<CategoryBean> data) {
        disLoading();
        mRefreshLayout.finishRefresh();
        mAdapter.setNewData(data);
    }

    @Override
    public void onGetCategoriesFailure(String content) {
        disLoading();
        mRefreshLayout.finishRefresh();
        showToast(content + "获取目录失败");
    }

    @TargetApi(value = 23)
    private void initBannerPager(List<BannerBean> data) {
        mBanner.setIndicatorVisibility(View.VISIBLE)
                .setIndicatorSliderColor(Color.parseColor("#FFFFFF"),
                        mContext.getResources().getColor(R.color.colorPrimary, null))
                .setIndicatorGravity(IndicatorGravity.CENTER)
                .setIndicatorSlideMode(IndicatorSlideMode.SMOOTH)
                .setPageStyle(PageStyle.MULTI_PAGE_SCALE)
                .setPageMargin(20)
                .setRevealWidth(50)
                .setScrollDuration(1000)
                .setHolderCreator(MyBannerViewHolder::new)
                .setOnPageClickListener(position -> {
                    BigPictureActivity.start(mContext, data.get(position).getImage());
                }).create(data);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mBanner != null) {
            mBanner.stopLoop();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mBanner != null) {
            mBanner.startLoop();
        }
    }

    @Override
    public void setBanner(List<BannerBean> data) {
        disLoading();
        initBannerPager(data);
    }

    @Override
    public void onBannerFailure(String des) {
        disLoading();
        showToast(des + "获取banner失败");
    }

    //banner viewHolder
    private static class MyBannerViewHolder implements ViewHolder<BannerBean> {

        @Override
        public int getLayoutId() {
            return R.layout.item_picture;
        }

        @Override
        public void onBind(View itemView, BannerBean data, int position, int size) {
            ImageView imageView = itemView.findViewById(R.id.picture);
            ImageLoaderManager.getInstance().displayWithDefaultPlaceHolder(imageView, data.getImage());
        }
    }
}
