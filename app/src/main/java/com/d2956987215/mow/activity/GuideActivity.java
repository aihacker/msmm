package com.d2956987215.mow.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.StringUtils;
import com.d2956987215.mow.Act_Welcome;
import com.d2956987215.mow.R;
import com.d2956987215.mow.activity.home.LInkActivity;
import com.d2956987215.mow.activity.home.TransposeActivity;
import com.d2956987215.mow.activity.home.UserGuideActivity;
import com.d2956987215.mow.activity.kotlin.BaseActivity;
import com.d2956987215.mow.bean.LinkBean;
import com.d2956987215.mow.dialog.LinkDialog;
import com.d2956987215.mow.dialog.ShouYeDialog;
import com.d2956987215.mow.imageloader.GlideImageLoader;
import com.d2956987215.mow.rxjava.Request;
import com.d2956987215.mow.rxjava.Result;
import com.d2956987215.mow.rxjava.RxJavaUtil;
import com.d2956987215.mow.rxjava.response.GuideResponse;
import com.d2956987215.mow.util.ActivityUtils;
import com.d2956987215.mow.util.AppFrontBackHelper;
import com.d2956987215.mow.util.NetworkUtils;
import com.d2956987215.mow.util.PermissionUtils;
import com.d2956987215.mow.util.SP;
import com.d2956987215.mow.util.User;
import com.d2956987215.mow.mvp.p.ParsingPresenter;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import cn.finalteam.galleryfinal.CoreConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.ThemeConfig;

import static com.d2956987215.mow.activity.APP.functionConfigBuilder;


/**
 * 启动欢迎页面
 */
public class GuideActivity extends BaseActivity {
    //    @BindView(R.id.image)
    ImageView mImage;


    /**
     * 欢迎页动画
     */
    private Animation animation;
    private Context mContext;
    private String content = "";

    /**
     * 是否是第一次使用
     */
    private final String[] PERMISSIONS = new String[]{Manifest.permission.CAMERA
            , Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.READ_PHONE_STATE};

    // 打开相机请求Code，多个权限请求Code
    private final int REQUEST_CODE_PERMISSIONS = 2;
    private GuideResponse.DataBean.ListBean databean;

    private String isFirst = "";
    private ShouYeDialog mSignDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_welcome);
//        initSDK();

//
    }

    private void initSDK() {
//        initGalleryFinal();
//        resolveCamera();
//        JPushInterface.setDebugMode(true);
//        JPushInterface.init(this);
//        Bugly.init(getApplicationContext(), "f2ae68fe62", false);
//        um();
//        AlibcTradeSDK.asyncInit(APP.getApplication(), new AlibcTradeInitCallback() {
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
//                AlibcTradeSDK.asyncInit(APP.getApplication(), new AlibcTradeInitCallback() {
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


//        AppFrontBackHelper helper = new AppFrontBackHelper();
//        helper.register(APP.getApplication(), new AppFrontBackHelper.OnAppStatusListener() {
//            @Override
//            public void onFront(Activity context) {
//                LogUtils.e("context");
////                mContext = context;
//                //应用切到前台处理
////                huoqufuzhi();
////                EventBus.getDefault().post(new SearchEvent());
////                if (context.getComponentName().getClassName().)
//                if (context != null && !context.getClass().equals(GuideActivity.class))
//                    huoqufuzhi(context);
//            }
//
//            @Override
//            public void onBack() {
//                //应用切到后台处理
////                EventBus.getDefault().post(new SearchDialogHideEvent());
//                if (mSignDialog != null) {
//                    mSignDialog.dismiss();
//                    mSignDialog = null;
//                }
//
//            }
//        });
//
//
    }

