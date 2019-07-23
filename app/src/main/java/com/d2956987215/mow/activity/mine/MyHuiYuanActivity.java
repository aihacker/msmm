package com.d2956987215.mow.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.d2956987215.mow.R;
import com.d2956987215.mow.activity.BaseActivity;
import com.d2956987215.mow.adapter.UpdateIdAdapter;
import com.d2956987215.mow.rxjava.Request;
import com.d2956987215.mow.rxjava.Result;
import com.d2956987215.mow.rxjava.RxJavaUtil;
import com.d2956987215.mow.rxjava.response.BaseResponse;
import com.d2956987215.mow.rxjava.response.UpdateIdResponse;
import com.d2956987215.mow.util.StringUtil;
import com.d2956987215.mow.util.ToastUtil;
import com.d2956987215.mow.util.User;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;


public class MyHuiYuanActivity extends BaseActivity {

/*
    @BindView(R.id.la_maishou)
    LinearLayout la_maishou;
    @BindView(R.id.la_banzhang)
    LinearLayout la_banzhang;
    @BindView(R.id.la_zongdai)
    LinearLayout la_zongdai;
    @BindView(R.id.tv_maishou_no)
    TextView tv_maishou_no;
    @BindView(R.id.tv_banzhang_no)
    TextView tv_banzhang_no;
    @BindView(R.id.tv_zongdai_no)
    TextView tv_zongdai_no;
*/


    @BindView(R.id.rv)
    RecyclerView mRv;

    UpdateIdAdapter mAdapter;


    private String roletype;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
/*
        roletype = getIntent().getStringExtra("roletype");
        if (!StringUtil.isBlank(roletype) && roletype.equals("1")) {
            la_maishou.setVisibility(View.VISIBLE);
            tv_maishou_no.setText("No:" + getIntent().getStringExtra("xuehao"));
            la_banzhang.setVisibility(View.GONE);
            la_zongdai.setVisibility(View.GONE);
        } else if (!StringUtil.isBlank(roletype) && roletype.equals("2")) {
            la_maishou.setVisibility(View.GONE);
            la_banzhang.setVisibility(View.VISIBLE);
            tv_banzhang_no.setText("No:" + getIntent().getStringExtra("xuehao"));
            la_zongdai.setVisibility(View.GONE);
        } else if (!StringUtil.isBlank(roletype) && roletype.equals("3")) {
            la_maishou.setVisibility(View.GONE);
            la_banzhang.setVisibility(View.GONE);
            la_zongdai.setVisibility(View.VISIBLE);
            tv_zongdai_no.setText("No:" + getIntent().getStringExtra("xuehao"));
        } else {
            la_maishou.setVisibility(View.VISIBLE);
            tv_maishou_no.setText("No:" + getIntent().getStringExtra("xuehao"));
            la_banzhang.setVisibility(View.GONE);
            la_zongdai.setVisibility(View.GONE);
        }
*/


        Map<String, String> map = new HashMap<>();
        map.put("user_id", User.uid() + "");
        new Request<UpdateIdResponse>().request(RxJavaUtil.xApi().updateIdList(map, "Bearer " + User.token()), "升级列表", this, false, new Result<UpdateIdResponse>() {
            @Override
            public void get(UpdateIdResponse response) {
                mAdapter = new UpdateIdAdapter(response.getData());
                mRv.setLayoutManager(new LinearLayoutManager(MyHuiYuanActivity.this));
                mRv.setAdapter(mAdapter);
                mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        startActivity(new Intent(MyHuiYuanActivity.this, MyHuiYuanDetailActivity.class).putExtra("roletype", mAdapter.getItem(position).getRoletype()))
                        ;
                    }
                });
            }
        });


    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_huiyuan;
    }

    @Override
    public void show(Object obj) {

    }

/*
    @OnClick({R.id.la_maishou, R.id.la_banzhang, R.id.la_zongdai})
    public void onViewClicked(View view) {
        Intent intent = new Intent(this, MyHuiYuanDetailActivity.class);
        switch (view.getId()) {

            case R.id.la_maishou:
                break;
            case R.id.la_banzhang:
            case R.id.la_zongdai:

                intent.putExtra("roletype", roletype);
//                intent.putExtra("roletype", "0");
                startActivity(intent);
                finish();
                break;
        }

    }
*/


    @Override
    protected String title() {
        return "加入会员";
    }
}
