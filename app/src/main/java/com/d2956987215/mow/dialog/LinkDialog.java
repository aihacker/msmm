package com.d2956987215.mow.dialog;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.d2956987215.mow.R;
import com.d2956987215.mow.activity.APP;
import com.d2956987215.mow.activity.home.LInkActivity;
import com.d2956987215.mow.bean.LinkBean;
import com.d2956987215.mow.rxjava.Request;
import com.d2956987215.mow.rxjava.Result;
import com.d2956987215.mow.rxjava.RxJavaUtil;
import com.d2956987215.mow.util.ActivityUtils;
import com.d2956987215.mow.util.User;
import com.example.qimiao.zz.uitls.ui.Density;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by Administrator on 2016/5/19.
 */
public class LinkDialog extends BaseDialog implements View.OnClickListener {

    private Activity activity;
    private TextView tv_quxiao, tv_queding, tv_hf;
    private String mMessage;
    private ImageView iv_qx;
    private LinkBean response;

    CallBack callback;

    public LinkDialog(Context context, LinkBean response,String message,CallBack callBack) {
        super(context);
        this.callback = callBack;
        this.response=response;
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
        //获取剪贴板管理器：
        ClipboardManager cm = (ClipboardManager) activity.getSystemService(Context.CLIPBOARD_SERVICE);
        // 创建普通字符型ClipData
        ClipData mClipData = ClipData.newPlainText("Label", "");
        // 将ClipData内容放到系统剪贴板里。
        cm.setPrimaryClip(mClipData);
        dismiss();
        switch (v.getId()) {

            case R.id.quxiao:

                break;
            case R.id.iv_qx:

                break;
            case R.id.tv_queding:
                if (User.uid() < 0) {
                    ActivityUtils.startLoginAcitivy(activity);
                    return;
                }
                Map<String, String> map1 = new HashMap<>();
                map1.put("keyword", mMessage);
                map1.put("type", response.getData().getType() + "");
                map1.put("user_id", User.uid() + "");
                new Request<LinkBean>().request(RxJavaUtil.xApi().chainLink(map1), "", activity, true, new Result<LinkBean>() {
                    @Override
                    public void get(LinkBean response1) {
                        EventBus.getDefault().postSticky(response1);
                        activity.startActivity(new Intent(activity, LInkActivity.class));
                    }
                });
                callback.NO();


        }
    }

    public interface CallBack {
        public void NO();
    }

}
