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
import com.d2956987215.mow.rxjava.response.MemberUpgradeResponse;
import com.d2956987215.mow.util.GlideRoundTransform;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/3/30.
 */

public class MyAuthortyGvAdpter extends BaseAdapter {

    private Context context;
    private List<MemberUpgradeResponse.DataBean.IsopenBean> lists;//数据源


    public MyAuthortyGvAdpter(Context context, List<MemberUpgradeResponse.DataBean.IsopenBean> lists) {
        this.context = context;
        this.lists = lists;
    }

    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public MemberUpgradeResponse.DataBean.IsopenBean getItem(int arg0) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_myauthority, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (!TextUtils.isEmpty(getItem(position).getIcon())) {
            Glide.with(context).load(getItem(position).getIcon()).into(holder.mAuthortyIcon);
        }
        if (!TextUtils.isEmpty(getItem(position).getTitle())) {
            holder.mAuthortyTitle.setText(getItem(position).getTitle());
        }
        if (!TextUtils.isEmpty(getItem(position).getText())) {
            holder.mAuthortySmalltitle.setText(getItem(position).getText());
        }

        return convertView;
    }


    static class ViewHolder {
        @BindView(R.id.authorty_icon)
        ImageView mAuthortyIcon;
        @BindView(R.id.authorty_title)
        TextView mAuthortyTitle;
        @BindView(R.id.authorty_smalltitle)
        TextView mAuthortySmalltitle;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}