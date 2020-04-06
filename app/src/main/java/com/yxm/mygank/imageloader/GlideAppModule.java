package com.yxm.mygank.imageloader;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.module.AppGlideModule;

import androidx.annotation.NonNull;

/**
 * Created by yxm on 2020/4/2
 *
 * @function glideAppModule
 */
@GlideModule
public class GlideAppModule extends AppGlideModule {
    @Override
    public void applyOptions(@NonNull Context context, @NonNull GlideBuilder builder) {
        int memoryCacheSizeBytes = 1024 * 1024 * 20; // 20mb
        int diskCacheSizeBytes = 1024 * 1024 * 100;  //100 MB
        builder.setMemoryCache(new LruResourceCache(memoryCacheSizeBytes))
                .setDiskCache(new InternalCacheDiskCacheFactory(context, diskCacheSizeBytes))
                .setDefaultTransitionOptions(Drawable.class, DrawableTransitionOptions.withCrossFade());
    }

    @Override
    public boolean isManifestParsingEnabled() {
        return false;
    }
}
