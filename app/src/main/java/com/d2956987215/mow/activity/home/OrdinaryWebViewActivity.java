package com.d2956987215.mow.activity.home;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.OrientationEventListener;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.webkit.JavascriptInterface;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.d2956987215.mow.R;
import com.d2956987215.mow.activity.APP;
import com.d2956987215.mow.activity.BaseActivity;
import com.d2956987215.mow.activity.SplashActivity;
import com.d2956987215.mow.activity.login.LoginNewActivity;
import com.d2956987215.mow.activity.mine.MemberCenterActivity;
import com.d2956987215.mow.activity.mine.MyHuiYuanDetailActivity;
import com.d2956987215.mow.dialog.GetQuanDialog;
import com.d2956987215.mow.rxjava.Request;
import com.d2956987215.mow.rxjava.Result;
import com.d2956987215.mow.rxjava.RxJavaUtil;
import com.d2956987215.mow.rxjava.response.GetCouponResponse;
import com.d2956987215.mow.rxjava.response.ShareResponse;
import com.d2956987215.mow.util.ActivityUtils;
import com.d2956987215.mow.util.ConstantUtil;
import com.d2956987215.mow.util.TBKUtils;
import com.d2956987215.mow.util.ToastUtil;
import com.d2956987215.mow.util.User;
import com.d2956987215.mow.util.WebViewUtils;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class OrdinaryWebViewActivity extends BaseActivity implements WebViewUtils.WebViewInterface, View.OnClickListener {

    @BindView(R.id.web)
    WebView web;
    private String savePicUrl;
    private String[] permisssions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initWeb();
        web.addJavascriptInterface(OrdinaryWebViewActivity.this, "jsandroid");
    }


    private void initWeb() {
        web.setHorizontalScrollBarEnabled(false);
        web.setVerticalScrollBarEnabled(false);
        WebSettings webSettings = web.getSettings();
        webSettings.setDatabaseEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setGeolocationEnabled(true);
        String dir = APP.application.getDir("database", Context.MODE_PRIVATE).getPath();
        webSettings.setGeolocationDatabasePath(dir);
        webSettings.setDomStorageEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setSaveFormData(false);
        webSettings.setSavePassword(false);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setAppCachePath(APP.application.getCacheDir().getAbsolutePath());
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSettings.setSupportZoom(true);
        web.setWebViewClient(new MyWebViewClient());
        web.loadUrl(getIntent().getStringExtra("url").replace("\\", ""));
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.tv_weixin:
                IWXAPI iwxapi = WXAPIFactory.createWXAPI(OrdinaryWebViewActivity.this, ConstantUtil.WXAPP_ID);
                if (iwxapi.isWXAppInstalled()) {
                    UMImage umImage = new UMImage(OrdinaryWebViewActivity.this, mSharePic);
                    UMWeb web = new UMWeb(mShareUrl);//连接地址
                    web.setTitle(mShareTitle);//标题
                    web.setThumb(umImage);
                    new ShareAction(OrdinaryWebViewActivity.this)
                            .setPlatform(SHARE_MEDIA.WEIXIN)
                            .withMedia(web)
                            .share();

                } else {
                    ToastUtil.show(OrdinaryWebViewActivity.this, "未安装微信");
                }
                break;
            case R.id.tv_pengyouquan:
                IWXAPI iwxapi1 = WXAPIFactory.createWXAPI(OrdinaryWebViewActivity.this, ConstantUtil.WXAPP_ID);
                if (iwxapi1.isWXAppInstalled()) {
                    UMImage umImage = new UMImage(OrdinaryWebViewActivity.this, mSharePic);
                    UMWeb web = new UMWeb(mShareUrl);//连接地址
                    web.setTitle(mShareTitle);//标题
                    web.setThumb(umImage);
                    new ShareAction(OrdinaryWebViewActivity.this)
                            .setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
                            .withMedia(web)
                            .share();

                } else {
                    ToastUtil.show(OrdinaryWebViewActivity.this, "未安装微信");
                }
                break;
            case R.id.tv_weibo:
                UMImage umImage = new UMImage(OrdinaryWebViewActivity.this, mSharePic);
                UMWeb web = new UMWeb(mShareUrl);//连接地址
                web.setTitle(mShareTitle);//标题
                web.setThumb(umImage);
                new ShareAction(OrdinaryWebViewActivity.this)
                        .setPlatform(SHARE_MEDIA.SINA)
                        .withMedia(web)
                        .share();

                break;
            case R.id.tv_qq:
                UMImage umImageqq = new UMImage(OrdinaryWebViewActivity.this, mSharePic);
                UMWeb webqq = new UMWeb(mShareUrl);//连接地址
                webqq.setTitle(mShareTitle);//标题
                webqq.setThumb(umImageqq);
                new ShareAction(OrdinaryWebViewActivity.this)
                        .setPlatform(SHARE_MEDIA.QQ)
                        .withMedia(webqq)
                        .share();

                break;
            case R.id.tv_kongjian:
                UMImage umImageqz = new UMImage(OrdinaryWebViewActivity.this, mSharePic);
                UMWeb webqz = new UMWeb(mShareUrl);//连接地址
                webqz.setTitle(mShareTitle);//标题
                webqz.setThumb(umImageqz);
                new ShareAction(OrdinaryWebViewActivity.this)
                        .setPlatform(SHARE_MEDIA.QZONE)
                        .withMedia(webqz)
                        .share();

                break;
        }
    }


    class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            String url = "";
            //android 7.0系统以上 已经摒弃了shouldOverrideUrlLoading(WebView view, String url)此方法
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                url = request.getUrl().toString();
            } else {
                url = request.toString();
            }
            view.loadUrl(url);
            return true;
        }

    }

    //领券
    @SuppressLint("JavascriptInterface")
    @JavascriptInterface
    public void GetQuan(String itemid, String quanid, String pid) {
        if (User.uid() > 0) {
            if (pid != null) {
                couponRequest(User.uid(), itemid, quanid, pid);
            }
//            else {
//                if (url != null) {
//                    Intent intent = new Intent(this, WebViewActivity.class);
//                    intent.putExtra("url", url);
//                    startActivity(intent);
//                }
//            }
        } else
//            startActivity(new Intent(this, SplashActivity.class));
//        startActivity(new Intent(this, LoginNewActivity.class));
        ActivityUtils.startLoginAcitivy(this);

    }

    //分享赚
    @SuppressLint("JavascriptInterface")
    @JavascriptInterface
    public void ShareQuan(String itemid, String quanid) {
        if (User.uid() > 0) {
            if (!TextUtils.isEmpty(itemid)) {
                shareRequest(User.uid(), itemid, quanid);
            }
        } else
//            startActivity(new Intent(this, SplashActivity.class));
//        startActivity(new Intent(this, LoginNewActivity.class));
        ActivityUtils.startLoginAcitivy(this);
    }

    //升级赚
    @SuppressLint("JavascriptInterface")
    @JavascriptInterface
    public void UpGrade(String uproletype) {
        Intent intent = new Intent(OrdinaryWebViewActivity.this, MemberCenterActivity.class);
        intent.putExtra("uproletype", uproletype);
        OrdinaryWebViewActivity.this.startActivity(intent);
    }

    //商品详情
    @SuppressLint("JavascriptInterface")
    @JavascriptInterface
    public void ToGoodsDetail(String itemid, String quanid) {
        Intent intent = new Intent(getContext(), TaoBaoDetailActivity.class);
        intent.putExtra("id", itemid);
        intent.putExtra("quan_id", quanid);
        startActivity(intent);
    }

    /**
     * 分享
     */
    private String mShareTitle;
    private String mShareUrl;
    private String mSharePic;

    @JavascriptInterface
    public void AppShare(final String title, final String duanurl, final String picurl) {
        mShareTitle = title;
        mShareUrl = duanurl;
        mSharePic = picurl;

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                share(title, duanurl, picurl);

            }
        });


    }

    /**
     * 微信
     */
    @SuppressLint("JavascriptInterface")
    @JavascriptInterface
    public void StudyShareToFriend(final String title, final String duanurl, final String picurl) {
        IWXAPI iwxapi = WXAPIFactory.createWXAPI(OrdinaryWebViewActivity.this, ConstantUtil.WXAPP_ID);
        if (iwxapi.isWXAppInstalled()) {
            UMWeb web = new UMWeb(duanurl);//连接地址
            web.setTitle(title);//标题
            if (!TextUtils.isEmpty(picurl)) {
                UMImage umImage = new UMImage(OrdinaryWebViewActivity.this, picurl);
                web.setThumb(umImage);
            }
            new ShareAction(OrdinaryWebViewActivity.this)
                    .setPlatform(SHARE_MEDIA.WEIXIN)
                    .withMedia(web)
                    .share();

        } else {
            ToastUtil.show(OrdinaryWebViewActivity.this, "未安装微信");
        }


    }

    /**
     * 微信
     */
    @JavascriptInterface
    public void StudyShareToCircle(final String title, final String duanurl, final String picurl) {
        IWXAPI iwxapi = WXAPIFactory.createWXAPI(OrdinaryWebViewActivity.this, ConstantUtil.WXAPP_ID);
        if (iwxapi.isWXAppInstalled()) {
            UMWeb web = new UMWeb(duanurl);//连接地址
            web.setTitle(title);//标题
            if (!TextUtils.isEmpty(picurl)) {
                UMImage umImage = new UMImage(OrdinaryWebViewActivity.this, picurl);
                web.setThumb(umImage);
            }
            new ShareAction(OrdinaryWebViewActivity.this)
                    .setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
                    .withMedia(web)
                    .share();

        } else {
            ToastUtil.show(OrdinaryWebViewActivity.this, "未安装微信");
        }


    }


    /**
     * 复制文字
     */
    @JavascriptInterface
    public void CopyText(final String title) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                // 将文本内容放到系统剪贴板里。
                cm.setText(title);
                Toast.makeText(OrdinaryWebViewActivity.this, "复制成功", Toast.LENGTH_LONG).show();
