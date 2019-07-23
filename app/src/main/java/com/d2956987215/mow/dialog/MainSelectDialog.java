package com.d2956987215.mow.dialog;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.d2956987215.mow.R;
import com.d2956987215.mow.activity.APP;
import com.d2956987215.mow.activity.mine.SearchActivity;
import com.d2956987215.mow.activity.mine.SearchSingleActivity;
import com.d2956987215.mow.constant.Const;
import com.example.qimiao.zz.uitls.ui.Density;


public class MainSelectDialog extends BaseDialog implements View.OnClickListener {
    private Activity activity;

    private ImageView tv_quxiao;

    private LinearLayout ll_tb, ll_jd, ll_pdd;
    private String mMessage;
    private TextView tv_ms;

    CallBack callback;

    public MainSelectDialog(Context context, String messasge, CallBack callBack) {

        super(context);
        activity = (Activity) context;
        Density.setDensity(APP.application, activity);
        this.callback = callBack;
        this.mMessage = messasge;
        init();

    }

    private void init() {
        setContentView(R.layout.ml_dialog_main_select);
        tv_quxiao = findViewById(R.id.iv_qx);
        ll_tb = findViewById(R.id.ll_tb);
        ll_jd = findViewById(R.id.ll_jd);
        ll_pdd = findViewById(R.id.ll_pdd);
        tv_ms = findViewById(R.id.tv_ms);
        tv_quxiao.setOnClickListener(this);
        ll_jd.setOnClickListener(this);
        ll_pdd.setOnClickListener(this);
        ll_tb.setOnClickListener(this);
        tv_ms.setText(mMessage + "");
    }


    @Override
    public void onClick(View v) {
        dismiss();
        Intent intent = new Intent(activity, SearchSingleActivity.class);
        switch (v.getId()) {
            case R.id.quxiao:

                break;
            case R.id.ll_tb:
                callback.NO(1);
                Const.tjptype="1";
                intent.putExtra("type", "1");
                intent.putExtra("keyword", mMessage);
                activity.startActivity(intent);
                break;
            case R.id.ll_jd:
                callback.NO(2);
                Const.tjptype="2";
                intent.putExtra("type", "2");
                intent.putExtra("keyword", mMessage);
                activity.startActivity(intent);
                break;
            case R.id.ll_pdd:
                callback.NO(3);
                Const.tjptype="3";
                intent.putExtra("type", "3");
                intent.putExtra("keyword", mMessage);
                activity.startActivity(intent);
                break;


        }
    }

    public interface CallBack {
        void NO(int id);
    }

}
