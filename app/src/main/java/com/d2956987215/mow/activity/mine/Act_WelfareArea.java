package com.d2956987215.mow.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.d2956987215.mow.R;
import com.d2956987215.mow.activity.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Act_WelfareArea extends BaseActivity {
    @BindView(R.id.tv_submit)
    TextView mTvSubmit;
    @BindView(R.id.tv_money)
    TextView mTvMoney;
    @BindView(R.id.tv_shareMoney)
    TextView mTvShareMoney;
    @BindView(R.id.tv_myRequest)
    TextView mTvMyRequest;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @Override
    protected int getLayoutId() {
        return R.layout.act_welfare_area;
    }

    @Override
    public void show(Object obj) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @OnClick({R.id.rl_back, R.id.tv_submit, R.id.tv_shareMoney, R.id.tv_myRequest})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.tv_submit:
                startActivity(new Intent(Act_WelfareArea.this, Act_AwardRecord.class));
                break;
            case R.id.tv_shareMoney:
                break;
            case R.id.tv_myRequest:
                startActivity(new Intent(Act_WelfareArea.this, Act_MyRequest.class));
                break;
        }
    }
}
