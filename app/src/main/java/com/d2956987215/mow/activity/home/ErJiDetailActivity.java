package com.d2956987215.mow.activity.home;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.d2956987215.mow.R;
import com.d2956987215.mow.activity.BaseActivity;
import com.d2956987215.mow.adapter.ErJiHotAdapter;
import com.d2956987215.mow.rxjava.Request;
import com.d2956987215.mow.rxjava.Result;
import com.d2956987215.mow.rxjava.RxJavaUtil;
import com.d2956987215.mow.rxjava.response.ErJiListResponse;
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

public class ErJiDetailActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {


    @BindView(R.id.recycler_hot_product)
    RecyclerView recycler_hot_product;
    @BindView(R.id.iv_iv_jiage)
    ImageView iv_iv_jiage;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;

    @BindView(R.id.iv_show_quan)
    ImageView iv_show_quan;
    private int p = 1;
    private String type = "1";
    private StatusLayoutManager statusLayoutManager;
    private AnimationDrawable animationDrawable;

    boolean mFirst = true;
    private String hascoupon = "0";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        statusLayoutManager = new StatusLayoutManager.Builder(refreshLayout).setDefaultLayoutsBackgroundColor(Color.WHITE).setEmptyLayout(inflate(R.layout.custom_empty_view))
                .build();
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
        map.put("keyword", getIntent().getStringExtra("name"));
        if (User.uid() > 0) {
            map.put("user_id", User.uid() + "");
        }
        map.put("cate_id", getIntent().getStringExtra("cate_id"));
//        if (order != null) {
//            map.put("order", order);
//            if (order.equals("itemendprice")) {
//                map.put("sort", sort);
//            }
//        }
        map.put("order", order);
        map.put("sort", sort);
        map.put("hascoupon", hascoupon);
        map.put("page", p + "");
        if (User.uid() > 0) {
            map.put("user_id", User.uid() + "");
        }
        new Request<ErJiListResponse>().request(RxJavaUtil.xApi().erjidetail(map), "二级列表", this, false, new Result<ErJiListResponse>() {


            @Override
            public void get(ErJiListResponse response) {
                refreshLayout.setRefreshing(false);
                if (response.getData().getData() instanceof List && ((List) response.getData().getData()).size() > 0) {
                    statusLayoutManager.showSuccessLayout();
                    List<ErJiListResponse.DataBeanX.DataBean> list = response.getData().getData();
                    if (list != null && list.size() > 0) {
                        initHotRecycler1(list);
//                        statusLayoutManager.showEmptyLayout();
                    }

                } else {
                    if (p == 1) {
                        if (hotAdapter != null) {
//                            goodlist.clear();
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
            public void onBackErrorMessage(ErJiListResponse response) {
                super.onBackErrorMessage(response);
            }

            @Override
            public void onServerError(String errDesc) {
                super.onServerError(errDesc);
            }
        });

    }

    private String order = "";
    private String sort = "desc";

    @OnClick({R.id.rb_zong, R.id.rb_yongjin, R.id.rb_jiage, R.id.rb_xiaoshou, R.id.iv_show_quan})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rb_zong:
                iv_iv_jiage.setBackground(getResources().getDrawable(R.mipmap.list_comp_default));
                order = "";
                p = 1;
                showRefresh();
                huoqu();
                break;
            case R.id.rb_yongjin:
                iv_iv_jiage.setBackground(getResources().getDrawable(R.mipmap.list_comp_default));
                order = "tkmoney";
                sort = "desc";
                p = 1;
                showRefresh();
                huoqu();
                break;
            case R.id.rb_jiage:
                p = 1;

                order = "itemendprice";
                if (sort.equals("desc")) {
                    iv_iv_jiage.setBackground(getResources().getDrawable(R.mipmap.list_comp_down));
                    sort = "asc";

                    showRefresh();
                    huoqu();
                } else {
                    sort = "desc";

                    iv_iv_jiage.setBackground(getResources().getDrawable(R.mipmap.list_comp_top));
                    huoqu();
                }

                break;
            case R.id.rb_xiaoshou:
                iv_iv_jiage.setBackground(getResources().getDrawable(R.mipmap.list_comp_default));
                sort = "desc";
                order = "itemsale";
                p = 1;
                showRefresh();
                huoqu();
                break;

            case R.id.iv_show_quan:
                sort = "desc";
                p = 1;
                showRefresh();
                if (TextUtils.equals("1", hascoupon)) {
                    iv_show_quan.setImageResource(R.mipmap.news_close);
                    hascoupon = "0";
                } else {
                    iv_show_quan.setImageResource(R.mipmap.news_open);
                    hascoupon = "1";
                }
                huoqu();
                break;
        }
    }

    private List<ErJiListResponse.DataBeanX.DataBean> goodlist = new ArrayList<>();
    private ErJiHotAdapter hotAdapter;

    private void initHotRecycler1(final List<ErJiListResponse.DataBeanX.DataBean> list) {
        if (hotAdapter == null) {
            hotAdapter = new ErJiHotAdapter(R.layout.adapter_hot_product, list);
            hotAdapter.setPreLoadNumber(3);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            recycler_hot_product.setLayoutManager(layoutManager);
            recycler_hot_product.setAdapter(hotAdapter);
        }
        if (p == 1) {
            refreshLayout.setRefreshing(false);
            hotAdapter.setNewData(list);
            hotAdapter.loadMoreComplete();
        } else {
            hotAdapter.addData(list);
            hotAdapter.loadMoreComplete();
        }
        hotAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(ErJiDetailActivity.this, TaoBaoDetailActivity.class);
                intent.putExtra("id", hotAdapter.getItem(position).getItemid() + "");
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
        }, recycler_hot_product);


    }

    @Override
    protected String title() {
        return getIntent().getStringExtra("name");

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_erjidetail;
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
