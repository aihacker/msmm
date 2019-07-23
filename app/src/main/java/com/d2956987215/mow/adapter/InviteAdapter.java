package com.d2956987215.mow.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.d2956987215.mow.R;

import java.util.List;

/**
 * Created by chenxiaoping on 2017/3/28.
 */

public class InviteAdapter extends RecyclerView.Adapter<InviteAdapter.ViewHolder> {

    private  List<String> mData;
    private Context mContext;
//    private int[] mColors = {R.mipmap.item1,R.mipmap.item2,R.mipmap.item3,R.mipmap.item4,
//            R.mipmap.item5,R.mipmap.item6};

    private onItemClick clickCb;

    public InviteAdapter(Context c) {
        mContext = c;
    }

    public InviteAdapter(Context c, onItemClick cb, List<String> data) {
        mContext = c;
        clickCb = cb;
        mData = data;
    }

    public void setOnClickLstn(onItemClick cb) {
        this.clickCb = cb;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_invite_image, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Glide.with(mContext).load(mData.get(position)).error(R.mipmap.have_no_head)
                .into(holder.img);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(mContext, "点击了："+position, Toast.LENGTH_SHORT).show();
                if (clickCb != null) {
                    clickCb.clickItem(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;

        public ViewHolder(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.iv);
        }
    }

    public interface onItemClick {
        void clickItem(int pos);
    }
}
