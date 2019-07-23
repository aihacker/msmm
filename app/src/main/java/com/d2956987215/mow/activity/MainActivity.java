package com.d2956987215.mow.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.TextView;

import com.alibaba.baichuan.android.trade.AlibcTradeSDK;
import com.alibaba.baichuan.android.trade.callback.AlibcTradeInitCallback;
import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.d2956987215.mow.R;
import com.d2956987215.mow.activity.home.BrandSaleActivity;
import com.d2956987215.mow.activity.home.GuideWebViewActivity;
import com.d2956987215.mow.activity.home.HomeFragment;
import com.d2956987215.mow.activity.home.JuhuaSuanActivity;
import com.d2956987215.mow.activity.home.OrdinaryWebViewActivity;
import com.d2956987215.mow.activity.home.QiangGouListActivity;
import com.d2956987215.mow.activity.home.TaoBaoDetailActivity;
import com.d2956987215.mow.activity.home.TransposeActivity;
import com.d2956987215.mow.activity.kotlin.BaseActivity;
import com.d2956987215.mow.activity.luntan.LunTanFragment;
import com.d2956987215.mow.activity.mine.BanZhangActivity;
import com.d2956987215.mow.activity.mine.MemberCenterActivity;
import com.d2956987215.mow.activity.mine.MineFragment;
import com.d2956987215.mow.activity.mine.MyYaoQingActivity;
import com.d2956987215.mow.activity.mine.SearchActivity;
import com.d2956987215.mow.activity.product.ProductFragment;
import com.d2956987215.mow.bean.LinkBean;
import com.d2956987215.mow.bean.transposeBean;
import com.d2956987215.mow.constant.Const;
import com.d2956987215.mow.dialog.LinkDialog;
import com.d2956987215.mow.dialog.MainSelectDialog;
import com.d2956987215.mow.dialog.ShouYeDialog;
import com.d2956987215.mow.dialog.UpdateIDDialog;
import com.d2956987215.mow.eventbus.AnimBottomBar;
import com.d2956987215.mow.eventbus.RefreshListData;
import com.d2956987215.mow.eventbus.ResetMainActivityNavigation;
import com.d2956987215.mow.eventbus.ResetOneLevelCateEvent;
import com.d2956987215.mow.eventbus.SwitchFenLeiNavigationEvent;
import com.d2956987215.mow.imageloader.GlideImageLoader;
import com.d2956987215.mow.jpush.ExampleUtil;
import com.d2956987215.mow.jpush.LocalBroadcastManager;
import com.d2956987215.mow.listener.RequestPermissionListener;
import com.d2956987215.mow.mvp.p.ParsingPresenter;
import com.d2956987215.mow.rxjava.Request;
import com.d2956987215.mow.rxjava.Result;
import com.d2956987215.mow.rxjava.RxJavaUtil;
import com.d2956987215.mow.rxjava.response.BaseResponse;
import com.d2956987215.mow.rxjava.response.GuideResponse;
import com.d2956987215.mow.rxjava.response.RulesResponse;
import com.d2956987215.mow.rxjava.response.UpdateResponse;
import com.d2956987215.mow.util.ActivityManager;
import com.d2956987215.mow.util.ActivityUtils;
import com.d2956987215.mow.util.AppFrontBackHelper;
import com.d2956987215.mow.util.MyLog;
import com.d2956987215.mow.util.NavigationUtil;
import com.d2956987215.mow.util.PermissionUtils;
import com.d2956987215.mow.util.SP;
import com.d2956987215.mow.util.TBKUtils;
import com.d2956987215.mow.util.ToastUtil;
import com.d2956987215.mow.util.User;
import com.tencent.bugly.Bugly;
import com.tencent.mm.opensdk.modelbiz.WXLaunchMiniProgram;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.finalteam.galleryfinal.CoreConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.ThemeConfig;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

import static com.d2956987215.mow.activity.APP.functionConfigBuilder;

