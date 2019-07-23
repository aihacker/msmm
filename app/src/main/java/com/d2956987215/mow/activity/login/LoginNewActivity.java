package com.d2956987215.mow.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.baichuan.trade.biz.login.AlibcLogin;
import com.alibaba.baichuan.trade.biz.login.AlibcLoginCallback;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.d2956987215.mow.R;
import com.d2956987215.mow.activity.APP;
import com.d2956987215.mow.activity.BaseActivity;
import com.d2956987215.mow.activity.MainActivity;
import com.d2956987215.mow.bean.AreaBean;
import com.d2956987215.mow.bean.CheckMobileBean;
import com.d2956987215.mow.constant.Const;
import com.d2956987215.mow.dialog.RegistDialog;

import com.d2956987215.mow.eventbus.RefreshListData;

import com.d2956987215.mow.rxjava.Request;
import com.d2956987215.mow.rxjava.Result;
import com.d2956987215.mow.rxjava.RxJavaUtil;
import com.d2956987215.mow.rxjava.response.LoginResponse;
import com.d2956987215.mow.util.DisplayUtil;
import com.d2956987215.mow.util.MyLog;
import com.d2956987215.mow.util.SP;
import com.d2956987215.mow.util.ToastUtil;
import com.example.qimiao.zz.uitls.ui.Density;
import com.example.urilslibrary.Utils;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;

public class LoginNewActivity extends BaseActivity {


