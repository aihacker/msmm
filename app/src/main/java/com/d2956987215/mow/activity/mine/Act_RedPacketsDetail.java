package com.d2956987215.mow.activity.mine;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.d2956987215.mow.R;
import com.d2956987215.mow.activity.BaseActivity;

import butterknife.BindView;

public class Act_RedPacketsDetail extends BaseActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.act_awardrecord;
    }

    @Override
    public void show(Object obj) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected String title() {
        return "红包明细";
    }
}
