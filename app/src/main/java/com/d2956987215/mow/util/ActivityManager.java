package com.d2956987215.mow.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import com.d2956987215.mow.activity.SplashActivity;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by lq
 */

public class ActivityManager {
    private static ActivityManager instance;
    private static List<Activity> activities = new ArrayList<>();

    public  void addActivity(Activity activity) {
        activities.add(activity);
    }

    public  void removeActivity(Activity activity) {
        activities.remove(activity);
    }
    /**
     * 单一实例
     */
    public static ActivityManager getAppManager() {
        if (instance == null) {
            instance = new ActivityManager();
        }
        return instance;
    }
    private ActivityManager() {}
    public static Activity getCurrentActivity() {
        if (!activities.isEmpty()) {
            return activities.get(activities.size() - 1);
        } else {
            return null;
        }
    }

    /**
     * 判断activity是否存在
     * @param
     * @param className
     * @return
     */
    public static boolean isExitActivity(Context context, String className){
        Intent intent = new Intent();
        intent.setClassName(context.getPackageName(), "xxx.xxx.XxxActivity");
        ResolveInfo resolveInfo = context.getPackageManager().resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY);
        if(resolveInfo != null) {
            return true;
        }else{
            return false;
        }
    }
    /**
     * 结束所有Activity
     */
    public static void finishAllActivity() {
        if(activities!=null&&activities.size()>0) {
            for (int i = 0, size = activities.size(); i < size; i++) {
                if (null != activities.get(i)) {
                    //finishActivity方法中的activity.isFinishing()方法会导致某些activity无法销毁
                    //貌似跳转的时候最后一个activity 是finishing状态，所以没有执行
                    //内部实现不是很清楚，但是实测结果如此，使用下面代码则没有问题
                    // find by TopJohn
                    //finishActivity(activityStack.getProperty(i));
                    if(!activities.get(i).getClass().getName().equals("com.d2956987215.mow.activity.MainActivity")){
                        activities.get(i).finish();
                    }
                    //break;
                }
            }
            activities.clear();
        }

    }
    public static void finishguoAllActivity() {
        if(activities!=null&&activities.size()>0) {
            for (int i = 0, size = activities.size(); i < size; i++) {
//                if (null != activities.get(i)&&activities.get(i)!= SplashActivity.loginActivity) {
                    //finishActivity方法中的activity.isFinishing()方法会导致某些activity无法销毁
                    //貌似跳转的时候最后一个activity 是finishing状态，所以没有执行
                    //内部实现不是很清楚，但是实测结果如此，使用下面代码则没有问题
                    // find by TopJohn
//                    finishActivity(activityStack.getProperty(i));
//                    if(!activities.get(i).getClass().getName().equals("com.d2956987215.mow.activity.MainActivity")){
//                        activities.get(i).finish();
//                    }
                    activities.get(i).finish();
//                    activities.get(i).finish();
                    //break;
//                }
            }
            activities.clear();
        }

    }

    public static List<Activity> getActivities(){
        return activities;
    }
}