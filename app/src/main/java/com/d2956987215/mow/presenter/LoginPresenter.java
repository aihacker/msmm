package com.d2956987215.mow.presenter;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.text.TextUtils;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.d2956987215.mow.R;
import com.d2956987215.mow.activity.BaseActivity;
import com.d2956987215.mow.activity.MainActivity;
import com.d2956987215.mow.activity.login.LoginActivity;
import com.d2956987215.mow.activity.login.SettingPawActivity;
import com.d2956987215.mow.activity.mine.ChangePassActivity;
import com.d2956987215.mow.activity.mine.SetMiMaActivity;
import com.d2956987215.mow.constant.Const;
import com.d2956987215.mow.dialog.SettingPasswordDialog;
import com.d2956987215.mow.eventbus.GetCodeSuccess;
import com.d2956987215.mow.eventbus.RefreshListData;
import com.d2956987215.mow.rxjava.Request;
import com.d2956987215.mow.rxjava.Result;
import com.d2956987215.mow.rxjava.RxJavaUtil;
import com.d2956987215.mow.rxjava.response.BaseResponse;
import com.d2956987215.mow.rxjava.response.LoginResponse;
import com.d2956987215.mow.rxjava.response.RegisterResponse;
import com.d2956987215.mow.util.DisplayUtil;
import com.d2956987215.mow.util.SP;
import com.d2956987215.mow.util.ToastUtil;
import com.d2956987215.mow.util.User;
import com.d2956987215.mow.widgets.CustomDialog;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by lq on 2018/3/15.
 */

public class LoginPresenter {
    private BaseActivity activity;

    public LoginPresenter(BaseActivity activity) {
        this.activity = activity;
    }

