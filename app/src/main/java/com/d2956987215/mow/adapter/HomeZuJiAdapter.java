package com.d2956987215.mow.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.d2956987215.mow.R;
import com.d2956987215.mow.rxjava.response.MyZuJiResponse;
import com.d2956987215.mow.widgets.ShapedImageView;

import java.util.List;

/**
 * Created by Administrator on 2018/3/7.
 */

public class HomeZuJiAdapter extends BaseQuickAdapter<MyZuJiResponse.DataBean, BaseViewHolder> {

    public HomeZuJiAdapter(@LayoutRes int layoutResId, @Nullable List<MyZuJiResponse.DataBean> data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(final BaseViewHolder holder, MyZuJiResponse.DataBean item) {

        ShapedImageView iv_pic = holder.getView(R.id.iv_pic);
        ShapedImageView iv_logo = holder.getView(R.id.iv_logo);

        Glide.with(mContext).load(item.getGoods_pic()).error(R.mipmap.da_tu).into(iv_pic);
        holder.setText(R.id.tv_name, item.getGoods_title());
        holder.setText(R.id.tv_zhuce, item.getCreated_at());
        holder.setText(R.id.tv_liulan, "浏览次数:" + item.getGoods_vit());
        holder.setText(R.id.tv_nowprice, "￥" + item.getGoods_price());
        if (TextUtils.equals(item.getType(),"C")) {
            iv_logo.setBackground(mContext.getResources().getDrawable(R.mipmap.home_icon_taobao));
            holder.setText(R.id.tv_pinlei, "淘宝");
        } else {
            iv_logo.setBackground(mContext.getResources().getDrawable(R.mipmap.icon_tianmao));
            holder.setText(R.id.tv_pinlei, "天猫");
        }
    }
}
