package com.d2956987215.mow.dialog;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.baichuan.trade.biz.login.AlibcLogin;
import com.alibaba.baichuan.trade.biz.login.AlibcLoginCallback;
import com.blankj.utilcode.util.StringUtils;
import com.d2956987215.mow.R;
import com.d2956987215.mow.activity.home.GuideWebViewActivity;
import com.d2956987215.mow.activity.home.OrdinaryWebViewActivity;
import com.d2956987215.mow.rxjava.Request;
import com.d2956987215.mow.rxjava.Result;
import com.d2956987215.mow.rxjava.RxJavaUtil;
import com.d2956987215.mow.rxjava.response.AuthTaoBaoResponse;
import com.d2956987215.mow.util.MyLog;
import com.d2956987215.mow.util.User;

import java.util.HashMap;
import java.util.Map;

import static com.blankj.utilcode.util.ActivityUtils.startActivity;
import static com.blankj.utilcode.util.ActivityUtils.startActivityForResult;


/**
 * Created by Administrator on 2016/5/19.
 */
public class AuthTaoBaoDialog extends BaseDialog implements View.OnClickListener {
    private Activity activity;
    private TextView mTvTitle, mTvDes, mTvAuth, mTvCancel;
    private ImageView mIvClose;
    private String mUrl, detailUrl;
    private AlibcLogin alibcLogin;

    public AuthTaoBaoDialog(Context context) {
        super(context);
        activity = (Activity) context;
        init();

    }

    private void init() {
        setContentView(R.layout.ml_auth_taobao);
        mTvTitle = findViewById(R.id.tv_title);
        mTvDes = findViewById(R.id.tv_des);
        mTvAuth = findViewById(R.id.tv_auth);
        mIvClose = findViewById(R.id.iv_close);
        mTvCancel = findViewById(R.id.tv_cancel);
        mTvTitle.setOnClickListener(this);
        mTvDes.setOnClickListener(this);
        mTvAuth.setOnClickListener(this);
        mIvClose.setOnClickListener(this);
        mTvCancel.setOnClickListener(this);


        Map<String, String> map = new HashMap<>();
        map.put("user_id", User.uid() + "");
        new Request<AuthTaoBaoResponse>().request(RxJavaUtil.xApi().authTaoBao(map, "Bearer " + User.token()), "领券分享淘宝授权", activity, false, new Result<AuthTaoBaoResponse>() {
            @Override
            public void get(final AuthTaoBaoResponse response) {
                mTvTitle.setText(response.getData().getTitle());
                mTvDes.setText(response.getData().getText());
                mTvAuth.setText(response.getData().getBtntitle());
                mUrl = response.getData().getH5url();
                detailUrl = response.getData().getUrl();
                show();
            }
        });

    }

    @Override
    public void onClick(View v) {
        dismiss();
        switch (v.getId()) {

            case R.id.iv_close:
                dismiss();

                break;
            case R.id.tv_auth:
                alibcLogin = AlibcLogin.getInstance();
                alibcLogin.showLogin(new AlibcLoginCallback() {

                    @Override
                    public void onSuccess(int i) {
                        //获取淘宝用户信息
                        MyLog.e("tagdeng", "获取淘宝用户信息: " + AlibcLogin.getInstance().getSession());
//                        ToastUtils.showShort("淘宝登录成功");

                        if (!StringUtils.isEmpty(mUrl)) {
                            dismiss();
                            Intent intent3 = new Intent(activity, GuideWebViewActivity.class);
                            intent3.putExtra("url", mUrl);
                            intent3.putExtra("name", "授权");
                            startActivityForResult(activity, intent3, 222);
                        }
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        Toast.makeText(activity, "请重新授权 ",
                                Toast.LENGTH_LONG).show();
                    }
                });


                break;
            case R.id.tv_cancel:
                Intent intent = new Intent(activity, OrdinaryWebViewActivity.class);
                intent.putExtra("name", "详情");
                intent.putExtra("url", detailUrl);
                startActivity(intent);
                break;

        }
    }


}