    public void login(String loginName, String loginPwd) {
        if ("".equals(loginName) || "".equals(loginPwd)) {
            ToastUtil.show(activity, "请输入账户或者密码");
            return;
        }
        Map<String, String> map = new HashMap<>();
        map.put("mobile", loginName);
        map.put("password", loginPwd);

        new Request<LoginResponse>().request(RxJavaUtil.xApi().login(map), "登录", activity, true, new Result<LoginResponse>() {
            @Override
            public void get(LoginResponse response) {
                //做一些存储等的工作
                SP.putInt("userId", response.getRetval().getUid());
                SP.putString("token", response.getRetval().getToken());
                SP.putString("roletypes", response.getRetval().getRoletypes());
                SP.putString("uproletype", response.getRetval().getUproletypes());
                SP.putString("deadline", response.getRetval().getDeadline());
                //   SP.putString("phone", response.getRetval().getToken());
                mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_ALIAS, SPUtils.getInstance().getString(Const.USER_ID, response.getRetval().getUid() + "")));
//                EventBus.getDefault().post(new RefreshListData());
                Intent intent1 = new Intent(activity, MainActivity.class);
//                intent1.putExtra("isRefreshData", false);
                activity.startActivity(intent1);
                activity.finish();
            }

        });
    }

    public void loginphone(String loginName, String loginPwd) {
        Map<String, String> map = new HashMap<>();
        map.put("mobile", loginName);
        map.put("code", loginPwd);
        Const.phone = loginName;
        Const.code = loginPwd;
        new Request<LoginResponse>().request(RxJavaUtil.xApi().loginphone(map), "手机号登录", activity, true, new Result<LoginResponse>() {
            @Override
            public void get(LoginResponse response) {
                //做一些存储等的工作
                SP.putInt("userId", response.getRetval().getUid());
                SP.putString("token", response.getRetval().getToken());
                SP.putString("roletypes", response.getRetval().getRoletypes());
                SP.putString("uproletype", response.getRetval().getUproletypes());
                SP.putString("deadline", response.getRetval().getDeadline());
                //   SP.putString("phone", response.getRetval().getToken());
                Intent intent1 = new Intent(activity, MainActivity.class);
                intent1.putExtra("isRefreshData", true);
                activity.startActivity(intent1);
                activity.finish();
            }
        });
    }

    /**
     * 获取登录验证码
     *
     * @param mobile
     */
    public void getLoginCode(String mobile) {
        String time = String.valueOf(DisplayUtil.getCurrTime());
        Map<String, String> map = new HashMap<>();
        String key = "T#rr#6ZS6lexJHDD";
        String code = DisplayUtil.md5(key + mobile + time);
        map.put("mobile", mobile);
        map.put("time", time);
        map.put("code", code);
        String area = Const.area;
        if (TextUtils.isEmpty(area)) {
            area = "86";
        }else {
            area=area.trim().replace("+","");
        }
        map.put("areaCode", area);
        new Request<BaseResponse>().request(RxJavaUtil.xApi().getcode(map), "手机号登录", activity, true, new Result<BaseResponse>() {
            @Override
            public void get(BaseResponse response) {
                ToastUtil.show(activity, activity.getString(R.string.send_success));
                EventBus.getDefault().post(new GetCodeSuccess());
            }
        });
    }

    public void register(String loginName, String loginPwd, String reUserPwd, String mobileCode) {
        if ("".equals(loginName) || "".equals(loginName) || "".equals(reUserPwd) || "".equals(mobileCode)) {
            ToastUtil.show(activity, activity.getString(R.string.please_complete));
            return;
        }
        Map<String, String> map = new HashMap<>();
        map.put("loginName", loginName);
        map.put("loginPwd", loginPwd);
        map.put("reUserPwd", reUserPwd);
        map.put("mobileCode", mobileCode);
        new Request<RegisterResponse>().request(RxJavaUtil.xApi().register(map), "注册", activity, false, new Result<RegisterResponse>() {
            @Override
            public void get(RegisterResponse response) {
                activity.finish();
                ToastUtil.show(activity, activity.getString(R.string.register_success));
            }
        });
    }

    public void register1(final String mobile, final String lcode, String verifycode) {
        if ("".equals(mobile) || "".equals(lcode) || "".equals(verifycode)) {
            ToastUtil.show(activity, activity.getString(R.string.please_complete));
            return;
        }

        /**
         *         map.put("avatarUrl", AlibcLogin.getInstance().getSession().avatarUrl);
         *         map.put("openSid", AlibcLogin.getInstance().getSession().openSid);
         *         map.put("openId", AlibcLogin.getInstance().getSession().openId);
         *         map.put("nick", AlibcLogin.getInstance().getSession().nick);
         */
        Map<String, String> map = new HashMap<>();
        map.put("mobile", mobile);
        map.put("lcode", lcode);
        map.put("verifycode", verifycode);
        map.put("avatarUrl", SP.getString("avatarUrl", ""));
        map.put("openSid", SP.getString("openSid", ""));
        map.put("openId", SP.getString("openId", ""));
        map.put("nick", SP.getString("nick", ""));

//微信
        map.put("open_id", SP.getString("open_idw", ""));
        String avatarUrlw = SP.getString("avatarUrlw", "");
        if (!TextUtils.isEmpty(avatarUrlw)) {
            map.put("avatarUrl", avatarUrlw);
        }

        map.put("unionid", SP.getString("openSidw", ""));
        map.put("userNick", SP.getString("userNick", ""));

        new Request<RegisterResponse>().request(RxJavaUtil.xApi().register(map), "注册", activity, false, new Result<RegisterResponse>() {
            @Override
            public void get(RegisterResponse response) {
                Const.phone = mobile;
                SP.putInt("userId", response.getRetval().getUid());
                SP.putString("token", response.getRetval().getToken());
                SP.putString("roletypes", response.getRetval().getRoletypes());
//                SP.putString("uproletype", response.getRetval().getUproletype());
                SP.putString("deadline", response.getRetval().getDeadline());
                SP.putString("phone", mobile);
//                CustomDialog.Builder builder = new CustomDialog.Builder(activity)
//                        .setTitle("设置登录密码")
//                        .setMessage("手机尚未设置登录密码，去设置")
//                        .setPositiveButton("设置", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
////                                Intent intent = new Intent(activity, SetMiMaActivity.class);
//                                Intent intent = new Intent(activity, SettingPawActivity.class);
//                                intent.putExtra("lcode", lcode);
//                                intent.putExtra("mobile", mobile);
//                                intent.putExtra("phone", mobile);
//                                activity.startActivity(intent);
//                                activity.finish();
//                            }
//                        });
//                builder.setNegativeButton("跳过", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
////                        SP.putInt("userId", response.getRetval().getUid());
////                        SP.putString("token", response.getRetval().getToken());
//                        //   SP.putString("phone", response.getRetval().getToken());
//                        Const.setSjhuaxin("1");
//                        activity.startActivity(new Intent(activity, MainActivity.class));
//                        activity.finish();
//                    }
//                });
//                builder.create().show();
                SettingPasswordDialog settingPasswordDialog = new SettingPasswordDialog(activity, new SettingPasswordDialog.CallBack() {
                    @Override
                    public void NO() {


                        // Intent intent = new Intent(activity, SetMiMaActivity.class);
                        Intent intent = new Intent(activity, SettingPawActivity.class);
                        intent.putExtra("lcode", lcode);
                        intent.putExtra("mobile", mobile);
                        intent.putExtra("phone", mobile);
                        activity.startActivity(intent);
                        activity.finish();
                    }

                    @Override
                    public void qx() {
                        Const.setSjhuaxin("1");
                        activity.startActivity(new Intent(activity, MainActivity.class));
                        activity.finish();
                    }
                });
                settingPasswordDialog.show();


            }
        });
    }

    //    public void findPd(String userPhone, String newPass, String oldpass, String mobileCode) {
