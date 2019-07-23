package com.d2956987215.mow.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.d2956987215.mow.R;
import com.d2956987215.mow.rxjava.response.HomeDataResponse;
import com.d2956987215.mow.util.DateUtil;
import com.d2956987215.mow.util.GlideRoundTransform;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/3/30.
 */

public class HomeIntelligenceGridViewAdpter extends BaseAdapter {

    private Context context;
    private List<HomeDataResponse.DataBean.IntelligenceBean> lists;//数据源


    public HomeIntelligenceGridViewAdpter(Context context, List<HomeDataResponse.DataBean.IntelligenceBean> lists) {
        this.context = context;
        this.lists = lists;
    }

    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public HomeDataResponse.DataBean.IntelligenceBean getItem(int arg0) {
        return lists.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_homeintelligence, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        ViewHolder finalHolder = holder;
        ViewHolder finalHolder1 = holder;
//        holder.mIvImg.post(() -> {
//            int width = finalHolder.mIvImg.getWidth();
//            int height = finalHolder.mIvImg.getHeight();
//            ViewGroup.LayoutParams layoutParams = finalHolder.mIvImg.getLayoutParams();
//            if(width>=height){
//                layoutParams.height=width;
//                layoutParams.width=width;
//                finalHolder.mIvImg.setLayoutParams(layoutParams);
//            }else {
//                layoutParams.height=height;
//                layoutParams.width=height;
//                finalHolder.mIvImg.setLayoutParams(layoutParams);
//            }
//
//
//
//
//        });
        if (!TextUtils.isEmpty(getItem(position).getPicUrl())) {
            Glide.with(context).load(getItem(position).getPicUrl()).transform(new GlideRoundTransform(context, 8)).into(finalHolder1.mIvImg);
        }
        if (!TextUtils.isEmpty(getItem(position).getTitle())) {
            finalHolder1.mTvTitle.setText(getItem(position).getTitle());
        }
        if (!TextUtils.isEmpty(getItem(position).getSubTitle())) {
            finalHolder1.mTvTips.setText(getItem(position).getSubTitle());
        }
        if (!TextUtils.isEmpty(getItem(position).getPlatformDesc())) {
            String ss = getItem(position).getPlatformDesc() + " | ";
            if (getItem(position).getCreateTime() > 0) {
                ss += getTime(getItem(position).getCreateTime(), "MM-dd");
            }
            finalHolder1.mTvTime.setText(ss);
        }



        return convertView;
    }


    static class ViewHolder {
        @BindView(R.id.iv_img)
        ImageView mIvImg;
        @BindView(R.id.tv_title)
        TextView mTvTitle;
        @BindView(R.id.tv_tips)
        TextView mTvTips;
        @BindView(R.id.tv_time)
        TextView mTvTime;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    private String getTime(long time, String formatStr) {
        Date date = new Date(time * 1000);
        SimpleDateFormat format = new SimpleDateFormat(formatStr);
        return format.format(date);
    }
}