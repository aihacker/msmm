package com.d2956987215.mow.activity.mine;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.d2956987215.mow.R;
import com.d2956987215.mow.activity.BaseActivity;
import com.d2956987215.mow.adapter.MyTeamMaiShouAdapter;
import com.d2956987215.mow.rxjava.Request;
import com.d2956987215.mow.rxjava.Result;
import com.d2956987215.mow.rxjava.RxJavaUtil;
import com.d2956987215.mow.rxjava.response.MyTeamResponse;
import com.d2956987215.mow.util.User;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import me.bakumon.statuslayoutmanager.library.StatusLayoutManager;

public class MyTuanDuiListActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {


    @BindView(R.id.recycler_hot_product)
    RecyclerView recycler_hot_product;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.tv_title)
    TextView tv_title;
    private int p = 1;
    private String type = "1A";
    private StatusLayoutManager statusLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        statusLayoutManager = new StatusLayoutManager.Builder(refreshLayout).setDefaultLayoutsBackgroundColor(Color.WHITE).setEmptyLayout(initEmptyView())
                .build();
        huoqu();
        refreshLayout.setOnRefreshListener(this);
        showRefresh();
    }


    private View initEmptyView() {
        View emptyView = getLayoutInflater().inflate(R.layout.custom_empty_view, null);
        ImageView iv = emptyView.findViewById(R.id.empty_retry_view);
        TextView tv = emptyView.findViewById(R.id.empty_view_tv);
        iv.setBackgroundResource(R.drawable.nothing_img_default);
        tv.setText("还没有粉丝呢,继续加油喔~");
        return emptyView;
    }


    private void showRefresh() {
        refreshLayout.post(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(true);
            }
        });
    }

    private LayoutInflater inflater;

    private View inflate(@LayoutRes int resource) {
        if (inflater == null) {
            inflater = LayoutInflater.from(this);
        }
        return inflater.inflate(resource, null);
    }

    private void huoqu() {
        Map<String, String> map = new HashMap<>();
        map.put("type", type);
        map.put("user_id", User.uid() + "");
        map.put("page", p + "");
        new Request<MyTeamResponse>().request(RxJavaUtil.xApi().maishoutuandui(map, "Bearer " + User.token()), "获取我的买手团队列表", this, false, new Result<MyTeamResponse>() {
            @Override
            public void get(MyTeamResponse response) {
                refreshLayout.setRefreshing(false);
                statusLayoutManager.showSuccessLayout();
                tv_title.setText(response.getData().getTitle());
                if (response.getData().getList() instanceof List && ((List) response.getData().getList()).size() > 0) {
                    statusLayoutManager.showSuccessLayout();
                    List<MyTeamResponse.DataBean.ListBean> list = response.getData().getList();
                    if (list != null && list.size() > 0) {
                        initHotRecycler1(list);
                    }

                } else {
                    if (p == 1)
                        statusLayoutManager.showEmptyLayout();
                    if (hotAdapter != null) {
                        hotAdapter.loadMoreComplete();
                        hotAdapter.loadMoreEnd();
                        hotAdapter.notifyDataSetChanged();

                    } else {
                        if(hotAdapter!=null){
                            hotAdapter.loadMoreComplete();
                            hotAdapter.loadMoreEnd();
                        }

                    }

                }

            }
        });

    }


    @OnClick({R.id.rb_a, R.id.rb_b})
    public void onViewClicked(View view) {
        p = 1;
        switch (view.getId()) {
            case R.id.rb_a:
                showRefresh();
                clearAdapterData();
                type = "1A";
                huoqu();
                break;
            case R.id.rb_b:
                showRefresh();
                clearAdapterData();
                type = "2B";
                huoqu();
                break;

        }
    }

    private void clearAdapterData() {
        if (hotAdapter != null) {
            hotAdapter.getData().clear();
            hotAdapter.notifyDataSetChanged();
        }

    }


//    @Override
//    public void onLoadmore(RefreshLayout refreshlayout) {
//        p++;
//        huoqu();
//        refreshlayout.finishLoadmore();
//    }

    //    @Override
//    public void onRefresh(RefreshLayout refreshlayout) {
//        p = 1;
//        huoqu();
//        refreshlayout.finishRefresh();
//    }
//    private List<MyTeamResponse.DataBean.ListBean> goodlist = new ArrayList<>();
    private MyTeamMaiShouAdapter hotAdapter;

    private void initHotRecycler1(final List<MyTeamResponse.DataBean.ListBean> list) {
        refreshLayout.setRefreshing(false);
        if (hotAdapter == null) {
            hotAdapter = new MyTeamMaiShouAdapter(R.layout.adapter_tuandui, list);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            recycler_hot_product.setLayoutManager(layoutManager);
            recycler_hot_product.setAdapter(hotAdapter);
        }
        if (p == 1) {
            refreshLayout.setRefreshing(false);
            hotAdapter.setNewData(list);
        } else {
            hotAdapter.addData(list);
            hotAdapter.loadMoreComplete();
        }


        hotAdapter.setOnLoadMoreListener(() -> {
            p++;
            huoqu();
        }, recycler_hot_product);


    }

    @Override
    protected String title() {
        return "";

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_tuanduilist;
    }

    @Override
    public void show(Object obj) {

    }

    @Override
    public void onRefresh() {
        p = 1;
        huoqu();
    }
}
