package com.d2956987215.mow.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.baichuan.android.trade.AlibcTradeSDK;
import com.alibaba.baichuan.trade.biz.login.AlibcLogin;
import com.alibaba.baichuan.trade.biz.login.AlibcLoginCallback;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.d2956987215.mow.R;
import com.d2956987215.mow.activity.login.LoginActivity;
import com.d2956987215.mow.activity.login.RegisterActivity1;
import com.d2956987215.mow.activity.mine.BindPhoneActivity;
import com.d2956987215.mow.constant.Const;
import com.d2956987215.mow.eventbus.RefreshListData;
import com.d2956987215.mow.rxjava.Request;
import com.d2956987215.mow.rxjava.Result;
import com.d2956987215.mow.rxjava.RxJavaUtil;
import com.d2956987215.mow.rxjava.response.LoginResponse;
import com.d2956987215.mow.util.ActivityManager;
import com.d2956987215.mow.util.AnimatorEffect;
import com.d2956987215.mow.util.MyLog;
import com.d2956987215.mow.util.SP;
import com.d2956987215.mow.util.ToastUtil;
import com.example.urilslibrary.Utils;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;

import static com.d2956987215.mow.R.id.tv_taobao;


/**
 * 启动欢迎页面
 */
public class SplashActivity extends BaseActivity {
    @BindView(R.id.splash_image)
    ImageView splashImage;
    @BindView(R.id.tv_zhuce)
    TextView tv_zhuce;
    @BindView(R.id.rl_back_kd)
    RelativeLayout rl_back_kd;


    private static SplashActivity loginActivity;
    /**
     * 欢迎页动画
     */
    private Animation animation;
    private Context mContext;

    /**
     * 是否是第一次使用
     */
    private final String[] PERMISSIONS = new String[]{Manifest.permission.CAMERA
            , Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.READ_PHONE_STATE};

    // 打开相机请求Code，多个权限请求Code
    private final int REQUEST_CODE_PERMISSIONS = 2;
    private AlibcLogin alibcLogin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginActivity = this;
        mContext = this;
//        ButterKnife.bind(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @OnClick({R.id.tv_zhuce, R.id.tv_denglu, tv_taobao, R.id.rl_back_kd})
    public void onViewClicked(View view) {
//        AnimatorEffect.setClickEffect(getApplicationContext(), view);
        switch (view.getId()) {
            case R.id.tv_zhuce:
                Intent intent = new Intent(this, RegisterActivity1.class);
                startActivity(intent);
                break;
            case R.id.tv_denglu:
                intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.rl_back_kd:
//                Utils.ToastShort(this,"kd");
//                MainActivity mainActivity = MainActivity.mainActivity;
//
//                if (mainActivity != null) {
//                    mainActivity.finish();
//                }
                intent = new Intent(this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

//                while (this.star)
                finish();
                break;
            case tv_taobao:
                alibcLogin = AlibcLogin.getInstance();
//                if (Build.VERSION.SDK_INT == 26) {
//                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
//                } else {
//                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//                }
                if (alibcLogin.isLogin()) {
                    alibcLogin.logout(new AlibcLoginCallback() {
                        @Override
                        public void onSuccess(int i) {
                            loginAli();
                        }

                        @Override
                        public void onFailure(int i, String s) {
                            Log.e("tag", "s--" + s);

                        }
                    });
                } else {
                    loginAli();
                }


                break;

        }

    }

    private void loginAli() {
        alibcLogin.showLogin(new AlibcLoginCallback() {

            @Override
            public void onSuccess(int i) {
                //获取淘宝用户信息
                MyLog.e("tagdeng", "获取淘宝用户信息: " + AlibcLogin.getInstance().getSession());
                ToastUtils.showShort("淘宝登录成功");
                login();
            }

            @Override
            public void onFailure(int code, String msg) {
                Toast.makeText(SplashActivity.this, "登录失败 ",
                        Toast.LENGTH_LONG).show();
            }
        });
    }


    private void login() {

        SP.putString("avatarUrl", AlibcLogin.getInstance().getSession().avatarUrl);
        SP.putString("openSid", AlibcLogin.getInstance().getSession().openSid);
        SP.putString("openId", AlibcLogin.getInstance().getSession().openId);
        SP.putString("nick", AlibcLogin.getInstance().getSession().nick);

        Map<String, String> map = new HashMap<>();
        map.put("avatarUrl", AlibcLogin.getInstance().getSession().avatarUrl);
        map.put("openSid", AlibcLogin.getInstance().getSession().openSid);
        map.put("openId", AlibcLogin.getInstance().getSession().openId);
        map.put("nick", AlibcLogin.getInstance().getSession().nick);
        EventBus.getDefault().post(new RefreshListData());
        new Request<LoginResponse>().request(RxJavaUtil.xApi().taobaologin(map), "淘宝登陆", SplashActivity.this, false, new Result<LoginResponse>() {
            @Override
            public void get(LoginResponse response) {
                Intent intent1 = null;

                //   SP.putString("phone", response.getRetval().getToken());
                if (StringUtils.isEmpty(response.getRetval().getToken()))
                    intent1 = new Intent(SplashActivity.this, RegisterActivity1.class);
                else {
                    SP.putInt("userId", response.getRetval().getUid());
                    SP.putString("token", response.getRetval().getToken());
                    SP.putString("roletypes", response.getRetval().getRoletypes());
                    SP.putString("uproletype", response.getRetval().getUproletypes());
                    SP.putString("deadline", response.getRetval().getDeadline());
                    mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_ALIAS, SPUtils.getInstance().getString(Const.USER_ID, response.getRetval().getUid() + "")));
                    intent1 = new Intent(SplashActivity.this, MainActivity.class);
                }
//                intent1.putExtra("isRefreshData", true);
                startActivity(intent1);
                finish();
            }
        });
    }


    @Override
    public void onStop() {
        super.onStop();
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(MSG_SET_ALIAS);
        }
    }


    @Override
    public void show(Object obj) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        Utils.ToastShort(SplashActivity.this,"123");
        /**
         * 销毁电商SDK相关资源引用，防止内存泄露
         */
        AlibcTradeSDK.destory();


    }

    private static final int MSG_SET_ALIAS = 1001;
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_SET_ALIAS:
                    // 调用 JPush 接口来设置别名。
                    JPushInterface.setAliasAndTags(SplashActivity.this.getApplicationContext(),
                            (String) msg.obj,
                            null,
                            null);
                    break;
                default:
            }
        }
    };


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);

//                while (this.star)
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
