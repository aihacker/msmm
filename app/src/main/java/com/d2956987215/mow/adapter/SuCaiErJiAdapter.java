package com.d2956987215.mow.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.d2956987215.mow.R;
import com.d2956987215.mow.rxjava.response.LunTanTitleResponse;

import java.util.List;


/**
 * Created by Administrator on 2018/3/7.
 */

public class SuCaiErJiAdapter extends BaseQuickAdapter<LunTanTitleResponse.DataBean, BaseViewHolder> {

    private int selectIndex = -1;

    public void setSelectIndex(int jiage) {
        selectIndex = jiage;

    }

    public SuCaiErJiAdapter(@LayoutRes int layoutResId, @Nullable List<LunTanTitleResponse.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, LunTanTitleResponse.DataBean item) {
        TextView tv_city = helper.getView(R.id.tv_city);
        tv_city.setText(item.getCat_name());

        if (helper.getPosition() == selectIndex) {
            tv_city.setTextColor(mContext.getResources().getColor(R.color.dialog_text_color));
            tv_city.setBackground(mContext.getResources().getDrawable(R.drawable.luntan_tag_select));
        } else {
            tv_city.setTextColor(mContext.getResources().getColor(R.color.text_deep));
            tv_city.setBackground(mContext.getResources().getDrawable(R.drawable.luntan_tag_unselect));
        }


    }
}
