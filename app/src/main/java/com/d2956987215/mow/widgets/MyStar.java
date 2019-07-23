package com.d2956987215.mow.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.iarcuschin.simpleratingbar.SimpleRatingBar;

/**
 * Created by lq on 2018/3/22.
 */

public class MyStar extends SimpleRatingBar {

    public MyStar(Context context) {
        super(context);
    }

    public MyStar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyStar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        return false;
    }
}
