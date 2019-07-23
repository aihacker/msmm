package com.d2956987215.mow.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.d2956987215.mow.R;
import com.d2956987215.mow.rxjava.response.ShouYeErjiResponse;
import com.d2956987215.mow.widgets.ShapedImageView;

import java.util.List;

/**
 * Created by Administrator on 2018/3/7.
 */

public class ShouYeErJiAdapter extends BaseQuickAdapter<ShouYeErjiResponse.DataBean, BaseViewHolder> {
    public ShouYeErJiAdapter(@LayoutRes int layoutResId, @Nullable List<ShouYeErjiResponse.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ShouYeErjiResponse.DataBean item) {
        ShapedImageView iv_head = helper.getView(R.id.iv_head);
        Glide.with(mContext).load(item.getPic()).placeholder(R.mipmap.da_tu).error(R.mipmap.da_tu).into(iv_head);
        helper.setText(R.id.tv_name, item.getName());

    }
}
