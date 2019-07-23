package com.d2956987215.mow.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.RegexUtils;
import com.d2956987215.mow.R;
import com.d2956987215.mow.activity.BaseActivity;
import com.d2956987215.mow.eventbus.GetCodeSuccess;
import com.d2956987215.mow.presenter.LoginPresenter;
import com.d2956987215.mow.util.AnimatorEffect;
import com.d2956987215.mow.util.StringUtil;
import com.d2956987215.mow.util.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

import static com.d2956987215.mow.R.id.et_pass;

public class LoginPhoneActivity extends BaseActivity implements TextWatcher {

    @BindView(R.id.et_account)
    EditText etAccount;
    @BindView(et_pass)
    EditText etPass;
    @BindView(R.id.tv_duanxin_login)
    TextView tv_duanxin_login;
    @BindView(R.id.tv_yanzhengma)
    TextView tv_yanzhengma;
    @BindView(R.id.bt_login)
    Button bt_login;


    private TimeCount count;
    private LoginPresenter presenter;
    private String inviteCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new LoginPresenter(this);
        EventBus.getDefault().register(this);
        inviteCode =getIntent().getStringExtra("yaoqingma");
        etPass.addTextChangedListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_phone_login;
    }

    @Override
    protected String title() {
        return getString(R.string.user_login);
    }

    @Override
    public void show(Object obj) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (!TextUtils.isEmpty(etAccount.getText().toString().trim()) && !TextUtils.isEmpty(etPass.getText().toString().trim())) {
            bt_login.setBackground(getResources().getDrawable(R.drawable.bg_loginred));
            bt_login.setClickable(true);
        } else {
            bt_login.setBackground(getResources().getDrawable(R.drawable.bg_login));
            bt_login.setClickable(false);
        }
    }


    @Override
    public void afterTextChanged(Editable editable) {

    }

    @OnClick({R.id.bt_login, R.id.tv_forget_pass, R.id.ll_register, R.id.tv_duanxin_login, R.id.tv_yanzhengma})
    public void onViewClicked(View view) {
        AnimatorEffect.setClickEffect(getApplicationContext(), view);
        switch (view.getId()) {
            case R.id.bt_login:
                if (TextUtils.isEmpty(etAccount.getText())) {
                    ToastUtil.show(this, "请输入手机号");
                    return;
                }
                if (TextUtils.isEmpty(etPass.getText())) {
                    ToastUtil.show(this, "请输入验证码");
                    return;
                }



                presenter.loginphone(etAccount.getText().toString(), etPass.getText().toString());
                break;
            case R.id.tv_forget_pass:
                startActivity(new Intent(getApplicationContext(), FindPassActivity.class));
                break;
            case R.id.ll_register:
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
                break;

            case R.id.tv_duanxin_login:
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                break;
            case R.id.tv_yanzhengma:

                if(!RegexUtils.isMobileExact(etAccount.getText().toString())){
                    ToastUtil.show(this,"请输入正确的手机号");
                    return;
                }
                presenter.getLoginCode(etAccount.getText().toString());
                break;
        }
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
//        Glide.with(getContext()).load(changeHead.head).into(circleHead);
        count = new LoginPhoneActivity.TimeCount(60000, 1000);
        count.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
