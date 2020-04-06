package com.yxm.mygank.controller.fragments;

import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gyf.immersionbar.BarHide;
import com.gyf.immersionbar.ImmersionBar;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.yxm.mygank.R;
import com.yxm.mygank.common.base.BaseFragment;
import com.yxm.mygank.common.event.HideBottomViewEvent;
import com.yxm.mygank.common.event.RepeatTabEvent;
import com.yxm.mygank.controller.adapter.PictureAdapter;
import com.yxm.mygank.model.ContentModel;
import com.yxm.mygank.model.bean.ContentBean;
import com.yxm.mygank.network.constants.Constants;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

/**
 * Created by yxm on 2020/4/4
 *
 * @function 妹纸fragment
 */
public class GirlFragment extends BaseFragment implements ContentModel.OnGetContentListener {

    private ContentModel mModel = new ContentModel(this);

    private PictureAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private SmartRefreshLayout mRefreshLayout;

    public static GirlFragment newInstance() {
        return new GirlFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_girl;
    }

    @Override
    public void initView(View view) {
        mRecyclerView = view.findViewById(R.id.content_girl_rv);
        mRefreshLayout = view.findViewById(R.id.refreshLayout);
        mAdapter = new PictureAdapter(R.layout.item_picture);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void initListener() {
        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            ContentBean item = (ContentBean) adapter.getItem(position);
            if (item != null) {
                if (view.getId() == R.id.picture) {
                    showToast("url" + item.getUrl());
                }
            }
        });

        mRefreshLayout.setOnRefreshListener(refreshLayout -> lazyLoad());

        mRefreshLayout.setOnLoadMoreListener(refreshLayout -> mModel.loadMore(Constants.GIRL, Constants.GIRL));

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            HideBottomViewEvent event = new HideBottomViewEvent(true);

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
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
    }

    @Override
    public void lazyLoad() {
        mModel.getContent(Constants.GIRL, Constants.GIRL);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void tabReClickEvent(RepeatTabEvent event) {
        if (event.getIndex() == 2) {
            mRecyclerView.scrollToPosition(0);
            mRefreshLayout.autoRefresh();
        }
    }

    @Override
    public void onGetContentSuccess(List<ContentBean> data) {
        mAdapter.setNewData(data);
        mRefreshLayout.finishRefresh();
    }

    @Override
    public void onGetContentFailure(String content) {
        showToast(content + "获取图片失败");
        mRefreshLayout.finishRefresh();
    }

    @Override
    public void onLoadMore(List<ContentBean> data) {
        mAdapter.addData(data);
        mRefreshLayout.finishLoadMore();
    }
}
