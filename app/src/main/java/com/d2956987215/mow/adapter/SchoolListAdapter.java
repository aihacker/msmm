package com.d2956987215.mow.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.View;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.d2956987215.mow.R;
import com.d2956987215.mow.activity.SplashActivity;
import com.d2956987215.mow.activity.home.GuideWebViewActivity;
import com.d2956987215.mow.activity.login.LoginNewActivity;
import com.d2956987215.mow.dialog.ShareDialogView;
import com.d2956987215.mow.rxjava.Request;
import com.d2956987215.mow.rxjava.Result;
import com.d2956987215.mow.rxjava.RxJavaUtil;
import com.d2956987215.mow.rxjava.response.BaseResponse;
import com.d2956987215.mow.rxjava.response.SchoolListResponse;
import com.d2956987215.mow.util.ActivityUtils;
import com.d2956987215.mow.util.ConstantUtil;
import com.d2956987215.mow.util.ToastUtil;
import com.d2956987215.mow.util.User;
import com.d2956987215.mow.widgets.ShapedImageView;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMWeb;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Administrator on 2018/3/7.
 */

public class SchoolListAdapter extends BaseQuickAdapter<SchoolListResponse.DataBean, BaseViewHolder> {
    private String mId;
    private int shareIndex = -1;

    public SchoolListAdapter(@LayoutRes int layoutResId, @Nullable List<SchoolListResponse.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final SchoolListResponse.DataBean item) {
        helper.setText(R.id.tv_title, item.getTitle());
        helper.setText(R.id.tv_time, item.getCreated_at());
        helper.setText(R.id.tv_count, item.getShareNum() + "");
        ShapedImageView imageView = helper.getView(R.id.iv_pic);
        Glide.with(mContext).load(item.getPic()).into(imageView);

        helper.getView(R.id.ll_item).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, GuideWebViewActivity.class);
                intent.putExtra("url", item.getDetailUrl());
                intent.putExtra("name", "文章详情");
                mContext.startActivity(intent);
            }
        });

        helper.getView(R.id.tv_count).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (User.uid() > 0) {
                    mId = item.getId();
                    shareIndex = helper.getAdapterPosition();
                    ShareDialogView shareDialogView = new ShareDialogView((Activity) mContext, new ShareDialogView.CallBack() {
                        @Override
                        public void weixinhaoyou() {
                            if (User.uid() > 0) {
                                IWXAPI iwxapi1 = WXAPIFactory.createWXAPI(mContext, ConstantUtil.WXAPP_ID);
                                if (iwxapi1.isWXAppInstalled()) {
                                    UMWeb web = new UMWeb(item.getDetailUrl());//连接地址
                                    web.setTitle(item.getTitle());//标题
                                    web.setDescription(item.getTitle());//描述
                                    new ShareAction((Activity) mContext)
                                            .setPlatform(SHARE_MEDIA.WEIXIN)
                                            .withMedia(web)
                                            .setCallback(new MyUMShareListener())
                                            .share();
                                } else {
                                    ToastUtil.show(mContext, "未安装微信");
                                }

                            } else {
                                ToastUtil.show(mContext, "请先登录");
//                                Intent intent = new Intent(mContext, SplashActivity.class);
//                                Intent intent = new Intent(mContext, LoginNewActivity.class);
//                                mContext.startActivity(intent);
                                ActivityUtils.startLoginAcitivy(mContext);

                            }
                        }

                        @Override
                        public void pengyouquan() {
                            if (User.uid() > 0) {
                                IWXAPI iwxapi1 = WXAPIFactory.createWXAPI(mContext, ConstantUtil.WXAPP_ID);
                                if (iwxapi1.isWXAppInstalled()) {
                                    UMWeb web = new UMWeb(item.getDetailUrl());//连接地址
                                    web.setTitle(item.getTitle());//标题
                                    web.setDescription(item.getTitle());//描述
                                    new ShareAction((Activity) mContext)
                                            .setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
                                            .withMedia(web)
                                            .setCallback(new MyUMShareListener())
                                            .share();
                                } else {
                                    ToastUtil.show(mContext, "未安装微信");
                                }

                            } else {
                                ToastUtil.show(mContext, "请先登录");
//                                Intent intent = new Intent(mContext, SplashActivity.class);
//                                Intent intent = new Intent(mContext, LoginNewActivity.class);
//                                mContext.startActivity(intent);
                                ActivityUtils.startLoginAcitivy(mContext);
                            }

                        }

                        @Override
                        public void qqhaoyou() {
                            if (User.uid() > 0) {
                                UMWeb web = new UMWeb(item.getDetailUrl());//连接地址
                                web.setTitle(item.getTitle());//标题
                                web.setDescription(item.getTitle());//描述
                                new ShareAction((Activity) mContext)
                                        .setPlatform(SHARE_MEDIA.QQ)
                                        .withMedia(web)
                                        .setCallback(new MyUMShareListener())
                                        .share();

                            } else {
                                ToastUtil.show(mContext, "请先登录");
//                                Intent intent = new Intent(mContext, SplashActivity.class);
//                                Intent intent = new Intent(mContext, LoginNewActivity.class);
//                                mContext.startActivity(intent);
                                ActivityUtils.startLoginAcitivy(mContext);
                            }

                        }

                        @Override
                        public void qqkongjian() {
                            if (User.uid() > 0) {
                                UMWeb web = new UMWeb(item.getDetailUrl());//连接地址
                                web.setTitle(item.getTitle());//标题
                                web.setDescription(item.getTitle());//描述
                                new ShareAction((Activity) mContext)
                                        .setPlatform(SHARE_MEDIA.QZONE)
                                        .withMedia(web)
                                        .setCallback(new MyUMShareListener())
                                        .share();

                            } else {
                                ToastUtil.show(mContext, "请先登录");
//                                Intent intent = new Intent(mContext, SplashActivity.class);
//                                Intent intent = new Intent(mContext, LoginNewActivity.class);
//                                mContext.startActivity(intent);
                                ActivityUtils.startLoginAcitivy(mContext);
                            }
                        }

                        @Override
                        public void xinlangweibo() {
                            if (User.uid() > 0) {
                                UMWeb web = new UMWeb(item.getDetailUrl());//连接地址
                                web.setTitle(item.getTitle());//标题
                                web.setDescription(item.getTitle());//描述
                                new ShareAction((Activity) mContext)
                                        .setPlatform(SHARE_MEDIA.SINA)
                                        .withMedia(web)
                                        .setCallback(new MyUMShareListener())
                                        .share();

                            } else {
                                ToastUtil.show(mContext, "请先登录");
//                                Intent intent = new Intent(mContext, SplashActivity.class);
//                                Intent intent = new Intent(mContext, LoginNewActivity.class);
//                                mContext.startActivity(intent);
                                ActivityUtils.startLoginAcitivy(mContext);
                            }
                        }

                        @Override
                        public void save() {

                        }
                    }, false);
                    shareDialogView.show();
                } else

                {
                    ToastUtil.show(mContext, "请先登录");
//                    Intent intent = new Intent(mContext, SplashActivity.class);
//                    Intent intent = new Intent(mContext, LoginNewActivity.class);
//                    mContext.startActivity(intent);
                    ActivityUtils.startLoginAcitivy(mContext);
                    return;
                }
            }
        });
    }

    private class MyUMShareListener implements UMShareListener {

        @Override
        public void onStart(SHARE_MEDIA share_media) {
            ToastUtil.show(mContext, "分享开始，请稍后");
            shareRequest();
        }

        @Override
        public void onResult(SHARE_MEDIA share_media) {
            ToastUtil.show(mContext, "分享成功");
        }

        @Override

        public void onError(SHARE_MEDIA share_media, Throwable throwable) {
            ToastUtil.show(mContext, "分享失败");
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media) {
            ToastUtil.show(mContext, "分享取消");
        }
    }

    private void shareRequest() {
        Map<String, String> map = new HashMap<>();
        map.put("id", mId);
        new Request<BaseResponse>().request(RxJavaUtil.xApi().SchoolAddShareNum(map), "分享回调", mContext, false, new Result<BaseResponse>() {
            @Override
            public void get(BaseResponse response) {
                if (shareIndex >-1){
                    int shareNum = getData().get(shareIndex).getShareNum();
                    getData().get(shareIndex).setShareNum(shareNum + 1);
                    notifyItemChanged(shareIndex);
                }

            }
        });

    }
}
