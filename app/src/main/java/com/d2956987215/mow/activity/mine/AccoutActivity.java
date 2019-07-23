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

import com.blankj.utilcode.util.LogUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.d2956987215.mow.R;
import com.d2956987215.mow.activity.BaseActivity;
import com.d2956987215.mow.adapter.MyaccoutlistAdapter;
import com.d2956987215.mow.rxjava.Request;
import com.d2956987215.mow.rxjava.Result;
import com.d2956987215.mow.rxjava.RxJavaUtil;
import com.d2956987215.mow.rxjava.response.AccuntListResponse;
import com.d2956987215.mow.util.SP;
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
import me.bakumon.statuslayoutmanager.library.StatusLayoutManager;

public class AccoutActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerHotProduct;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;
    StatusLayoutManager statusLayoutManager;

    private MyaccoutlistAdapter hotAdapter;
    private int p = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        statusLayoutManager = new StatusLayoutManager.Builder(refreshLayout).setDefaultLayoutsBackgroundColor(Color.WHITE).setEmptyLayout(initEmptyView())
                .build();
        indata();
        refreshLayout.setOnRefreshListener(this);
        showRefresh();
        hotAdapter = new MyaccoutlistAdapter(R.layout.adapter_accountlist, null);
        hotAdapter.setEmptyView(initEmptyView());

        recyclerHotProduct.setLayoutManager(new LinearLayoutManager(this));

    }

    private View initEmptyView() {
        View emptyView = getLayoutInflater().inflate(R.layout.custom_empty_view, null);
        ImageView iv = emptyView.findViewById(R.id.empty_retry_view);
        TextView tv = emptyView.findViewById(R.id.empty_view_tv);
        iv.setBackgroundResource(R.drawable.nothing_img_default);
        tv.setText("暂无订单~");
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


    private void indata() {
        Map<String, String> map = new HashMap<>();

        map.put("user_id", String.valueOf(User.uid()));
        map.put("roletype", SP.getString("roletypes", null));
        map.put("type", "2");
        map.put("page", p + "");
        new Request<AccuntListResponse>().request(RxJavaUtil.xApi().accountlist(map, "Bearer " + User.token()), "浏览商品", this, false, new Result<AccuntListResponse>() {
            @Override
            public void get(AccuntListResponse response) {
                refreshLayout.setRefreshing(false);

                if (response.getData().getData() != null && response.getData().getData().size() > 0) {
                    statusLayoutManager.showSuccessLayout();
                    List<AccuntListResponse.DataBeanX.DataBean> list = response.getData().getData();
                    initHotRecycler(list);
                } else {
                    if (p == 1) {
                        statusLayoutManager.showEmptyLayout();
//                        hotAdapter.setEmptyView(initEmptyView());
                        if (hotAdapter != null) {
                            hotAdapter.getData().clear();
                            hotAdapter.notifyDataSetChanged();
                        }
                        LogUtils.e("aaaaaaaaaaaaaaaaaaaaa", "bbb");
//                        statusLayoutManager.showEmptyLayout();
                    } else {
                        hotAdapter.loadMoreComplete();
                        hotAdapter.loadMoreEnd();
                    }

                }


            }
        });


    }

    private void initHotRecycler(List<AccuntListResponse.DataBeanX.DataBean> list) {

        recyclerHotProduct.setAdapter(hotAdapter);
        if (p == 1) {
            refreshLayout.setRefreshing(false);
            hotAdapter.setNewData(list);
        } else {
            hotAdapter.addData(list);
            hotAdapter.loadMoreComplete();
        }

        hotAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                p++;
                indata();
            }
        }, recyclerHotProduct);


    }

    @Override
    protected String title() {
        return "我的账单";
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_list;
    }


    @Override
    public void show(Object obj) {

    }



    @Override
    public void onRefresh() {
        p = 1;
        indata();
    }

}
