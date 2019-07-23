package com.d2956987215.mow.adapter;

import android.graphics.Paint;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.d2956987215.mow.R;
import com.d2956987215.mow.rxjava.response.MyFavariteResponse;
import com.d2956987215.mow.util.MoneyUtils;
import com.d2956987215.mow.util.StringUtil;
import com.d2956987215.mow.widgets.ShapedImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/3/7.
 */

public class HomeShouCangAdapter extends BaseQuickAdapter<MyFavariteResponse.DataBean, BaseViewHolder> {
    private int selectIndex=1;
    private SparseBooleanArray mSelectedPositions = new SparseBooleanArray();
    private boolean mIsSelectable = false;
    public HomeShouCangAdapter(@LayoutRes int layoutResId, @Nullable List<MyFavariteResponse.DataBean> data) {
        super(layoutResId, data);
    }

    public void setSelectIndex(int position) {
        selectIndex = position;
    }

    @Override
    protected void convert(final BaseViewHolder holder, final MyFavariteResponse.DataBean item) {
        final CheckBox cb_choose = holder.getView(R.id.cb_choose);
        TextView tv_old_price = holder.getView(R.id.tv_old_price);

        LinearLayout ll_choose = holder.getView(R.id.ll_choose);
        if (selectIndex == 1) {
            cb_choose.setVisibility(View.VISIBLE);
        } else {
            cb_choose.setVisibility(View.GONE);
        }
        ShapedImageView iv_pic = holder.getView(R.id.iv_pic);
        ShapedImageView iv_logo = holder.getView(R.id.iv_logo);

        Glide.with(mContext).load(item.getGoods_pic()).placeholder(R.mipmap.da_tu).error(R.mipmap.da_tu).into(iv_pic);
        holder.setText(R.id.tv_name, item.getGoods_title());

        holder.setText(R.id.tv_quancount, "￥" + item.getGoods_quan());
        holder.setText(R.id.tv_total, "已售" + (StringUtil.isBlank(item.getGoods_sale()) ? 0 : MoneyUtils.getMoney(item.getGoods_sale())));
        holder.setText(R.id.tv_nowprice, "￥" + item.getGoods_endprice());
        holder.setText(R.id.tv_dianming, item.getShop_name());
        tv_old_price.setText("￥" + item.getGoods_price());
        tv_old_price.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG);
        //holder.setText(R.id.tv_share_money, "赚￥" + item.getShare_money());

//        holder.setText(R.id.tv_shengji, "￥" + item.getGive_money());
//        holder.setText(R.id.tv_fenxiang, "￥" + item.getGive_money());
//        holder.setText(tv_shengji, "￥" + item.getItemsale());
        if (item.getType().equals("B")) {
            iv_logo.setBackground(mContext.getResources().getDrawable(R.mipmap.icon_tianmao));
        } else {
            iv_logo.setBackground(mContext.getResources().getDrawable(R.mipmap.home_icon_taobao));
        }
//        if (isItemChecked(holder.getPosition())) {
//            setItemChecked(holder.getPosition(), false);
//        } else {
//            setItemChecked(holder.getPosition(), true);
//        }
        if(item.isChoose()){
            cb_choose.setChecked(true);
        }else {
            cb_choose.setChecked(false);
        }
        ll_choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (isItemChecked(holder.getPosition())) {
//                    setItemChecked(holder.getPosition(), false);
//                    cb_choose.setChecked(false);
//                } else {
//                    setItemChecked(holder.getPosition(), true);
//                    cb_choose.setChecked(true);
//                }

                if (item.isChoose()) {
                    item.setChoose(false);
                    setItemChecked(holder.getPosition(), false);
                    cb_choose.setChecked(false);
                } else {
                    item.setChoose(true);
                    setItemChecked(holder.getPosition(), true);
                    cb_choose.setChecked(true);
                }


                String str = getSelectedItem().toString();
                int len = str.length() - 1;
                String ids = str.substring(1, len).replace(" ", "");
                if(quxiaoListener!=null){
                    quxiaoListener.xuanqu(ids, String.valueOf(getSelectedItem().size()));
                }
//                notifyDataSetChanged();
            }
        });

    }

    //获得选中
    public ArrayList<String> getSelectedItem() {
        ArrayList<String> selectList = new ArrayList<>();
        for (int i = 0; i < mData.size(); i++) {
            if (isItemChecked(i)) {
                selectList.add(mData.get(i).getGoods_id());
            }
        }
        return selectList;
    }
    //设置给定位置条目的选择状态
    public void setItemChecked(int position, boolean isChecked) {
        mSelectedPositions.put(position, isChecked);
    }

    //情况选择
    public void clearItemChecked() {
        mSelectedPositions.clear();
    }

    //根据位置判断条目是否选中
    private boolean isItemChecked(int position) {
        return mSelectedPositions.get(position);
    }

    //根据位置判断条目是否可选
    private boolean isSelectable() {
        return mIsSelectable;
    }

    //设置给定位置条目的可选与否的状态
    private void setSelectable(boolean selectable) {
        mIsSelectable = selectable;
    }

    public interface onClickQuxiaoListener {
        void xuanqu(String a, String size);
    }

    private onClickQuxiaoListener quxiaoListener;

    public void setClickButtonListener(onClickQuxiaoListener quxiaoListener) {
        this.quxiaoListener = quxiaoListener;
    }
}
