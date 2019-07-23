package com.d2956987215.mow.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.d2956987215.mow.R;
import com.d2956987215.mow.rxjava.response.MsgMoneyListResponse;
import com.d2956987215.mow.rxjava.response.MsgSystemResponse;
import com.d2956987215.mow.util.DateUtil;
import com.d2956987215.mow.util.DateUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MsgSystemListAdapter extends BaseQuickAdapter<MsgSystemResponse.DataBean, BaseViewHolder> {


    public MsgSystemListAdapter(int layoutResId, @Nullable List<MsgSystemResponse.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, MsgSystemResponse.DataBean dataBean) {
        if (!TextUtils.isEmpty(dataBean.getCreateTime())) {
            baseViewHolder.setText(R.id.tv_time, getTime(Long.parseLong(dataBean.getCreateTime()), "MM-dd hh:MM"));
        }
        if (!TextUtils.isEmpty(dataBean.getTitle())) {
            baseViewHolder.setText(R.id.tv_title, dataBean.getTitle());
        }

        LinearLayout ll_time = baseViewHolder.getView(R.id.ll_time);
        LinearLayout ll_message = baseViewHolder.getView(R.id.ll_message);
        ll_time.post(() -> {
            ViewGroup.LayoutParams layoutParams = ll_time.getLayoutParams();
            layoutParams.height=ll_message.getHeight();
            layoutParams.width=ll_time.getWidth();
            ll_time.setLayoutParams(layoutParams);
        });


        if (!TextUtils.isEmpty(dataBean.getContent())) {
            baseViewHolder.setText(R.id.tv_content, dataBean.getContent());
        }
    }

    private String getTime(long time, String formatStr) {
        Date date = new Date(time * 1000);
        SimpleDateFormat format = new SimpleDateFormat(formatStr);
        return format.format(date);
    }
}
