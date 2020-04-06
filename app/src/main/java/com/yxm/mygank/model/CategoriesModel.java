package com.yxm.mygank.model;

import com.yxm.mygank.model.bean.CategoryBean;
import com.yxm.mygank.network.constants.Constants;
import com.yxm.mygank.network.core.RetrofitCallback;
import com.yxm.mygank.network.core.RetrofitManager;

import java.util.List;

/**
 * Created by yxm on 2020/4/6
 *
 * @function 获得目录
 */
public class CategoriesModel extends RetrofitCallback<List<CategoryBean>> {

    private OnGetCategoriesListener mListener;

    public CategoriesModel(OnGetCategoriesListener listener) {
        this.mListener = listener;
    }

    public void getCategories() {
        RetrofitManager.Api().getCategorie(Constants.GANHUO).enqueue(this);
    }

    @Override
    public void onSuccess(List<CategoryBean> data) {
        mListener.onGetCategories(data);
    }

    @Override
    public void onFailure(int errorCode, String description) {
        mListener.onGetCategoriesFailure(description);
    }

    public interface OnGetCategoriesListener {
        void onGetCategories(List<CategoryBean> data);

        void onGetCategoriesFailure(String content);
    }
}
