package com.d2956987215.mow.activity.home;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.d2956987215.mow.R;
import com.d2956987215.mow.adapter.BrandListAdapter;
import com.d2956987215.mow.adapter.JiHuaSuanAdapter;
import com.d2956987215.mow.rxjava.Request;
import com.d2956987215.mow.rxjava.Result;
import com.d2956987215.mow.rxjava.RxJavaUtil;
import com.d2956987215.mow.rxjava.response.BrandListResponse;
import com.d2956987215.mow.rxjava.response.JuHuaSuanResponse;
import com.d2956987215.mow.util.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

@SuppressLint("ValidFragment")
public class JuHuaSuanFragmentOne extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout mRefreshLayout;
    Unbinder unbinder;
    private int page = 1;
    private JiHuaSuanAdapter brandListAdapter;
    private int brandcat;
    private String activity_type;

    @SuppressLint("ValidFragment")
    public JuHuaSuanFragmentOne(int brandcat, String activity_type) {
        this.brandcat = brandcat;
        this.activity_type = activity_type;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frg_juhuasuan, container, false);
        unbinder = ButterKnife.bind(this, view);
        mRefreshLayout.setOnRefreshListener(this);
        huoqutitile();
        return view;
    }


    private void huoqutitile() {
        Map<String, String> map = new HashMap<>();
        map.put("user_id", User.uid() + "");
        map.put("activity_type", activity_type + "");
        map.put("cate_id", brandcat + "");
        map.put("page", page + "");
        new Request<JuHuaSuanResponse>().request(RxJavaUtil.xApi1().ActivityGoodList(map), "顶部数据", getActivity(), false, new Result<JuHuaSuanResponse>() {
            @Override
            public void get(JuHuaSuanResponse response) {
                if (response != null) {
                    if (page == 1) {
                        mRefreshLayout.setRefreshing(false);
                    } else {
                        brandListAdapter.loadMoreComplete();
                    }
                    if (response.getData().getGoodsList() != null && response.getData().getGoodsList().size() > 0) {
                        if (page == 1) {
                            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                            brandListAdapter = new JiHuaSuanAdapter(R.layout.adapter_hot_product, response.getData().getGoodsList());
                            mRecyclerView.setAdapter(brandListAdapter);
                        } else {
                            brandListAdapter.addData(response.getData().getGoodsList());
                        }

                    }


                    brandListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                            Intent intent = new Intent(getContext(), TaoBaoDetailActivity.class);
                            intent.putExtra("id", brandListAdapter.getItem(position).getItemid() + "");
                            intent.putExtra("quan_id", brandListAdapter.getItem(position).getQuan_id() + "");

                            startActivity(intent);
                        }
                    });
                    brandListAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
                        @Override
                        public void onLoadMoreRequested() {
                            page++;
                            huoqutitile();
                        }
                    }, mRecyclerView);
                }
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onRefresh() {
        page = 1;
        huoqutitile();
    }
}