//                share("", "", "");

            }
        });


    }


    /**
     * 调用淘宝
     */
    @JavascriptInterface
    public void OpenTaobao(final String url) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ToastUtils.showShort("正在前往...");
                TBKUtils.showDetailPageForUrl(OrdinaryWebViewActivity.this, url, "taobao");

            }
        });


    }


    /**
     * 保存图片
     */
    @JavascriptInterface
    public void SavePic(final String url) {
        savePicUrl = url;
        if (permission(permisssions)) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            mHandler.obtainMessage(SAVE_BEGIN).sendToTarget();
                            Bitmap bp = GetImageInputStream(url);
                            saveImageToPhotos(OrdinaryWebViewActivity.this, bp);
                        }
                    }).start();
                }
            });
        } else {
            ActivityCompat.requestPermissions(OrdinaryWebViewActivity.this, permisssions, 111);
        }


    }

    /**
     * 保存图片
     */
    @JavascriptInterface
    public void CloseWeb() {

        runOnUiThread(new Runnable() {

            @Override
            public void run() {
//                ToastUtils.showShort("点击了关闭");
                finish();
            }
        });


    }

    @Override
    protected String title() {
        if (StringUtils.isEmpty(getIntent().getStringExtra("name")))
            return "详情";
        else
            return getIntent().getStringExtra("name");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_webview;
    }

    @Override
    public void show(Object obj) {

    }

    @Override
    public boolean check(WebView webView, String url) {
        return false;
    }

    @Override
    public void JavaScriptObjectMethod(String url) {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (web.canGoBack()) {
                web.goBack();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onBack() {
        if (web.canGoBack()) {
            web.goBack();
            return;
        } else {
            finish();
        }

    }

    private void couponRequest(int userId, String itemid, String coupon_id, final String pids) {

        Map<String, String> map = new HashMap<>();
        map.put("Itemid", itemid);
        map.put("quan_id", coupon_id);
        map.put("user_id", userId + "");
        new Request<GetCouponResponse>().request(RxJavaUtil.xApi().getQuan(map, "Bearer " + User.token()), "优惠券接口", this, true, new Result<GetCouponResponse>() {
            @Override
            public void get(final GetCouponResponse response) {

                if (response.getData().getMenu() != null) {

                    GetQuanDialog getQuanDialog = new GetQuanDialog(OrdinaryWebViewActivity.this, response.getData().getMenu().getDialogtitle(), response.getData().getMenu().getRighttitle(), new GetQuanDialog.CallBack() {
                        @Override
                        public void NO() {

                            switch (response.getData().getMenu().getRighttype()) {
                                case 1:
                                    goGetCoupon(pids, response.getData().getCoupon_rul());
                                    break;
                                case 2:
                                    goGetCoupon(pids, response.getData().getCoupon_rul());
                                    break;
                                case 3:
                                    startActivity(new Intent(OrdinaryWebViewActivity.this, MyHuiYuanDetailActivity.class).putExtra("roletype", User.roleType()));
                                    break;
                            }


                        }
                    });
                    getQuanDialog.show();


                } else {
                    goGetCoupon(pids, response.getData().getCoupon_rul());
                }

            }
        });
    }

    private void goGetCoupon(String pids, String url) {
        String[] pid = pids.split("_");
        TBKUtils.showDetailPageForUrl(OrdinaryWebViewActivity.this, url, "taobao", "", pid[pid.length - 1]);

    }


    private void shareRequest(int userId, String itemid, String coupon_id) {
        Map<String, String> map = new HashMap<>();
        map.put("Itemid", itemid);
        map.put("quan_id", coupon_id);
        map.put("user_id", userId + "");
        new Request<ShareResponse>().request(RxJavaUtil.xApi1().getShare(map, "Bearer " + User.token()), "分享接口", OrdinaryWebViewActivity.this, true, new Result<ShareResponse>() {
            @Override
            public void get(final ShareResponse response) {

                if (response.getData().getMenu() != null) {

                    GetQuanDialog getQuanDialog = new GetQuanDialog(OrdinaryWebViewActivity.this, response.getData().getMenu().getDialogtitle(), response.getData().getMenu().getRighttitle(), new GetQuanDialog.CallBack() {
                        @Override
                        public void NO() {

                            switch (response.getData().getMenu().getRighttype()) {
                                case 1:
                                    goGetShare(response.getData().getDetails());
                                    break;
                                case 2:
                                    goGetShare(response.getData().getDetails());
                                    break;
                                case 3:
                                    startActivity(new Intent(OrdinaryWebViewActivity.this, MyHuiYuanDetailActivity.class).putExtra("roletype", User.upreletype()));
                                    break;
                            }


                        }
                    });
                    getQuanDialog.show();


                } else {
                    goGetShare(response.getData().getDetails());
                }

            }

            @Override
            public void onServerError(String errDesc) {
                super.onServerError(errDesc);
            }

            @Override
            public void onBackErrorMessage(ShareResponse response) {
                super.onBackErrorMessage(response);
            }
        });
    }


    private void goGetShare(ShareResponse.DataBean.DetailsBean data) {
        Intent intent = new Intent(OrdinaryWebViewActivity.this, ShareZhuanActivity.class);
        intent.putStringArrayListExtra("list", (ArrayList<String>) data.getBanner());
        intent.putExtra("title", data.getTitle());
        intent.putExtra("coupon_price", data.getCoupon_price() + "");
        intent.putExtra("yishou", data.getVolume());
        intent.putExtra("yongjin", data.getYongjin() + "");
        intent.putExtra("share_yongjin", data.getShare_yongjin() + "");
        intent.putExtra("nowprice", data.getEndprice());
        intent.putExtra("oldprice", data.getZk_final_price());
        intent.putExtra("shop_type", data.getShop_type());
        intent.putExtra("eurl", data.getEwmurl());
        intent.putExtra("template", data.getTemplate());
        intent.putExtra("temp", data.getTemp());
        intent.putExtra("rhyurl", data.getCoupon_rul());
        intent.putExtra("tkl", data.getTkl());
        intent.putExtra("desc", "");
        intent.putExtra("Comment", data.getComment());
        intent.putExtra("Comment_Template", data.getComment_Template());
        intent.putExtra("ShowMoney", data.getShowMoney());
        intent.putExtra("ShowMoney_Template", data.getShowMoney_Template());
        intent.putExtra("extension", data.getExtension());
        intent.putExtra("studentId", data.getStudentId());
        intent.putExtra("download_link", data.getDownload_link());

        OrdinaryWebViewActivity.this.startActivity(intent);


    }


    // 声明PopupWindow
    private PopupWindow popupWindow;
    // 声明PopupWindow对应的视图
    private View popupView;
    // 声明平移动画
    private TranslateAnimation animation;

    private void share(String title, String duanurl, String picurl) {
        if (popupWindow == null) {
            popupView = View.inflate(this, R.layout.popupwindow_share_bottom, null);

            TextView tv_weixin = popupView.findViewById(R.id.tv_weixin);
            TextView tv_pengyouquan = popupView.findViewById(R.id.tv_pengyouquan);
            TextView tv_weibo = popupView.findViewById(R.id.tv_weibo);
            TextView tv_qq = popupView.findViewById(R.id.tv_qq);
            TextView tv_kongjian = popupView.findViewById(R.id.tv_kongjian);

            tv_weixin.setOnClickListener(this);
            tv_pengyouquan.setOnClickListener(this);
            tv_weibo.setOnClickListener(this);
            tv_qq.setOnClickListener(this);
            tv_kongjian.setOnClickListener(this);


            // 参数2,3：指明popupwindow的宽度和高度
            popupWindow = new PopupWindow(popupView, WindowManager.LayoutParams.MATCH_PARENT,
                    300);

            popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    lighton();
                }
            });

            // 设置背景图片， 必须设置，不然动画没作用
            popupWindow.setBackgroundDrawable(new BitmapDrawable());
            popupWindow.setFocusable(true);

            // 设置点击popupwindow外屏幕其它地方消失
            popupWindow.setOutsideTouchable(true);

            // 平移动画相对于手机屏幕的底部开始，X轴不变，Y轴从1变0
            animation = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0, Animation.RELATIVE_TO_PARENT, 0,
                    Animation.RELATIVE_TO_PARENT, 1, Animation.RELATIVE_TO_PARENT, 0);
            animation.setInterpolator(new AccelerateInterpolator());
            animation.setDuration(200);


        }

        // 在点击之后设置popupwindow的销毁
        if (popupWindow.isShowing()) {
            popupWindow.dismiss();
        }

        // 设置popupWindow的显示位置，此处是在手机屏幕底部且水平居中的位置
        popupWindow.showAtLocation(OrdinaryWebViewActivity.this.findViewById(R.id.web), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        popupView.startAnimation(animation);
        lightoff();
    }

    /**
     * 设置手机屏幕亮度显示正常
     */
    private void lighton() {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 1f;
        getWindow().setAttributes(lp);
    }

    /**
     * 设置手机屏幕亮度变暗
     */
    private void lightoff() {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.3f;
        getWindow().setAttributes(lp);
    }

    private static final int SAVE_SUCCESS = 0;//保存图片成功
    private static final int SAVE_FAILURE = 1;//保存图片失败
    private static final int SAVE_BEGIN = 2;//开始保存图片
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SAVE_BEGIN:
                    ToastUtil.show(OrdinaryWebViewActivity.this, "开始保存图片...");
                    break;
                case SAVE_SUCCESS:
                    ToastUtil.show(OrdinaryWebViewActivity.this, "图片保存成功,请到相册查找");
                    break;
                case SAVE_FAILURE:
                    ToastUtil.show(OrdinaryWebViewActivity.this, "图片保存失败,请稍后再试...");
                    break;
            }
        }
    };

    /**
     * 获取网络图片
     *
     * @param imageurl 图片网络地址
     * @return Bitmap 返回位图
     */
    public Bitmap GetImageInputStream(String imageurl) {
        URL url;
        HttpURLConnection connection = null;
        Bitmap bitmap = null;
        try {
            url = new URL(imageurl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(6000); //超时设置
            connection.setDoInput(true);
            connection.setUseCaches(true); //设置不使用缓存
            InputStream inputStream = connection.getInputStream();
            bitmap = BitmapFactory.decodeStream(inputStream);
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    /**
     * 保存二维码到本地相册
     */
    public static final String ROOTPATH = Environment.getExternalStorageDirectory().getPath();
    public static final String DOWNLOAD_DIR = ROOTPATH + "/download/";

    private void saveImageToPhotos(Context context, Bitmap bmp) {
        // 首先保存图片
        File appDir = new File(DOWNLOAD_DIR, "");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = TimeUtils.getNowMills() + "maishoumama.jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 其次把文件插入到系统图库
//        try {
//            MediaStore.Images.Media.insertImage(context.getContentResolver(),
//                    file.getAbsolutePath(), fileName, null);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//            mHandler.obtainMessage(SAVE_FAILURE).sendToTarget();
//            return;
//        }
        // 最后通知图库更新
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri uri = Uri.fromFile(file);
        intent.setData(uri);
        context.sendBroadcast(intent);
        mHandler.obtainMessage(SAVE_SUCCESS).sendToTarget();
    }


    private boolean permission(String[] permission) {
        int permissipnNum = 0;
        if (permission.length > 0) {
            for (int i = 0; i < permission.length; i++) {
                if (ContextCompat.checkSelfPermission(OrdinaryWebViewActivity.this, permission[i]) == PackageManager.PERMISSION_GRANTED) {
                    permissipnNum++;
                }
            }
            if (permissipnNum == permission.length) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 111:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    Bitmap bp = GetImageInputStream(savePicUrl);
                                    saveImageToPhotos(OrdinaryWebViewActivity.this, bp);
                                }
                            }).start();
                        }
                    });

                } else {
                    Toast.makeText(OrdinaryWebViewActivity.this, "为了您能正常使用，请开启权限", Toast.LENGTH_SHORT).show();
                }
                break;

            default:
                break;
        }
    }

}
