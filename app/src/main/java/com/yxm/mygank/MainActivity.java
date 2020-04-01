package com.yxm.mygank;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.yxm.mygank.model.BannerBean;
import com.yxm.mygank.network.core.RetrofitManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements Callback<BannerBean>{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RetrofitManager.Api().getBanners().enqueue(this);
    }

    @Override
    public void onFailure(Call<BannerBean> call, Throwable t) {

    }

    @Override
    public void onResponse(Call<BannerBean> call, Response<BannerBean> response) {

    }
}
