package com.d2956987215.mow.adapter;

import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.d2956987215.mow.R;
import com.d2956987215.mow.activity.SplashActivity;
import com.d2956987215.mow.activity.login.LoginActivity;
import com.d2956987215.mow.activity.login.LoginNewActivity;
import com.d2956987215.mow.rxjava.Request;
import com.d2956987215.mow.rxjava.Result;
import com.d2956987215.mow.rxjava.RxJavaUtil;
import com.d2956987215.mow.rxjava.response.BaseResponse;
import com.d2956987215.mow.rxjava.response.CopyResponse;
import com.d2956987215.mow.rxjava.response.LanMuListResponse;
import com.d2956987215.mow.util.ActivityUtils;
import com.d2956987215.mow.util.ConstantUtil;
import com.d2956987215.mow.util.DateUtils;
import com.d2956987215.mow.util.DisplayUtil;
import com.d2956987215.mow.util.GlideRoundTransform;
import com.d2956987215.mow.util.QRCodeUtil;
import com.d2956987215.mow.util.ToastUtil;
import com.d2956987215.mow.util.User;
import com.d2956987215.mow.widgets.CircleImageView;
import com.d2956987215.mow.widgets.MultiImageView;
import com.d2956987215.mow.widgets.NoScrollGridView;
import com.d2956987215.mow.widgets.ShapedImageView;
import com.d2956987215.mow.widgets.yulanpic.ImageDrawableDetailFragment;
import com.d2956987215.mow.widgets.yulanpic.ImagePagerActivity;

import org.sufficientlysecure.htmltextview.HtmlResImageGetter;
import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Administrator on 2018/3/7.
 */

public class SuCaiAdapter extends BaseQuickAdapter<LanMuListResponse.DataBean, BaseViewHolder> {
    private Activity activity;
    final List<String> mlist = new ArrayList<String>();

    public SuCaiAdapter(@LayoutRes int layoutResId, @Nullable List<LanMuListResponse.DataBean> data, Activity activity) {
        super(layoutResId, data);
        this.activity = activity;
    }

    @Override
    protected void convert(BaseViewHolder helper, final LanMuListResponse.DataBean item) {

        helper.setText(R.id.title, item.getArticle_content());
        CircleImageView head_img = helper.getView(R.id.head_img);
        helper.getView(R.id.tv_tuijian).setVisibility(View.GONE);
        Glide.with(mContext).load(item.getAvatar()).error(R.mipmap.have_no_head).into(head_img);
        helper.setText(R.id.name, item.getName());

        helper.setText(R.id.trends_date, DateUtils.getTimeFormatText(item.getArticle_date()));

        helper.setText(R.id.tv_share, "已分享" + item.getArticle_readnum_v() + "次");
        helper.setText(R.id.tv_count, item.getYongjin());
        helper.setText(R.id.et_content, item.getGood_comment());

        NoScrollGridView recyclerView = helper.getView(R.id.rl_image);
        final ImageView singleImage = helper.getView(R.id.singleImage);
        final TextView tv_haibao = helper.getView(R.id.tv_haibao);
        if (item.getImages() != null && item.getImages().size() > 0) {
            if (item.getImages().size() == 1) {
                recyclerView.setVisibility(View.GONE);
                singleImage.setVisibility(View.VISIBLE);
                tv_haibao.setVisibility(View.VISIBLE);

                int imgWidth = (DisplayUtil.getScreenWidth(mContext) - DisplayUtil.dip2px(mContext, 92)) / 3 * 2 + DisplayUtil.dip2px(mContext, 20);
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) singleImage.getLayoutParams();
                params.width = imgWidth;
                singleImage.setLayoutParams(params);
                Glide.with(mContext).load(item.getImages().get(0)).transform(new GlideRoundTransform(mContext, 10)).into(singleImage);
            } else {
                recyclerView.setVisibility(View.VISIBLE);
                singleImage.setVisibility(View.GONE);
                tv_haibao.setVisibility(View.GONE);

                int imgWidth = (DisplayUtil.getScreenWidth(mContext) - DisplayUtil.dip2px(mContext, 92)) / 3;
                LunSuCaiPicAdapter lunTnPicAdapter = new LunSuCaiPicAdapter(mContext, item.getImages(), imgWidth);
                if (item.getImages().size() == 4) {
                    recyclerView.setNumColumns(2);
                } else {
                    recyclerView.setNumColumns(3);
                }
                recyclerView.setAdapter(lunTnPicAdapter);
            }
        }

