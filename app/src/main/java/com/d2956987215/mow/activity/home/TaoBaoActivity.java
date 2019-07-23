package com.d2956987215.mow.activity.home;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.d2956987215.mow.R;
import com.d2956987215.mow.activity.BaseActivity;
import com.d2956987215.mow.activity.mine.SearchActivity;
import com.d2956987215.mow.frament.HomeErJiFragment;
import com.d2956987215.mow.rxjava.Request;
import com.d2956987215.mow.rxjava.Result;
import com.d2956987215.mow.rxjava.RxJavaUtil;
import com.d2956987215.mow.rxjava.response.ShouTitleResponse;
import com.d2956987215.mow.util.MyFragmentPagerAdapter;
import com.d2956987215.mow.util.User;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class TaoBaoActivity extends BaseActivity {

    @BindView(R.id.tv_sou)
    TextView tv_sou;
    @BindView(R.id.magicIndicator)
    MagicIndicator magicIndicator;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.iv_logo)
    ImageView iv_logo;
    private String type = "1";
    private List<Fragment> fragments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = getIntent().getStringExtra("type");
        if (type.equals("1")) {
            iv_logo.setBackground(getResources().getDrawable(R.mipmap.logo_tianmao));
        } else if (type.equals("2")) {
            iv_logo.setBackground(getResources().getDrawable(R.mipmap.logo_jingdong));
        } else if (type.equals("3")) {
            iv_logo.setBackground(getResources().getDrawable(R.mipmap.logo_pdd));
        } else {
            iv_logo.setBackground(getResources().getDrawable(R.mipmap.logo_tianmao));
        }

        huoqutitile();

    }


    List<ShouTitleResponse.DataBean.CateBean> titlelist = new ArrayList<>();

    private void huoqutitile() {
        Map<String, String> map = new HashMap<>();
        if (User.uid() > 0) {
            map.put("user_id", User.uid() + "");
        }
        new Request<ShouTitleResponse>().request(RxJavaUtil.xApi().gettitlelist(map), "首页头数据", this, false, new Result<ShouTitleResponse>() {
            @Override
            public void get(ShouTitleResponse response) {
                ShouTitleResponse.DataBean.CateBean catabean = new ShouTitleResponse.DataBean.CateBean();
                catabean.setId(1);
                catabean.setName("精选");
                titlelist.add(catabean);
                titlelist.addAll(response.getData().getCate());
                addFragment();
                initMagicIndicator();

            }
        });

    }

    private void addFragment() {
        for (int i = 0; i < titlelist.size(); i++) {
            HomeErJiFragment hometwoframent = new HomeErJiFragment();
            hometwoframent.setType(titlelist.get(i), type);
            fragments.add(hometwoframent);

        }
        viewPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(), fragments));
    }

    @OnClick({R.id.tv_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_search:
                Intent intent = new Intent(this, SearchActivity.class);
                intent.putExtra("type", type);
                startActivity(intent);
                break;
        }
    }

    private List<String> mDataList = new ArrayList<>();

    private void initMagicIndicator() {
        for (int i = 0; i < titlelist.size(); i++) {
            mDataList.add(titlelist.get(i).getName());
        }
        CommonNavigator commonNavigator = new CommonNavigator(this);
        magicIndicator.setForegroundGravity(Gravity.CENTER);
        commonNavigator.setScrollPivotX(0.35f);
        commonNavigator.setAdjustMode(false);
//        //设置平均分
//        commonNavigator.setAdjustMode(true);
        // commonNavigator.setScrollPivotX(0.25f);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return mDataList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new SimplePagerTitleView(context);
                simplePagerTitleView.setText(mDataList.get(index));
                simplePagerTitleView.setNormalColor(Color.GRAY);
                simplePagerTitleView.setSelectedColor(Color.RED);
                simplePagerTitleView.setTextSize(16);
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        viewPager.setCurrentItem(index);

                    }
                });
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setMode(LinePagerIndicator.MODE_WRAP_CONTENT);
                indicator.setYOffset(UIUtil.dip2px(context, 5));
                indicator.setLineHeight(UIUtil.dip2px(context, 2));
                indicator.setLineWidth(UIUtil.dip2px(context, 35));
                indicator.setColors(Color.RED);
                return indicator;
            }
        });

//        viewPager.setOffscreenPageLimit(mDataList.size());
        magicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(magicIndicator, viewPager);
    }

    @Override
    protected String title() {
        return getIntent().getStringExtra("name");

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_taobao;
    }

    @Override
    public void show(Object obj) {

    }
}
