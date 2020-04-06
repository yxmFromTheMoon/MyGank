package com.yxm.mygank.model;

import com.yxm.mygank.model.bean.ContentBean;
import com.yxm.mygank.network.constants.Constants;
import com.yxm.mygank.network.core.RetrofitCallback;
import com.yxm.mygank.network.core.RetrofitManager;

import java.util.List;

/**
 * Created by yxm on 2020/4/5
 *
 * @function 获取具体内容
 */
public class ContentModel extends RetrofitCallback<List<ContentBean>> {

    private OnGetContentListener mListener;

    private int page = 1;

    public ContentModel(OnGetContentListener listener) {
        this.mListener = listener;
    }

    public void getContent(String category,String type) {
        page = 1;
        RetrofitManager.Api().getContent(category, type, page, Constants.PAGE_COUNT).enqueue(this);
    }

    public void loadMore(String category,String type) {
        page++;
        RetrofitManager.Api().getContent(category, type, page, Constants.PAGE_COUNT).enqueue(this);
    }

    @Override
    public void onSuccess(List<ContentBean> data) {
        if (page == 1) {
            mListener.onGetContentSuccess(data);
        } else {
            mListener.onLoadMore(data);
        }
    }

    @Override
    public void onFailure(int errorCode, String description) {
        mListener.onGetContentFailure(description);
    }

    public interface OnGetContentListener {
        void onGetContentSuccess(List<ContentBean> data);

        void onLoadMore(List<ContentBean> data);

        void onGetContentFailure(String content);
    }
}
