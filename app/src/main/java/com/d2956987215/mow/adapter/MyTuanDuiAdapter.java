package com.d2956987215.mow.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.d2956987215.mow.R;
import com.d2956987215.mow.rxjava.response.ErJiListResponse;
import com.d2956987215.mow.widgets.CircleImageView;

import java.util.List;

/**
 * Created by Administrator on 2018/3/7.
 */

public class MyTuanDuiAdapter extends BaseQuickAdapter<ErJiListResponse.DataBeanX.DataBean, BaseViewHolder> {
    public MyTuanDuiAdapter(@LayoutRes int layoutResId, @Nullable List<ErJiListResponse.DataBeanX.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, ErJiListResponse.DataBeanX.DataBean item) {
        CircleImageView head_img = holder.getView(R.id.head_img);

        Glide.with(mContext).load(item.getItempic()).error(R.mipmap.da_tu).into(head_img);
     //   holder.setText(R.id.tv_name, item.getItemshorttitle());




    }
}
