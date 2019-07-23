package com.d2956987215.mow;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.d2956987215.mow.activity.BaseActivity;
import com.d2956987215.mow.activity.MainActivity;
import com.d2956987215.mow.rxjava.response.GuideResponse;

import butterknife.BindView;
import butterknife.OnClick;

public class Act_Welcome extends BaseActivity {
    @BindView(R.id.splash_image)
    ImageView mSplashImage;
    @BindView(R.id.tv_tiaoguo)
    TextView mTvTiaoguo;
    private GuideResponse.DataBean.ListBean databean;
    private String json;

    private int time = 6;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_guide;
    }

    @Override
    public void show(Object obj) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        json = getIntent().getStringExtra("databean");
        if (!TextUtils.isEmpty(json)) {
            databean = JSON.parseObject(json, GuideResponse.DataBean.ListBean.class);
            Glide.with(Act_Welcome.this).load(databean.getPicUrl()).into(mSplashImage);
            mHandler.sendEmptyMessage(0);
        } else {
            Intent intent = new Intent(Act_Welcome.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

    }

    @OnClick({R.id.splash_image, R.id.tv_tiaoguo})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.splash_image:
                if (databean != null) {
                    Intent intent = new Intent(Act_Welcome.this, MainActivity.class);
                    intent.putExtra("json", json);
                    startActivity(intent);
                    finish();

                }
                break;
            case R.id.tv_tiaoguo:
                Intent intent = new Intent(Act_Welcome.this, MainActivity.class);
                startActivity(intent);
                finish();

                break;

        }

    }


    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            time--;
            if (time == 0) {
                Intent intent = new Intent(Act_Welcome.this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                mTvTiaoguo.setText(time + "s跳过广告");
                mHandler.sendEmptyMessageDelayed(0, 1000);
            }
        }
    };

    @Override
    protected void onDestroy() {
        if (mHandler != null) {
            mHandler.removeMessages(0);
        }
        super.onDestroy();
    }
}
