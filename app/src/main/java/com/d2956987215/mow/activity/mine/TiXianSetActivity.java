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

import com.d2956987215.mow.R;
import com.d2956987215.mow.activity.BaseActivity;
import com.d2956987215.mow.presenter.MinePresenter;
import com.d2956987215.mow.rxjava.Request;
import com.d2956987215.mow.rxjava.Result;
import com.d2956987215.mow.rxjava.RxJavaUtil;
import com.d2956987215.mow.rxjava.response.BaseResponse;
import com.d2956987215.mow.util.AnimatorEffect;
import com.d2956987215.mow.util.SP;
import com.d2956987215.mow.util.ToastUtil;
import com.d2956987215.mow.util.User;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

import static com.d2956987215.mow.R.id.et_auth_code;

public class TiXianSetActivity extends BaseActivity implements TextWatcher {

    @BindView(R.id.et_name)
    EditText et_name;
    @BindView(R.id.et_account)
    EditText et_account;
    @BindView(et_auth_code)
    EditText etAuthCode;
    @BindView(R.id.tv_phone)
    TextView tv_phone;
    @BindView(R.id.bt_next)
    Button bt_submit;
    @BindView(R.id.tv_auth_code)
    TextView tv_auth_code;


    private TimeCount count;
    private MinePresenter presenter;
    private String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new MinePresenter(this);
        phone = SP.getString("phone", null);
        String phonetext = phone.substring(0, 3) + "****" + phone.substring(7, 11);
        tv_phone.setText(phonetext);
        et_name.addTextChangedListener(this);
        et_account.addTextChangedListener(this);
        etAuthCode.addTextChangedListener(this);

        String alipayname = SP.getString("alipayname", "");
        if (!TextUtils.isEmpty(alipayname)) {
            et_name.setText(alipayname);
        }

        String alipayusername = SP.getString("alipayusername", "");
        if (!TextUtils.isEmpty(alipayusername)) {
            et_account.setText(alipayusername);
        }

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_tixian_set;
    }

    @Override
    public void show(Object obj) {

    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (!TextUtils.isEmpty(et_name.getText().toString().trim()) && !TextUtils.isEmpty(et_account.getText().toString().trim())
                && !TextUtils.isEmpty(etAuthCode.getText().toString().trim())
                ) {
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
    protected String title() {
        return "提现账户设置";
    }

    @OnClick({R.id.tv_auth_code, R.id.bt_next})
    public void onViewClicked(View view) {
        AnimatorEffect.setClickEffect(getApplicationContext(), view);
        switch (view.getId()) {
            case R.id.tv_auth_code:
                presenter.changezhifubao(phone);
                count = new TimeCount(60000, 1000);
                count.start();
                break;
            case R.id.bt_next:
                Map<String, String> map = new HashMap<>();
                map.put("alipayname", et_name.getText().toString());
                map.put("code", etAuthCode.getText().toString());
                map.put("user_id", User.uid() + "");
                map.put("mobile", phone);
                map.put("alipayusername", et_account.getText().toString());
                new Request<>().request(RxJavaUtil.xApi().changetixian(map, "Bearer " + User.token()), "体现绑定支付宝账号", this, false, new Result<BaseResponse>() {
                    @Override
                    public void get(BaseResponse response) {
                        ToastUtil.show(TiXianSetActivity.this, response.message);
                        SP.putString("alipayname", et_name.getText().toString());
                        SP.putString("alipayusername", et_account.getText().toString());
                        setResult(10);
                        finish();
                    }
                });
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
            tv_auth_code.setClickable(false);
            tv_auth_code.setText(millisUntilFinished / 1000 + "秒后重新发送");
        }

        @Override
        public void onFinish() {
            //计时完成
            tv_auth_code.setText("重新获取");
            tv_auth_code.setClickable(true);
        }
    }
}


