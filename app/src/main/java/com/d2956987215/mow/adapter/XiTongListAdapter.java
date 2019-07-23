package com.d2956987215.mow.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.text.Html;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.d2956987215.mow.R;
import com.d2956987215.mow.rxjava.response.XiTonfListResponse;

import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.util.List;


/**
 * Created by Administrator on 2018/3/8.
 */

public class XiTongListAdapter extends BaseQuickAdapter<XiTonfListResponse.DataBeanX.DataBean, BaseViewHolder> {
    public XiTongListAdapter(@LayoutRes int layoutResId, @Nullable List<XiTonfListResponse.DataBeanX.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final XiTonfListResponse.DataBeanX.DataBean item) {
        helper.setText(R.id.tv_time, item.getCreated_at())
                .setText(R.id.tv_name, item.getTitle());
        HtmlTextView htmlTextView= helper.getView(R.id.html_text);
        htmlTextView.setHtml(item.getDetail());
    }
}
