package com.d2956987215.mow.activity.home;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.blankj.utilcode.util.StringUtils;
import com.bumptech.glide.Glide;
import com.d2956987215.mow.R;
import com.d2956987215.mow.activity.BaseActivity;
import com.d2956987215.mow.activity.MainActivity;
import com.d2956987215.mow.activity.login.WebViewActivity;
import com.d2956987215.mow.activity.mine.MemberCenterActivity;
import com.d2956987215.mow.activity.mine.MyHuiYuanDetailActivity;
import com.d2956987215.mow.activity.mine.MyShouCangActivity;
import com.d2956987215.mow.adapter.DetailServiseAdapter;
import com.d2956987215.mow.adapter.DetailShuomingAdapter;
import com.d2956987215.mow.adapter.ImageAdapter;
import com.d2956987215.mow.constant.Const;
import com.d2956987215.mow.dialog.GetQuanDialog;
import com.d2956987215.mow.imageloader.LocalImageHolderView;
import com.d2956987215.mow.jpush.MsgBean;
import com.d2956987215.mow.rxjava.Request;
import com.d2956987215.mow.rxjava.Result;
import com.d2956987215.mow.rxjava.RxJavaUtil;
import com.d2956987215.mow.rxjava.response.BaseResponse;
import com.d2956987215.mow.rxjava.response.GetCouponResponse;
import com.d2956987215.mow.rxjava.response.GoodDetailResponse;
import com.d2956987215.mow.rxjava.response.ShareResponse;
import com.d2956987215.mow.rxjava.response.ShopInfoResponse;
import com.d2956987215.mow.rxjava.response.ShouTitleResponse;
import com.d2956987215.mow.rxjava.response.TklCopyResponse;
import com.d2956987215.mow.util.ActivityUtils;
import com.d2956987215.mow.util.AppUtils;
import com.d2956987215.mow.util.StringUtil;
import com.d2956987215.mow.util.TBKUtils;
import com.d2956987215.mow.util.ToastUtil;
import com.d2956987215.mow.util.User;
import com.d2956987215.mow.util.WebViewUtils;
import com.d2956987215.mow.widgets.NoScrollGridView;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;
//import rx.Observable;

public class TaoBaoDetailActivity extends BaseActivity implements WebViewUtils.WebViewInterface {


    @BindView(R.id.banner)
    ConvenientBanner banner;
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_jiage)
    TextView tv_jiage;
    @BindView(R.id.tv_yueshou)
    TextView tv_yueshou;
    @BindView(R.id.iv_icon)
    ImageView iv_icon;
    @BindView(R.id.tv_dianming)
    TextView tv_dianming;
    @BindView(R.id.web)
    WebView web;
    @BindView(R.id.tv_tuijianci)
    TextView tv_tuijianci;
    @BindView(R.id.tv_youhui)
    TextView tv_youhui;
    @BindView(R.id.tv_riqi)
    TextView tv_riqi;
    @BindView(R.id.tv_fenxiang_money)
    TextView tv_fenxiang_money;
    @BindView(R.id.iv_feedback)
    ImageView ivFeedback;
    @BindView(R.id.tv_shoucang)
    TextView tvShoucang;
    @BindView(R.id.iv_favorite_list)
    ImageView ivFavoriteList;
    @BindView(R.id.rl_huiyuan)
    RelativeLayout rlHuiyuan;
    @BindView(R.id.tv_high_money)
    TextView mTvHighMoney;
    @BindView(R.id.tv_original_price)
    TextView mTvOrginalPrice;
    @BindView(R.id.tv_share_zero)
    TextView mTvShareZero;
    @BindView(R.id.service_recycleView)
    NoScrollGridView mServiceRecycleView;
    @BindView(R.id.shuoming_recycleView)
    NoScrollGridView mShuomingRecycleView;
    @BindView(R.id.rl_shop_all_coupon)
    LinearLayout mRlShopAllCoupon;
    @BindView(R.id.rv_details)
    RecyclerView rvDetails;

    private GoodDetailResponse.DataBean dataBean;


    //    public static String type = "1";
    private List<Fragment> fragments = new ArrayList<>();
    private String url;
    private List<String> piclist;
    private String desc;
    private String itemId;
    private String goodsId;
    private String quan_id;
    private String shopUrl = "";//店铺地址url
    private String shopName;//店铺地址名字

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getIntent() != null) {
            if (getIntent().getData() != null) {
                String msgJson = getIntent().getData().toString();
                if (!TextUtils.isEmpty(msgJson)) {
                    MsgBean msgBean = JSON.parseObject(msgJson, MsgBean.class);
                    if (msgBean.getN_extras() != null) {
                        goodsId = msgBean.getN_extras().getAid();
                        quan_id = msgBean.getN_extras().getQuan_id();
                    }
                }
            } else {
//                type = getIntent().getStringExtra("type");
                goodsId = getIntent().getStringExtra("id");
                quan_id = getIntent().getStringExtra("quan_id");
            }
        }

