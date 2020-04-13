package com.yxm.mygank.common.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Toast;

import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.yxm.mygank.common.MyApplication;
import com.yxm.mygank.imageloader.GlideApp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

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
     * 保存图片
     *
     * @param bitmap
     */
    private void saveImageToGallery(Bitmap bitmap) {
        File fileDir = mContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        if (!fileDir.exists()) {
            fileDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(fileDir, fileName);
        try (FileOutputStream outputStream = new FileOutputStream(file)) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            MediaStore.Images.Media.insertImage(mContext.getContentResolver(),
                    file.getAbsolutePath(), fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //通知图库更新
        Uri uri = Uri.fromFile(file);
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        intent.setData(uri);
        mContext.sendBroadcast(intent);
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
                        saveImageToGallery(resource);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                });
    }
}
