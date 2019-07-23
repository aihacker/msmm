package com.d2956987215.mow.activity.home;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.d2956987215.mow.R;
import com.d2956987215.mow.activity.BaseActivity;
import com.d2956987215.mow.imageloader.GlideCornerHolderView;
import com.d2956987215.mow.rxjava.Request;
import com.d2956987215.mow.rxjava.Result;
import com.d2956987215.mow.rxjava.RxJavaUtil;
import com.d2956987215.mow.rxjava.response.JuHuaSuanResponse;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class JuhuaSuanActivity extends BaseActivity {

    @BindView(R.id.banner)
    ConvenientBanner mBanner;
    @BindView(R.id.rl_back)
    RelativeLayout mRlBack;
    @BindView(R.id.ll_back)
    LinearLayout mLlBack;
    @BindView(R.id.magicIndicator)
    MagicIndicator mMagicIndicator;
    @BindView(R.id.appbarlayout)
    AppBarLayout mAppbarlayout;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.tv_bartitle)
    TextView mTvTitle;
    private List<JuHuaSuanResponse.DataBean.CateListBean> titlelist = new ArrayList<>();
    private List<String> mDataList = new ArrayList<>();
    private List<Fragment> fragments = new ArrayList<>();
    private String activity_type;
    private String activity_name;


    @Override
    protected int getLayoutId() {
        return R.layout.act_juhuasuan;
    }

    @Override
    public void show(Object obj) {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activity_type = getIntent().getStringExtra("activity_type");
        activity_name = getIntent().getStringExtra("activity_name");
        if (!TextUtils.isEmpty(activity_name)) {
            mTvTitle.setText(activity_name);
        }

        huoqutitile();
        mAppbarlayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset == 0) {
                    mIvBack.setImageResource(R.mipmap.white_back);
                    mTvTitle.setTextColor(getResources().getColor(R.color.white));
                } else {
                    mIvBack.setImageResource(R.mipmap.back_black);
                    mTvTitle.setTextColor(getResources().getColor(R.color.d3));
                }
                mLlBack.setBackgroundColor(changeAlpha(getResources().getColor(R.color.white), Math.abs(verticalOffset * 1.0f) / appBarLayout.getTotalScrollRange()));
            }
        });

        mRlBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    /**
     * 根据百分比改变颜色透明度
     */
    public int changeAlpha(int color, float fraction) {
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        int alpha = (int) (Color.alpha(color) * fraction);
        Log.d("zzz", fraction + "--" + alpha);
        return Color.argb(alpha, red, green, blue);
    }


    private void huoqutitile() {
        Map<String, String> map = new HashMap<>();
        map.put("user_id", User.uid() + "");
        map.put("activity_type", activity_type + "");
        new Request<JuHuaSuanResponse>().request(RxJavaUtil.xApi1().ActivityGoodList(map), "顶部数据", JuhuaSuanActivity.this, false, new Result<JuHuaSuanResponse>() {
            @Override
            public void get(JuHuaSuanResponse response) {
                if (response != null) {
                    if (response.getData().getBanner() != null && response.getData().getBanner().size() > 0) {
                        List<String> mBrandListBean = response.getData().getBanner();
                        initBanner(mBrandListBean);
                    }
                    if (response.getData().getCateList() != null && response.getData().getCateList().size() > 0) {
                        titlelist.addAll(response.getData().getCateList());
                        addFragment();
                        initMagicIndicator();

                    }
                }

            }
        });

    }

    private void initBanner(final List<String> bannerBeans) {
        if (bannerBeans != null && bannerBeans.size() > 0) {
            List<String> list = new ArrayList<String>();
            for (String str : bannerBeans) {                //处理第一个数组,list里面的值为1,2,3,4
                if (!list.contains("")) {
                    list.add(str);  //本地照片Url需要拼接
                }
            }
            mBanner.setPages(
                    new CBViewHolderCreator<GlideCornerHolderView>() {
                        @Override
                        public GlideCornerHolderView createHolder() {
                            return new GlideCornerHolderView();
                        }
                    }, list)
                    .setPointViewVisible(true)
                    //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                    .setPageIndicator(new int[]{R.drawable.cirlce_white_halfalpha, R.drawable.cirlce_white})
                    //设置指示器的方向
                    .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                    .startTurning(2000)
                    .setManualPageable(true);//设置能手动影响;
        }

    }


    private void addFragment() {
        for (int i = 0; i < titlelist.size(); i++) {
            fragments.add(new JuHuaSuanFragmentOne(titlelist.get(i).getCate_id(), activity_type));
        }
        mViewPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(), fragments));
    }

    private void initMagicIndicator() {
        for (int i = 0; i < titlelist.size(); i++) {
            mDataList.add(titlelist.get(i).getTitle());
        }
        CommonNavigator commonNavigator = new CommonNavigator(JuhuaSuanActivity.this);
        mMagicIndicator.setForegroundGravity(Gravity.CENTER);
        commonNavigator.setScrollPivotX(0.35f);
        commonNavigator.setAdjustMode(false);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return mDataList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                MyPagerTitleView myPagerTitleView = new MyPagerTitleView(JuhuaSuanActivity.this);
                myPagerTitleView.setText(mDataList.get(index));
                myPagerTitleView.setTextColor(getResources().getColor(R.color.text_deep));
                myPagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mViewPager.setCurrentItem(index);
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
                indicator.setColors(getResources().getColor(
                        R.color.red1));
                indicator.setRoundRadius(UIUtil.dip2px(context, 2));
                return indicator;
            }
        });

        //  mViewPager.setOffscreenPageLimit(mDataList.size());
        mMagicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(mMagicIndicator, mViewPager);
    }


    public class MyPagerTitleView extends AppCompatTextView implements IPagerTitleView {

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
            setTextSize(15);
            getPaint().setFakeBoldText(true);
        }

        @Override
        public void onDeselected(int index, int totalCount) {
            setTextSize(14);
            getPaint().setFakeBoldText(false);
        }
    }
}
