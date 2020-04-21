package com.yxm.mygank.controller.activity;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.appbar.AppBarLayout;
import com.gyf.immersionbar.ImmersionBar;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.yxm.mygank.R;
import com.yxm.mygank.common.base.BaseActivity;
import com.yxm.mygank.common.util.AppBarStateChangeListener;
import com.yxm.mygank.controller.adapter.CommonArticleAdapter;
import com.yxm.mygank.imageloader.ImageLoaderManager;
import com.yxm.mygank.model.ContentModel;
import com.yxm.mygank.model.bean.CategoryBean;
import com.yxm.mygank.model.bean.ContentBean;
import com.yxm.mygank.network.constants.Constants;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by yxm on 2020/4/7
 *
 * @function 干货分类详情
 */
public class GanHuoDetailActivity extends BaseActivity implements ContentModel.OnGetContentListener {

    public static final String CATEGORY = "category";
    private ContentModel mModel = new ContentModel(this);
    private CategoryBean categoryBean;
    private CommonArticleAdapter mAdapter;
    /**
     * UI
     */
    private AppBarLayout mAppbar;
    private ImageView mHeaderImage;
    private Toolbar mToolbar;
    private TextView mType;
    private RecyclerView mRecyclerView;
    private SmartRefreshLayout mRefreshLayout;

    public static void start(Context context, CategoryBean bean, ImageView imageView) {
        Intent intent = new Intent(context, GanHuoDetailActivity.class);
        intent.putExtra(CATEGORY, bean);
        context.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation((MainActivity) context,
                imageView, "shared").toBundle());
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_ganhuo_detail;
    }

    @Override
    protected void initDataBeforeView() {
        super.initDataBeforeView();
        categoryBean = (CategoryBean) getIntent().getSerializableExtra(CATEGORY);
    }

    @Override
    public void initView() {
        mAppbar = findViewById(R.id.appbar);
        mToolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mHeaderImage = findViewById(R.id.header_image);
        mType = findViewById(R.id.toolbar_title);
        mRefreshLayout = findViewById(R.id.refreshLayout);
        mRecyclerView = findViewById(R.id.content_ganhuo_rv);
        ImmersionBar.with(GanHuoDetailActivity.this)
                .titleBar(mToolbar)
                .statusBarDarkFont(false)
                .init();
        mAdapter = new CommonArticleAdapter(R.layout.item_common_article);
        LinearLayoutManager lm = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(lm);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void initListener() {

        mRefreshLayout.setOnRefreshListener(refreshLayout ->
                mModel.getContent(Constants.GANHUO, categoryBean.getType())
        );
        mRefreshLayout.setOnLoadMoreListener(refreshLayout ->
                mModel.loadMore(Constants.GANHUO, categoryBean.getType())
        );
        mToolbar.setNavigationOnClickListener(listener ->
                onBackPressed()
        );
        mRefreshLayout.getParent().requestDisallowInterceptTouchEvent(true);

        mAppbar.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(@NotNull AppBarLayout appBarLayout, @NotNull State state) {

                switch (state) {
                    case IDLE:
                        //展开状态
                    case EXPANDED:
                        mType.setVisibility(View.INVISIBLE);
                        break;
                    //折叠
                    case COLLAPSED:
                        mType.setVisibility(View.VISIBLE);
                        break;
                    default:
                        mType.setVisibility(View.INVISIBLE);
                        break;
                }
            }
        });
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            ContentBean item = (ContentBean) adapter.getItem(position);
            if (item != null) {
                WebViewActivity.start(mContext, item.getUrl(), item.getTitle());
            }
        });
    }

    @Override
    public void initData() {
        ImageLoaderManager.getInstance().displayWithDefaultPlaceHolder(mHeaderImage, categoryBean.getCoverImageUrl());
        mType.setText(categoryBean.getType());
        mModel.getContent(Constants.GANHUO, categoryBean.getType());
    }

    @Override
    public void onGetContentSuccess(List<ContentBean> data) {
        mRefreshLayout.finishRefresh();
        mAdapter.setNewData(data);
    }

    @Override
    public void onLoadMore(List<ContentBean> data) {
        mRefreshLayout.finishLoadMore();
        mAdapter.addData(data);
    }

    @Override
    public void onGetContentFailure(String content) {
        mRefreshLayout.finishRefresh();
        mRefreshLayout.finishLoadMore();
        showToast(content + "获取干货失败");
    }
}