        helper.getView(R.id.tv_count).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (User.uid() > 0) {
                    IsNeedRecord(item);
                } else {
//                    mContext.startActivity(new Intent(mContext, SplashActivity.class));

//                    mContext.startActivity(new Intent(mContext, LoginNewActivity.class));

                    ActivityUtils.startLoginAcitivy(mContext);
                }


            }
        });

        helper.getView(R.id.ll_shengcheng).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (User.uid() > 0) {
                    if (item.getIsGetKl() == 1) {
                        if (item.getArticle_goods() != null && item.getArticle_goods().size() > 0) {
                            CopyComment(item.getArticle_goods().get(0).getId() + "", item.getArticle_goods().get(0).getItemtitle(),
                                    item.getArticle_goods().get(0).getZk_final_price(), item.getArticle_goods().get(0).getEndprice());
                        }
                    } else {
                        ClipboardManager cm = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
                        // 将文本内容放到系统剪贴板里。
                        cm.setText(item.getGood_comment());
                        Toast.makeText(mContext, "已复制", Toast.LENGTH_LONG).show();
                    }
                } else {
//                    mContext.startActivity(new Intent(mContext, SplashActivity.class));
//                    mContext.startActivity(new Intent(mContext, LoginNewActivity.class));
                    ActivityUtils.startLoginAcitivy(mContext);
                }

            }
        });

        helper.getView(R.id.title).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ClipboardManager cm = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
                // 将文本内容放到系统剪贴板里。
                cm.setText(item.getArticle_content());
                Toast.makeText(mContext, "复制成功，可以发给朋友们了。", Toast.LENGTH_LONG).show();
                return false;
            }
        });

        recyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (item.getImages() != null && item.getImages().size() > 0) {
                    String[] picArr = new String[item.getImages().size()];
                    for (int j = 0; j < item.getImages().size(); j++) {
                        picArr[j] = item.getImages().get(j);
                    }
                    Intent intent1 = new Intent(activity, ImagePagerActivity.class); //
                    // 图片url,为了演示这里使用常量，一般从数据库中或网络中获取
                    intent1.putExtra(ImagePagerActivity.EXTRA_IMAGE_URLS, picArr);
                    intent1.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, i);
                    activity.startActivity(intent1);
                    activity.overridePendingTransition(R.anim.activity_zoom_open, 0);
                }

            }
        });

        tv_haibao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (User.uid() > 0) {
                    if (item.getIsGenerate() == 0) {
                        item.setIsGenerate(1);
                        haibao(item.getImages().get(0), item.getEwm(), singleImage);
                    } else {
                        ToastUtil.show(mContext, "您的专属海报已生成");
                    }
                } else {
//                    mContext.startActivity(new Intent(mContext, SplashActivity.class));
//                    mContext.startActivity(new Intent(mContext, LoginNewActivity.class));
                    ActivityUtils.startLoginAcitivy(mContext);
                }

            }
        });

        singleImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (item.getIsGenerate() == 1) {
                    Drawable singelDrawable = singleImage.getDrawable();
                    ConstantUtil.drawable = singelDrawable;
                    Intent intent = new Intent(mContext, ImageDrawableDetailFragment.class);
                    mContext.startActivity(intent);
                } else {
                    if (item.getImages() != null && item.getImages().size() > 0) {
                        String[] picArr = new String[1];
                        picArr[0] = item.getImages().get(0);
                        Intent intent1 = new Intent(activity, ImagePagerActivity.class); //
                        // 图片url,为了演示这里使用常量，一般从数据库中或网络中获取
                        intent1.putExtra(ImagePagerActivity.EXTRA_IMAGE_URLS, picArr);
                        intent1.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, 1);
                        activity.startActivity(intent1);
                        activity.overridePendingTransition(R.anim.activity_zoom_open, 0);
                    }
                }

            }
        });


    }


    public interface onClickShareListener {
        void share(String[] hehe, String name, String id);

        void shareBitmap(String picUrl, String qrcodeUrl, String name, String id);
    }

    private onClickShareListener shareListener;

    public void setClickButtonListener(onClickShareListener shareListener) {
        this.shareListener = shareListener;
    }


    private void CopyComment(String Itemid, String itemtitle, String zk_final_price, String endprice) {
        Map<String, String> map = new HashMap<>();
        map.put("Itemid", Itemid);
        map.put("itemtitle", itemtitle);
        map.put("zk_final_price", zk_final_price);
        map.put("endprice", endprice);
        if (User.uid() > 0) {
            map.put("user_id", User.uid() + "");
        }
        new Request<CopyResponse>().request(RxJavaUtil.xApi1().copyComment(map), "论坛头列表", mContext, false, new Result<CopyResponse>() {
            @Override
            public void get(CopyResponse response) {
                if (response != null) {
                    ClipboardManager cm = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
                    // 将文本内容放到系统剪贴板里。
                    cm.setText(response.getData());
                    Toast.makeText(mContext, "已复制", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void haibao(String picUrl, String qrcodeUrl, final ImageView singleImage) {
        final View view = LayoutInflater.from(mContext).inflate(R.layout.layout_haibao1, null);
        final ImageView goodsPic = view.findViewById(R.id.goodsPic);
        final ImageView iv_erweimanew = view.findViewById(R.id.iv_erweimanew);
        final ScrollView scroll = view.findViewById(R.id.scroll);
        Bitmap bmp = QRCodeUtil.createQRCodeBitmap(qrcodeUrl, 400);
        iv_erweimanew.setImageBitmap(bmp);
        final int imgWidth = (DisplayUtil.getScreenWidth(mContext) - DisplayUtil.dip2px(mContext, 92)) / 3 * 2 + DisplayUtil.dip2px(mContext, 20);
        Glide.with(mContext).load(picUrl).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) goodsPic.getLayoutParams();
                params.width = imgWidth;
                goodsPic.setLayoutParams(params);
                goodsPic.setImageBitmap(resource);

                Bitmap bmp = createImage(view, scroll);
                singleImage.setImageBitmap(bmp);
                view.destroyDrawingCache(); // 保存过后释放资源
                return;
            }
        });

    }

    public Bitmap createImage(View view, ScrollView scroll) {
        scroll.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        int IMAGE_HEIGHT = scroll.getMeasuredHeight();
        int IMAGE_WIDTH = scroll.getMeasuredWidth();

        //由于直接new出来的view是不会走测量、布局、绘制的方法的，所以需要我们手动去调这些方法，不然生成的图片就是黑色的。
        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(IMAGE_WIDTH, View.MeasureSpec.EXACTLY);
        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(IMAGE_HEIGHT, View.MeasureSpec.EXACTLY);
        view.measure(widthMeasureSpec, heightMeasureSpec);
        view.layout(0, 0, IMAGE_WIDTH, IMAGE_HEIGHT);
        Bitmap bitmap = Bitmap.createBitmap(IMAGE_WIDTH, IMAGE_HEIGHT, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }

    private void IsNeedRecord(final LanMuListResponse.DataBean item) {
        Map<String, String> map = new HashMap<>();
        if (User.uid() > 0) {
            map.put("user_id", User.uid() + "");
        }
        new Request<BaseResponse>().request(RxJavaUtil.xApi1().IsNeedRecord(map, "Bearer " + User.token()), "首页数据", mContext, false, new Result<BaseResponse>() {

            @Override
            public void get(final BaseResponse response) {
                if (shareListener != null) {
                    if (item.getIsGenerate() == 1) {
                        shareListener.shareBitmap(item.getImages().get(0), item.getEwm(), item.getArticle_content(), item.getId());
                    } else {
                        if (item.getImages() != null && item.getImages().size() > 0) {
                            ArrayList<String> mlist = new ArrayList<>();
                            for (String str : item.getImages()) {                //处理第一个数组,list里面的值为1,2,3,4
                                if (!mlist.contains("")) {
                                    mlist.add(str);  //本地照片Url需要拼接
                                }
                            }

                            if (mlist != null && mlist.size() > 0) {
                                String[] picArr = (String[]) mlist.toArray(new String[mlist.size()]);
                                shareListener.share(picArr, item.getArticle_content(), item.getId());
                            }
                        }

                    }

                }
            }
        });
    }

}
