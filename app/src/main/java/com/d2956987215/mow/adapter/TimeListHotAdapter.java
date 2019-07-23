package com.d2956987215.mow.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.d2956987215.mow.R;
import com.d2956987215.mow.rxjava.response.QiagGouListResponse;
import com.d2956987215.mow.util.User;
import com.d2956987215.mow.widgets.SaleProgressView;
import com.d2956987215.mow.widgets.ShapedImageView;

import java.util.List;

/**
 * Created by Administrator on 2018/3/7.
 */

public class TimeListHotAdapter extends BaseQuickAdapter<QiagGouListResponse.DataBean, BaseViewHolder> {

    private boolean isStart;

    public boolean isStart() {
        return isStart;
    }

    public void setStart(boolean start) {
        isStart = start;
    }

    public TimeListHotAdapter(@LayoutRes int layoutResId, @Nullable List<QiagGouListResponse.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, QiagGouListResponse.DataBean item) {

        TextView tvShareOrGet = holder.getView(R.id.tv_share_or_get);

        if (User.roleType().equals("0") || User.roleType().equals("3")) {
            tvShareOrGet.setText("领券");
            tvShareOrGet.setTextSize(15);
        } else {
            tvShareOrGet.setText("分享赚");
        }
        ShapedImageView iv_pic = holder.getView(R.id.iv_pic);
        ShapedImageView iv_logo = holder.getView(R.id.iv_logo);
        SaleProgressView saleProgressView = holder.getView(R.id.spv);

        Glide.with(mContext).load(item.getPic()).placeholder(R.mipmap.da_tu).error(R.mipmap.da_tu).into(iv_pic);
        holder.setText(R.id.tv_name, item.getTitle());

        holder.setText(R.id.tv_quancount, "￥" + item.getQuan_price());
        TextView tvShare = holder.getView(R.id.tv_zhuan);
        if (item.getShare_money().equals("0"))
            tvShare.setVisibility(View.GONE);
        else
            tvShare.setVisibility(View.VISIBLE);
        holder.setText(R.id.tv_zhuan, "赚￥" + item.getShare_money());
        int total = item.getQuan_surplus() + item.getQuan_receive();
        int yihsou = item.getQuan_receive() * 100 / total;
        saleProgressView.setTotalAndCurrentCount(100, yihsou, item.getQuan_receive() + "");

        holder.setText(R.id.tv_nowprice, "￥" + item.getPrice() + "");
        holder.setText(R.id.tv_yuanjia, "￥" + item.getOrg_Price());
        /*holder.getView(R.id.la_fenxiang).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isStart){
                    ToastUtil.show(mContext,"活动暂未开启");
                    return;
                }

            }
        });*/

        holder.setText(R.id.tv_dianming, item.getShop_name());
        if (item.getIsTmal() != null && item.getIsTmal().equals("1")) {
            iv_logo.setBackground(mContext.getResources().getDrawable(R.mipmap.icon_tianmao));
        } else {
            iv_logo.setBackground(mContext.getResources().getDrawable(R.mipmap.home_icon_taobao));
        }


    }
}
