package com.d2956987215.mow.frament;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.d2956987215.mow.R;
import com.d2956987215.mow.adapter.OrderAdapter;
import com.d2956987215.mow.rxjava.Request;
import com.d2956987215.mow.rxjava.Result;
import com.d2956987215.mow.rxjava.RxJavaUtil;
import com.d2956987215.mow.rxjava.response.MyOrderList;
import com.d2956987215.mow.util.User;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.bakumon.statuslayoutmanager.library.StatusLayoutManager;


public class MyOrderFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    RecyclerView recycler_hot_product;
    private StatusLayoutManager statusLayoutManager;
    private String year, month;
    private String type;
    private int p = 1;
    SwipeRefreshLayout refreshLayout;

    private String mKeyWord = "";


    public static MyOrderFragment newInstance(String type) {
        MyOrderFragment fragment = new MyOrderFragment();
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_order, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        type = getArguments().getString("type");

        recycler_hot_product = view.findViewById(R.id.recycler_hot_product);
        refreshLayout = view.findViewById(R.id.refresh_layout);
        statusLayoutManager = new StatusLayoutManager.Builder(refreshLayout).setDefaultLayoutsBackgroundColor(Color.WHITE).setEmptyLayout(initEmptyView()).build();
        huoqu();
        refreshLayout.setOnRefreshListener(this);
        showRefresh();
    }

    private OrderAdapter hotAdapter;

    private View initEmptyView() {
        View emptyView = LayoutInflater.from(getContext()).inflate(R.layout.custom_empty_view, null);
        ImageView iv = emptyView.findViewById(R.id.empty_retry_view);
        TextView tv = emptyView.findViewById(R.id.empty_view_tv);
        iv.setBackgroundResource(R.drawable.collect_img_default);
        tv.setText("当前页面暂无内容哦~");
        return emptyView;
    }

    public void huquyear(String datenew) {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM");
        Date date = null;
        try {
            date = formatter.parse(datenew);

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        // System.out.println(calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DAY_OF_MONTH));
        year = calendar.get(Calendar.YEAR) + "";
        month = calendar.get(Calendar.MONTH) + 1 + "";
        if (hotAdapter != null) {
            hotAdapter.getData().clear();
            hotAdapter.notifyDataSetChanged();
        }
        p = 1;
        huoqu();
    }


    private void showRefresh() {
        refreshLayout.post(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(true);
            }
        });
    }

    public void searchForKeyWord(String s) {
        p = 1;
        showRefresh();
        mKeyWord = s;
        huoqu();
    }

    public void huoqu() {
        Map<String, String> map = new HashMap<>();
        if (type != null) {
            map.put("type", type);
        }
        if (year != null && month != null) {
            map.put("year", year);
            map.put("month", month);
        }
        map.put("user_id", User.uid() + "");
        map.put("page", p + "");
        map.put("keyword", mKeyWord);
        new Request<MyOrderList>().request(RxJavaUtil.xApi().mydingdan(map, "Bearer " + User.token()), "获取我的订单列表", getContext(), false, new Result<MyOrderList>() {
            @Override
            public void get(MyOrderList response) {
                refreshLayout.setRefreshing(false);
                if (response.getData().getData() instanceof List && ((List) response.getData().getData()).size() > 0) {
                    statusLayoutManager.showSuccessLayout();
                    List<MyOrderList.DataBeanX.DataBean> list = response.getData().getData();
                    if (list != null && list.size() > 0) {
                        initHotRecycler1(list);
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
        });
    }

    private void initHotRecycler1(final List<MyOrderList.DataBeanX.DataBean> list) {
        if (hotAdapter == null) {
            hotAdapter = new OrderAdapter(R.layout.adapter_dingdan, null);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
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
        hotAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                Intent intent = new Intent(MyOrderListActivity.this, TaoBaoDetailActivity.class);
//                intent.putExtra("id", list.get(position).getId() + "");
//                startActivity(intent);
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
    public void onRefresh() {
        p = 1;
        huoqu();
    }
}
