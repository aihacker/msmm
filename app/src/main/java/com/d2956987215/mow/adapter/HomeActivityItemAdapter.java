package com.d2956987215.mow.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.d2956987215.mow.R;
import com.d2956987215.mow.activity.SplashActivity;
import com.d2956987215.mow.activity.home.ErJiDetailActivity;
import com.d2956987215.mow.activity.home.GuideWebViewActivity;
import com.d2956987215.mow.activity.home.QiangGouListActivity;
import com.d2956987215.mow.activity.home.ShouYeDetailActivity;
import com.d2956987215.mow.activity.home.TaoBaoDetailActivity;
import com.d2956987215.mow.rxjava.response.HomeDataResponse;
import com.d2956987215.mow.util.TBKUtils;
import com.d2956987215.mow.util.ToastUtil;
import com.d2956987215.mow.util.User;
import com.tencent.mm.opensdk.modelbiz.WXLaunchMiniProgram;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivityItemAdapter extends BaseAdapter {

    private Context context;
    private List<HomeDataResponse.DataBean.ActivityBean.ModuleListBean.ListBean> activityBeans;
    private int ingHeight;

    public HomeActivityItemAdapter(Context context, List<HomeDataResponse.DataBean.ActivityBean.ModuleListBean.ListBean> activityBeans, int ingHeight) {
        this.context = context;
        this.activityBeans = activityBeans;
        this.ingHeight = ingHeight;
    }

    @Override
    public int getCount() {
        return activityBeans.size();
    }

    @Override
    public HomeDataResponse.DataBean.ActivityBean.ModuleListBean.ListBean getItem(int i) {
        return activityBeans.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_homeactivity, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ViewGroup.LayoutParams params = holder.mIvImg.getLayoutParams();
        params.height = ingHeight;
        holder.mIvImg.setLayoutParams(params);
        Glide.with(context).load(getItem(position).getPicUrl()).into(holder.mIvImg);

        holder.mIvImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mSetOnImgClick != null) {
                    mSetOnImgClick.OnImgClick(getItem(position).getDirectType(), getItem(position).getUrl(), getItem(position).getTitle(),
                            "", "", getItem(position).getNeedLogin(), getItem(position).getNeedrecord());
                }
            }
        });

        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.iv_img)
        ImageView mIvImg;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }


    private setOnImgClick mSetOnImgClick;

    public interface setOnImgClick {
        void OnImgClick(int type, String url, String name, String quan_id, String id, int needLogin, int needrecord);
    }

    public void setOnImgClick(setOnImgClick mSetOnImgClick) {
        this.mSetOnImgClick = mSetOnImgClick;
    }

}
