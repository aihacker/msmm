package com.d2956987215.mow.mvp.v;

/**
 * Created by lk on 2017/12/31.
 */

public interface TimerView<T> {
    void onCompile();
    void onRefresh(T message);
    void onError(T message);
    void onBegin(T message);
}
