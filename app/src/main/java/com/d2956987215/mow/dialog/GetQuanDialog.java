package com.d2956987215.mow.dialog;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.d2956987215.mow.R;


/**
 * Created by Administrator on 2016/5/19.
 */
public class GetQuanDialog extends BaseDialog implements View.OnClickListener {
    private final String mMessage;
    private final String mRightBtn;
    private Activity activity;
    private TextView tv_quxiao, tv_queding, tv_message;

    CallBack callback;

    public GetQuanDialog(Context context, String message, String rightBtn, CallBack callBack) {
        super(context);
        this.callback = callBack;
        activity = (Activity) context;
        mMessage = message;
        mRightBtn = rightBtn;
        init();

    }

    private void init() {
        setContentView(R.layout.ml_dialog_get_quan);
        tv_quxiao = findViewById(R.id.tv_quxiao);
        tv_message = findViewById(R.id.tv_message);
        tv_queding = findViewById(R.id.tv_queding);
        tv_queding.setOnClickListener(this);
        tv_quxiao.setOnClickListener(this);

        tv_message.setText(mMessage);
        tv_queding.setText(mRightBtn);


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
