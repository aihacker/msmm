package com.d2956987215.mow.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.d2956987215.mow.R;
import com.d2956987215.mow.activity.home.TaoBaoDetailActivity;
import com.d2956987215.mow.rxjava.response.BrandListResponse;
import com.d2956987215.mow.util.DisplayUtil;
import com.d2956987215.mow.util.DisplayUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/3/30.
 */

public class BrandFrgOneItemAdpter extends BaseAdapter {

    private Context context;
    private List<BrandListResponse.DataBean.ItemBean> lists;//数据源


    public BrandFrgOneItemAdpter(Context context, List<BrandListResponse.DataBean.ItemBean> lists) {
        this.context = context;
        this.lists = lists;
    }

    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public BrandListResponse.DataBean.ItemBean getItem(int arg0) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_brandoneitem, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (!TextUtils.isEmpty(getItem(position).getItempic())) {
            int width = (DisplayUtils.getScreenWidth((Activity) context) - DisplayUtil.dip2px(context, 12 * 2 + 8 * 4)) / 3;
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) holder.mIvImg.getLayoutParams();
            params.height = width;
            holder.mIvImg.setLayoutParams(params);
            Glide.with(context).load(getItem(position).getItempic()).into(holder.mIvImg);
        }
        if (!TextUtils.isEmpty(getItem(position).getItemprice())) {
            holder.mTvPrice.setText("￥" + getItem(position).getItemprice());
        }
        if (!TextUtils.isEmpty(getItem(position).getGive_money())) {
            holder.mTvZhuan.setText("预估赚￥" + getItem(position).getGive_money());
        }

        if (!TextUtils.isEmpty(getItem(position).getCouponmoney())) {
            holder.mTvQuan.setText("￥" + getItem(position).getCouponmoney());
        }
        holder.mItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, TaoBaoDetailActivity.class);
                intent.putExtra("id", getItem(position).getItemid());
                intent.putExtra("quan_id", getItem(position).getQuan_id());
                context.startActivity(intent);
            }
        });
        return convertView;
    }


    class ViewHolder {
        @BindView(R.id.iv_img)
        ImageView mIvImg;
        @BindView(R.id.tv_quan)
        TextView mTvQuan;
        @BindView(R.id.tv_price)
        TextView mTvPrice;
        @BindView(R.id.tv_zhuan)
        TextView mTvZhuan;
        @BindView(R.id.item)
        LinearLayout mItem;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}