package com.d2956987215.mow.activity.mine;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import com.d2956987215.mow.R;
import com.d2956987215.mow.activity.BaseActivity;
import com.d2956987215.mow.dialog.IncomeDialog;
import com.d2956987215.mow.rxjava.Request;
import com.d2956987215.mow.rxjava.Result;
import com.d2956987215.mow.rxjava.RxJavaUtil;
import com.d2956987215.mow.rxjava.response.MyShouYiResponse;
import com.d2956987215.mow.util.User;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;


public class MyShouYiActivity extends BaseActivity {
    @BindView(R.id.tv_leiji)
    TextView tv_leiji;
    @BindView(R.id.tv_thisyu)
    TextView tv_thisyu;
    @BindView(R.id.tv_lastyu)
    TextView tv_lastyu;
    @BindView(R.id.tv_thiyugu)
    TextView tv_thiyugu;
    @BindView(R.id.tv_last_yugu)
    TextView tv_last_yugu;
    @BindView(R.id.tv_xiadan)
    TextView tv_xiadan;
    @BindView(R.id.rb_today)
    RadioButton rb_today;
    @BindView(R.id.rb_zuori)
    RadioButton rb_zuori;
    @BindView(R.id.tv_queding)
    TextView tv_queding;
    @BindView(R.id.tv_count)
    TextView tv_count;
    @BindView(R.id.tv_yue)
    TextView tv_yue;

    @BindView(R.id.textView3)
    TextView textView3;
    @BindView(R.id.textView4)
    TextView textView4;
    @BindView(R.id.textView5)
    TextView textView5;
    @BindView(R.id.textView6)
    TextView textView6;
    @BindView(R.id.textView7)
    TextView textView7;
    @BindView(R.id.textView8)
    TextView textView8;
    @BindView(R.id.textView9)
    TextView textView9;
    private MyShouYiResponse.DataBean dataBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View decorView = getWindow().getDecorView();

            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN

                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;

            decorView.setSystemUiVisibility(option);

            getWindow().setStatusBarColor(Color.TRANSPARENT);

        }
        super.onCreate(savedInstanceState);
        huoqu();
    }

    private void huoqu() {
        //S
        Map<String, String> map = new HashMap<>();
        map.put("user_id", User.uid() + "");
        new Request<MyShouYiResponse>().request(RxJavaUtil.xApi().myshouyi(map, "Bearer " + User.token()), "添加班长", this, false, new Result<MyShouYiResponse>() {
            @Override
            public void get(MyShouYiResponse response) {
                dataBean = response.getData();
                tv_leiji.setText("￥" + response.getData().getAlljs());
                tv_thisyu.setText("￥" + response.getData().getThis_month_jsprice());
                tv_lastyu.setText("￥" + response.getData().getLast_month_jsprice());
                tv_thiyugu.setText("￥" + response.getData().getThis_month_price());
                tv_last_yugu.setText("￥" + response.getData().getLast_month_price());
                tv_xiadan.setText("￥" + response.getData().getToday_info().getPrice());
                tv_queding.setText("￥" + response.getData().getToday_info().getJsprice());
                tv_count.setText(response.getData().getToday_info().getCount());
                tv_yue.setText("账户余额（元）:￥" + response.getData().getCredit2());
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_myshouyi;
    }

    @Override
    public void show(Object obj) {

    }

    @OnClick({R.id.la_zhangdan, R.id.la_dingdan, R.id.rb_today, R.id.rb_zuori
            , R.id.textView3, R.id.textView4, R.id.textView5, R.id.textView6, R.id.textView7,
            R.id.textView8, R.id.textView9})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rb_today:
                if (dataBean != null && dataBean.getToday_info() != null) {
                    tv_xiadan.setText("￥" + dataBean.getToday_info().getPrice());
                    tv_queding.setText("￥" + dataBean.getToday_info().getJsprice());
                    tv_count.setText(dataBean.getToday_info().getCount());
                }

                break;
            case R.id.rb_zuori:
                if (dataBean != null && dataBean.getYesterday_info() != null) {
                    tv_xiadan.setText("￥" + dataBean.getYesterday_info().getPrice());
                    tv_queding.setText("￥" + dataBean.getYesterday_info().getJsprice());
                    tv_count.setText(dataBean.getYesterday_info().getCount());
                }

                break;
            case R.id.la_zhangdan:
                Intent intent = new Intent(this, AccoutActivity.class);
                startActivity(intent);
                break;
            case R.id.la_dingdan:
                intent = new Intent(this, MyOrderListActivity.class);
                startActivity(intent);
                break;

            case R.id.textView3:
                shoDoalog(textView3.getText().toString(),"本月内结算的订单收益，每月25日结算后，将转入到余额中");
                break;
            case R.id.textView4:
                shoDoalog(textView4.getText().toString(),"上月内结算的订单收益，每月25日结算后，将转入到余额中");
                break;
            case R.id.textView5:
                shoDoalog(textView5.getText().toString(),"本月内创建的所有订单预估收益");
                break;
            case R.id.textView6:
                shoDoalog(textView6.getText().toString(),"上月内创建的所有订单预估收益");
                break;
            case R.id.textView7:
                shoDoalog(textView7.getText().toString(),"今日创建的所有订单预估收益");
                break;
            case R.id.textView8:
                shoDoalog(textView8.getText().toString(),"今日内结算的订单收益");
                break;
            case R.id.textView9:
                shoDoalog(textView9.getText().toString(),"昨日内有效订单数量，不包括失效订单");
                break;
        }

    }

    private void shoDoalog(String title,String name) {
        new IncomeDialog(this, title,name).show();
    }

    @Override
    protected String title() {
        return "我的收益";
    }
}
