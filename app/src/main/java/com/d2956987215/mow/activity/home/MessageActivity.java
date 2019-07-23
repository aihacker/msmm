package com.d2956987215.mow.activity.home;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.d2956987215.mow.R;
import com.d2956987215.mow.activity.BaseActivity;
import com.d2956987215.mow.activity.mine.MessageSetActivity;
import com.d2956987215.mow.eventbus.UnReadNum;
import com.d2956987215.mow.rxjava.Request;
import com.d2956987215.mow.rxjava.Result;
import com.d2956987215.mow.rxjava.RxJavaUtil;
import com.d2956987215.mow.rxjava.response.MessageTypeResponse;
import com.d2956987215.mow.util.User;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class MessageActivity extends BaseActivity {
    @BindView(R.id.tv_submit)
    TextView mTvSubmit;
    @BindView(R.id.rl_back)
    RelativeLayout mRlBack;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_gouwuche)
    ImageView mTvGouwuche;
    @BindView(R.id.tv_money)
    TextView mTvMoney;
    @BindView(R.id.ll_money)
    LinearLayout mLlMoney;
    @BindView(R.id.tv_system)
    TextView mTvSystem;
    @BindView(R.id.ll_system)
    LinearLayout mLlSystem;
    @BindView(R.id.tv_activity)
    TextView mTvActivity;
    @BindView(R.id.ll_activity)
    LinearLayout mLlActivity;
    @BindView(R.id.tv_everyday)
    TextView mTvEveryday;
    @BindView(R.id.ll_everyday)
    LinearLayout mLlEveryday;
    @BindView(R.id.iv_money_dot)
    ImageView mIvMoneyDot;
    @BindView(R.id.iv_system_dot)
    ImageView mIvSystemDot;
    @BindView(R.id.iv_activity_dot)
    ImageView mIvActivityDot;
    @BindView(R.id.iv_everyday_dot)
    ImageView mIvEverydayDot;
    private MessageTypeResponse mMessageTypeResponse;

    @Override
    protected int getLayoutId() {
        return R.layout.acticity_message;
    }

    @Override
    public void show(Object obj) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTvSubmit.setVisibility(View.VISIBLE);
        mTvSubmit.setText("提醒设置");
        mTvSubmit.setTextColor(getResources().getColor(R.color.six));

        mTvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MessageActivity.this, MessageSetActivity.class));
            }
        });
        getData();
    }


    @Override
    protected String title() {
        return "消息";
    }


    @OnClick({R.id.ll_money, R.id.ll_system, R.id.ll_activity, R.id.ll_everyday})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_money:
                if (mMessageTypeResponse != null) {
                    if (mMessageTypeResponse.getData() != null && mMessageTypeResponse.getData().size() > 0) {
                        mMessageTypeResponse.getData().get(0).setUnreadStatus(0);
                        mTvMoney.setText("暂无消息");
                        mIvMoneyDot.setVisibility(View.GONE);
                        Intent intent = new Intent(this, Message_MoneyActivity.class);
                        intent.putExtra("typeid", mMessageTypeResponse.getData().get(0).getTypeid());
                        startActivity(intent);
                    }
                }
                break;
            case R.id.ll_system:
                if (mMessageTypeResponse != null) {
                    if (mMessageTypeResponse.getData() != null && mMessageTypeResponse.getData().size() > 0) {
                        mMessageTypeResponse.getData().get(1).setUnreadStatus(0);
                        mTvSystem.setText("暂无消息");
                        mIvSystemDot.setVisibility(View.GONE);
                        Intent intent = new Intent(this, Message_SystemActivity.class);
                        intent.putExtra("typeid", mMessageTypeResponse.getData().get(1).getTypeid());
                        startActivity(intent);
                    }
                }
                break;
            case R.id.ll_activity:
                if (mMessageTypeResponse != null) {
                    if (mMessageTypeResponse.getData() != null && mMessageTypeResponse.getData().size() > 0) {
                        mMessageTypeResponse.getData().get(2).setUnreadStatus(0);
                        mTvActivity.setText("暂无消息");
                        mIvActivityDot.setVisibility(View.GONE);
                        Intent intent = new Intent(this, Message_NoticeActivity.class);
                        intent.putExtra("typeid", mMessageTypeResponse.getData().get(2).getTypeid());
                        startActivity(intent);
                    }
                }
                break;
            case R.id.ll_everyday:
                if (mMessageTypeResponse != null) {
                    if (mMessageTypeResponse.getData() != null && mMessageTypeResponse.getData().size() > 0) {
                        mMessageTypeResponse.getData().get(3).setUnreadStatus(0);
                        mTvEveryday.setText("暂无消息");
                        mIvEverydayDot.setVisibility(View.GONE);
                        Intent intent = new Intent(this, Message_EveryDayActivity.class);
                        intent.putExtra("typeid", mMessageTypeResponse.getData().get(3).getTypeid());
                        startActivity(intent);
                    }
                }
                break;
        }
    }

    private void getData() {
        Map<String, String> map = new HashMap<>();
        if (User.uid() > 0) {
            map.put("user_id", User.uid() + "");
        }
        new Request<MessageTypeResponse>().request(RxJavaUtil.xApi1().megType(map), "消息类型", this, false, new Result<MessageTypeResponse>() {

            @Override
            public void get(final MessageTypeResponse response) {
                if (response != null) {
                    mMessageTypeResponse = response;
                    if (response.getData() != null && response.getData().size() > 0) {
                        //奖金
                        if (!TextUtils.isEmpty(response.getData().get(0).getMessageX())) {
                            mTvMoney.setText(response.getData().get(0).getMessageX());
                        } else {
                            mTvMoney.setText("暂无消息");
                        }
                        if (response.getData().get(0).getUnreadStatus() == 0) {
                            mIvMoneyDot.setVisibility(View.VISIBLE);
                        } else {
                            mIvMoneyDot.setVisibility(View.GONE);
                        }
                        //系统
                        if (!TextUtils.isEmpty(response.getData().get(1).getMessageX())) {
                            mTvSystem.setText(response.getData().get(1).getMessageX());
                        } else {
                            mTvSystem.setText("暂无消息");
                        }
                        if (response.getData().get(1).getUnreadStatus() == 0) {
                            mIvSystemDot.setVisibility(View.VISIBLE);
                        } else {
                            mIvSystemDot.setVisibility(View.GONE);
                        }
                        //活动
                        if (!TextUtils.isEmpty(response.getData().get(2).getMessageX())) {
                            mTvActivity.setText(response.getData().get(2).getMessageX());
                        } else {
                            mTvActivity.setText("暂无消息");
                        }
                        if (response.getData().get(2).getUnreadStatus() == 0) {
                            mIvActivityDot.setVisibility(View.VISIBLE);
                        } else {
                            mIvActivityDot.setVisibility(View.GONE);
                        }
                        //每日
                        if (!TextUtils.isEmpty(response.getData().get(3).getMessageX())) {
                            mTvEveryday.setText(response.getData().get(3).getMessageX());
                        } else {
                            mTvEveryday.setText("暂无消息");
                        }
                        if (response.getData().get(3).getUnreadStatus() == 0) {
                            mIvEverydayDot.setVisibility(View.VISIBLE);
                        } else {
                            mIvEverydayDot.setVisibility(View.GONE);
                        }
                    }
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().post(new UnReadNum());
        super.onDestroy();
    }
}
