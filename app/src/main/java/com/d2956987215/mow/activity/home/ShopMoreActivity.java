package com.d2956987215.mow.activity.home;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.d2956987215.mow.R;
import com.d2956987215.mow.activity.BaseActivity;
import com.d2956987215.mow.adapter.ShopListAdapter;
import com.d2956987215.mow.rxjava.Request;
import com.d2956987215.mow.rxjava.Result;
import com.d2956987215.mow.rxjava.RxJavaUtil;
import com.d2956987215.mow.rxjava.response.ShopResponse;
import com.d2956987215.mow.util.GlideRoundTransform;
import com.d2956987215.mow.util.User;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class ShopMoreActivity extends BaseActivity {

    @BindView(R.id.iv_brand)
    ImageView mIvBrand;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_intro)
    TextView mTvIntro;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.rl_back)
    RelativeLayout mRlBack;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.scroll_view)
    ScrollView mScrollView;
    @BindView(R.id.ll_title)
    LinearLayout mLlTitle;

    private ShopResponse mShopResponse;
    private ShopListAdapter shopListAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String id = getIntent().getStringExtra("id");
        if (id != null) {
            huoqutitile(id);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mScrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View view, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                    int mHeight = mLlTitle.getHeight();//获取标题栏高度
                    if (scrollY <= 0) {//未滑动
                        mTvTitle.setTextColor(getResources().getColor(R.color.white));
                        mIvBack.setImageResource(R.mipmap.white_back);
                        mLlTitle.setBackgroundColor(Color.argb((int) 0, 255, 255, 255));
                    } else if (scrollY > 0 && scrollY <= mHeight) { //滑动过程中 并且在mHeight之内
                        float scale = (float) scrollY / mHeight;
                        float alpha = (255 * scale);
                        mTvTitle.setTextColor(getResources().getColor(R.color.text_deep));
                        mIvBack.setImageResource(R.mipmap.back_black);
                        mLlTitle.setBackgroundColor(Color.argb((int) alpha, 255, 255, 255));
                    } else {//超过mHeight
                        mTvTitle.setTextColor(getResources().getColor(R.color.text_deep));
                        mIvBack.setImageResource(R.mipmap.back_black);
                        mLlTitle.setBackgroundColor(Color.argb((int) 255, 255, 255, 255));
                    }
                }
            });
        }

    }


    @Override
    protected int getLayoutId() {
        return R.layout.act_shopmore;
    }

    @Override
    protected String title() {
        return "品牌专区";
    }

    @Override
    public void show(Object obj) {

    }

    private void huoqutitile(final String id) {
        Map<String, String> map = new HashMap<>();
        map.put("id", id);
        map.put("user_id", User.uid() + "");
        new Request<ShopResponse>().request(RxJavaUtil.xApi1().shopList(map), "数据", ShopMoreActivity.this, false, new Result<ShopResponse>() {
            @Override
            public void get(ShopResponse response) {
                if (response != null) {
                    mShopResponse = response;
                    mTvName.setText(response.getData().getFq_brand_name());
                    mTvIntro.setText(response.getData().getIntroduce());
                    Glide.with(ShopMoreActivity.this).load(response.getData().getBrand_logo()).transform(new GlideRoundTransform(ShopMoreActivity.this, 3)).into(mIvBrand);
                    if (response.getData().getItem() != null && response.getData().getItem().size() > 0) {
                        mRecyclerView.setLayoutManager(new LinearLayoutManager(ShopMoreActivity.this));
                        shopListAdapter = new ShopListAdapter(R.layout.adapter_hot_product, response.getData().getItem());
                        mRecyclerView.setAdapter(shopListAdapter);


                        shopListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                Intent intent = new Intent(getContext(), TaoBaoDetailActivity.class);
                                intent.putExtra("id", shopListAdapter.getItem(position).getItemid() + "");
                                intent.putExtra("quan_id", shopListAdapter.getItem(position).getQuan_id() + "");

                                startActivity(intent);
                            }
                        });
                    }

                }
            }
        });

    }

    @OnClick(R.id.tv_toshop)
    public void onViewClicked() {
        if (mShopResponse != null) {
            Intent intent = new Intent(ShopMoreActivity.this, GuideWebViewActivity.class);
            intent.putExtra("url", mShopResponse.getData().getShop_url());
            intent.putExtra("name", mShopResponse.getData().getFq_brand_name());
            startActivity(intent);
        }

    }
}
