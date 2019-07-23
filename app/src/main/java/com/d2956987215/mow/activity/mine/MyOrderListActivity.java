package com.d2956987215.mow.activity.mine;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.d2956987215.mow.R;
import com.d2956987215.mow.activity.BaseActivity;
import com.d2956987215.mow.activity.home.TaoBaoDetailActivity;
import com.d2956987215.mow.adapter.OrderAdapter;
import com.d2956987215.mow.frament.MyOrderFragment;
import com.d2956987215.mow.rxjava.Request;
import com.d2956987215.mow.rxjava.Result;
import com.d2956987215.mow.rxjava.RxJavaUtil;
import com.d2956987215.mow.rxjava.response.MyOrderList;
import com.d2956987215.mow.util.DateUtil;
import com.d2956987215.mow.util.DisplayUtil;
import com.d2956987215.mow.util.StringUtil;
import com.d2956987215.mow.util.User;
import com.d2956987215.mow.widgets.SpinerPopWindow4;
import com.d2956987215.mow.widgets.SpinerPopWindowOrderFilter;
import com.d2956987215.mow.widgets.datepicker.GetDateListener;
import com.d2956987215.mow.widgets.datepicker.PopWheelDatepicker;

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

import butterknife.BindView;
import butterknife.OnClick;
import me.bakumon.statuslayoutmanager.library.StatusLayoutManager;

