package com.yxm.mygank.network.core;

/**
 * Created by yxm on 2020/4/1
 *
 * @function 自定义exception
 */
public class OkHttpException extends Exception {
    //错误描述
    private String description;
    //错误码
    private int errorCode;

    public OkHttpException(String description,int errorCode){
        this.description = description;
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getDescription() {
        return description;
    }
}
