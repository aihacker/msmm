package com.d2956987215.mow.activity.mine;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.d2956987215.mow.R;
import com.d2956987215.mow.activity.BaseActivity;
import com.d2956987215.mow.adapter.HomeHotAdapter;
import com.d2956987215.mow.rxjava.Request;
import com.d2956987215.mow.rxjava.Result;
import com.d2956987215.mow.rxjava.RxJavaUtil;
import com.d2956987215.mow.rxjava.response.HomeListResponse;
import com.d2956987215.mow.util.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class Act_TaoBaoSearchTicket extends BaseActivity {
    @BindView(R.id.tv_nums)
    TextView mTvNums;
    @BindView(R.id.tv_saveMoney)
    TextView mTvSaveMoney;
    @BindView(R.id.tv_getMoney)
    TextView mTvGetMoney;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @Override
    protected int getLayoutId() {
        return R.layout.act_taobao_searchticket;
    }

    @Override
    public void show(Object obj) {

    }

    @Override
    protected String title() {
        if (!TextUtils.isEmpty(getIntent().getStringExtra("name"))) {
            return getIntent().getStringExtra("name");
        } else {
            return "搜索优惠券";
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String idJson = getIntent().getStringExtra("ids");
        if (!TextUtils.isEmpty(idJson)) {
            List<String> ids = JSON.parseArray(idJson, String.class);
            if (ids != null && ids.size() > 0) {
                String tmp = "";
                for (int i = 0; i < ids.size(); i++) {
                    tmp += ids.get(i) + ",";
                }
                getData(tmp.substring(0, tmp.length() - 1));
            }
        }

    }

    private void getData(String ids) {
        Map<String, String> map = new HashMap<>();
        map.put("id", ids);
        if (User.uid() > 0) {
            map.put("user_id", User.uid() + "");
        }
        new Request<HomeListResponse>().request(RxJavaUtil.xApi1().searchTicket(map), "搜索优惠券列表", this, false, new Result<HomeListResponse>() {

            @Override
            public void get(final HomeListResponse response) {
                if (response != null) {
                    if (response.getData().getData() != null && response.getData().getData().size() > 0) {
                        mRecyclerView.setLayoutManager(new LinearLayoutManager(Act_TaoBaoSearchTicket.this));
                        mRecyclerView.setAdapter(new HomeHotAdapter(R.layout.adapter_hot_product, response.getData().getData()));

                        mTvNums.setText(response.getData().getData().size() + "");
                        float saveMoney = 0;
                        float getMoney = 0;
                        for (int i = 0; i < response.getData().getData().size(); i++) {
                            String monry1 = response.getData().getData().get(i).getCouponmoney();
                            String monry2 = response.getData().getData().get(i).getShare_money();
                            if (!TextUtils.isEmpty(monry1)) {
                                saveMoney += Float.parseFloat(monry1);
                                getMoney += Float.parseFloat(monry2);
                            }
                        }
                        if (saveMoney == 0) {
                            mTvSaveMoney.setText("￥0");
                        } else {
                            mTvSaveMoney.setText("￥" + saveMoney);
                        }
                        if (getMoney == 0) {
                            mTvGetMoney.setText("￥0");
                        } else {
                            mTvGetMoney.setText("￥" + getMoney);
                        }


                    }
                }
            }
        });
    }
}
