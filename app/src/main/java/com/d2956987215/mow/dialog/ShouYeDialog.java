package com.d2956987215.mow.dialog;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.d2956987215.mow.R;
import com.d2956987215.mow.activity.mine.SearchActivity;
import com.d2956987215.mow.activity.mine.SearchSingleActivity;


/**
 * Created by Administrator on 2016/5/19.
 */
public class ShouYeDialog extends BaseDialog implements View.OnClickListener {
    private Activity activity;
    private TextView tv_quxiao, tv_queding, tv_name;
    private String content;


    public ShouYeDialog(Activity context, String content) {
        super(context);
        this.content = content;
        activity = context;
        init();

    }

    private void init() {
        setContentView(R.layout.ml_dialog_shouye);
        tv_quxiao = findViewById(R.id.tv_quxiao);
        tv_queding = findViewById(R.id.tv_queding);
        tv_name = findViewById(R.id.tv_name);
        tv_name.setText(content);
        tv_quxiao.setOnClickListener(this);
        tv_queding.setOnClickListener(this);


    }


    public void setMessage(String message){
        content = message;
        tv_name.setText(message);
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
                dismiss();
                break;
            case R.id.tv_queding:
//                if (ActivityUtils.isActivityExistsInStack(SearchSingleActivity.class))
                    ActivityUtils.finishActivity(SearchSingleActivity.class);
                dismiss();

                Intent intent = new Intent(activity, SearchSingleActivity.class);
                intent.putExtra("keyword", content);
                activity.startActivity(intent);
//                cm = (ClipboardManager) activity.getSystemService(Context.CLIPBOARD_SERVICE);
//                cm.setText("");

        }
    }


}
