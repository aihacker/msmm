package com.d2956987215.mow.constant;


import android.os.Environment;

import java.io.File;

public class Const {


    public final static String ALIAS_TYPE = "alias_type";
    public final static String USER_ID = "user_id";


    /**
     * 资源文件地址
     */
    public final static String ROOT_PATH = "jianshang";
    public static final String XYYRL = "https://images.maishoumm.com/html/xieyi.html";
//    public static final String XYYRL = "pinduoduo://com.xunmeng.pinduoduo/duo_coupon_landing.html?goods_id=2672480170&pid=&t=JDj7m0HqSXQbTTWKnb0jjHkWGN3zVjAa9Hs5ZUD0O0s=";
    /**
     * 图片对应目录文件名
     */
    private final static String PICTURE_PATH = "picture";
    public static String pName, cName, tName, pId, cId, tId;//省市区 三级分类id和name
    //定位位置
    public static double Loc_lat;
    public static double Loc_lon;
    //注册位置
    public static String lat, lon;
    //消息数量
    public static int Message_count;
    public static int fresh_address;
    public static int Index = 1;
    /**
     * 会员头像路径
     */
    public static final String KEY_HEAD_IMAGE_OUT_PATH = "head_image_out_path";
    //修改或者编辑
    public static String type;

    public static String APP_ID = "wx3f5193b437228bfd";

    // 默认存放文件下载的路径
    public final static String DEFAULT_SAVE_FILE_PATH = Environment
            .getExternalStorageDirectory()
            + File.separator
            + "jianshagn"
            + File.separator + "download" + File.separator;

    public static String phone;
    public static String code;
    public static String sjhuaxin;
    public static String area;
    public static String tjptype="1";
    public static String getSjhuaxin() {
        return sjhuaxin;
    }

    public static void setSjhuaxin(String sjhuaxin) {
        Const.sjhuaxin = sjhuaxin;
    }
}
