package com.d2956987215.mow.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.d2956987215.mow.R;

import java.util.List;

public class AlertSearchAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public AlertSearchAdapter( @Nullable List<String> data) {
        super(R.layout.item_alert_serch, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_item, item);
    }

}
