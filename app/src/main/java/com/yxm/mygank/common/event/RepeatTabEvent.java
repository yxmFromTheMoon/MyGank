package com.yxm.mygank.common.event;

/**
 * Created by yxm on 2020/4/4
 *
 * @function 底部导航栏重复点击事件
 */
public class RepeatTabEvent {
    private int index;

    public RepeatTabEvent(int index){
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
}
