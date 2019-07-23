package com.d2956987215.mow.adapter;

import android.content.ActivityNotFoundException;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.d2956987215.mow.R;
import com.d2956987215.mow.dialog.GoWechatDialog;
import com.d2956987215.mow.rxjava.response.MyTeamResponse;
import com.d2956987215.mow.widgets.CircleImageView;

import java.util.List;

import static com.blankj.utilcode.util.ActivityUtils.startActivity;

/**
 * Created by Administrator on 2018/3/7.
 */

public class MyTeamOtherAdapter extends BaseQuickAdapter<MyTeamResponse.DataBean.ListBean, BaseViewHolder> {
    private OtherClick mOtherClick;

    public MyTeamOtherAdapter(@LayoutRes int layoutResId, @Nullable List<MyTeamResponse.DataBean.ListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(final BaseViewHolder holder, final MyTeamResponse.DataBean.ListBean item) {
        TextView tv_shoucang = holder.getView(R.id.tv_weishoucang);
        CircleImageView head_img = holder.getView(R.id.head_img);
        Glide.with(mContext).load(item.getAvatar()).error(R.mipmap.da_tu).into(head_img);
        holder.setText(R.id.tv_name, item.getWw());
        holder.setText(R.id.tv_id, item.getStudentId());
        holder.setText(R.id.tv_dabanzhang, "大班长:" + item.getDbz());
        holder.setText(R.id.tv_renshu, "团队:" + item.getTeamnum() + "人");
        holder.setText(R.id.tv_tuijian, "推荐人:" + item.getTjr());
        holder.setText(R.id.tv_yongjin, "佣金:" + item.getAllxgyg());
        holder.setText(R.id.tv_phone, "手机:" + item.getMobile());

        holder.setText(R.id.tv_juli, "(距离" + item.getJi() + "个)");

        holder.setText(R.id.tv_zhuce, "注册时间:" + item.getCreatetime());
        holder.setText(R.id.tv_guoqi, "过期时间:" + item.getDeadline().getDeadline());
        if (item.getDeadline().getIs_kt().equals("1")) {
            tv_shoucang.setTextColor(mContext.getResources().getColor(R.color.hot_product));
            tv_shoucang.setBackground(mContext.getResources().getDrawable(R.drawable.bg_red));
            tv_shoucang.setText("已开通");
        } else {
            tv_shoucang.setTextColor(mContext.getResources().getColor(R.color.white));
            tv_shoucang.setBackground(mContext.getResources().getDrawable(R.drawable.bg_loginred));
            tv_shoucang.setText("开通");
        }
        holder.getView(R.id.tv_fuzhi).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager cm = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
                // 将文本内容放到系统剪贴板里。
                cm.setText(item.getMobiles());
                Toast.makeText(mContext, "复制成功，可以发给朋友们了。", Toast.LENGTH_LONG).show();
                getWechatApi();
            }
        });

        tv_shoucang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (item.getDeadline().getIs_kt().equals("0"))
                mOtherClick.otherClick(item.getId() + "", holder.getAdapterPosition());
            }
        });


    }
    /**
     * 跳转到微信
     */
    private void getWechatApi(){


        GoWechatDialog goWechatDialog = new GoWechatDialog(mContext, new GoWechatDialog.CallBack() {
            @Override
            public void NO() {
                try {
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    ComponentName cmp = new ComponentName("com.tencent.mm","com.tencent.mm.ui.LauncherUI");
                    intent.addCategory(Intent.CATEGORY_LAUNCHER);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setComponent(cmp);
                    startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    // TODO: handle exception
                    ToastUtils.showShort("检查到您手机没有安装微信，请安装后使用该功能");
                }
            }
        });
        goWechatDialog.show();

    }


    public void setOtherClick(OtherClick click) {
        mOtherClick = click;
    }


    public interface OtherClick{
        void otherClick(String s, int position);
    }

}
