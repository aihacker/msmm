package com.d2956987215.mow.dialog;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.d2956987215.mow.R;


/**
 * Created by Administrator on 2016/5/19.
 */
public class ErWeiMaDialog extends BaseDialog implements View.OnClickListener {
    private TextView tv_quxiao, tv_queding;
    private ImageView iv_erweima;
    CallBack callback;
    private String erweima;
    private Context context;


    public ErWeiMaDialog(Context context, String erweima,CallBack callBack) {
        super(context);
        this.callback = callBack;
        this.context=context;
        this.erweima=erweima;
        init();

    }

    private void init() {
        setContentView(R.layout.ml_dialog_erweima);
        tv_quxiao = findViewById(R.id.tv_quxiao);
        tv_queding = findViewById(R.id.tv_queding);
        iv_erweima = findViewById(R.id.iv_erweima);
        Glide.with(context).load(erweima).error(R.mipmap.have_no_head).into(iv_erweima);
        tv_quxiao.setOnClickListener(this);
        tv_queding.setOnClickListener(this);


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
