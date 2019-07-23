package com.d2956987215.mow.activity.mine;


import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.bumptech.glide.Glide;
import com.d2956987215.mow.R;
import com.d2956987215.mow.activity.SplashActivity;
import com.d2956987215.mow.activity.home.BrandSaleActivity;
import com.d2956987215.mow.activity.home.GuideWebViewActivity;
import com.d2956987215.mow.activity.home.JuhuaSuanActivity;
import com.d2956987215.mow.activity.home.MessageActivity;
import com.d2956987215.mow.activity.home.OrdinaryWebViewActivity;
import com.d2956987215.mow.activity.home.QiangGouListActivity;
import com.d2956987215.mow.activity.home.TaoBaoDetailActivity;
import com.d2956987215.mow.activity.login.LoginNewActivity;
import com.d2956987215.mow.dialog.UpdateIDDialog;
import com.d2956987215.mow.eventbus.ChangeHead;
import com.d2956987215.mow.eventbus.ResetMainActivityNavigation;
import com.d2956987215.mow.eventbus.UnReadNum;
import com.d2956987215.mow.imageloader.GlideCornerHolderView;
import com.d2956987215.mow.rxjava.Request;
import com.d2956987215.mow.rxjava.Result;
import com.d2956987215.mow.rxjava.RxJavaUtil;
import com.d2956987215.mow.rxjava.response.BaseResponse;
import com.d2956987215.mow.rxjava.response.MessageTypeResponse;
import com.d2956987215.mow.rxjava.response.PersonInfoResponse;
import com.d2956987215.mow.util.ActivityUtils;
import com.d2956987215.mow.util.AnimatorEffect;
import com.d2956987215.mow.util.SP;
import com.d2956987215.mow.util.TBKUtils;
import com.d2956987215.mow.util.ToastUtil;
import com.d2956987215.mow.util.User;
import com.d2956987215.mow.widgets.CircleImageView;
import com.tencent.mm.opensdk.modelbiz.WXLaunchMiniProgram;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

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
public class MineFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.circle_head)
    CircleImageView circleHead;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_yaoqingma)
    TextView tv_yaoqingma;
    @BindView(R.id.tv_yue)
    TextView tv_yue;
    @BindView(R.id.tv_yugu)
    TextView tv_yugu;
    @BindView(R.id.tv_shouyi)
    TextView tv_shouyi;
    @BindView(R.id.tv_shangyue)
    TextView tv_shangyue;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.tv_rolename)
    TextView mTvRolename;
    @BindView(R.id.ivbg_grade)
    LinearLayout mIvbgGrade;
    @BindView(R.id.scroll_view)
    ScrollView mScrollView;
    Unbinder unbinder;
    @BindView(R.id.banner)
    ConvenientBanner mBanner;
    @BindView(R.id.ll_setting)
    RelativeLayout mLlSetting;
    @BindView(R.id.tv_mine_toptitle)
    TextView mTvMineToptitle;
    @BindView(R.id.tv_id)
    TextView mTvId;
    @BindView(R.id.tv_fuzhi)
    TextView mTvFuzhi;
    @BindView(R.id.iv_jiantou)
    ImageView mIvJiantou;
    @BindView(R.id.ll_userinfo)
    LinearLayout mLlUserinfo;
    @BindView(R.id.iv_upgrade)
    ImageView mIvUpgrade;
    @BindView(R.id.tv_yuname)
    TextView mTvYuname;
    @BindView(R.id.tv_tixian)
    TextView mTvTixian;
    @BindView(R.id.ll_myProfit)
    LinearLayout mLlMyProfit;
    @BindView(R.id.rl_month)
    RelativeLayout mRlMonth;
    @BindView(R.id.rl_day)
    RelativeLayout mRlDay;
    @BindView(R.id.rl_pre_month)
    RelativeLayout mRlPreMonth;
    @BindView(R.id.tv_ditui)
    TextView mTvDitui;
    @BindView(R.id.tv_dingdan)
    TextView mTvDingdan;
    @BindView(R.id.tv_tuandui)
    TextView mTvTuandui;
    @BindView(R.id.tv_yaoqing)
    TextView mTvYaoqing;
    @BindView(R.id.iv_taocar)
    ImageView mIvTaocar;
    @BindView(R.id.iv_taocollection)
    ImageView mIvTaocollection;
    @BindView(R.id.la_xinshou)
    LinearLayout mLaXinshou;
    @BindView(R.id.la_shoucang)
    LinearLayout mLaShoucang;
    @BindView(R.id.la_zuji)
    LinearLayout mLaZuji;
    @BindView(R.id.la_about)
    LinearLayout mLaAbout;
    @BindView(R.id.iv_shezhi)
    ImageView mIvShezhi;
    @BindView(R.id.iv_close)
    ImageView mIvClose;
    @BindView(R.id.tv_notice_title)
    TextView mTvNoticeTitle;
    @BindView(R.id.ll_notice_url)
    LinearLayout mLlNoticeUrl;
    @BindView(R.id.view_circle)
    TextView mViewCircle;
    @BindView(R.id.rl_msg)
    RelativeLayout mRlMsg;
    private String name, head;
    private PersonInfoResponse.DataBean data;
    private String money;
    private boolean first = true;
    private String roleType = "";

    public MineFragment() {
        // Required empty public constructor
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        unbinder = ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        refreshLayout.setOnRefreshListener(this);
        initInfo();
        getMessageNum();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mScrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View view, int i, int i1, int i2, int i3) {
                    if (i1 > 0) {
                        mLlSetting.setBackgroundResource(R.mipmap.mine_topbg);
                        mTvMineToptitle.setText("买手妈妈");
                    } else {
                        mLlSetting.setBackground(null);
                        mTvMineToptitle.setText("");
                    }
                }
            });
        }
        return view;
    }

    private void initInfo() {
        Map<String, String> map = new HashMap<>();
        if (User.uid() > 0) {
            map.put("user_id", User.uid() + "");
        } else {
//            Intent intent = new Intent(getActivity(), SplashActivity.class);
//            Intent intent = new Intent(getActivity(), LoginNewActivity.class);
//            startActivity(intent);
            ActivityUtils.startLoginAcitivy(getActivity());
            return;
        }

        new Request<PersonInfoResponse>().request(RxJavaUtil.xApi1().personalInfo(map, "Bearer " + User.token()), "个人信息", getActivity(), false, new Result<PersonInfoResponse>() {
            @Override
            public void get(PersonInfoResponse response) {
                refreshLayout.setRefreshing(false);
                data = response.getRetval();
                SP.putString("name", data.getRealname());
                SP.putString("head", data.getAvatarUrl());
                SP.putString("phone", data.getMobile());
                SP.putString("roletypes", data.getRoletypes());
                SP.putString("uproletype", data.getUproletypes());

                SP.putString("studentId", data.getStudentId());
                SP.putString("taobaoname", data.getWw());
                SP.putString("alipayname", data.getAlipayname());
                SP.putString("alipayusername", data.getAlipayusername());
                SP.putString("deadline", data.getDeadline());

                name = data.getName();
                head = data.getAvatarUrl();
                tvName.setText(data.getRealname());
                mTvRolename.setText(name);
                if (data.getRoletypes().equals("1")) {
                    mIvbgGrade.setBackgroundResource(R.mipmap.mais_icon_ynms);
                } else if (data.getRoletypes().equals("2")) {
                    mIvbgGrade.setBackgroundResource(R.mipmap.vip_icon_ynms);
                } else if (data.getRoletypes().equals("3")) {
                    mIvbgGrade.setBackgroundResource(R.mipmap.bigvip_icon_yjms);
                }
                tv_yaoqingma.setText(data.getStudentId());
                tv_yugu.setText("￥" + data.getThis_money());
                money = data.getCredit2();
                tv_yue.setText(data.getCredit2());
                tv_shouyi.setText("￥" + data.getT_money());
                tv_shangyue.setText("￥" + data.getPre_money());
                Glide.with(getContext()).load(data.getAvatarUrl()).error(R.mipmap.have_no_head).into(circleHead);

                if (data.getBanner() != null && data.getBanner().size() > 0) {
                    initBanner(data.getBanner());
                } else {
                    mBanner.setVisibility(View.GONE);
                }

                if (data.getNews() != null) {
                    if (!TextUtils.isEmpty(data.getNews().getText())) {
                        roleType = data.getNews().getRoletype();
                        mLlNoticeUrl.setVisibility(View.VISIBLE);
                        mTvNoticeTitle.setText(data.getNews().getText());
                    } else {
                        mLlNoticeUrl.setVisibility(View.GONE);
                    }
                } else {
                    mLlNoticeUrl.setVisibility(View.GONE);
                }
            }
        });
    }


    private void initBanner(final List<PersonInfoResponse.DataBean.BannerBean> bannerBeans) {
        if (bannerBeans != null && bannerBeans.size() > 0) {
            List<String> list = new ArrayList<String>();
            for (PersonInfoResponse.DataBean.BannerBean str : bannerBeans) {                //处理第一个数组,list里面的值为1,2,3,4
                if (!list.contains("")) {
                    list.add(str.getPicUrl());  //本地照片Url需要拼接
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
            mBanner.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(int i) {
                    if (bannerBeans != null && bannerBeans.size() > 0) {
                        toIntent(bannerBeans.get(i).getDirectType(), bannerBeans.get(i).getUrl(), bannerBeans.get(i).getTitle(), bannerBeans.get(i).getQuan_id(),
                                bannerBeans.get(i).getAid(), bannerBeans.get(i).getNeedLogin(), bannerBeans.get(i).getNeedrecord(), bannerBeans.get(i).getRoleType());
                    }
                }
            });
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        if (first) {
            first = false;
        } else {
            initInfo();
            first = false;
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void changeHead(ChangeHead changeHead) {
        Glide.with(getContext()).load(changeHead.head).into(circleHead);
    }

    @OnClick({R.id.ll_userinfo, R.id.iv_shezhi, R.id.tv_tixian, R.id.la_shoucang, R.id.tv_dingdan,
            R.id.ll_myProfit, R.id.tv_yaoqing, R.id.la_zuji, R.id.tv_fuzhi, R.id
            .iv_jiantou, R.id.la_about,
            R.id.tv_tuandui, R.id.la_xinshou,
            R.id.rl_msg,
            R.id.rl_month,
            R.id.rl_day,
            R.id.rl_pre_month,
            R.id.iv_upgrade,
            R.id.iv_taocar,
            R.id.iv_taocollection,
            R.id.tv_ditui,
            R.id.ll_notice_url,
            R.id.iv_close
    })
    public void onViewClicked(View view) {
        AnimatorEffect.setClickEffect(getContext(), view);
        Intent intent = null;
        switch (view.getId()) {
            case R.id.tv_fuzhi:
                ClipboardManager cm = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                // 将文本内容放到系统剪贴板里。
                cm.setText(tv_yaoqingma.getText());
                Toast.makeText(getActivity(), "复制成功，可以发给朋友们了。", Toast.LENGTH_LONG).show();
                break;
            case R.id.la_xinshou:
                if (data != null) {
//                Intent intent = new Intent(getActivity(), NewGuideActivity.class);
                    intent = new Intent(getActivity(), GuideWebViewActivity.class)
                            .putExtra("url", data.getXszn())
                            .putExtra("name", "新手指导");
                    startActivity(intent);
                }
                break;
            case R.id.tv_yaoqing:
                if (data != null) {
                    if (!data.getRoletypes().equals("0")) {
                        intent = new Intent(getActivity(), MyYaoQingActivity.class);
//                    intent.putExtra("studentId", data.getStudentId());
                        //TODO  这个地方后台还没返回二维码
//                    intent.putExtra("erweima", data.getEwm());
                        startActivity(intent);
                    } else
                        showDialog();
                }

                break;
            case R.id.la_shoucang:
                intent = new Intent(getActivity(), MyShouCangActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_userinfo:
                if (data != null) {
                    startSettingActivity();
                }
                break;
            case R.id.iv_shezhi:
                if (data != null) {
                    startSettingActivity();
                }
                break;
            case R.id.iv_jiantou:
            case R.id.tv_tixian:
                if (data != null) {

                    if (money != null) {
                        intent = new Intent(getActivity(), TiXianActivity.class);
                        intent.putExtra("money", money);
                        intent.putExtra("name", data.getAlipayname());
                        intent.putExtra("alipayusername", data.getAlipayusername());
                        startActivity(intent);
                    } else {
                        ToastUtil.show(getActivity(), "获取余额失败，请重试");
                    }
                }
                break;
//            case R.id.tv_lianxi:
//                if (data != null) {
//                    intent = new Intent(getActivity(), BanZhangActivity.class);
//                    intent.putExtra("roletype", data.getContactid());
//                    intent.putExtra("contact", data.getContact());
//                    startActivity(intent);
//                }

            //break;
            case R.id.ll_myProfit:
//                if (data != null) {
//                    if (!data.getRoletypes().equals("0"))
//                        startActivity(new Intent(getContext(), MyShouYiActivity.class));
//                    else
//                        showDialog();
//                }
            case R.id.rl_month:
            case R.id.rl_day:
            case R.id.rl_pre_month:
                goMoneyInfo();
                break;
            case R.id.iv_upgrade:
                if (data != null) {
                    intent = new Intent(getActivity(), MemberCenterActivity.class);
                    intent.putExtra("uproletype", data.getUproletypes());
                    startActivity(intent);
                }
                break;
            case R.id.tv_tuandui:
                if (data != null) {
                    if (!data.getRoletypes().equals("0")) {
                        if (data.getRoletypes().equals("2")) {
                            intent = new Intent(getActivity(), MyTuanDuiListActivity1.class);
                            intent.putExtra("roletype", data.getRoletypes());
                            startActivity(intent);

                        } else if (data.getRoletypes().equals("3")) {
                            intent = new Intent(getActivity(), MyTuanDuiListActivity1.class);
                            intent.putExtra("roletype", data.getRoletypes());
                            startActivity(intent);
                        } else {
                            startActivity(new Intent(getContext(), MyTuanDuiListActivity.class));
                        }
                    } else
                        showDialog();
                }
                break;

//            case R.id.ll_contact_us:
//                startActivity(new Intent(getContext(), FeedBackActivity.class));
//                break;
            case R.id.tv_dingdan:
                if (data != null) {

                    if (!data.getRoletypes().equals("0")) {
                        intent = new Intent(getActivity(), MyOrderListActivity.class);
                        startActivity(intent);
                    } else
                        showDialog();
                }
                break;
            case R.id.la_zuji:
                intent = new Intent(getActivity(), MyZuJiActivity.class);
                startActivity(intent);
                break;
            case R.id.la_about:
                startActivity(new Intent(getActivity(), AboutUsActivity.class));
                break;
            case R.id.rl_msg:
                startActivity(new Intent(getActivity(), MessageActivity.class));
                break;
            case R.id.iv_taocar:
                intent = new Intent(getActivity(), TaoBaoCarActivity.class);
                intent.putExtra("url", "https://h5.m.taobao.com/mlapp/cart.html");
                intent.putExtra("name", "淘宝购物车");
                getActivity().startActivity(intent);
                break;
            case R.id.iv_taocollection:
                intent = new Intent(getActivity(), TaoBaoCollectionActivity.class);
                intent.putExtra("url", "https://h5.m.taobao.com/fav/index.htm");
                intent.putExtra("name", "淘宝收藏夹");
                getActivity().startActivity(intent);
                break;
            case R.id.tv_ditui:
                if (data != null) {
                    intent = new Intent(getActivity(), GuideWebViewActivity.class);
                    intent.putExtra("url", data.getGroundPush());
                    intent.putExtra("name", "地推");
                    getActivity().startActivity(intent);
                }
                break;
            case R.id.ll_notice_url:
                intent = new Intent(getActivity(), MemberCenterActivity.class);
                intent.putExtra("uproletype", roleType);
                getActivity().startActivity(intent);
                break;
            case R.id.iv_close:
                mLlNoticeUrl.setVisibility(View.GONE);
                break;
            default:
                break;
        }
    }

    private void goMoneyInfo() {
        if (data != null) {
            if (!data.getRoletypes().equals("0"))
                startActivity(new Intent(getContext(), MyShouYiActivity.class));
            else
                showDialog();
        }

    }


    private void startSettingActivity() {
        Intent intent = new Intent(getContext(), SettingActivity.class);
        intent.putExtra("data", JSON.toJSONString(data));
        startActivity(intent);
    }

    @Override
    public void onRefresh() {
        initInfo();
    }

    private void showDialog() {
        UpdateIDDialog updateIDDialog = new UpdateIDDialog(getActivity(), new UpdateIDDialog.CallBack() {
            @Override
            public void NO() {
                if (data != null) {
                    Intent intent = new Intent(getActivity(), MyHuiYuanDetailActivity.class);
                    intent.putExtra("roletype", "1");
                    startActivity(intent);
                }

            }
        });
        updateIDDialog.show();
    }

    @OnClick(R.id.banner)
    public void onViewClicked() {
    }

    //0 不执行     1 商品     2 分类    3 普通 h5  4 sdk（阿里百川    小程序等）   5 限时抢购    6 品牌特卖    7 带解析的 h5 8会员升级 9邀请好友 10聚划算 11联系班长/联系运营商
    private void toIntent(int type, String url, String title, String quan_id, String aid, int needLogin, int needrecord, int RoleType) {
        if (needLogin == 1) {
            //需要登录才能看
            if (User.uid() > 0) {
                if (data != null) {
                    if (needrecord == 1) {
                        //需要备案,去备案
                        IsNeedRecord(type, url, title, quan_id, aid, RoleType);
                        return;
                    } else {
                        //不需要备案,继续执行以下跳转
                        toDetail(type, url, title, quan_id, aid, RoleType);
                    }
                }

            } else {
//                getActivity().startActivity(new Intent(getActivity(), SplashActivity.class));

//                getActivity().startActivity(new Intent(getActivity(), LoginNewActivity.class));
                ActivityUtils.startLoginAcitivy(getActivity());
                return;
            }
        } else {
            if (data != null) {
                if (needrecord == 1) {
                    if (User.uid() > 0) {
                        //需要备案已登陆,去备案
                        IsNeedRecord(type, url, title, quan_id, aid, RoleType);
                        return;
                    } else {
                        //需要备案没有登陆，去登陆
//                        getActivity().startActivity(new Intent(getActivity(), SplashActivity.class));
//                        getActivity().startActivity(new Intent(getActivity(), LoginNewActivity.class));
                        ActivityUtils.startLoginAcitivy(getActivity());
                        return;
                    }
                } else {
                    //不需要备案,继续执行以下跳转
                    toDetail(type, url, title, quan_id, aid, RoleType);
                }
            }

        }

    }

    private void toDetail(int type, String url, String title, String quan_id, String aid, int RoleType) {

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
                intent.putExtra("name", title);
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
                intent.putExtra("activity_type", "3");
                startActivity(intent);
                break;
            case 6:
                intent = new Intent(getActivity(), BrandSaleActivity.class);
                startActivity(intent);
                break;
            case 7:
                intent = new Intent(getActivity(), GuideWebViewActivity.class);
                intent.putExtra("url", url);
                intent.putExtra("name", title);
                getActivity().startActivity(intent);
                break;
            case 8:
                intent = new Intent(getActivity(), MemberCenterActivity.class);
                getActivity().startActivity(intent);
                break;
            case 9:
                if (RoleType == 0) {
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
                intent.putExtra("activity_name", title);
                getActivity().startActivity(intent);
                break;

            case 11:
                intent = new Intent(getActivity(), BanZhangActivity.class);
                intent.putExtra("roleType", aid);
                startActivity(intent);
                break;
        }
    }

    private static boolean isPkgInstalled(Context context, String pkgName) {
        PackageInfo packageInfo;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(pkgName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            packageInfo = null;
            e.printStackTrace();
        }
        return packageInfo != null;
    }


    private void IsNeedRecord(final int type, final String url, final String title, final String quan_id, final String aid, final int RoleType) {
        Map<String, String> map = new HashMap<>();
        if (User.uid() > 0) {
            map.put("user_id", User.uid() + "");
        }
        new Request<BaseResponse>().request(RxJavaUtil.xApi1().IsNeedRecord(map, "Bearer " + User.token()), "首页数据", getActivity(), false, new Result<BaseResponse>() {

            @Override
            public void get(final BaseResponse response) {
                toDetail(type, url, title, quan_id, aid, RoleType);
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void unReadNum(UnReadNum unReadNum) {
        getMessageNum();
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
                    if(mViewCircle!=null){
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
            }
        });
    }

}
