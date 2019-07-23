package com.d2956987215.mow.adapter;

import android.content.ClipboardManager;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.d2956987215.mow.R;
import com.d2956987215.mow.rxjava.response.MyOrderList;
import com.d2956987215.mow.util.ToastUtil;
import com.d2956987215.mow.widgets.ShapedImageView;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by Administrator on 2018/3/7.
 */

public class OrderAdapter extends BaseQuickAdapter<MyOrderList.DataBeanX.DataBean, BaseViewHolder> {
    public OrderAdapter(@LayoutRes int layoutResId, @Nullable List<MyOrderList.DataBeanX.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, final MyOrderList.DataBeanX.DataBean item) {
        ShapedImageView iv_pic = holder.getView(R.id.iv_pic);
//        ShapedImageView iv_logo = holder.getView(R.id.iv_logo);

        Glide.with(mContext).load(item.getPicture()).placeholder(R.mipmap.da_tu).error(R.mipmap.da_tu).into(iv_pic);
        holder.setText(R.id.tv_zhuangtai, item.getOrderzt());

        holder.setText(R.id.tv_name, item.getTitle());
        holder.setText(R.id.tv_yugu, "消费预估：￥" + item.getFkprice());
        holder.setText(R.id.tv_danhui, "返还" + item.getDlyj() + "元");
        holder.setText(R.id.tv_dingdan, "订单号：" + item.getOrderid());
        holder.setText(R.id.tv_zhuce, "下单日:" + item.getCreatetime());
//        holder.setText(R.id.tv_guoqi, TimeUtils.millis2String(Integer.valueOf(item.getAddtime()) * 1000L, new SimpleDateFormat("yyyy-MM-dd"))+ "结算");
        holder.setText(R.id.tv_guoqi, "结算日:" + item.getJstime());

        ImageView imageView = holder.getView(R.id.iv_fromType);

        if (item.getShoptype().equals("B")) {
            imageView.setImageResource(R.mipmap.icon_tianmao);
            holder.setText(R.id.tv_fromName, "天猫");
        } else if (item.getShoptype().equals("C")) {
            imageView.setImageResource(R.mipmap.icon_taobao);
            holder.setText(R.id.tv_fromName, "淘宝");
        } else if (item.getShoptype().equals("J")) {
            imageView.setImageResource(R.mipmap.icon_jingdong);
            holder.setText(R.id.tv_fromName, "京东");
        } else if (item.getShoptype().equals("P")) {
            imageView.setImageResource(R.mipmap.icon_pinduoduo);
            holder.setText(R.id.tv_fromName, "拼多多");
        }


        holder.getView(R.id.tv_copy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager cm = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
                // 将文本内容放到系统剪贴板里。
                cm.setText(item.getOrderid());
                ToastUtils.showShort("复制成功!");
            }
        });

//        String shoptype = item.getShoptype();
//
//        if (TextUtils.isEmpty(shoptype)) {
//            return;
//        }
//        switch (shoptype) {
//            case "B":
//                holder.setText(R.id.tv_dianming, "天猫");
//                iv_logo.setBackground(mContext.getResources().getDrawable(R.mipmap.icon_tianmao));
//                break;
//            case "C":
//                holder.setText(R.id.tv_dianming, "淘宝");
//                iv_logo.setBackground(mContext.getResources().getDrawable(R.mipmap.home_icon_taobao));
//                break;
//            default:
//
//                break;
//
//        }
//        if (item.getShoptype().equals("B")) {
//            iv_logo.setBackground(mContext.getResources().getDrawable(R.mipmap.icon_tianmao));
//        } else {
//            iv_logo.setBackground(mContext.getResources().getDrawable(R.mipmap.home_icon_taobao));
//        }


    }
}