//        type = SearchSingleActivity.type;
        if (StringUtils.isEmpty(quan_id))
            quan_id = "";
        huoqu();
        changeStatusBarTextColor(true);
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }

    List<ShouTitleResponse.DataBean.CateBean> titlelist = new ArrayList<>();

    private void getShopInfo(String url) {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .build();
        okhttp3.Request request = new okhttp3.Request.Builder().
                url(url).build();
        //get
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response != null) {
                    String body = response.body().string();
                    if (!TextUtils.isEmpty(body)) {
                        detailAnalysis(body);
                    }
                }
            }
        });
    }


    private void detailAnalysis(String content) {
        Map<String, String> map = new HashMap<>();
        map.put("content", content);
        map.put("user_id", User.uid() + "");
        new Request<ShopInfoResponse>().request(RxJavaUtil.xApi1().DetailAnalysis(map), "获取店铺详情", this, false, new Result<ShopInfoResponse>() {
            @Override
            public void get(final ShopInfoResponse response) {
                if (response != null) {
                    mRlShopAllCoupon.setVisibility(View.VISIBLE);
                    shopUrl = response.getData().getSeller().getShopUrl();
                    shopName = response.getData().getSeller().getShopName();
                    if (!TextUtils.isEmpty(response.getData().getSeller().getShopName())) {
                        tv_dianming.setText(response.getData().getSeller().getShopName());
                    }

                    if (!TextUtils.isEmpty(response.getData().getSeller().getShopIcon())) {
                        Glide.with(TaoBaoDetailActivity.this).load(response.getData().getSeller().getShopIcon()).placeholder(R.mipmap.da_tu).error(R.mipmap.da_tu).into(iv_icon);
                    }
                    if (response.getData().getSeller() != null) {
                        if (response.getData().getSeller().getEvaluates() != null && response.getData().getSeller().getEvaluates().size() > 0) {
                            mServiceRecycleView.setAdapter(new DetailServiseAdapter(TaoBaoDetailActivity.this, response.getData().getSeller().getEvaluates()));
                        } else {
                            mServiceRecycleView.setVisibility(View.GONE);
                        }
                    } else {
                        mServiceRecycleView.setVisibility(View.GONE);
                    }

                    if (response.getData().getConsumerProtection() != null && response.getData().getConsumerProtection().size() > 0) {
                        mShuomingRecycleView.setAdapter(new DetailShuomingAdapter(TaoBaoDetailActivity.this, response.getData().getConsumerProtection()));
                    } else {
                        mShuomingRecycleView.setVisibility(View.GONE);
                    }
                } else {

                }
            }
        });

    }

    private void huoqu() {
        Map<String, String> map = new HashMap<>();
        map.put("id", goodsId);
        map.put("quan_id", quan_id);
        map.put("user_id", User.uid() + "");
        Log.d("zzz", goodsId + "," + quan_id + "," + User.uid());
        Observable<GoodDetailResponse> getlistdetail = null;
        String type = Const.tjptype;
        if (type == null) {
            type = "";
        }
        if ("2".equals(type)) {
            getlistdetail = RxJavaUtil.xApi1().JdGoodDetail(map, "Bearer " + User.token());
        } else if ("3".equals(type)) {
            getlistdetail = RxJavaUtil.xApi1().PddGoodDetail(map, "Bearer " + User.token());
        } else {
            getlistdetail = RxJavaUtil.xApi1().getlistdetail(map, "Bearer " + User.token());
        }
        String finalType = type;
        new Request<GoodDetailResponse>().request(getlistdetail, "获取详情", this, false, new Result<GoodDetailResponse>() {
            @Override
            public void get(final GoodDetailResponse response) {
                dataBean = response.getData();
                piclist = new ArrayList<String>();
                for (String str : response.getData().getDetails().getBanner()) {                //处理第一个数组,list里面的值为1,2,3,4
                    if (!piclist.contains("")) {
                        piclist.add(str);  //本地照片Url需要拼接
                    }
                }
                initBanner(piclist);
                desc = response.getData().getDetails().getExtension();
                url = response.getData().getDetails().getRhyurl();

                itemId = response.getData().getDetails().getItemid();
//                Glide.with(TaoBaoDetailActivity.this).load(response.getData().getSeller().getPict_url()).error(R.mipmap.da_tu).into(iv_logo);
                //    Glide.with(TaoBaoDetailActivity.this).load(response.getData().getSeller().getPict_url()).placeholder(R.mipmap.da_tu).error(R.mipmap.da_tu).into(iv_icon);
                tv_youhui.setText(response.getData().getDetails().getCoupon_price() + "元优惠券");
                tv_riqi.setText("使用期限:" + response.getData().getDetails().getCoupon_start_time() + " - " + response.getData().getDetails().getCoupon_end_time());
                String share_yongjin = response.getData().getDetails().getShare_yongjin();
                if (TextUtils.isEmpty(share_yongjin)) {
                    share_yongjin = "0";
                }
                tv_fenxiang_money.setText("(赚:" + share_yongjin + "元)");
                // tv_name.setText(response.getData().getDetails().getTitle());


                final String title = response.getData().getDetails().getTitle();
                Resources resources = getResources();
                Drawable imgdrawable;
                String shop_type = response.getData().getSeller().getShop_type();
                if ("B".equals(shop_type)) {
                    imgdrawable = resources.getDrawable(R.mipmap.icon_tianmao);
                } else if ("J".equals(shop_type)) {
                    imgdrawable = resources.getDrawable(R.mipmap.jd_icon_small);
                } else if ("P".equals(shop_type)) {
                    imgdrawable = resources.getDrawable(R.mipmap.pdd_icon_small);
                } else {
                    imgdrawable = resources.getDrawable(R.mipmap.icon_taobao);
                }
                SpannableString spannableString = new SpannableString("  " + title);//textView控件
                imgdrawable.setBounds(0, 0, imgdrawable.getMinimumWidth(), imgdrawable.getMinimumHeight());//左上右下
                ImageSpan imageSpan = new ImageSpan(imgdrawable);
                spannableString.setSpan(imageSpan, 0, 1, ImageSpan.ALIGN_BASELINE);
                tv_name.setText(spannableString);

                tv_name.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                        // 将文本内容放到系统剪贴板里。
                        cm.setText(tv_name.getText());
                        Toast.makeText(TaoBaoDetailActivity.this, "复制成功", Toast.LENGTH_LONG).show();
                        return false;
                    }
                });
                tv_jiage.setText("￥" + response.getData().getDetails().getEndprice());
                tv_yueshou.setText("月销" + response.getData().getDetails().getVolume());
                tv_tuijianci.setText(response.getData().getDetails().getExtension());
                tv_tuijianci.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                        // 将文本内容放到系统剪贴板里。
                        cm.setText(tv_tuijianci.getText());
                        Toast.makeText(TaoBaoDetailActivity.this, "复制成功", Toast.LENGTH_LONG).show();

                        return false;
                    }
                });
                //  tv_dianming.setText(response.getData().getSeller().getShop_title());
                // TODO: 2018/10/12  物流、商品描述、评分没做等待接口返回后添加


                if (response.getData().getDetails().getIs_collect().equals("0")) {
                    Drawable drawable = getResources().getDrawable(R.drawable.detail_icon_collect_unselect);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    tvShoucang.setCompoundDrawables(null, drawable, null, null);
                } else {
                    Drawable drawable = getResources().getDrawable(R.drawable.detail_icon_collect_select);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    tvShoucang.setCompoundDrawables(null, drawable, null, null);
                }
                //
                if (User.roleType().equals("3")) {
                    rlHuiyuan.setVisibility(View.GONE);
                } else {
                    rlHuiyuan.setVisibility(View.VISIBLE);
                    mTvHighMoney.setText("现在升级" + response.getData().getDetails().getUprolename() + "最高可得佣金￥" + response.getData().getDetails().getYongjin());
                }
                if ("2".equals(finalType) || "3".equals(finalType)) {
//                    rl_huiyuan
                    rlHuiyuan.setVisibility(View.GONE);
                    ImageAdapter imageAdapter = new ImageAdapter(R.layout.image_item, piclist);
                    rvDetails.setLayoutManager(new LinearLayoutManager(TaoBaoDetailActivity.this));
                    rvDetails.setAdapter(imageAdapter);

                } else {
                    new WebViewUtils(web, TaoBaoDetailActivity.this, TaoBaoDetailActivity.this);
                    web.loadUrl(response.getData().getDetails().getDescUrl());
                }


                if (User.roleType().equals("0")) {
                    mTvShareZero.setText("立即分享");
                    tv_fenxiang_money.setVisibility(View.GONE);
                } else {
                    tv_fenxiang_money.setVisibility(View.VISIBLE);
                }
