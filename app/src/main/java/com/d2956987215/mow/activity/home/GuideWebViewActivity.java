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
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.d2956987215.mow.R;
import com.d2956987215.mow.activity.BaseActivity;
import com.d2956987215.mow.eventbus.AuthrotySuccess;
import com.d2956987215.mow.jpush.MsgBean;
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

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import butterknife.BindView;

public class GuideWebViewActivity extends BaseActivity implements WebViewUtils.WebViewInterface, View.OnClickListener {

    @BindView(R.id.web)
    WebView web;
    private WebSettings mSettings;
    private String mShareTitle;
    private String mShareUrl;
    private String mSharePic;
    private String savePicUrl;
    private String titleName = "";
    private String url = "";
    private String[] permisssions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //华为自定义消息跳转配置
        if (getIntent() != null) {
            if (getIntent().getData() != null) {
                String msgJson = getIntent().getData().toString();
                if (!TextUtils.isEmpty(msgJson)) {
                    MsgBean msgBean = JSON.parseObject(msgJson, MsgBean.class);
                    if (msgBean.getN_extras() != null) {
                        titleName = msgBean.getN_title();
                        url = msgBean.getN_extras().getUrl();
                        if (User.uid() > 0) {
                            //拼接user_id
                            if (url.contains("?")) {
                                url += "&user_id=" + User.uid();
                            } else {
                                url += "?user_id=" + User.uid();
                            }
                        }

                    }
                }
            } else {
                titleName = getIntent().getStringExtra("name");
                url = getIntent().getStringExtra("url");
            }
        }

