package com.yxm.mygank.model;

import com.yxm.mygank.model.bean.DetailsBean;
import com.yxm.mygank.network.core.RetrofitCallback;
import com.yxm.mygank.network.core.RetrofitManager;

/**
 * Created by yxm on 2020/4/5
 *
 * @function 获取详情
 */
public class DetailsModel extends RetrofitCallback<DetailsBean> {

    private GetDetailsListener mListener;

    public DetailsModel(GetDetailsListener listener) {
        this.mListener = listener;
    }

    public void getDetails(String id) {
        RetrofitManager.Api().getDetails(id).enqueue(this);
    }

    @Override
    public void onSuccess(DetailsBean data) {
        mListener.onGetDetail(data);
    }

    @Override
    public void onFailure(int errorCode, String description) {
        mListener.onGetDetailFail(description);
    }

    public interface GetDetailsListener {
        void onGetDetail(DetailsBean data);

        void onGetDetailFail(String error);
    }

}
