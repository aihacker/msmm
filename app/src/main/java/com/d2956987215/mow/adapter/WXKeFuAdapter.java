package com.d2956987215.mow.adapter;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.blankj.utilcode.util.TimeUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.d2956987215.mow.R;
import com.d2956987215.mow.activity.APP;
import com.d2956987215.mow.rxjava.response.MemberUpgradeResponse;
import com.d2956987215.mow.util.ImageUtils;
import com.d2956987215.mow.util.ToastUtil;
import com.d2956987215.mow.widgets.CircleImageView;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class WXKeFuAdapter extends BaseQuickAdapter<MemberUpgradeResponse.DataBean.WxBean, BaseViewHolder> {
private AppCompatActivity mContext;

    public WXKeFuAdapter(int layoutResId, @Nullable List<MemberUpgradeResponse.DataBean.WxBean> data,AppCompatActivity context) {
        super(layoutResId, data);
        this.mContext=context;
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, final MemberUpgradeResponse.DataBean.WxBean dataBean) {
        if (!TextUtils.isEmpty(dataBean.getTitle())) {
            baseViewHolder.setText(R.id.tv_title, dataBean.getTitle());
        }
        if (!TextUtils.isEmpty(dataBean.getText())) {
            baseViewHolder.setText(R.id.tv_text, dataBean.getText());
        }
        if (!TextUtils.isEmpty(dataBean.getWx())) {
            baseViewHolder.setText(R.id.tv_wechatName, dataBean.getWx());
        }

        if (!TextUtils.isEmpty(dataBean.getRealname())) {
            baseViewHolder.setText(R.id.grade_name, dataBean.getRealname());
        }

        if (!TextUtils.isEmpty(dataBean.getRolename())) {
            baseViewHolder.setText(R.id.grade, dataBean.getRolename());
        }
        if (!TextUtils.isEmpty(dataBean.getStudentid())) {
            baseViewHolder.setText(R.id.nickname, dataBean.getStudentid());
        }

        CircleImageView circle_head = baseViewHolder.getView(R.id.circle_head);
        if (!TextUtils.isEmpty(dataBean.getAvatar())) {
            Glide.with(mContext).load(dataBean.getAvatar()).error(R.drawable.maishou).into(circle_head);
        }

        ImageView iv_wxqrcode = baseViewHolder.getView(R.id.iv_wxqrcode);
        if (!TextUtils.isEmpty(dataBean.getWxqrcode())) {
            Glide.with(mContext).load(dataBean.getWxqrcode()).error(R.drawable.maishou).into(iv_wxqrcode);
        }

        baseViewHolder.getView(R.id.tv_copy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager cm = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
                // 将文本内容放到系统剪贴板里。
                cm.setText(dataBean.getWx());
                ToastUtil.show(mContext, "已复制");
            }
        });
        baseViewHolder.getView(R.id.tv_save).setOnClickListener(new View.OnClickListener() {
            @SuppressLint("CheckResult")
            @Override
            public void onClick(View view) {
                final RxPermissions rxPermissions = new RxPermissions(mContext);
                rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE).subscribe(
                        granted -> {
                            if (granted) {
                                new Thread(() -> saveImageToCamera(dataBean.getWxqrcode())).start();
                            } else {
                                ToastUtil.show(mContext,"权限已拒绝，请打开后重新使用");
                            }
                        }
            );

            }
        });


    }

    private void saveImageToCamera(String picUrl) {
        if(TextUtils.isEmpty(picUrl)){
            return;
        }
        Bitmap myBitmap = ImageUtils.getBitMBitmap(picUrl);
        if(myBitmap==null){
            return;
        }

        // 首先保存图片
        String fileName = TimeUtils.getNowMills() + "maishoumama.jpg";
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) + "/Camera", fileName);
        if(!file.exists()){
            return;
        }
        try {
            FileOutputStream fos = new FileOutputStream(file);
            myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //通知相册更新
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri uri = Uri.fromFile(file);
        intent.setData(uri);
        mContext.sendBroadcast(intent);
        mHandler.sendEmptyMessage(0);
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            ToastUtil.show(mContext, "保存成功");
        }
    };
}
