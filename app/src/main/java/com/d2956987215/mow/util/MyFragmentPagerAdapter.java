package com.d2956987215.mow.util;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.ViewGroup;

import java.util.List;

public class MyFragmentPagerAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> fragments;

    public MyFragmentPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
       /* 每次调用 notifyDataSetChanged() 方法时，都会激活 getItemPosition方法
        POSITION_NONE表示该 Item 会被 destroyItem方法remove 掉，然后重新加载，
        POSITION_UNCHANGED表示不会重新加载，默认是 POSITION_UNCHANGED*/
        return PagerAdapter.POSITION_NONE;
    }
}