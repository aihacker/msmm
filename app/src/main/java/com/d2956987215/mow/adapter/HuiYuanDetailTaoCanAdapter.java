package com.d2956987215.mow.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.d2956987215.mow.R;
import com.d2956987215.mow.rxjava.response.VipDetailResponse;
import com.d2956987215.mow.widgets.adapters.BaseRecyclerAdapter;
import com.d2956987215.mow.widgets.adapters.BaseRecyclerViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2016/9/3.
 */
public class HuiYuanDetailTaoCanAdapter extends BaseRecyclerAdapter<VipDetailResponse.DataBean.SpecOptions> {

    private int checkPosition;

    public int getCheckPosition() {
        return checkPosition;
    }

    public void setCheckPosition(int checkPosition) {
        this.checkPosition = checkPosition;
    }

    public HuiYuanDetailTaoCanAdapter(Context context, List<VipDetailResponse.DataBean.SpecOptions> datas) {
        super(context, datas);
    }

    @Override
    protected void showViewHolder(BaseRecyclerViewHolder holder, int position) {
        VipDetailResponse.DataBean.SpecOptions specOptions = mDatas.get(position);
        MyGridHolder myGridHolder = (MyGridHolder) holder;
        if(position==checkPosition){
            myGridHolder.cb_ok.setChecked(true);
        }else {
            myGridHolder.cb_ok.setChecked(false);
        }
        myGridHolder.tv_title.setText(specOptions.getTitle());
        myGridHolder.tv_price.setText("¥ "+specOptions.getPrice());
    }

    @Override
    protected BaseRecyclerViewHolder createViewMyHolder(ViewGroup parent, int viewType) {
        return new MyGridHolder(mInflater.inflate(R.layout.item_huiyuan_taocan, parent, false));
    }

    class MyGridHolder extends BaseRecyclerViewHolder {
        private TextView tv_title;
        private TextView tv_price;
        private CheckBox cb_ok;

        public MyGridHolder(View view) {
            super(view);
            cb_ok = (CheckBox) view.findViewById(R.id.cb_ok);
            tv_title = (TextView) view.findViewById(R.id.tv_title);
            tv_price = (TextView) view.findViewById(R.id.tv_price);
        }
    }
}
