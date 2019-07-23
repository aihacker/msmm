package com.d2956987215.mow.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.d2956987215.mow.R;
import com.d2956987215.mow.rxjava.response.HomeDataResponse;
import com.d2956987215.mow.util.DisplayUtil;
import com.d2956987215.mow.widgets.NoScrollGridView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivityAdapter extends BaseAdapter implements HomeActivityItemAdapter.setOnImgClick {

    private Context context;
    private List<HomeDataResponse.DataBean.ActivityBean.ModuleListBean> activityBeans;

    public HomeActivityAdapter(Context context, List<HomeDataResponse.DataBean.ActivityBean.ModuleListBean> activityBeans) {
        this.context = context;
        this.activityBeans = activityBeans;
    }

    @Override
    public int getCount() {
        return activityBeans.size();
    }

    @Override
    public HomeDataResponse.DataBean.ActivityBean.ModuleListBean getItem(int i) {
        return activityBeans.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_home_activity, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (getItem(i).getList() != null && getItem(i).getList().size() > 0) {
            if (getItem(i).getList().size() == 3) {
                RelativeLayout.LayoutParams paramsGv = (RelativeLayout.LayoutParams) holder.mGv.getLayoutParams();
                paramsGv.rightMargin = DisplayUtil.dip2px(context, 12);
                paramsGv.leftMargin = DisplayUtil.dip2px(context, 12);
                holder.mGv.setLayoutParams(paramsGv);

                int imgHeight = (DisplayUtil.getScreenWidth(context) - DisplayUtil.dip2px(context, 44)) / 3;
                holder.mGv.setNumColumns(getItem(i).getList().size());
                HomeActivityItemAdapter homeActivityItemAdapter = new HomeActivityItemAdapter(context, getItem(i).getList(), imgHeight);
                homeActivityItemAdapter.setOnImgClick(HomeActivityAdapter.this);
                holder.mGv.setAdapter(homeActivityItemAdapter);

                ViewGroup.LayoutParams params = holder.mIvBg.getLayoutParams();
                params.height = imgHeight + DisplayUtil.dip2px(context, 10);
                holder.mIvBg.setLayoutParams(params);
                Glide.with(context).load(getItem(i).getBgImg()).into(holder.mIvBg);
            } else if (getItem(i).getList().size() == 2) {
                RelativeLayout.LayoutParams paramsGv = (RelativeLayout.LayoutParams) holder.mGv.getLayoutParams();
                paramsGv.rightMargin = DisplayUtil.dip2px(context, 12);
                paramsGv.leftMargin = DisplayUtil.dip2px(context, 12);
                holder.mGv.setLayoutParams(paramsGv);

                int imgHeight = (DisplayUtil.getScreenWidth(context) - DisplayUtil.dip2px(context, 34)) / 2 * getItem(i).getHeight() / getItem(i).getWidth();
                holder.mGv.setNumColumns(getItem(i).getList().size());
                HomeActivityItemAdapter homeActivityItemAdapter = new HomeActivityItemAdapter(context, getItem(i).getList(), imgHeight);
                homeActivityItemAdapter.setOnImgClick(HomeActivityAdapter.this);
                holder.mGv.setAdapter(homeActivityItemAdapter);

                ViewGroup.LayoutParams params = holder.mIvBg.getLayoutParams();
                params.height = imgHeight + DisplayUtil.dip2px(context, 10);
                holder.mIvBg.setLayoutParams(params);
                Glide.with(context).load(getItem(i).getBgImg()).into(holder.mIvBg);
            } else if (getItem(i).getList().size() == 1) {
                RelativeLayout.LayoutParams paramsGv = (RelativeLayout.LayoutParams) holder.mGv.getLayoutParams();
                paramsGv.rightMargin = 0;
                paramsGv.leftMargin = 0;
                holder.mGv.setLayoutParams(paramsGv);

                int imgHeight = DisplayUtil.getScreenWidth(context) * getItem(i).getHeight() / getItem(i).getWidth();
                holder.mGv.setNumColumns(getItem(i).getList().size());
                HomeActivityItemAdapter homeActivityItemAdapter = new HomeActivityItemAdapter(context, getItem(i).getList(), imgHeight);
                homeActivityItemAdapter.setOnImgClick(HomeActivityAdapter.this);
                holder.mGv.setAdapter(homeActivityItemAdapter);

                ViewGroup.LayoutParams params = holder.mIvBg.getLayoutParams();
                params.height = imgHeight;
                holder.mIvBg.setLayoutParams(params);
                Glide.with(context).load(getItem(i).getBgImg()).into(holder.mIvBg);

            }

        }
        return convertView;
    }

    @Override
    public void OnImgClick(int type, String url, String name, String quan_id, String id, int needLogin, int needrecord) {
        if (mSetOnImgClick != null) {
            mSetOnImgClick.OnImgClick(type, url, name, quan_id, id, needLogin, needrecord);
        }

    }


    class ViewHolder {
        @BindView(R.id.iv_bg)
        ImageView mIvBg;
        @BindView(R.id.gv)
        NoScrollGridView mGv;

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
