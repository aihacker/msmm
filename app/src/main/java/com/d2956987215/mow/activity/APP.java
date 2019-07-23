package com.d2956987215.mow.activity;

import android.app.Activity;
import android.app.Application;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.multidex.MultiDex;
import android.support.v7.app.AppCompatDelegate;

import com.alibaba.baichuan.android.trade.AlibcTradeSDK;
import com.alibaba.baichuan.android.trade.callback.AlibcTradeInitCallback;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.StringUtils;
import com.d2956987215.mow.R;
import com.d2956987215.mow.activity.home.LInkActivity;
import com.d2956987215.mow.activity.home.TransposeActivity;
import com.d2956987215.mow.activity.mine.SearchActivity;
import com.d2956987215.mow.bean.LinkBean;
import com.d2956987215.mow.bean.transposeBean;
import com.d2956987215.mow.dialog.LinkDialog;
import com.d2956987215.mow.dialog.MainSelectDialog;
import com.d2956987215.mow.dialog.ShouYeDialog;
import com.d2956987215.mow.imageloader.GlideImageLoader;
import com.d2956987215.mow.rxjava.Request;
import com.d2956987215.mow.rxjava.Result;
import com.d2956987215.mow.rxjava.RxJavaUtil;
import com.d2956987215.mow.rxjava.response.AdverResponse;
import com.d2956987215.mow.util.ActivityUtils;
import com.d2956987215.mow.util.AppFrontBackHelper;
import com.d2956987215.mow.util.DynamicTimeFormat;
import com.d2956987215.mow.util.MyLog;
import com.d2956987215.mow.util.SP;
import com.d2956987215.mow.util.User;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreater;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.tencent.bugly.Bugly;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import cn.finalteam.galleryfinal.CoreConfig;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.ThemeConfig;
import cn.jpush.android.api.JPushInterface;

public class APP extends Application {
    public static int versionCode = -1;
    /**
     * 应用程序实例
     */
    public static APP application = null;
    private Context mContext;
    private ShouYeDialog mSignDialog;


    public static APP getApplication() {
        if (application == null) {
            application = new APP();
        }
        return application;

    }

