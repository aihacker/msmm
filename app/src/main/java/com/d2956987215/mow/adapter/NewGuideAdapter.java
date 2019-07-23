package com.d2956987215.mow.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.d2956987215.mow.R;
import com.d2956987215.mow.rxjava.Request;
import com.d2956987215.mow.rxjava.Result;
import com.d2956987215.mow.rxjava.RxJavaUtil;
import com.d2956987215.mow.rxjava.response.BaseResponse;
import com.d2956987215.mow.rxjava.response.GuiListResponse;
import com.d2956987215.mow.util.ToastUtil;
import com.d2956987215.mow.widgets.ShapedImageView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/3/7.
 */

public class NewGuideAdapter extends BaseQuickAdapter<GuiListResponse.DataBean, BaseViewHolder> {
    public NewGuideAdapter(@LayoutRes int layoutResId, @Nullable List<GuiListResponse.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, final GuiListResponse.DataBean item) {
        ShapedImageView iv_pic = holder.getView(R.id.iv_pic);

        Glide.with(mContext).load(item.getResp_img()).error(R.mipmap.da_tu).into(iv_pic);

        holder.setText(R.id.tv_name, item.getArticle_title());
        holder.setText(R.id.tv_content, item.getResp_desc());
        holder.setText(R.id.tv_liulan, item.getArticle_readnum());
        final TextView tv_dianzan = holder.getView(R.id.tv_dianzan);
        tv_dianzan.setText(item.getArticle_likenum()+"");
        tv_dianzan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, String> map = new HashMap<>();
                map.put("id", item.getId() + "");
                new Request<BaseResponse>().request(RxJavaUtil.xApi().dianzan(map), "点赞", mContext, false, new Result<BaseResponse>() {
                    @Override
                    public void get(BaseResponse response) {
                        ToastUtil.show(mContext, response.message);
                        int count = item.getArticle_likenum() + 1;
                        tv_dianzan.setText(count + "");
                    }
                });


            }
        });


    }
}
