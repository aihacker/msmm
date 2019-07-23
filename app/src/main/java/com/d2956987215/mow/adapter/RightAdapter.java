package com.d2956987215.mow.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.d2956987215.mow.rxjava.response.FenLeiListResponse;
import com.d2956987215.mow.widgets.ShapedImageView;

import java.util.List;

import com.d2956987215.mow.R;
import com.d2956987215.mow.rxjava.response.FenLeiListResponse;
import com.d2956987215.mow.widgets.ShapedImageView;

/**
 * Created by Administrator on 2018/3/7.
 */

public class RightAdapter extends BaseQuickAdapter<FenLeiListResponse.DataBean.SonBean, BaseViewHolder> {
    public RightAdapter(@LayoutRes int layoutResId, @Nullable List<FenLeiListResponse.DataBean.SonBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, FenLeiListResponse.DataBean.SonBean item) {
        ShapedImageView iv_head = helper.getView(R.id.iv_head);
        Glide.with(mContext).load(item.getPic()).error(R.mipmap.have_no_head).into(iv_head);
        helper.setText(R.id.tv_name, item.getName());

    }
}
