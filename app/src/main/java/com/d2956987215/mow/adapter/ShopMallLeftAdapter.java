package com.d2956987215.mow.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import com.d2956987215.mow.R;
import com.d2956987215.mow.rxjava.response.FenLeiListResponse;

/**
 * Created by Administrator on 2018/3/10.
 */

public class ShopMallLeftAdapter extends BaseQuickAdapter<FenLeiListResponse.DataBean, BaseViewHolder> {

    public ShopMallLeftAdapter(@LayoutRes int layoutResId, @Nullable List<FenLeiListResponse.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper,FenLeiListResponse.DataBean item) {
        TextView name = helper.getView(R.id.shop_item_name);
        name.setText(item.getName());

        if (item.isSelete() == true) {
            name.setTextColor(mContext.getResources().getColor(R.color.white));
            name.setBackground(mContext.getResources().getDrawable(R.drawable.bg_left_read));
        } else {
            name.setTextColor(mContext.getResources().getColor(R.color.dark));
            name.setBackgroundColor(mContext.getResources().getColor(R.color.white));
        }
    }
}
