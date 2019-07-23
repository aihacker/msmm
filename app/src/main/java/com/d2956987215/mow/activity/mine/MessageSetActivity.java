package com.d2956987215.mow.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

import com.bumptech.glide.Glide;
import com.d2956987215.mow.R;
import com.d2956987215.mow.activity.BaseActivity;
import com.d2956987215.mow.activity.MainActivity;
import com.d2956987215.mow.activity.SplashActivity;
import com.d2956987215.mow.activity.home.MessageActivity;
import com.d2956987215.mow.activity.login.LoginNewActivity;
import com.d2956987215.mow.rxjava.Request;
import com.d2956987215.mow.rxjava.Result;
import com.d2956987215.mow.rxjava.RxJavaUtil;
import com.d2956987215.mow.rxjava.response.BaseResponse;
import com.d2956987215.mow.rxjava.response.PersonInfoResponse;
import com.d2956987215.mow.util.ActivityUtils;
import com.d2956987215.mow.util.AnimatorEffect;
import com.d2956987215.mow.util.SP;
import com.d2956987215.mow.util.ToastUtil;
import com.d2956987215.mow.util.User;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class MessageSetActivity extends BaseActivity {

    @BindView(R.id.cb_msg)
    CheckBox cb_msg;
    @BindView(R.id.cb_shouyi)
    CheckBox cb_shouyi;
    @BindView(R.id.cb_new)
    CheckBox cb_new;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initInfo();

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_message_set;
    }

    @Override
    public void show(Object obj) {

    }

    @Override
    protected String title() {
        return "消息提醒";
    }

    @OnClick({R.id.cb_msg, R.id.cb_shouyi, R.id.cb_new})
    public void onViewClicked(View view) {
        AnimatorEffect.setClickEffect(getApplicationContext(), view);
        String message_push = "";
        String money_push = "";
        String addmember_push = "";
        switch (view.getId()) {
            case R.id.cb_msg:
                if (cb_shouyi.isChecked()) {
                    money_push = "0";
                } else {
                    money_push = "1";
                }
                if (cb_new.isChecked()) {
                    addmember_push = "0";
                } else {
                    addmember_push = "1";
                }
                if (cb_msg.isChecked()) {
                    Map<String, String> map = new HashMap<>();
                    map.put("message_push", "0");
                    map.put("money_push", money_push);
                    map.put("addmember_push", addmember_push);
                    map.put("user_id", User.uid() + "");
                    new Request<>().request(RxJavaUtil.xApi().changepeson(map, "Bearer " + User.token()), "修改推送消息提醒", this, false, new Result<BaseResponse>() {
                        @Override
                        public void get(BaseResponse response) {
                            ToastUtil.show(MessageSetActivity.this, response.message);
                        }
                    });
                } else {
                    Map<String, String> map = new HashMap<>();
                    map.put("message_push", "1");
                    map.put("money_push", money_push);
                    map.put("addmember_push", addmember_push);
                    map.put("user_id", User.uid() + "");
                    new Request<>().request(RxJavaUtil.xApi().changepeson(map, "Bearer " + User.token()), "修改推送消息提醒", this, false, new Result<BaseResponse>() {
                        @Override
                        public void get(BaseResponse response) {
                            ToastUtil.show(MessageSetActivity.this, response.message);
                        }
                    });
                }


                break;
            case R.id.cb_shouyi:
                if (cb_msg.isChecked()) {
                    message_push = "0";
                } else {
                    message_push = "1";
                }
                if (cb_new.isChecked()) {
                    addmember_push = "0";
                } else {
                    addmember_push = "1";
                }
                if (cb_shouyi.isChecked()) {
                    Map<String, String> map = new HashMap<>();
                    map.put("money_push", "0");
                    map.put("message_push", message_push);
                    map.put("addmember_push", addmember_push);
                    map.put("user_id", User.uid() + "");
                    new Request<>().request(RxJavaUtil.xApi().changepeson(map, "Bearer " + User.token()), "修改收益消息提醒", this, false, new Result<BaseResponse>() {
                        @Override
                        public void get(BaseResponse response) {
                            ToastUtil.show(MessageSetActivity.this, response.message);
                        }
                    });
                } else {
                    Map<String, String> map = new HashMap<>();
                    map.put("money_push", "1");
                    map.put("message_push", message_push);
                    map.put("addmember_push", addmember_push);
                    map.put("user_id", User.uid() + "");
                    new Request<>().request(RxJavaUtil.xApi().changepeson(map, "Bearer " + User.token()), "修改收益消息提醒", this, false, new Result<BaseResponse>() {
                        @Override
                        public void get(BaseResponse response) {
                            ToastUtil.show(MessageSetActivity.this, response.message);
                        }
                    });
                }


                break;
            case R.id.cb_new:
                if (cb_msg.isChecked()) {
                    message_push = "0";
                } else {
                    message_push = "1";
                }
                if (cb_shouyi.isChecked()) {
                    money_push = "0";
                } else {
                    money_push = "1";
                }
                if (cb_new.isChecked()) {
                    Map<String, String> map = new HashMap<>();
                    map.put("addmember_push", "0");
                    map.put("money_push", money_push);
                    map.put("message_push", message_push);
                    map.put("user_id", User.uid() + "");
                    new Request<>().request(RxJavaUtil.xApi().changepeson(map, "Bearer " + User.token()), "修改新成员提醒", this, false, new Result<BaseResponse>() {
                        @Override
                        public void get(BaseResponse response) {
                            ToastUtil.show(MessageSetActivity.this, response.message);
                        }
                    });
                } else {
                    Map<String, String> map = new HashMap<>();
                    map.put("addmember_push", "1");
                    map.put("money_push", money_push);
                    map.put("message_push", message_push);
                    map.put("user_id", User.uid() + "");
                    new Request<>().request(RxJavaUtil.xApi().changepeson(map, "Bearer " + User.token()), "修改新成员提醒", this, false, new Result<BaseResponse>() {
                        @Override
                        public void get(BaseResponse response) {
                            ToastUtil.show(MessageSetActivity.this, response.message);
                        }
                    });
                }
                break;
        }
    }

    private void initInfo() {
        Map<String, String> map = new HashMap<>();
        if (User.uid() > 0) {
            map.put("user_id", User.uid() + "");
        } else {
//            Intent intent = new Intent(MessageSetActivity.this, SplashActivity.class);
//            Intent intent = new Intent(MessageSetActivity.this, LoginNewActivity.class);
//            startActivity(intent);
            ActivityUtils.startLoginAcitivy(this);
            return;
        }

        new Request<PersonInfoResponse>().request(RxJavaUtil.xApi1().personalInfo(map, "Bearer " + User.token()), "个人信息", MessageSetActivity.this, false, new Result<PersonInfoResponse>() {
            @Override
            public void get(PersonInfoResponse response) {
                if (response != null) {
                    if (response.getRetval() != null) {
                        if (response.getRetval().getMessage_push() == 0) {
                            cb_msg.setChecked(true);
                        } else {
                            cb_msg.setChecked(false);
                        }
                        if (response.getRetval().getMoney_push() == 0) {
                            cb_shouyi.setChecked(true);
                        } else {
                            cb_shouyi.setChecked(false);
                        }
                        if (response.getRetval().getAddmember_push() == 0) {
                            cb_new.setChecked(true);
                        } else {
                            cb_new.setChecked(false);
                        }
                    }
                }

            }
        });
    }

}