    @BindView(R.id.rl_back)
    RelativeLayout rlBack;
    @BindView(R.id.iv_del_phone)
    ImageView ivDelPhone;
    @BindView(R.id.cl_text)
    LinearLayout clText;
    @BindView(R.id.bt_next)
    Button btNext;
    @BindView(R.id.ll_message)
    LinearLayout llMessage;
    @BindView(R.id.ll_wx)
    LinearLayout llWx;
    @BindView(R.id.ll_tb)
    LinearLayout llTb;
    @BindView(R.id.ll_phone)
    LinearLayout llPhone;
    @BindView(R.id.ll_login)
    LinearLayout llLogin;
    @BindView(R.id.tv_xy)
    TextView tvXy;
    @BindView(R.id.et_phone_num)
    EditText etPhoneNum;
    @BindView(R.id.tv_area_code)
    TextView tvArea;
    @BindView(R.id.bt_select_area)
    Button btSelectArea;
    private AlibcLogin alibcLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Density.setDensity(APP.getApplication(), this);
        super.onCreate(savedInstanceState);

//        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        setOnclick();
        EventBus.getDefault().register(this);
        Const.area = "86";
    }

    private void setOnclick() {
//        btNext.setClickable(false);
        etPhoneNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String s = editable.toString();
                if (s.length() > 0) {
                    ivDelPhone.setVisibility(View.VISIBLE);
                } else {
                    ivDelPhone.setVisibility(View.GONE);
                }
                boolean mobileNO = Utils.isMobileNO(s);
                if (mobileNO) {
                    btNext.setBackgroundResource(R.drawable.bt_login_nextd);
                    btNext.setTextColor(getContext().getResources().getColor(R.color.white));
                } else {
                    btNext.setBackgroundResource(R.drawable.bt_login_next);
                    btNext.setTextColor(getContext().getResources().getColor(R.color.black));
                }


            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login_new;
    }

    @Override
    public void show(Object obj) {

    }

    @OnClick({R.id.rl_back, R.id.bt_next, R.id.ll_message, R.id.ll_wx, R.id.ll_tb, R.id.ll_phone, R.id.ll_login, R.id.tv_xy, R.id.iv_del_phone, R.id.bt_select_area})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.bt_next:
                if (etPhoneNum.getText() == null) {
                    ToastUtil.show(this, "请输入正确的手机号");
                    return;
                }
                String s = etPhoneNum.getText().toString();
                boolean mobileNO = Utils.isMobileNO(s);
                if (!mobileNO) {
                    ToastUtil.show(this, "请输入正确的手机号");
                    return;
                }
                startLogin(s);

                break;
            case R.id.ll_message:
                break;
            case R.id.ll_wx:
                UMShareAPI.get(this).getPlatformInfo(LoginNewActivity.this, SHARE_MEDIA.WEIXIN, authListener);
                break;
            case R.id.ll_tb:
                alibcLogin = AlibcLogin.getInstance();

                if (alibcLogin.isLogin()) {
                    alibcLogin.logout(new AlibcLoginCallback() {
                        @Override
                        public void onSuccess(int i) {
                            loginAli();
                        }

                        @Override
                        public void onFailure(int i, String s) {

                        }
                    });
                } else {
                    loginAli();
                }

                break;
            case R.id.ll_phone:
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.ll_login:

                break;
            case R.id.tv_xy:
                startActivity(new Intent(this,WebViewActivity.class).putExtra("url",Const.XYYRL).putExtra("title","买手妈妈协议"));
                break;
            case R.id.iv_del_phone:
                etPhoneNum.setText("");
                break;
            case R.id.bt_select_area:
                startActivity(new Intent(this, AreaSelectActivity.class));
                break;


        }
    }

    private void startLogin(String s) {
//        String code = CodeUtlis.getCode(s);
        String time = String.valueOf(DisplayUtil.getCurrTime());
        String key = "T#rr#6ZS6lexJHDD";
        String code = DisplayUtil.md5(key + s + time);
        Map<String, String> map = new HashMap<>();
        map.put("mobile", s);
        map.put("code", code);
        map.put("time", time);
        Const.phone = s;
        new Request<CheckMobileBean>().request(RxJavaUtil.xApi().CheckMobile(map), "手机号验证", this, true, new Result<CheckMobileBean>() {
            @Override
            public void get(CheckMobileBean response) {
                if (response.getStatus_code() == 200) {
                    //已注册
                    if (response.getData().getIsregister() == 1) {
                        Intent intent = new Intent(LoginNewActivity.this, PasswordActivity.class);
                        intent.putExtra("phone", s);
                        intent.putExtra("area", tvArea.getText().toString());
                        startActivity(intent);
                    } else {
                        //未注册
                        RegistDialog dialog = new RegistDialog(LoginNewActivity.this, () -> {

                            Intent intent = new Intent(LoginNewActivity.this, RegisterActivity1.class);
                            intent.putExtra("phone", s);
                            startActivity(intent);

                        });
                        dialog.show();

                    }

                } else {
                    Utils.ToastShort(LoginNewActivity.this, "请求错误");
                }

            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onAreaCode(AreaBean.DataBean event) {
        Const.area = event.getAreaCode();
        String s = "+" + Const.area;
        tvArea.setText(s);
    }

    private void loginAli() {
        alibcLogin.showLogin(new AlibcLoginCallback() {
            @Override
            public void onSuccess(int i) {
                //获取淘宝用户信息
                MyLog.e("tagdeng", "获取淘宝用户信息: " + AlibcLogin.getInstance().getSession());
                ToastUtils.showShort("淘宝登录成功");
                tbLogin();
            }

            @Override
            public void onFailure(int code, String msg) {
                Toast.makeText(LoginNewActivity.this, "登录失败 ",
                        Toast.LENGTH_LONG).show();
            }
        });
    }


    private void tbLogin() {

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
        new Request<LoginResponse>().request(RxJavaUtil.xApi().taobaologin(map), "淘宝登陆", LoginNewActivity.this, false, new Result<LoginResponse>() {
            @Override
            public void get(LoginResponse response) {
                Intent intent1 = null;

                //   SP.putString("phone", response.getRetval().getToken());
                if (StringUtils.isEmpty(response.getRetval().getToken()))
                    intent1 = new Intent(LoginNewActivity.this, RegisterActivity1.class);
                else {
                    SP.putInt("userId", response.getRetval().getUid());
                    SP.putString("token", response.getRetval().getToken());
                    SP.putString("roletypes", response.getRetval().getRoletypes());
                    SP.putString("uproletype", response.getRetval().getUproletypes());
                    SP.putString("deadline", response.getRetval().getDeadline());
                    mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_ALIAS, SPUtils.getInstance().getString(Const.USER_ID, response.getRetval().getUid() + "")));
                    intent1 = new Intent(LoginNewActivity.this, MainActivity.class);
                }
//                intent1.putExtra("isRefreshData", true);
                startActivity(intent1);
                finish();
            }
        });
    }


    private static final int MSG_SET_ALIAS = 1001;
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_SET_ALIAS:
                    // 调用 JPush 接口来设置别名。
                    JPushInterface.setAliasAndTags(LoginNewActivity.this.getApplicationContext(),
                            (String) msg.obj,
                            null,
                            null);
                    break;
                default:
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
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

            Toast.makeText(getApplicationContext(), "授权成功", Toast.LENGTH_LONG).show();
            wxLogin(data);
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


    private void wxLogin( Map<String, String> data) {

        SP.putString("avatarUrlw", data.get("iconurl"));
        SP.putString("openSidw", data.get("unionid"));
        SP.putString("openIdw",  data.get("openid"));
        SP.putString("nickw",  data.get("name"));

        Map<String, String> map = new HashMap<>();
        map.put("avatarUrl", data.get("iconurl"));
        map.put("open_id", data.get("openid"));
        map.put("unionid", data.get("unionid"));
        map.put("userNick", data.get("name"));
        EventBus.getDefault().post(new RefreshListData());
        new Request<LoginResponse>().request(RxJavaUtil.xApi().WxLogin(map), "微信登陆", LoginNewActivity.this, false, new Result<LoginResponse>() {
            @Override
            public void get(LoginResponse response) {
                Intent intent1 = null;

                //   SP.putString("phone", response.getRetval().getToken());
                if (StringUtils.isEmpty(response.getRetval().getToken()))
                    intent1 = new Intent(LoginNewActivity.this, RegisterActivity1.class);
                else {
                    SP.putInt("userId", response.getRetval().getUid());
                    SP.putString("token", response.getRetval().getToken());
                    SP.putString("roletypes", response.getRetval().getRoletypes());
                    SP.putString("uproletype", response.getRetval().getUproletypes());
                    SP.putString("deadline", response.getRetval().getDeadline());
                    mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_ALIAS, SPUtils.getInstance().getString(Const.USER_ID, response.getRetval().getUid() + "")));
                    intent1 = new Intent(LoginNewActivity.this, MainActivity.class);
                }
//                intent1.putExtra("isRefreshData", true);
                startActivity(intent1);
                finish();
            }
        });
    }
}
