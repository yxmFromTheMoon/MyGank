package com.yxm.mygank.common.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.yxm.mygank.common.view.LoadingDialog;

import org.greenrobot.eventbus.EventBus;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * Created by yxm on 2020/4/2
 *
 * @function baseFragment
 */
public abstract class BaseFragment extends Fragment {

    protected Context mContext;
    protected String TAG = getClass().getSimpleName();
    private boolean isFirstLoad = true;
    protected LoadingDialog dialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        dialog = new LoadingDialog(mContext);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(mContext).inflate(getLayoutId(), container, false);
        EventBus.getDefault().register(this);
        initView(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initListener();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isFirstLoad) {
            lazyLoad();
            isFirstLoad = false;
        }
    }

    public abstract int getLayoutId();

    public abstract void initView(View view);

    public abstract void initListener();

    public abstract void lazyLoad();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    protected void showToast(String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }

    protected void showLoading(){
        dialog.show();
    }

    protected void disLoading(){
        dialog.dismiss();
    }

    @NonNull
    @Override
    public String toString() {
        return TAG;
    }
}
