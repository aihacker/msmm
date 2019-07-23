package com.d2956987215.mow.activity.mine;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.LayoutRes;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.KeyboardUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.d2956987215.mow.R;
import com.d2956987215.mow.activity.BaseActivity;
import com.d2956987215.mow.activity.home.TaoBaoDetailActivity;
import com.d2956987215.mow.adapter.AlertSearchAdapter;
import com.d2956987215.mow.adapter.ErJiHotAdapter;
import com.d2956987215.mow.constant.Const;
import com.d2956987215.mow.eventbus.StopRefreshLoadMoreAnim;
import com.d2956987215.mow.presenter.FenLeiPresenter;
import com.d2956987215.mow.rxjava.response.ErJiListResponse;
import com.d2956987215.mow.rxjava.response.HotKetResponse;
import com.d2956987215.mow.util.DialogUtil;
import com.d2956987215.mow.util.PreferencesUtils;
import com.d2956987215.mow.util.StringUtil;
import com.d2956987215.mow.util.ToastUtil;
import com.d2956987215.mow.widgets.MyRadioGroup;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import me.bakumon.statuslayoutmanager.library.StatusLayoutManager;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SearchSingleActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, Handler.Callback {

    public static final String KEYWORD = "keyword";
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.redian_list)
    RecyclerView redian_list;

    @BindView(R.id.rl_search)
    RelativeLayout rl_search;
    @BindView(R.id.et_search)
    EditText et_search;

    @BindView(R.id.iv_iv_jiage)
    ImageView ivIvJiage;
    @BindView(R.id.mrg_sort_type)
    MyRadioGroup mrgSortType;
    @BindView(R.id.rb_order_1)
    RadioButton rbOrder1;
    @BindView(R.id.rb_order_2)
    RadioButton rbOrder2;
    @BindView(R.id.rb_order_3)
    RadioButton rbOrder3;
    @BindView(R.id.rb_order_4)
    RadioButton rbOrder4;
    @BindView(R.id.iv_show_quan)
    ImageView iv_show_quan;
    @BindView(R.id.rl_quan)
    View rl_quan;


    @BindView(R.id.iv_clear)
    ImageView mIvClear;


    private FenLeiPresenter presenter;
    private ErJiHotAdapter sAdapter;
    private int p = 1;
    private List<String> pdata = new ArrayList<>();
    private List<String> hotKeyData = new ArrayList<>();
    private List<String> historyName;
    public  String type = "1";
    private String keyWord;

    private StatusLayoutManager statusLayoutManager;
    private String order = "";
    private String sort = "desc";
    private AnimationDrawable animationDrawable;
    private String hascoupon = "0";

    private void showRefresh() {
        refreshLayout.post(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(true);
            }
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        KeyboardUtils.hideSoftInput(this);
        refreshLayout.setOnRefreshListener(this);
        showRefresh();
        mHandler = new Handler(this);
        EventBus.getDefault().register(this);
        presenter = new FenLeiPresenter(this);
//        type = getIntent().getStringExtra("type");

        type= Const.tjptype;
        if (StringUtil.isBlank(type)) {
            type = "1";
        }
        if (type.equals("1")) {
            rbOrder1.setChecked(true);
            rbOrder2.setChecked(false);
            rbOrder3.setChecked(false);
            rbOrder4.setChecked(false);
        } else if (type.equals("2")) {
            rbOrder1.setChecked(false);
            rbOrder2.setChecked(true);
            rbOrder3.setChecked(false);
            rbOrder4.setChecked(false);
        } else if (type.equals("3")) {
            rbOrder1.setChecked(false);
            rbOrder2.setChecked(false);
            rbOrder3.setChecked(true);
            rbOrder4.setChecked(false);
        } else if (type.equals("4")) {
            rbOrder1.setChecked(false);
            rbOrder2.setChecked(false);
            rbOrder3.setChecked(false);
            rbOrder4.setChecked(true);
        } else {
            rbOrder1.setChecked(true);
            rbOrder2.setChecked(false);
            rbOrder3.setChecked(false);
            rbOrder4.setChecked(false);
        }

        statusLayoutManager = new StatusLayoutManager.Builder(redian_list).setDefaultLayoutsBackgroundColor(Color.WHITE).setEmptyLayout(initEmptyView())
                .build();

        historyName = PreferencesUtils.getList(this, "HISTORY_NAME");
        pdata.addAll(historyName);
        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s == null || s != null && "".equals(s.toString().trim())) {
//                    refreshLayout.setVisibility(View.GONE);
                    mIvClear.setVisibility(View.GONE);
                    if (popupWindow != null && popupWindow.isShowing())
                        popupWindow.dismiss();
                    return;
                } else {
                    mIvClear.setVisibility(View.VISIBLE);
                    et_search.requestFocus();
//                    et_search.setSelection(et_search.getText().length());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

                if (s.length() > 0) {
                    if (keyWord != null && keyWord.equals(et_search.getText().toString())) {
                        KeyboardUtils.hideSoftInput(SearchSingleActivity.this);
                        return;
                    }
                    if (!getIntent().getStringExtra("keyword").equals(et_search.getText().toString()))
                        searchAlertKey(s.toString());
                    else if (popupWindow != null && popupWindow.isShowing())
                        popupWindow.dismiss();
                }
            }
        });
        keyWord = getIntent().getStringExtra(KEYWORD);
        if (!StringUtil.isBlank(keyWord)) {
            presenter.search(p, keyWord, type, order, sort, hascoupon);
            et_search.setText(keyWord);
            refreshLayout.setVisibility(View.VISIBLE);
        } else {
            presenter.searchHotKey();
        }
        if (getIntent().getStringExtra("content") != null && getIntent().getStringExtra("content").length() > 0) {
            et_search.setText(getIntent().getStringExtra("content"));
            pdata.clear();
            historyName = PreferencesUtils.getList(SearchSingleActivity.this, "HISTORY_NAME");
            pdata.addAll(historyName);
            DialogUtil.hideInputMethod(this);
            keyWord = et_search.getText().toString().trim();
            if (TextUtils.isEmpty(keyWord)) {
                ToastUtil.show(this, "请输入搜索关键字");
                return;
            }
            refreshLayout.setVisibility(View.VISIBLE);
            historyName.add(keyWord);
            //删除重复元素的方法
            delRepeatElements(historyName);
            PreferencesUtils.putList(this, "HISTORY_NAME", historyName);
            presenter.search(p, keyWord, type, order, sort, hascoupon);


        }


