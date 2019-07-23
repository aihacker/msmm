package com.d2956987215.mow.activity.home;

import android.annotation.SuppressLint;
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
import com.d2956987215.mow.rxjava.Request;
import com.d2956987215.mow.rxjava.Result;
import com.d2956987215.mow.rxjava.RxJavaUtil;
import com.d2956987215.mow.rxjava.response.BrandListResponse;
import com.d2956987215.mow.util.User;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

@SuppressLint("ValidFragment")
public class BrandSaleFragmentOne extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout mRefreshLayout;
    Unbinder unbinder;
    private int page = 1;
    private BrandListAdapter brandListAdapter;
    private String brandcat="";

//    @SuppressLint("ValidFragment")
//    public BrandSaleFragmentOne(String brandcat) {
//        this.brandcat = brandcat;
//    }

    public static BrandSaleFragmentOne newInstance(String brandcat){
        BrandSaleFragmentOne brandSaleFragmentOne=new BrandSaleFragmentOne();
        Bundle args = new Bundle();
        args.putString("param", brandcat);
        brandSaleFragmentOne.setArguments(args);
        return brandSaleFragmentOne;
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.common_refresh, container, false);
        unbinder = ButterKnife.bind(this, view);
        if(getArguments()!=null){
            brandcat=getArguments().getString("param");
        }
        mRefreshLayout.setOnRefreshListener(this);
        huoqutitile();
        return view;
    }


    private void huoqutitile() {
        Map<String, String> map = new HashMap<>();
        if (User.uid() > 0) {
            map.put("user_id", User.uid() + "");
            map.put("page", page + "");
            if (brandcat != null) {
                map.put("brandcat", brandcat);
            }
        }
        new Request<BrandListResponse>().request(RxJavaUtil.xApi1().brandList(map), "数据", getActivity(), false, new Result<BrandListResponse>() {
            @Override
            public void get(BrandListResponse response) {
                if (page == 1) {
                    mRefreshLayout.setRefreshing(false);
                } else {
                    brandListAdapter.loadMoreComplete();
                }
                if (response != null) {
                    if (response.getData() != null && response.getData().size() > 0) {
                        if (page == 1) {
                            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                            brandListAdapter = new BrandListAdapter(R.layout.item_brand_frgone, response.getData());
                            mRecyclerView.setAdapter(brandListAdapter);
                        } else {
                            brandListAdapter.addData(response.getData());
                        }

                    }

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