public class MyOrderListActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.et_search)
    EditText et_search;
    @BindView(R.id.tv_benyue)
    TextView tv_benyue;

    PopWheelDatepicker popWheelDatepicker;

    MyOrderFragment allOrderFragment, paidOrderFragment, balanceOrderFragment, invalidOrderFragment;
    int index = 0;

    @BindView(R.id.recycler_hot_product)
    RecyclerView recycler_hot_product;
    private StatusLayoutManager statusLayoutManager;
    private String year, month;
    private String type = "";
    private int p = 1;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;

    private String mKeyWord = "";
    List<MyOrderList.DataBeanX.DataBean> emptyList = new ArrayList<>();
    private List<MyOrderList.DataBeanX.TypeBean> mTypeList;
    private SpinerPopWindowOrderFilter spinerPopWindowOrder;
    @BindView(R.id.tv_submit)
    TextView tv_submit;
    private String mTypeId = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Calendar cal = Calendar.getInstance();
        year = cal.get(Calendar.YEAR) + "";
        month = cal.get(Calendar.MONTH) + 1 + "";
        statusLayoutManager = new StatusLayoutManager.Builder(refreshLayout).setDefaultLayoutsBackgroundColor(Color.WHITE).setEmptyLayout(initEmptyView()).build();
        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if (s == null || s != null && "".equals(s.toString().trim())) {
////                    allOrderFragment.searchForKeyWord(s.toString());
//
//                    return;
//                }
                if (StringUtils.isEmpty(s.toString()))
                    mKeyWord = "";
                else
                    mKeyWord = s.toString();
//                searchForKeyWord(s.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
//        showRefresh();

        et_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent event) {
                //当actionId == XX_SEND 或者 XX_DONE时都触发
                //或者event.getKeyCode == ENTER 且 event.getAction == ACTION_DOWN时也触发
                //注意，这是一定要判断event != null。因为在某些输入法上会返回null。
                if (actionId == EditorInfo.IME_ACTION_SEND
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || (event != null && KeyEvent.KEYCODE_ENTER == event.getKeyCode() && KeyEvent.ACTION_DOWN == event.getAction())) {
                    //处理事件
//                    if (!StringUtils.isEmpty(mKeyWord))
                    searchForKeyWord(mKeyWord);
                }
                return false;
            }
        });

        hotAdapter = new OrderAdapter(R.layout.adapter_dingdan, null);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recycler_hot_product.setLayoutManager(layoutManager);
        refreshLayout.setOnRefreshListener(this);
        huoqu();
    }


    @OnClick({R.id.rb_quanbu, R.id.rb_yifukuan, R.id.rb_yijiesuan, R.id.rb_yishixiao, R.id.tv_benyue, R.id.tv_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rb_quanbu:

                if (StringUtil.isFastClick()) {

                    clearAdapter();
                    type = "";
                    showRefresh();
                } else {
//                    ToastUtils.showShort("请不要点击太快");
                }
                onRefresh();
                break;
            case R.id.rb_yifukuan:
                if (StringUtil.isFastClick()) {

                    clearAdapter();
                    type = "1";
                    showRefresh();
                    onRefresh();
                } else {
//                    ToastUtils.showShort("请不要点击太快");
                }
                break;
            case R.id.rb_yijiesuan:
                if (StringUtil.isFastClick()) {

                    clearAdapter();
                    type = "2";
                    showRefresh();
                    onRefresh();
                } else {
//                    ToastUtils.showShort("请不要点击太快");
                }
                break;
            case R.id.rb_yishixiao:
                if (StringUtil.isFastClick()) {
                    clearAdapter();
                    type = "3";
                    showRefresh();
                    onRefresh();
                } else {
//                    ToastUtils.showShort("请不要点击太快");
                }
                break;
            case R.id.tv_benyue:
                popWheelDatepicker = new PopWheelDatepicker(this, new Date(), "yyyy-MM", DateUtil.getCurrYear() - 2, 2050, "");
                popWheelDatepicker.setGetDateListener(new GetDateListener() {
                    @Override
                    public void getdate(String date) {
                        showRefresh();
                        tv_benyue.setText(date);
                        huquyear(date);
                    }
                });
                popWheelDatepicker.showAtLocation(getWindow().getDecorView());
                break;

            case R.id.tv_submit:
                spinerPopWindowOrder = new SpinerPopWindowOrderFilter(mTypeList, this, new SpinerPopWindowOrderFilter.CallBack() {
                    @Override
                    public void xuanze(String leixing, String name, String id) {
                        p = 1;
                        mTypeId = id;
                        showRefresh();
                        huoqu();
                        tv_submit.setText(name);
                    }
                });
                spinerPopWindowOrder.setWidth(DisplayUtil.dip2px(this, 130));
                spinerPopWindowOrder.showAsDropDown(tv_submit, -20, -30);

                break;
            default:
                break;
        }
    }

    private void clearAdapter() {
        if (hotAdapter != null) {
            hotAdapter.getData().clear();
            hotAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_orderlist;
    }

    @Override
    public void show(Object obj) {

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
//        showRefresh();
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
        map.put("typeid", mTypeId);
        new Request<MyOrderList>().request(RxJavaUtil.xApi().mydingdan(map, "Bearer " + User.token()), "获取我的订单列表", getContext(), false, new Result<MyOrderList>() {
            @Override
            public void get(MyOrderList response) {
                KeyboardUtils.hideSoftInput(MyOrderListActivity.this);
                refreshLayout.setRefreshing(false);
                if(response.getData()!=null){
                    mTypeList = response.getData().getTypes();
                }

                if (response.getData().getData() instanceof List && ((List) response.getData().getData()).size() > 0) {

                    statusLayoutManager.showSuccessLayout();
                    List<MyOrderList.DataBeanX.DataBean> list = response.getData().getData();
                    if (list != null && list.size() > 0) {
                        initHotRecycler1(list);
                    }
                } else {
                    if (p == 1) {
                        statusLayoutManager.showEmptyLayout();
                    } else {
                        hotAdapter.loadMoreComplete();
                        hotAdapter.loadMoreEnd();
                    }
                }
            }

            @Override
            public void onServerError(String errDesc) {
                super.onServerError(errDesc);

                if (hotAdapter != null) {
                    hotAdapter.getData().clear();
                    hotAdapter.setNewData(emptyList);
                    hotAdapter.notifyDataSetChanged();
                }
                refreshLayout.setRefreshing(false);
                statusLayoutManager.showEmptyLayout();
            }


        });
    }

    private void initHotRecycler1(final List<MyOrderList.DataBeanX.DataBean> list) {
        if (p == 1) {
            recycler_hot_product.setAdapter(hotAdapter);
            refreshLayout.setRefreshing(false);
            hotAdapter.setNewData(list);

        } else {
            hotAdapter.addData(list);
            hotAdapter.loadMoreComplete();
        }
        hotAdapter.notifyDataSetChanged();
        hotAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                try {

                    Intent intent = new Intent(MyOrderListActivity.this, TaoBaoDetailActivity.class);
                    intent.putExtra("id", hotAdapter.getItem(position).getNumid() + "");
                    intent.putExtra("type", "1");
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }

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

    @Override
    public void onPause() {
        super.onPause();
        refreshLayout.setRefreshing(false);
    }
}
