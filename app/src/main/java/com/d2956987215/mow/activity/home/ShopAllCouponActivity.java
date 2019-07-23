package com.d2956987215.mow.activity.home;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.d2956987215.mow.R;
import com.d2956987215.mow.activity.BaseActivity;
import com.d2956987215.mow.adapter.ErJiHotAdapter;
import com.d2956987215.mow.adapter.ShopAllCouponAdapter;
import com.d2956987215.mow.rxjava.Request;
import com.d2956987215.mow.rxjava.Result;
import com.d2956987215.mow.rxjava.RxJavaUtil;
import com.d2956987215.mow.rxjava.response.ErJiListResponse;
import com.d2956987215.mow.rxjava.response.ShopAllCouponResponse;
import com.d2956987215.mow.util.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import me.bakumon.statuslayoutmanager.library.StatusLayoutManager;

public class ShopAllCouponActivity extends BaseActivity implements  SwipeRefreshLayout.OnRefreshListener  {


    @BindView(R.id.recycler_hot_product)
    RecyclerView recycler_hot_product;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;

    private int p = 1;
    private StatusLayoutManager statusLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        statusLayoutManager = new StatusLayoutManager.Builder(refreshLayout).setDefaultLayoutsBackgroundColor(Color.WHITE).setEmptyLayout(inflate(R.layout.custom_empty_view))
                .build();
        huoqu();
        refreshLayout.setOnRefreshListener(this);
        showRefresh();
    }

    private void showRefresh(){
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
        map.put("sellernick", getIntent().getStringExtra("name"));
        if (User.uid() > 0) {
            map.put("user_id", User.uid()+"");
        }
        map.put("page", p + "");
        if(User.uid()>0){
            map.put("user_id",User.uid()+"");
        }
        new Request<ShopAllCouponResponse>().request(RxJavaUtil.xApi().shopAllCoupon(map, "Bearer " + User.token()), "店铺所有优惠券", this, false, new Result<ShopAllCouponResponse>() {


            @Override
            public void get(ShopAllCouponResponse response) {
                refreshLayout.setRefreshing(false);
                if (response.getData() instanceof List && ((List) response.getData()).size() > 0) {
                    statusLayoutManager.showSuccessLayout();
                    List<ShopAllCouponResponse.DataBean> list = response.getData();
                        initHotRecycler1(list);

                } else {
                    if (p == 1) {
                        statusLayoutManager.showEmptyLayout();

                        if (hotAdapter != null) {
                            hotAdapter.getData().clear();
                            hotAdapter.notifyDataSetChanged();
                            hotAdapter.loadMoreComplete();
                            hotAdapter.loadMoreEnd();
                        }

                    } else {

                        hotAdapter.loadMoreComplete();
                        hotAdapter.loadMoreEnd();
                    }

                }

            }

            @Override
            public void onBackErrorMessage(ShopAllCouponResponse response) {
                super.onBackErrorMessage(response);
            }

            @Override
            public void onServerError(String errDesc) {
                super.onServerError(errDesc);
            }
        });

    }

    private ShopAllCouponAdapter hotAdapter;

    private void initHotRecycler1(final List<ShopAllCouponResponse.DataBean> list) {
        if (hotAdapter == null) {
            hotAdapter = new ShopAllCouponAdapter(R.layout.adapter_shop_all_coupon, list);
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
            hotAdapter.loadMoreEnd();
        }
        hotAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(ShopAllCouponActivity.this, TaoBaoDetailActivity.class);
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
        return R.layout.activity_shop_all_coupon;
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
