package com.d2956987215.mow.activity.mine;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.baichuan.trade.biz.login.AlibcLogin;
import com.alibaba.baichuan.trade.biz.login.AlibcLoginCallback;
import com.d2956987215.mow.R;
import com.d2956987215.mow.activity.BaseActivity;
import com.d2956987215.mow.activity.login.LoginNewActivity;
import com.d2956987215.mow.eventbus.GetCodeSuccess;
import com.d2956987215.mow.presenter.MinePresenter;
import com.d2956987215.mow.rxjava.Request;
import com.d2956987215.mow.rxjava.Result;
import com.d2956987215.mow.rxjava.RxJavaUtil;
import com.d2956987215.mow.rxjava.response.BaseResponse;
import com.d2956987215.mow.rxjava.response.LoginResponse;
import com.d2956987215.mow.util.AnimatorEffect;
import com.d2956987215.mow.util.SP;
import com.d2956987215.mow.util.StringUtil;
import com.d2956987215.mow.util.ToastUtil;
import com.d2956987215.mow.util.User;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

import static com.d2956987215.mow.R.id.et_phone;

public class BindTBActivity extends BaseActivity implements TextWatcher {
    @BindView(et_phone)
    TextView etPhone;
    @BindView(R.id.et_auth_code)
    EditText etAuthCode;
    @BindView(R.id.tv_yanzhengma)
    TextView tv_yanzhengma;
    @BindView(R.id.bt_next)
    Button bt_next;

    @BindView(R.id.rl_tb_name)
    View rl_tb_name;
    @BindView(R.id.tv_tb_name)
    TextView tv_tb_name;

    private String phone;
    private TimeCount count;
    private MinePresenter presenter;
    private String bindTb;
    private int type;
    private String message;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindTb = getIntent().getStringExtra("bindTb");
        type = getIntent().getIntExtra("type",0);
        if (2==type) {
            message = "去绑定微信";
        } else {
            message = "去绑定淘宝";
        }
        presenter = new MinePresenter(this);
        etAuthCode.addTextChangedListener(this);
        phone = SP.getString("phone", null);
        if (!StringUtil.isBlank(phone)) {
            String phonetext = phone.substring(0, 3) + "****" + phone.substring(7, 11);
            etPhone.setText(phonetext);
        } else {
            ToastUtil.show(this, "手机号为空,暂不支持修改");
            finish();
        }
        if (getIntent().getStringExtra("lcode") != null) {
            etAuthCode.setText(getIntent().getStringExtra("lcode"));
        }
        if (TextUtils.equals(bindTb, "1")) {
            bt_next.setText("解除绑定");
            rl_tb_name.setVisibility(View.VISIBLE);
            tv_tb_name.setText(SP.getString("taobaoname", null));
        } else {
            bt_next.setText(message);
            rl_tb_name.setVisibility(View.GONE);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_bind_tb;
    }

    @Override
    public void show(Object obj) {

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (!TextUtils.isEmpty(etAuthCode.getText().toString().trim())) {
            bt_next.setBackground(getResources().getDrawable(R.drawable.bg_loginred));
            bt_next.setClickable(true);
        } else {
            bt_next.setBackground(getResources().getDrawable(R.drawable.bg_login));
            bt_next.setClickable(false);
        }
    }


    @Override
    public void afterTextChanged(Editable editable) {

    }

    @OnClick({R.id.bt_next, R.id.tv_yanzhengma})
    public void onViewClicked(View view) {
        AnimatorEffect.setClickEffect(getApplicationContext(), view);
        switch (view.getId()) {
            case R.id.bt_next:
                if (TextUtils.isEmpty(etAuthCode.getText())) {
                    ToastUtil.show(this, "请输入验证码");
                    return;
                }
                makeSureCode();

                break;
            case R.id.tv_yanzhengma:
                presenter.registerAuthCode(phone);
                count = new TimeCount(60000, 1000);
                count.start();
                break;
        }
    }

    @Override
    protected String title() {
        return "绑定淘宝";
    }

    /*倒计时类 */
    public class TimeCount extends CountDownTimer {
        /**
         * @param millisInFuture    The number of millis in the future from the call
         *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
         *                          is called.
         * @param countDownInterval The interval along the way to receive
         *                          {@link #onTick(long)} callbacks.
         */
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            //计时过程中
            tv_yanzhengma.setClickable(false);
            tv_yanzhengma.setText(millisUntilFinished / 1000 + "秒后重新发送");
        }

