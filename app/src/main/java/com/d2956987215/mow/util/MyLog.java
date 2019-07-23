package com.d2956987215.mow.util;

import android.util.Log;


public class MyLog {
    private static final    boolean  flag=true;
        public  static  void   e(String tag,String result){
            if(flag)
            Log.e(tag,result);
        }
}
