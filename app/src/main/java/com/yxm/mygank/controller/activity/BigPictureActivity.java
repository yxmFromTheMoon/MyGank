package com.yxm.mygank.controller.activity;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.github.chrisbanes.photoview.PhotoView;
import com.gyf.immersionbar.ImmersionBar;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.yxm.mygank.MainActivity;
import com.yxm.mygank.R;
import com.yxm.mygank.common.base.BaseActivity;
import com.yxm.mygank.common.view.SavePictureDialog;
import com.yxm.mygank.imageloader.ImageLoaderManager;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by yxm on 2020/4/8
 *
 * @function
 */
public class BigPictureActivity extends BaseActivity {

    public static final String PICTURE_URL = "pictureUrl";
    private String mUrl;

    private PhotoView mPhotoView;

    private RxPermissions permissions = new RxPermissions(this);

    public static void start(Context context, String url) {
        Intent intent = new Intent(context, BigPictureActivity.class);
        intent.putExtra(PICTURE_URL, url);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_big_picture;
    }

    @Override
    public void initView() {
        showLoading();
        mPhotoView = findViewById(R.id.picture);
        mUrl = getIntent().getStringExtra(PICTURE_URL);
        ImmersionBar.with(this)
                .statusBarColor(R.color.black)
                .statusBarDarkFont(false)
                .init();
    }

    @Override
    public void initListener() {
        mPhotoView.setOnLongClickListener(v -> {
            Disposable disposable = permissions.request("android.permission.WRITE_EXTERNAL_STORAGE")
                    .subscribe(aBoolean -> {
                                if (aBoolean) {
                                    new SavePictureDialog(mContext, mUrl).show();
                                }
                            }
                    );
            disposable.dispose();
            return true;
        });
    }

    @Override
    public void initData() {
        ImageLoaderManager.getInstance().displayWithDefaultPlaceHolder(mPhotoView, mUrl);
        disLoading();
    }
}
