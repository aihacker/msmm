package com.d2956987215.mow.dialog;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.d2956987215.mow.R;
import com.d2956987215.mow.activity.login.LoginActivity;


/**
 * Created by Administrator on 2016/5/19.
 */
public class UpdateIDDialog extends BaseDialog implements View.OnClickListener {
    private Activity activity;
    private TextView tv_quxiao, tv_queding;
    CallBack callback;

    public UpdateIDDialog(Context context, CallBack callBack) {
        super(context);
        this.callback = callBack;
        activity = (Activity) context;
        init();

    }

    private void init() {
        setContentView(R.layout.ml_dialog_id_update);
        tv_quxiao = findViewById(R.id.tv_quxiao);
        tv_queding = findViewById(R.id.tv_queding);
        tv_queding.setOnClickListener(this);
        tv_quxiao.setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {
        dismiss();
        switch (v.getId()) {

            case R.id.quxiao:
                break;
            case R.id.tv_queding:
                callback.NO();


        }
    }

    public interface CallBack {
        public void NO();
    }

}
