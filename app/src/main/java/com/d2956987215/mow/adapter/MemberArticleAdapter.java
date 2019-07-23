package com.d2956987215.mow.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.d2956987215.mow.R;
import com.d2956987215.mow.activity.home.OrdinaryWebViewActivity;
import com.d2956987215.mow.rxjava.response.MemberUpgradeResponse;
import com.d2956987215.mow.util.GlideRoundTransform;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MemberArticleAdapter extends BaseAdapter {
    private Context context;
    private List<MemberUpgradeResponse.DataBean.ArticleBean> list;

    public MemberArticleAdapter(Context context, List<MemberUpgradeResponse.DataBean.ArticleBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public MemberUpgradeResponse.DataBean.ArticleBean getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View convertView, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_member_article, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (!TextUtils.isEmpty(getItem(i).getImages())) {
            Glide.with(context).load(getItem(i).getImages()).transform(new GlideRoundTransform(context, 10)).into(holder.mIvImg);
        }

        holder.mIvImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, OrdinaryWebViewActivity.class);
                intent.putExtra("url", getItem(i).getUrl());
                intent.putExtra("name", getItem(i).getTitle());
                context.startActivity(intent);
            }
        });
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
