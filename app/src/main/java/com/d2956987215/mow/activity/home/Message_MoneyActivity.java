package com.d2956987215.mow.activity.home;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.d2956987215.mow.R;
import com.d2956987215.mow.activity.BaseActivity;
import com.d2956987215.mow.adapter.MsgMoneyListAdapter;
import com.d2956987215.mow.eventbus.StopRefreshLoadMoreAnim;
import com.d2956987215.mow.rxjava.Request;
import com.d2956987215.mow.rxjava.Result;
import com.d2956987215.mow.rxjava.RxJavaUtil;
import com.d2956987215.mow.rxjava.response.MsgMoneyListResponse;
import com.d2956987215.mow.util.InitRefreshLayout;
import com.d2956987215.mow.util.User;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

public class Message_MoneyActivity extends BaseActivity {
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.rl_back)
    RelativeLayout mRlBack;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_submit)
    TextView mTvSubmit;
    @BindView(R.id.tv_gouwuche)
    ImageView mTvGouwuche;
    @BindView(R.id.ll_nothing)
    LinearLayout mLlNothing;

    private int page = 1, typeid;
    private MsgMoneyListAdapter msgListAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_msglist;
    }

    @Override
    public void show(Object obj) {

    }

    @Override
    protected String title() {
        return "奖金消息";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        typeid = getIntent().getIntExtra("typeid", 0);
        getData();

        InitRefreshLayout.initRefreshLayout(mRefreshLayout, Message_MoneyActivity.this, true);
        mRefreshLayout.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshLayout) {
                page++;
                getData();
            }

            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                page = 1;
                getData();
            }
        });

    }


    private void getData() {
        Map<String, String> map = new HashMap<>();
        if (User.uid() > 0) {
            map.put("user_id", User.uid() + "");
        }
        map.put("typeid", typeid + "");
        map.put("page", page + "");
        map.put("page_size", "15");
        new Request<MsgMoneyListResponse>().request(RxJavaUtil.xApi1().megMoneyList(map), "奖金消息列表", this, false, new Result<MsgMoneyListResponse>() {

            @Override
            public void get(final MsgMoneyListResponse response) {
                if (page == 1) {
                    mRefreshLayout.finishRefresh();
                } else {
                    mRefreshLayout.finishLoadmore();
                }
                if (response != null) {
                    if (response.getData() != null && response.getData().size() > 0) {
                        if (page == 1) {
                            mRecyclerView.setLayoutManager(new LinearLayoutManager(Message_MoneyActivity.this));
                            msgListAdapter = new MsgMoneyListAdapter(R.layout.item_msg_money, response.getData());
                            mRecyclerView.setAdapter(msgListAdapter);
                        } else {
                            msgListAdapter.addData(response.getData());
                        }


                    } else {
                        if (page == 1) {
                            mRefreshLayout.setVisibility(View.GONE);
                            mLlNothing.setVisibility(View.VISIBLE);
                        }
                    }


                } else {
                    if (page == 1) {
                        mRefreshLayout.setVisibility(View.GONE);
                        mLlNothing.setVisibility(View.VISIBLE);
                    }
                }

            }

            @Override
            public void onServerError(String errDesc) {
                super.onServerError(errDesc);
                EventBus.getDefault().post(new StopRefreshLoadMoreAnim());
            }

            @Override
            public void onBackErrorMessage(MsgMoneyListResponse response) {
                super.onBackErrorMessage(response);
                EventBus.getDefault().post(new StopRefreshLoadMoreAnim());
            }
        });
    }

}
