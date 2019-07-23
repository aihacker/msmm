package com.d2956987215.mow.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.d2956987215.mow.R;


/**
 * Created by Administrator on 2016/5/25.
 */
public class ButtomDialogView extends Dialog {
    CallBack callback;
    private View view;
    private Activity context;
    private TextView tv_weixin, tv_pengyouquan, tv_weibo, tv_qq, tv_kongjian, tv_fuzhi;
    private String erweima;

    //这里的view其实可以替换直接传layout过来的 因为各种原因没传(lan)
    public ButtomDialogView(Activity context, CallBack callback) {
        super(context, R.style.MyDialognew);

        this.context = context;
        this.callback = callback;


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.ml_dialog_fenxiang);//这行一定要写在前面
        tv_weixin = findViewById(R.id.tv_weixin);
        tv_pengyouquan = findViewById(R.id.tv_pengyouquan);
        tv_weibo = findViewById(R.id.tv_weibo);
        tv_qq = findViewById(R.id.tv_qq);
        tv_kongjian = findViewById(R.id.tv_kongjian);
        tv_fuzhi = findViewById(R.id.tv_fuzhi);

        setCancelable(false);
        //点击外部不可dismiss
        setCanceledOnTouchOutside(true);
        Window window = this.getWindow();
        window.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(params);
        tv_weixin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                callback.weixinhaoyou();
            }

        });
        tv_pengyouquan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                callback.pengyouquan();
            }

        });
        tv_weibo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                callback.xinlangweibo();
            }

        });
        tv_qq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                callback.qqhaoyou();
            }

        });

        tv_kongjian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                callback.qqkongjian();
            }

        });
        tv_fuzhi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                callback.fuzhi();
            }
        });

    }

    public interface CallBack {
        public void weixinhaoyou();

        public void pengyouquan();

        public void qqhaoyou();

        public void qqkongjian();

        public void xinlangweibo();
        public void fuzhi();

        public void NO();
    }

}