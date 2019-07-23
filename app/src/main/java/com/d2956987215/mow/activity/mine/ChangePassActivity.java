package com.d2956987215.mow.activity.mine;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.d2956987215.mow.R;
import com.d2956987215.mow.activity.BaseActivity;
import com.d2956987215.mow.eventbus.GetCodeSuccess;
import com.d2956987215.mow.presenter.MinePresenter;
import com.d2956987215.mow.util.AnimatorEffect;
import com.d2956987215.mow.util.SP;
import com.d2956987215.mow.util.StringUtil;
import com.d2956987215.mow.util.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

import static com.d2956987215.mow.R.id.et_phone;

public class ChangePassActivity extends BaseActivity implements TextWatcher {

    @BindView(et_phone)
    TextView etPhone;
    @BindView(R.id.et_auth_code)
    EditText etAuthCode;
    @BindView(R.id.et_new_pass)
    EditText etNewPass;
    @BindView(R.id.tv_yanzhengma)
    TextView tv_yanzhengma;
    @BindView(R.id.bt_next)
    Button bt_next;


    private TimeCount count;

    private MinePresenter presenter;
    private String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new MinePresenter(this);
        etAuthCode.addTextChangedListener(this);
        etNewPass.addTextChangedListener(this);
        phone = SP.getString("phone", null);
        if (!StringUtil.isBlank(phone)) {
            String phonetext = phone.substring(0, 3) + "****" + phone.substring(7, 11);
            etPhone.setText(phonetext);
        } else {
            ToastUtil.show(this, "手机号为空,暂不支持修改");
            finish();
        }
        if(getIntent().getStringExtra("lcode")!=null){
            etAuthCode.setText(getIntent().getStringExtra("lcode"));

        }

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (!TextUtils.isEmpty(etAuthCode.getText().toString().trim())
                && !TextUtils.isEmpty(etNewPass.getText().toString().trim())
                ) {
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

    @Override
    protected int getLayoutId() {
        return R.layout.activity_change_pass;
    }

    @Override
    public void show(Object obj) {

    }

    @Override
    protected String title() {
        return getString(R.string.change_pass);
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

                if (TextUtils.isEmpty(etNewPass.getText())) {
                    ToastUtil.show(this, "请输入新密码");
                    return;
                }


                presenter.changePass(phone, etAuthCode.getText().toString(), etNewPass.getText().toString());
                break;
            case R.id.tv_yanzhengma:
                presenter.registerAuthCode(phone);
                count = new TimeCount(60000, 1000);
                count.start();
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
        count = new TimeCount(60000, 1000);
        count.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}
