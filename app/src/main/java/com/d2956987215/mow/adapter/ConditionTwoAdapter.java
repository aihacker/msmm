package com.d2956987215.mow.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.d2956987215.mow.R;
import com.d2956987215.mow.activity.MainActivity;
import com.d2956987215.mow.activity.mine.MyHuiYuanDetailActivity;
import com.d2956987215.mow.activity.mine.MyYaoQingActivity;
import com.d2956987215.mow.dialog.UpdateIDDialog;
import com.d2956987215.mow.rxjava.response.MemberUpgradeResponse;

import java.util.List;

public class ConditionTwoAdapter extends BaseQuickAdapter<MemberUpgradeResponse.DataBean.ConditionTwoBean, BaseViewHolder> {
    private Activity mActivity;
    private int roletype;

    public ConditionTwoAdapter(int layoutResId, @Nullable List<MemberUpgradeResponse.DataBean.ConditionTwoBean> data,int roletype, Activity mActivity) {
        super(layoutResId, data);
        this.roletype = roletype;
        this.mActivity = mActivity;
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, final MemberUpgradeResponse.DataBean.ConditionTwoBean dataBean) {
        if (!TextUtils.isEmpty(dataBean.getMessageX())) {
            baseViewHolder.setText(R.id.tv_msg, dataBean.getMessageX());
        }
        if (!TextUtils.isEmpty(dataBean.getSchedule())) {
            baseViewHolder.setText(R.id.tv_nums, dataBean.getSchedule());
        }

        if (dataBean.getType() == 1) {
            baseViewHolder.setText(R.id.tv_schedule, "去邀请");
        } else if (dataBean.getType() == 2) {
            baseViewHolder.setText(R.id.tv_schedule, "去赚佣");
        }

        ProgressBar progressBar = baseViewHolder.getView(R.id.progressBar);
        progressBar.setMax(dataBean.getOneLevelCount());
        progressBar.setProgress(dataBean.getFinishOneLevelCount());


        baseViewHolder.getView(R.id.tv_schedule).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dataBean.getType() == 1) {
                    if (roletype != 0) {
                        Intent intent = new Intent(mContext, MyYaoQingActivity.class);
                        mContext.startActivity(intent);
                    } else {
                        UpdateIDDialog updateIDDialog = new UpdateIDDialog(mContext, new UpdateIDDialog.CallBack() {
                            @Override
                            public void NO() {
                                Intent intent = new Intent(mContext, MyHuiYuanDetailActivity.class);
                                intent.putExtra("roletype", roletype + "");
                                mContext.startActivity(intent);
                            }
                        });
                        updateIDDialog.show();
                    }
                } else if (dataBean.getType() == 2) {
                    Intent mainIntent = new Intent(mActivity, MainActivity.class);
                    mainIntent.putExtra("from", "buyNow");
                    mActivity.startActivity(mainIntent);
                    mActivity.finish();
                }
            }
        });
    }
}
