package com.d2956987215.mow.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.d2956987215.mow.R;
import com.d2956987215.mow.rxjava.response.ConsumerProtectionBean;
import com.d2956987215.mow.rxjava.response.ShopInfoResponse;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Administrator on 2018/3/7.
 */

public class DetailShuomingAdapter extends BaseAdapter {
    private List<ShopInfoResponse.DataBean.ConsumerProtectionBean> data;
    private Context context;

    public DetailShuomingAdapter(Context context, List<ShopInfoResponse.DataBean.ConsumerProtectionBean> data) {
        this.data = data;
        this.context = context;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public ShopInfoResponse.DataBean.ConsumerProtectionBean getItem(int i) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_detail_shuoming, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.mTvName.setText(getItem(i).getTitle());
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.tv_name)
        TextView mTvName;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
