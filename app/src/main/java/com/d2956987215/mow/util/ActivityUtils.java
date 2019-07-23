package com.d2956987215.mow.util;

import android.content.Context;
import android.content.Intent;
import com.d2956987215.mow.activity.SplashActivity;
import com.d2956987215.mow.activity.login.LoginNewActivity;


public class ActivityUtils {

    public static void startLoginAcitivy(Context activity) {
//        Intent intent = new Intent(activity, SplashActivity.class);

        Intent intent = new Intent(activity, LoginNewActivity.class);
        activity.startActivity(intent);
    }
}
