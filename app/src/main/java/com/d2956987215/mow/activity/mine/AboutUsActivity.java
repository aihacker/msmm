package com.d2956987215.mow.activity.mine;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.d2956987215.mow.R;
import com.d2956987215.mow.activity.BaseActivity;
import com.d2956987215.mow.util.CacheUtil;
import com.tencent.bugly.beta.Beta;

import butterknife.BindView;

public class AboutUsActivity extends BaseActivity {

    @BindView(R.id.tv_version)
    TextView tvVersion;

    @BindView(R.id.rl_version_update)
    RelativeLayout mRl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            // tvCacheSize.setText(CacheUtil.getTotalCacheSize(this));
            //获取版本号
            tvVersion.setText(CacheUtil.getVersion(this));

        } catch (Exception e) {
            e.printStackTrace();
        }
        mRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Beta.checkUpgrade(true,false);
            }
        });
    }



    @Override
    protected String title() {
        return getString(R.string.about_us);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_about_us;
    }

    @Override
    public void show(Object obj) {

    }
}