public class MainActivity extends BaseActivity {
    public static final String TAG = "MainActivity";
    BottomNavigationView navigation;
    private HomeFragment homeFragment;
    private ProductFragment productFragment;
    private LunTanFragment lunTanFragment;
    private MineFragment mineFragment;
    private Fragment currentFragment;
    public static MainActivity mainActivity;
    private int curentPageIndex = 1;//当前页下标，默认1是首页
    private final String MY_PACKAGE_NAME = "com.d2956987215.mow";
    private final String YYB_PACKAGE_NAME = "com.tencent.android.qqdownloader";
    private ShouYeDialog mSignDialog;
    private ParsingPresenter parsingPresenter = new ParsingPresenter(this);


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener

            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    if (homeFragment == null) {
                        homeFragment = new HomeFragment();
                    }
                    switchFragment(homeFragment, "HomeFragment");
                    EventBus.getDefault().post(new ResetOneLevelCateEvent());
                    return true;
                case R.id.navigation_product:
                    if (productFragment == null) {
                        productFragment = new ProductFragment();
                    }
                    curentPageIndex = 2;
                    switchFragment(productFragment, "ProductFragment");
                    return true;
                case R.id.navigation_shop_car:
                    if (lunTanFragment == null) {
                        lunTanFragment = new LunTanFragment();
                    }
                    curentPageIndex = 3;
                    switchFragment(lunTanFragment, "LunTanFragment");
                    return true;
                case R.id.navigation_mine:
                    if (User.uid() > 0) {

                    } else {
//                        Intent intent = new Intent(MainActivity.this, SplashActivity.class);
//                        Intent intent = new Intent(MainActivity.this, LoginNewActivity.class);
//                        startActivity(intent);
                        com.d2956987215.mow.util.ActivityUtils.startLoginAcitivy(MainActivity.this);
                        return false;
                    }
                    if (mineFragment == null) {
                        mineFragment = new MineFragment();
                    }
                    curentPageIndex = 4;
                    switchFragment(mineFragment, "MineFragment");
                    return true;
            }
            return false;
        }

    };
    private long mExitTime;
    private final String[] PERMISSIONS = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.READ_PHONE_STATE};
    private GuideResponse.DataBean.ListBean databean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainActivity = this;
//        if (SplashActivity.loginActivity != null) {
//            SplashActivity.loginActivity.finish();
//        }
        initSDK();
        navigation = (BottomNavigationView) findViewById(R.id.navigation);

        Resources resource = (Resources) getBaseContext().getResources();
        ColorStateList csl = (ColorStateList) resource.getColorStateList(R.color.navigation_menu_item_color);
        navigation.setItemTextColor(csl);
