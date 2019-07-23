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
import com.d2956987215.mow.rxjava.response.XinShouResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import me.bakumon.statuslayoutmanager.library.StatusLayoutManager;

public class NewGuideActivity extends BaseActivity {


    @BindView(R.id.recycler_hot_product)
    RecyclerView recycler_hot_product;


    private int p = 1;
    private String type = "1";
    private StatusLayoutManager statusLayoutManager;
    private List<XinShouResponse.DataBean> xinshoulist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        statusLayoutManager = new StatusLayoutManager.Builder(recycler_hot_product).setDefaultLayoutsBackgroundColor(Color.WHITE).setEmptyLayout(inflate(R.layout.custom_empty_view))
                .build();
        huoquzhinan();
        huoqu();

    }

    private void huoquzhinan() {
        new Request<XinShouResponse>().request(RxJavaUtil.xApi().getzhinanlist(), "新手指南", this, false, new Result<XinShouResponse>() {
            @Override
            public void get(XinShouResponse response) {
                if (response.getData() != null && response.getData().size() > 0) {
                    xinshoulist = response.getData();
                }


            }
        });


    }

    @OnClick({R.id.iv_new, R.id.la_pingtai, R.id.rl_bikan, R.id.rl_jingyan, R.id.rl_jinjie, R.id.rl_shipin})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_new:
                if (xinshoulist != null) {
                    Intent intent = new Intent(this, NewGuideListActivity.class);
                    intent.putExtra("id", xinshoulist.get(0).getId() + "");
                    startActivity(intent);

                }

                break;

            case R.id.la_pingtai:
                if (xinshoulist != null) {
                    Intent intent = new Intent(this, NewGuideListActivity.class);
                    intent.putExtra("id", xinshoulist.get(1).getId() + "");
                    startActivity(intent);

                }
                break;
            case R.id.rl_bikan:
                if (xinshoulist != null) {
                    Intent intent = new Intent(this, NewGuideListActivity.class);
                    intent.putExtra("id", xinshoulist.get(2).getId() + "");
                    startActivity(intent);

                }
                break;
            case R.id.rl_jingyan:
                if (xinshoulist != null) {
                    Intent intent = new Intent(this, NewGuideListActivity.class);
                    intent.putExtra("id", xinshoulist.get(3).getId() + "");
                    startActivity(intent);

                }

                break;
            case R.id.rl_jinjie:
                if (xinshoulist != null) {
                    Intent intent = new Intent(this, NewGuideListActivity.class);
                    intent.putExtra("id", xinshoulist.get(4).getId() + "");
                    startActivity(intent);

                }
                break;
            case R.id.rl_shipin:
                if (xinshoulist != null) {
                    Intent intent = new Intent(this, NewGuideListActivity.class);
                    intent.putExtra("id", xinshoulist.get(5).getId() + "");
                    startActivity(intent);

                }
                break;
        }


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
        map.put("is_top", "0");
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
                Intent intent = new Intent(NewGuideActivity.this, GuideDetailActivity.class);
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
        return R.layout.activity_newguide;
    }

    @Override
    public void show(Object obj) {

    }
}
