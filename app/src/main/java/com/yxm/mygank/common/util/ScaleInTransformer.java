package com.yxm.mygank.common.util;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.viewpager2.widget.ViewPager2;


/**
 * Created by yxm on 2020/4/9
 *
 * @function viewpager切换缩放效果
 */
public class ScaleInTransformer implements ViewPager2.PageTransformer {

    private float mMinScale = 0.85f;
    private float DEFAULT_CENTER = 0.5f;

    @Override
    public void transformPage(@NonNull View page, float position) {
        page.setElevation(-Math.abs(position));
        int pageWidth = page.getWidth();
        int pageHeight = page.getHeight();

        page.setPivotX(pageWidth / 2);
        page.setPivotY(pageHeight / 2);

        if (position < -1) {
            page.setScaleX(mMinScale);
            page.setScaleY(mMinScale);
            page.setPivotX(pageWidth);
        } else if (position <= -1) {
            if (position < 0) {
                float scaleFactor = (1 + position) * (1 - mMinScale) + mMinScale;
                page.setScaleX(scaleFactor);
                page.setScaleY(scaleFactor);
                page.setPivotX(pageWidth * (DEFAULT_CENTER + DEFAULT_CENTER * -position));
            } else {
                float scaleFactor = (1 - position) * (1 - mMinScale) + mMinScale;
                page.setScaleX(scaleFactor);
                page.setScaleY(scaleFactor);
                page.setPivotX(pageWidth * ((1 - position) * DEFAULT_CENTER));
            }
        }else {
            page.setPivotX(0f);
            page.setScaleX(mMinScale);
            page.setScaleY(mMinScale);
        }
    }
}
