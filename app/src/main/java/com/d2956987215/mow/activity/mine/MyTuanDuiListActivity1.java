package com.d2956987215.mow.activity.mine;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.d2956987215.mow.R;
import com.d2956987215.mow.activity.BaseActivity;
import com.d2956987215.mow.adapter.MyTeamOtherAdapter;
import com.d2956987215.mow.rxjava.Request;
import com.d2956987215.mow.rxjava.Result;
import com.d2956987215.mow.rxjava.RxJavaUtil;
import com.d2956987215.mow.rxjava.response.BaseResponse;
import com.d2956987215.mow.rxjava.response.MyTeamResponse;
import com.d2956987215.mow.util.DisplayUtil;
import com.d2956987215.mow.util.StringUtil;
import com.d2956987215.mow.util.User;
import com.d2956987215.mow.widgets.SpinerPopWindow4;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import me.bakumon.statuslayoutmanager.library.StatusLayoutManager;

public class MyTuanDuiListActivity1 extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, MyTeamOtherAdapter.OtherClick {


    @BindView(R.id.recycler_hot_product)
    RecyclerView recycler_hot_product;
    @BindView(R.id.et_search)
    EditText et_search;
    @BindView(R.id.rl_title)
    RelativeLayout rl_title;
    @BindView(R.id.tv_submit)
    TextView tv_submit;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.iv_iv_tuandui)
    ImageView iv_iv_tuandui;
    @BindView(R.id.iv_iv_jiage)
    ImageView iv_iv_jiage;
    @BindView(R.id.tv_title)
    TextView tv_title;


    private int p = 1;
    private String type = "1";
    private StatusLayoutManager statusLayoutManager;
    private String roletype;
    private List<String> mNextList;
    private String mKeyWord = "";

    private boolean mFirst = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        statusLayoutManager = new StatusLayoutManager.Builder(refreshLayout).setDefaultLayoutsBackgroundColor(Color.WHITE).setEmptyLayout(inflate(R.layout.custom_empty_view))
                .build();
        roletype = getIntent().getStringExtra("roletype");
        huoqu();
        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if (s == null || s != null && "".equals(s.toString().trim())) {
//
//                    return;
//                }
                if (StringUtils.isEmpty(s.toString()))
                    mKeyWord = "";
                else
                    mKeyWord = s.toString();


            }

            @Override
            public void afterTextChanged(Editable editable) {


            }
        });

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
                    p = 1;
                    showRefresh();
                    huoqu();
                }
                return false;
            }
        });



        hotAdapter = new MyTeamOtherAdapter(R.layout.adapter_tuandui1, null);
        hotAdapter.setOtherClick(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recycler_hot_product.setLayoutManager(layoutManager);

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
        map.put("nextlist", tv_submit.getText().toString());
        map.put("roletype", roletype);
        map.put("keyword", mKeyWord);
        map.put("sort", order);
        map.put("user_id", User.uid() + "");
        map.put("page", p + "");
        new Request<MyTeamResponse>().request(RxJavaUtil.xApi().mytuandui(map, "Bearer " + User.token()), "我的团队", this, false, new Result<MyTeamResponse>() {


            @Override
            public void get(MyTeamResponse response) {
                refreshLayout.setRefreshing(false);
                refreshLayout.setRefreshing(false);
                KeyboardUtils.hideSoftInput(MyTuanDuiListActivity1.this);
                initTopRight(response.getData().getNextlist());
                tv_title.setText(response.getData().getTitle());
                if (response.getData().getList() instanceof List && ((List) response.getData().getList()).size() > 0) {
                    refreshLayout.setRefreshing(false);
                    statusLayoutManager.showSuccessLayout();
                    List<MyTeamResponse.DataBean.ListBean> list = response.getData().getList();
                    if (list != null && list.size() > 0) {
                        initHotRecycler1(list);
                    }
                } else {
                    if (p == 1) {
                        statusLayoutManager.showEmptyLayout();
                        if (hotAdapter != null) {
                            refreshLayout.setRefreshing(false);
                            hotAdapter.getData().clear();
                            recycler_hot_product.setAdapter(new MyTeamOtherAdapter(R.layout.adapter_tuandui1, null));
                            hotAdapter.notifyDataSetChanged();
//                            hotAdapter.loadMoreComplete();
//                            hotAdapter.loadMoreEnd();
//                            hotAdapter.notifyDataSetChanged();
//                            hotAdapter.setEmptyView(inflate(R.layout.custom_empty_view));
//                            hotAdapter.isUseEmpty(true);

                        }
                    } else {
                        hotAdapter.loadMoreComplete();
                        hotAdapter.loadMoreEnd();
                    }

                }
            }
        });

    }

    private void initTopRight(List<String> nextlist) {
        mNextList = nextlist;
        if (mFirst)
            tv_submit.setText(nextlist.get(0));


    }

    private String order = "desc";
    private SpinerPopWindow4 spinerPopWindow4;

    @OnClick({R.id.rb_zuixin, R.id.rb_tuandui, R.id.rb_yongjin, R.id.tv_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rb_zuixin:
                iv_iv_tuandui.setBackground(getResources().getDrawable(R.mipmap.list_comp_default));
                if (StringUtil.isFastClick()) {
                    p = 1;
                    refreshAdater();
                    type = "1";
                    iv_iv_jiage.setBackground(getResources().getDrawable(R.mipmap.list_comp_default));
                    huoqu();
                }
                break;
            case R.id.rb_tuandui:
                iv_iv_jiage.setBackground(getResources().getDrawable(R.mipmap.list_comp_default));

                if (StringUtil.isFastClick()) {
                    p = 1;
                    refreshAdater();
                    iv_iv_tuandui.setBackground(getResources().getDrawable(R.mipmap.list_comp_default));
                    if (order.equals("desc")) {
                        iv_iv_tuandui.setBackground(getResources().getDrawable(R.mipmap.list_comp_top));
                        order = "asc";
                    } else {
                        iv_iv_tuandui.setBackground(getResources().getDrawable(R.mipmap.list_comp_down));
                        order = "desc";
                    }
                    type = "2";
                    huoqu();
                }
                break;
            case R.id.rb_yongjin:
                iv_iv_tuandui.setBackground(getResources().getDrawable(R.mipmap.list_comp_default));
                if (StringUtil.isFastClick()) {
                    p = 1;
                    refreshAdater();
                    iv_iv_jiage.setBackground(getResources().getDrawable(R.mipmap.list_comp_default));

                    if (order.equals("desc")) {
                        iv_iv_jiage.setBackground(getResources().getDrawable(R.mipmap.list_comp_top));
                        order = "asc";
                    } else {
                        iv_iv_jiage.setBackground(getResources().getDrawable(R.mipmap.list_comp_down));

                        order = "desc";
                    }
                    type = "3";
                    huoqu();
                }
                break;
            case R.id.tv_submit:
                spinerPopWindow4 = new SpinerPopWindow4(mNextList, this, new SpinerPopWindow4.CallBack() {
                    @Override
                    public void xuanze(String leixing, String name) {
                        p = 1;
                        showRefresh();
                        mFirst = false;
                        tv_submit.setText(name);
                        refreshAdater();
                        huoqu();
                    }
                });
                spinerPopWindow4.setWidth(DisplayUtil.dip2px(this, 130));
                spinerPopWindow4.showAsDropDown(tv_submit, Gravity.CENTER, -40);

                break;

        }
    }


    private void refreshAdater() {
        if (hotAdapter != null) {
            hotAdapter.getData().clear();
            hotAdapter.notifyDataSetChanged();
        }
//        p = 1;
//        showRefresh();
    }

    //    private List<MyTeamResponse.DataBean.ListBean> goodlist = new ArrayList<>();
    private MyTeamOtherAdapter hotAdapter;


    private void initHotRecycler1(final List<MyTeamResponse.DataBean.ListBean> list) {

        if (p == 1) {
            recycler_hot_product.setAdapter(hotAdapter);
            refreshLayout.setRefreshing(false);
            hotAdapter.setNewData(list);
        } else {
            hotAdapter.addData(list);
        }

        hotAdapter.loadMoreComplete();
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
//        return getIntent().getStringExtra("name");
        return "";

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_tuanduilist1;
    }

    @Override
    public void show(Object obj) {

    }


    @Override
    public void onRefresh() {

        p = 1;
        huoqu();
    }

    @Override
    public void otherClick(String s, final int position) {
        Map<String, String> map = new HashMap<>();
        map.put("user_id", User.uid() + "");
        map.put("ms_id", s);
        new Request<BaseResponse>().request(RxJavaUtil.xApi().openMs(map, "Bearer " + User.token()), "开通团队成功", this, false, new Result<BaseResponse>() {
            @Override
            public void get(BaseResponse response) {
                ToastUtils.showShort("开通成功");
                hotAdapter.getItem(position).getDeadline().setIs_kt("1");
                hotAdapter.notifyDataSetChanged();
            }
        });

    }


}