/**设置MenuItem默认选中项**/
        navigation.getMenu().getItem(0).setChecked(true);


        NavigationUtil.disableShiftMode(navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
//         judgeIfNeedUpdate();
        initFragment();

        EventBus.getDefault().register(this);
        LogUtils.e("222-" + "onCreate");

        registerMessageReceiver();  // used for receive msg
        setAlias();

        //活动页点击事件
        String json = getIntent().getStringExtra("json");
        if (!TextUtils.isEmpty(json)) {
            databean = JSON.parseObject(json, GuideResponse.DataBean.ListBean.class);
            if (databean != null) {
                toIntent(databean);
            }
        }

        getData();

        PermissionUtils.checkMorePermissions(MainActivity.this, PERMISSIONS, new PermissionUtils.PermissionCheckCallBack() {
            @Override
            public void onHasPermission() {

            }

            @Override
            public void onUserHasAlreadyTurnedDown(String... permission) {
                PermissionUtils.requestMorePermissions(MainActivity.this, PERMISSIONS, 111);
            }

            @Override
            public void onUserHasAlreadyTurnedDownAndDontAsk(String... permission) {
                PermissionUtils.requestMorePermissions(MainActivity.this, PERMISSIONS, 111);
            }
        });

//        mHandler.sendEmptyMessageDelayed(3, 1500);
//        huoqufuzhi();
    }

    //判断是否需要更新（app版本号低于后台版本号就更新）
    private void judgeIfNeedUpdate() {
        new Request<UpdateResponse>().request(RxJavaUtil.xApi().update("android"), "更新判断", getApplicationContext(), false, new Result<UpdateResponse>() {
            @Override
            public void get(UpdateResponse response) {
                if (getAppVersionCode(getApplicationContext()) < response.getData().getApp_version()) {
                    APP.versionCode = getAppVersionCode(getApplicationContext());
                    dialogUpdate(MainActivity.this, response.getData().getContent() + "");
                }
            }
        });
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent != null) {
            if ("buyNow".equals(intent.getStringExtra("from"))) {
                //navigation.setSelectedItemId(R.id.navigation_home);
                navigation.getMenu().getItem(0).setChecked(true);
                navigation.setSelectedItemId(0);
                if (homeFragment == null) {
                    homeFragment = new HomeFragment();
                }
                switchFragment(homeFragment, "HomeFragment");
            }
//            if (intent.getBooleanExtra("isRefreshData", false)) {
//                EventBus.getDefault().post(new RefreshListData());
//            }
        }
    }

    /**
     * 获取系统版本号
     *
     * @param context
     * @return
     */
    public static int getAppVersionCode(Context context) {
        PackageInfo pi = null;
        try {
            pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        assert pi != null;
        return pi.versionCode;
    }

    private void initFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        homeFragment = new HomeFragment();
        currentFragment = homeFragment;
        transaction.replace(R.id.fragment_content, homeFragment).commit();
    }

//    @Override
//    protected int getLayoutId() {
//        return R.layout.activity_main;
//    }

//    @Override
//    public void show(Object obj) {
//
//    }

    //正确的做法
    private void switchFragment(Fragment targetFragment, String tag) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (!targetFragment.isAdded() && targetFragment.getTag() == null) {
            transaction
                    .hide(currentFragment)
                    .add(R.id.fragment_content, targetFragment, tag)
                    .commit();
        } else {
            transaction
                    .hide(currentFragment)
                    .show(targetFragment)
                    .commit();
        }
        currentFragment = targetFragment;
    }

