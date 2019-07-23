package com.d2956987215.mow.rxjava;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;


import com.blankj.utilcode.util.ToastUtils;
import com.d2956987215.mow.R;
import com.d2956987215.mow.dialog.AuthTaoBaoDialog;
import com.d2956987215.mow.dialog.LoadingDialog;
import com.d2956987215.mow.rxjava.response.BaseResponse;
import com.d2956987215.mow.util.ActivityManager;
import com.d2956987215.mow.util.ActivityUtils;
import com.d2956987215.mow.util.NetworkUtils;
import com.d2956987215.mow.util.SP;
import com.d2956987215.mow.util.ToastUtil;

import org.reactivestreams.Subscription;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by lq on 2018/1/24.
 */

public class Request<T extends BaseResponse> {
    private Disposable sp;

    LoadingDialog loadingDialog;


    public void showProgress1(String message, Context context) {
        // super.showProgress(message);
/*if(loadingDialog==null)loadingDialog=LoadingDialog.getInstance(this);
           loadingDialog.show();*/

        if (loadingDialog == null) {
            loadingDialog = createProgressDialog(message, context);
        }
        if (loadingDialog != null) {
            loadingDialog.show();
        }
        // loadingDialog.setMessage(message);


    }

    private LoadingDialog createProgressDialog(String message, Context context) {
        if (context != null) {
            LoadingDialog dialog = new LoadingDialog(context);
            dialog.setCancelable(true);
            //dialog.setMessage(message);
            // dialog.setIndeterminate(true);
            return dialog;
        } else {
            return null;
        }
    }

    public void request(Observable<T> observable, final String tag, final Context context, boolean showProgress, final Result<T> result, String... object) {

        if (context != null && !NetworkUtils.isNetworkAvailable(context)) {
            Toast.makeText(context, tag + "->没有网络，请稍后重试", Toast.LENGTH_SHORT).show();
            return;
        }

        if (showProgress) {
            showProgress1("加载", context);
        }

        observable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<T>() {

                    @Override
                    public void onError(Throwable e) {
//                        ToastUtils.showShort("错误---" + e.toString());
                        if (loadingDialog != null && loadingDialog.isShowing()) {
                            loadingDialog.dismiss();
                        }
                        if (e.toString().contains("com.google.gson.JsonSyntaxException: java.lang.IllegalStateException: Expected BEGIN_OBJECT: retrofit2.adapter.rxjava.HttpException")) {
                            ToastUtil.show(context, context.getString(R.string.have_no_data));
                        } else if (e.toString().contains("401")) {
                            SP.putInt("userId", 0);
                            SP.putString("token", null);
                            ActivityManager.finishguoAllActivity();
                            ToastUtil.show(context, "请重新登录");
//                            Intent intent = new Intent(context, SplashActivity.class);
//                            Intent intent = new Intent(context, LoginNewActivity.class);
//                            context.startActivity(intent);

                            ActivityUtils.startLoginAcitivy(context);
                            return;
                        } else if (e.toString().contains("404") || e.toString().contains("500")) {
                            ToastUtils.showShort("服务器异常");
                        }
                        //ToastUtil.show(context, tag + "->" + e.toString());
                        Log.e("AppOnError", tag + "->onError: " + e.toString());
                        result.onServerError(e.toString());

                    }

                    @Override
                    public void onComplete() {
//                        if (sp != null &&) {
//                            sp.cancel();
//                        }
                        try {

                            if (loadingDialog != null && loadingDialog.isShowing()) {
                                loadingDialog.dismiss();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(T o) {
//                        ToastUtils.showShort("数据响应---message" + o.message + "code" + o.status_code);
                        if (o.status_code == 200) {
                            result.get(o);
                        } else if (o.status_code == 401) {
                            SP.putInt("userId", 0);
                            SP.putString("token", null);
                            ActivityManager.finishguoAllActivity();
                            ToastUtil.show(context, o.message);
//                            Intent intent = new Intent(context, SplashActivity.class);
//                            Intent intent = new Intent(context, LoginNewActivity.class);
//
//                            context.startActivity(intent);


                            ActivityUtils.startLoginAcitivy(context);
                            return;
                        } else if (o.status_code == 206) {
//                            ToastUtil.show(context, o.message);
//                            if (object == null) {
//                                AuthTaoBaoDialog signDialog = new AuthTaoBaoDialog(context);
//                            } else {
//                                result.onServerError("");
//                            }

                            AuthTaoBaoDialog signDialog = new AuthTaoBaoDialog(context);

                        } else {
                            ToastUtil.show(context, o.message);
                            result.onBackErrorMessage(o);
                            Log.e("AppOnError", tag + "->onError: errorCode:" + o.status_code + ",msg:" + o.message);

                        }
                    }
                });
    }
}
