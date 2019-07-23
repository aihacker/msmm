package com.d2956987215.mow.rxjava;

/**
 * Created by lq on 2018/1/24.
 */

public abstract class Result<T> {
    public abstract void get(T response);
    public void onServerError(String errDesc){}  //服务器错误
    public void onBackErrorMessage(T response){}  //服务器返回错误提示信息
}
