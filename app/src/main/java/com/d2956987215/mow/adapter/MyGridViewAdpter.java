package com.d2956987215.mow.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.d2956987215.mow.R;
import com.d2956987215.mow.activity.SplashActivity;
import com.d2956987215.mow.rxjava.response.HomeDataResponse;
import com.d2956987215.mow.util.User;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/3/30.
 */

public class MyGridViewAdpter extends BaseAdapter {

    private Context context;
    private List<HomeDataResponse.DataBean.PlatformBean> lists;//数据源
    private int mIndex; // 页数下标，标示第几页，从0开始
    private int mPargerSize;// 每页显示的最大的数量


    public MyGridViewAdpter(Context context, List<HomeDataResponse.DataBean.PlatformBean> lists,
                            int mIndex, int mPargerSize) {
        this.context = context;
        this.lists = lists;
        this.mIndex = mIndex;
        this.mPargerSize = mPargerSize;
    }

    /**
     * 先判断数据及的大小是否显示满本页lists.size() > (mIndex + 1)*mPagerSize
     * 如果满足，则此页就显示最大数量lists的个数
     * 如果不够显示每页的最大数量，那么剩下几个就显示几个
     */
    @Override
    public int getCount() {
        return lists.size() > (mIndex + 1) * mPargerSize ?
                mPargerSize : (lists.size() - mIndex * mPargerSize);
    }

    @Override
    public HomeDataResponse.DataBean.PlatformBean getItem(int arg0) {
        return lists.get(arg0 + mIndex * mPargerSize);
    }

    @Override
    public long getItemId(int arg0) {
        return arg0 + mIndex * mPargerSize;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_homenav, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        //重新确定position因为拿到的总是数据源，数据源是分页加载到每页的GridView上的
        final int pos = position + mIndex * mPargerSize;//假设mPageSiez
        //假设mPagerSize=8，假如点击的是第二页（即mIndex=1）上的第二个位置item(position=1),那么这个item的实际位置就是pos=9
        if (!TextUtils.isEmpty(getItem(position).getTitle())) {
            holder.tvName.setText(getItem(position).getTitle());
        }
        if (!TextUtils.isEmpty(getItem(position).getPicUrl())) {
            Glide.with(context).load(getItem(position).getPicUrl()).into(holder.ivIcon);
        }
        //添加item监听
        convertView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (mOnItemClick != null) {
                    mOnItemClick.itemClick(getItem(position).getDirectType(), getItem(position).getUrl(), getItem(position).getTitle(), getItem(position).getQuan_id(), getItem(position).getAid(),
                            getItem(position).getNeedLogin(), getItem(position).getNeedrecord());
                }
            }
        });
        return convertView;
    }


    class ViewHolder {
        @BindView(R.id.iv_icon)
        ImageView ivIcon;
        @BindView(R.id.tv_name)
        TextView tvName;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    private onItemClick mOnItemClick;

    public void setOnItemClick(onItemClick onItemClick) {
        mOnItemClick = onItemClick;
    }

    public interface onItemClick {
        void itemClick(int type, String url, String name, String quan_id, String id, int needLogin, int needrecord);
    }

}