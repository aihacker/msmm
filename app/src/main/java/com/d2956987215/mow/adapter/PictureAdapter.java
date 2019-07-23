package com.huasheng.user.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import com.d2956987215.mow.R;


/**
 * Created by Machenike on 2018/1/19.
 */

public class PictureAdapter extends BaseAdapter {
    private Context context;

    private List<String> pictures=new ArrayList<String>();

    public PictureAdapter(List<String> images, Context context) {
        super();
        this.context = context;
        this.pictures=images;


    }

    @Override
    public int getCount() {

        if (null != pictures) {
            return pictures.size();
        } else {
            return 0;
        }
    }

    @Override
    public Object getItem(int position) {

        return pictures.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            // 获得容器
            convertView = LayoutInflater.from(this.context).inflate(R.layout.picture_item, null);

            // 初始化组件
            viewHolder.image = (ImageView) convertView.findViewById(R.id.image);

            // 给converHolder附加一个对象
            convertView.setTag(viewHolder);
        } else {
            // 取得converHolder附加的对象
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // 给组件设置资源
        Glide.with(context).load(pictures.get(position).toString()).centerCrop().error(R.mipmap.da_tu).into(viewHolder.image);

        return convertView;
    }

    class ViewHolder {

        public ImageView image;
    }

}