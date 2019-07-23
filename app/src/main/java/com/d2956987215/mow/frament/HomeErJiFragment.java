package com.d2956987215.mow.frament;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.d2956987215.mow.R;
import com.d2956987215.mow.activity.home.TaoBaoDetailActivity;
import com.d2956987215.mow.adapter.HomeHotAdapter;
import com.d2956987215.mow.listener.IShowData;
import com.d2956987215.mow.rxjava.Request;
import com.d2956987215.mow.rxjava.Result;
import com.d2956987215.mow.rxjava.RxJavaUtil;
import com.d2956987215.mow.rxjava.response.HomeListResponse;
import com.d2956987215.mow.rxjava.response.ShouTitleResponse;
import com.d2956987215.mow.util.AnimatorEffect;
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
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeErJiFragment extends Fragment implements IShowData, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.recycler_hot_product)
    RecyclerView recyclerHotProduct;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;
//    @BindView(R.id.scrollView)
//    NestedScrollView scrollView;


    Unbinder unbinder;

    private HomeHotAdapter hotAdapter;
    private String type = "1";

    private List<HomeListResponse.DataBeanX.DataBean> goodlist = new ArrayList<>();
    private int p = 1;
    private ShouTitleResponse.DataBean.CateBean dataBean;

    public void setType(ShouTitleResponse.DataBean.CateBean dataBean, String type) {
        this.dataBean = dataBean;
        this.type = type;
    }

    private boolean isLoadingMore = true;

    public HomeErJiFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_erji, container, false);
        unbinder = ButterKnife.bind(this, view);
        refreshLayout.setOnRefreshListener(this);
//        refreshLayout.setOnRefreshListener(this);
//        refreshLayout.setOnLoadmoreListener(this);
        refreshLayout.post(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(true);
            }
        });
        huoqu();
//        scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
//            @Override
//            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
////                if (scrollY > oldScrollY) {
////                    Log.i(TAG, "Scroll DOWN");
////                }
////                if (scrollY < oldScrollY) {
////                    Log.i(TAG, "Scroll UP");
////                }
//
////                if (scrollY == 0) {
////                    Log.i(TAG, "TOP SCROLL");
////                }
//                int pos = v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight() - scrollY;
//                if (pos < 2000 && isLoadingMore) {
//
//                    p++;
//                    isLoadingMore = false;
//                    huoqu();
//
//
//                }
//            }
//        });

        return view;
    }


    private void huoqu() {
        Map<String, String> map = new HashMap<>();
        map.put("cate_id", dataBean.getId() + "");
        map.put("keyword", dataBean.getName());
        map.put("type", type);
        if (User.uid() > 0) {
            map.put("user_id", User.uid() + "");
        }
        map.put("page", p + "");
        if (User.uid() > 0) {
            map.put("user_id", User.uid() + "");
        }
        new Request<HomeListResponse>().request(RxJavaUtil.xApi().getsearchlist(map), "首页列表", getActivity(), false, new Result<HomeListResponse>() {


            @Override
            public void get(HomeListResponse response) {
                if (response.getData().getData() instanceof List && ((List) response.getData().getData()).size() > 0) {
                    List<HomeListResponse.DataBeanX.DataBean> list = response.getData().getData();
                    if (list != null && list.size() > 0) {
                        initHotRecycler1(list);
                    }

                }

            }
        });

    }


    private void initHotRecycler1(final List<HomeListResponse.DataBeanX.DataBean> list) {
        if (hotAdapter == null) {
            hotAdapter = new HomeHotAdapter(R.layout.adapter_hot_product, list);
            int spanCount = 1; // 只显示一行
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            recyclerHotProduct.setLayoutManager(layoutManager);
//            recyclerHotProduct.setLayoutManager(layoutManager);
//            recyclerHotProduct.setHasFixedSize(true);
//            recyclerHotProduct.setItemViewCacheSize(10);
//            recyclerHotProduct.setItemAnimator(null);
//            hotAdapter.setHasStableIds(true);
//            recyclerHotProduct.setNestedScrollingEnabled(false);
            recyclerHotProduct.setAdapter(hotAdapter);
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
                Intent intent = new Intent(getContext(), TaoBaoDetailActivity.class);
                intent.putExtra("id", hotAdapter.getItem(position).getItemid() + "");
                intent.putExtra("quan_id", hotAdapter.getItem(position).getQuan_id() + "");
                startActivity(intent);


            }
        });
        hotAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                p++;
                huoqu();
            }
        }, recyclerHotProduct);

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @OnClick({})
    public void onViewClicked(View view) {
        AnimatorEffect.setClickEffect(getContext(), view);
        switch (view.getId()) {
//            case R.id.la_dingwei:
//                //跳转选择地址或者列表选择
//                tanchu();
//
//                break;

        }
    }


    @SuppressWarnings("unchecked")
    @Override
    public void show(Object obj) {

    }


//    @Override
//    public void onRefresh(RefreshLayout refreshlayout) {
//        //presenter.banner();
//        p = 1;
//
//        huoqu();
//
//        // presenter.hotProduct(p);
//        refreshlayout.finishRefresh();
//    }

//
//    @Override
//    public void onLoadmore(RefreshLayout refreshlayout) {
//        p++;
//        huoqu();
//        refreshlayout.finishLoadmore();
//
//    }

    @Override
    public void onRefresh() {
        p = 1;

        huoqu();
    }
}
