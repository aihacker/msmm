package com.d2956987215.mow.activity.mine;

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

import com.blankj.utilcode.util.KeyboardUtils;
import com.d2956987215.mow.R;
import com.d2956987215.mow.activity.BaseActivity;
import com.d2956987215.mow.eventbus.GetCodeSuccess;
import com.d2956987215.mow.presenter.MinePresenter;
import com.d2956987215.mow.rxjava.Request;
import com.d2956987215.mow.rxjava.Result;
import com.d2956987215.mow.rxjava.RxJavaUtil;
import com.d2956987215.mow.rxjava.response.BaseResponse;
import com.d2956987215.mow.util.AnimatorEffect;
import com.d2956987215.mow.util.SP;
import com.d2956987215.mow.util.ToastUtil;
import com.d2956987215.mow.util.User;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

import static com.d2956987215.mow.R.id.et_phone;

public class ModifyPhoneActivity extends BaseActivity implements TextWatcher {

    @BindView(et_phone)
    EditText etPhone;
    @BindView(R.id.et_auth_code)
    EditText etAuthCode;
    @BindView(R.id.bt_next)
    Button bt_submit;
    @BindView(R.id.tv_auth_code)
    TextView tv_yanzhengma;


    private MinePresenter presenter;
    private TimeCount count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new MinePresenter(this);
        etPhone.addTextChangedListener(this);
        etAuthCode.addTextChangedListener(this);
        String phone = SP.getString("phone", "");
        etPhone.setText(phone);
        KeyboardUtils.showSoftInput(etPhone);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_modify_phone;
    }

    @Override
    public void show(Object obj) {

    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (!TextUtils.isEmpty(etPhone.getText()) && !TextUtils.isEmpty(etAuthCode.getText())

                ) {
            bt_submit.setBackground(getResources().getDrawable(R.drawable.bg_loginred));
            bt_submit.setClickable(true);
        } else {
            bt_submit.setBackground(getResources().getDrawable(R.drawable.bg_login));
            bt_submit.setClickable(false);
        }
    }

    @Override
    protected String title() {
        return "修改手机号";
    }

    @OnClick({R.id.tv_auth_code, R.id.bt_next})
    public void onViewClicked(View view) {
        AnimatorEffect.setClickEffect(getApplicationContext(), view);
        switch (view.getId()) {
            case R.id.tv_auth_code:
                presenter.changeAuthCode(etPhone.getText().toString());
                count = new TimeCount(60000, 1000);
                count.start();

                break;
            case R.id.bt_next:
                verifyPhone(etPhone.getText().toString(), etAuthCode.getText().toString());


                break;
        }
    }

    public void verifyPhone(String phone, String code) {
        Map<String, String> map = new HashMap<>();
        map.put("mobile", phone);
        map.put("code", code);
        map.put("user_id", User.uid() + "");
        new Request<>().request(RxJavaUtil.xApi().verifyPhone(map, "Bearer " + User.token()), "验证手机号", ModifyPhoneActivity.this, false, new Result<BaseResponse>() {
            @Override
            public void get(BaseResponse response) {
                startActivity(new Intent(ModifyPhoneActivity.this, BindPhoneActivity.class));
                finish();
            }
        });
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
