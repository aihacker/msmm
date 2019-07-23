package com.d2956987215.mow.activity.product;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.d2956987215.mow.R;
import com.d2956987215.mow.activity.BaseActivity;
import com.d2956987215.mow.util.DialogUtil;
import com.d2956987215.mow.util.PreferencesUtils;
import com.d2956987215.mow.util.ToastUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class SearchShopActivity extends BaseActivity implements OnRefreshListener, OnLoadmoreListener {

    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.redian_list)
    RecyclerView redian_list;
    @BindView(R.id.rl_sousuo)
    RecyclerView rl_sousuo;

    @BindView(R.id.rl_search)
    RelativeLayout rl_search;
    @BindView(R.id.et_search)
    EditText et_search;
    @BindView(R.id.la_sousuo)
    LinearLayout la_sousuo;
    @BindView(R.id.tag_first)
    TagFlowLayout firstTag;


    private int p = 1;
    private String SmallTypeId;
    private List<String> pdata = new ArrayList<>();
    private List<String> historyName;
    private TagAdapter tagAdapter;
    private String type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setOnLoadmoreListener(this);
        firstTag.setMaxSelectCount(1);
        type="1";
        tagAdapter=new TagAdapter<String>(pdata) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                View view = LayoutInflater.from(SearchShopActivity.this).inflate(R.layout.adapter_search_hot, parent, false);
                TextView tv = view.findViewById(R.id.tv_item);
                tv.setText(s);
                return view;
            }
        };
        firstTag.setAdapter(tagAdapter);
        firstTag.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
              //  presenter.search(p,pdata.get(position).toString(),type);
                la_sousuo.setVisibility(View.GONE);
                firstTag.setVisibility(View.GONE);
                refreshLayout.setVisibility(View.VISIBLE);
                return false;


            }
        });
        historyName = PreferencesUtils.getList(this, "HISTORY_NAME");
        pdata.addAll(historyName);
        tagAdapter.notifyDataChanged();
        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s == null || s != null && "".equals(s.toString().trim())) {
                    la_sousuo.setVisibility(View.VISIBLE);
                    firstTag.setVisibility(View.VISIBLE);
                    refreshLayout.setVisibility(View.GONE);
                    rl_sousuo.setVisibility(View.GONE);
                    return;
                }
                rl_sousuo.setVisibility(View.VISIBLE);



                pdata.clear();
                historyName = PreferencesUtils.getList(SearchShopActivity.this, "HISTORY_NAME");
                pdata.addAll(historyName);
                tagAdapter.notifyDataChanged();


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

    @Override
    protected String title() {
        return getIntent().getStringExtra("name");

    }

    @Override
    protected boolean xianshiright() {
        return true;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_searchshop_list;
    }


    @SuppressWarnings("unchecked")
    @Override
    public void show(Object obj) {

    }



    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        p = 1;
        if (SmallTypeId != null) {
          //  presenter.shoplist(p, SmallTypeId);
        }

        refreshLayout.finishRefresh();

    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        p++;
        if (SmallTypeId != null) {
           // presenter.shoplist(p, SmallTypeId);
        }
        refreshlayout.finishLoadmore();

    }

    @OnClick({R.id.rb_order_1,R.id.rb_order_2,R.id.rl_search,R.id.search_empty})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rb_order_1:
                type="1";
                break;
            case R.id.rb_order_2:
                type="2";
                break;
            case R.id.rl_search:
                DialogUtil.hideInputMethod(this);
                String keyWord = et_search.getText().toString().trim();
                if (TextUtils.isEmpty(keyWord)) {
                    ToastUtil.show(this,"请输入搜索关键字");
                    return;
                }
                la_sousuo.setVisibility(View.GONE);
                firstTag.setVisibility(View.GONE);
                refreshLayout.setVisibility(View.VISIBLE);
                historyName.add(keyWord);
                //删除重复元素的方法
                delRepeatElements(historyName);
                PreferencesUtils.putList(this, "HISTORY_NAME", historyName);
                //presenter.search(p,keyWord,type);
                break;
            case R.id.search_empty:
                pdata.clear();
                tagAdapter.notifyDataChanged();
                PreferencesUtils.removeStrList(this, "HISTORY_NAME");

                break;

        }

    }
    public static void delRepeatElements(List list) {
        //1.定义个一个tempList用来存放临时元素
        List tempList = new ArrayList();

        //2.遍历原容器
        for(Iterator it = list.iterator(); it.hasNext();){
            Object obj = it.next();

            //判断tempList中是否包含有取出来的这个元素? 如果不包含,则存入tempList
            //这里需要重写Dog类的equals方法
            if(!(tempList.contains(obj))){    //如果不包含
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


}
