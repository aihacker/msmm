package com.d2956987215.mow.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.d2956987215.mow.R;
import com.d2956987215.mow.rxjava.response.EvaluatesBean;
import com.d2956987215.mow.rxjava.response.ShopInfoResponse;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Administrator on 2018/3/7.
 */

public class DetailServiseAdapter extends BaseAdapter {
    private List<ShopInfoResponse.DataBean.SellerBean.EvaluatesBean> data;
    private Context context;

    public DetailServiseAdapter(Context context, List<ShopInfoResponse.DataBean.SellerBean.EvaluatesBean> data) {
        this.data = data;
        this.context = context;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public ShopInfoResponse.DataBean.SellerBean.EvaluatesBean getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_detail_service, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.mTvLevel.setText(getItem(i).getLevelText());
        holder.mTvLevel.setTextColor(Color.parseColor(getItem(i).getLevelTextColor()));
        holder.mTvLevel.setBackgroundColor(Color.parseColor(getItem(i).getLevelBackgroundColor()));
        holder.mTvName.setText(getItem(i).getTitle());
        holder.mTvScore.setText(getItem(i).getScore());
        holder.mTvScore.setTextColor(Color.parseColor(getItem(i).getLevelTextColor()));
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.tv_name)
        TextView mTvName;
        @BindView(R.id.tv_score)
        TextView mTvScore;
        @BindView(R.id.tv_level)
        TextView mTvLevel;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
