package com.d2956987215.mow.activity.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.d2956987215.mow.R;
import com.d2956987215.mow.activity.BaseActivity;
import com.d2956987215.mow.activity.MainActivity;
import com.d2956987215.mow.util.SP;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class UserGuideActivity extends BaseActivity {
    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    private List<View> viewList;

    @Override
    protected int getLayoutId() {
        return R.layout.act_userguide;
    }

    @Override
    public void show(Object obj) {

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SP.putString("first", "1");
        viewList = new ArrayList<View>();
        initView();

        MyPagerAdapter myPagerAdapter = new MyPagerAdapter();
        mViewPager.setAdapter(myPagerAdapter);
    }

    private void initView() {
    /*
    通过view对象作为viewpager的数据源
     */

        View view3 = View.inflate(getBaseContext(), R.layout.guide3, null);
        viewList.add(View.inflate(getBaseContext(), R.layout.guide1, null));
        viewList.add(View.inflate(getBaseContext(), R.layout.guide2, null));
        viewList.add(view3);

        view3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserGuideActivity.this, MainActivity.class));
                finish();
            }
        });

    }

    private class MyPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return viewList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        //实例化一个页卡
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(viewList.get(position));
            return viewList.get(position);
        }

        //销毁一个页卡
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            //super.destroyItem(container, position, object);
            container.removeView(viewList.get(position));
        }
    }


}
