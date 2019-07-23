package com.d2956987215.mow.adapter;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.d2956987215.mow.R;
import com.d2956987215.mow.activity.home.ShopMoreActivity;
import com.d2956987215.mow.rxjava.response.BrandListResponse;
import com.d2956987215.mow.rxjava.response.MsgEverydayResponse;
import com.d2956987215.mow.util.GlideRoundTransform;
import com.d2956987215.mow.widgets.NoScrollGridView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class BrandListAdapter extends BaseQuickAdapter<BrandListResponse.DataBean, BaseViewHolder> {


    public BrandListAdapter(int layoutResId, @Nullable List<BrandListResponse.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, final BrandListResponse.DataBean dataBean) {
        if (!TextUtils.isEmpty(dataBean.getFq_brand_name())) {
            baseViewHolder.setText(R.id.tv_name, dataBean.getFq_brand_name());
        }

        if (!TextUtils.isEmpty(dataBean.getBrand_logo())) {
            ImageView imageView = baseViewHolder.getView(R.id.iv_brand);
            Glide.with(mContext).load(dataBean.getBrand_logo()).transform(new GlideRoundTransform(mContext, 3)).into(imageView);
        }

        if (dataBean.getItem() != null && dataBean.getItem().size() > 0) {
            NoScrollGridView gridView = baseViewHolder.getView(R.id.gv);
            gridView.setAdapter(new BrandFrgOneItemAdpter(mContext, dataBean.getItem()));
        }
        LinearLayout ll_more = baseViewHolder.getView(R.id.ll_more);
        ll_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ShopMoreActivity.class);
                intent.putExtra("id", dataBean.getId());
                mContext.startActivity(intent);
            }
        });
    }

}
