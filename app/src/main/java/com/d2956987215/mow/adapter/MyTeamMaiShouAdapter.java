package com.d2956987215.mow.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.d2956987215.mow.R;
import com.d2956987215.mow.rxjava.response.MyTeamResponse;
import com.d2956987215.mow.widgets.CircleImageView;

import java.util.List;

import mtopsdk.common.util.StringUtils;

/**
 * Created by Administrator on 2018/3/7.
 */

public class MyTeamMaiShouAdapter extends BaseQuickAdapter<MyTeamResponse.DataBean.ListBean, BaseViewHolder> {
    public MyTeamMaiShouAdapter(@LayoutRes int layoutResId, @Nullable List<MyTeamResponse.DataBean.ListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, MyTeamResponse.DataBean.ListBean item) {
        TextView tv_shoucang = holder.getView(R.id.tv_shoucang);
        CircleImageView head_img = holder.getView(R.id.head_img);
        Glide.with(mContext).load(item.getAvatar()).error(R.mipmap.da_tu).into(head_img);
        holder.setText(R.id.tv_name, item.getWw());
        holder.setText(R.id.tv_id, item.getStudentId());

        holder.setText(R.id.tv_zhuce, "注册时间:" + item.getCreatetime());
        holder.setText(R.id.tv_guoqi, "过期时间:" + item.getDeadline().getDeadline());
        if (!StringUtils.isEmpty(item.getDeadline().getIs_kt())) {
            if (item.getDeadline().getIs_kt().equals("1")) {
                tv_shoucang.setTextColor(mContext.getResources().getColor(R.color.hot_product));
                tv_shoucang.setBackground(mContext.getResources().getDrawable(R.drawable.bg_red));
                tv_shoucang.setText("已开通");

            } else {
                tv_shoucang.setTextColor(mContext.getResources().getColor(R.color.white));
                tv_shoucang.setBackground(mContext.getResources().getDrawable(R.drawable.bg_loginred));
                tv_shoucang.setText("开通");

            }

        }
    }
}