//    private void handlePermission() {
//        requestPermission(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, new RequestPermissionListener() {
//            @Override
//            public void onGranted() {
//                //做相应操作
//            }
//
//            @Override
//            public void onDeny(List<String> permissions) {
//                //弹出Toast或者提示
//            }
//        });
//    }

    //更新Dialog
    public Dialog dialogUpdate(final Activity activity, String str) {
        View popupView = LayoutInflater.from(activity).inflate(R.layout.dialog_update, null);
        TextView content = popupView.findViewById(R.id.tv_content);
        final Dialog dialogUpdate = new Dialog(activity, R.style.dialogTransparent);
        dialogUpdate.setContentView(popupView);
        if (dialogUpdate.getWindow() != null)
            dialogUpdate.getWindow().setGravity(Gravity.CENTER);
        dialogUpdate.getWindow().setWindowAnimations(R.style.dialogWindowAnim);
        content.setText(str);
        popupView.findViewById(R.id.bt_ensure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toYYBUpdate();
                dialogUpdate.dismiss();
            }
        });
        popupView.findViewById(R.id.bt_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogUpdate.dismiss();
            }
        });
        dialogUpdate.show();
        return dialogUpdate;
    }

    private void toYYBUpdate() {
        if (isAvailable(this, YYB_PACKAGE_NAME)) {
            // 市场存在
            ToastUtil.show(getApplicationContext(), "正在跳转到应用宝");
            launchAppDetail(getApplicationContext(), MY_PACKAGE_NAME, YYB_PACKAGE_NAME);
        } else {
            ToastUtil.show(getApplicationContext(), "跳转网页版应用宝");
            Uri uri = Uri.parse("http://a.app.qq.com/o/simple.jsp?pkgname=" + MY_PACKAGE_NAME);
            Intent it = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(it);
        }
    }

    // 判断市场是否存在的方法
    public static boolean isAvailable(Context context, String packageName) {
        final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        List<String> pName = new ArrayList<>();// 用于存储所有已安装程序的包名
        // 从pinfo中将包名字逐一取出，压入pName list中
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                pName.add(pn);
            }
        }
        return pName.contains(packageName);// 判断pName中是否有目标程序的包名，有TRUE，没有FALSE
    }

    /**
     * 启动到app详情界面
     *
     * @param appPkg    App的包名
     * @param marketPkg 应用商店包名 ,如果为""则由系统弹出应用商店列表供用户选择,否则调转到目标市场的应用详情界面，某些应用商店可能会失败
     */
    public static void launchAppDetail(Context context, String appPkg, String marketPkg) {
        try {
            if (TextUtils.isEmpty(appPkg)) {
                return;
            }
            Uri uri = Uri.parse("market://details?id=" + appPkg);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            if (!TextUtils.isEmpty(marketPkg)) {
                intent.setPackage(marketPkg);
            }
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void animBottomBar(AnimBottomBar animBottomBar) {
//        Glide.with(getContext()).load(changeHead.head).into(circleHead);
        if (animBottomBar.isShow()) {
            AlphaAnimation showanimation = new AlphaAnimation(1f, 0.4f);
            showanimation.setDuration(300);
            showanimation.setFillAfter(true);
            navigation.startAnimation(showanimation);
        } else {
            AlphaAnimation hideanimation = new AlphaAnimation(0.4f, 1f);
            hideanimation.setDuration(300);
            hideanimation.setFillAfter(true);
            navigation.startAnimation(hideanimation);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void switchFenLeiNavigation(SwitchFenLeiNavigationEvent fenLeiNavigationEvent) {
        navigation.setSelectedItemId(R.id.navigation_product);
    }

    @SuppressLint("ResourceType")
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void toProductNavigation(ResetMainActivityNavigation resetMainActivityNavigation) {
        navigation.getMenu().getItem(1).setChecked(true);
        navigation.setSelectedItemId(1);
        if (productFragment == null) {
            productFragment = new ProductFragment();
            switchFragment(productFragment, "ProductFragment");
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
        EventBus.getDefault().unregister(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                ToastUtil.show(MainActivity.this, "再按一次退出程序");
                mExitTime = System.currentTimeMillis();
            } else {

                ActivityManager.finishAllActivity();
                finish();
//                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        LogUtils.e("222-" + "onRestart");
//        huoqufuzhi();

//        if (mSignDialog != null)
//            mSignDialog.dismiss();
    }


    private void huoqufuzhi() {


//        MainSelectDialog mainSelectDialog = new MainSelectDialog(this, id -> {
//            Intent intent = new Intent(this, SearchActivity.class);
//            String type = "1";
//            if (id == 1) {
//                type = "1";
//            } else if (id == 2) {
//                type = "2";
//            } else if (id == 3) {
//                type = "3";
//            } else {
//                return;
//            }
//            intent.putExtra("type", type);
//            startActivity(intent);
//        });
//        mainSelectDialog.show();

//        try {
//            ClipboardManager cm = (ClipboardManager) getActivity().getSystemService(CLIPBOARD_SERVICE);
//            ClipData data = cm.getPrimaryClip();
//            ClipData.Item item = data.getItemAt(0);
//            String content = item.getText().toString();
//            if (content == null) {
//                return;
//            }
//            if (jianqie != null && jianqie.length() > 0) {
//                if (jianqie.equals(content)) {
//                    return;
//                } else {
//
//
//                    jianqie = content;
//                    huoqujiantie(content);
//
//
//                }
//
//            } else {
//                jianqie = content;
//                if (content != null) {
//                    huoqujiantie(content);
//
////
////                    ShouYeDialog signDialog = new ShouYeDialog(getActivity(), content);
////                    signDialog.show();
//
//                }
//
//
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        try {

            ClipboardManager cm = (ClipboardManager) APP.getApplication().getSystemService(CLIPBOARD_SERVICE);
            ClipData data = cm.getPrimaryClip();
            String content = "";
            if (data != null) {
                ClipData.Item item = data.getItemAt(0);
                if (item != null)
                    content = item.getText().toString();
            }
            if (!StringUtils.isEmpty(content)) {
                LogUtils.e("2222" + content);
                if (!content.matches("[0-9]+")) {
                    huoqujiantie(content);
                }


            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtils.e("222" + "onResume");
    }

    private void huoqujiantie(String content) {
        Map<String, String> map = new HashMap<>();
        map.put("keyword", content);
        parsingPresenter.start("jiantie", "jiantie", "", map);
//        new Request<LinkBean>().request(RxJavaUtil.xApi().jiantie(map), "获取剪贴板数据", getContext(), false, new Result<LinkBean>() {
//            @Override
//            public void get(LinkBean response) {
//
//
//                if (mSignDialog != null) {
//                    mSignDialog.setMessage(response.getData().getTitle());
//
//                } else {
//                    mSignDialog = new ShouYeDialog(MainActivity.this, response.getData().getTitle());
//                    mSignDialog.show();
//                }
//
//
////                if (response.getData().getType() == -1) {
////                    startActivity(new Intent(MainActivity.this, TransposeActivity.class).putExtra("content", content).putExtra("type", "0"));
////                } else {
////                    LinkDialog dialog = new LinkDialog(MainActivity.this, response.getData().getTitle() + "", () -> {
////                        if (User.uid() < 0) {
////                            ActivityUtils.startLoginAcitivy(MainActivity.this);
////                            return;
////                        }
////                        Map<String, String> map1 = new HashMap<>();
////                        map1.put("keyword", content);
////                        map1.put("type", response.getData().getType() + "");
////                        map1.put("user_id", User.uid() + "");
////                        new Request<LinkBean>().request(RxJavaUtil.xApi().chainLink(map1), "", MainActivity.this, true, new Result<LinkBean>() {
////                            @Override
////                            public void get(LinkBean response1) {
////                                EventBus.getDefault().postSticky(response1);
////                                startActivity(new Intent(MainActivity.this, LInkActivity.class));
////                            }
////                        });
////
////                    });
////                    dialog.show();
////
////
////                }
//
//
//            }
//        });


    }


    public static boolean isForeground = false;
    //for receive customer msg from jpush server
    private MessageReceiver mMessageReceiver;
    public static final String MESSAGE_RECEIVED_ACTION = "com.example.jpushdemo.MESSAGE_RECEIVED_ACTION";
    public static final String KEY_TITLE = "title";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";

    public void registerMessageReceiver() {
        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(MESSAGE_RECEIVED_ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, filter);
    }

    @NotNull
    @Override
    public Activity initview() {
        return this;
    }


    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onEventMainThread(transposeBean bean) {
//        startActivity(new Intent(MainActivity.this, TransposeActivity.class).putExtra("content", bean.getContent()).putExtra("type", "0"));



//        MainSelectDialog mainSelectDialog = new MainSelectDialog(this, id -> {
//            Intent intent = new Intent(this, SearchActivity.class);
//            String type = "1";
//            if (id == 1) {
//                type = "1";
//            } else if (id == 2) {
//                type = "2";
//            } else if (id == 3) {
//                type = "3";
//            } else {
//                return;
//            }
//            intent.putExtra("type", type);
//            intent.putExtra("keyword",bean.getContent());
//            startActivity(intent);
//        });
//        mainSelectDialog.show();

    }


    @Override
    public <T> void setData(@NotNull String type, T bean) {
        if ("IsNeedRecord" == type) {
            toDetail(databean.getDirectType(), databean.getUrl(), databean.getTitle(), databean.getQuan_id(), databean.getAid());
        } else if ("jiantie".equals(type) && bean != null) {

            LinkBean response = (LinkBean) bean;
//            if (mSignDialog != null) {
//                mSignDialog.setMessage(response.getData().getTitle());
//            } else {
//                mSignDialog = new ShouYeDialog(MainActivity.this, response.getData().getTitle());
//                mSignDialog.show();
//            }


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
//
//
        }


    }

    @Override
    public void onError(@NotNull String type, @NotNull Throwable error) {
        Log.e("error", "error--" + error.getMessage().toString());

    }

    public class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
                    String messge = intent.getStringExtra(KEY_MESSAGE);
                    String extras = intent.getStringExtra(KEY_EXTRAS);
                    StringBuilder showMsg = new StringBuilder();
                    showMsg.append(KEY_MESSAGE + " : " + messge + "\n");
                    if (!ExampleUtil.isEmpty(extras)) {
                        showMsg.append(KEY_EXTRAS + " : " + extras + "\n");
                    }
                    setCostomMsg(showMsg.toString());
                }
            } catch (Exception e) {
            }
        }
    }

    private void setCostomMsg(String msg) {
        ToastUtils.showShort(msg);
//        if (null != msgText) {
//            msgText.setText(msg);
//            msgText.setVisibility(android.view.View.VISIBLE);
//        }
    }


    // 这是来自 JPush Example 的设置别名的 Activity 里的代码。一般 App 的设置的调用入口，在任何方便的地方调用都可以。
    private void setAlias() {

//        if () {
//            Toast.makeText(PushSetActivity.this,R.string.error_alias_empty, Toast.LENGTH_SHORT).show();
//            return;
//        }
//        if (!ExampleUtil.isValidTagAndAlias(alias)) {
//            Toast.makeText(PushSetActivity.this,R.string.error_tag_gs_empty, Toast.LENGTH_SHORT).show();
//            return;
//        }


        if (User.uid() > 0) {
            if (mHandler != null) {
                mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_ALIAS, SPUtils.getInstance().getString(Const.USER_ID, User.uid() + "")));
            }
        }

    }

    private final TagAliasCallback mAliasCallback = new TagAliasCallback() {
        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            String logs;
            switch (code) {
                case 0:
                    logs = "Set tag and alias success";
                    Log.i(TAG, logs);
                    // 建议这里往 SharePreference 里写一个成功设置的状态。成功设置一次后，以后不必再次设置了。
                    SPUtils.getInstance().put(Const.ALIAS_TYPE, true);
                    Log.d("zzz", logs);
                    break;
                case 6002:
                    SPUtils.getInstance().put(Const.ALIAS_TYPE, false);
                    logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
                    Log.i(TAG, logs);
                    // 延迟 60 秒来调用 Handler 设置别名
                    if (mHandler != null) {
                        mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_ALIAS, alias), 1000 * 60);
                    }

                    break;
                default:
                    logs = "Failed with errorCode = " + code;
                    Log.e(TAG, logs);
            }
//            ExampleUtil.showToast(logs, getApplicationContext());
        }
    };
    private static final int MSG_SET_ALIAS = 1001;
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_SET_ALIAS:
                    LogUtils.d(TAG, "Set alias in handler.");
                    // 调用 JPush 接口来设置别名。
                    JPushInterface.setAliasAndTags(getApplicationContext(),
                            (String) msg.obj,
                            null,
                            mAliasCallback);
                    break;
                case 3:
                    huoqufuzhi();
                    break;
                default:
                    LogUtils.i(TAG, "Unhandled msg - " + msg.what);
            }
        }
    };

    //0 不执行     1 商品     2 分类    3 普通 h5  4 sdk（阿里百川    小程序等）   5 限时抢购    6 品牌特卖    7 带解析的 h5 8会员升级 9邀请好友 10聚划算 11联系班长/联系运营商
    private void toIntent(GuideResponse.DataBean.ListBean databean) {
        if (databean.getNeedLogin() == 1) {
            //需要登录才能看
            if (User.uid() > 0) {
                if (databean.getNeedrecord() == 1) {
                    //需要备案,去备案
                    IsNeedRecord(databean.getDirectType(), databean.getUrl(), databean.getTitle(), databean.getQuan_id(), databean.getAid());
                    return;
                } else {
                    //不需要备案,继续执行以下跳转
                    toDetail(databean.getDirectType(), databean.getUrl(), databean.getTitle(), databean.getQuan_id(), databean.getAid());
                }
            } else {
//                startActivity(new Intent(MainActivity.this, SplashActivity.class));
//                startActivity(new Intent(this, LoginNewActivity.class));
                com.d2956987215.mow.util.ActivityUtils.startLoginAcitivy(this);
                return;
            }
        } else {
            //不需要登录就能看
            if (databean.getNeedrecord() == 1) {
                if (User.uid() > 0) {
                    //需要备案已登陆,去备案
                    IsNeedRecord(databean.getDirectType(), databean.getUrl(), databean.getTitle(), databean.getQuan_id(), databean.getAid());
                    return;
                } else {
                    //需要备案没有登陆，去登陆
//                    startActivity(new Intent(MainActivity.this, SplashActivity.class));
//                    startActivity(new Intent(MainActivity.this, LoginNewActivity.class));
                    com.d2956987215.mow.util.ActivityUtils.startLoginAcitivy(this);
                    return;
                }

            } else {
                //不需要登陆不需要备案,继续执行以下跳转
                toDetail(databean.getDirectType(), databean.getUrl(), databean.getTitle(), databean.getQuan_id(), databean.getAid());
            }
        }
    }

    private static boolean isPkgInstalled(Context context, String pkgName) {
        PackageInfo packageInfo;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(pkgName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            packageInfo = null;
            e.printStackTrace();
        }
        return packageInfo != null;
    }

    private void IsNeedRecord(final int type, final String url, final String name, final String quan_id, final String aid) {
        Map<String, String> map = new HashMap<>();
        if (User.uid() > 0) {
            map.put("user_id", User.uid() + "");
        }

//        parsingPresenter.start("IsNeedRecord", "IsNeedRecord", "", map, "Bearer " + User.token());

        new Request<BaseResponse>().request(RxJavaUtil.xApi1().IsNeedRecord(map, "Bearer " + User.token()), "首页数据", MainActivity.this, false, new Result<BaseResponse>() {

            @Override
            public void get(final BaseResponse response) {
                toDetail(type, url, name, quan_id, aid);
            }
        });
    }

    private void toDetail(int type, String url, String name, String quan_id, String aid) {

        Intent intent;
        switch (type) {
            case 0:
                break;
            case 1:
                intent = new Intent(getContext(), TaoBaoDetailActivity.class);
                intent.putExtra("id", aid);
                intent.putExtra("quan_id", quan_id);
                startActivity(intent);
                break;
            case 2:
                EventBus.getDefault().post(new ResetMainActivityNavigation());
                break;
            case 3:
                intent = new Intent(MainActivity.this, OrdinaryWebViewActivity.class);
                intent.putExtra("url", url);
                intent.putExtra("name", name);
                startActivity(intent);
                break;
            case 4:
                if (!TextUtils.isEmpty(url)) {
                    if (url.contains("wechat")) {
                        String userName = Uri.parse(url).getQueryParameter("userName");
                        String path = Uri.parse(url).getQueryParameter("path");
                        //小程序
                        String appId = "wx07bf5ccf131324cc"; // 填应用AppId
                        IWXAPI api = WXAPIFactory.createWXAPI(MainActivity.this, appId);
                        WXLaunchMiniProgram.Req req = new WXLaunchMiniProgram.Req();
                        req.userName = userName; // 填小程序原始id
                        req.path = path;                  //拉起小程序页面的可带参路径，不填默认拉起小程序首页
                        req.miniprogramType = WXLaunchMiniProgram.Req.MINIPTOGRAM_TYPE_RELEASE;// 可选打开 开发版，体验版和正式版
                        api.sendReq(req);
                    } else if (url.contains("taobao")) {
                        //淘宝客户端
                        if (isPkgInstalled(MainActivity.this, "com.taobao.taobao")) {
                            TBKUtils.showDetailPageForUrl(MainActivity.this, url.replaceAll("", "\\"), "taobao");
                        } else {
                            ToastUtil.show(MainActivity.this, "您还没有安装淘宝客户端");
                        }
                    }
                }
                break;
            case 5:
                intent = new Intent(getContext(), QiangGouListActivity.class);
                intent.putExtra("name", "限时抢购");
                intent.putExtra("activity_type", "3");
                startActivity(intent);
                break;
            case 6:
                intent = new Intent(MainActivity.this, BrandSaleActivity.class);
                startActivity(intent);
                break;
            case 7:
                intent = new Intent(MainActivity.this, GuideWebViewActivity.class);
                intent.putExtra("url", url);
                intent.putExtra("name", name);
                startActivity(intent);
                break;
            case 8:
                intent = new Intent(MainActivity.this, MemberCenterActivity.class);
                startActivity(intent);
                break;
            case 9:
                if (aid.equals("0")) {
                    UpdateIDDialog updateIDDialog = new UpdateIDDialog(MainActivity.this, new UpdateIDDialog.CallBack() {
                        @Override
                        public void NO() {
                            Intent intent = new Intent(MainActivity.this, MemberCenterActivity.class);
                            startActivity(intent);

                        }
                    });
                    updateIDDialog.show();
                } else {
                    intent = new Intent(MainActivity.this, MyYaoQingActivity.class);
                    startActivity(intent);
                }

                break;
            case 10:
                intent = new Intent(MainActivity.this, JuhuaSuanActivity.class);
                intent.putExtra("activity_type", aid);
                intent.putExtra("activity_name", name);
                startActivity(intent);
                break;
            case 11:
                intent = new Intent(MainActivity.this, BanZhangActivity.class);
                intent.putExtra("roleType", aid);
                startActivity(intent);
                break;

        }
    }

    private void getData() {
        new Request<RulesResponse>().request(RxJavaUtil.xApi1().rules(), "规则", MainActivity.this, false, new Result<RulesResponse>() {
            @Override
            public void get(RulesResponse response) {
                if (response.getData() != null) {
                    SP.putString("rules", JSON.toJSONString(response.getData()));
                }
            }

            @Override
            public void onServerError(String errDesc) {
                super.onServerError(errDesc);
            }

            @Override
            public void onBackErrorMessage(RulesResponse response) {
                super.onBackErrorMessage(response);
            }
        });

    }


    private void initSDK() {
        initGalleryFinal();
        resolveCamera();
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);

        Bugly.init(getApplicationContext(), "f2ae68fe62", false);
        um();


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
//                    huoqufuzhi();
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


        // MobSDK.init(this);
        AlibcTradeSDK.asyncInit(APP.getApplication(), new AlibcTradeInitCallback() {
            @Override
            public void onSuccess() {
                //初始化成功，设置相关的全局配置参数
                MyLog.e("tagsuss", "zhengque");
                // ...
            }

            @Override
            public void onFailure(int code, String msg) {
                //初始化失败，可以根据code和msg判断失败原因，详情参见错误说明
                MyLog.e("tagsuss", "code==" + code + "msg====" + msg);
                AlibcTradeSDK.asyncInit(APP.getApplication(), new AlibcTradeInitCallback() {
                    @Override
                    public void onSuccess() {
                        MyLog.e("tagsuss2", "zhengque");
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        MyLog.e("tagsuss2", "code==" + code + "msg====" + msg);
                    }
                });
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


}
