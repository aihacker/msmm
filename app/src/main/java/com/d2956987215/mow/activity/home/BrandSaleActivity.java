package com.d2956987215.mow.activity.home;

import android.content.Context;
import android.content.Intent;
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
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.d2956987215.mow.R;
import com.d2956987215.mow.activity.BaseActivity;
import com.d2956987215.mow.adapter.BrandGridViewAdpter;
import com.d2956987215.mow.rxjava.Request;
import com.d2956987215.mow.rxjava.Result;
import com.d2956987215.mow.rxjava.RxJavaUtil;
import com.d2956987215.mow.rxjava.response.BrandResponse;
import com.d2956987215.mow.util.MyFragmentPagerAdapter;
import com.d2956987215.mow.widgets.NoScrollGridView;

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

public class BrandSaleActivity extends BaseActivity {
    @BindView(R.id.rl_back)
    RelativeLayout mRlBack;
    @BindView(R.id.gv)
    NoScrollGridView mGv;
    @BindView(R.id.magicIndicator)
    MagicIndicator mMagicIndicator;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    @BindView(R.id.appbarlayout)
    AppBarLayout mAppbarlayout;
    @BindView(R.id.ll_back)
    LinearLayout mLlBack;
    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.tv_bartitle)
    TextView mTvBartitle;
    private List<BrandResponse.DataBean.CateBean> titlelist = new ArrayList<>();
    private List<String> mDataList = new ArrayList<>();
    private List<Fragment> fragments = new ArrayList<>();
    private List<BrandResponse.DataBean.BrandListBean> mBrandListBean;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_brandsale;
    }

    @Override
    public void show(Object obj) {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        huoqutitile();
        mAppbarlayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset == 0) {
                    mIvBack.setImageResource(R.mipmap.white_back);
                    mTvBartitle.setText("");
                } else {
                    mIvBack.setImageResource(R.mipmap.back_black);
                    mTvBartitle.setText("品牌联盟");
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

        mGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (mBrandListBean != null && mBrandListBean.size() > 0) {
                    Intent intent = new Intent(BrandSaleActivity.this, GuideWebViewActivity.class);
                    intent.putExtra("url", mBrandListBean.get(i).getShop_url());
                    intent.putExtra("name", mBrandListBean.get(i).getBrandName());
                    startActivity(intent);
                }

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
        new Request<BrandResponse>().request(RxJavaUtil.xApi1().brand(map), "顶部数据", BrandSaleActivity.this, false, new Result<BrandResponse>() {
            @Override
            public void get(BrandResponse response) {
                if (response != null) {
                    if (response.getData().getBrand_list() != null && response.getData().getBrand_list().size() > 0) {
                        mBrandListBean = response.getData().getBrand_list();
                        mGv.setAdapter(new BrandGridViewAdpter(BrandSaleActivity.this, response.getData().getBrand_list()));
                    }
                    if (response.getData().getCate() != null && response.getData().getCate().size() > 0) {
                        BrandResponse.DataBean.CateBean cateBean = new BrandResponse.DataBean.CateBean();
                        cateBean.setBrandcat("");
                        cateBean.setBrandName("精选");
                        titlelist.add(cateBean);
                        titlelist.addAll(response.getData().getCate());
                        addFragment();
                        initMagicIndicator();

                    }
                }

            }
        });

    }

    private void addFragment() {
        for (int i = 0; i < titlelist.size(); i++) {
//            fragments.add(new BrandSaleFragmentOne(titlelist.get(i).getBrandcat()));
            fragments.add( BrandSaleFragmentOne.newInstance(titlelist.get(i).getBrandcat()));
        }
        mViewPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(), fragments));
    }

    private void initMagicIndicator() {
        for (int i = 0; i < titlelist.size(); i++) {
            mDataList.add(titlelist.get(i).getBrandName());
        }
        CommonNavigator commonNavigator = new CommonNavigator(BrandSaleActivity.this);
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
                MyPagerTitleView myPagerTitleView = new MyPagerTitleView(BrandSaleActivity.this);
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

        // mViewPager.setOffscreenPageLimit(mDataList.size());
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
