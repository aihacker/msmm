package com.d2956987215.mow.adapter;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.d2956987215.mow.R;
import com.d2956987215.mow.activity.home.GuideWebViewActivity;
import com.d2956987215.mow.rxjava.response.MsgActivityResponse;
import com.d2956987215.mow.rxjava.response.MsgSystemResponse;
import com.d2956987215.mow.util.DisplayUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MsgNoticeListAdapter extends BaseQuickAdapter<MsgActivityResponse.DataBean, BaseViewHolder> {


    public MsgNoticeListAdapter(int layoutResId, @Nullable List<MsgActivityResponse.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, final MsgActivityResponse.DataBean dataBean) {
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
            ImageView imageView = baseViewHolder.getView(R.id.iv_img);
            Glide.with(mContext).load(dataBean.getPicUrl()).into(imageView);
        }

        LinearLayout ll_end = baseViewHolder.getView(R.id.ll_end);
        if (DisplayUtil.getCurrTime() > dataBean.getActivityEndTime()) {
            ll_end.setVisibility(View.VISIBLE);
        } else {
            ll_end.setVisibility(View.GONE);
        }
        LinearLayout ll_item = baseViewHolder.getView(R.id.ll_item);
        ll_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (DisplayUtil.getCurrTime() < dataBean.getActivityEndTime()) {
                    Intent intent = new Intent(mContext, GuideWebViewActivity.class);
                    intent.putExtra("url", dataBean.getUrl());
                    intent.putExtra("name", dataBean.getTitle());
                    mContext.startActivity(intent);
                }

            }
        });

    }

    private String getTime(long time, String formatStr) {
        Date date = new Date(time * 1000);
        SimpleDateFormat format = new SimpleDateFormat(formatStr);
        return format.format(date);
    }
}
