package com.d2956987215.mow.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.d2956987215.mow.R;
import com.d2956987215.mow.activity.BaseActivity;
import com.d2956987215.mow.activity.MainActivity;
import com.d2956987215.mow.constant.Const;
import com.d2956987215.mow.presenter.LoginPresenter;
import com.d2956987215.mow.rxjava.Request;
import com.d2956987215.mow.rxjava.Result;
import com.d2956987215.mow.rxjava.RxJavaUtil;
import com.d2956987215.mow.rxjava.response.BaseResponse;
import com.d2956987215.mow.util.AnimatorEffect;
import com.d2956987215.mow.util.DisplayUtil;
import com.d2956987215.mow.util.ToastUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class RegisterActivity extends BaseActivity implements TextWatcher {


    @BindView(R.id.et_pass)
    EditText et_pass;
    @BindView(R.id.bt_register)
    Button bt_register;

    private LoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout viewById = findViewById(R.id.fl_close);
        if (viewById != null) {
            viewById.setVisibility(View.VISIBLE);
        }

        presenter = new LoginPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
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
        if (!TextUtils.isEmpty(et_pass.getText().toString().trim())) {
            bt_register.setBackground(getResources().getDrawable(R.drawable.bg_loginred));
            bt_register.setClickable(true);
        } else {
            bt_register.setBackground(getResources().getDrawable(R.drawable.bg_login));
            bt_register.setClickable(false);
        }
    }


    @Override
    public void afterTextChanged(Editable editable) {

    }

    @OnClick({R.id.bt_register, R.id.ll_login})
    public void onViewClicked(View view) {
        AnimatorEffect.setClickEffect(getApplicationContext(), view);
        switch (view.getId()) {
            case R.id.bt_register:
                if (TextUtils.isEmpty(et_pass.getText())) {
                    ToastUtil.show(this, "请输入邀请码");
                    return;
                }

                String time = String.valueOf(DisplayUtil.getCurrTime());
                String key = "T#rr#6ZS6lexJHDD";
                String code = DisplayUtil.md5(key + Const.phone + time);
                Map<String, String> map = new HashMap<>();
                map.put("lcode", et_pass.getText().toString());
                map.put("mobile", Const.phone);
                map.put("time", time);
                map.put("code", code);
                new Request<>().request(RxJavaUtil.xApi().yanzhengzhuce(map), "验证邀请码注册", this, false, new Result<BaseResponse>() {
                    @Override
                    public void get(BaseResponse response) {
                        ToastUtil.show(RegisterActivity.this, getString(R.string.register_success));
                        finish();
                        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                        startActivity(intent);

                    }
                });


                break;
            case R.id.ll_login:
                finish();
                break;

        }
    }
}
