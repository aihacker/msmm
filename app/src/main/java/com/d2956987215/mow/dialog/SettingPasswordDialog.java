package com.d2956987215.mow.dialog;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.d2956987215.mow.R;

public class SettingPasswordDialog extends BaseDialog implements View.OnClickListener {
    private Activity activity;
    private TextView tv_quxiao, tv_queding;

    CallBack callback;

    public SettingPasswordDialog(Context context, CallBack callBack) {
        super(context);
        this.callback = callBack;
        activity = (Activity) context;
        init();

    }

    private void init() {
        setContentView(R.layout.ml_dialog_setting_pwd);
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
                callback.qx();
                break;
            case R.id.tv_queding:
                callback.NO();


        }
    }

    public interface CallBack {
        void NO();
        void qx();
    }

}
