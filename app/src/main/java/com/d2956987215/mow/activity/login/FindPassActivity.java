package com.d2956987215.mow.activity.login;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.d2956987215.mow.R;
import com.d2956987215.mow.activity.BaseActivity;
import com.d2956987215.mow.presenter.LoginPresenter;

import butterknife.BindView;
import butterknife.OnClick;

public class FindPassActivity extends BaseActivity {

    @BindView(R.id.rl_back)
    RelativeLayout rlBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_auth_code)
    EditText etAuthCode;
    @BindView(R.id.tv_auth_code)
    TextView tvAuthCode;
    @BindView(R.id.et_pass)
    EditText etPass;
    @BindView(R.id.et_ensure_pass)
    EditText etEnsurePass;
    @BindView(R.id.bt_over)
    Button btOver;
    private LoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        findViewById(R.id.fl_close).setVisibility(View.VISIBLE);
        presenter = new LoginPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_find_pass;
    }

    @Override
    protected String title() {
        if(getIntent().getStringExtra("name")!=null){
            return getIntent().getStringExtra("name");
        }else {
            return getString(R.string.find_pass);
        }

    }

    @Override
    public void show(Object obj) {

    }

    @OnClick({R.id.rl_back, R.id.tv_title,R.id.tv_submit, R.id.et_phone, R.id.et_auth_code, R.id.tv_auth_code, R.id.et_pass, R.id.et_ensure_pass, R.id.bt_over})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                break;
            case R.id.tv_title:
                break;
            case R.id.tv_submit:
                break;
            case R.id.et_phone:
                break;
            case R.id.et_auth_code:
                break;
            case R.id.tv_auth_code:
                presenter.findPasswordCode(etPhone.getText().toString());
                break;
            case R.id.et_pass:
                break;
            case R.id.et_ensure_pass:
                break;
            case R.id.bt_over:
                presenter.findPassword(etPhone.getText().toString(),etPass.getText().toString(),etEnsurePass.getText().toString(),etAuthCode.getText().toString());
                break;
        }
    }
}