    //GalleryFinal
    public static FunctionConfig.Builder functionConfigBuilder = new FunctionConfig.Builder()
            .setEnableCamera(true)
            .setEnableEdit(true)
            .setEnableCrop(true)
            .setEnableRotate(true)
            .setCropSquare(true)
            .setEnablePreview(true);

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        disableAPIDialog();
//        SP.init(this);
//        initGalleryFinal();
//        resolveCamera();
//        JPushInterface.setDebugMode(true);
//        JPushInterface.init(this);
//       // MobSDK.init(this);
//        AlibcTradeSDK.asyncInit(this, new AlibcTradeInitCallback() {
//            @Override
//            public void onSuccess() {
//                //初始化成功，设置相关的全局配置参数
//                MyLog.e("tagsuss", "zhengque");
//                // ...
//            }
//
//            @Override
//            public void onFailure(int code, String msg) {
//                //初始化失败，可以根据code和msg判断失败原因，详情参见错误说明
//                MyLog.e("tagsuss", "code==" + code + "msg====" + msg);
//                AlibcTradeSDK.asyncInit(application, new AlibcTradeInitCallback() {
//                    @Override
//                    public void onSuccess() {
//                        MyLog.e("tagsuss2", "zhengque");
//                    }
//
//                    @Override
//                    public void onFailure(int code, String msg) {
//                        MyLog.e("tagsuss2", "code==" + code + "msg====" + msg);
//                    }
//                });
//            }
//        });
//        Bugly.init(getApplicationContext(), "f2ae68fe62", false);
//
//
        AppFrontBackHelper helper = new AppFrontBackHelper();
        helper.register(APP.this, new AppFrontBackHelper.OnAppStatusListener() {
            @Override
            public void onFront(Activity context) {
                LogUtils.e("context");
//                mContext = context;
                //应用切到前台处理
//                huoqufuzhi();
//                EventBus.getDefault().post(new SearchEvent());
//                if (context.getComponentName().getClassName().)
                if (!context.getClass().equals(GuideActivity.class))
                    huoqufuzhi(context);
            }

            @Override
            public void onBack() {
                //应用切到后台处理
//                EventBus.getDefault().post(new SearchDialogHideEvent());
                if (mSignDialog != null) {
                    mSignDialog.dismiss();
                    mSignDialog = null;
                }

            }
        });
//
//
//        um();


    }

    private void disableAPIDialog() {
        try {
            Class clazz = Class.forName("android.app.ActivityThread");
            Method currentActivityThread = clazz.getDeclaredMethod("currentActivityThread");
            currentActivityThread.setAccessible(true);
            Object activityThread = currentActivityThread.invoke(null);
            Field mHiddenApiWarningShown = clazz.getDeclaredField("mHiddenApiWarningShown");
            mHiddenApiWarningShown.setAccessible(true);
            mHiddenApiWarningShown.setBoolean(activityThread, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void um() {
        /*
注意: 即使您已经在AndroidManifest.xml中配置过appkey和channel值，也需要在App代码中调用初始化接口（如需要使用AndroidManifest.xml中配置好的appkey和channel值，UMConfigure.init调用中appkey和channel参数请置为null）。
*/
        //初始化组件化基础库, 统计SDK/推送SDK/分享SDK都必须调用此初始化接口
        UMConfigure.init(this, "5c07b727b465f52a53000248", "Umeng", UMConfigure.DEVICE_TYPE_PHONE, null);
        //友盟初始化
        UMShareAPI.get(this);//初始化sdk
        UMConfigure.setLogEnabled(true);
        PlatformConfig.setWeixin("wx07bf5ccf131324cc", "855ae7a34a3f2b9c04a2e76c3afe0218");
        PlatformConfig.setQQZone("1106554516", "O57LU6T58lrZqarq");
        PlatformConfig.setSinaWeibo("1177513179", "a774572b5aa2a09cf83d415f52ae230c", "http://www.sharesdk.cn");
    }


    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        SmartRefreshLayout.setDefaultRefreshHeaderCreater(new DefaultRefreshHeaderCreater() {
            @NonNull
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(R.color.three);
                return new ClassicsHeader(context).setTimeFormat(new DynamicTimeFormat("更新于 %s"));
            }
        });

    }


    //GalleryFinal
    private void initGalleryFinal() {
        CoreConfig coreConfig = new CoreConfig.Builder(this, new GlideImageLoader(), new ThemeConfig.Builder()
                .setTitleBarBgColor(Color.parseColor("#2D3E58"))
                .setFabNornalColor(Color.parseColor("#17C4BB"))
                .setFabPressedColor(Color.parseColor("#2D3E58"))
                .setCheckSelectedColor(Color.parseColor("#17C4BB"))
                .setCropControlColor(Color.parseColor("#17C4BB"))
                .build())
                .setFunctionConfig(functionConfigBuilder.build())
                .build();
        GalleryFinal.init(coreConfig);
    }

    //解决相机崩溃的问题
    private void resolveCamera() {
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            builder.detectFileUriExposure();
        }
    }


    private void huoqufuzhi(Activity context) {
        LogUtils.e("11111-开始");
        ClipboardManager cm = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        ClipData data = cm.getPrimaryClip();
        String content = "";
        if (data != null) {
            ClipData.Item item = data.getItemAt(0);
            if (item != null && item.getText() != null)
                content = item.getText().toString();
        }
        if (!StringUtils.isEmpty(content)) {
            LogUtils.e("11111-" + content);
            if (!content.matches("[0-9]+"))
                huoqujiantie(content, context);
        }
    }

    private void huoqujiantie(String content, final Activity context) {
        Map<String, String> map = new HashMap<>();
        map.put("keyword", content);
        new Request<LinkBean>().request(RxJavaUtil.xApi().jiantie(map), "获取剪贴板数据", context, false, new Result<LinkBean>() {
            @Override
            public void get(LinkBean response) {
//                if (mSignDialog != null) {
//                    mSignDialog.setMessage(response.getData().getTitle());
//                }
//                else
//                mSignDialog = new ShouYeDialog(context, response.getData().getTitle());
//                mSignDialog.show();


                if (response.getData().getIsLink() == 0) {
//                    startActivity(new Intent(context, TransposeActivity.class).putExtra("content", content).putExtra("type", "0"));
//                    EventBus.getDefault().postSticky(new transposeBean(content,"0"));


                    MainSelectDialog mainSelectDialog = new MainSelectDialog(context,content, id -> {

                    });
                    mainSelectDialog.show();

                } else {
                    LinkDialog dialg = new LinkDialog(context, response,content, () -> {

                    });
                    dialg.show();

                }
            }
        });


    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

}
