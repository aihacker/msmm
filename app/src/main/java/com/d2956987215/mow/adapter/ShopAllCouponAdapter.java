package com.d2956987215.mow.adapter;

import android.graphics.Paint;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.d2956987215.mow.R;
import com.d2956987215.mow.rxjava.response.ShopAllCouponResponse;
import com.d2956987215.mow.util.MoneyUtils;
import com.d2956987215.mow.util.StringUtil;
import com.d2956987215.mow.widgets.ShapedImageView;

import java.util.List;

/**
 * Created by Administrator on 2018/3/7.
 */

public class ShopAllCouponAdapter extends BaseQuickAdapter<ShopAllCouponResponse.DataBean, BaseViewHolder> {
    public ShopAllCouponAdapter(@LayoutRes int layoutResId, @Nullable List<ShopAllCouponResponse.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, final ShopAllCouponResponse.DataBean item) {
        ShapedImageView iv_pic = holder.getView(R.id.iv_pic);
        ShapedImageView iv_logo = holder.getView(R.id.iv_logo);
        TextView tv_total = holder.getView(R.id.tv_total);
        tv_total.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        Glide.with(mContext).load(item.getItempic()).placeholder(R.mipmap.da_tu).error(R.mipmap.da_tu).into(iv_pic);
        holder.setText(R.id.tv_name, item.getItemtitle());
        holder.setText(R.id.tv_total, "￥" + item.getItemprice());
        holder.setText(R.id.tv_quancount, "￥" + item.getCouponmoney());
        holder.setText(R.id.tv_yishou, "已售" + (StringUtil.isBlank(item.getItemsale())?0:MoneyUtils.getMoney(item.getItemsale())));
        holder.setText(R.id.tv_nowprice, "￥" + item.getItemendprice());
        holder.setText(R.id.tv_dianming, item.getSellernick());
//        holder.setText(R.id.tv_shengji, "￥" + (StringUtil.isBlank(item.getGive_money())?0:item.getGive_money()));
//        holder.setText(R.id.tv_fenxiang, "￥" + (StringUtil.isBlank(item.getShare_money())?0:item.getShare_money()));
        if (item.getShoptype().equals("B")) {
            iv_logo.setBackground(mContext.getResources().getDrawable(R.mipmap.icon_tianmao));
        } else {
            iv_logo.setBackground(mContext.getResources().getDrawable(R.mipmap.home_icon_taobao));
        }
        /*
        if (User.roleType().equals("0")) {
            holder.setText(R.id.tv_fenxiang_title, "领券");
            holder.setGone(R.id.tv_fenxiang, true);
            holder.getView(R.id.la_fenxiang).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //领券
                    if (User.uid() <= 0) {
                        ToastUtil.show(mContext, "请先登录");
                        Intent intent = new Intent(mContext, SplashActivity.class);
                        mContext.startActivity(intent);
                        return;
                    }
                    Intent intent = new Intent(mContext, WebViewActivity.class);
                    intent.putExtra("url", item.getCouponurl());
                    intent.putExtra("itemid", item.getItemid());
                    mContext.startActivity(intent);
                }
            });
            holder.getView(R.id.la_shengji).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //升级会员
                    if (User.uid() <= 0) {
                        ToastUtil.show(mContext, "请先登录");
                        Intent intent = new Intent(mContext, SplashActivity.class);
                        mContext.startActivity(intent);
                        return;
                    }
                    Intent intent = new Intent(mContext, MyHuiYuanDetailActivity.class);
                    mContext.startActivity(intent);
                }
            });
        } else if (User.roleType().equals("1") || User.roleType().equals("2")) {

            holder.getView(R.id.la_fenxiang).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (User.uid() <= 0) {
                        ToastUtil.show(mContext, "请先登录");
                        Intent intent = new Intent(mContext, SplashActivity.class);
                        mContext.startActivity(intent);
                        return;
                    }
//                    if(StringUtil.isBlank(User.remainAdNum())){
//                        ToastUtil.show(mContext, "您的推广位id还未生成，请稍后再试");
//                        return;
//                    }
                    if(StringUtil.isBlank(User.deadline())||Long.parseLong(User.deadline())<System.currentTimeMillis()){
                        ToastUtil.show(mContext, "您的会员已到期,请先续费");
                        Intent intent = new Intent(mContext, MyHuiYuanDetailActivity.class);
                        mContext.startActivity(intent);
                        return;
                    }
                    Intent intent = new Intent(mContext, TaoBaoDetailActivity.class);
                    intent.putExtra("id", item.getItemid());
                    mContext.startActivity(intent);

                }
            });
            holder.getView(R.id.la_shengji).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //升级会员
                    if (User.uid() <= 0) {
                        ToastUtil.show(mContext, "请先登录");
                        Intent intent = new Intent(mContext, SplashActivity.class);
                        mContext.startActivity(intent);
                        return;
                    }
                    Intent intent = new Intent(mContext, MyHuiYuanDetailActivity.class);
                    mContext.startActivity(intent);
                }
            });
        } else if (User.roleType().equals("3")) {
            holder.getView(R.id.la_fenxiang).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //分享
                    if (User.uid() <= 0) {
                        ToastUtil.show(mContext, "请先登录");
                        Intent intent = new Intent(mContext, SplashActivity.class);
                        mContext.startActivity(intent);
                        return;
                    }
//                    if(StringUtil.isBlank(User.remainAdNum())){
//                        ToastUtil.show(mContext, "您的推广位id还未生成，请稍后再试");
//                        return;
//                    }
                    if(StringUtil.isBlank(User.deadline())||Long.parseLong(User.deadline())<System.currentTimeMillis()){
                        ToastUtil.show(mContext, "您的会员已到期,请先续费");
                        Intent intent = new Intent(mContext, MyHuiYuanDetailActivity.class);
                        mContext.startActivity(intent);
                        return;
                    }
                    Intent intent = new Intent(mContext, TaoBaoDetailActivity.class);
                    intent.putExtra("id", item.getItemid());
                    mContext.startActivity(intent);
                }
            });
            holder.setGone(R.id.la_shengji, true);
        }*/


    }
}
