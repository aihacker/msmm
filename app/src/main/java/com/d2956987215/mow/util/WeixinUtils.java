package com.d2956987215.mow.util;

import android.app.Activity;

import com.d2956987215.mow.constant.Const;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import com.d2956987215.mow.constant.Const;
import com.d2956987215.mow.rxjava.response.WeiPayBean;


/**
 * Created by Administrator on 2016/5/28.
 */
public class WeixinUtils {

    Activity activity;

    public WeixinUtils(Activity activity) {
        this.activity = activity;
    }

    /**
     * 组装微信支付参数
     */
    public void Pay(WeiPayBean weiPayBean) {
        PayReq req = new PayReq();
        req.appId = weiPayBean.getAppid();
        req.partnerId =weiPayBean.getPartnerId();
        req.prepayId =weiPayBean.getPrepayId();
        req.packageValue=weiPayBean.getPackageName();
        req.nonceStr = weiPayBean.getNonceStr();
        req.timeStamp = weiPayBean.getTimeStamp();
        req.sign = weiPayBean.getSign();
        req.extData	= "app data"; // optional
        sendPayReq(req);// 起掉支付
    }

    /**
     * 调用支付
     */
    private void sendPayReq(PayReq req ) {
        IWXAPI api = WXAPIFactory.createWXAPI(activity, Const.APP_ID,true);
        Const.APP_ID=req.appId;
        api.registerApp(req.appId);
        api.sendReq(req);

        //Log.e("tag","============================"+api.openWXApp());
    }



}
