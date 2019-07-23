package com.d2956987215.mow.mvp.p.imbl;


import com.d2956987215.mow.mvp.v.TimerView;

/**
 * Created by lk on 2017/12/31.
 */

public abstract class AbsRxTimerPresenter<V extends TimerView> {
    private  V view;
    public V getView() {
        return view;
    }
}
