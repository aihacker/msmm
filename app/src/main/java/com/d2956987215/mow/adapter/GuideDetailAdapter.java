package com.d2956987215.mow.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.d2956987215.mow.rxjava.response.GuiDetailResponse;
import com.d2956987215.mow.widgets.ShapedImageView;

import java.util.List;

import com.d2956987215.mow.R;
import com.d2956987215.mow.rxjava.response.GuiDetailResponse;
import com.d2956987215.mow.rxjava.response.GuiListResponse;
import com.d2956987215.mow.widgets.ShapedImageView;

/**
 * Created by Administrator on 2018/3/7.
 */

public class GuideDetailAdapter extends BaseQuickAdapter<GuiDetailResponse.DataBean.TinfoBean, BaseViewHolder> {
    public GuideDetailAdapter(@LayoutRes int layoutResId, @Nullable List<GuiDetailResponse.DataBean.TinfoBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, GuiDetailResponse.DataBean.TinfoBean item) {
        ShapedImageView iv_pic = holder.getView(R.id.iv_pic);

        Glide.with(mContext).load(item.getResp_img()).error(R.mipmap.da_tu).into(iv_pic);

        holder.setText(R.id.tv_name, item.getArticle_title());
        holder.setText(R.id.tv_content, item.getResp_desc());
        holder.setText(R.id.tv_liulan, item.getArticle_readnum());
        holder.setText(R.id.tv_dianzan, item.getArticle_likenum());


    }
}
