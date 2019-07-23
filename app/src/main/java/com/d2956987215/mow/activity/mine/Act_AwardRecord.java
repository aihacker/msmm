package com.d2956987215.mow.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.d2956987215.mow.R;
import com.d2956987215.mow.activity.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class Act_AwardRecord extends BaseActivity {
    @BindView(R.id.tv_submit)
    TextView mTvSubmit;

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
        mTvSubmit.setVisibility(View.VISIBLE);
        mTvSubmit.setTextColor(getResources().getColor(R.color.text_deep));
        mTvSubmit.setText("红包明细");
    }

    @Override
    protected String title() {
        return "兑换记录";
    }

    @OnClick(R.id.tv_submit)
    public void onViewClicked() {
        startActivity(new Intent(Act_AwardRecord.this, Act_RedPacketsDetail.class));
    }
}
