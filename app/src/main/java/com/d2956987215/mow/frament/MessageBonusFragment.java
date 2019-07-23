package com.d2956987215.mow.frament;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.d2956987215.mow.R;
import com.d2956987215.mow.adapter.XiTongListAdapter;
import com.d2956987215.mow.listener.IShowData;
import com.d2956987215.mow.rxjava.Request;
import com.d2956987215.mow.rxjava.Result;
import com.d2956987215.mow.rxjava.RxJavaUtil;
import com.d2956987215.mow.rxjava.response.HomeListResponse;
import com.d2956987215.mow.rxjava.response.ShouTitleResponse;
import com.d2956987215.mow.rxjava.response.XiTonfListResponse;
import com.d2956987215.mow.util.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.bakumon.statuslayoutmanager.library.StatusLayoutManager;

/**
 * A simple {@link Fragment} subclass.
 */
public class MessageBonusFragment extends Fragment implements IShowData, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.recycler_hot_product)
    RecyclerView recyclerHotProduct;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.ll)
    LinearLayout mLl;


    Unbinder unbinder;
    private StatusLayoutManager statusLayoutManager;

    private XiTongListAdapter hotAdapter;
    private int type;

    private List<HomeListResponse.DataBeanX.DataBean> goodlist = new ArrayList<>();
    private int p = 1;
    private ShouTitleResponse.DataBean.CateBean dataBean;

    public void setType(int type) {

        this.type = type;
    }

    private boolean isLoadingMore = true;

    public MessageBonusFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_message, container, false);
        unbinder = ButterKnife.bind(this, view);
        huoqu();
        refreshLayout.setOnRefreshListener(this);
        showRefresh();
        statusLayoutManager = new StatusLayoutManager.Builder(mLl).setDefaultLayoutsBackgroundColor(Color.WHITE).setEmptyLayout(inflate(R.layout.custom_empty_view))
                .build();
        return view;
    }
    private LayoutInflater inflater;

    private View inflate(@LayoutRes int resource) {
        if (inflater == null) {
            inflater = LayoutInflater.from(getActivity());
        }
        return inflater.inflate(resource, null);
    }

    private void showRefresh() {
        refreshLayout.post(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(true);
            }
        });
    }


    private void huoqu() {
        Map<String, String> map = new HashMap<>();
        map.put("user_id", String.valueOf(User.uid()));
        map.put("page", p + "");
        map.put("token", User.token());
        new Request<XiTonfListResponse>().request(RxJavaUtil.xApi().bonusMessage(map,"Bearer " + User.token()), "奖金消息", getActivity(), false, new Result<XiTonfListResponse>() {
            @Override
            public void get(XiTonfListResponse response) {
                refreshLayout.setRefreshing(false);
                statusLayoutManager.showSuccessLayout();

                if (response.getData().getData() instanceof List && ((List) response.getData().getData()).size() > 0) {
                    initHotRecycler(response.getData().getData());
                } else {
                    if (p == 1) {
                        statusLayoutManager.showEmptyLayout();
                        if (hotAdapter != null) {
                            hotAdapter.getData().clear();
                            hotAdapter.notifyDataSetChanged();
                        }
                    } else {
                        hotAdapter.loadMoreComplete();
                        hotAdapter.loadMoreEnd();
                    }

                }

            }
        });

    }


    private void initHotRecycler(final List<XiTonfListResponse.DataBeanX.DataBean> list) {

        if (hotAdapter == null) {
            hotAdapter = new XiTongListAdapter(R.layout.adapter_xitonglist, list);
            LinearLayoutManager manager = new LinearLayoutManager(getActivity());
            manager.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerHotProduct.setLayoutManager(manager);
            recyclerHotProduct.setAdapter(hotAdapter);
        }
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
                huoqu();
            }
        }, recyclerHotProduct);


    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @SuppressWarnings("unchecked")
    @Override
    public void show(Object obj) {

    }


    @Override
    public void onRefresh() {
        p = 1;

        huoqu();

    }
}
