package com.d2956987215.mow.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.d2956987215.mow.R;
import com.d2956987215.mow.rxjava.response.AccuntListResponse;
import com.ut.mini.internal.UTTeamWork;

import java.util.List;


/**
 * Created by Administrator on 2018/3/7.
 */

public class MyaccoutlistAdapter extends BaseQuickAdapter<AccuntListResponse.DataBeanX.DataBean, BaseViewHolder> {


    public MyaccoutlistAdapter(@LayoutRes int layoutResId, @Nullable List<AccuntListResponse.DataBeanX.DataBean> data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(final BaseViewHolder helper, AccuntListResponse.DataBeanX.DataBean item) {
        TextView tv_price = helper.getView(R.id.tv_price);
        helper.setText(R.id.tv_content, item.getAddtime());
        helper.setText(R.id.tv_name, item.getStatus());
        tv_price.setText("+￥" + item.getCredit2());
//        if (TextUtils.equals("1", item.getSh())) {
////            helper.setText(R.id.tv_name, "佣金结算");
//            String[] split = item.getAddtime().split("-");
//            if (split.length > 1) {
//                String month = split[1];
//                if (month.contains("0")) {
//                    month = month.replace("0", "");
//                }
//                helper.setText(R.id.tv_name, month + "");
//            }
//            tv_price.setTextColor(mContext.getResources().getColor(R.color.hot_product));
//            tv_price.setText("+￥" + item.getCredit2());
//
//        } else {
//            helper.setText(R.id.tv_name, "佣金提现");
//            tv_price.setTextColor(mContext.getResources().getColor(R.color.accountcolor));
//            tv_price.setText("+￥" + item.getCredit2());
//        }
    }
}
