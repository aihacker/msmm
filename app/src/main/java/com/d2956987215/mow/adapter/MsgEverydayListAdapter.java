package com.d2956987215.mow.adapter;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.d2956987215.mow.R;
import com.d2956987215.mow.activity.home.TaoBaoDetailActivity;
import com.d2956987215.mow.rxjava.response.MsgActivityResponse;
import com.d2956987215.mow.rxjava.response.MsgEverydayResponse;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MsgEverydayListAdapter extends BaseQuickAdapter<MsgEverydayResponse.DataBean, BaseViewHolder> {


    public MsgEverydayListAdapter(int layoutResId, @Nullable List<MsgEverydayResponse.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, final MsgEverydayResponse.DataBean dataBean) {
        if (dataBean.getPushTime() > 0) {
            baseViewHolder.setText(R.id.tv_time, getTime(dataBean.getPushTime(), "MM-dd hh:MM"));
        }
        if (!TextUtils.isEmpty(dataBean.getTitle())) {
            baseViewHolder.setText(R.id.tv_title, dataBean.getTitle());
        }
        if (!TextUtils.isEmpty(dataBean.getContent())) {
            baseViewHolder.setText(R.id.tv_content, dataBean.getContent());
        }

        if (!TextUtils.isEmpty(dataBean.getPicUrl())) {
            ImageView imageView = baseViewHolder.getView(R.id.iv_icon);
            Glide.with(mContext).load(dataBean.getPicUrl()).into(imageView);
        }

        LinearLayout item = baseViewHolder.getView(R.id.item);
        item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, TaoBaoDetailActivity.class);
                intent.putExtra("id", dataBean.getId());
                intent.putExtra("quan_id", dataBean.getQuan_id());
                mContext.startActivity(intent);
            }
        });
    }

    private String getTime(long time, String formatStr) {
        Date date = new Date(time * 1000);
        SimpleDateFormat format = new SimpleDateFormat(formatStr);
        return format.format(date);
    }
}
