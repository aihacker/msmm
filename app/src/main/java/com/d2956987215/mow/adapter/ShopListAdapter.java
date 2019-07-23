package com.d2956987215.mow.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.d2956987215.mow.R;
import com.d2956987215.mow.activity.SplashActivity;
import com.d2956987215.mow.activity.home.OrdinaryWebViewActivity;
import com.d2956987215.mow.activity.home.ShareZhuanActivity;
import com.d2956987215.mow.activity.login.LoginNewActivity;
import com.d2956987215.mow.activity.mine.MemberCenterActivity;
import com.d2956987215.mow.dialog.GetQuanDialog;
import com.d2956987215.mow.rxjava.Request;
import com.d2956987215.mow.rxjava.Result;
import com.d2956987215.mow.rxjava.RxJavaUtil;
import com.d2956987215.mow.rxjava.response.GetCouponResponse;
import com.d2956987215.mow.rxjava.response.JuHuaSuanResponse;
import com.d2956987215.mow.rxjava.response.ShareResponse;
import com.d2956987215.mow.rxjava.response.ShopResponse;
import com.d2956987215.mow.util.ActivityUtils;
import com.d2956987215.mow.util.MoneyUtils;
import com.d2956987215.mow.util.StringUtil;
import com.d2956987215.mow.util.TBKUtils;
import com.d2956987215.mow.util.ToastUtil;
import com.d2956987215.mow.util.User;
import com.d2956987215.mow.widgets.ShapedImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/3/7.
 */

public class ShopListAdapter extends BaseQuickAdapter<ShopResponse.DataBean.ItemBean, BaseViewHolder> {

    public ShopListAdapter(@LayoutRes int layoutResId, @Nullable List<ShopResponse.DataBean.ItemBean> data) {
        super(layoutResId, data);
    }

    @Override
    public int getItemCount() {
        int count = 0;
        if (getData().size() > 0) {
            count = getData().size();
        }
        return count;
    }

