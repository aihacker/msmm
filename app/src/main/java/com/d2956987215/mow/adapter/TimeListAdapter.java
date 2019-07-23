package com.d2956987215.mow.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.d2956987215.mow.R;
import com.d2956987215.mow.rxjava.response.TimeListResponse;
import com.d2956987215.mow.widgets.ShapedImageView;

import java.util.List;

/**
 * Created by Administrator on 2018/3/7.
 */

public class TimeListAdapter extends BaseQuickAdapter<TimeListResponse.DataBean, BaseViewHolder> {
    public TimeListAdapter(@LayoutRes int layoutResId, @Nullable List<TimeListResponse.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, TimeListResponse.DataBean item) {
        ShapedImageView iv_pic = holder.getView(R.id.iv_pic);
        ShapedImageView iv_logo = holder.getView(R.id.iv_logo);
        LinearLayout la_xuanze = holder.getView(R.id.la_xuanze);
        TextView tv_time = holder.getView(R.id.tv_time);
        TextView tv_content = holder.getView(R.id.tv_content);
        tv_time.setText(item.getTime());
        tv_content.setText(item.getStatus());
        if (item.isSelete() == true) {
            la_xuanze.setBackground(mContext.getResources().getDrawable(R.drawable.bg_whrite));
            tv_time.setTextColor(mContext.getResources().getColor(R.color.hot_product));
            tv_content.setTextColor(mContext.getResources().getColor(R.color.hot_product));
        } else {
            la_xuanze.setBackground(mContext.getResources().getDrawable(R.color.wuse));
            tv_time.setTextColor(mContext.getResources().getColor(R.color.white));
            tv_content.setTextColor(mContext.getResources().getColor(R.color.white));
        }


    }
}
