package com.d2956987215.mow.adapter;

import android.support.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.d2956987215.mow.R;
import com.d2956987215.mow.rxjava.response.UpdateIdResponse;
import com.d2956987215.mow.widgets.ShapedImageView;

import java.util.List;

public class UpdateIdAdapter extends BaseQuickAdapter<UpdateIdResponse.DataBean, BaseViewHolder> {

    public UpdateIdAdapter(@Nullable List<UpdateIdResponse.DataBean> data) {
        super(R.layout.item_update_id, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, UpdateIdResponse.DataBean item) {
        ShapedImageView iv = helper.getView(R.id.iv_banzhang);
        helper.setText(R.id.tv_type, item.getRolename());
        Glide.with(mContext).load(item.getRolepic()).into(iv);


    }

}