//    @Override
//    protected int getLayoutId() {
//        return R.layout.act_welcome;
//    }


    private void huoqu() {
        Map<String, Object> map = new HashMap<>();
        int uid = User.uid();
        if (uid > 0) {
            map.put("user_id", uid + "");
        }
        parsingPresenter.start("getguide", "getguide", "", map);
//        new Request<GuideResponse>().request(RxJavaUtil.xApi().getguide(map), "闪屏页图", this, false, new Result<GuideResponse>() {
//            @Override
//            public void get(GuideResponse response) {
//                if (response != null && response.getData() != null) {
//                    if (response.getData().getList() != null && response.getData().getList().size() > 0) {
//                        databean = response.getData().getList().get(0);
//
//                        if (databean != null) {
//                            Intent intent = new Intent(GuideActivity.this, Act_Welcome.class);
//                            intent.putExtra("databean", JSON.toJSONString(databean));
//                            startActivity(intent);
//                            finish();
//                        } else {
//                            Intent intent = new Intent(GuideActivity.this, MainActivity.class);
//                            startActivity(intent);
//                            finish();
//                        }
//
//                    } else {
//                        Intent intent = new Intent(GuideActivity.this, MainActivity.class);
//                        startActivity(intent);
//                        finish();
//                    }
//                } else {
//                    Intent intent = new Intent(GuideActivity.this, MainActivity.class);
//                    startActivity(intent);
//                    finish();
//                }
//
//
//            }
//
//            @Override
//            public void onServerError(String errDesc) {
//                Intent intent = new Intent(GuideActivity.this, MainActivity.class);
//                startActivity(intent);
//                finish();
//            }
//
//            @Override
//            public void onBackErrorMessage(GuideResponse response) {
//                Intent intent = new Intent(GuideActivity.this, MainActivity.class);
//                startActivity(intent);
//                finish();
//            }
//        });
//


    }

    private ParsingPresenter parsingPresenter = null;

    private void initData() {
        parsingPresenter = new ParsingPresenter(this);
        if (!TextUtils.isEmpty(isFirst)) {
            if (isFirst.equals("1")) {
                if (NetworkUtils.isNetworkAvailable(GuideActivity.this)) {
                    huoqu();
                } else {
                    Intent intent = new Intent(GuideActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }

//                Intent intent = new Intent(GuideActivity.this, MainActivity.class);
//                startActivity(intent);
//                finish();
            } else {
                Intent intent = new Intent(GuideActivity.this, UserGuideActivity.class);
                startActivity(intent);
                finish();

//                huoqu();
            }

        } else {
            Intent intent = new Intent(GuideActivity.this, UserGuideActivity.class);
            startActivity(intent);
            finish();
        }
    }


    @Override
    public void onStop() {
        super.onStop();
    }


    //开始录像
    public void startRecord() {
        PermissionUtils.checkMorePermissions(mContext, PERMISSIONS, new PermissionUtils.PermissionCheckCallBack() {
            @Override
            public void onHasPermission() {
                initData();
            }

            @Override
            public void onUserHasAlreadyTurnedDown(String... permission) {
                PermissionUtils.requestMorePermissions(mContext, PERMISSIONS, REQUEST_CODE_PERMISSIONS);

//                showExplainDialog(permission, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        PermissionUtils.requestMorePermissions(mContext, PERMISSIONS, REQUEST_CODE_PERMISSIONS);
//                    }
//                });
            }

            @Override
            public void onUserHasAlreadyTurnedDownAndDontAsk(String... permission) {
                PermissionUtils.requestMorePermissions(mContext, PERMISSIONS, REQUEST_CODE_PERMISSIONS);
            }
        });
    }


    /**
     * 解释权限的dialog
     */
    private void showExplainDialog(String[] permission, DialogInterface.OnClickListener onClickListener) {
        new AlertDialog.Builder(mContext)
                .setTitle("申请权限")
//                .setMessage("我们需要" + Arrays.toString(permission) + "权限")
                .setPositiveButton("确定", onClickListener)
                .show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {

            case REQUEST_CODE_PERMISSIONS:
                PermissionUtils.onRequestMorePermissionsResult(mContext, PERMISSIONS, new PermissionUtils.PermissionCheckCallBack() {
                    @Override
                    public void onHasPermission() {
                        initData();
                    }

                    @Override
                    public void onUserHasAlreadyTurnedDown(String... permission) {
                        Toast.makeText(mContext, "我们需要" + Arrays.toString(permission) + "权限", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onUserHasAlreadyTurnedDownAndDontAsk(String... permission) {
                        Toast.makeText(mContext, "我们需要" + Arrays.toString(permission) + "权限", Toast.LENGTH_SHORT).show();
                        showToAppSettingDialog();
                    }
                });


        }
    }

    /**
     * 显示前往应用设置Dialog
     */
    private void showToAppSettingDialog() {
        new AlertDialog.Builder(mContext)
                .setTitle("需要权限")
                .setMessage("我们需要相关权限，才能实现功能，点击前往，将转到应用的设置界面，请开启应用的相关权限。")
                .setPositiveButton("前往", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        PermissionUtils.toAppSetting(mContext);
                    }
                })
                .setNegativeButton("取消", null).show();
    }

//    @Override
//    public void show(Object obj) {
//
//    }


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
        if (context == null) {
            return;
        }
        ClipboardManager cm = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        ClipData data = cm.getPrimaryClip();

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
        parsingPresenter.start("jiantie", "jiantie", "", map);
//        new Request<LinkBean>().request(RxJavaUtil.xApi().jiantie(map), "获取剪贴板数据", context, false, new Result<LinkBean>() {
//            @Override
//            public void get(LinkBean response) {
//                if (mSignDialog != null) {
//                    mSignDialog.setMessage(response.getData().getTitle());
//                } else
//                    mSignDialog = new ShouYeDialog(context, response.getData().getTitle());
//                mSignDialog.show();
////                jiantie LinkBean
////                if (response.getData().getType() == 0) {
////                    startActivity(new Intent(GuideActivity.this, TransposeActivity.class).putExtra("content", content).putExtra("type", "0"));
////                } else {
////                    LinkDialog dialg = new LinkDialog(GuideActivity.this, response.getData().getTitle() + "", () -> {
////                        if (User.uid() < 0) {
////                            ActivityUtils.startLoginAcitivy(GuideActivity.this);
////                            return;
////                        }
////                        Map<String, String> map1 = new HashMap<>();
////                        map1.put("keyword", content);
////                        map1.put("type", response.getData().getType() + "");
////                        map1.put("user_id", User.uid() + "");
////                        new Request<LinkBean>().request(RxJavaUtil.xApi().chainLink(map1), "", context, true, new Result<LinkBean>() {
////                            @Override
////                            public void get(LinkBean response1) {
////                                EventBus.getDefault().post(response1);
////                                startActivity(new Intent(GuideActivity.this, LInkActivity.class));
////                            }
////                        });
////
////                    });
////                    dialg.show();
////
////
////                }
//
//            }
//        });
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



    @Override
    public Activity initview() {
        mContext = this;
        //  startRecord();
        SP.init(this);
        mImage = findViewById(R.id.image);
        isFirst = SP.getString("first", "");
//        initSDK();
        initData();
        return this;
    }

    @Override
    public <T> void setData(@NotNull String type, T bean) {
        if ("jiantie".equals(type) && bean != null) {

            LinkBean response = (LinkBean) bean;
            if (mSignDialog != null) {
                mSignDialog.setMessage(response.getData().getTitle());
            } else
                mSignDialog = new ShouYeDialog(GuideActivity.this, response.getData().getTitle());
            mSignDialog.show();
//            if (response.getData().getType() == 0) {
//                startActivity(new Intent(GuideActivity.this, TransposeActivity.class).putExtra("content", content).putExtra("type", "0"));
//            } else {
//                LinkDialog dialg = new LinkDialog(GuideActivity.this, response.getData().getTitle() + "", () -> {
//                    if (User.uid() < 0) {
//                        ActivityUtils.startLoginAcitivy(GuideActivity.this);
//                        return;
//                    }
//                    Map<String, String> map1 = new HashMap<>();
//                    map1.put("keyword", content);
//                    map1.put("type", response.getData().getType() + "");
//                    map1.put("user_id", User.uid() + "");
//                    new Request<LinkBean>().request(RxJavaUtil.xApi().chainLink(map1), "", GuideActivity.this, true, new Result<LinkBean>() {
//                        @Override
//                        public void get(LinkBean response1) {
//                            EventBus.getDefault().post(response1);
//                            startActivity(new Intent(GuideActivity.this, LInkActivity.class));
//                        }
//                    });
//
//                });
//                dialg.show();


        }

     else{
        GuideResponse response = null;
        if (bean != null) {
            response = (GuideResponse) bean;
        }
        if (response != null && response.getData() != null) {
            if (response.getData().getList() != null && response.getData().getList().size() > 0) {
                databean = response.getData().getList().get(0);

                if (databean != null) {
                    Intent intent = new Intent(GuideActivity.this, Act_Welcome.class);
                    intent.putExtra("databean", JSON.toJSONString(databean));
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(GuideActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }

            } else {
                Intent intent = new Intent(GuideActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        } else {
            Intent intent = new Intent(GuideActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }


    }


}

    @Override
    public void onError(@NotNull String type, @NotNull Throwable error) {
        Intent intent = new Intent(GuideActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
