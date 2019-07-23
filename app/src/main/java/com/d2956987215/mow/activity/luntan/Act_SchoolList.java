package com.d2956987215.mow.activity.luntan;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.d2956987215.mow.R;
import com.d2956987215.mow.activity.BaseActivity;
import com.d2956987215.mow.adapter.SchoolListAdapter;
import com.d2956987215.mow.rxjava.Request;
import com.d2956987215.mow.rxjava.Result;
import com.d2956987215.mow.rxjava.RxJavaUtil;
import com.d2956987215.mow.rxjava.response.SchoolListResponse;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

public class Act_SchoolList extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.rl_back)
    RelativeLayout mRlBack;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_submit)
    TextView mTvSubmit;
    @BindView(R.id.tv_gouwuche)
    ImageView mTvGouwuche;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout mRefreshLayout;
    private int cate_id;
    private int p = 1;
    private SchoolListAdapter schoolListAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.act_schoollist;
    }

    @Override
    public void show(Object obj) {

    }

    @Override
    protected String title() {
        if (!TextUtils.isEmpty(getIntent().getStringExtra("titleName"))) {
            return getIntent().getStringExtra("titleName");
        } else {
            return "文章列表";
        }

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cate_id = getIntent().getIntExtra("cate_id", 0);
        mRefreshLayout.setOnRefreshListener(this);
        articlelist();
    }

    private void articlelist() {
        Map<String, String> map = new HashMap<>();
        map.put("cate_id", cate_id + "");
        map.put("page", p + "");
        map.put("page_size", "20");
        new Request<SchoolListResponse>().request(RxJavaUtil.xApi1().SchoolList(map), "每日爆款列表", Act_SchoolList.this, false, new Result<SchoolListResponse>() {
            @Override
            public void get(SchoolListResponse response) {
                if (p == 1) {
                    mRefreshLayout.setRefreshing(false);
                } else {
                    schoolListAdapter.loadMoreComplete();
                }
                if (response.getData() != null && response.getData().size() > 0) {
                    mRecyclerView.setVisibility(View.VISIBLE);
                    if (p == 1) {
                        mRecyclerView.setLayoutManager(new LinearLayoutManager(Act_SchoolList.this));
                        schoolListAdapter = new SchoolListAdapter(R.layout.item_schoollist, response.getData());
                        mRecyclerView.setAdapter(schoolListAdapter);
                    } else {
                        schoolListAdapter.addData(response.getData());
                    }

                    if (response.getData().size() == 20) {
                        schoolListAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
                            @Override
                            public void onLoadMoreRequested() {
                                p++;
                                articlelist();
                            }
                        }, mRecyclerView);
                    } else {
                        schoolListAdapter.addFooterView(LayoutInflater.from(Act_SchoolList.this).inflate(R.layout.footer_layout, null));
                    }
                } else {
                    if (p == 1) {
                        mRecyclerView.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onBackErrorMessage(SchoolListResponse response) {
                if (p == 1) {
                    mRefreshLayout.setRefreshing(false);
                } else {
                    schoolListAdapter.loadMoreComplete();
                }
                //没有数据
                mRecyclerView.setVisibility(View.GONE);
            }
        });

    }

    @Override
    public void onRefresh() {
        p = 1;
        articlelist();
    }
}