//        KeyboardUtils.toggleSoftInput();

    }

    private LayoutInflater inflater;

    private View inflate(@LayoutRes int resource) {
        if (inflater == null) {
            inflater = LayoutInflater.from(this);
        }
        return inflater.inflate(resource, null);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_single_searchshop_list;
    }


    @SuppressWarnings("unchecked")
    @Override
    public void show(Object obj) {
        refreshLayout.setRefreshing(false);
        if (obj instanceof ErJiListResponse.DataBeanX) {
            refreshLayout.setRefreshing(false);
            if (((ErJiListResponse.DataBeanX) obj).getData() != null && ((ErJiListResponse.DataBeanX) obj).getData().size() > 0) {
                statusLayoutManager.showSuccessLayout();
                List<ErJiListResponse.DataBeanX.DataBean> list = ((ErJiListResponse.DataBeanX) obj).getData();
                initright(list);
                mrgSortType.setVisibility(View.VISIBLE);

                refreshLayout.setVisibility(View.VISIBLE);
                rl_quan.setVisibility(View.VISIBLE);
            } else {
                if (p == 1) {
                    if (sAdapter != null) {
                        sAdapter.getData().clear();
                        sAdapter.notifyDataSetChanged();
                    }

                    mrgSortType.setVisibility(View.GONE);
                    rl_quan.setVisibility(View.VISIBLE);
                    statusLayoutManager.showEmptyLayout();

                } else {
                    sAdapter.loadMoreComplete();
                    sAdapter.loadMoreEnd();
                }
            }
        }
        if (obj instanceof HotKetResponse) {
            if (((HotKetResponse) obj).getData().size() > 0) {
                hotKeyData.clear();
                hotKeyData.addAll(((HotKetResponse) obj).getData());
            } else {
                hotKeyData.clear();
            }
        }
    }


    private void initright(final List<ErJiListResponse.DataBeanX.DataBean> list) {
        if (sAdapter == null) {
            sAdapter = new ErJiHotAdapter(R.layout.adapter_hot_product, list);
            sAdapter.setPreLoadNumber(3);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            redian_list.setLayoutManager(layoutManager);
            redian_list.setAdapter(sAdapter);
        }
        if (p == 1) {
            refreshLayout.setRefreshing(false);
            sAdapter.setNewData(list);
            sAdapter.loadMoreComplete();
        } else {
            sAdapter.addData(list);
            sAdapter.loadMoreComplete();
        }
        sAdapter.notifyDataSetChanged();
        sAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(SearchSingleActivity.this, TaoBaoDetailActivity.class);
                intent.putExtra("id", sAdapter.getItem(position).getItemid() + "");
                intent.putExtra("quan_id", sAdapter.getItem(position).getQuan_id() + "");
                intent.putExtra("type", type);
                startActivity(intent);
            }
        });

        sAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                p++;
                if (keyWord != null) {
                    presenter.search(p, keyWord, type, order, sort, hascoupon);
                }
            }
        }, redian_list);


    }


    @Override
    public void onRefresh() {
        p = 1;
        if (keyWord != null) {
            presenter.search(p, keyWord, type, order, sort, hascoupon);
        }
    }


    @OnClick({R.id.iv_clear, R.id.rb_order_1, R.id.rl_backone, R.id.rb_order_2, R.id.rb_order_3, R.id.rb_order_4, R.id.rl_search, R.id.rb_zong, R.id.rb_yongjin, R.id.rb_jiage, R.id.rb_xiaoshou, R.id.iv_show_quan})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_backone:
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘
                finish();
                break;
            case R.id.iv_clear:
                et_search.setText("");
                if (popupWindow != null)
                    popupWindow.dismiss();
                break;
            case R.id.rb_order_1:
                type = "1";
                p=1;
              Const.tjptype="1";
                showRefresh();
                keyWord = et_search.getText().toString().trim();
                if (TextUtils.isEmpty(keyWord)) {
                    ToastUtil.show(this, "请输入搜索关键字");
                    return;
                }
                presenter.search(p, keyWord, type, order, sort, hascoupon);

                break;
            case R.id.rb_order_2:
                type = "2";
                p=1;
                Const.tjptype="2";
                showRefresh();
                keyWord = et_search.getText().toString().trim();
                if (TextUtils.isEmpty(keyWord)) {
                    ToastUtil.show(this, "请输入搜索关键字");
                    return;
                }
                presenter.search(p, keyWord, type, order, sort, hascoupon);

                break;
            case R.id.rb_order_3:
                type = "3";
                p=1;
                Const.tjptype="3";
                showRefresh();
                keyWord = et_search.getText().toString().trim();
                if (TextUtils.isEmpty(keyWord)) {
                    ToastUtil.show(this, "请输入搜索关键字");
                    return;
                }
                presenter.search(p, keyWord, type, order, sort, hascoupon);

                break;
            case R.id.rb_order_4:
                type = "4";
                p=1;
                Const.tjptype="4";
                showRefresh();

                keyWord = et_search.getText().toString().trim();
                if (TextUtils.isEmpty(keyWord)) {
                    ToastUtil.show(this, "请输入搜索关键字");
                    return;
                }
                presenter.search(p, keyWord, type, order, sort, hascoupon);

                break;
            case R.id.rl_search:
                if (popupWindow != null)
                    popupWindow.dismiss();
                showRefresh();
                KeyboardUtils.hideSoftInput(SearchSingleActivity.this);
