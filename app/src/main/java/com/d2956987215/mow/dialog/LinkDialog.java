package com.d2956987215.mow.dialog;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.d2956987215.mow.R;
import com.d2956987215.mow.activity.APP;
import com.example.qimiao.zz.uitls.ui.Density;


/**
 * Created by Administrator on 2016/5/19.
 */
public class LinkDialog extends BaseDialog implements View.OnClickListener {

    private Activity activity;
    private TextView tv_quxiao, tv_queding, tv_hf;
    private String mMessage;
    private ImageView iv_qx;

    CallBack callback;

    public LinkDialog(Context context, String message,CallBack callBack) {
        super(context);
        this.callback = callBack;
        this.mMessage=message;
        activity = (Activity) context;

        init();

    }

    private void init() {
        Density.setDensity(APP.getApplication(),activity);
        setContentView(R.layout.ml_dialog_link);
        tv_quxiao = findViewById(R.id.tv_quxiao);
        tv_hf = findViewById(R.id.tv_hf);
        iv_qx=findViewById(R.id.iv_qx);
        tv_queding = findViewById(R.id.tv_queding);
        tv_queding.setOnClickListener(this);
        tv_quxiao.setOnClickListener(this);
        iv_qx.setOnClickListener(this);
        tv_hf.setText(mMessage);
//        tv_queding.setText(mRightBtn);


    }


    @Override
    public void onClick(View v) {
        dismiss();
        switch (v.getId()) {

            case R.id.quxiao:

                break;
            case R.id.iv_qx:

                break;
            case R.id.tv_queding:
                callback.NO();


        }
    }

    public interface CallBack {
        public void NO();
    }

}
