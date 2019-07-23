package com.d2956987215.mow.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.d2956987215.mow.activity.BaseActivity;
import com.d2956987215.mow.activity.MainActivity;
import com.d2956987215.mow.eventbus.GetCodeSuccess;
import com.d2956987215.mow.presenter.MinePresenter;

import butterknife.BindView;
import butterknife.OnClick;

import com.d2956987215.mow.R;
import com.d2956987215.mow.activity.BaseActivity;
import com.d2956987215.mow.presenter.MinePresenter;
import com.d2956987215.mow.rxjava.Request;
import com.d2956987215.mow.rxjava.Result;
import com.d2956987215.mow.rxjava.RxJavaUtil;
import com.d2956987215.mow.rxjava.response.BaseResponse;
import com.d2956987215.mow.util.AnimatorEffect;
import com.d2956987215.mow.util.ToastUtil;
import com.d2956987215.mow.util.User;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

public class SetMiMaActivity extends BaseActivity implements TextWatcher {


    @BindView(R.id.et_new_pass)
    EditText etNewPass;

    @BindView(R.id.et_queren)
    EditText etConfirmPass;

    @BindView(R.id.bt_next)
    Button mBtnNext;

    @BindView(R.id.tv_cancel)
    TextView mTvCancel;


    private MinePresenter presenter;
    private String mCode;
    private String mMobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new MinePresenter(this);
        mCode = getIntent().getStringExtra("lcode");
        mMobile = getIntent().getStringExtra("mobile");

        etNewPass.addTextChangedListener(this);
        etConfirmPass.addTextChangedListener(this);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_set_mima;
    }

    @Override
    public void show(Object obj) {

    }


    @OnClick({R.id.bt_next, R.id.tv_cancel})
    public void onViewClicked(View view) {
        AnimatorEffect.setClickEffect(getApplicationContext(), view);
        switch (view.getId()) {
            case R.id.bt_next:

                String password = etNewPass.getText().toString();
                String passwordConfirm = etConfirmPass.getText().toString();

                if (StringUtils.isEmpty(password)) {
                    ToastUtils.showShort("请输入密码");
                    return;
                }

                if (StringUtils.isEmpty(passwordConfirm)) {
                    ToastUtils.showShort("请确认密码");
                    return;
                }
                if (!password.equals(passwordConfirm)) {
                    ToastUtils.showShort("两次密码不一致");
                    return;
                }

                changePass(mMobile, mCode, password);
                //presenter.changePass(etPhone.getText().toString(), etNewPass.getText().toString());
                break;
            case R.id.tv_cancel:
                goMain();
                break;
        }
    }

    public void changePass(String phone, String code, String newPass) {
        Map<String, String> map = new HashMap<>();
        map.put("password", newPass);
        map.put("user_id", User.uid() + "");
        new Request<>().request(RxJavaUtil.xApi().setPwd(map, "Bearer " + User.token()), "设置密码", SetMiMaActivity.this, false, new Result<BaseResponse>() {
            @Override
            public void get(BaseResponse response) {
                goMain();
            }
        });
    }


    private void goMain() {

        startActivity(new Intent(SetMiMaActivity.this, MainActivity.class));
        finish();
    }


    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (!TextUtils.isEmpty(etNewPass.getText().toString().trim()) && !TextUtils.isEmpty(etConfirmPass.getText().toString().trim())) {
            mBtnNext.setBackground(getResources().getDrawable(R.drawable.bg_loginred));
            mBtnNext.setClickable(true);
        } else {
            mBtnNext.setBackground(getResources().getDrawable(R.drawable.bg_login));
            mBtnNext.setClickable(false);
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}
