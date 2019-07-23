package com.d2956987215.mow.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.d2956987215.mow.R;
import com.d2956987215.mow.widgets.adapters.BaseRecyclerAdapter;
import com.d2956987215.mow.widgets.yulanpic.ImagePagerActivity;

import java.util.List;

/**
 * Created by Administrator on 2018/8/7 0007.
 */

public class RecAdapter extends RecyclerView.Adapter<RecAdapter.VH> {
    private int mRecyclerViewWidth;
    private Activity mContext;
    private RecyclerView mRecyclerView;
    private List<String> item;


    public RecAdapter(Activity context, int recyclerViewWidth, RecyclerView recyclerview, List<String> item) {
        mContext = context;
        mRecyclerViewWidth = recyclerViewWidth;
        mRecyclerView = recyclerview;
        this.item = item;

    }


    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater from = LayoutInflater.from(mContext);
        View view = from.inflate(R.layout.viewpager_item, parent, false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(final VH holder, final int position) {
        Glide.with(mContext).load(item.get(position).toString()).error(R.mipmap.have_no_head).into(holder.iv);


        final ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) holder.itemRoot.getLayoutParams();
        // 为了居中， 第一个条目leftMagrin、最后一个条目的rightMargin是（recyclerView宽度减去一个条目的宽度）/2
        int margin = (mRecyclerViewWidth - p.width) / 2;
        if (position == 0) {
            p.leftMargin = margin;
            p.rightMargin = 0;
            holder.itemRoot.setLayoutParams(p);
        } else if (position == item.size() - 1) {
            p.leftMargin = 0;
            p.rightMargin = margin;
            holder.itemRoot.setLayoutParams(p);
        } else {
            p.leftMargin = 0;
            p.rightMargin = 0;
            holder.itemRoot.setLayoutParams(p);

        }


        holder.itemRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int[] location = new int[2];
                v.getLocationOnScreen(location);
                int currentX = location[0];
                int currentCenterX = (int) (currentX + p.width / 2 * 0.8f);//因为除了中间外的其他条目是被缩放为0.8的状态
                int recyclerViewCenterX = mRecyclerViewWidth / 2;
                int offX = currentCenterX - recyclerViewCenterX;

                if (Math.abs(offX) > p.width / 2 * 0.21f) {//因为已经居中的Item，已经被放大到比例1了
                    mRecyclerView.smoothScrollBy(offX, 0);
                }
            }
        });

        holder.iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (item != null && item.size() > 0) {
                    if (position < item.size()) {
//                            String[] arr = (String[]) item.getImages().toArray(new String[item.getImages().size()]);
//                            List<String> list = new LinkedList<String>();
//                            for (String str : arr) {                //处理第一个数组,list里面的值为1,2,3,4
//                                if (!list.contains("")) {
//                                    list.add(RxJavaUtil.ImageHOST + str);  //本地照片Url需要拼接
//                                }
//                            }
                        String[] picArr = (String[]) item.toArray(new String[item.size()]);
                        Intent intent1 = new Intent(mContext, ImagePagerActivity.class); //
                        // 图片url,为了演示这里使用常量，一般从数据库中或网络中获取
                        intent1.putExtra(ImagePagerActivity.EXTRA_IMAGE_URLS, picArr);
                        intent1.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, position);
                        mContext.startActivity(intent1);
                        mContext.overridePendingTransition(R.anim.activity_zoom_open, 0);
                    } else {

                    }
                } else {
                }


//                Intent intent = new Intent(mContext, WebActivity.class);
//                intent.putExtra("url", item.get(position).getInput() + "");
//                intent.putExtra("name", "详情");
//                mContext.startActivity(intent);

            }
        });
    }



    @Override
    public int getItemCount() {
        return item.size();
    }


    class VH extends RecyclerView.ViewHolder {

        private RelativeLayout itemRoot;
        private ImageView iv;

        public VH(View itemView) {
            super(itemView);
            itemRoot = itemView.findViewById(R.id.item_root);

            iv = itemView.findViewById(R.id.iv);
        }
    }

}
