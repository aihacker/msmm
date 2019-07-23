package com.d2956987215.mow.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import com.d2956987215.mow.R;
import com.d2956987215.mow.rxjava.response.ShouTitleResponse;
import com.d2956987215.mow.widgets.ShapedImageView;

/**
 * Created by Administrator on 2018/3/7.
 */

public class ShouYeAdapter extends BaseQuickAdapter<ShouTitleResponse.DataBean.CateBean, BaseViewHolder> {
    public ShouYeAdapter(@LayoutRes int layoutResId, @Nullable List<ShouTitleResponse.DataBean.CateBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ShouTitleResponse.DataBean.CateBean item) {
        ShapedImageView iv_head = helper.getView(R.id.iv_head);
        Glide.with(mContext).load(item.getPic()).error(R.mipmap.have_no_head).into(iv_head);
        helper.setText(R.id.tv_name, item.getName());

    }
}
