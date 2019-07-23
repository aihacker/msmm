package com.d2956987215.mow.dialog;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.d2956987215.mow.R;


/**
 * Created by Administrator on 2016/5/19.
 */
public class UpdateDialog extends BaseDialog implements View.OnClickListener {
    private Activity activity;
    private TextView tv_quxiao, tv_queding, tv_message;

    CallBack callback;

    public UpdateDialog(Context context, CallBack callBack) {
        super(context);
        this.callback = callBack;
        activity = (Activity) context;
        init();

    }

    private void init() {
        setContentView(R.layout.ml_dialog_pay);
        tv_quxiao = findViewById(R.id.tv_quxiao);
        tv_queding = findViewById(R.id.tv_queding);
        tv_message = findViewById(R.id.tv_message);
        tv_quxiao.setOnClickListener(this);
        tv_queding.setOnClickListener(this);


    }

    public void setMessage(String message){
        tv_message.setText(message);
    }

    public void setRightText(String rightText){
        tv_queding.setText(rightText);
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
