package com.d2956987215.mow.dialog;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.d2956987215.mow.R;
import com.d2956987215.mow.activity.APP;
import com.example.qimiao.zz.uitls.ui.Density;


public class MainSelectDialog extends BaseDialog implements View.OnClickListener {
    private Activity activity;

    private ImageView tv_quxiao;

    private LinearLayout ll_tb, ll_jd, ll_pdd;

    CallBack callback;

    public MainSelectDialog(Context context, CallBack callBack) {

        super(context);
        activity = (Activity) context;
        Density.setDensity(APP.application,activity);
        this.callback = callBack;
        init();

    }

    private void init() {
        setContentView(R.layout.ml_dialog_main_select);
        tv_quxiao = findViewById(R.id.iv_qx);
        ll_tb = findViewById(R.id.ll_tb);
        ll_jd = findViewById(R.id.ll_jd);
        ll_pdd = findViewById(R.id.ll_pdd);
        tv_quxiao.setOnClickListener(this);
        ll_jd.setOnClickListener(this);
        ll_pdd.setOnClickListener(this);
        ll_tb.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        dismiss();
        switch (v.getId()) {
            case R.id.quxiao:

                break;
            case R.id.ll_tb:
                callback.NO(1);
                break;
            case R.id.ll_jd:
                callback.NO(2);
                break;
            case R.id.ll_pdd:
                callback.NO(3);
                break;
        }
    }

    public interface CallBack {
        void NO(int id);
    }

}
