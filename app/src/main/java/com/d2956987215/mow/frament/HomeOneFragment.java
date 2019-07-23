package com.d2956987215.mow.frament;


import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.d2956987215.mow.R;
import com.d2956987215.mow.activity.SplashActivity;
import com.d2956987215.mow.activity.home.BrandSaleActivity;
import com.d2956987215.mow.activity.home.GuideWebViewActivity;
import com.d2956987215.mow.activity.home.JuhuaSuanActivity;
import com.d2956987215.mow.activity.home.OrdinaryWebViewActivity;
import com.d2956987215.mow.activity.home.QiangGouListActivity;
import com.d2956987215.mow.activity.home.TaoBaoDetailActivity;
import com.d2956987215.mow.activity.login.LoginNewActivity;
import com.d2956987215.mow.activity.mine.BanZhangActivity;
import com.d2956987215.mow.activity.mine.MemberCenterActivity;
import com.d2956987215.mow.activity.mine.MyYaoQingActivity;
import com.d2956987215.mow.adapter.HomeActivityAdapter;
import com.d2956987215.mow.adapter.HomeHotAdapter;
import com.d2956987215.mow.adapter.HomeIntelligenceGridViewAdpter;
import com.d2956987215.mow.adapter.MyGridViewAdpter;
import com.d2956987215.mow.adapter.MyViewPagerAdapter;
import com.d2956987215.mow.constant.Const;
import com.d2956987215.mow.dialog.UpdateIDDialog;
import com.d2956987215.mow.eventbus.AuthrotySuccess;
import com.d2956987215.mow.eventbus.RefreshListData;
import com.d2956987215.mow.eventbus.ResetMainActivityNavigation;
import com.d2956987215.mow.frament.base.BaseFragment;
import com.d2956987215.mow.imageloader.GlideCornerHolderView;
import com.d2956987215.mow.imageloader.LocalImageHolderView;
import com.d2956987215.mow.listener.IShowData;
import com.d2956987215.mow.mvp.p.ParsingPresenter;
import com.d2956987215.mow.rxjava.Request;
import com.d2956987215.mow.rxjava.Result;
import com.d2956987215.mow.rxjava.RxJavaUtil;
import com.d2956987215.mow.rxjava.response.AuthTaoBaoResponse;
import com.d2956987215.mow.rxjava.response.BaseResponse;
import com.d2956987215.mow.rxjava.response.HomeDataResponse;
import com.d2956987215.mow.rxjava.response.HomeListResponse;
import com.d2956987215.mow.rxjava.response.TimeListResponse;
import com.d2956987215.mow.util.ActivityUtils;
import com.d2956987215.mow.util.AnimatorEffect;
import com.d2956987215.mow.util.DateUtil;
import com.d2956987215.mow.util.DisplayUtil;
import com.d2956987215.mow.util.TBKUtils;
import com.d2956987215.mow.util.ToastUtil;
import com.d2956987215.mow.util.User;
import com.d2956987215.mow.widgets.NoScrollGridView;
import com.d2956987215.mow.widgets.NoScrollListview;
import com.d2956987215.mow.widgets.VpSwipeRefreshLayout;
import com.tencent.mm.opensdk.modelbiz.WXLaunchMiniProgram;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static android.view.View.inflate;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeOneFragment extends BaseFragment implements Runnable, IShowData, SwipeRefreshLayout.OnRefreshListener, MyGridViewAdpter.onItemClick, HomeActivityAdapter.setOnImgClick {

    @BindView(R.id.banner)
    ConvenientBanner banner;
    @BindView(R.id.recycler_hot_product)
    RecyclerView recyclerHotProduct;
    @BindView(R.id.refresh_layout)
    VpSwipeRefreshLayout refreshLayout;
    @BindView(R.id.iv_qiang)
    ImageView iv_qiang;
    @BindView(R.id.iv_juhua)
    ImageView iv_juhua;
    @BindView(R.id.iv_bao)
    ImageView iv_bao;
    @BindView(R.id.iv_dapai)
    ImageView iv_dapai;
    Unbinder unbinder;
    @BindView(R.id.tv_remain_hour)
    TextView tvRemainHour;
    @BindView(R.id.tv_remain_min)
    TextView tvRemainMin;
    @BindView(R.id.tv_remain_miao)
    TextView tvRemainMiao;
    @BindView(R.id.banner2)
    ConvenientBanner banner2;
    @BindView(R.id.id_appbarlayout)
    AppBarLayout idAppbarlayout;
    @BindView(R.id.ll_notice_url)
    LinearLayout mLlNotice;
    @BindView(R.id.tv_notice_title)
    TextView mTvNoticeTitle;
    @BindView(R.id.viewpager)
    ViewPager mViewpager;
    @BindView(R.id.points)
    LinearLayout mPoints;
    @BindView(R.id.rl_nav)
    RelativeLayout mRlNav;
    @BindView(R.id.gv_activities)
    NoScrollListview mGvActivities;
    @BindView(R.id.gv_intelligence)
    NoScrollGridView mGvIntelligence;
    @BindView(R.id.ll_intelligence)
    LinearLayout mLlIntelligence;
    @BindView(R.id.marquee_view)
    ViewFlipper mMarqueeView;
    @BindView(R.id.ll_toutiao)
    LinearLayout mLlToutiao;
    @BindView(R.id.iv_hongbao)
    ImageView mIvHongbao;
    @BindView(R.id.coordinator_layout)
    CoordinatorLayout mCoordinatorLayout;
    @BindView(R.id.iv_close)
    ImageView mIvClose;
    @BindView(R.id.rb_type1)
    TextView mRbType1;
    @BindView(R.id.tv_type1)
    TextView mTvType1;
    @BindView(R.id.ll_type1)
    LinearLayout mLlType1;
    @BindView(R.id.rb_type2)
    TextView mRbType2;
    @BindView(R.id.tv_type2)
    TextView mTvType2;
    @BindView(R.id.ll_type2)
    LinearLayout mLlType2;
    @BindView(R.id.rb_type3)
    TextView mRbType3;
    @BindView(R.id.tv_type3)
    TextView mTvType3;
    @BindView(R.id.ll_type3)
    LinearLayout mLlType3;
    @BindView(R.id.rb_type4)
    TextView mRbType4;
    @BindView(R.id.tv_type4)
    TextView mTvType4;
    @BindView(R.id.ll_type4)
    LinearLayout mLlType4;
    @BindView(R.id.rb_type5)
    TextView mRbType5;
    @BindView(R.id.tv_type5)
    TextView mTvType5;
    @BindView(R.id.ll_type5)
    LinearLayout mLlType5;

    private HomeHotAdapter hotAdapter;
    private String type = "1";
    private HomeDataResponse mHomeDataResponse;
    private ParsingPresenter parsingPresenter = new ParsingPresenter(this);


    @SuppressLint("HandlerLeak")
    private Handler timeHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                try {
                    computeTime();
                    if (hour < 10) {
                        tvRemainHour.setText("0" + hour);
                    } else {
                        tvRemainHour.setText(hour + "");
                    }
                    if (minute < 10) {
                        tvRemainMin.setText("0" + minute);
                    } else {
                        tvRemainMin.setText(minute + "");
                    }
                    if (second < 10) {
                        tvRemainMiao.setText("0" + second);
                    } else {
                        tvRemainMiao.setText(second + "");
                    }
                    if (hour == 0 && minute == 0 && second == 0) {
                        isRun = false;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    };


    private int p = 1;
    private LinearLayoutManager layoutManager;
    private int hour;
    private int minute;
    private int second;
    private boolean isRun;
    private String mNoticeUrl;
    private View view;

    @SuppressLint("ClickableViewAccessibility")
    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_one, container, false);
        unbinder = ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        layoutManager = new LinearLayoutManager(getActivity());

//        initView();

        refreshLayout.setOnRefreshListener(this);

        //处理SwipeRefreshLayoutCoordinatorLayout
        idAppbarlayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset >= 0) {
                    refreshLayout.setEnabled(true);
                } else {
                    refreshLayout.setEnabled(false);
                }
            }
        });


        recyclerHotProduct.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                //表示屏幕已停止。屏幕停止滚动时为0;表示正在滚动。当屏幕滚动且用户使用的触碰或手指还在屏幕上时为1;表示正在滚动。当屏幕滚动且用户使用的触碰或手指还在屏幕上时为2
                if (newState == 0) {
                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mIvHongbao.getLayoutParams();
                    params.rightMargin = DisplayUtil.dip2px(getActivity(), 10);
                    mIvHongbao.setLayoutParams(params);
                } else if (newState == 1) {
                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mIvHongbao.getLayoutParams();
                    params.rightMargin = DisplayUtil.dip2px(getActivity(), -40);
                    mIvHongbao.setLayoutParams(params);

                }
            }
        });

        mGvIntelligence.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (mHomeDataResponse.getData().getIntelligence() != null && mHomeDataResponse.getData().getIntelligence().size() > 0) {
                    Intent intent = new Intent(getActivity(), GuideWebViewActivity.class);
                    intent.putExtra("url", mHomeDataResponse.getData().getIntelligence().get(i).getUrl());
                    intent.putExtra("name", mHomeDataResponse.getData().getIntelligence().get(i).getTitle());
                    getActivity().startActivity(intent);
                }

            }
        });
        return view;
    }


    private void initViewCustor() {
        p = 1;
        mRbType1.setSelected(true);
        mTvType1.setSelected(true);
        mTvType1.setBackgroundResource(R.drawable.home_tabtext_gradient);
        huoqu();
        authTaoBaoNotice();
        huoqutime();
        homeData();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Const.getSjhuaxin() != null && Const.getSjhuaxin().equals("1")) {
            huoqu();
        } else {
            Const.setSjhuaxin("2");
        }


    }

    public void initAppbar() {
//        idAppbarlayout.post(new Runnable() {
//            @Override
//            public void run() {
//                CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) idAppbarlayout.getLayoutParams();
//                AppBarLayout.Behavior behavior = (AppBarLayout.Behavior) layoutParams.getBehavior();
//
//                try {
//                    behavior.setDragCallback(new AppBarLayout.Behavior.DragCallback() {
//                        @Override
//                        public boolean canDrag(@NonNull AppBarLayout appBarLayout) {
//                            return true;
//                        }
//                    });
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });

        if (idAppbarlayout != null && idAppbarlayout.getLayoutParams() != null) {
            CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) idAppbarlayout.getLayoutParams();
            AppBarLayout.Behavior behavior = (AppBarLayout.Behavior) layoutParams.getBehavior();
            behavior.setDragCallback(new AppBarLayout.Behavior.DragCallback() {
                @Override
                public boolean canDrag(@NonNull AppBarLayout appBarLayout) {
                    return true;
                }
            });
        }

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            onResume();
        } else {
            onPause();
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            onPause();
        } else {
            onResume();
        }
    }

    private void authTaoBaoNotice() {
        Map<String, String> map = new HashMap<>();
        map.put("user_id", User.uid() + "");
        parsingPresenter.start("authTaoBaoNotice", "authTaoBaoNotice", "", map, "Bearer " + User.token());

//        new Request<AuthTaoBaoResponse>().request(RxJavaUtil.xApi().authTaoBaoNotice(map, "Bearer " + User.token()), "淘宝授权通知接口", getActivity(), false, new Result<AuthTaoBaoResponse>() {
//            @Override
//            public void get(AuthTaoBaoResponse response) {
////                authTaoBaoNotice AuthTaoBaoResponse
//                if (response.getData() != null) {
//                    mLlNotice.setVisibility(VISIBLE);
//                    mTvNoticeTitle.setText(response.getData().getTitle());
//                    mNoticeUrl = response.getData().getH5url();
//                } else {
//                    mLlNotice.setVisibility(GONE);
//                }
//            }
//
//        });

    }

    private void huoqu() {
        Map<String, Object> map = new HashMap<>();
        map.put("type", type);
        map.put("page", p + "");
        if (User.uid() > 0) {
            map.put("user_id", User.uid() + "");
        }
        parsingPresenter.start("getHomeGoodsList", "getHomeGoodsList", "", map);

//        new Request<HomeListResponse>().request(RxJavaUtil.xApi1().getHomeGoodsList(map), "首页列表", getActivity(), false, new Result<HomeListResponse>() {
//
//
//            @Override
//            public void get(HomeListResponse response) {
//                if (response.getData().getData() instanceof List && ((List) response.getData().getData()).size() > 0) {
//                    List<HomeListResponse.DataBeanX.DataBean> list = response.getData().getData();
//                    if (list != null && list.size() > 0) {
//                        initHotRecycler1(list);
//                    }
//
//                }
//            }
//
//            @Override
//            public void onServerError(String errDesc) {
//                super.onServerError(errDesc);
//
//            }
//
//            @Override
//            public void onBackErrorMessage(HomeListResponse response) {
//                super.onBackErrorMessage(response);
//            }
//        });

    }


    private void initHotRecycler1(final List<HomeListResponse.DataBeanX.DataBean> list) {

        if (hotAdapter == null) {
            hotAdapter = new HomeHotAdapter(R.layout.adapter_hot_product, list);
            hotAdapter.setPreLoadNumber(3);
            recyclerHotProduct.setLayoutManager(layoutManager);
            recyclerHotProduct.setAdapter(hotAdapter);
//            hotAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener<HomeListResponse.DataBeanX.DataBean>() {
//                @Override
//                public void onItemClick(View view, int position, HomeListResponse.DataBeanX.DataBean model) {
//                    Intent intent = new Intent(getContext(), TaoBaoDetailActivity.class);
//                    intent.putExtra("id", goodlist.get(position).getItemid() + "");
//                    intent.putExtra("type", "1");
//                    startActivity(intent);
//                }
//            });
            hotAdapter.setOnItemClickListener((adapter, view, position) -> {
                Intent intent = new Intent(getContext(), TaoBaoDetailActivity.class);
                intent.putExtra("id", hotAdapter.getItem(position).getItemid() + "");
                intent.putExtra("quan_id", hotAdapter.getItem(position).getQuan_id() + "");

                intent.putExtra("type", "1");
                startActivity(intent);
            });
           /* recyclerHotProduct.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    int lastVisibleItemPosition=layoutManager.findLastVisibleItemPosition();//可见范围内的最后一项的位置
                    int itemCount=layoutManager.getItemCount();
                    if(lastVisibleItemPosition==goodlist.size()-10){
                        refreshLayout.autoLoadmore();
                    }
                }
            });*/
        }
        int startPosition = 0;


        if (p == 1) {

            hotAdapter.setNewData(list);
            refreshLayout.setRefreshing(false);

//            goodlist.clear();

        } else {
            hotAdapter.addData(list);
            hotAdapter.loadMoreComplete();
        }
//        startPosition = goodlist.size();
//        goodlist.addAll(list);
//        hotAdapter.notifyDataSetChanged();
//     hotAdapter.notifyItemRangeInserted(startPosition,list.size());
//     hotAdapter.notifyItemRangeChanged(startPosition,list.size());
//
//        if (p != 1) {
//            isLoadingMore = true;
//        }


//        recyclerHotProduct.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//            }
//
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//
//                super.onScrolled(recyclerView, dx, dy);
//                lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
//                totalcount = layoutManager.getItemCount();
//                if (lastVisibleItemPosition >= totalcount - 4) {
//                    boolean isRefreshing = refreshLayout.isRefreshing();
//                    if (isRefreshing) {
//                        return;
//                    }
//                    p++;
//                    huoqu();
//                }
//            }
//
//        });

        hotAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                hotAdapter.setEnableLoadMore(true);
                p++;
                huoqu();
            }
        }, recyclerHotProduct);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (timeHandler != null) {
            timeHandler.removeMessages(1);
            timeHandler = null;
        }

        EventBus.getDefault().unregister(this);
    }

    private void initBanner(final List<HomeDataResponse.DataBean.Banner1Bean> bannerBeans) {
        if (bannerBeans != null && bannerBeans.size() > 0) {
            List<String> list = new ArrayList<String>();
            for (HomeDataResponse.DataBean.Banner1Bean str : bannerBeans) {                //处理第一个数组,list里面的值为1,2,3,4
                if (!list.contains("")) {
                    list.add(str.getPicUrl());  //本地照片Url需要拼接
                }
            }
            banner.setPages(
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
            banner.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(int i) {
                    if (bannerBeans != null && bannerBeans.size() > 0) {
                        toIntent(bannerBeans.get(i).getDirectType(), bannerBeans.get(i).getUrl(), bannerBeans.get(i).getTitle(), bannerBeans.get(i).getQuan_id(),
                                bannerBeans.get(i).getAid(), bannerBeans.get(i).getNeedLogin(), bannerBeans.get(i).getNeedrecord());
                    }
                }
            });
        }

    }

    private void initBanner2(final List<HomeDataResponse.DataBean.Banner2Bean> bannerBeans) {
        if (bannerBeans != null && bannerBeans.size() > 0) {
            List<String> list = new ArrayList<String>();
            for (HomeDataResponse.DataBean.Banner2Bean str : bannerBeans) {                //处理第一个数组,list里面的值为1,2,3,4
                if (!list.contains("")) {
                    list.add(str.getPicUrl());  //本地照片Url需要拼接
                }
            }
            Boolean isFalg = false;
            if (list.size() >= 2) {
                isFalg = true;
            }
            banner2.setPages(
                    new CBViewHolderCreator<LocalImageHolderView>() {
                        @Override
                        public LocalImageHolderView createHolder() {
                            return new LocalImageHolderView();
                        }
                    }, list)
                    .setPointViewVisible(isFalg)
                    //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                    .setPageIndicator(new int[]{R.drawable.cirlce_white_halfalpha, R.drawable.cirlce_white})
                    //设置指示器的方向
                    .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                    .startTurning(2000)
                    .setCanLoop(isFalg);//设置能手动影响;
            banner2.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(int i) {
                    if (bannerBeans != null && bannerBeans.size() > 0) {
                        toIntent(bannerBeans.get(i).getDirectType(), bannerBeans.get(i).getUrl(), bannerBeans.get(i).getTitle(),
                                bannerBeans.get(i).getQuan_id(), bannerBeans.get(i).getAid(), bannerBeans.get(i).getNeedLogin(),
                                bannerBeans.get(i).getNeedrecord());
                    }
                }
            });
        }

    }


    public static boolean isPkgInstalled(Context context, String pkgName) {
        PackageInfo packageInfo;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(pkgName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            packageInfo = null;
            e.printStackTrace();
        }
        return packageInfo != null;
    }


    private void huoqutime() {
        Map<String, Object> map = new HashMap<>();
        if (User.uid() > 0) {
            map.put("user_id", User.uid() + "");
        }
        parsingPresenter.start("timelist", "timelist", "", map);
//        new Request<TimeListResponse>().request(RxJavaUtil.xApi().timelist(map), "时间列表", getActivity(), false, new Result<TimeListResponse>() {
//
//
//            @Override
//            public void get(final TimeListResponse response) {
//                List<TimeListResponse.DataBean> data = response.getData();
//                if (data != null) {
//                    for (int i = 0; i < data.size(); i++) {                //处理第一个数组,list里面的值为1,2,3,4
//                        if (data.get(i).getStatus().equals("疯抢中")) {
//                            if (!isRun) {
//                                TimeListResponse.DataBean dataBean;
//                                int intervalTime;
//                                if (i >= data.size() - 1) {
//                                    dataBean = data.get(0);
//                                    intervalTime = DateUtil.getInterval(dataBean.getTime(), true);
//                                } else {
//                                    dataBean = data.get(i + 1);
//                                    intervalTime = DateUtil.getInterval(dataBean.getTime(), false);
//                                }
//                                hour = intervalTime / 1000 / 3600;
//                                minute = intervalTime / (60 * 1000) - hour * 60;
//                                second = intervalTime / 1000 - hour * 60 * 60 - minute * 60;
////                                startRun();
//
//                                Thread thread = new Thread(HomeOneFragment.this);
//                                thread.start();
//                            }
//                        }
//                    }
//                }
//
//            }
//        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @OnClick({R.id.ll_type1, R.id.ll_type2, R.id.ll_type3, R.id.ll_type4, R.id.ll_type5, R.id.la_qianggou, R.id.iv_juhua,
            R.id.iv_bao, R.id.iv_dapai, R.id.iv_close, R.id.ll_notice_url, R.id.iv_hongbao, R.id.ll_intelligenceMore})
    public void onViewClicked(View view) {
        AnimatorEffect.setClickEffect(getContext(), view);
        Intent intent;
        switch (view.getId()) {
            case R.id.la_qianggou:
//                Intent intent = new Intent(getActivity(), QiangGouListActivity.class);
//                intent.putExtra("name", "限时抢购");
//                intent.putExtra("activity_type", "3");
//                startActivity(intent);

                if (mHomeDataResponse.getData().getHotgoods() != null && mHomeDataResponse.getData().getHotgoods().size() > 0) {
                    toIntent(mHomeDataResponse.getData().getHotgoods().get(0).getDirectType(), mHomeDataResponse.getData().getHotgoods().get(0).getUrl(),
                            mHomeDataResponse.getData().getHotgoods().get(0).getTitle(), "", mHomeDataResponse.getData().getHotgoods().get(0).getAid(),
                            mHomeDataResponse.getData().getHotgoods().get(0).getNeedLogin(), mHomeDataResponse.getData().getHotgoods().get(0).getNeedrecord());
                }
                break;
            case R.id.iv_juhua:
//                intent = new Intent(getActivity(), BrandSaleActivity.class);
//                startActivity(intent);

                if (mHomeDataResponse.getData().getHotgoods() != null && mHomeDataResponse.getData().getHotgoods().size() > 0) {
                    toIntent(mHomeDataResponse.getData().getHotgoods().get(1).getDirectType(), mHomeDataResponse.getData().getHotgoods().get(1).getUrl(),
                            mHomeDataResponse.getData().getHotgoods().get(1).getTitle(), "", mHomeDataResponse.getData().getHotgoods().get(1).getAid(),
                            mHomeDataResponse.getData().getHotgoods().get(1).getNeedLogin(), mHomeDataResponse.getData().getHotgoods().get(1).getNeedrecord());
                }

                break;
            case R.id.iv_bao:
//                intent = new Intent(getActivity(), ShouYeDetailActivity.class);
//                intent.putExtra("name", "9.9元包邮");
//                intent.putExtra("price", "9.9");
//                startActivity(intent);

                if (mHomeDataResponse.getData().getHotgoods() != null && mHomeDataResponse.getData().getHotgoods().size() > 0) {
                    toIntent(mHomeDataResponse.getData().getHotgoods().get(2).getDirectType(), mHomeDataResponse.getData().getHotgoods().get(2).getUrl(),
                            mHomeDataResponse.getData().getHotgoods().get(2).getTitle(), "", mHomeDataResponse.getData().getHotgoods().get(2).getAid(),
                            mHomeDataResponse.getData().getHotgoods().get(2).getNeedLogin(), mHomeDataResponse.getData().getHotgoods().get(2).getNeedrecord());
                }
                break;
            case R.id.iv_dapai:
//                intent = new Intent(getActivity(), ShouYeDetailActivity.class);
//                intent.putExtra("name", "今日值得买");
//                intent.putExtra("activity_type", "5");
//                startActivity(intent);

                if (mHomeDataResponse.getData().getHotgoods() != null && mHomeDataResponse.getData().getHotgoods().size() > 0) {
                    toIntent(mHomeDataResponse.getData().getHotgoods().get(3).getDirectType(), mHomeDataResponse.getData().getHotgoods().get(3).getUrl(),
                            mHomeDataResponse.getData().getHotgoods().get(3).getTitle(), "", mHomeDataResponse.getData().getHotgoods().get(3).getAid(),
                            mHomeDataResponse.getData().getHotgoods().get(3).getNeedLogin(), mHomeDataResponse.getData().getHotgoods().get(3).getNeedrecord());
                }
                break;

            case R.id.ll_type1:
                showRefreshImage();
                resetRadio();
                mRbType1.setSelected(true);
                mTvType1.setSelected(true);
                mTvType1.setBackgroundResource(R.drawable.home_tabtext_gradient);
                type = "1";
                p = 1;
                huoqu();
                break;
            case R.id.ll_type2:
                showRefreshImage();
                resetRadio();
                mRbType2.setSelected(true);
                mTvType2.setSelected(true);
                mTvType2.setBackgroundResource(R.drawable.home_tabtext_gradient);
                type = "2";
                p = 1;
                huoqu();
                break;
            case R.id.ll_type3:
                showRefreshImage();
                resetRadio();
                mRbType3.setSelected(true);
                mTvType3.setSelected(true);
                mTvType3.setBackgroundResource(R.drawable.home_tabtext_gradient);
                type = "3";
                p = 1;
                huoqu();
                break;
            case R.id.ll_type4:
                showRefreshImage();
                resetRadio();
                mRbType4.setSelected(true);
                mTvType4.setSelected(true);
                mTvType4.setBackgroundResource(R.drawable.home_tabtext_gradient);
                type = "4";
                p = 1;
                huoqu();
                break;
            case R.id.ll_type5:
                showRefreshImage();
                resetRadio();
                mRbType5.setSelected(true);
                mTvType5.setSelected(true);
                mTvType5.setBackgroundResource(R.drawable.home_tabtext_gradient);
                type = "5";
                p = 1;
                huoqu();
                break;
            case R.id.iv_close:
                mLlNotice.setVisibility(GONE);
                break;
            case R.id.ll_notice_url:
                Intent intent3 = new Intent(getContext(), GuideWebViewActivity.class);
                intent3.putExtra("url", mNoticeUrl);
                intent3.putExtra("name", "通知");
                startActivity(intent3);
                break;
            case R.id.iv_hongbao:
                if (mHomeDataResponse.getData().getAdResources() != null) {
                    toIntent(mHomeDataResponse.getData().getAdResources().getDirectType(), mHomeDataResponse.getData().getAdResources().getUrl(),
                            mHomeDataResponse.getData().getAdResources().getTitle(), mHomeDataResponse.getData().getAdResources().getQuan_id(),
                            mHomeDataResponse.getData().getAdResources().getAid(), mHomeDataResponse.getData().getAdResources().getNeedLogin(),
                            mHomeDataResponse.getData().getAdResources().getNeedrecord());
                }
                break;
            case R.id.ll_intelligenceMore:
                intent = new Intent(getActivity(), OrdinaryWebViewActivity.class);
                intent.putExtra("url", RxJavaUtil.URL + "v2/ImlistWeb");
                intent.putExtra("name", "买手情报局");
                getActivity().startActivity(intent);
                break;

        }
    }

    private void resetRadio() {
        mRbType1.setSelected(false);
        mRbType2.setSelected(false);
        mRbType3.setSelected(false);
        mRbType4.setSelected(false);
        mRbType5.setSelected(false);
        mTvType1.setSelected(false);
        mTvType2.setSelected(false);
        mTvType3.setSelected(false);
        mTvType4.setSelected(false);
        mTvType5.setSelected(false);
        mTvType1.setBackground(null);
        mTvType2.setBackground(null);
        mTvType3.setBackground(null);
        mTvType4.setBackground(null);
        mTvType5.setBackground(null);
    }


    /**
     * Intent intent = new Intent(getActivity(), ErJiDetailActivity.class);
     * intent.putExtra("cate_id", sList.get(position).getId() + "");
     * intent.putExtra("name", sList.get(position).getName());
     * startActivity(intent);
     */

    private void showRefreshImage() {
        refreshLayout.post(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(true);
            }
        });

    }


    @SuppressWarnings("unchecked")
    @Override
    public void show(Object obj) {

    }

    /**
     * 开启倒计时
     */
    private void startRun() {
        isRun = true;

        new Thread(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                while (isRun) {
                    try {

                        if (timeHandler != null) {
                            Thread.sleep(1000); // sleep 1000ms
                            Message message = Message.obtain();
                            message.what = 1;
                            timeHandler.sendMessage(message);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }


    /**
     * 倒计时计算
     */
    private void computeTime() {
        second--;
        if (second < 0) {
            minute--;
            second = 59;
            if (minute < 0) {
                minute = 59;
                hour--;
                if (hour < 0) {                    // 倒计时结束
//                    isRun = false;
                }
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRefreshListData(RefreshListData refreshListData) {
        initViewCustor();
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onAuthrotySuccess(AuthrotySuccess authrotySuccess) {
        initViewCustor();
    }

    @Override
    public void onRefresh() {
        initViewCustor();
    }

    private void homeData() {
        Map<String, String> map = new HashMap<>();
        if (User.uid() > 0) {
            map.put("user_id", User.uid() + "");
        }
        parsingPresenter.start("homeData", "homeData", "", map);
//        new Request<HomeDataResponse>().request(RxJavaUtil.xApi().homeData(map), "首页数据", getActivity(), false, new Result<HomeDataResponse>() {
//
//            @Override
//            public void get(final HomeDataResponse response) {
//                if (response.getData() != null) {
//                    mHomeDataResponse = response;
//                    //红包
//                    if (response.getData().getAdResources() != null) {
//                        Glide.with(getActivity()).load(response.getData().getAdResources().getPicUrl()).into(mIvHongbao);
//                    }
//                    //banner1
//                    if (response.getData().getBanner1() != null && response.getData().getBanner1().size() > 0) {
//                        initBanner(response.getData().getBanner1());
//                    } else {
//                        banner.setVisibility(GONE);
//                    }
//                    //banner2
//                    if (response.getData().getBanner2() != null && response.getData().getBanner2().size() > 0) {
//                        initBanner2(response.getData().getBanner2());
//                    } else {
//                        banner2.setVisibility(GONE);
//                    }
//                    //nav导航
//                    List<HomeDataResponse.DataBean.PlatformBean> platformBeans = response.getData().getPlatform();
//                    if (platformBeans != null && platformBeans.size() > 0) {
//                        setNavData(platformBeans);
//                    } else {
//                        mRlNav.setVisibility(GONE);
//                    }
//                    //活动
//                    if (response.getData().getActivity() != null) {
//                        if (response.getData().getActivity().getModuleList() != null && response.getData().getActivity().getModuleList().size() > 0) {
//                            LinearLayoutManager manager = new LinearLayoutManager(getActivity());
//                            manager.setOrientation(LinearLayoutManager.VERTICAL);
//                            HomeActivityAdapter homeActivityAdapter = new HomeActivityAdapter(getActivity(), response.getData().getActivity().getModuleList());
//                            homeActivityAdapter.setOnImgClick(HomeOneFragment.this);
//                            mGvActivities.setAdapter(homeActivityAdapter);
//                        }
//
//                    } else {
//                        mGvActivities.setVisibility(View.GONE);
//                    }
//
//                    //情报局
//                    if (response.getData().getIntelligence() != null && response.getData().getIntelligence().size() > 0) {
//                        if (response.getData().getIntelligence().size() > 2) {
//                            mGvIntelligence.setAdapter(new HomeIntelligenceGridViewAdpter(getActivity(), response.getData().getIntelligence().subList(0, 2)));
//                        } else {
//                            mGvIntelligence.setAdapter(new HomeIntelligenceGridViewAdpter(getActivity(), response.getData().getIntelligence()));
//                        }
//
//                    } else {
//                        mLlIntelligence.setVisibility(GONE);
//                    }
//                    //头条
//                    if (response.getData().getToplist() != null && response.getData().getToplist().size() > 0) {
//
//                        for (int i = 0; i < response.getData().getToplist().size(); i++) {
//                            View ViewNotice = inflate(getActivity(), R.layout.item_homenotice, null);
//                            TextView textView = ViewNotice.findViewById(R.id.tv_name);
//                            textView.setText(response.getData().getToplist().get(i).getTitle());
//                            mMarqueeView.addView(ViewNotice);
//                        }
//                    } else {
//                        mLlToutiao.setVisibility(GONE);
//                    }
//                    //热卖
//                    if (response.getData().getHotgoods() != null && response.getData().getHotgoods().size() > 0) {
//                        List<HomeDataResponse.DataBean.HotgoodsBean> fourlist = response.getData().getHotgoods();
//                        Glide.with(getActivity()).load(fourlist.get(0).getPicUrl()).error(R.mipmap.da_tu).into(iv_qiang);
//                        Glide.with(getActivity()).load(fourlist.get(1).getPicUrl()).error(R.mipmap.da_tu).into(iv_juhua);
//                        Glide.with(getActivity()).load(fourlist.get(2).getPicUrl()).error(R.mipmap.da_tu).into(iv_bao);
//                        Glide.with(getActivity()).load(fourlist.get(3).getPicUrl()).error(R.mipmap.da_tu).into(iv_dapai);
//                    }
//                } else {
//                    banner.setVisibility(GONE);
//                    banner2.setVisibility(GONE);
//                    mRlNav.setVisibility(GONE);
//                    mLlIntelligence.setVisibility(GONE);
//                    mLlToutiao.setVisibility(GONE);
//                    mGvActivities.setVisibility(View.GONE);
//                }
//
//                initAppbar();
//            }
//        });
    }


    //gridview+viewpager
    private ImageView[] ivPoints;//小圆点图片的集合
    private int totalPage; //总的页数
    private int mPageSize = 10; //每页显示的最大的数量
    private List<View> viewPagerList;//GridView作为一个View对象添加到ViewPager集合中

    private void setNavData(List<HomeDataResponse.DataBean.PlatformBean> platformBeans) {
        //总的页数向上取整
        totalPage = (int) Math.ceil(platformBeans.size() * 1.0 / mPageSize);
        viewPagerList = new ArrayList<>();
        for (int i = 0; i < totalPage; i++) {
            //每个页面都是inflate出一个新实例
            GridView gridView = (GridView) inflate(getActivity(), R.layout.gridview, null);
            MyGridViewAdpter myGridViewAdpter = new MyGridViewAdpter(getActivity(), platformBeans, i, mPageSize);
            myGridViewAdpter.setOnItemClick(HomeOneFragment.this);
            gridView.setAdapter(myGridViewAdpter);

            //每一个GridView作为一个View对象添加到ViewPager集合中
            viewPagerList.add(gridView);
        }
        //设置ViewPager适配器
        mViewpager.setAdapter(new MyViewPagerAdapter(viewPagerList));

        //添加小圆点
        ivPoints = new ImageView[totalPage];
        mPoints.removeAllViews();
        for (int i = 0; i < totalPage; i++) {
            //循坏加入点点图片组
            ivPoints[i] = new ImageView(getActivity());
            if (i == 0) {
                ivPoints[i].setImageResource(R.drawable.nav_indecator_select);
            } else {
                ivPoints[i].setImageResource(R.drawable.nav_indecator_unselect);
            }
            mPoints.addView(ivPoints[i]);
        }
        //设置ViewPager的滑动监听，主要是设置点点的背景颜色的改变
        mViewpager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < totalPage; i++) {
                    if (i == position) {
                        ivPoints[i].setImageResource(R.drawable.nav_indecator_select);
                    } else {
                        ivPoints[i].setImageResource(R.drawable.nav_indecator_unselect);
                    }
                }
            }
        });

    }

    @Override
    public void itemClick(int type, String url, String name, String quan_id, String id, int needLogin, int needrecord) {
        toIntent(type, url, name, quan_id, id, needLogin, needrecord);
    }

    @Override
    public void OnImgClick(int type, String url, String name, String quan_id, String id, int needLogin, int needrecord) {
        toIntent(type, url, name, quan_id, id, needLogin, needrecord);
    }

    private void IsNeedRecord(final int type, final String url, final String name, final String quan_id, final String aid) {
        Map<String, String> map = new HashMap<>();
        if (User.uid() > 0) {
            map.put("user_id", User.uid() + "");
        }
        new Request<BaseResponse>().request(RxJavaUtil.xApi1().IsNeedRecord(map, "Bearer " + User.token()), "首页数据", getActivity(), false, new Result<BaseResponse>() {

            @Override
            public void get(final BaseResponse response) {
                toDetail(type, url, name, quan_id, aid);
            }
        });
    }

    //0 不执行     1 商品     2 分类    3 普通 h5  4 sdk（阿里百川    小程序等）   5 限时抢购    6 品牌特卖    7 带解析的 h5 8会员升级 9邀请好友 10聚划算 11联系班长联系运营商
    private void toIntent(int type, String url, String name, String quan_id, String aid, int needLogin, int needrecord) {
        if (needLogin == 1) {
            //需要登录才能看
            if (User.uid() > 0) {
                if (needrecord == 1) {
                    //需要备案,去备案
                    IsNeedRecord(type, url, name, quan_id, aid);
                    return;
                } else {
                    //不需要备案,继续执行以下跳转
                    toDetail(type, url, name, quan_id, aid);
                }
            } else {
//                getActivity().startActivity(new Intent(getActivity(), SplashActivity.class));
//                getActivity().startActivity(new Intent(getActivity(), LoginNewActivity.class));
                ActivityUtils.startLoginAcitivy(getActivity());
                return;
            }
        } else {
            //不需要登录就能看
            if (needrecord == 1) {
                if (User.uid() > 0) {
                    //需要备案已登陆,去备案
                    IsNeedRecord(type, url, name, quan_id, aid);
                    return;
                } else {
                    //需要备案没有登陆，去登陆
//                    getActivity().startActivity(new Intent(getActivity(), SplashActivity.class));
//                    getActivity().startActivity(new Intent(getActivity(), LoginNewActivity.class));
                    ActivityUtils.startLoginAcitivy(getActivity());
                    return;
                }

            } else {
                //不需要登陆不需要备案,继续执行以下跳转
                toDetail(type, url, name, quan_id, aid);
            }
        }

    }

    private void toDetail(int type, String url, String name, String quan_id, String aid) {

        Intent intent;
        switch (type) {
            case 0:
                break;
            case 1:
                intent = new Intent(getContext(), TaoBaoDetailActivity.class);
                intent.putExtra("id", aid);
                intent.putExtra("quan_id", quan_id);
                startActivity(intent);
                break;
            case 2:
                EventBus.getDefault().post(new ResetMainActivityNavigation());
                break;
            case 3:
                intent = new Intent(getActivity(), OrdinaryWebViewActivity.class);
                intent.putExtra("url", url);
                intent.putExtra("name", name);
                getActivity().startActivity(intent);
                break;
            case 4:
                if (!TextUtils.isEmpty(url)) {
                    if (url.contains("wechat")) {
                        String userName = Uri.parse(url).getQueryParameter("userName");
                        String path = Uri.parse(url).getQueryParameter("path");
                        //小程序
                        String appId = "wx07bf5ccf131324cc"; // 填应用AppId
                        IWXAPI api = WXAPIFactory.createWXAPI(getActivity(), appId);
                        WXLaunchMiniProgram.Req req = new WXLaunchMiniProgram.Req();
                        req.userName = userName; // 填小程序原始id
                        req.path = path;                  //拉起小程序页面的可带参路径，不填默认拉起小程序首页
                        req.miniprogramType = WXLaunchMiniProgram.Req.MINIPTOGRAM_TYPE_RELEASE;// 可选打开 开发版，体验版和正式版
                        api.sendReq(req);
                    } else if (url.contains("taobao")) {
                        //淘宝客户端
                        if (isPkgInstalled(getActivity(), "com.taobao.taobao")) {
                            TBKUtils.showDetailPageForUrl(getActivity(), url.replaceAll("", "\\"), "taobao");
                        } else {
                            ToastUtil.show(getActivity(), "您还没有安装淘宝客户端");
                        }
                    }
                }
                break;
            case 5:
                intent = new Intent(getContext(), QiangGouListActivity.class);
                intent.putExtra("name", "限时抢购");
                intent.putExtra("activity_type", aid);
                startActivity(intent);
                break;
            case 6:
                intent = new Intent(getActivity(), BrandSaleActivity.class);
                startActivity(intent);
                break;
            case 7:
                intent = new Intent(getActivity(), GuideWebViewActivity.class);
                intent.putExtra("url", url);
                intent.putExtra("name", name);
                getActivity().startActivity(intent);
                break;
            case 8:
                intent = new Intent(getActivity(), MemberCenterActivity.class);
                getActivity().startActivity(intent);
                break;
            case 9:
                if (aid.equals("0")) {
                    UpdateIDDialog updateIDDialog = new UpdateIDDialog(getActivity(), new UpdateIDDialog.CallBack() {
                        @Override
                        public void NO() {
                            Intent intent = new Intent(getActivity(), MemberCenterActivity.class);
                            startActivity(intent);

                        }
                    });
                    updateIDDialog.show();
                } else {
                    intent = new Intent(getActivity(), MyYaoQingActivity.class);
                    startActivity(intent);
                }

                break;
            case 10:
                intent = new Intent(getActivity(), JuhuaSuanActivity.class);
                intent.putExtra("activity_type", aid);
                intent.putExtra("activity_name", name);
                getActivity().startActivity(intent);
                break;
            case 11:
                intent = new Intent(getActivity(), BanZhangActivity.class);
                intent.putExtra("roleType", aid);
                startActivity(intent);
                break;

        }
    }


    @Override
    public void run() {
        while (isRun) {
            if (timeHandler != null) {
                try {
                    Thread.sleep(1000); // sleep 1000ms
                    Message message = Message.obtain();
                    if (message != null) {
                        message.what = 1;
                        timeHandler.sendMessage(message);
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    @NotNull
    @Override
    public View getLayoutView() {
        return view;
    }

    @Override
    public <T> void setData(@NotNull String type, T bean) {
        if (bean == null) {
            return;
        }
        if ("homeData" == type) {
            HomeDataResponse response = (HomeDataResponse) bean;
            if (response.getData() != null) {
                mHomeDataResponse = response;
                //红包
                if (response.getData().getAdResources() != null) {
                    Glide.with(getActivity()).load(response.getData().getAdResources().getPicUrl()).into(mIvHongbao);
                }
                //banner1
                if (response.getData().getBanner1() != null && response.getData().getBanner1().size() > 0) {
                    initBanner(response.getData().getBanner1());
                } else {
                    banner.setVisibility(GONE);
                }
                //banner2
                if (response.getData().getBanner2() != null && response.getData().getBanner2().size() > 0) {
                    initBanner2(response.getData().getBanner2());
                } else {
                    banner2.setVisibility(GONE);
                }
                //nav导航
                List<HomeDataResponse.DataBean.PlatformBean> platformBeans = response.getData().getPlatform();
                if (platformBeans != null && platformBeans.size() > 0) {
                    setNavData(platformBeans);
                } else {
                    mRlNav.setVisibility(GONE);
                }
                //活动
                if (response.getData().getActivity() != null) {
                    if (response.getData().getActivity().getModuleList() != null && response.getData().getActivity().getModuleList().size() > 0) {
                        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
                        manager.setOrientation(LinearLayoutManager.VERTICAL);
                        HomeActivityAdapter homeActivityAdapter = new HomeActivityAdapter(getActivity(), response.getData().getActivity().getModuleList());
                        homeActivityAdapter.setOnImgClick(HomeOneFragment.this);
                        mGvActivities.setAdapter(homeActivityAdapter);
                    }

                } else {
                    mGvActivities.setVisibility(View.GONE);
                }

                //情报局
                if (response.getData().getIntelligence() != null && response.getData().getIntelligence().size() > 0) {
                    if (response.getData().getIntelligence().size() > 2) {
                        mGvIntelligence.setAdapter(new HomeIntelligenceGridViewAdpter(getActivity(), response.getData().getIntelligence().subList(0, 2)));
                    } else {
                        mGvIntelligence.setAdapter(new HomeIntelligenceGridViewAdpter(getActivity(), response.getData().getIntelligence()));
                    }

                } else {
                    mLlIntelligence.setVisibility(GONE);
                }
                //头条
                if (response.getData().getToplist() != null && response.getData().getToplist().size() > 0) {

                    for (int i = 0; i < response.getData().getToplist().size(); i++) {
                        View ViewNotice = inflate(getActivity(), R.layout.item_homenotice, null);
                        TextView textView = ViewNotice.findViewById(R.id.tv_name);
                        textView.setText(response.getData().getToplist().get(i).getTitle());
                        mMarqueeView.addView(ViewNotice);
                    }
                } else {
                    mLlToutiao.setVisibility(GONE);
                }
                //热卖
                if (response.getData().getHotgoods() != null && response.getData().getHotgoods().size() > 0) {
                    List<HomeDataResponse.DataBean.HotgoodsBean> fourlist = response.getData().getHotgoods();
                    Glide.with(getActivity()).load(fourlist.get(0).getPicUrl()).error(R.mipmap.da_tu).into(iv_qiang);
                    Glide.with(getActivity()).load(fourlist.get(1).getPicUrl()).error(R.mipmap.da_tu).into(iv_juhua);
                    Glide.with(getActivity()).load(fourlist.get(2).getPicUrl()).error(R.mipmap.da_tu).into(iv_bao);
                    Glide.with(getActivity()).load(fourlist.get(3).getPicUrl()).error(R.mipmap.da_tu).into(iv_dapai);
                }
            } else {
                banner.setVisibility(GONE);
                banner2.setVisibility(GONE);
                mRlNav.setVisibility(GONE);
                mLlIntelligence.setVisibility(GONE);
                mLlToutiao.setVisibility(GONE);
                mGvActivities.setVisibility(View.GONE);
            }

            initAppbar();
        } else if ("getHomeGoodsList" == type) {
            HomeListResponse response = (HomeListResponse) bean;
            if (response.getData().getData() instanceof List && ( response.getData().getData()).size() > 0) {
                List<HomeListResponse.DataBeanX.DataBean> list = response.getData().getData();
                if (list != null && list.size() > 0) {
                    initHotRecycler1(list);
                }

            }
        } else if ("timelist" == type) {
            TimeListResponse response = (TimeListResponse) bean;
            List<TimeListResponse.DataBean> data = response.getData();
            if (data != null) {
                for (int i = 0; i < data.size(); i++) {                //处理第一个数组,list里面的值为1,2,3,4
                    if (data.get(i).getStatus().equals("疯抢中")) {
                        if (!isRun) {
                            TimeListResponse.DataBean dataBean;
                            int intervalTime;
                            if (i >= data.size() - 1) {
                                dataBean = data.get(0);
                                intervalTime = DateUtil.getInterval(dataBean.getTime(), true);
                            } else {
                                dataBean = data.get(i + 1);
                                intervalTime = DateUtil.getInterval(dataBean.getTime(), false);
                            }
                            hour = intervalTime / 1000 / 3600;
                            minute = intervalTime / (60 * 1000) - hour * 60;
                            second = intervalTime / 1000 - hour * 60 * 60 - minute * 60;
//                                startRun();

                            Thread thread = new Thread(HomeOneFragment.this);
                            thread.start();
                        }
                    }
                }
            }
        } else if ("authTaoBaoNotice" == type) {
            AuthTaoBaoResponse response = (AuthTaoBaoResponse) bean;
            if (response.getData() != null) {
                mLlNotice.setVisibility(VISIBLE);
                mTvNoticeTitle.setText(response.getData().getTitle());
                mNoticeUrl = response.getData().getH5url();
            } else {
                mLlNotice.setVisibility(GONE);
            }
        }
    }

    @Override
    public void onError(@NotNull String type, @NotNull Throwable error) {
        Log.e("type","type---"+type+error.getMessage());

    }

    @NotNull
    @Override
    public Fragment initView() {
        initViewCustor();
        return this;
    }
}
