package com.d2956987215.mow.activity.mine;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.d2956987215.mow.R;
import com.d2956987215.mow.activity.BaseActivity;
import com.d2956987215.mow.adapter.NewGuideAdapter;
import com.d2956987215.mow.rxjava.Request;
import com.d2956987215.mow.rxjava.Result;
import com.d2956987215.mow.rxjava.RxJavaUtil;
import com.d2956987215.mow.rxjava.response.GuiListResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.bakumon.statuslayoutmanager.library.StatusLayoutManager;

public class NewGuideListActivity extends BaseActivity {


    @BindView(R.id.recycler_hot_product)
    RecyclerView recycler_hot_product;


    private int p = 1;
    private String type = "1";
    private StatusLayoutManager statusLayoutManager;
    Unbinder unbinder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        unbinder = ButterKnife.bind(this);
        statusLayoutManager = new StatusLayoutManager.Builder(recycler_hot_product).setDefaultLayoutsBackgroundColor(Color.WHITE).setEmptyLayout(inflate(R.layout.custom_empty_view))
                .build();

        huoqu();

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
        map.put("cate_id", getIntent().getStringExtra("id"));
        map.put("is_top", "1");
        map.put("page", p + "");
        new Request<GuiListResponse>().request(RxJavaUtil.xApi().xinshouzhidao(map), "获取新手指导列表", this, false, new Result<GuiListResponse>() {


            @Override
            public void get(GuiListResponse response) {
                if (response.getData() instanceof List && ((List) response.getData()).size() > 0) {
                    statusLayoutManager.showSuccessLayout();
                    List<GuiListResponse.DataBean> list = response.getData();
                    if (list != null && list.size() > 0) {
                        initHotRecycler1(list);
                    }

                } else {
                    if (p == 1) {
                        if (goodlist != null) {
                            goodlist.clear();
                        }
                        hotAdapter.notifyDataSetChanged();
                        statusLayoutManager.showEmptyLayout();
                    }

                }

            }
        });

    }


    private List<GuiListResponse.DataBean> goodlist = new ArrayList<>();
    private NewGuideAdapter hotAdapter;

    private void initHotRecycler1(final List<GuiListResponse.DataBean> list) {
        if (hotAdapter == null) {
            hotAdapter = new NewGuideAdapter(R.layout.adapter_newgui, goodlist);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            recycler_hot_product.setLayoutManager(layoutManager);
            recycler_hot_product.setLayoutManager(layoutManager);
            recycler_hot_product.setHasFixedSize(true);
            recycler_hot_product.setNestedScrollingEnabled(false);
            recycler_hot_product.setAdapter(hotAdapter);
        }
        if (p == 1) {
            goodlist.clear();
        }
        goodlist.addAll(list);
        hotAdapter.notifyDataSetChanged();
        hotAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(NewGuideListActivity.this, GuideDetailActivity.class);
                intent.putExtra("title", list.get(position).getArticle_title());
                intent.putExtra("id", list.get(position).getId() + "");
                startActivity(intent);


            }
        });
    }

    @Override
    protected String title() {
        return "新手指导";

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_newguidelist;
    }

    @Override
    public void show(Object obj) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
