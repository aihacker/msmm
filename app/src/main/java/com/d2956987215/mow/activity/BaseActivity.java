package com.d2956987215.mow.activity;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.StringUtils;
import com.d2956987215.mow.R;
import com.d2956987215.mow.bean.LinkBean;
import com.d2956987215.mow.dialog.LoadingDialog;
import com.d2956987215.mow.dialog.ShouYeDialog;
import com.d2956987215.mow.eventbus.SearchDialogHideEvent;
import com.d2956987215.mow.eventbus.SearchEvent;
import com.d2956987215.mow.listener.IShowData;
import com.d2956987215.mow.listener.RequestPermissionListener;
import com.d2956987215.mow.rxjava.Request;
import com.d2956987215.mow.rxjava.Result;
import com.d2956987215.mow.rxjava.RxJavaUtil;
import com.d2956987215.mow.util.ActivityManager;
import com.d2956987215.mow.util.StatusNavUtils;
import com.d2956987215.mow.util.ToastUtil;
import com.d2956987215.mow.widgets.EmptyRecyclerView;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;


public abstract class BaseActivity extends AppCompatActivity implements IShowData {
    //权限监听
    private static RequestPermissionListener mListener;
    LoadingDialog loadingDialog;
    private BaseActivity baseActivity;
    protected ShouYeDialog mSignDialog;
    private boolean mOutRestart = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (!isTaskRoot()) {
            final Intent intent = getIntent();
            final String intentAction = intent.getAction();
            if (intent.hasCategory(Intent.CATEGORY_LAUNCHER) && intentAction != null && intentAction.equals(Intent
                    .ACTION_MAIN)) {
                finish();
                return;
            }
        }
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        //     StatusNavUtils.setStatusBarColor(this,0x00000000);
        StatusNavUtils.setStatusBarColor(this, 0x00000000);
        baseActivity = this;
        ActivityManager.getAppManager().addActivity(this);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        init();
//        ToastUtils.showShort(getClass().getSimpleName());
    }

    protected void changeStatusBarTextColor(boolean isBlack) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            if (isBlack) {
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//设置状态栏黑色字体
            } else {
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);//恢复状态栏白色字体
            }
        }
    }


    //加载动画

    private LoadingDialog createProgressDialog() {
        LoadingDialog dialog = new LoadingDialog(this);
        dialog.setCancelable(true);
        return dialog;
    }

    public Context getContext() {
        return this;
    }

    public void toast(String text) {
        ToastUtil.show(getContext(), text);
    }

    public void showProgress() {
        if (loadingDialog == null) {
            loadingDialog = createProgressDialog();
        }
        loadingDialog.show();

    }

    public void hideProgress() {
        if (loadingDialog != null) {
            loadingDialog.stop();
        }
    }

    protected void init() {
        if (findViewById(R.id.tv_title) != null) {
            ((TextView) findViewById(R.id.tv_title)).setText(title());
        }
        if (findViewById(R.id.tv_gouwuche) != null) {
            if (xianshiright()) {
                ((ImageView) findViewById(R.id.tv_gouwuche)).setVisibility(View.VISIBLE);
            } else {
                ((ImageView) findViewById(R.id.tv_gouwuche)).setVisibility(View.GONE);
            }

        }
        if (findViewById(R.id.rl_back) != null)
            findViewById(R.id.rl_back).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBack();
                }
            });

        if (findViewById(R.id.fl_close) != null)
            findViewById(R.id.fl_close).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBack();
                }
            });
    }

    //设置标题
    protected String title() {
        return "";
    }

    //设置右边显示
    protected boolean xianshiright() {
        return false;
    }

    protected void onBack() {
        finish();
    }

    protected abstract int getLayoutId();

    @Override
    protected void onDestroy() {
//        ActivityManager.getAppManager().removeActivity(this);

        super.onDestroy();
        try {
            if (mSignDialog != null) {
                mSignDialog.dismiss();
                mSignDialog = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //权限的申请
    public static void requestPermission(String allPermissions[], RequestPermissionListener listener) {
        mListener = listener;
        List<String> permissions = new ArrayList<>();
        Activity activity = ActivityManager.getCurrentActivity();
        for (String permission : allPermissions) {
            assert activity != null;
            if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(permission);
            }
        }
        if (!permissions.isEmpty()) {
            assert activity != null;
            ActivityCompat.requestPermissions(activity, permissions.toArray(new String[permissions.size()]), 1);
        } else {
            listener.onGranted();
        }
    }

    //权限申请结果的处理
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0) {
                    List<String> denyPermission = new ArrayList<>();
                    for (int i = 0; i < grantResults.length; i++) {
                        String permission = permissions[i];
                        if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                            denyPermission.add(permission);
                        }
                    }

                    if (denyPermission.isEmpty()) {
                        mListener.onGranted();
                    } else {
                        mListener.onDeny(denyPermission);
                    }
                }
                break;
        }
    }

    public void setRecyleEmptyView(EmptyRecyclerView emptyRecyclerView, int iv_icon) {
        View v = findViewById(R.id.empty_view);
        if (iv_icon != 0) {
            ImageView iv_empty = (ImageView) v.findViewById(R.id.iv_empty);
            iv_empty.setBackgroundResource(iv_icon);
        }
        emptyRecyclerView.setEmptyView(v);
    }


    private void huoqufuzhi() {
        ClipboardManager cm = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        ClipData data = cm.getPrimaryClip();
        String content = "";
        if (data != null) {
            ClipData.Item item = data.getItemAt(0);
            if (item != null)
                content = item.getText().toString();
        }
        if (!StringUtils.isEmpty(content)) {
            if (!content.matches("[0-9]+"))
                huoqujiantie(content);
        }
    }

    private void huoqujiantie(String content) {
        Map<String, String> map = new HashMap<>();
        map.put("keyword", content);
        new Request<LinkBean>().request(RxJavaUtil.xApi().jiantie(map), "获取剪贴板数据", getContext(), false, new Result<LinkBean>() {
            @Override
            public void get(LinkBean response) {
//                if (response.getData().getType() == 0) {
//                    startActivity(new Intent(BaseActivity.this, TransposeActivity.class).putExtra("content", content).putExtra("type", "0"));
//
//                } else {
//                    if (mSignDialog != null) {
//                        mSignDialog.setMessage(response.getData().getTitle());
//
//                    } else {
//                        mSignDialog = new ShouYeDialog(ActivityUtils.getTopActivity(), response.getData().getTitle());
//                        mSignDialog.show();
//                    }
//                }
                if (mSignDialog != null) {
                    mSignDialog.setMessage(response.getData().getTitle());

                } else {
                    mSignDialog = new ShouYeDialog(ActivityUtils.getTopActivity(), response.getData().getTitle());
                    mSignDialog.show();
                }

            }
        });


    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void searchEvents(SearchEvent event) {
        huoqufuzhi();
        mOutRestart = true;
//        onRestart();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void searchEvent(SearchDialogHideEvent event) {
        mOutRestart = false;

//        ActivityManager.getCurrentActivity().di
//        try {
//
//            if (mSignDialog != null) {
//                mSignDialog.dismiss();
//                mSignDialog = null;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

    }

    public void onResume() {
//        if (mSignDialog != null) {
//            mSignDialog.dismiss();
//            mSignDialog = null;
//        }
        super.onResume();
        MobclickAgent.onResume(this); //统计时长

    }

    public void onPause() {
//        try {
//
//            if (mSignDialog != null) {
//                mSignDialog.dismiss();
//                mSignDialog = null;
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        super.onPause();
        MobclickAgent.onPause(this); //统计时长
//        if (mSignDialog != null) {
//            mSignDialog.dismiss();
//        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        LogUtils.e("wocao" + "onStop");
//        if (mSignDialog != null) {
//            mSignDialog.dismiss();
//            mSignDialog = null;
//        }
    }

    @Override
    protected void onRestart() {
//        try {
//
//            if (mSignDialog != null) {
//                mSignDialog.dismiss();
//                mSignDialog = null;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        super.onRestart();
//        if (mOutRestart)
//            huoqufuzhi();

    }


    @Override
    public void onBackPressed() {

//        if (mSignDialog != null && mSignDialog.isShowing()) {
//            mSignDialog.dismiss();
//            mSignDialog = null;
//        }
        super.onBackPressed();
    }

}