        initWeb();
        web.addJavascriptInterface(GuideWebViewActivity.this, "jsandroid");

    }


    @SuppressLint("JavascriptInterface")
    private void initWeb() {
        new WebViewUtils(web, GuideWebViewActivity.this, this);
        web.loadUrl(url);

////        if (StringUtil.notEmpty(httpUrl) && httpUrl.contains("u0026"))
////            httpUrl = httpUrl.replaceAll("u0026", "&");
//        mSettings = web.getSettings();
//
//        //添加UA,  “app/XXX”：是与h5商量好的标识，h5确认UA为app/XXX就认为该请求的终端为App
//        mSettings.setJavaScriptCanOpenWindowsAutomatically(true);
//        mSettings.setJavaScriptEnabled(true);
//
//        //设置参数
//        mSettings.setBuiltInZoomControls(true);
//        mSettings.setAppCacheEnabled(true);
//        web.loadUrl(getIntent().getStringExtra("url"));
//        web.addJavascriptInterface(GuideWebViewActivity.this, "jsandroid");
//        web.setWebViewClient(new WebViewClient() {
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                view.loadUrl(url);
//                return true;
//            }
//        });//        if (StringUtil.notEmpty(httpUrl) && httpUrl.contains("u0026"))
////            httpUrl = httpUrl.replaceAll("u0026", "&");
//        mSettings = web.getSettings();
//
//        //添加UA,  “app/XXX”：是与h5商量好的标识，h5确认UA为app/XXX就认为该请求的终端为App
//        mSettings.setJavaScriptCanOpenWindowsAutomatically(true);
//        mSettings.setJavaScriptEnabled(true);
//
//        //设置参数
//        mSettings.setBuiltInZoomControls(true);
//        mSettings.setAppCacheEnabled(true);
//        web.loadUrl(getIntent().getStringExtra("url"));
//        web.addJavascriptInterface(GuideWebViewActivity.this, "jsandroid");
//        web.setWebViewClient(new WebViewClient() {
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                view.loadUrl(url);
//                return true;
//            }
//        });


    }

    @Override
    protected String title() {
        if (StringUtils.isEmpty(getIntent().getStringExtra("name")))
            return "活动详情";
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

    /**
     * 分享
     */
    @JavascriptInterface
    public void AppShare(final String title, final String duanurl, final String picurl) {

        mShareTitle = title;
        mShareUrl = duanurl;
        mSharePic = picurl;


        runOnUiThread(new Runnable() {
            @Override
            public void run() {
/*                HashMap<String, Object> optionMap = new HashMap<>();
                optionMap.put("Id", "5");
                optionMap.put("SortId", "5");
                optionMap.put("AppId", "wx07bf5ccf131324cc");
                optionMap.put("AppSecret", "855ae7a34a3f2b9c04a2e76c3afe0218");
                optionMap.put("BypassApproval", false);
                optionMap.put("Enable", true);
                ShareSDK.setPlatformDevInfo(Wechat.NAME, optionMap);
                Platform.ShareParams wechat = new Platform.ShareParams();
                wechat.setShareType(Platform.SHARE_WEBPAGE);
                wechat.setUrl(duanurl);
                wechat.setTitle(title);
                wechat.setImageUrl(picurl);

                Platform weixin = ShareSDK.getPlatform(Wechat.NAME);
                if (weixin.isClientValid()) {
                    //System.out.println("安装了微信");
//                    weixin.setPlatformActionListener(GuideDetailActivity.this);
                    weixin.share(wechat);
                } else {
                    //System.out.println("没有安装了微信");
                    ToastUtil.show(GuideWebViewActivity.this, "未安装微信");
                }*/


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
        IWXAPI iwxapi = WXAPIFactory.createWXAPI(GuideWebViewActivity.this, ConstantUtil.WXAPP_ID);
        if (iwxapi.isWXAppInstalled()) {
            UMWeb web = new UMWeb(duanurl);//连接地址
            web.setTitle(title);//标题
            if (!TextUtils.isEmpty(picurl)) {
                UMImage umImage = new UMImage(GuideWebViewActivity.this, picurl);
                web.setThumb(umImage);
            }
            new ShareAction(GuideWebViewActivity.this)
                    .setPlatform(SHARE_MEDIA.WEIXIN)
                    .withMedia(web)
                    .share();

        } else {
            ToastUtil.show(GuideWebViewActivity.this, "未安装微信");
        }


    }

    /**
     * 微信
     */
    @JavascriptInterface
    public void StudyShareToCircle(final String title, final String duanurl, final String picurl) {
        IWXAPI iwxapi = WXAPIFactory.createWXAPI(GuideWebViewActivity.this, ConstantUtil.WXAPP_ID);
        if (iwxapi.isWXAppInstalled()) {
            UMWeb web = new UMWeb(duanurl);//连接地址
            web.setTitle(title);//标题
            if (!TextUtils.isEmpty(picurl)) {
                UMImage umImage = new UMImage(GuideWebViewActivity.this, picurl);
                web.setThumb(umImage);
            }
            new ShareAction(GuideWebViewActivity.this)
                    .setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
                    .withMedia(web)
                    .share();

        } else {
            ToastUtil.show(GuideWebViewActivity.this, "未安装微信");
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
                Toast.makeText(GuideWebViewActivity.this, "复制成功", Toast.LENGTH_LONG).show();
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
                ToastUtil.show(GuideWebViewActivity.this, "正在前往...");
                TBKUtils.showDetailPageForUrl(GuideWebViewActivity.this, url, "taobao");

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
                            saveImageToPhotos(GuideWebViewActivity.this, bp);
                        }
                    }).start();
                }
            });
        } else {
            ActivityCompat.requestPermissions(GuideWebViewActivity.this, permisssions, 111);
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

    public static final String ROOTPATH = Environment.getExternalStorageDirectory().getPath();
    public static final String DOWNLOAD_DIR = ROOTPATH + "/download/";

    /**
     * 保存二维码到本地相册
     */
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

    private static final int SAVE_SUCCESS = 0;//保存图片成功
    private static final int SAVE_FAILURE = 1;//保存图片失败
    private static final int SAVE_BEGIN = 2;//开始保存图片
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SAVE_BEGIN:
                    ToastUtil.show(GuideWebViewActivity.this, "开始保存图片...");
                    break;
                case SAVE_SUCCESS:
                    ToastUtil.show(GuideWebViewActivity.this, "图片保存成功,请到相册查找");
                    break;
                case SAVE_FAILURE:
                    ToastUtil.show(GuideWebViewActivity.this, "图片保存失败,请稍后再试...");
                    break;
            }
        }
    };


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (web.canGoBack()) {
                web.goBack();
            } else {
                if (!TextUtils.isEmpty(titleName)) {
                    if (titleName.equals("授权")) {
                        EventBus.getDefault().post(new AuthrotySuccess());
                    }
                }
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
            if (!TextUtils.isEmpty(titleName)) {
                if (titleName.equals("授权")) {
                    EventBus.getDefault().post(new AuthrotySuccess());
                }
            }
            finish();
        }


    }

    /**
     * 弹出popupWindow更改头像
     */

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
        popupWindow.showAtLocation(GuideWebViewActivity.this.findViewById(R.id.web), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        popupView.startAnimation(animation);
        lightoff();
    }

    /**
     * 设置手机屏幕亮度变暗
     */
    private void lightoff() {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.3f;
        getWindow().setAttributes(lp);
    }

    /**
     * 设置手机屏幕亮度显示正常
     */
    private void lighton() {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 1f;
        getWindow().setAttributes(lp);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.tv_weixin:
                IWXAPI iwxapi = WXAPIFactory.createWXAPI(GuideWebViewActivity.this, ConstantUtil.WXAPP_ID);
                if (iwxapi.isWXAppInstalled()) {
                    UMImage umImage = new UMImage(GuideWebViewActivity.this, mSharePic);
                    UMWeb web = new UMWeb(mShareUrl);//连接地址
                    web.setTitle(mShareTitle);//标题
                    web.setThumb(umImage);
                    new ShareAction(GuideWebViewActivity.this)
                            .setPlatform(SHARE_MEDIA.WEIXIN)
                            .withMedia(web)
                            .share();

                } else {
                    ToastUtil.show(GuideWebViewActivity.this, "未安装微信");
                }
                break;
            case R.id.tv_pengyouquan:
                IWXAPI iwxapi1 = WXAPIFactory.createWXAPI(GuideWebViewActivity.this, ConstantUtil.WXAPP_ID);
                if (iwxapi1.isWXAppInstalled()) {
                    UMImage umImage = new UMImage(GuideWebViewActivity.this, mSharePic);
                    UMWeb web = new UMWeb(mShareUrl);//连接地址
                    web.setTitle(mShareTitle);//标题
                    web.setThumb(umImage);
                    new ShareAction(GuideWebViewActivity.this)
                            .setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
                            .withMedia(web)
                            .share();

                } else {
                    ToastUtil.show(GuideWebViewActivity.this, "未安装微信");
                }
                break;
            case R.id.tv_weibo:
                UMImage umImage = new UMImage(GuideWebViewActivity.this, mSharePic);
                UMWeb web = new UMWeb(mShareUrl);//连接地址
                web.setTitle(mShareTitle);//标题
                web.setThumb(umImage);
                new ShareAction(GuideWebViewActivity.this)
                        .setPlatform(SHARE_MEDIA.SINA)
                        .withMedia(web)
                        .share();

                break;
            case R.id.tv_qq:
                UMImage umImageqq = new UMImage(GuideWebViewActivity.this, mSharePic);
                UMWeb webqq = new UMWeb(mShareUrl);//连接地址
                webqq.setTitle(mShareTitle);//标题
                webqq.setThumb(umImageqq);
                new ShareAction(GuideWebViewActivity.this)
                        .setPlatform(SHARE_MEDIA.QQ)
                        .withMedia(webqq)
                        .share();

                break;
            case R.id.tv_kongjian:
                UMImage umImageqz = new UMImage(GuideWebViewActivity.this, mSharePic);
                UMWeb webqz = new UMWeb(mShareUrl);//连接地址
                webqz.setTitle(mShareTitle);//标题
                webqz.setThumb(umImageqz);
                new ShareAction(GuideWebViewActivity.this)
                        .setPlatform(SHARE_MEDIA.QZONE)
                        .withMedia(webqz)
                        .share();

                break;
        }
    }


    private boolean permission(String[] permission) {
        int permissipnNum = 0;
        if (permission.length > 0) {
            for (int i = 0; i < permission.length; i++) {
                if (ContextCompat.checkSelfPermission(GuideWebViewActivity.this, permission[i]) == PackageManager.PERMISSION_GRANTED) {
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
                                    saveImageToPhotos(GuideWebViewActivity.this, bp);
                                }
                            }).start();
                        }
                    });

                } else {
                    Toast.makeText(GuideWebViewActivity.this, "为了您能正常使用，请开启权限", Toast.LENGTH_SHORT).show();
                }
                break;

            default:
                break;
        }
    }

}
