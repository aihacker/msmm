package com.d2956987215.mow.activity.login;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.d2956987215.mow.DESUtils.JiaMiUtils;
import com.d2956987215.mow.R;
import com.d2956987215.mow.activity.BaseActivity;
import com.d2956987215.mow.presenter.LoginPresenter;
import com.d2956987215.mow.rxjava.Request;
import com.d2956987215.mow.rxjava.Result;
import com.d2956987215.mow.rxjava.RxJavaUtil;
import com.d2956987215.mow.rxjava.response.SaoMaResponse;
import com.d2956987215.mow.util.AnimatorEffect;
import com.d2956987215.mow.util.MyLog;
import com.d2956987215.mow.util.ToastUtil;
import com.d2956987215.mow.util.User;
import com.d2956987215.mow.widgets.CircleImageView;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class RegisterActivity1 extends BaseActivity implements TextWatcher {


    @BindView(R.id.et_pass)
    EditText et_pass;
    @BindView(R.id.rl_invite_info)
    RelativeLayout rl_invite_info;
    @BindView(R.id.circle_head)
    CircleImageView circle_head;
    @BindView(R.id.tv_nickname)
    TextView tv_nickname;
    @BindView(R.id.bt_register)
    Button bt_register;
    private long time;
    private CountDownTimer countDownTimer;
    private final int timeInterval = 600;//监听et_pass改动,若timeInterval时间内没改动则去校验邀请码
    private boolean countDownTimerStopStatus;//true:停止  false：运行


    private LoginPresenter presenter;
    private SaoMaResponse.DataBean dataBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new LoginPresenter(this);
        et_pass.addTextChangedListener(this);
        countDownTimer = new CountDownTimer(timeInterval, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                saoyisao(et_pass.getText().toString());
                countDownTimerStopStatus = true;
            }
        };
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register1;
    }

    @Override
    protected String title() {
        return getString(R.string.user_register);
    }

    @Override
    public void show(Object obj) {

    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }


    @Override
    public void afterTextChanged(Editable editable) {
        bt_register.setBackground(getResources().getDrawable(R.drawable.bg_login));
        bt_register.setClickable(false);
        rl_invite_info.setVisibility(View.INVISIBLE);
        if (!TextUtils.isEmpty(et_pass.getText().toString().trim()) && et_pass.getText().length() > 4) {
            if (!countDownTimerStopStatus) {
                countDownTimer.cancel();
                countDownTimerStopStatus = true;
            }
            countDownTimer.start();
            countDownTimerStopStatus = false;
            time = System.currentTimeMillis();

        } else {
            bt_register.setBackground(getResources().getDrawable(R.drawable.bg_login));
            bt_register.setClickable(false);
            rl_invite_info.setVisibility(View.INVISIBLE);
        }
    }

    @OnClick({R.id.bt_register, R.id.ic_saoma})
    public void onViewClicked(View view) {
        AnimatorEffect.setClickEffect(getApplicationContext(), view);
        switch (view.getId()) {
            case R.id.bt_register:
                if (TextUtils.isEmpty(et_pass.getText())) {
                    ToastUtil.show(this, "请输入邀请码");
                    return;
                }
//                Intent intent = new Intent(this, LoginPhoneActivity1.class);
                Intent intent = new Intent(this, LoginPhoneNewActivity.class);
                intent.putExtra("yaoqingma", et_pass.getText().toString().trim());
                startActivity(intent);
                finish();


                break;
            case R.id.ic_saoma:

                RegisterActivity1PermissionsDispatcher.needsusWithPermissionCheck(RegisterActivity1.this);

                break;


        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 100) {
                et_pass.setText(data.getStringExtra("yaoqingma"));
                et_pass.setSelection(data.getStringExtra("yaoqingma").length());
            }
        }
    }

    @NeedsPermission(Manifest.permission.CAMERA)
    void needsus() {
        Intent intent = new Intent(this, CaptureActivity.class);
        startActivityForResult(intent, 100);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        RegisterActivity1PermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    private void saoyisao(final String saoma) {
        MyLog.e("tagjiage", saoma);
        Map<String, String> map = new HashMap<>();
        map.put("lcode", saoma);
        Date date = new Date();
        long time = date.getTime() / 1000;
        String key = JiaMiUtils.getkey(User.uid() + User.token(), String.valueOf(time));
        map.put("user_id", String.valueOf(User.uid()));
        map.put("timestamp", String.valueOf(time));
        map.put("sign", key);
        map.put("token", User.token());

        new Request<SaoMaResponse>().request(RxJavaUtil.xApi().saoma(map), "店铺套餐详情列表", this, false, new Result<SaoMaResponse>() {
            @Override
            public void get(SaoMaResponse response) {
                dataBean = response.getData();
                Glide.with(getApplicationContext()).load(dataBean.getAvatar()).error(R.mipmap.have_no_head).into(circle_head);
                tv_nickname.setText(dataBean.getNickname());
                bt_register.setBackground(getResources().getDrawable(R.drawable.bg_loginred));
                bt_register.setClickable(true);
                rl_invite_info.setVisibility(View.VISIBLE);
            }

            @Override
            public void onBackErrorMessage(SaoMaResponse response) {
                super.onBackErrorMessage(response);
                bt_register.setBackground(getResources().getDrawable(R.drawable.bg_login));
                bt_register.setClickable(false);
                rl_invite_info.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onServerError(String errDesc) {
                super.onServerError(errDesc);
                bt_register.setBackground(getResources().getDrawable(R.drawable.bg_login));
                bt_register.setClickable(false);
                rl_invite_info.setVisibility(View.INVISIBLE);
            }
        });


    }
}
