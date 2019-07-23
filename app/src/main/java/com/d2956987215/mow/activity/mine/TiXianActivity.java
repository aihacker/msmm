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

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.d2956987215.mow.R;
import com.d2956987215.mow.activity.BaseActivity;
import com.d2956987215.mow.rxjava.Request;
import com.d2956987215.mow.rxjava.Result;
import com.d2956987215.mow.rxjava.RxJavaUtil;
import com.d2956987215.mow.rxjava.response.BaseResponse;
import com.d2956987215.mow.util.SP;
import com.d2956987215.mow.util.ToastUtil;
import com.d2956987215.mow.util.User;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;


public class TiXianActivity extends BaseActivity implements TextWatcher {
    @BindView(R.id.tv_bangding)
    TextView tv_bangding;
    @BindView(R.id.tv_yue)
    TextView tv_yue;
    private String money;
    @BindView(R.id.et_input_money)
    EditText et_input_money;
    @BindView(R.id.bt_next)
    Button bt_next;
    @BindView(R.id.tv_name)
    TextView tv_name;

    @BindView(R.id.tv_phone)
    TextView tv_phone;

    @BindView(R.id.tv_submit)
    TextView mTvSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        money = getIntent().getStringExtra("money");
        tv_yue.setText("可用余额：￥" + money);
        et_input_money.addTextChangedListener(this);
        if (getIntent().getStringExtra("alipayusername") != null && getIntent().getStringExtra("alipayusername").length() > 0) {
            tv_name.setText(getIntent().getStringExtra("name"));
            tv_bangding.setText("修改");
            tv_phone.setText(getIntent().getStringExtra("alipayusername"));
        } else {
            tv_phone.setText("请先绑定支付宝");
            tv_bangding.setText("绑定支付宝");
        }

        mTvSubmit.setTextColor(getResources().getColor(R.color.black));
        mTvSubmit.setText("记录");
        mTvSubmit.setVisibility(View.VISIBLE);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (!TextUtils.isEmpty(et_input_money.getText().toString().trim())

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
        return R.layout.activity_upto;
    }

    @Override
    public void show(Object obj) {

    }

    @OnClick({R.id.tv_bangding, R.id.bt_next, R.id.tv_all, R.id.tv_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_bangding:
                startActivityForResult(new Intent(this, TiXianSetActivity.class), 10);

                break;
            case R.id.bt_next:
                if (tv_phone.getText().equals("请先绑定支付宝")) {
                    ToastUtil.show(this, "请先绑定支付宝");
                    return;
                }
                if (StringUtils.isEmpty(et_input_money.getText().toString())) {
                    ToastUtils.showShort("请输入提现金额");
                    return;
                }
                if (Float.parseFloat(et_input_money.getText().toString()) < 1) {
                    ToastUtil.show(this, "最低提现金额1元");
                    return;
                }


                Map<String, String> map = new HashMap<>();
                map.put("user_id", User.uid() + "");
                if (getIntent().getStringExtra("alipayusername") != null) {
                    map.put("alipayusername", getIntent().getStringExtra("alipayusername"));
                }
                if (getIntent().getStringExtra("name") != null) {
                    map.put("alipayname", getIntent().getStringExtra("name"));
                }
                map.put("money", et_input_money.getText().toString());
//                map.put("mobile", SP.getString("phone", ""));
                new Request<>().request(RxJavaUtil.xApi().tixian(map, "Bearer " + User.token()), "提现绑定支付宝账号", this, false, new Result<BaseResponse>() {
                    @Override
                    public void get(BaseResponse response) {
                        ToastUtil.show(TiXianActivity.this, response.message);
                        finish();
                    }
                });

                break;
            case R.id.tv_all:
                et_input_money.setText(money);
                break;
            case R.id.tv_submit:
                ActivityUtils.startActivity(WithdrawInfoActivity.class);
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        tv_name.setText(SP.getString("alipayname", ""));
        tv_phone.setText(SP.getString("alipayusername", ""));
    }


    @Override
    protected String title() {
        return "提现";
    }
}
