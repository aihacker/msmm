package com.d2956987215.mow.activity.home;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.d2956987215.mow.R;
import com.d2956987215.mow.activity.APP;
import com.d2956987215.mow.activity.SplashActivity;
import com.d2956987215.mow.activity.login.LoginNewActivity;
import com.d2956987215.mow.activity.mine.BanZhangActivity;
import com.d2956987215.mow.activity.mine.MemberCenterActivity;
import com.d2956987215.mow.activity.mine.MyYaoQingActivity;
import com.d2956987215.mow.activity.mine.SearchActivity;
import com.d2956987215.mow.dialog.UpdateIDDialog;
import com.d2956987215.mow.eventbus.RefreshListData;
import com.d2956987215.mow.eventbus.ResetMainActivityNavigation;
import com.d2956987215.mow.eventbus.ResetOneLevelCateEvent;
import com.d2956987215.mow.eventbus.UnReadNum;
import com.d2956987215.mow.frament.HomeOneFragment;
import com.d2956987215.mow.frament.HomeTwoFragment;
import com.d2956987215.mow.listener.IShowData;
import com.d2956987215.mow.rxjava.Request;
import com.d2956987215.mow.rxjava.Result;
import com.d2956987215.mow.rxjava.RxJavaUtil;
import com.d2956987215.mow.rxjava.response.BaseResponse;
import com.d2956987215.mow.rxjava.response.HomeAds;
import com.d2956987215.mow.rxjava.response.MessageTypeResponse;
import com.d2956987215.mow.rxjava.response.ShouTitleResponse;
import com.d2956987215.mow.util.ActivityUtils;
import com.d2956987215.mow.util.AnimatorEffect;
import com.d2956987215.mow.util.DateUtils;
import com.d2956987215.mow.util.DisplayUtil;
import com.d2956987215.mow.util.MyFragmentPagerAdapter;
import com.d2956987215.mow.util.SP;
import com.d2956987215.mow.util.TBKUtils;
import com.d2956987215.mow.util.ToastUtil;
import com.d2956987215.mow.util.User;
import com.d2956987215.mow.widgets.SpinerPopWindow3;
import com.tencent.mm.opensdk.modelbiz.WXLaunchMiniProgram;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.model.PositionData;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import me.leolin.shortcutbadger.ShortcutBadger;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements IShowData {


    //    @BindView(R.id.magicIndicator)
    MagicIndicator magicIndicator;
    //    @BindView(R.id.viewPager)
    ViewPager viewPager;
    //    @BindView(R.id.la_lanmu)
    LinearLayout la_lanmu;
    //    @BindView(R.id.view_circle)
    TextView mViewCircle;
    private Activity mContext;
    Unbinder unbinder;


    private List<Fragment> fragments = new ArrayList<>();

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        mContext=this.getActivity();
        unbinder = ButterKnife.bind(this, view);
        findView(view);
        EventBus.getDefault().register(this);
        huoqutitile();
        getAds();
        getMessageNum();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void findView(View view) {
        magicIndicator = view.findViewById(R.id.magicIndicator);
        viewPager = view.findViewById(R.id.viewPager);
        la_lanmu = view.findViewById(R.id.la_lanmu);
        mViewCircle =view.findViewById(R.id.view_circle);
    }

    List<ShouTitleResponse.DataBean.CateBean> titlelist = new ArrayList<>();

    private void huoqutitile() {
        Map<String, String> map = new HashMap<>();
        if (User.uid() > 0) {
            map.put("user_id", User.uid() + "");
        }
        new Request<ShouTitleResponse>().request(RxJavaUtil.xApi().gettitlelist(map), "首页头数据", getActivity(), false, new Result<ShouTitleResponse>() {
            @Override
            public void get(ShouTitleResponse response) {
                ShouTitleResponse.DataBean.CateBean catabean = new ShouTitleResponse.DataBean.CateBean();
                catabean.setId(1);
                catabean.setName("精选");
                titlelist.add(catabean);
                titlelist.addAll(response.getData().getCate());
                addFragment(response.getData());
                initMagicIndicator();

            }
        });

    }

    private void addFragment(ShouTitleResponse.DataBean dataBean) {

        for (int i = 0; i < titlelist.size(); i++) {
            if (i == 0) {
                HomeOneFragment homeoneframent = new HomeOneFragment();
                fragments.add(homeoneframent);
            } else {
                HomeTwoFragment hometwoframent = new HomeTwoFragment();
                hometwoframent.setType(titlelist.get(i));
                fragments.add(hometwoframent);
            }
        }
        if (viewPager != null) {
            viewPager.setAdapter(new MyFragmentPagerAdapter(getFragmentManager(), fragments));
        }

    }

    private List<String> mDataList = new ArrayList<>();


    private void initMagicIndicator() {
        for (int i = 0; i < titlelist.size(); i++) {
            mDataList.add(titlelist.get(i).getName());
        }
        CommonNavigator commonNavigator = new CommonNavigator(APP.getApplication());
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
//                SimplePagerTitleView simplePagerTitleView = new SimplePagerTitleView(context);
//                simplePagerTitleView.setText(mDataList.get(index));
//                simplePagerTitleView.setNormalColor(getResources().getColor(R.color.home_class_title_color));
//                simplePagerTitleView.setSelectedColor(getResources().getColor(R.color.home_class_title_color));
//                simplePagerTitleView.setTextSize(15);
//                simplePagerTitleView.getPaint().setFakeBoldText(true);
//                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        viewPager.setCurrentItem(index);
//
//                    }
//                });

                MyPagerTitleView myPagerTitleView = new MyPagerTitleView(APP.getApplication());
                myPagerTitleView.setText(mDataList.get(index));
                myPagerTitleView.setTextColor(mContext.getResources().getColor(R.color.text_deep));
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
                indicator.setColors(mContext.getResources().getColor(R.color.red1));
                indicator.setRoundRadius(UIUtil.dip2px(mContext, 2));
                return indicator;
            }
        });

        viewPager.setOffscreenPageLimit(mDataList.size());
        magicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(magicIndicator, viewPager);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        unbinder.unbind();
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRefreshListData(RefreshListData refreshListData) {
        huoqutitile();
        getAds();
        getMessageNum();
    }

    private SpinerPopWindow3 mSpinerPopWindow;

    @OnClick({R.id.iv_more, R.id.iv_xiaoxi, R.id.la_search})
    public void onViewClicked(View view) {
        AnimatorEffect.setClickEffect(getContext(), view);
        switch (view.getId()) {
            case R.id.iv_more:
                mSpinerPopWindow = new SpinerPopWindow3(getActivity(), viewPager, titlelist, new SpinerPopWindow3.CallBack() {
                    @Override
                    public void OK(int position) {
                        mSpinerPopWindow.dismiss();
                        viewPager.setCurrentItem(position);
                    }

                    @Override
                    public void NO() {
                        mSpinerPopWindow.dismiss();
                    }
                });
                mSpinerPopWindow.showAsDropDown(mSpinerPopWindow, la_lanmu, 0, 0);

                break;
            case R.id.iv_xiaoxi:
                Intent intent = new Intent(getActivity(), MessageActivity.class);
                startActivity(intent);
                break;
            case R.id.la_search:
                intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
                break;

        }
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);

    }


    @SuppressWarnings("unchecked")
    @Override
    public void show(Object obj) {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void resetOneLevelCate(ResetOneLevelCateEvent resetOneLevelCateEvent) {
        if (viewPager != null) {
            viewPager.setCurrentItem(0);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void unReadNum(UnReadNum unReadNum) {
        getMessageNum();
    }


    public class MyPagerTitleView extends android.support.v7.widget.AppCompatTextView implements IPagerTitleView {

        public MyPagerTitleView(Context context) {
            super(context);
            setGravity(Gravity.CENTER);
            int padding = 39;//UIUtil.dip2px(context, 20);
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

    public class DotPagerIndicator extends View implements IPagerIndicator {

        public DotPagerIndicator(Context context) {
            super(context);
        }

        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int position) {
            setBackgroundResource(R.drawable.home_tabindecator_gradient);
        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }

        @Override
        public void onPositionDataProvide(List<PositionData> dataList) {

        }

        @Override
        protected void onDraw(Canvas canvas) {

        }
    }

    private void getAds() {
        Map<String, String> map = new HashMap<>();
        if (User.uid() > 0) {
            map.put("user_id", User.uid() + "");
        }
        new Request<HomeAds>().request(RxJavaUtil.xApi1().homeDialogAds(map), "首页弹窗广告", getActivity(), false, new Result<HomeAds>() {

            @Override
            public void get(final HomeAds response) {
                if (response.getData() != null) {
                    if (response.getData().getPopType() == 1) {
                        //每天展示广告
                        long lastTime = SP.getLong("homeAds", 0);
                        long currTime = getTime();
                        boolean isSameday = DateUtils.isSameData(currTime, lastTime);
                        if (!isSameday) {
                            showDialogImage(response);
                        }
                    } else if (response.getData().getPopType() == 2) {
                        showDialogImage(response);
                    }

                }
            }
        });

    }

    private Dialog dialogSelectImage;

    private void showDialogImage(final HomeAds homeAds) {
        View popupView = LayoutInflater.from(getActivity()).inflate(R.layout.home_dialog_ads, null);
        dialogSelectImage = new Dialog(getActivity(), R.style.dialogMatchParent);
        dialogSelectImage.setContentView(popupView);
        dialogSelectImage.getWindow().setGravity(Gravity.CENTER);
        dialogSelectImage.getWindow().setWindowAnimations(R.style.dialogWindowAnim);
        dialogSelectImage.setCancelable(false);
        dialogSelectImage.show();
        final ImageView iv_ads = dialogSelectImage.findViewById(R.id.iv_ads);
        if (homeAds != null) {
            //获取图片真正的宽高
            Glide.with(getActivity())
                    .load(homeAds.getData().getPicUrl())
                    .asBitmap()//强制Glide返回一个Bitmap对象
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap bitmap, GlideAnimation<? super Bitmap> glideAnimation) {
                            int picWidth = bitmap.getWidth();
                            int picHeight = bitmap.getHeight();
                            int showHeight = DisplayUtil.getScreenWidth(getActivity()) * picHeight / picWidth;

                            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) iv_ads.getLayoutParams();
                            params.width = DisplayUtil.getScreenWidth(getActivity());
                            params.height = showHeight;
                            iv_ads.setLayoutParams(params);
                            iv_ads.setImageBitmap(bitmap);
                        }
                    });
        }

        popupView.findViewById(R.id.iv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogSelectImage.dismiss();

                SP.putLong("homeAds", getTime());
            }
        });

        iv_ads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toIntent(homeAds);
            }
        });


    }

    //获取当前10位时间戳
    private long getTime() {
        long timeStamp = System.currentTimeMillis();
        return timeStamp;
    }


    //0 不执行     1 商品     2 分类    3 普通 h5  4 sdk（阿里百川    小程序等）   5 限时抢购    6 品牌特卖    7 带解析的 h5 8会员升级 9邀请好友 10聚划算 11联系班长/联系运营商
    private void toIntent(HomeAds homeAds) {
        if (homeAds.getData().getNeedLogin() == 1) {
            //需要登录才能看
            if (User.uid() > 0) {
                if (homeAds.getData().getIs_needrecord() == 1) {
                    //需要备案,去备案
                    IsNeedRecord(homeAds);
                    return;
                } else {
                    //不需要备案,继续执行以下跳转
                    toDetail(homeAds);
                }
            } else {
//                getActivity().startActivity(new Intent(getActivity(), SplashActivity.class));
//                getActivity().startActivity(new Intent(getActivity(), LoginNewActivity.class));
                ActivityUtils.startLoginAcitivy(getActivity());


                return;
            }
        } else {
            //不需要登录就能看
            if (homeAds.getData().getIs_needrecord() == 1) {
                if (User.uid() > 0) {
                    //需要备案已登陆,去备案
                    IsNeedRecord(homeAds);
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
                toDetail(homeAds);
            }
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

    private void IsNeedRecord(final HomeAds homeAds) {
        Map<String, String> map = new HashMap<>();
        if (User.uid() > 0) {
            map.put("user_id", User.uid() + "");
        }
        new Request<BaseResponse>().request(RxJavaUtil.xApi1().IsNeedRecord(map, "Bearer " + User.token()), "首页数据", getActivity(), false, new Result<BaseResponse>() {

            @Override
            public void get(final BaseResponse response) {
                toDetail(homeAds);
            }
        });
    }

    private void toDetail(HomeAds homeAds) {

        Intent intent;
        switch (homeAds.getData().getDirectType()) {
            case 0:
                break;
            case 1:
                intent = new Intent(getContext(), TaoBaoDetailActivity.class);
                intent.putExtra("id", homeAds.getData().getAid());
                intent.putExtra("quan_id", homeAds.getData().getQuan_id());
                startActivity(intent);
                break;
            case 2:
                EventBus.getDefault().post(new ResetMainActivityNavigation());
                break;
            case 3:
                intent = new Intent(getActivity(), OrdinaryWebViewActivity.class);
                intent.putExtra("url", homeAds.getData().getUrl());
                intent.putExtra("name", homeAds.getData().getTitle());
                getActivity().startActivity(intent);
                break;
            case 4:
                if (!TextUtils.isEmpty(homeAds.getData().getUrl())) {
                    if (homeAds.getData().getUrl().contains("wechat")) {
                        String userName = Uri.parse(homeAds.getData().getUrl()).getQueryParameter("userName");
                        String path = Uri.parse(homeAds.getData().getUrl()).getQueryParameter("path");
                        //小程序
                        String appId = "wx07bf5ccf131324cc"; // 填应用AppId
                        IWXAPI api = WXAPIFactory.createWXAPI(getActivity(), appId);
                        WXLaunchMiniProgram.Req req = new WXLaunchMiniProgram.Req();
                        req.userName = userName; // 填小程序原始id
                        req.path = path;                  //拉起小程序页面的可带参路径，不填默认拉起小程序首页
                        req.miniprogramType = WXLaunchMiniProgram.Req.MINIPTOGRAM_TYPE_RELEASE;// 可选打开 开发版，体验版和正式版
                        api.sendReq(req);
                    } else if (homeAds.getData().getUrl().contains("taobao")) {
                        //淘宝客户端
                        if (isPkgInstalled(getActivity(), "com.taobao.taobao")) {
                            TBKUtils.showDetailPageForUrl(getActivity(), homeAds.getData().getUrl().replaceAll("", "\\"), "taobao");
                        } else {
                            ToastUtil.show(getActivity(), "您还没有安装淘宝客户端");
                        }
                    }
                }
                break;
            case 5:
                intent = new Intent(getContext(), QiangGouListActivity.class);
                intent.putExtra("name", "限时抢购");
                intent.putExtra("activity_type", "3");
                startActivity(intent);
                break;
            case 6:
                intent = new Intent(getActivity(), BrandSaleActivity.class);
                startActivity(intent);
                break;
            case 7:
                intent = new Intent(getActivity(), GuideWebViewActivity.class);
                intent.putExtra("url", homeAds.getData().getUrl());
                intent.putExtra("name", homeAds.getData().getTitle());
                getActivity().startActivity(intent);
                break;
            case 8:
                intent = new Intent(getActivity(), MemberCenterActivity.class);
                getActivity().startActivity(intent);
                break;
            case 9:
                if (homeAds.getData().getRoleType() == 0) {
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
                intent.putExtra("activity_type", homeAds.getData().getAid());
                intent.putExtra("activity_name", homeAds.getData().getTitle());
                getActivity().startActivity(intent);
                break;
            case 11:
                intent = new Intent(getActivity(), BanZhangActivity.class);
                intent.putExtra("roleType", homeAds.getData().getAid());
                startActivity(intent);
                break;
        }
    }

    private void getMessageNum() {
        Map<String, String> map = new HashMap<>();
        if (User.uid() > 0) {
            map.put("user_id", User.uid() + "");
        }
        new Request<MessageTypeResponse>().request(RxJavaUtil.xApi1().megType(map), "消息类型", getActivity(), false, new Result<MessageTypeResponse>() {

            @Override
            public void get(final MessageTypeResponse response) {
                if (response != null) {
                    if (response.getUnreadNum() > 0) {
                        mViewCircle.setVisibility(View.VISIBLE);
                        mViewCircle.setText(String.valueOf(response.getUnreadNum()));
                        ShortcutBadger.applyCount(getActivity(), response.getUnreadNum());
                    } else {
                        mViewCircle.setVisibility(View.GONE);
                        ShortcutBadger.removeCount(getActivity());
                    }
                }
            }
        });
    }


}
