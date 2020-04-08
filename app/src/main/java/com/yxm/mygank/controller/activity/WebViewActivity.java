package com.yxm.mygank.controller.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.gyf.immersionbar.ImmersionBar;
import com.wang.avi.AVLoadingIndicatorView;
import com.yxm.mygank.R;
import com.yxm.mygank.common.base.BaseActivity;

import androidx.appcompat.widget.Toolbar;

/**
 * Created by yxm on 2020/4/7
 *
 * @function webView
 */
public class WebViewActivity extends BaseActivity {

    public static final String URL = "url";
    public static final String TITLE = "title";

    /**
     * UI
     */
    private Toolbar mToolbar;
    private TextView mTitleTv;
    private WebView mWebView;

    private String mUrl;
    private String mTitle;

    public static void start(Context context, String url, String title) {
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra(URL, url);
        intent.putExtra(TITLE, title);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_webview;
    }

    @Override
    public void initView() {
        mToolbar = findViewById(R.id.web_toolbar);
        mTitleTv = findViewById(R.id.web_title);
        mWebView = findViewById(R.id.web_view);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        ImmersionBar.with(this)
                .titleBar(mToolbar)
                .statusBarDarkFont(false)
                .init();
        initWebView();
        dialog.setLoadingColor(getResources().getColor(R.color.black));
    }

    private void initWebView() {
        WebSettings settings = mWebView.getSettings();
        settings.setLoadWithOverviewMode(true);
        settings.setJavaScriptEnabled(true);
        settings.setAppCacheEnabled(false);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
        settings.setSupportZoom(true);
        mWebView.setWebChromeClient(new MyWebChrome());
        mWebView.setWebViewClient(new MyWebClient());
    }

    @Override
    public void initListener() {
        mToolbar.setNavigationOnClickListener(listener ->
                finish()
        );
    }

    @Override
    public void initData() {
        mTitleTv.setText(mTitle);
        mWebView.loadUrl(mUrl);
    }

    @Override
    protected void initDataBeforeView() {
        super.initDataBeforeView();
        mUrl = getIntent().getStringExtra(URL);
        mTitle = getIntent().getStringExtra(TITLE);
    }

    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            finish();
        }
    }


    private class MyWebChrome extends WebChromeClient {

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            showLoading();
        }
    }

    private class MyWebClient extends WebViewClient {
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            disLoading();
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            showLoading();
        }
    }
}