        @Override
        public void onFinish() {
            //计时完成
            tv_yanzhengma.setText("重新获取");
            tv_yanzhengma.setClickable(true);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getCodeSuccess(GetCodeSuccess getCodeSuccess) {
        count = new TimeCount(60000, 1000);
        count.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void bindTb() {
        final AlibcLogin alibcLogin = AlibcLogin.getInstance();


        alibcLogin.showLogin(new AlibcLoginCallback() {
//            @Override
//            public void onSuccess() {
//                //获取淘宝用户信息
//                Log.i("tagdeng", "获取淘宝用户信息: " + AlibcLogin.getInstance().getSession());
//                Map<String, String> map = new HashMap<>();
//                map.put("avatarUrl", AlibcLogin.getInstance().getSession().avatarUrl);
//                map.put("openSid", AlibcLogin.getInstance().getSession().openSid);
//                map.put("openid", AlibcLogin.getInstance().getSession().openId);
//                map.put("nick", AlibcLogin.getInstance().getSession().nick);
//                map.put("user_id", User.uid() + "");
//                new Request<LoginResponse>().request(RxJavaUtil.xApi().taobaobangding(map, "Bearer " + User.token()), "淘宝绑定", BindTBActivity.this, false, new Result<LoginResponse>() {
//                    @Override
//                    public void get(LoginResponse response) {
//                        ToastUtil.show(BindTBActivity.this, response.message);
//                        setResult(1);
//                        finish();
//                    }
//                });
//            }

            @Override
            public void onSuccess(int i) {
                //获取淘宝用户信息
                Log.i("tagdeng", "获取淘宝用户信息: " + AlibcLogin.getInstance().getSession());
                Map<String, String> map = new HashMap<>();
                map.put("avatarUrl", AlibcLogin.getInstance().getSession().avatarUrl);
                map.put("openSid", AlibcLogin.getInstance().getSession().openSid);
                map.put("openid", AlibcLogin.getInstance().getSession().openId);
                map.put("nick", AlibcLogin.getInstance().getSession().nick);
                map.put("user_id", User.uid() + "");
                new Request<LoginResponse>().request(RxJavaUtil.xApi().taobaobangding(map, "Bearer " + User.token()), "淘宝绑定", BindTBActivity.this, false, new Result<LoginResponse>() {
                    @Override
                    public void get(LoginResponse response) {
                        ToastUtil.show(BindTBActivity.this, response.message);
                        setResult(1);
                        finish();
                    }
                });
            }

            @Override
            public void onFailure(int code, String msg) {
                Toast.makeText(BindTBActivity.this, "授权失败 ",
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    private void unBindTb() {
        Map<String, String> map = new HashMap<>();
        map.put("retaobao", "0");
        map.put("user_id", User.uid() + "");
        new Request<>().request(RxJavaUtil.xApi().changepeson(map, "Bearer " + User.token()), "解绑淘宝", this, false, new Result<BaseResponse>() {
            @Override
            public void get(BaseResponse response) {
                ToastUtil.show(BindTBActivity.this, response.message);
                setResult(1);
                finish();
            }
        });
    }

    public void makeSureCode() {
        if (TextUtils.isEmpty(phone)) {
            ToastUtil.show(this, "请输入手机号");
            return;
        }
        if (TextUtils.isEmpty(etAuthCode.getText().toString())) {
            ToastUtil.show(this, "请输入验证码");
            return;
        }
        Map<String, String> map = new HashMap<>();
        map.put("mobile", phone);
        map.put("user_id", User.uid() + "");
        map.put("code", etAuthCode.getText().toString());
        new Request<BaseResponse>().request(RxJavaUtil.xApi().makeSureCode(map, "Bearer " + User.token()), "检验验证", BindTBActivity.this, true, new Result<BaseResponse>() {
            @Override
            public void get(BaseResponse response) {
                ToastUtil.show(BindTBActivity.this, response.message);
                if (TextUtils.equals(bindTb, "1")) {
                    if (type==2) {
                        unBindWx();
                    } else {
                        unBindTb();
                    }

                } else {
                    if (type==2) {
                        bindWx();
                    } else {
                        bindTb();
                    }

                }
            }
        });
    }

    private void unBindWx() {
        UMShareAPI.get(this).deleteOauth(this, SHARE_MEDIA.WEIXIN, null);
    }

    private void bindWx() {
        UMShareAPI.get(this).getPlatformInfo(this, SHARE_MEDIA.WEIXIN, authListener);
    }

    UMAuthListener authListener = new UMAuthListener() {
        /**
         * @desc 授权开始的回调
         * @param platform 平台名称
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {

        }

        /**
         * @desc 授权成功的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         * @param data 用户资料返回
         */
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {

//            Toast.makeText(getApplicationContext(), "授权成功", Toast.LENGTH_LONG).show();
//            wxLogin(data);
            Map<String, String> map = new HashMap<>();
            map.put("avatarUrl", data.get("iconurl"));
            map.put("user_id", User.uid() + "");
            map.put("open_id", data.get("openid"));
            map.put("unionid", data.get("unionid"));
            map.put("nick", data.get("name"));


            new Request<LoginResponse>().request(RxJavaUtil.xApi().taobaobangding(map, "Bearer " + User.token()), "微信绑定", BindTBActivity.this, false, new Result<LoginResponse>() {
                @Override
                public void get(LoginResponse response) {
                    ToastUtil.show(BindTBActivity.this, response.message);
                    setResult(2);
                    finish();
                }
            });


        }

        /**
         * @desc 授权失败的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {

            Toast.makeText(getApplicationContext(), "微信授权失败：" + t.getMessage(), Toast.LENGTH_LONG).show();

        }

        /**
         * @desc 授权取消的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         */
        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            Toast.makeText(getApplicationContext(), "取消微信登录", Toast.LENGTH_LONG).show();
        }
    };
}
