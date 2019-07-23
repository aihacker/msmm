package com.d2956987215.mow.activity.luntan;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import com.d2956987215.mow.R;
import com.d2956987215.mow.activity.home.HomeFragment;
import com.d2956987215.mow.listener.IShowData;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class LunTanFragment extends Fragment implements IShowData {

    @BindView(R.id.viewPager)
    ViewPager viewPager;
    Unbinder unbinder;
    @BindView(R.id.magicIndicator)
    MagicIndicator mMagicIndicator;


    private ArrayList<Fragment> lists;
    private String[] titles = {"每日爆款", "宣传素材", "商学院"};

    public LunTanFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        lists = new ArrayList<>();
        lists.add(new LunTandetailFragment());
        lists.add(new LunTanXuanFragment());
        lists.add(new LunTanSchoolFragment());
        View view = inflater.inflate(R.layout.fragment_luntan, container, false);
        unbinder = ButterKnife.bind(this, view);
        initMagicIndicator();
        initData();
        return view;
    }

    private void initMagicIndicator() {
        CommonNavigator commonNavigator = new CommonNavigator(getActivity());
        mMagicIndicator.setForegroundGravity(Gravity.CENTER);
        commonNavigator.setScrollPivotX(0.35f);
        commonNavigator.setAdjustMode(false);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return titles.length;
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                MyPagerTitleView myPagerTitleView = new MyPagerTitleView(getActivity());
                myPagerTitleView.setText(titles[index]);
                myPagerTitleView.setTextColor(getResources().getColor(R.color.text_deep));
                myPagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        viewPager.setCurrentItem(index);
                    }
                });
                return myPagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setMode(LinePagerIndicator.MODE_EXACTLY);
                indicator.setYOffset(UIUtil.dip2px(context, 5));
                indicator.setLineHeight(UIUtil.dip2px(context, 3));
                indicator.setLineWidth(UIUtil.dip2px(context, 17));
                indicator.setColors(getResources().getColor(R.color.red1));
                indicator.setRoundRadius(UIUtil.dip2px(context, 2));
                return indicator;
            }
        });

        viewPager.setOffscreenPageLimit(titles.length);
        mMagicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(mMagicIndicator, viewPager);
    }

    public class MyPagerTitleView extends android.support.v7.widget.AppCompatTextView implements IPagerTitleView {

        public MyPagerTitleView(Context context) {
            super(context);
            setGravity(Gravity.CENTER);
            int padding = UIUtil.dip2px(context, 20);
            setPadding(padding, 0, padding, 0);
            setSingleLine();
            setEllipsize(TextUtils.TruncateAt.END);
        }

        @Override
        public void onLeave(int index, int totalCount, float leavePercent, boolean leftToRight) {
        }

        @Override
        public void onEnter(int index, int totalCount, float enterPercent, boolean leftToRight) {
        }

        @Override
        public void onSelected(int index, int totalCount) {
            setTextSize(21);
            getPaint().setFakeBoldText(true);
        }

        @Override
        public void onDeselected(int index, int totalCount) {
            setTextSize(15);
            getPaint().setFakeBoldText(false);
        }
    }

    public void initData() {

        viewPager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return lists.get(position);
            }

            @Override
            public int getCount() {
                //返回viewpager的数量
                return lists.size();
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                super.destroyItem(container, position, object);

            }
        });

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @SuppressWarnings("unchecked")
    @Override
    public void show(Object obj) {

    }

}
