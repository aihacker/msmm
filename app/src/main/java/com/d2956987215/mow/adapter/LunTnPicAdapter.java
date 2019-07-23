package com.d2956987215.mow.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.d2956987215.mow.R;
import com.d2956987215.mow.rxjava.response.LanMuListResponse;
import com.d2956987215.mow.widgets.ShapedImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/3/7.
 */

public class LunTnPicAdapter extends BaseAdapter {
    private Context context;
    private List<LanMuListResponse.DataBean.ArticleGoodsBean> lists;
    private int imhWidth;

    public LunTnPicAdapter(Context context, List<LanMuListResponse.DataBean.ArticleGoodsBean> lists, int imhWidth) {
        this.context = context;
        this.lists = lists;
        this.imhWidth = imhWidth;
    }

    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public LanMuListResponse.DataBean.ArticleGoodsBean getItem(int i) {
        return lists.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.luntan_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.mTvPrice.setText("￥" + getItem(i).getZk_final_price());
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) holder.mImage.getLayoutParams();
        params.width = imhWidth;
        params.height = imhWidth;
        holder.mImage.setLayoutParams(params);
        Glide.with(context).load(getItem(i).getItempic()).into(holder.mImage);
        return convertView;
    }


    class ViewHolder {
        @BindView(R.id.image)
        ShapedImageView mImage;
        @BindView(R.id.tv_price)
        TextView mTvPrice;
        @BindView(R.id.rl_item)
        RelativeLayout mRlItem;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
