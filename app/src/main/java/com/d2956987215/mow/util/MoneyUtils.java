package com.d2956987215.mow.util;

public class MoneyUtils {

    public static String getMoney(String money) {
        if (money.length() == 5)
            return money.substring(0, 1) + "." + money.substring(1, 2) + money.substring(2, 3) + "万";
        else if (money.length() == 6)
            return money.substring(0, 2) + "万";
        else if (money.length() == 7)
            return money.substring(0, 3) + "万";
        else if (money.length() == 8)
            return money.substring(0, 4) + "万";
        else
            return money;

    }

}
