package com.d2956987215.mow.DESUtils;



public class JiaMiUtils {
    public static String key;
    public static String iv;
    public static long time;
    public static String DESIV;

    public static String getkey(String time, String appkey) {
        String a = "";
        time=time+appkey;
        StringBuffer buffer = new StringBuffer(MD5Utils.md5Password(time, 1));
        String b = buffer.toString();
        //a = b.substring(12, 20).toLowerCase();
        return b;
    }

    public static String getiv(String time, String appscreate) {
        String a = "";
        StringBuffer buffer3 = new StringBuffer(MD5Utils.md5Password(time, 1));//d080db3b03e22080ce132023fc0f306c
        String dd = buffer3.substring(12, 20);
        a = dd.toLowerCase() + appscreate;
        StringBuffer buffer4 = new StringBuffer(MD5Utils.md5Password(a, 1));
        a = buffer4.substring(24, 32).toUpperCase();

        return a;
    }
//    public static String getkey(String str, String salt) {
//        if (str != null && !str.equals("")) {
//            try {
//                str = str + "{" + salt.toString() + "}";
//                MessageDigest md5 = MessageDigest.getInstance("MD5");
//                char[] HEX = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
//                byte[] md5Byte = md5.digest(str.getBytes("UTF8"));
//                StringBuffer sb = new StringBuffer();
//                for (int i = 0; i < md5Byte.length; i++) {
//                    sb.append(HEX[(int) (md5Byte[i] & 0xff) / 16]);
//                    sb.append(HEX[(int) (md5Byte[i] & 0xff) % 16]);
//                }
//                str = sb.toString();
//                str = str.substring(12, 20).toLowerCase();
//            } catch (NoSuchAlgorithmException e) {
//            } catch (Exception e) {
//            }
//        }
//        return str;
//    }

}
