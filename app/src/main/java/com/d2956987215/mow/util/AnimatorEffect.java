package com.d2956987215.mow.util;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.content.Context;
import android.view.View;

import com.d2956987215.mow.R;


/**
 * @author xf
 *         Created by xf on 2017/9/29.
 */

public class AnimatorEffect {

    //点击按钮的缩放效果
    public static void setClickEffect(Context context, View view){
//        Animator animator= AnimatorInflater.loadAnimator(context, R.animator.click_scale);
//        animator.setTarget(view);
//        animator.start();
    }

    //点击按钮的缩放效果
    public static void scale(Context context, View view){
        Animator animator= AnimatorInflater.loadAnimator(context, R.animator.scale);
        animator.setTarget(view);
        animator.start();
    }
}
