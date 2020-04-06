package com.yxm.mygank.common.event;

/**
 * Created by yxm on 2020/4/6
 *
 * @function 隐藏底部导航栏事件
 */
public class HideBottomViewEvent {

    //是否隐藏
    private boolean isHide;

    public HideBottomViewEvent(boolean isHide){
        this.isHide = isHide;
    }

    public boolean isHide() {
        return isHide;
    }

    public void setHide(boolean hide) {
        isHide = hide;
    }
}
