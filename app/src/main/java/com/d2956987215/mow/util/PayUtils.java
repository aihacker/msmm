package com.d2956987215.mow.util;

import android.content.Context;
import android.widget.Toast;

import com.d2956987215.mow.wxapi.WXPayResponse;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class PayUtils {
    public static IWXAPI api;
    public static final String APP_ID = "wxa68e3f31ba28d2c5";

    //TODO 微信支付第二步：将相应的参数加入到官方请求参数中，然后发送请求即可
    public static void toWxpay(Context context, WXPayResponse info) {
        //实例化IWXAPI
        api = WXAPIFactory.createWXAPI(context, APP_ID,false);
        //注册
        api.registerApp(APP_ID);
        //设置相应参数
        PayReq req = new PayReq();
        req.appId = info.data.str.appId;
        req.packageValue = "Sign=WXPay";
        req.nonceStr = info.data.str.nonceStr;
        req.sign = info.data.str.paySign;
        req.signType = info.data.str.signType;
        req.timeStamp = info.data.str.timeStamp;
        req.prepayId = info.data.str.packAge.split("=")[1];
        req.partnerId = "1486710102";
        Toast.makeText(context,"正在调起微信~",Toast.LENGTH_SHORT).show();
        //发送请求
        api.sendReq(req);
    }
}
