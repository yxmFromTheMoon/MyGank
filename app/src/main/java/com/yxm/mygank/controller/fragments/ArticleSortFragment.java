package com.yxm.mygank.controller.fragments;

import android.os.Bundle;
import android.view.View;

import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.yxm.mygank.R;
import com.yxm.mygank.common.base.BaseFragment;
import com.yxm.mygank.common.event.HideBottomViewEvent;
import com.yxm.mygank.common.event.RepeatTabEvent;
import com.yxm.mygank.controller.activity.WebViewActivity;
import com.yxm.mygank.controller.adapter.CommonArticleAdapter;
import com.yxm.mygank.model.ContentModel;
import com.yxm.mygank.model.bean.ContentBean;
import com.yxm.mygank.network.constants.Constants;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by yxm on 2020/4/8
 *
 * @function 文章分类
 */
public class ArticleSortFragment extends BaseFragment implements ContentModel.OnGetContentListener {

    /**
     * UI
     */
    private SmartRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    /**
     * data
     */
    private ContentModel mModel = new ContentModel(this);
    private CommonArticleAdapter articleAdapter;

    private String mType;

    static ArticleSortFragment newInstance(String type) {
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        ArticleSortFragment fragment = new ArticleSortFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_article_sort;
    }

    @Override
    public void initView(View view) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            mType = bundle.getString("type");
        }
        mRecyclerView = view.findViewById(R.id.article_rv);
        mRefreshLayout = view.findViewById(R.id.refreshLayout);

        LinearLayoutManager lm = new LinearLayoutManager(mContext);
        articleAdapter = new CommonArticleAdapter(R.layout.item_common_article);
        mRecyclerView.setLayoutManager(lm);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        mRecyclerView.setAdapter(articleAdapter);
    }

    @Override
    public void initListener() {
        mRefreshLayout.setOnRefreshListener(listener ->
                mModel.getContent(Constants.ARTICLE, mType)
        );

        mRefreshLayout.setOnLoadMoreListener(listener ->
                mModel.loadMore(Constants.ARTICLE, mType)
        );

        articleAdapter.setOnItemClickListener((adapter, view, position) -> {
            ContentBean item = (ContentBean) adapter.getItem(position);
            if (item != null) {
                WebViewActivity.start(mContext, item.getUrl(), item.getTitle());
            }
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
    }

    @Override
    public void lazyLoad() {
        showLoading();
        mModel.getContent(Constants.ARTICLE, mType);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void tabReClickEvent(RepeatTabEvent event) {
        if (event.getIndex() == 1) {
            mRecyclerView.scrollToPosition(0);
            mRefreshLayout.autoRefresh();
        }
    }

    @Override
    public void onGetContentSuccess(List<ContentBean> data) {
        disLoading();
        mRefreshLayout.finishRefresh();
        articleAdapter.setNewData(data);
    }

    @Override
    public void onLoadMore(List<ContentBean> data) {
        mRefreshLayout.finishLoadMore();
        articleAdapter.addData(data);
    }

    @Override
    public void onGetContentFailure(String content) {
        disLoading();
        mRefreshLayout.finishRefresh();
        mRefreshLayout.finishLoadMore();
        showToast(content + "获取文章列表失败");
    }
}
