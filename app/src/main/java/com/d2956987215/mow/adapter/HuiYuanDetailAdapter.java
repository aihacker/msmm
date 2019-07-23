package com.d2956987215.mow.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.d2956987215.mow.R;
import com.d2956987215.mow.rxjava.response.VipDetailResponse;
import com.d2956987215.mow.widgets.adapters.BaseRecyclerAdapter;
import com.d2956987215.mow.widgets.adapters.BaseRecyclerViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2016/9/3.
 */
public class HuiYuanDetailAdapter extends BaseRecyclerAdapter<VipDetailResponse.DataBean.Explain.Tq> {

    public HuiYuanDetailAdapter(Context context, List<VipDetailResponse.DataBean.Explain.Tq> datas) {
        super(context, datas);
    }

    @Override
    protected void showViewHolder(BaseRecyclerViewHolder holder, int position) {
        VipDetailResponse.DataBean.Explain.Tq tq = mDatas.get(position);
        MyGridHolder myGridHolder=(MyGridHolder)holder;
//        myGridHolder.tv_icon.setBackgroundResource(tq.getIcon());
        Glide.with(mContext.getApplicationContext()).load(tq.getTq_img()).placeholder(R.mipmap.da_tu).error(R.mipmap.da_tu).into(myGridHolder.tv_icon);
        myGridHolder.tv_name.setText(tq.getTq_t());
        myGridHolder.tv_name_desc.setText(tq.getTq_d());
//        if(tq.getIsFinish())
//        {
//            myGridHolder.checkFinish.setVisibility(View.VISIBLE);
//        }else {
//            myGridHolder.checkFinish.setVisibility(View.GONE);
//        }
    }

    @Override
    protected BaseRecyclerViewHolder createViewMyHolder(ViewGroup parent, int viewType) {
        return new MyGridHolder(mInflater.inflate(R.layout.item_huiyuan_detail_grid_item,parent,false));
    }
    class MyGridHolder extends BaseRecyclerViewHolder{
        private ImageView tv_icon;
        private TextView tv_name;
        private TextView tv_name_desc;
        private ImageView checkFinish;

        public MyGridHolder(View view) {
            super(view);
            tv_icon=(ImageView)view.findViewById(R.id.iv_icon);
            checkFinish=(ImageView)view.findViewById(R.id.checkFinish);
            tv_name=(TextView)view.findViewById(R.id.tv_name);
            tv_name_desc=(TextView)view.findViewById(R.id.tv_name_desc);
        }
    }
}
