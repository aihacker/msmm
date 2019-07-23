package com.d2956987215.mow.rxjava.response;

import org.json.JSONObject;

/**
 * Created by Administrator on 2016/5/28.
 */
public class WeiPayBean {

    public WeiPayBean(String seq){
        try {
            JSONObject jsonObject=new JSONObject(seq);
            appid=jsonObject.getString("appId");
            partnerId=   jsonObject.getString("partnerId");
            prepayId= jsonObject.getString("prepayId");
            nonceStr= jsonObject.getString("nonceStr");
            timeStamp= jsonObject.getString("timeStamp");
            //    packageName= jsonObject.getString("package").replace("\u003d", "=");
             packageName="Sign=WXPay";
            sign= jsonObject.getString("sign");

         }catch (Exception e){
            e.printStackTrace();
        }


    }
    private String appid;

    private String partnerId;
    private String prepayId;
    private String nonceStr;
    private String timeStamp;
    private String packageName;
    private String sign;

    public String getAppid() {
        return appid;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public String getPrepayId() {
        return prepayId;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getSign() {
        return sign;
    }
}
