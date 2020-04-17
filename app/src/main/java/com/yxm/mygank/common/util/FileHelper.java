package com.yxm.mygank.common.util;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.yxm.mygank.imageloader.GlideApp;

import java.io.IOException;
import java.io.OutputStream;

import static android.os.Environment.DIRECTORY_PICTURES;

/**
 * Created by yxm on 2020/4/13
 *
 * @function 文件相关类
 */
public class FileHelper {

    private Context mContext;

    private FileHelper() {
    }

    public void init(Context context) {
        mContext = context;
    }

    private static class SingleTon {
        @SuppressLint("StaticFieldLeak")
        private static FileHelper mInstance = new FileHelper();
    }

    public static FileHelper getInstance() {
        return SingleTon.mInstance;
    }

    /**
     * 适配Android10作用域存储
     *
     * @param bitmap
     */
    private void addBitmapToAlbum(Bitmap bitmap) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, System.currentTimeMillis() + ".jpg");
        contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, DIRECTORY_PICTURES);
        } else {
            contentValues.put(MediaStore.MediaColumns.DATA, Environment.getExternalStorageDirectory().getPath() + "/" + DIRECTORY_PICTURES + "/" + System.currentTimeMillis() + ".jpg");
        }
        Uri uri = mContext.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
        if (uri != null) {
            try (OutputStream outputStream = mContext.getContentResolver().openOutputStream(uri)) {
                if (outputStream != null) {
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Toast.makeText(mContext, "保存成功", Toast.LENGTH_SHORT).show();
    }

    /**
     * 下载图片
     *
     * @param url
     */
    public void saveImage(String url) {
        GlideApp.get(mContext).clearMemory();
        GlideApp.with(mContext)
                .asBitmap()
                .load(url)
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        addBitmapToAlbum(resource);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                });
    }
}
