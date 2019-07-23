package com.d2956987215.mow.adapter;

import android.content.Context;
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
import com.d2956987215.mow.widgets.ShapedImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/3/7.
 */

public class LunSuCaiPicAdapter extends BaseAdapter {
    private Context context;
    private List<String> lists;
    private int imhWidth;

    public LunSuCaiPicAdapter(Context context, List<String> lists, int imhWidth) {
        this.context = context;
        this.lists = lists;
        this.imhWidth = imhWidth;
    }

    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public String getItem(int i) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.lunsucai_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) holder.mIvImg.getLayoutParams();
        params.width = imhWidth;
        params.height = imhWidth;
        holder.mIvImg.setLayoutParams(params);
        Glide.with(context).load(getItem(i)).into(holder.mIvImg);

        return convertView;
    }


    class ViewHolder {
        @BindView(R.id.iv_img)
        ImageView mIvImg;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
