package com.yxm.mygank.controller.activity

import android.content.Context
import android.content.Intent
import android.view.View
import coil.load
import com.github.chrisbanes.photoview.PhotoView
import com.gyf.immersionbar.ImmersionBar
import com.tbruyelle.rxpermissions2.RxPermissions
import com.yxm.mygank.R
import com.yxm.mygank.common.base.BaseActivity
import com.yxm.mygank.common.view.SavePictureDialog
import com.yxm.mygank.imageloader.ImageLoaderManager

/**
 * Created by yxm on 2020/4/8
 *
 * @function
 */
class BigPictureActivity : BaseActivity() {
    private var mUrl: String? = null
    private var mPhotoView: PhotoView? = null
    private val permissions = RxPermissions(this)
    override fun getLayoutId(): Int {
        return R.layout.activity_big_picture
    }

    override fun initView() {
        showLoading()
        mPhotoView = findViewById(R.id.picture)
        mUrl = intent.getStringExtra(PICTURE_URL)
        ImmersionBar.with(this)
                .statusBarColor(R.color.black)
                .statusBarDarkFont(false)
                .init()
    }

    override fun initListener() {
        mPhotoView!!.setOnLongClickListener { v: View? ->
            val disposable = permissions.request("android.permission.WRITE_EXTERNAL_STORAGE")
                    .subscribe { aBoolean: Boolean ->
                        if (aBoolean) {
                            SavePictureDialog(mContext, mUrl).show()
                        }
                    }
            disposable.dispose()
            true
        }
    }

    override fun initData() {
        mPhotoView?.load(mUrl)
        disLoading()
    }

    companion object {
        const val PICTURE_URL = "pictureUrl"
        @JvmStatic
        fun start(context: Context, url: String?) {
            val intent = Intent(context, BigPictureActivity::class.java)
            intent.putExtra(PICTURE_URL, url)
            context.startActivity(intent)
        }
    }
}