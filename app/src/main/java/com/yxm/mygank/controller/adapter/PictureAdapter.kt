package com.yxm.mygank.controller.adapter

import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.LayoutRes
import coil.ImageLoader
import coil.load
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.yxm.mygank.R
import com.yxm.mygank.common.MyApplication
import com.yxm.mygank.common.util.ScreenHelper
import com.yxm.mygank.imageloader.ImageLoaderManager
import com.yxm.mygank.model.bean.ContentBean

/**
 * Created by yxm on 2020/4/6
 *
 * @function 图片adapter
 */
class PictureAdapter(@LayoutRes layoutId: Int) : BaseQuickAdapter<ContentBean, BaseViewHolder>(layoutId) {

    override fun convert(helper: BaseViewHolder, item: ContentBean) {
        helper.setText(R.id.picture_description, item.desc)
        val imageView = helper.getView<ImageView>(R.id.picture)
        displayScaleImage(imageView.layoutParams, imageView, item.url)
        helper.addOnClickListener(R.id.picture)
    }

    private fun displayScaleImage(layoutParams: ViewGroup.LayoutParams, imageView: ImageView, url: String) {
        val screenWidth = ScreenHelper.getScreenWidth(imageView.context)
        layoutParams.width = (screenWidth - 5 * 4) / 2
        layoutParams.height = ScreenHelper.getScreenHeight(imageView.context) / 3
        imageView.layoutParams = layoutParams

        imageView.load(url){
            size(layoutParams.width,layoutParams.height)
            crossfade(true)
            placeholder(R.mipmap.placeholderwhite)
        }
    }
}