package com.d2956987215.mow.activity.mine;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RadioButton;

import com.d2956987215.mow.activity.BaseActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

import com.d2956987215.mow.R;
import com.d2956987215.mow.frament.MessageSystemFragment;
import com.d2956987215.mow.frament.MessageBonusFragment;
import com.d2956987215.mow.widgets.MyRadioGroup;

public class XiaoXiActivity extends BaseActivity {

    @BindView(R.id.main_qiuzhi)
    RadioButton main_qiuzhi;
    @BindView(R.id.main_zhaopin)
    RadioButton main_zhaopin;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.main_rg)
    MyRadioGroup main_rg;


    private ArrayList<Fragment> lists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lists = new ArrayList<>();
        MessageSystemFragment homeSystemframent = new MessageSystemFragment();
        MessageBonusFragment homebonusframent = new MessageBonusFragment();
        lists.add(homebonusframent);
        lists.add(homeSystemframent);

        initData();
        viewPager.setCurrentItem(0);
    }

    @Override
    protected String title() {
        return "我的消息";
    }

    public void initData() {

        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return lists.get(position);
            }

            @Override
            public int getCount() {
                //返回viewpager的数量
                return lists.size();
            }
        });

        //viewpager滑动监听
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //radiogroup选中对应的radiobutton
                if (position == 0) {

                    viewPager.setCurrentItem(0);
                    main_qiuzhi.setChecked(true);
                } else {

                    viewPager.setCurrentItem(1);
                    main_zhaopin.setChecked(true);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @OnClick({R.id.main_qiuzhi, R.id.main_zhaopin})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.main_qiuzhi:

                viewPager.setCurrentItem(0);

                break;
            case R.id.main_zhaopin:

                viewPager.setCurrentItem(1);
                break;


        }
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_xiaoxi;
    }


    @Override
    public void show(Object obj) {

    }
}
