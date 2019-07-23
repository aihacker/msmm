package com.d2956987215.mow.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.d2956987215.mow.rxjava.Request;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import com.d2956987215.mow.R;
import com.d2956987215.mow.activity.BaseActivity;
import com.d2956987215.mow.adapter.GuideDetailAdapter;
import com.d2956987215.mow.adapter.NewGuideAdapter;
import com.d2956987215.mow.rxjava.Request;
import com.d2956987215.mow.rxjava.Result;
import com.d2956987215.mow.rxjava.RxJavaUtil;
import com.d2956987215.mow.rxjava.response.GuiDetailResponse;
import com.d2956987215.mow.rxjava.response.GuiListResponse;

public class GuideDetailActivity extends BaseActivity {
    @BindView(R.id.recycler_hot_product)
    RecyclerView recycler_hot_product;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_time)
    TextView tv_time;
    @BindView(R.id.tv_liulan)
    TextView tv_liulan;
    @BindView(R.id.tv_dianzan)
    TextView tv_dianzan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        huoqu();

    }

    private void huoqu() {
        Map<String, String> map = new HashMap<>();
        map.put("id", getIntent().getStringExtra("id"));
        new Request<GuiDetailResponse>().request(RxJavaUtil.xApi().xinshoudetail(map), "获取新手指导列表", this, false, new Result<GuiDetailResponse>() {
            @Override
            public void get(GuiDetailResponse response) {
                tv_title.setText(response.getData().getAinfo().getArticle_title());
                tv_time.setText(response.getData().getAinfo().getArticle_date());
                tv_liulan.setText(response.getData().getAinfo().getArticle_readnum());
                tv_dianzan.setText(response.getData().getAinfo().getArticle_likenum());
                List<GuiDetailResponse.DataBean.TinfoBean> list = response.getData().getTinfo();
                if (list != null && list.size() > 0) {
                    initHotRecycler1(list);
                }
            }
        });

    }

    private GuideDetailAdapter hotAdapter;

    private void initHotRecycler1(final List<GuiDetailResponse.DataBean.TinfoBean> list) {

        hotAdapter = new GuideDetailAdapter(R.layout.adapter_newgui, list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recycler_hot_product.setLayoutManager(layoutManager);
        recycler_hot_product.setLayoutManager(layoutManager);
        recycler_hot_product.setHasFixedSize(true);
        recycler_hot_product.setNestedScrollingEnabled(false);
        recycler_hot_product.setAdapter(hotAdapter);
        hotAdapter.notifyDataSetChanged();
        hotAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(GuideDetailActivity.this, GuideDetailActivity.class);
                intent.putExtra("title", list.get(position).getArticle_title());
                intent.putExtra("id", list.get(position).getId() + "");
                startActivity(intent);


            }
        });
    }


    @Override
    protected String title() {
        return getIntent().getStringExtra("title");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_guidedetail;
    }

    @Override
    public void show(Object obj) {

    }
}