//        if ("".equals(loginName) || "".equals(loginName) || "".equals(reUserPwd) || "".equals(mobileCode)) {
//            ToastUtil.show(activity, activity.getString(R.string.please_complete));
//            return;
//        }
//        Map<String, String> map = new HashMap<>();
//        map.put("userPhone", userPhone);
//        map.put("loginPwd", newPass);
//        map.put("reUserPwd", oldpass);
//        map.put("mobileCode", mobileCode);
//        new Request<>().request(RxJavaUtil.xApi().findPd(map), "找回密码", activity, false, new Result<BaseResponse>() {
//            @Override
//            public void get(BaseResponse response) {
//                activity.finish();
//                ToastUtil.show(activity, activity.getString(R.string.register_success));
//            }
//        });
//    }
    public void findPassword(String phone, String newPass, String ensurePass, String code) {
        if (phone.equals("") || newPass.equals("") || ensurePass.equals("") || code.equals("")) {
            ToastUtil.show(activity, activity.getString(R.string.please_complete));
            return;
        }
        new Request<>().request(RxJavaUtil.xApi().findPswd(phone, newPass, ensurePass, code), "找回密码", activity, false, new Result<BaseResponse>() {
            @Override
            public void get(BaseResponse response) {
                ToastUtil.show(activity, response.message);
                activity.finish();
            }
        });
    }

    public void findPasswordCode(String userphone) {
        if ("".equals(userphone)) {
            ToastUtil.show(activity, activity.getString(R.string.phone_not_null));
            return;
        }
        Map<String, String> map = new HashMap<>();
        map.put("userPhone", userphone);
        new Request<>().request(RxJavaUtil.xApi().findcode(map), "找回密码验证码", activity, false, new Result<BaseResponse>() {
            @Override
            public void get(BaseResponse response) {
                ToastUtil.show(activity, activity.getString(R.string.send_success));
            }
        });

    }

    public void registerAuthCode(String userPhone, String lcode) {
        String time = String.valueOf(DisplayUtil.getCurrTime());
        String key = "T#rr#6ZS6lexJHDD";
        String code = DisplayUtil.md5(key + userPhone + time);
        if ("".equals(userPhone)) {
            ToastUtil.show(activity, activity.getString(R.string.phone_not_null));
            return;
        }
        Map<String, String> map = new HashMap<>();
        map.put("mobile", userPhone);
        map.put("lcode", lcode);
        map.put("time", time);
        map.put("code", code);
        String area = Const.area;
        if (TextUtils.isEmpty(area)) {
            area = "86";
        }else {
            area=area.trim().replace("+","");
        }
        map.put("areaCode", area);
        new Request<>().request(RxJavaUtil.xApi().yanzhengzhuce(map), "获取验证码", activity, false, new Result<BaseResponse>() {
            @Override
            public void get(BaseResponse response) {
                ToastUtil.show(activity, activity.getString(R.string.send_success));
                EventBus.getDefault().post(new GetCodeSuccess());
            }
        });
    }

    private static final int MSG_SET_ALIAS = 1001;
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_SET_ALIAS:
                    // 调用 JPush 接口来设置别名。
                    JPushInterface.setAliasAndTags(activity.getApplicationContext(),
                            (String) msg.obj,
                            null,
                            null);
                    break;
                default:
            }
        }
    };

}
