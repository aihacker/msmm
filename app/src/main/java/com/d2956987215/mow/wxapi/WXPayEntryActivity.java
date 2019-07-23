package com.d2956987215.mow.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Window;

import com.d2956987215.mow.R;
import com.d2956987215.mow.activity.mine.MyHuiYuanDetailActivity;
import com.d2956987215.mow.util.PayUtils;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;


/**
 * Created by lq on 2017/6/20 0020.
 */

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private static final String TAG = "WXPayEntryActivity";
    public static IWXAPI api;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translent);

        api = WXAPIFactory.createWXAPI(this, PayUtils.APP_ID);
        api.handleIntent(getIntent(), this);

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    @Override
    public void onReq(BaseReq baseReq) {
        Log.i(TAG, "onReq: " + baseReq.checkArgs());
    }

    @Override
    public void onResp(BaseResp resp) {
        Log.i(TAG, "onResp: " + resp.errCode);
        Log.e(TAG, "onResp: " + resp.errStr);
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            switch (resp.errCode) {
                case 0://展示成功页面
                    Intent intent = new Intent();
                    intent.setAction("action_wx_pay_success");
                    sendBroadcast(intent);
                    finish();
                    break;
                case -1:
                    // TODO 注意微信的调用必须要有签名才能成功
                    //可能的原因：签名错误、未注册APPID、项目设置APPID不正确、注册的APPID与设置的不匹配、其他异常等。
                    Intent intent1=new Intent(MyHuiYuanDetailActivity.APPLY);
                    intent1.putExtra("apply_result",1);
                    sendBroadcast(intent1);
                    finish();
                    break;
                case -2://无需处理。发生场景：用户不支付了，点击取消，返回APP。
                    Intent intent2=new Intent(MyHuiYuanDetailActivity.APPLY);
                    intent2.putExtra("apply_result",2);
                    sendBroadcast(intent2);
                    finish();
                    break;
                case 2://无需处理。发生场景：用户不支付了，点击取消，返回APP。
                    finish();
                    break;
            }
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
