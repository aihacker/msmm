package com.d2956987215.mow.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.d2956987215.mow.R;
import com.d2956987215.mow.bean.SharePicBean;
import com.d2956987215.mow.widgets.ShapedImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/3/7.
 */

public class SharePicAdapter extends BaseQuickAdapter<SharePicBean, BaseViewHolder> {
    private SparseBooleanArray mSelectedPositions = new SparseBooleanArray();
    private boolean mIsSelectable = false;

    public int mSelectedPosition = -1;
    private OnOtherClickListener mOnOtherClickListener;


    public void setSelectedPosition(int position) {
        mSelectedPosition = position;
    }


    public interface OnOtherClickListener {
        void setOtherClick(int position);
    }

    public void setOnOtherClickListener(OnOtherClickListener onOtherClickListener) {
        mOnOtherClickListener = onOtherClickListener;
    }

    public SharePicAdapter(@LayoutRes int layoutResId, @Nullable List<SharePicBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(final BaseViewHolder holder, SharePicBean item) {


        ShapedImageView iv_pic = holder.getView(R.id.iv_pic);
        Glide.with(mContext).load(item.getUrl()).error(R.mipmap.da_tu).into(iv_pic);
        final TextView tv_xuaqu = holder.getView(R.id.tv_xuaqu);
        TextView tv_qrcode = holder.getView(R.id.tv_qrcode);

        if (holder.getAdapterPosition() == mSelectedPosition) {
            tv_qrcode.setVisibility(View.VISIBLE);
        } else {
            tv_qrcode.setVisibility(View.GONE);
        }

        if (item.isCheck())
            tv_xuaqu.setBackgroundResource(R.mipmap.icon_share_select_select);
        else
            tv_xuaqu.setBackgroundResource(R.mipmap.icon_share_select_unselect);

        tv_xuaqu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnOtherClickListener.setOtherClick(holder.getAdapterPosition());
            }
        });

        /*tv_xuaqu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isItemChecked(holder.getPosition())) {
                    setItemChecked(holder.getPosition(), false);
                } else {
                    setItemChecked(holder.getPosition(), true);

                }
//                String str = getSelectedItem().toString();
//                int len = str.length() - 1;
//                String ids = str.substring(1, len).replace(" ", "");
                quxiaoListener.xuanqu(getSelectedItem(), String.valueOf(getSelectedItem().size()));
            }
        });
*/

    }

    //获得选中
    public ArrayList<String> getSelectedItem() {
        ArrayList<String> selectList = new ArrayList<>();
        for (int i = 0; i < mData.size(); i++) {
            if (isItemChecked(i)) {
                selectList.add(mData.get(i).toString());
            }
        }
        return selectList;
    }

    //设置给定位置条目的选择状态
    public void setItemChecked(int position, boolean isChecked) {
        mSelectedPositions.put(position, isChecked);
    }

    //根据位置判断条目是否选中
    public boolean isItemChecked(int position) {
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
        void xuanqu(List<String> list, String size);

    }

    private onClickQuxiaoListener quxiaoListener;

    public void setClickButtonListener(onClickQuxiaoListener quxiaoListener) {
        this.quxiaoListener = quxiaoListener;
    }
}