//                DialogUtil.hideInputMethod(this);
                keyWord = et_search.getText().toString().trim();
                if (TextUtils.isEmpty(keyWord)) {
                    ToastUtil.show(this, "请输入搜索关键字");
                    return;
                }
                refreshLayout.setVisibility(View.VISIBLE);
                historyName.add(keyWord);
                //删除重复元素的方法
                delRepeatElements(historyName);
                PreferencesUtils.putList(this, "HISTORY_NAME", historyName);
                presenter.search(p, keyWord, type, order, sort, hascoupon);

                break;

            case R.id.rb_zong:
                p = 1;
                showRefresh();
                ivIvJiage.setBackground(getResources().getDrawable(R.mipmap.list_comp_default));
                order = "";
                presenter.search(p, keyWord, type, order, sort, hascoupon);
                break;
            case R.id.rb_yongjin:
                p = 1;
                showRefresh();
                ivIvJiage.setBackground(getResources().getDrawable(R.mipmap.list_comp_default));
                sort = "desc";
                order = "tkmoney";
                presenter.search(p, keyWord, type, order, sort, hascoupon);
                break;
            case R.id.rb_jiage:
                p = 1;
                showRefresh();
                order = "itemendprice";
                if (sort.equals("desc")) {
                    ivIvJiage.setBackground(getResources().getDrawable(R.mipmap.list_comp_top));
                    sort = "asc";
                    presenter.search(p, keyWord, type, order, sort, hascoupon);
                } else {
                    ivIvJiage.setBackground(getResources().getDrawable(R.mipmap.list_comp_down));
                    sort = "desc";
                    presenter.search(p, keyWord, type, order, sort, hascoupon);
                }

                break;
            case R.id.rb_xiaoshou:
                p = 1;
                showRefresh();
                ivIvJiage.setBackground(getResources().getDrawable(R.mipmap.list_comp_default));
                sort = "desc";
                order = "itemsale";
                presenter.search(p, keyWord, type, order, sort, hascoupon);
                break;
            case R.id.iv_show_quan:
                p = 1;
                showRefresh();
                if (TextUtils.equals("1", hascoupon)) {
                    iv_show_quan.setImageResource(R.mipmap.news_close);
                    hascoupon = "0";
                } else {
                    hascoupon = "1";
                    iv_show_quan.setImageResource(R.mipmap.news_open);

                }
                sort = "desc";
                presenter.search(p, keyWord, type, order, sort, hascoupon);
                break;
            default:
                break;

        }

    }

    public static void delRepeatElements(List list) {
        //1.定义个一个tempList用来存放临时元素
        List tempList = new ArrayList();

        //2.遍历原容器
        for (Iterator it = list.iterator(); it.hasNext(); ) {
            Object obj = it.next();

            //判断tempList中是否包含有取出来的这个元素? 如果不包含,则存入tempList
            //这里需要重写Dog类的equals方法
            if (!(tempList.contains(obj))) {    //如果不包含
                tempList.add(obj);    //添加到tempList容器。
            }
        }
        //4，遍历结束后，tempList容器中存储的就是唯一性的元素。可以返回给调用者
        //return tempList;

        /*5.如果调用者需要将这些唯一性的元素保留到原容器中，只要将原容器清空，
            将临时容器中的元素添加到原容器中即可。*/

        //清空原来容器
        list.clear();

        //把临时容器中的元素添加到原容器中。
        list.addAll(tempList);
    }

    private View initEmptyView() {
        View emptyView = inflate(R.layout.custom_empty_view);
        ImageView iv = emptyView.findViewById(R.id.empty_retry_view);
        TextView tv = emptyView.findViewById(R.id.empty_view_tv);
        iv.setBackgroundResource(R.drawable.collect_img_default);
        tv.setText("你还没有搜藏商品，快去逛逛吧");
        return emptyView;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void stopRefreshLoadMoreAnim(StopRefreshLoadMoreAnim stopRefreshLoadMoreAnim) {
//        if (animFresh != null && animationDrawable == null) {
//            animationDrawable = (AnimationDrawable) animFresh.getDrawable();
//        }
//        if (animationDrawable != null) {
//            animationDrawable.stop();
//        }
        if (refreshLayout != null) {
//            refreshLayout.finishLoadmore();
            refreshLayout.setRefreshing(false);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private OkHttpClient okHttpClient;
    private Handler mHandler;

    private void searchAlertKey(String alertKey) {


        if (okHttpClient == null) {
            okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(5, TimeUnit.SECONDS)
                    .writeTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(10, TimeUnit.SECONDS)
                    .build();
        }

        String url = "https://suggest.taobao.com/sug?code=utf-8&callback=jsonp&q=" + alertKey;
        Request request = new Request.Builder()
                .get()
                .url(url)
                .build();
        Log.e("url", url);
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mHandler.sendEmptyMessage(2);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String result = response.body().string();
                    Message message = Message.obtain();
                    message.what = 1;
                    message.obj = result;
                    mHandler.sendMessage(message);
                } else {
                    mHandler.sendEmptyMessage(2);
                }

            }
        });
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case 1:
                parseAlertKeyResult((String) msg.obj);
                break;
            case 2:

                break;
            default:
                break;
        }
        return true;
    }

    private void parseAlertKeyResult(String result) {
        List<String> dataStr = new ArrayList<>();
        if (result != null && result.length() > 2) {
            int startIndex = result.indexOf("(");
            int endIndex = result.lastIndexOf(")");
            String json = result.substring(startIndex + 1, endIndex);
            JSONObject parse = null;
            try {
                parse = new JSONObject(json);
                JSONArray resultArray = parse.getJSONArray("result");
                int len = resultArray.length();
                for (int i = 0; i < len; i++) {
                    JSONArray array = (JSONArray) resultArray.get(i);
                    if (array.length() > 0) {
                        String string = (String) array.get(0);
                        dataStr.add(string);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        showAlertSearchPop(dataStr);
    }

    private PopupWindow popupWindow;
    List<String> data = new ArrayList<>();
    AlertSearchAdapter adapter;

    private void showAlertSearchPop(final List<String> list) {
        data.clear();
        data.addAll(list);
        if (popupWindow == null) {
            View view = LayoutInflater.from(this).inflate(R.layout.pop_alert_search, null, false);
            popupWindow = new PopupWindow();
            popupWindow.setContentView(view);

            //设置键盘和popup不遮挡
            popupWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
            popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
            popupWindow.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
            popupWindow.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
            RecyclerView recyclerView = view.findViewById(R.id.search_recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            adapter = new AlertSearchAdapter(data);
            adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    showRefresh();
                    keyWord = data.get(position);
                    popupWindow.dismiss();
                    DialogUtil.hideInputMethod(SearchSingleActivity.this);
                    et_search.setText(keyWord);
                    presenter.search(p, keyWord, type, order, sort, hascoupon);
                    pdata.clear();
                    historyName = PreferencesUtils.getList(SearchSingleActivity.this, "HISTORY_NAME");
                    pdata.addAll(historyName);
                }
            });
            recyclerView.setAdapter(adapter);
            recyclerView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    KeyboardUtils.hideSoftInput(SearchSingleActivity.this);
                    return false;
                }
            });

        }
        adapter.notifyDataSetChanged();
//        popupWindow.showAtLocation(et_search, Gravity.TOP, 0, 10);
        if(popupWindow!=null&&et_search!=null&& !this.isFinishing()){
            popupWindow.showAsDropDown(et_search);
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (popupWindow != null && popupWindow.isShowing())
            popupWindow.dismiss();
        return super.onTouchEvent(event);

    }
}
