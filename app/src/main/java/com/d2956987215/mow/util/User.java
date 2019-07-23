package com.d2956987215.mow.util;

/**
 * @author lq
 *         Created by lq on 2017/10/23.
 */

public class User {

    public static String token() {
        return SP.getString("token", "");
    }
    public static String upreletype() {
        return SP.getString("uproletype", "");
    }

    public static int uid() {
        return SP.getInt("userId", -1);
    }

    public static String head() {
        return SP.getString("head", "");
    }
    public static String roleType() {
        return SP.getString("roletypes", "0");
    }

    public static String name() {
        return SP.getString("name", "暂无");
    }
    public static String deadline() {
        return SP.getString("deadline", "0");
    }
    public static String remainAdNum() {
        return SP.getString("remainAdNum", "");
    }
}
