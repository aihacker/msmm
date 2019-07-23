package com.d2956987215.mow.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.d2956987215.mow.R;
import com.d2956987215.mow.activity.BaseActivity;
import com.d2956987215.mow.activity.home.OrdinaryWebViewActivity;
import com.d2956987215.mow.adapter.ConditionOneAdapter;
import com.d2956987215.mow.adapter.ConditionTwoAdapter;
import com.d2956987215.mow.adapter.MemberArticleAdapter;
import com.d2956987215.mow.adapter.MyAuthortyGvAdpter;
import com.d2956987215.mow.adapter.PendingAuthortyGvAdpter;
import com.d2956987215.mow.adapter.WXKeFuAdapter;
import com.d2956987215.mow.rxjava.Request;
import com.d2956987215.mow.rxjava.Result;
import com.d2956987215.mow.rxjava.RxJavaUtil;
import com.d2956987215.mow.rxjava.response.MemberUpgradeResponse;
import com.d2956987215.mow.util.User;
import com.d2956987215.mow.widgets.CircleImageView;
import com.d2956987215.mow.widgets.NoScrollGridView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class MemberCenterActivity extends BaseActivity {
    @BindView(R.id.rl_back)
    RelativeLayout mRlBack;
    @BindView(R.id.circle_head)
    CircleImageView mCircleHead;
    @BindView(R.id.tv_nickname)
    TextView mTvNickname;
    @BindView(R.id.gv_myAuthority)
    NoScrollGridView mGvMyAuthority;
    @BindView(R.id.gv_pendingAuthority)
    NoScrollGridView mGvPendingAuthority;
    @BindView(R.id.ll_authority)
    LinearLayout mLlAuthority;
    @BindView(R.id.tv_rolename)
    TextView mTvRolename;
    @BindView(R.id.tv_date)
    TextView mTvDate;
    @BindView(R.id.ivbg_grade)
    LinearLayout mIvbgGrade;
    @BindView(R.id.recyclerView_conditionOne)
    RecyclerView mRecyclerViewConditionOne;
    @BindView(R.id.recyclerView_conditionTwo)
    RecyclerView mRecyclerViewConditionTwo;
    @BindView(R.id.ll_conditionTwo)
    LinearLayout mLlConditionTwo;
    @BindView(R.id.recyclerView_wx)
    RecyclerView mRecyclerViewWx;
    @BindView(R.id.gv_article)
    NoScrollGridView mGvArticle;
    @BindView(R.id.rl_article)
    RelativeLayout mRlArticle;
    @BindView(R.id.tv_shuoming)
    TextView mTvShuoming;
    private String uproletype;
    private MemberUpgradeResponse.DataBean mDataBean;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_membercenter_ordinary;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        uproletype = getIntent().getStringExtra("uproletype");
        getData();
    }

    @Override
    public void show(Object obj) {

    }

    private void getData() {
        Map<String, String> map = new HashMap<>();
        if (User.uid() > 0) {
            map.put("user_id", User.uid() + "");
        }
        if (!TextUtils.isEmpty(uproletype)) {
            map.put("uproletype", uproletype + "");
        } else {
            map.put("uproletype", User.upreletype() + "");
        }
        new Request<MemberUpgradeResponse>().request(RxJavaUtil.xApi1().memberUpgrade(map), "会员升级", MemberCenterActivity.this, false, new Result<MemberUpgradeResponse>() {

            @Override
            public void get(final MemberUpgradeResponse response) {
                if (response.getData() != null) {
                    mDataBean = response.getData();
                    //用户信息
                    if (response.getData().getUserinfo() != null) {
                        mTvNickname.setText(response.getData().getUserinfo().getRealname());
                        mTvRolename.setText(response.getData().getUserinfo().getRolename());
                        mTvDate.setText("有效期至：" + response.getData().getUserinfo().getDeadline());
                        Glide.with(getContext()).load(response.getData().getUserinfo().getAvatar()).error(R.drawable.maishou).into(mCircleHead);
                        if (response.getData().getRolecolor() == 1) {
                            mIvbgGrade.setBackgroundResource(R.mipmap.mais_icon_ynms);
                            mLlAuthority.setBackgroundResource(R.mipmap.mais_bg_qx);
                        } else if (response.getData().getRolecolor() == 2) {
                            mIvbgGrade.setBackgroundResource(R.mipmap.vip_icon_ynms);
                            mLlAuthority.setBackgroundResource(R.mipmap.vip_bg_qx);
                        } else if (response.getData().getRolecolor() == 3) {
                            mIvbgGrade.setBackgroundResource(R.mipmap.bigvip_icon_yjms);
                            mLlAuthority.setBackgroundResource(R.mipmap.bigvip_bg);
                        }
                    }
                    //我的权限
                    if (response.getData().getIsopen() != null && response.getData().getIsopen().size() > 0) {
                        mGvMyAuthority.setAdapter(new MyAuthortyGvAdpter(MemberCenterActivity.this, response.getData().getIsopen()));
                    }
                    //待开通权限
                    if (response.getData().getNoopen() != null && response.getData().getNoopen().size() > 0) {
                        mGvPendingAuthority.setAdapter(new PendingAuthortyGvAdpter(MemberCenterActivity.this, response.getData().getNoopen()));
                    }
                    //升级条件一
                    if (response.getData().getConditionOne() != null && response.getData().getConditionOne().size() > 0) {
                        mRecyclerViewConditionOne.setLayoutManager(new LinearLayoutManager(MemberCenterActivity.this));
                        mRecyclerViewConditionOne.setAdapter(new ConditionOneAdapter(R.layout.item_conditionone, response.getData().getConditionOne(), response.getData().getUserinfo().getRoletype(), MemberCenterActivity.this));
                    }
                    //升级条件二
                    if (response.getData().getConditionTwo() != null && response.getData().getConditionTwo().size() > 0) {
                        mLlConditionTwo.setVisibility(View.VISIBLE);
                        mRecyclerViewConditionTwo.setLayoutManager(new LinearLayoutManager(MemberCenterActivity.this));
                        mRecyclerViewConditionTwo.setAdapter(new ConditionTwoAdapter(R.layout.item_conditionone, response.getData().getConditionTwo(), response.getData().getUserinfo().getRoletype(), MemberCenterActivity.this));
                    }
                    //微信客服
                    if (response.getData().getWx() != null && response.getData().getWx().size() > 0) {
                        mRecyclerViewWx.setLayoutManager(new LinearLayoutManager(MemberCenterActivity.this));
                        mRecyclerViewWx.setAdapter(new WXKeFuAdapter(R.layout.item_exclusive_monitor, response.getData().getWx(),MemberCenterActivity.this));
                    }
                    //专属客服
                    if (response.getData().getArticle() != null && response.getData().getArticle().size() > 0) {
                        mGvArticle.setAdapter(new MemberArticleAdapter(MemberCenterActivity.this, response.getData().getArticle()));
                    } else {
                        mRlArticle.setVisibility(View.GONE);
                    }
                }
            }
        });
    }

    @OnClick(R.id.tv_shuoming)
    public void onViewClicked() {
        if (mDataBean != null) {
            Intent intent = new Intent(MemberCenterActivity.this, OrdinaryWebViewActivity.class);
            intent.putExtra("url", mDataBean.getRuleUrl());
            intent.putExtra("name", "会员升级说明");
            startActivity(intent);
        }
    }
}
