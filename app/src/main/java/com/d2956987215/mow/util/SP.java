package com.d2956987215.mow.util;

/*
 * Created by lq on 2017/8/9.
 */

import android.content.Context;
import android.content.SharedPreferences;

public class SP {
    private static SharedPreferences sp;

    public static void init(Context context) {
        sp = context.getSharedPreferences("per", Context.MODE_PRIVATE);
    }

    public static void putString(String key, String value) {
        if(sp!=null){
            sp.edit().putString(key, value).commit();
        }

    }

    public static void removeString(String key) {
        sp.edit().remove(key).commit();
    }

    public static void putInt(String key, int value) {
        sp.edit().putInt(key, value).commit();
    }

    public static void putLong(String key, long value) {
        sp.edit().putLong(key, value).commit();
    }

    public static void putBoolean(String key, boolean value) {
        sp.edit().putBoolean(key, value).commit();
    }

    public static String getString(String key, String defValue) {
        if (sp != null) {
            return sp.getString(key, defValue);
        }
        return "";

    }

    public static int getInt(String key, int defValue) {
        if (sp != null) {
            return sp.getInt(key, defValue);
        }
        return -1;
    }

    public static boolean getBoolean(String key, boolean defValue) {
        return sp.getBoolean(key, defValue);
    }

    public static long getLong(String key, long defValue) {
        if (sp != null) {
            return sp.getLong(key, defValue);
        }
        return -1;

    }

    public static void clear() {
        sp.edit().clear().commit();
        sp.edit().putBoolean("show_guide", true).commit();
    }
}
