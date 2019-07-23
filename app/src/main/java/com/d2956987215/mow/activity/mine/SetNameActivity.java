package com.d2956987215.mow.activity.mine;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.d2956987215.mow.R;
import com.d2956987215.mow.activity.BaseActivity;
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

public class SetNameActivity extends BaseActivity implements TextWatcher {

    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.bt_submit)
    Button bt_submit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        etContent.addTextChangedListener(this);
        etContent.setText(getIntent().getStringExtra("name"));
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (!TextUtils.isEmpty(etContent.getText().toString().trim())) {
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
    protected int getLayoutId() {
        return R.layout.activity_setname;
    }

    @Override
    protected String title() {
        return "昵称";
    }

    @Override
    public void show(Object obj) {

    }

    @OnClick({R.id.bt_submit})
    public void onViewClicked(View view) {
        AnimatorEffect.setClickEffect(getApplicationContext(), view);
        switch (view.getId()) {
            case R.id.bt_submit:
                Map<String, String> map = new HashMap<>();
                map.put("nick_name", etContent.getText().toString());
                map.put("user_id", User.uid() + "");
                new Request<>().request(RxJavaUtil.xApi().changepeson(map, "Bearer " + User.token()), "修改昵称", this, false, new Result<BaseResponse>() {
                    @Override
                    public void get(BaseResponse response) {
                        ToastUtil.show(SetNameActivity.this, response.message);
                        SP.putString("name",etContent.getText().toString());
                        finish();
                    }
                });
                break;
        }
    }


}
