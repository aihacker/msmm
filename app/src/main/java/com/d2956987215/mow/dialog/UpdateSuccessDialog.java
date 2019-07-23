package com.d2956987215.mow.dialog;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.d2956987215.mow.R;


/**
 * Created by Administrator on 2016/5/19.
 */
public class UpdateSuccessDialog extends BaseDialog implements View.OnClickListener {
    private Activity activity;
    private LinearLayout dialog_alarmAlert;

    CallBack callback;

    public UpdateSuccessDialog(Context context, CallBack callBack) {
        super(context);
        this.callback = callBack;
        activity = (Activity) context;
        init();

    }

    private void init() {
        setContentView(R.layout.ml_dialog_update_success);
        dialog_alarmAlert = findViewById(R.id.dialog_alarmAlert);
        dialog_alarmAlert.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        dismiss();
        switch (v.getId()) {
            case R.id.dialog_alarmAlert:
                callback.NO();


        }
    }

    public interface CallBack {
        public void NO();
    }

}
