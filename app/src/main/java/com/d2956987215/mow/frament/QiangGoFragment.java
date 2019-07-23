package com.d2956987215.mow.frament;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.d2956987215.mow.R;
import com.d2956987215.mow.activity.home.QiangGouListActivity;
import com.d2956987215.mow.activity.home.TaoBaoDetailActivity;
import com.d2956987215.mow.adapter.TimeListHotAdapter;
import com.d2956987215.mow.rxjava.Request;
import com.d2956987215.mow.rxjava.Result;
import com.d2956987215.mow.rxjava.RxJavaUtil;
import com.d2956987215.mow.rxjava.response.QiagGouListResponse;
import com.d2956987215.mow.util.AnimatorEffect;
import com.d2956987215.mow.util.StringUtil;
import com.d2956987215.mow.util.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import me.bakumon.statuslayoutmanager.library.StatusLayoutManager;

/**
 * A simple {@link Fragment} subclass.
 */
public class QiangGoFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.recycler_hot_product)
    RecyclerView recyclerHotProduct;
    private TimeListHotAdapter hotAdapter;
    private Unbinder unbinder;
    private StatusLayoutManager statusLayoutManager;
    private String time_id;
    private LayoutInflater inflater;
    private boolean isStart;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;
    private int p = 1;

    public QiangGoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_qianggou, container, false);
//        statusLayoutManager = new StatusLayoutManager.Builder(recyclerHotProduct).setEmptyLayout(inflate(R.layout.custom_empty_view))
//                .build();
        unbinder = ButterKnife.bind(this, view);
        time_id = getArguments().getString("time_id");
        isStart = getArguments().getBoolean("isStart",false);
        if(!StringUtil.isBlank(time_id)){
            huoqu(time_id);
        }
            refreshLayout.setOnRefreshListener(this);
            refreshLayout.post(new Runnable() {
                @Override
                public void run() {
                    if (refreshLayout != null) {

                    refreshLayout.setRefreshing(true);
                }        }

        });
        return view;
    }

    private View inflate(@LayoutRes int resource) {
        if (inflater == null) {
            inflater = LayoutInflater.from(getActivity());
        }
        return inflater.inflate(resource, null);
    }

    private void initHotRecycler1(final List<QiagGouListResponse.DataBean> list) {
        if (hotAdapter == null) {
            hotAdapter = new TimeListHotAdapter(R.layout.adapter_qianggou, list);
            hotAdapter.setStart(isStart);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
            recyclerHotProduct.setLayoutManager(layoutManager);
//        recyclerHotProduct.setLayoutManager(layoutManager);
            recyclerHotProduct.setHasFixedSize(true);
            recyclerHotProduct.setNestedScrollingEnabled(false);
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


                if (!isStart) {
                    showDialogLayout();
                } else {

                    Intent intent = new Intent(getContext(), TaoBaoDetailActivity.class);
                    intent.putExtra("id", hotAdapter.getItem(position).getGoodsID() + "");
                    intent.putExtra("quan_id", hotAdapter.getItem(position).getQuan_id() + "");

                    startActivity(intent);
                }
            }
        });

        hotAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                p++;
                huoqu(time_id);
            }
        }, recyclerHotProduct);


    }


    /**
     *
     */
    private void showDialogLayout() {

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_rush, null);
        TextView tvOk = view.findViewById(R.id.tv_ok);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setView(view);
        final AlertDialog alertDialog = builder.show();
        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });


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

    private void huoqu(String time_id) {
        Map<String, String> map = new HashMap<>();
        map.put("time_id", time_id);
        if (User.uid() == -1) {
            map.put("user_id", "0");
        } else {
            map.put("user_id", User.uid() + "");
        }
        map.put("page", p + "");
        new Request<QiagGouListResponse>().request(RxJavaUtil.xApi().qianggoulist(map), "抢购列表", getActivity(), false, new Result<QiagGouListResponse>() {
            @Override
            public void get(QiagGouListResponse response) {
                refreshLayout.setRefreshing(false);
                if (response.getData() instanceof List && ((List) response.getData()).size() > 0) {
//                    statusLayoutManager.showSuccessLayout();
                    List<QiagGouListResponse.DataBean> list = response.getData();
                    if (list != null && list.size() > 0) {
                        initHotRecycler1(list);
                    }

                } else {
                    if (hotAdapter != null){
                        hotAdapter.loadMoreComplete();
                        hotAdapter.loadMoreEnd();
                    }
//                    statusLayoutManager.showEmptyLayout();

                }

            }
        });

    }


    @Override
    public void onRefresh() {
        huoqu(time_id);
    }
}