    @Override
    protected void convert(BaseViewHolder holder, final ShopResponse.DataBean.ItemBean item) {

        final TextView tv_fenxiang_title = holder.getView(R.id.tv_fenxiang_title);

        ShapedImageView iv_pic = holder.getView(R.id.iv_pic);
        ShapedImageView iv_logo = holder.getView(R.id.iv_logo);
        TextView tv_total = holder.getView(R.id.tv_total);
        tv_total.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        Glide.with(mContext).load(item.getItempic()).placeholder(R.mipmap.da_tu).error(R.mipmap.da_tu).into(iv_pic);
        holder.setText(R.id.tv_name, item.getItemtitle());

        holder.setText(R.id.tv_quancount, "￥" + item.getCouponmoney());
        holder.setText(R.id.tv_total, "￥" + item.getItemprice());
        holder.setText(R.id.tv_nowprice, "￥" + item.getItemendprice());
        holder.setText(R.id.tv_dianming, item.getSellernick());
        holder.setText(R.id.tv_yishou, "已售:" + (StringUtil.isBlank(item.getItemsale()) ? 0 : MoneyUtils.getMoney(item.getItemsale())));
        if (!StringUtil.isBlank(item.getGive_money())) {
            holder.setText(R.id.tv_shengji, "￥" + item.getGive_money());
        } else {
            holder.setText(R.id.tv_shengji, "￥0");
        }
        if (!StringUtil.isBlank(item.getShare_money())) {
            holder.setText(R.id.tv_fenxiang, "￥" + item.getShare_money());
        } else {
            holder.setText(R.id.tv_fenxiang, "￥0");
        }
        if (item.getShoptype().equals("B")) {
            iv_logo.setBackground(mContext.getResources().getDrawable(R.mipmap.icon_tianmao));
        } else {
            iv_logo.setBackground(mContext.getResources().getDrawable(R.mipmap.home_icon_taobao));
        }

        if (StringUtil.isBlank(User.roleType()) || User.roleType().equals("0")) {
            holder.setText(R.id.tv_fenxiang_title, "领券");
            holder.setGone(R.id.tv_fenxiang, false);
            tv_fenxiang_title.setTextSize(14);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tv_fenxiang_title.getLayoutParams();
            params.setMargins(0, 20, 0, 0);
            tv_fenxiang_title.setLayoutParams(params);
            holder.getView(R.id.la_fenxiang).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //领券
                    if (User.uid() <= 0) {
                        ToastUtil.show(mContext, "请先登录");
//                        Intent intent = new Intent(mContext, SplashActivity.class);
//                        Intent intent = new Intent(mContext, LoginNewActivity.class);
//                        mContext.startActivity(intent);
                        ActivityUtils.startLoginAcitivy(mContext);
                        return;
                    }

                    if (tv_fenxiang_title.getText().toString().contains("领券")) {
                        couponRequest(User.uid(), item.getItemid(), item.getQuan_id(), item.getPid(), item.getUproletype());
                    }

                }
            });
            holder.getView(R.id.la_shengji).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //升级会员
                    if (User.uid() <= 0) {
                        ToastUtil.show(mContext, "请先登录");
//                        Intent intent = new Intent(mContext, SplashActivity.class);
//                        Intent intent = new Intent(mContext, LoginNewActivity.class);
//                        mContext.startActivity(intent);
                        ActivityUtils.startLoginAcitivy(mContext);
                        return;
                    }
                    Intent intent = new Intent(mContext, MemberCenterActivity.class);
                    intent.putExtra("uproletype", item.getUproletype());
                    mContext.startActivity(intent);
                }
            });
        } else if (!StringUtil.isBlank(User.roleType()) && (User.roleType().equals("1") || User.roleType().equals("2"))) {
            holder.setText(R.id.tv_fenxiang_title, "分享赚");
            tv_fenxiang_title.setTextSize(9);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tv_fenxiang_title.getLayoutParams();
            params.setMargins(0, 5, 0, 0);
            tv_fenxiang_title.setLayoutParams(params);
            holder.setGone(R.id.tv_fenxiang, true);
            holder.getView(R.id.la_fenxiang).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (User.uid() <= 0) {
                        ToastUtil.show(mContext, "请先登录");
//                        Intent intent = new Intent(mContext, SplashActivity.class);
//                        Intent intent = new Intent(mContext, LoginNewActivity.class);
//                        mContext.startActivity(intent);

                        ActivityUtils.startLoginAcitivy(mContext);
                        return;
                    }

                    shareRequest(User.uid(), item.getItemid(), item.getQuan_id(), item.getUproletype());
                }
            });
            holder.getView(R.id.la_shengji).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //升级会员
                    if (User.uid() <= 0) {
                        ToastUtil.show(mContext, "请先登录");
                        return;
                    }
                    Intent intent = new Intent(mContext, MemberCenterActivity.class);
                    intent.putExtra("uproletype", item.getUproletype());
                    mContext.startActivity(intent);
                }
            });
        } else if (!StringUtil.isBlank(User.roleType()) && User.roleType().equals("3")) {
            holder.setText(R.id.tv_shengji_title, "分享赚");
            holder.setGone(R.id.tv_fenxiang, false);
            tv_fenxiang_title.setTextSize(14);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tv_fenxiang_title.getLayoutParams();
            params.setMargins(0, 20, 0, 0);
            tv_fenxiang_title.setLayoutParams(params);
            holder.getView(R.id.la_fenxiang).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (User.uid() <= 0) {
                        ToastUtil.show(mContext, "请先登录");
//                        Intent intent = new Intent(mContext, SplashActivity.class);
//                        Intent intent = new Intent(mContext, LoginNewActivity.class);
//                        mContext.startActivity(intent);
                        ActivityUtils.startLoginAcitivy(mContext);
                        return;
                    }
                    couponRequest(User.uid(), item.getItemid(), item.getQuan_id(), item.getPid(), item.getUproletype());

                }
            });
            holder.getView(R.id.la_shengji).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //升级会员
                    if (User.uid() <= 0) {
                        ToastUtil.show(mContext, "请先登录");
                        return;
                    }
                    shareRequest(User.uid(), item.getItemid(), item.getQuan_id(), item.getUproletype());
                }
            });
        }


    }


    private void shareRequest(int userId, String itemid, String coupon_id, final String uproletype) {


        Map<String, String> map = new HashMap<>();
        map.put("Itemid", itemid);
        map.put("quan_id", coupon_id);
        map.put("user_id", userId + "");
        new Request<ShareResponse>().request(RxJavaUtil.xApi().getShare(map, "Bearer " + User.token()), "分享接口", mContext, true, new Result<ShareResponse>() {
            @Override
            public void get(final ShareResponse response) {

                if (response.getData().getMenu() != null) {

                    GetQuanDialog getQuanDialog = new GetQuanDialog(mContext, response.getData().getMenu().getDialogtitle(), response.getData().getMenu().getRighttitle(), new GetQuanDialog.CallBack() {
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
                                    Intent intent = new Intent(mContext, MemberCenterActivity.class);
                                    intent.putExtra("uproletype", uproletype);
                                    mContext.startActivity(intent);
                                    break;
                            }


                        }
                    });
                    getQuanDialog.show();


                } else {
                    goGetShare(response.getData().getDetails());
                }

            }
        });
    }

    private void goGetShare(ShareResponse.DataBean.DetailsBean data) {
        Intent intent = new Intent(mContext, ShareZhuanActivity.class);
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

        mContext.startActivity(intent);


    }


    private void couponRequest(int userId, String itemid, String coupon_id, final String pids, final String uproletype) {


        Map<String, String> map = new HashMap<>();
        map.put("Itemid", itemid);
        map.put("quan_id", coupon_id);
        map.put("user_id", userId + "");
        new Request<GetCouponResponse>().request(RxJavaUtil.xApi().getQuan(map, "Bearer " + User.token()), "优惠券接口", mContext, true, new Result<GetCouponResponse>() {
            @Override
            public void get(final GetCouponResponse response) {

                if (response.getData().getMenu() != null) {

                    GetQuanDialog getQuanDialog = new GetQuanDialog(mContext, response.getData().getMenu().getDialogtitle(), response.getData().getMenu().getRighttitle(), new GetQuanDialog.CallBack() {
                        @Override
                        public void NO() {

                            switch (response.getData().getMenu().getRighttype()) {
                                case 1:
                                    goGetCoupon(pids, response.getData().getCoupon_rul());
                                    break;
                                case 2:
                                    goGetCoupon(pids, response.getData().getCoupon_rul());
                                    break;
                                case 3:
                                    Intent intent = new Intent(mContext, MemberCenterActivity.class);
                                    intent.putExtra("uproletype", uproletype);
                                    mContext.startActivity(intent);
                                    break;
                            }


                        }
                    });
                    getQuanDialog.show();


                } else {
                    goGetCoupon(pids, response.getData().getCoupon_rul());
                }

            }
        });
    }

    private void goGetCoupon(String pids, String url) {
        String[] pid = pids.split("_");
        TBKUtils.showDetailPageForUrl((Activity) mContext, url, "taobao", pids, pid[pid.length - 1]);

    }


}
