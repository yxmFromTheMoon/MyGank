package com.yxm.mygank.imageloader;

import android.content.Context;
import android.os.Looper;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.load.engine.cache.ExternalPreferredCacheDiskCacheFactory;
import com.yxm.mygank.R;
import com.yxm.mygank.common.util.ScreenHelper;

import java.io.File;

import androidx.annotation.DrawableRes;

/**
 * Created by yxm on 2020/4/2
 *
 * @function 图片加载类, 单例模式
 */
public class ImageLoaderManager {

    private ImageLoaderManager() {
    }

    private static class SingleHolder {
        private static ImageLoaderManager mInstance = new ImageLoaderManager();
    }

    public static ImageLoaderManager getInstance() {
        return SingleHolder.mInstance;
    }

    /**
     * 普通加载图片
     *
     * @param imageView
     * @param url
     */
    public void diplayImage(ImageView imageView, String url) {
        GlideApp.with(imageView.getContext())
                .load(url)
                .into(imageView);
    }

    /**
     * 默认占位图
     * @param imageView
     * @param url
     */
    public void displayWithDefaultPlaceHolder(ImageView imageView, String url) {
        displayWithPlaceHolder(imageView, url, R.mipmap.placeholderwhite);
    }

    /**
     * 自定义占位图加载图片
     *
     * @param imageView
     * @param url
     * @param holderId
     */
    public void displayWithPlaceHolder(ImageView imageView, String url, @DrawableRes int holderId) {
        GlideApp.with(imageView.getContext())
                .load(url)
                .placeholder(holderId)
                .error(holderId)
                .into(imageView);
    }

    public void displayWithNewWidthAndHeight(ImageView imageView, String url, int width, int height) {
        GlideApp.with(imageView.getContext())
                .load(url)
                .placeholder(R.drawable.brvah_sample_footer_loading)
                .error(0)
                .override(width, height)
                .into(imageView);
    }

    /**
     * 清除磁盘缓存
     * 耗时操作，在子线程中进行
     */
    private void clearDiskCache(Context context) {
        try {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                new Thread(() ->
                        GlideApp.get(context).clearDiskCache()
                ).start();
            } else {
                GlideApp.get(context).clearDiskCache();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 清除内存缓存
     *
     * @param context
     */
    private void clearMemoryCache(Context context) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            GlideApp.get(context).clearMemory();
        }
    }

    /**
     * 删除文件夹下的缓存文件
     *
     * @param path
     * @param isDelete
     */
    private void deleteFolderFile(String path, Boolean isDelete) {
        if (!TextUtils.isEmpty(path)) {
            try {
                File file = new File(path);
                if (file.isDirectory()) {
                    for (File item : file.listFiles()) {
                        deleteFolderFile(item.getAbsolutePath(), true);
                    }
                }
                if (isDelete) {
                    if (!file.isDirectory()) {
                        file.delete();
                    } else {
                        if (file.listFiles().length == 0) {
                            file.delete();
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 清除所有缓存
     *
     * @param context
     */
    public void clearAllCache(Context context) {
        clearDiskCache(context);
        clearMemoryCache(context);
        String imageCacheDir = context.getExternalCacheDir() + ExternalPreferredCacheDiskCacheFactory.DEFAULT_DISK_CACHE_DIR;
        deleteFolderFile(imageCacheDir, true);
    }
}