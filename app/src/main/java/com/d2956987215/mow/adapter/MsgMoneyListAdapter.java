package com.d2956987215.mow.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.d2956987215.mow.R;
import com.d2956987215.mow.rxjava.response.MsgMoneyListResponse;

import java.util.List;

public class MsgMoneyListAdapter extends BaseQuickAdapter<MsgMoneyListResponse.DataBean, BaseViewHolder> {


    public MsgMoneyListAdapter(int layoutResId, @Nullable List<MsgMoneyListResponse.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, MsgMoneyListResponse.DataBean dataBean) {
        if (!TextUtils.isEmpty(dataBean.getContent())) {
            baseViewHolder.setText(R.id.tv_title, dataBean.getContent());
        }
        if (!TextUtils.isEmpty(dataBean.getOrderid())) {
            baseViewHolder.setText(R.id.tv_ordernum, "订单号：" + dataBean.getOrderid());
        }
        if (!TextUtils.isEmpty(dataBean.getSourceName())) {
            baseViewHolder.setText(R.id.tv_ordertype, "订单类型：" + dataBean.getSourceName());
        }
        if (!TextUtils.isEmpty(dataBean.getOrderAmount())) {
            baseViewHolder.setText(R.id.tv_ordermoney, "订单金额：" + dataBean.getOrderAmount());
        }
        if (!TextUtils.isEmpty(dataBean.getCommissionAmount())) {
            baseViewHolder.setText(R.id.tv_getmoney, "获得普通佣金：" + dataBean.getCommissionAmount());
        }
    }
}
