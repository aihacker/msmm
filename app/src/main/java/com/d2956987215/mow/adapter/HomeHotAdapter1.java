package com.d2956987215.mow.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.d2956987215.mow.R;
import com.d2956987215.mow.activity.home.GuideWebViewActivity;
import com.d2956987215.mow.activity.home.ShareZhuanActivity;
import com.d2956987215.mow.activity.home.TaoBaoDetailActivity;
import com.d2956987215.mow.activity.mine.MyHuiYuanDetailActivity;
import com.d2956987215.mow.rxjava.response.HomeListResponse;
import com.d2956987215.mow.util.MoneyUtils;
import com.d2956987215.mow.util.StringUtil;
import com.d2956987215.mow.util.User;
import com.d2956987215.mow.widgets.ShapedImageView;
import com.d2956987215.mow.widgets.adapters.BaseRecyclerAdapter;
import com.d2956987215.mow.widgets.adapters.BaseRecyclerViewHolder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/3/7.
 */

public class HomeHotAdapter1 extends BaseRecyclerAdapter<HomeListResponse.DataBeanX.DataBean> {

    public HomeHotAdapter1(Context context, List<HomeListResponse.DataBeanX.DataBean> datas) {
        super(context, datas);
    }

    @Override
    protected void showViewHolder(BaseRecyclerViewHolder holder, int position) {

        Log.e("HomeHotAdapter",System.currentTimeMillis()+"");
        Log.e("HomeHotAdapter","-------------------------------");
        MyGridHolder holder1 = (MyGridHolder) holder;
        final HomeListResponse.DataBeanX.DataBean item = mDatas.get(position);
        Glide.with(mContext).load(item.getItempic()).placeholder(R.mipmap.da_tu).error(R.mipmap.da_tu).into(holder1.ivPic);
        holder1.tvName.setText(item.getItemtitle());

        holder1.tvQuancount.setText("￥" + item.getCouponmoney());
        holder1.tvTotal.setText("￥" + item.getItemprice());
        holder1.tvNowprice.setText("￥" + item.getItemendprice());
        holder1.tvDianming.setText(item.getSellernick());
        holder1.tvYishou.setText("已售" + (StringUtil.isBlank(item.getItemsale()) ? 0 : MoneyUtils.getMoney(item.getItemsale())));
        if (!StringUtil.isBlank(item.getGive_money())) {
            holder1.tvShengji.setText( "￥" + item.getGive_money());
        } else {
            holder1.tvShengji.setText("￥0");
        }
        if (!StringUtil.isBlank(item.getShare_money())) {
            holder1.tvFenxiang.setText("￥" + item.getShare_money());
        } else {
            holder1.tvFenxiang.setText("￥0");
        }
        if (item.getShoptype().equals("B")) {
            holder1.ivLogo.setBackground(mContext.getResources().getDrawable(R.mipmap.icon_tianmao));
        } else {
            holder1.ivLogo.setBackground(mContext.getResources().getDrawable(R.mipmap.home_icon_taobao));
        }

        if (User.roleType().equals("0")) {
//            holder.setText(R.id.tv_fenxiang_title, "领券");
            holder1.tvFenxiangTitle.setText("领券");
            holder1.tvFenxiang.setVisibility(View.GONE);
            holder1.laFenxiang.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //领券
                    Intent intent = new Intent(mContext, GuideWebViewActivity.class);
                    intent.putExtra("url", item.getCouponurl());
                    intent.putExtra("itemid", item.getItemid());
                    mContext.startActivity(intent);
                }
            });
            holder1.laShengji.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //升级会员
                    Intent intent = new Intent(mContext, MyHuiYuanDetailActivity.class);
                    mContext.startActivity(intent);
                }
            });
        } else if (User.roleType().equals("1")||User.roleType().equals("2")) {

            holder1.laFenxiang.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //分享
                    Intent intent = new Intent(mContext, ShareZhuanActivity.class);
                    intent.putExtra("goods",item.getItemid());
                    mContext.startActivity(intent);
                }
            });
            holder1.laShengji.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //升级会员
                    Intent intent = new Intent(mContext, MyHuiYuanDetailActivity.class);
                    mContext.startActivity(intent);
                }
            });
        }  else if (User.roleType().equals("3")) {
            holder1.laFenxiang.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //分享
                    Intent intent = new Intent(mContext, TaoBaoDetailActivity.class);
                    intent.putExtra("id",item.getItemid());
                    intent.putExtra("quan_id",item.getQuan_id());
                    mContext.startActivity(intent);
                }
            });
            holder1.laShengji.setVisibility(View.GONE);
        }

    }

    @Override
    protected BaseRecyclerViewHolder createViewMyHolder(ViewGroup parent, int viewType) {
        return new MyGridHolder(mInflater.inflate(R.layout.adapter_hot_product, parent, false));
    }

    class MyGridHolder extends BaseRecyclerViewHolder {
        @BindView(R.id.iv_pic)
        ShapedImageView ivPic;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_quancount)
        TextView tvQuancount;
        @BindView(R.id.tv_yishou)
        TextView tvYishou;
        @BindView(R.id.tv_shengji_title)
        TextView tvShengjiTitle;
        @BindView(R.id.tv_shengji)
        TextView tvShengji;
        @BindView(R.id.la_shengji)
        LinearLayout laShengji;
        @BindView(R.id.la_quan)
        LinearLayout laQuan;
        @BindView(R.id.tv_fenxiang_title)
        TextView tvFenxiangTitle;
        @BindView(R.id.tv_fenxiang)
        TextView tvFenxiang;
        @BindView(R.id.la_fenxiang)
        LinearLayout laFenxiang;
        @BindView(R.id.tv_nowprice)
        TextView tvNowprice;
        @BindView(R.id.tv_total)
        TextView tvTotal;
        @BindView(R.id.iv_logo)
        ShapedImageView ivLogo;
        @BindView(R.id.tv_dianming)
        TextView tvDianming;
        @BindView(R.id.la_hehe)
        RelativeLayout laHehe;

        public MyGridHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
