package com.d2956987215.mow.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.d2956987215.mow.R;
import com.d2956987215.mow.rxjava.response.HomeDataResponse;
import com.d2956987215.mow.util.GlideRoundTransform;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/3/30.
 */

public class HomeActivityGridViewAdpter extends BaseAdapter {

    private Context context;
    private List<HomeDataResponse.DataBean.ActivityBean.ModuleListBean.ListBean> lists;//数据源


    public HomeActivityGridViewAdpter(Context context, List<HomeDataResponse.DataBean.ActivityBean.ModuleListBean.ListBean> lists) {
        this.context = context;
        this.lists = lists;
    }

    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public HomeDataResponse.DataBean.ActivityBean.ModuleListBean.ListBean getItem(int arg0) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_homeactivity, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (!TextUtils.isEmpty(getItem(position).getPicUrl())) {
            Glide.with(context).load(getItem(position).getPicUrl()).transform(new GlideRoundTransform(context, 8)).into(holder.mIvImg);
        }

        return convertView;
    }


    static class ViewHolder {
        @BindView(R.id.iv_img)
        ImageView mIvImg;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}