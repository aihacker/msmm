package com.d2956987215.mow.widgets;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.d2956987215.mow.R;

public class SearchTicketDialog {
    private Dialog dialog;
    private Context mContext;

    public SearchTicketDialog(Context mContext) {
        this.mContext = mContext;
    }

    public void showDialog() {
        dialog = new Dialog(mContext, R.style.CommentDialog);
        //自定义布局
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_searchticket, null);
        dialog.setContentView(view);

        GifView gifView = view.findViewById(R.id.gifview);
        gifView.setMovieResource(R.raw.gifview);

        //全屏
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }

        dialog.setCancelable(false);
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity(Gravity.CENTER);
        dialogWindow.getDecorView().setPadding(0, 0, 0, 0);


        //设置dialog的动画效果
        dialogWindow.setWindowAnimations(R.style.dialogWindowAnim);
        dialog.show();

    }

    public void dismissDislog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}
