package com.d2956987215.mow.activity.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.d2956987215.mow.R;
import com.d2956987215.mow.activity.BaseActivity;
import com.d2956987215.mow.rxjava.Request;
import com.d2956987215.mow.rxjava.Result;
import com.d2956987215.mow.rxjava.RxJavaUtil;
import com.d2956987215.mow.rxjava.response.ErJiListResponse;
import com.d2956987215.mow.rxjava.response.SelfSupportResponse;
import com.d2956987215.mow.util.CacheUtil;
import com.d2956987215.mow.util.TBKUtils;
import com.d2956987215.mow.util.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class SelfSupportActivity extends BaseActivity {


    @BindView(R.id.rv)
    RecyclerView mRv;

//
//    @BindView(R.id.tv_self_support_one)
//    TextView mTvSelfSupportOne;
//
//
//    @BindView(R.id.tv_self_support_two)
//    TextView mTvSelfSupportTwo;
    private List<SelfSupportResponse.DataBean> mList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    protected void init() {
        super.init();

        huoqu();
    }

    private void goShop( String shopId) {
//        String[] pid = pids.split("_");
        TBKUtils.showShopDetailPageForUrl(SelfSupportActivity.this, shopId, "taobao");

    }


    private void huoqu() {
        Map<String, String> map = new HashMap<>();

        if (User.uid() > 0) {
            map.put("user_id", User.uid() + "");
        }
        new Request<SelfSupportResponse>().request(RxJavaUtil.xApi().selfSupport(map, "Bearer " + User.token()), "买手自营", this, false, new Result<SelfSupportResponse>() {


            @Override
            public void get(SelfSupportResponse response) {
                if (response.getData() instanceof List && ((List) response.getData()).size() > 0) {
                    mList = response.getData();
                    if (mList != null && mList.size() > 0) {
                        mRv.setLayoutManager(new LinearLayoutManager(SelfSupportActivity.this));
                        mRv.setAdapter(new MyAdapter(mList));
                    }

                }

            }

        });

    }

    public class MyAdapter extends BaseQuickAdapter<SelfSupportResponse.DataBean, BaseViewHolder> {

        public MyAdapter(@Nullable List<SelfSupportResponse.DataBean> data) {
            super(R.layout.item_self_support, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, final SelfSupportResponse.DataBean item) {
            helper.setText(R.id.tv_text, item.getTitle());
            helper.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goShop(item.getSeller_id());
                }
            });
        }

    }


    @Override
    protected String title() {
        return "买手自营";
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_self_support;
    }

    @Override
    public void show(Object obj) {

    }
}
