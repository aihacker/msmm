package com.d2956987215.mow.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.Date;
import java.util.List;

import com.d2956987215.mow.R;
import com.d2956987215.mow.listener.ChangeName;

/**
 * Created by lq on 2018/3/10
 */
@SuppressLint("InflateParams")
public class DialogUtil {
    private static int count = 1;

    private static int goodsId;
    private static int kucun;
    private static Date date;


    //将list转换为带有 ， 的字符串
    public static String listToString(List<String> list) {
        StringBuilder sb = new StringBuilder();
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                if (i < list.size() - 1) {
                    sb.append(list.get(i) + ";");
                } else {
                    sb.append(list.get(i));
                }
            }
        }
        return sb.toString();
    }



    //更改名字
    public static Dialog dialogChangeName(final Activity activity, final ChangeName changeName) {
        View popupView = LayoutInflater.from(activity).inflate(R.layout.dialog_change_name, null);
        final Dialog dialogChangeName = new Dialog(activity, R.style.dialogTransparent);
        final EditText editText = popupView.findViewById(R.id.et_new_name);
        popupView.findViewById(R.id.bt_ensure_new_name).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = editText.getText().toString();
                if (s.equals("")) {
                    ToastUtil.show(activity, "请输入姓名");
                    return;
                }
                changeName.name(s);
                dialogChangeName.dismiss();
            }
        });
        dialogChangeName.setContentView(popupView);
        if (dialogChangeName.getWindow() != null)
            dialogChangeName.getWindow().setGravity(Gravity.CENTER);
        dialogChangeName.getWindow().setWindowAnimations(R.style.dialogWindowAnim);
        dialogChangeName.show();
        return dialogChangeName;
    }

    //反馈成功
    public static Dialog dialogsuccess(final Activity activity) {
        View popupView = LayoutInflater.from(activity).inflate(R.layout.dialog_success, null);
        final Dialog dialogChangeName = new Dialog(activity, R.style.dialogTransparent);
        final EditText editText = popupView.findViewById(R.id.et_new_name);
        popupView.findViewById(R.id.bt_ensure_new_name).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogChangeName.dismiss();
                activity.finish();
            }
        });
        dialogChangeName.setContentView(popupView);
        if (dialogChangeName.getWindow() != null)
            dialogChangeName.getWindow().setGravity(Gravity.CENTER);
        dialogChangeName.getWindow().setWindowAnimations(R.style.dialogWindowAnim);
        dialogChangeName.show();
        return dialogChangeName;
    }



    //查看大图
    public static Dialog dialogWatchBigImg(final Activity activity, final String path) {
        View popupView = LayoutInflater.from(activity).inflate(R.layout.dialog_watch_big_img, null);
        ImageView iv = popupView.findViewById(R.id.iv_img);
        Glide.with(activity).load(path).into(iv);
        final Dialog dialogWatchBigImg = new Dialog(activity, R.style.dialogTransparent);
        dialogWatchBigImg.setContentView(popupView);
        if (dialogWatchBigImg.getWindow() != null)
            dialogWatchBigImg.getWindow().setGravity(Gravity.CENTER);
        int paddingL = 0 - 10;
        Window window = dialogWatchBigImg.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(lp);
        dialogWatchBigImg.getWindow().getDecorView().setPadding(paddingL, 0, paddingL, 0);
        dialogWatchBigImg.getWindow().setWindowAnimations(R.style.dialogWindowAnim);
        dialogWatchBigImg.setCancelable(true);
        dialogWatchBigImg.show();
        return dialogWatchBigImg;
    }

    /**
     * 隐藏输入法面板
     *
     * @param activity
     */
    public static void hideInputMethod(Activity activity) {
        if (null == activity) {
            return;
        }
        if (null != activity.getCurrentFocus() && null != activity.getCurrentFocus().getWindowToken()) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(
                    activity.getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
