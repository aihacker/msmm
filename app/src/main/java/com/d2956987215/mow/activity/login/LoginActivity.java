package com.d2956987215.mow.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.baichuan.trade.biz.login.AlibcLogin;
import com.alibaba.baichuan.trade.biz.login.AlibcLoginCallback;
import com.d2956987215.mow.R;
import com.d2956987215.mow.activity.BaseActivity;
import com.d2956987215.mow.activity.MainActivity;
import com.d2956987215.mow.presenter.LoginPresenter;
import com.d2956987215.mow.util.AnimatorEffect;
import com.d2956987215.mow.util.SP;

import butterknife.BindView;
import butterknife.OnClick;

import static com.d2956987215.mow.R.id.et_account;

public class LoginActivity extends BaseActivity implements TextWatcher {

    @BindView(et_account)
    EditText etAccount;
    @BindView(R.id.et_pass)
    EditText etPass;
    @BindView(R.id.tv_duanxin_login)
    TextView tv_duanxin_login;
    @BindView(R.id.bt_login)
    Button bt_submit;


    private LoginPresenter presenter;
    public static LoginActivity loginActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findViewById(R.id.tv_submit).setVisibility(View.VISIBLE);
        loginActivity = this;
        ((TextView) findViewById(R.id.tv_submit)).setText("注册");
        findViewById(R.id.tv_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        presenter = new LoginPresenter(this);
        if (SP.getInt("userId", 0) != 0) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
        etPass.addTextChangedListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected String title() {
        return getString(R.string.user_login);
    }


    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (!TextUtils.isEmpty(etAccount.getText().toString().trim()) && !TextUtils.isEmpty(etPass.getText().toString().trim())) {
            bt_submit.setBackground(getResources().getDrawable(R.drawable.bg_loginred));
            bt_submit.setClickable(true);
        } else {
            bt_submit.setBackground(getResources().getDrawable(R.drawable.bg_login));
            bt_submit.setClickable(false);
        }
    }


    @Override
    public void afterTextChanged(Editable editable) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


    }

    @OnClick({R.id.bt_login, R.id.tv_forget_pass, R.id.ll_register, R.id.tv_duanxin_login})
    public void onViewClicked(View view) {
        AnimatorEffect.setClickEffect(getApplicationContext(), view);
        switch (view.getId()) {
            case R.id.bt_login:
                presenter.login(etAccount.getText().toString(), etPass.getText().toString());
                break;
            case R.id.tv_forget_pass:
                startActivity(new Intent(getApplicationContext(), FindPassActivity.class));
                break;
            case R.id.ll_register:
                Intent intent = new Intent(getApplicationContext(), FindPassActivity.class);
                intent.putExtra("name", "忘记密码");
                // startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
                break;
            case R.id.tv_duanxin_login:
                startActivity(new Intent(getApplicationContext(), LoginPhoneActivity.class));
                break;

        }
    }

    @Override
    protected void onBack() {
        AlibcLogin login = AlibcLogin.getInstance();
        if (login.isLogin()) {
            login.logout(new AlibcLoginCallback() {
                @Override
                public void onSuccess(int i) {

                }

                @Override
                public void onFailure(int i, String s) {

                }
            });
        }
        super.onBack();
    }

    @Override
    public void show(Object obj) {

    }


}