//                mTvHighMoney.setText("现在升级" + response.getData().getDetails().getShare_money() + " 买手，最高可得佣金￥" + response.getData().getDetails().getShare_yongjin());
                mTvOrginalPrice.setText("￥" + response.getData().getDetails().getZk_final_price());
                mTvOrginalPrice.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);

                //店铺信息的接口
                if (!TextUtils.isEmpty(response.getData().getDetails().getTaobaoDetailUrl())) {
                    getShopInfo(response.getData().getDetails().getTaobaoDetailUrl());
                }
            }
        });

    }

    private void initBanner(List<String> imgs) {
        banner.setPages(
                new CBViewHolderCreator<LocalImageHolderView>() {
                    @Override
                    public LocalImageHolderView createHolder() {
                        return new LocalImageHolderView();
                    }
                }, imgs)
                .setPointViewVisible(true)
                //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                .setPageIndicator(new int[]{R.drawable.baidian_3x, R.drawable.hongdian_2x})
                //设置指示器的方向
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                .startTurning(5000)
                .setManualPageable(true);//设置能手动影响;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
//        type = intent.getStringExtra("type");
//        tjptype
        goodsId = getIntent().getStringExtra("id");
        huoqu();
    }


//    private void login() {
//        if (User.uid() > 0)
//            Intent intent3 = new Intent(this, SplashActivity.class);
//            startActivity(intent3);
//            return;
//        }
//    }


    @OnClick({R.id.tv_yueshou, R.id.rl_huiyuan, R.id.iv_favorite_list, R.id.tv_shoucang, R.id.iv_back, R.id.tv_shouye,
            R.id.tv_lingqu, R.id.tv_goumai, R.id.ll_fenxiang, R.id.iv_feedback, R.id.la_more, R.id.ll_rules,
            R.id.ll_copy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_goumai:


                if (User.uid() > 0) {

                    if (dataBean != null)
                        if (dataBean.getDetails().getDlpid() != null) {
                            couponRequest(User.uid(), dataBean.getDetails().getItemid(), dataBean.getDetails().getQuan_id(), dataBean.getDetails().getDlpid());
                        } else {
                            if (url != null) {
                                Intent intent = new Intent(this, GuideWebViewActivity.class);
                                intent.putExtra("url", url);
                                startActivity(intent);
                            }
                        }
                } else {
                    //                    startActivity(new Intent(this, SplashActivity.class));
//                    startActivity(new Intent(this, LoginNewActivity.class));

                    ActivityUtils.startLoginAcitivy(this);
                }


                break;
            case R.id.tv_lingqu:
//                if (url != null) {
//                    Intent intent = new Intent(this, WebViewActivity.class);
//                    intent.putExtra("url", url);
//                    intent.putExtra("taokouling", dataBean.getDetails().getTaokouling());
//                    intent.putExtra("itemid", dataBean.getDetails().getItemid());
//
//                    startActivity(intent);
//                }
                if (User.uid() > 0) {
                    if (dataBean != null)
                        if (dataBean.getDetails().getDlpid() != null) {
                            couponRequest(User.uid(), dataBean.getDetails().getItemid(), dataBean.getDetails().getQuan_id(), dataBean.getDetails().getDlpid());

                        } else {
                            if (url != null) {
                                Intent intent = new Intent(this, GuideWebViewActivity.class);
                                intent.putExtra("url", url);
                                startActivity(intent);
                            }
                        }
                } else
//                    startActivity(new Intent(this, SplashActivity.class));
//                    startActivity(new Intent(this, LoginNewActivity.class));
                    ActivityUtils.startLoginAcitivy(this);
                break;
            case R.id.iv_favorite_list:
                Intent intent2 = new Intent(this, MyShouCangActivity.class);
                startActivity(intent2);
                break;
            case R.id.tv_yueshou:


                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_feedback:
                if (StringUtil.isBlank(itemId)) {
                    ToastUtil.show(this, "商品id为空");
                    return;
                }
                Intent intent1 = new Intent(this, GoodsDetailFeedBackActivity.class);
                intent1.putExtra("itemId", itemId);
                startActivity(intent1);
                break;
            case R.id.tv_shouye:
                Intent mainIntent = new Intent(this, MainActivity.class);
                mainIntent.putExtra("from", "buyNow");
                startActivity(mainIntent);
                finish();

                break;
            case R.id.ll_copy:
                copy(User.uid(), dataBean.getDetails().getItemid(), dataBean.getDetails().getQuan_id());
                break;
            case R.id.ll_fenxiang:

                if (User.uid() > 0) {
                    if (dataBean != null) {
                        shareRequest(User.uid(), dataBean.getDetails().getItemid(), dataBean.getDetails().getQuan_id());
                    }
                } else
//                    startActivity(new Intent(this, SplashActivity.class));
//                    startActivity(new Intent(this, LoginNewActivity.class));
                    ActivityUtils.startLoginAcitivy(this);


                break;
            case R.id.rl_huiyuan:
                if (User.uid() > 0)
                    TaoBaoDetailActivity.this.startActivity(new Intent(TaoBaoDetailActivity.this, MemberCenterActivity.class).putExtra("uproletype", dataBean.getDetails().getUproletype()));
                else
//                    startActivity(new Intent(this, SplashActivity.class));
//                    startActivity(new Intent(this, LoginNewActivity.class));
                    ActivityUtils.startLoginAcitivy(this);
                break;
            case R.id.tv_shoucang:
                if (User.uid() > 0) {
                    if (dataBean == null || dataBean.getDetails() == null || dataBean.getSeller() == null) {
                        ToastUtil.show(this, "详情数据不全");
                        return;
                    }
                    if (dataBean.getDetails().getIs_collect().equals("0")) {
                        Map<String, String> map = new HashMap<>();
                        map.put("user_id", User.uid() + "");
                        map.put("goods_id", goodsId);
                        map.put("quan_id", quan_id);
                        if (TextUtils.isEmpty(Const.tjptype)) {
                            map.put("type", "1");
                        } else {
                            map.put("type", Const.tjptype);
                        }

                        if (dataBean != null && dataBean.getDetails() != null) {
                            GoodDetailResponse.DataBean.DetailsBean details = dataBean.getDetails();
                            map.put("goods_title", details.getTitle());
                            map.put("goods_pic", details.getItempic());
                            map.put("goods_quan", details.getCoupon_price());
                            map.put("goods_sale", details.getVolume());
                            map.put("goods_endprice", details.getEndprice());
                            map.put("goods_price", details.getReserve_price());

                        }
                        if (dataBean != null && dataBean.getSeller() != null) {
                            map.put("shop_name", dataBean.getSeller().getShop_title());
                            map.put("shoptype", dataBean.getSeller().getShop_type());
                        }
                        new Request<BaseResponse>().request(RxJavaUtil.xApi().addshoucang(map, "Bearer " + User.token()), "添加收藏", this, false, new Result<BaseResponse>() {
                            @Override
                            public void get(BaseResponse response) {
                                Drawable drawable = getResources().getDrawable(R.drawable.detail_icon_collect_select);
                                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                                tvShoucang.setCompoundDrawables(null, drawable, null, null);
                                ToastUtil.show(TaoBaoDetailActivity.this, response.message);
                                dataBean.getDetails().setIs_collect("1");
                            }

                            @Override
                            public void onBackErrorMessage(BaseResponse response) {
                                super.onBackErrorMessage(response);
                            }

                            @Override
                            public void onServerError(String errDesc) {
                                super.onServerError(errDesc);
                            }
                        });
                    } else {
                        Map<String, String> map = new HashMap<>();
                        map.put("goods_id", new Gson().toJson(new String[]{goodsId}));
                        map.put("user_id", User.uid() + "");
                        new Request<BaseResponse>().request(RxJavaUtil.xApi().deleteshoucang(map, "Bearer " + User.token()), "取消收藏", this, false, new Result<BaseResponse>() {
                            @Override
                            public void get(BaseResponse response) {
                                Drawable drawable = getResources().getDrawable(R.drawable.detail_icon_collect_unselect);
                                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                                tvShoucang.setCompoundDrawables(null, drawable, null, null);
                                ToastUtil.show(TaoBaoDetailActivity.this, response.message);
                                dataBean.getDetails().setIs_collect("0");
                            }
                        });
                    }
                } else
//                    startActivity(new Intent(this, SplashActivity.class));
//                    startActivity(new Intent(this, LoginNewActivity.class));
                    ActivityUtils.startLoginAcitivy(this);

                break;
            case R.id.la_more:
                if (!TextUtils.isEmpty(shopUrl)) {
                    Intent in = new Intent(TaoBaoDetailActivity.this, GuideWebViewActivity.class);
                    in.putExtra("url", shopUrl);
                    in.putExtra("name", shopName);
                    startActivity(in);
                }

                break;
            case R.id.ll_rules:
                if (dataBean != null && dataBean.getDetails() != null) {
                    Intent intent = new Intent(TaoBaoDetailActivity.this, GuideWebViewActivity.class);
                    intent.putExtra("name", "返佣规则");
                    intent.putExtra("url", dataBean.getDetails().getGuize());
                    startActivity(intent);
                }
                break;
        }

    }

    String item = null;

    private void couponRequest(int userId, String itemid, String coupon_id, final String pids) {


        Map<String, String> map = new HashMap<>();
        map.put("Itemid", itemid);
        map.put("quan_id", coupon_id);
        map.put("user_id", userId + "");


        Observable<GetCouponResponse> quan = null;

        if ("2".equals(Const.tjptype)) {
            item = "1";
            quan = RxJavaUtil.xApi1().JdGetQuan(map, "Bearer " + User.token());
        } else if ("3".equals(Const.tjptype)) {
            item = "1";
            quan = RxJavaUtil.xApi1().PddGetQuan(map, "Bearer " + User.token());
        } else {
            quan = RxJavaUtil.xApi().getQuan(map, "Bearer " + User.token());
        }

        new Request<GetCouponResponse>().request(quan, "优惠券接口", this, true, new Result<GetCouponResponse>() {
            @Override
            public void get(final GetCouponResponse response) {

                if (response.getData().getMenu() != null) {

                    GetQuanDialog getQuanDialog = new GetQuanDialog(TaoBaoDetailActivity.this, response.getData().getMenu().getDialogtitle(), response.getData().getMenu().getRighttitle(), new GetQuanDialog.CallBack() {
                        @Override
                        public void NO() {
                            if ("2".equals(Const.tjptype) || "3".equals(Const.tjptype)) {
                                goGetCoupon(pids, response.getData().getCoupon_rul());
                            } else {

                                switch (response.getData().getMenu().getRighttype()) {
                                    case 1:
                                        goGetCoupon(pids, response.getData().getCoupon_rul());
                                        break;
                                    case 2:
                                        goGetCoupon(pids, response.getData().getCoupon_rul());
                                        break;
                                    case 3:
                                        startActivity(new Intent(TaoBaoDetailActivity.this, MyHuiYuanDetailActivity.class).putExtra("roletype", User.roleType()));
                                        break;
                                }
                            }


                        }
                    });
                    getQuanDialog.show();


                } else {
                    goGetCoupon(pids, response.getData().getCoupon_rul());
                }

            }

            @Override
            public void onServerError(String errDesc) {
                super.onServerError(errDesc);
                goGetCoupon(pids, dataBean.getDetails().getItemid());
//                Utils.ToastShort(TaoBaoDetailActivity.this,"123");

            }
        }, item);
    }

    private void goGetCoupon(String pids, String url) {
        String[] pid = pids.split("_");
        if ("2".equals(Const.tjptype)) {
            boolean hasInstalled = AppUtils.checkHasInstalledApp(this, "com.jingdong.app.mall");
            if (!hasInstalled) {
                String jdWebStr_goods = "https://item.m.jd.com/product/" + url + ".html";
                startActivity(new Intent(TaoBaoDetailActivity.this, WebViewActivity.class).putExtra("url", jdWebStr_goods).putExtra("title", "京东"));
            } else {
                String jdAppStr_goods = "openApp.jdMobile://virtual?params={\"category\":\"jump\",\"des\":\"productDetail\",\"skuId\":\"" + url + "\",\"sourceType\":\"JSHOP_SOURCE_TYPE\",\"sourceValue\":\"JSHOP_SOURCE_VALUE\"}";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(jdAppStr_goods));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        } else if ("3".equals(Const.tjptype)) {
            boolean hasInstalled = AppUtils.checkHasInstalledApp(this, "com.xunmeng.pinduoduo");
            String content = "pinduoduo://com.xunmeng.pinduoduo/duo_coupon_landing.html?goods_id=" +
                    url +
                    "&pid=" +
                    pids +
                    "&t=JDj7m0HqSXQbTTWKnb0jjHkWGN3zVjAa9Hs5ZUD0O0s=";
            if (!hasInstalled) {
                startActivity(new Intent(TaoBaoDetailActivity.this, WebViewActivity.class).putExtra("url", url).putExtra("title", "拼多多"));
                return;
            }

            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(content));
            startActivity(intent);
        } else {
            TBKUtils.showDetailPageForUrl(TaoBaoDetailActivity.this, url, "taobao", dataBean.getDetails().getDlpid(), pid[pid.length - 1]);
        }

    }


    private void shareRequest(int userId, String itemid, String coupon_id) {
        Log.d("zzz", User.token());
        Map<String, String> map = new HashMap<>();
        map.put("Itemid", itemid);
        map.put("quan_id", coupon_id);
        map.put("user_id", userId + "");
        Observable<ShareResponse> share = null;
        if ("2".equals(Const.tjptype)) {
            share = RxJavaUtil.xApi1().JdShareQuan(map, "Bearer " + User.token());
        } else if ("3".equals(Const.tjptype)) {
            share = RxJavaUtil.xApi1().PddShareQuan(map, "Bearer " + User.token());
        } else {
            share = RxJavaUtil.xApi1().getShare(map, "Bearer " + User.token());
        }

        new Request<ShareResponse>().request(share, "分享接口", TaoBaoDetailActivity.this, true, new Result<ShareResponse>() {
            @Override
            public void get(final ShareResponse response) {

                if (response.getData().getMenu() != null) {

                    GetQuanDialog getQuanDialog = new GetQuanDialog(TaoBaoDetailActivity.this, response.getData().getMenu().getDialogtitle(), response.getData().getMenu().getRighttitle(), new GetQuanDialog.CallBack() {
                        @Override
                        public void NO() {

                            switch (response.getData().getMenu().getRighttype()) {
                                case 1:
                                    goGetShare(response.getData().getDetails());
                                    break;
                                case 2:
                                    goGetShare(response.getData().getDetails());
                                    break;
                                case 3:
                                    TaoBaoDetailActivity.this.startActivity(new Intent(TaoBaoDetailActivity.this, MyHuiYuanDetailActivity.class).putExtra("roletype", User.upreletype()));
                                    break;
                            }


                        }
                    });
                    getQuanDialog.show();


                } else {
                    goGetShare(response.getData().getDetails());
                }

            }

            @Override
            public void onServerError(String errDesc) {
                super.onServerError(errDesc);
            }

            @Override
            public void onBackErrorMessage(ShareResponse response) {
                super.onBackErrorMessage(response);
            }
        });


    }


    private void goGetShare(ShareResponse.DataBean.DetailsBean data) {
        Intent intent = new Intent(TaoBaoDetailActivity.this, ShareZhuanActivity.class);
        intent.putStringArrayListExtra("list", (ArrayList<String>) data.getBanner());
        intent.putExtra("title", data.getTitle());
        intent.putExtra("coupon_price", data.getCoupon_price() + "");
        intent.putExtra("yishou", data.getVolume());
        intent.putExtra("yongjin", data.getYongjin() + "");
        intent.putExtra("share_yongjin", data.getShare_yongjin() + "");
        intent.putExtra("nowprice", data.getEndprice());
        intent.putExtra("oldprice", data.getZk_final_price());
        intent.putExtra("shop_type", data.getShop_type());
        intent.putExtra("eurl", data.getEwmurl());
        intent.putExtra("template", data.getTemplate());
        intent.putExtra("temp", data.getTemp());
        intent.putExtra("rhyurl", data.getCoupon_rul());
        intent.putExtra("tkl", data.getTkl());
        intent.putExtra("desc", "");
        intent.putExtra("Comment", data.getComment());
        intent.putExtra("Comment_Template", data.getComment_Template());
        intent.putExtra("ShowMoney", data.getShowMoney());
        intent.putExtra("ShowMoney_Template", data.getShowMoney_Template());
        intent.putExtra("extension", data.getExtension());
        intent.putExtra("studentId", data.getStudentId());
        intent.putExtra("download_link", data.getDownload_link());

        TaoBaoDetailActivity.this.startActivity(intent);


    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_taobaodetail;
    }

    @Override
    public void show(Object obj) {

    }

    @Override
    public boolean check(WebView webView, String url) {
        return false;
    }

    @Override
    public void JavaScriptObjectMethod(String url) {

    }

    private void copy(int userId, String itemid, String coupon_id) {
        Map<String, String> map = new HashMap<>();
        map.put("Itemid", itemid);
        map.put("quan_id", coupon_id);
        map.put("user_id", userId + "");

        Observable<TklCopyResponse> tklCopyResponseObservable = null;

        if ("2".equals(Const.tjptype)) {
            tklCopyResponseObservable = RxJavaUtil.xApi1().JdCopyTkl(map, "Bearer " + User.token());
        } else if ("3".equals(Const.tjptype)) {
            tklCopyResponseObservable = RxJavaUtil.xApi1().PddCopyTkl(map, "Bearer " + User.token());
        } else {
            tklCopyResponseObservable = RxJavaUtil.xApi1().CopyTkl(map, "Bearer " + User.token());
        }
        new Request<TklCopyResponse>().request(tklCopyResponseObservable, "分享接口", TaoBaoDetailActivity.this, true, new Result<TklCopyResponse>() {
            @Override
            public void get(final TklCopyResponse response) {
                if (response != null && response.getData().getTkl() != null) {
                    ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    // 将文本内容放到系统剪贴板里。
                    cm.setText(response.getData().getTkl());
                    Toast.makeText(TaoBaoDetailActivity.this, "已复制", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onServerError(String errDesc) {
                super.onServerError(errDesc);
            }

        });
    }
}
