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
import com.d2956987215.mow.rxjava.response.LunTanSchoolCateResponse;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/3/30.
 */

public class SchoolTitleAdpter extends BaseAdapter {

    private Context context;
    private List<LunTanSchoolCateResponse.DataBean> lists;//数据源


    public SchoolTitleAdpter(Context context, List<LunTanSchoolCateResponse.DataBean> lists) {
        this.context = context;
        this.lists = lists;
    }

    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public LunTanSchoolCateResponse.DataBean getItem(int arg0) {
        return lists.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_school_title, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (!TextUtils.isEmpty(getItem(position).getName())) {
            holder.mTvName.setText(getItem(position).getName());
        }

        if (!TextUtils.isEmpty(getItem(position).getIco())) {
            Glide.with(context).load(getItem(position).getIco()).into(holder.mIvBg);
        }
        return convertView;
    }


    static class ViewHolder {
        @BindView(R.id.iv_bg)
        ImageView mIvBg;
        @BindView(R.id.tv_name)
        TextView mTvName;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}