package com.d2956987215.mow.activity.mine;

import android.content.Intent;
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
import com.d2956987215.mow.activity.home.TaoBaoDetailActivity;
import com.d2956987215.mow.adapter.HomeZuJiAdapter;
import com.d2956987215.mow.dialog.QueDingDialog;
import com.d2956987215.mow.listener.IShowData;
import com.d2956987215.mow.rxjava.Request;
import com.d2956987215.mow.rxjava.Result;
import com.d2956987215.mow.rxjava.RxJavaUtil;
import com.d2956987215.mow.rxjava.response.MyZuJiResponse;
import com.d2956987215.mow.rxjava.response.PersonInfoResponse;
import com.d2956987215.mow.util.ToastUtil;
import com.d2956987215.mow.util.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import me.bakumon.statuslayoutmanager.library.StatusLayoutManager;


public class MyZuJiActivity extends BaseActivity implements IShowData, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.tv_bianji)
    TextView tv_bianji;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;


    private HomeZuJiAdapter hotAdapter;
    private boolean chooseAll = true;
    private int p = 1;
    private String ids;
    private StatusLayoutManager statusLayoutManager;
//    private List<MyZuJiResponse.DataBean> goodlist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        statusLayoutManager = new StatusLayoutManager.Builder(refreshLayout).setDefaultLayoutsBackgroundColor(Color.WHITE).setEmptyLayout(initEmptyView())
                .build();
//        refreshLayout.setOnRefreshListener(this);
//        refreshLayout.setOnLoadmoreListener(this);
        huoqu();
        refreshLayout.setOnRefreshListener(this);
        showRefresh();

    }

    private void showRefresh() {
        refreshLayout.post(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(true);
            }
        });
    }

    private View initEmptyView() {
        View emptyView = inflate(R.layout.custom_empty_view);
        ImageView iv = emptyView.findViewById(R.id.empty_retry_view);
        TextView tv = emptyView.findViewById(R.id.empty_view_tv);
        iv.setBackgroundResource(R.drawable.collect_img_default);
        tv.setText("你还没有浏览商品，快去逛逛吧");
        return emptyView;
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
        map.put("user_id", User.uid() + "");
        map.put("page", p + "");
        new Request<MyZuJiResponse>().request(RxJavaUtil.xApi().zujilist(map, "Bearer " + User.token()), "我的足迹", this, false, new Result<MyZuJiResponse>() {
            @Override
            public void get(MyZuJiResponse response) {
                refreshLayout.setRefreshing(false);
                if (response.getData() instanceof List && ((List) response.getData()).size() > 0) {

                    List<MyZuJiResponse.DataBean> list = response.getData();
                    if (list != null && list.size() > 0) {
//                        statusLayoutManager.showSuccessLayout();
                        initHotRecycler1(list);
                    } else {
//                        statusLayoutManager.showEmptyLayout();
                    }
                } else {
                    if (p == 1) {
                        if (hotAdapter != null) {
                            hotAdapter.getData().clear();
                            hotAdapter.notifyDataSetChanged();

                        }

                        statusLayoutManager.showEmptyLayout();
                    } else {
                        hotAdapter.loadMoreComplete();
                        hotAdapter.loadMoreEnd();
                    }
                }
            }

            @Override
            public void onBackErrorMessage(MyZuJiResponse response) {
                super.onBackErrorMessage(response);
//                if (p == 1) {
//                    if (goodlist != null) {
//                        goodlist.clear();
//                    }
//                    hotAdapter.notifyDataSetChanged();
                    statusLayoutManager.showEmptyLayout();
//                }
            }
        });

    }

    private void initHotRecycler1(final List<MyZuJiResponse.DataBean> list) {
        if (hotAdapter == null) {
            hotAdapter = new HomeZuJiAdapter(R.layout.adapter_hot_zuji, list);
            int spanCount = 1; // 只显示一行
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);
//            recyclerView.setLayoutManager(layoutManager);
//            recyclerView.setHasFixedSize(true);
//            recyclerView.setNestedScrollingEnabled(false);
            recyclerView.setAdapter(hotAdapter);
        }
        if (p == 1) {
            refreshLayout.setRefreshing(false);
            hotAdapter.setNewData(list);
        } else {
            hotAdapter.addData(list);
            hotAdapter.loadMoreComplete();
        }
        hotAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(MyZuJiActivity.this, TaoBaoDetailActivity.class);
                intent.putExtra("id", hotAdapter.getItem(position).getGoods_id() + "");
                intent.putExtra("quan_id", hotAdapter.getItem(position).getQuan_id() + "");
                intent.putExtra("type", "1");
                startActivity(intent);


            }
        });
        hotAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                p++;
                huoqu();
            }
        }, recyclerView);

    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_myzuji;
    }

    @Override
    public void show(Object obj) {

    }


    @OnClick({R.id.tv_bianji})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_bianji:
                QueDingDialog queDingDialog = new QueDingDialog(this, new QueDingDialog.CallBack() {
                    @Override
                    public void NO() {
                        Map<String, String> map = new HashMap<>();
                        map.put("user_id", User.uid() + "");
                        new Request<PersonInfoResponse>().request(RxJavaUtil.xApi().qingchuzuji(map, "Bearer " + User.token()), "", MyZuJiActivity.this, false, new Result<PersonInfoResponse>() {
                            @Override
                            public void get(PersonInfoResponse response) {
                                ToastUtil.show(MyZuJiActivity.this, response.message);
                                if (hotAdapter != null) {
                                    hotAdapter.getData().clear();
                                    hotAdapter.notifyDataSetChanged();
                                    statusLayoutManager.showEmptyLayout();
                                }


                            }
                        });
                    }
                });
                queDingDialog.show();

                break;

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
//
//
//    }

    @Override
    public void onRefresh() {
        p = 1;
        huoqu();
    }
}
