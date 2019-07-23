package com.d2956987215.mow.adapter;

import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.d2956987215.mow.R;
import com.d2956987215.mow.activity.home.ArticleDetailsActivity;
import com.d2956987215.mow.rxjava.response.ArticleResponse;
import com.d2956987215.mow.util.DateUtils;
import com.d2956987215.mow.util.GlideRoundTransform;
import com.d2956987215.mow.widgets.ShapedImageView;

import java.util.List;


/**
 * Created by Administrator on 2018/3/7.
 */

public class ArticleAdapter extends BaseQuickAdapter<ArticleResponse.DataBean, BaseViewHolder> {


    public ArticleAdapter(@LayoutRes int layoutResId, @Nullable List<ArticleResponse.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final ArticleResponse.DataBean item) {
        helper.setText(R.id.tv_title, item.getTitle());
        helper.setText(R.id.tv_time, DateUtils.getTime(item.getCreated_at(), "yyyy-MM-dd hh:MM"));
        helper.setText(R.id.tv_count, item.getArticle_readnum() + "");
        ShapedImageView imageView = helper.getView(R.id.iv_pic);
        Glide.with(mContext).load(item.getPic_url()).into(imageView);

        helper.getView(R.id.ll_item).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ArticleDetailsActivity.class);
                intent.putExtra("url", item.getWeb_url());
                intent.putExtra("name", "文章详情");
                mContext.startActivity(intent);
            }
        });
    }

}
