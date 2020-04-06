package com.yxm.mygank.network.core;

import com.yxm.mygank.network.constants.Constants;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by yxm on 2020/4/1
 *
 * @function 网络请求管理类，静态内部类单例实现
 */
public class RetrofitManager {

    private static final int TIME_OUT = 30;
    private static ApiService apiService;

    private RetrofitManager() {
    }

    public void initRetrofit() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.hostnameVerifier((hostname, session) -> true)//信任所有证书
                .addInterceptor(httpLoggingInterceptor)//日志拦截器
                .retryOnConnectionFailure(true)//失败重试
                .writeTimeout(TIME_OUT, TimeUnit.SECONDS)//写超时时间
                .readTimeout(TIME_OUT, TimeUnit.SECONDS)//读超时时间
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)//连接超时时间
                .sslSocketFactory(HttpsUtils.initSSLSocketFactory(),//访问https
                        HttpsUtils.initTrustManager());
        OkHttpClient mOkHttpClient = builder.build();
        //创建retrofit
        Retrofit mRetrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .client(mOkHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = mRetrofit.create(ApiService.class);
    }

    public static RetrofitManager getInstance() {
        return SingleHolder.mInstance;
    }

    private static class SingleHolder {
        private static RetrofitManager mInstance = new RetrofitManager();
    }

    //获取api接口
    public static ApiService Api() {
        return apiService;
    }
}
