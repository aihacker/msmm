package com.d2956987215.mow.dialog;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.widget.ImageView;

import com.d2956987215.mow.R;


/**
 * Created by xf on 2016/5/25.
 */
public class LoadingDialog extends BaseDialog {
    ImageView img;
    AnimationDrawable animationDrawable;
    LoadingDialog loadingDialog;


    public LoadingDialog(Context context) {
        super(context);
        loadingDialog = this;
    }

    @Override
    public void show() {
        super.show();
        init();
    }


    private void init() {
        setContentView(R.layout.ml_dialog_loading);
        img = findViewById(R.id.image);
        animationDrawable = (AnimationDrawable) img.getDrawable();
        animationDrawable.start();
    }

    public void start() {
        if (loadingDialog != null && !loadingDialog.isShowing()) {
            loadingDialog.show();
        }
    }

    public void stop() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            animationDrawable.stop();
            loadingDialog.dismiss();
        }
    }

}
