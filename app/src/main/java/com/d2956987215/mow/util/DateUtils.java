package com.d2956987215.mow.util;


import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期的大小
 */
public class DateUtils {

    /**
     * 比较两个日期的大小，日期格式为yyyy-MM-dd
     *
     * @param str2 the second date
     * @return true <br/>false
     */
    public static boolean isDateOneBigger(String str2) {
        //我要获取当前的日期
        //设置要获取到什么样的时间
        boolean isBigger = false;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dt1 = null;
        Date date = new Date();
        String createdate = sdf.format(date);
        Date dt2 = null;
        try {
            dt1 = sdf.parse(createdate);
            dt2 = sdf.parse(str2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (dt1.getTime() <= dt2.getTime()) {
            isBigger = true;

        } else {
            isBigger = false;
        }
        return isBigger;
    }


    /**
     * 比较两个日期的大小，日期格式为yyyy-MM-dd
     *
     * @param str2 the second date
     * @return true <br/>false
     */
    public static boolean isDate2Bigger(String str2) {
        boolean isBigger = false;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        MyLog.e("tagriqi", str2.toString());
        Date dt1 = new Date();
        Date dt2 = null;
        try {
            dt2 = sdf.parse(str2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (dt1.getTime() < dt2.getTime()) {
            isBigger = true;
        } else {
            isBigger = false;
        }
        return isBigger;
    }

    //两个时间戳是否是同一天 时间戳是long型的（11或者13）
    public static boolean isSameData(long currentTime, long lastTime) {
        try {
            Calendar nowCal = Calendar.getInstance();
            Calendar dataCal = Calendar.getInstance();
            SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
            SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
            String data1 = df1.format(currentTime);
            String data2 = df2.format(lastTime);
            java.util.Date now = df1.parse(data1);
            java.util.Date date = df2.parse(data2);
            nowCal.setTime(now);
            dataCal.setTime(date);
            return isSameDay(nowCal, dataCal);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean isSameDay(Calendar cal1, Calendar cal2) {
        if (cal1 != null && cal2 != null) {
            return cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA)
                    && cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)
                    && cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
        } else {
            return false;
        }
    }


    //计算几天前
    public static String getTimeFormatText(long time) {
        long minute = 60;// 1分钟
        long hour = 60 * minute;// 1小时
        long day = 24 * hour;// 1天

        if (time == 0) {
            return null;
        }
        long diff = new Date().getTime() / 1000 - time;
        long r = 0;
        if (diff > day) {
            if (diff > day * 8) {
                return getTime(time, "yyyy-MM-dd hh:MM");
            } else {
                r = (diff / day);
                return r + "天前";
            }

        }
        if (diff > hour) {
            r = (diff / hour);
            return r + "小时前";
        }
        if (diff > minute) {
            r = (diff / minute);
            return r + "分钟前";
        }
        return "刚刚";
    }

    //时间戳转时间
    public static String getTime(long time, String formatStr) {
        Date date = new Date(time * 1000);
        SimpleDateFormat format = new SimpleDateFormat(formatStr);
        return format.format(date);
    }

}
