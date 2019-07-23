package com.d2956987215.mow.dialog;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.d2956987215.mow.R;
import com.d2956987215.mow.activity.login.LoginActivity;
import com.d2956987215.mow.activity.login.RegisterActivity;


/**
 * Created by Administrator on 2016/5/19.
 */
public class SignDialog extends BaseDialog implements View.OnClickListener {
    private Activity activity;
    private TextView tv_quxiao, tv_queding;


    public SignDialog(Context context) {
        super(context);
        activity = (Activity) context;
        init();

    }

    private void init() {
        setContentView(R.layout.ml_dialog_zhuce);
        tv_quxiao = findViewById(R.id.tv_quxiao);
        tv_queding = findViewById(R.id.tv_queding);
        tv_quxiao.setOnClickListener(this);
        tv_queding.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        dismiss();
        switch (v.getId()) {

            case R.id.quxiao:
                dismiss();

                break;
            case R.id.tv_queding:
                dismiss();
                Intent intent = new Intent(activity, LoginActivity.class);
                activity.startActivity(intent);

        }
    }


}
