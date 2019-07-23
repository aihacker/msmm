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
import com.d2956987215.mow.rxjava.response.BrandResponse;
import com.d2956987215.mow.util.GlideRoundTransform;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/3/30.
 */

public class BrandGridViewAdpter extends BaseAdapter {

    private Context context;
    private List<BrandResponse.DataBean.BrandListBean> lists;//数据源


    public BrandGridViewAdpter(Context context, List<BrandResponse.DataBean.BrandListBean> lists) {
        this.context = context;
        this.lists = lists;
    }

    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public BrandResponse.DataBean.BrandListBean getItem(int arg0) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.itemgv_brandsale, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (!TextUtils.isEmpty(getItem(position).getBrandLogo())) {
            Glide.with(context).load(getItem(position).getBrandLogo()).transform(new GlideRoundTransform(context, 8)).into(holder.mIvIcon);
        }
        if (!TextUtils.isEmpty(getItem(position).getBrandName())) {
            holder.mTvName.setText(getItem(position).getBrandName());
        }
        if (!TextUtils.isEmpty(getItem(position).getBrandCommission())) {
            holder.mTvYongjin.setText(getItem(position).getBrandCommission());
        }
        return convertView;
    }


    static class ViewHolder {
        @BindView(R.id.iv_icon)
        ImageView mIvIcon;
        @BindView(R.id.tv_name)
        TextView mTvName;
        @BindView(R.id.tv_yongjin)
        TextView mTvYongjin;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}