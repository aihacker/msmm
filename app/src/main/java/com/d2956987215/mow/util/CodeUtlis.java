package com.d2956987215.mow.util;

import java.util.HashMap;
import java.util.Map;

public class CodeUtlis {

    public static String getCode(String mobile) {
        String time = String.valueOf(DisplayUtil.getCurrTime());
        String key = "T#rr#6ZS6lexJHDD";
        String code = DisplayUtil.md5(key + mobile + time);
        return code;
    }
}
