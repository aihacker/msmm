package com.d2956987215.mow.adapter;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.CheckBox;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.d2956987215.mow.R;
import com.d2956987215.mow.bean.PhoneDto;

import java.util.ArrayList;
import java.util.List;

import static com.blankj.utilcode.util.ActivityUtils.startActivity;

/**
 * Created by Administrator on 2018/3/7.
 */

public class TongXunAdapter extends BaseQuickAdapter<PhoneDto, BaseViewHolder> {
    private int selectIndex;
    private SparseBooleanArray mSelectedPositions = new SparseBooleanArray();
    private boolean mIsSelectable = false;
    private String yaoqingma;

    public TongXunAdapter(@LayoutRes int layoutResId, @Nullable List<PhoneDto> data, String yaoqingma) {
        super(layoutResId, data);
        this.yaoqingma = yaoqingma;
    }


    @Override
    protected void convert(final BaseViewHolder holder, final PhoneDto item) {

        CheckBox cb_choose = holder.getView(R.id.ct_zhiding);

        holder.setText(R.id.tv_name, item.getName());
        holder.setText(R.id.tv_phone, item.getTelPhone());
        cb_choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isItemChecked(holder.getPosition())) {
                    setItemChecked(holder.getPosition(), false);
                } else {
                    setItemChecked(holder.getPosition(), true);
                }
                String str = getSelectedItem().toString();
                int len = str.length() - 1;
                String ids = str.substring(1, len).replace(" ", "");
                quxiaoListener.xuanqu(ids, String.valueOf(getSelectedItem().size()));

            }
        });
        holder.getView(R.id.tv_yaoqing).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(Intent.ACTION_VIEW);
//                intent.putExtra("address", item.getTelPhone());
//                intent.putExtra("sms_body", "我的邀请码是：" + yaoqingma);
//                intent.setType("vnd.android-dir/mms-sms");
//                mContext.startActivity(intent);

                Uri smsToUri = Uri.parse("smsto:"+item.getTelPhone());
                Intent intent = new Intent(Intent.ACTION_SENDTO, smsToUri);
                intent.putExtra("sms_body", "我的邀请码是：" + yaoqingma);
                startActivity(intent);


            }
        });


    }


    //获得选中
    public ArrayList<String> getSelectedItem() {
        ArrayList<String> selectList = new ArrayList<>();
        for (int i = 0; i < mData.size(); i++) {
            if (isItemChecked(i)) {
                selectList.add(mData.get(i).getTelPhone() + ";");
            }
        }
        return selectList;
    }

    //设置给定位置条目的选择状态
    private void setItemChecked(int position, boolean isChecked) {
        mSelectedPositions.put(position, isChecked);
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
