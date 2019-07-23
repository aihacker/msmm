package com.d2956987215.mow.activity.mine;

import android.Manifest;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.d2956987215.mow.R;
import com.d2956987215.mow.activity.BaseActivity;
import com.d2956987215.mow.activity.home.ShareZhuanActivity;
import com.d2956987215.mow.bean.SharePicBean;
import com.d2956987215.mow.rxjava.Request;
import com.d2956987215.mow.rxjava.Result;
import com.d2956987215.mow.rxjava.RxJavaUtil;
import com.d2956987215.mow.rxjava.response.MonitorResponse;
import com.d2956987215.mow.rxjava.response.PersonInfoResponse;
import com.d2956987215.mow.util.ConstantUtil;
import com.d2956987215.mow.util.ToastUtil;
import com.d2956987215.mow.util.User;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import cn.finalteam.galleryfinal.GalleryFinal;


public class BanZhangActivity extends BaseActivity {

    @BindView(R.id.tv_content)
    TextView tv_content;
    @BindView(R.id.iv_erweima)
    ImageView iv_erweima;
    @BindView(R.id.tv_update)
    TextView tv_update;
    @BindView(R.id.tv_title)
    TextView tv_title;


    //剪切板Data对象
    private ClipData mClipData;
    //剪切板管理工具类
    private ClipboardManager mClipboardManager;
    private String erweima;
    private String[] permisssions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (!TextUtils.isEmpty(getIntent().getStringExtra("contact"))) {
//            tv_update.setText(getIntent().getStringExtra("contact")+"，免费升级");
//        }
        String roleType = getIntent().getStringExtra("roleType");
        huoqu(roleType);
    }

    private void huoqu(String roleType) {
        //S
        Map<String, String> map = new HashMap<>();
        map.put("user_id", User.uid() + "");
        map.put("roletype", roleType);
        new Request<MonitorResponse>().request(RxJavaUtil.xApi().tianjiabanzhang(map, "Bearer " + User.token()), "添加班长", this, false, new Result<MonitorResponse>() {
            @Override
            public void get(MonitorResponse response) {
                tv_content.setText(response.getData().getTj_wx());
                Glide.with(BanZhangActivity.this).load(response.getData().getTj_qrcode()).into(iv_erweima);
                erweima = response.getData().getTj_qrcode();
                tv_update.setText(response.getData().getText());
                tv_title.setText(response.getData().getTitle());
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_banzhang;
    }

    @Override
    public void show(Object obj) {

    }

    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {

                case 10:
                    Toast.makeText(BanZhangActivity.this, "保存成功!", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }

    };

    @OnClick({R.id.tv_tianjia, R.id.tv_baocun})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_tianjia:
                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                // 将文本内容放到系统剪贴板里。
                cm.setText(tv_content.getText());
                Toast.makeText(this, "复制成功，可以发给朋友们了。", Toast.LENGTH_LONG).show();

                break;
            case R.id.tv_baocun:
                if (erweima != null) {

                } else {
                    ToastUtil.show(this, "二维码获取失败");
                    return;
                }

                if (permission(permisssions)) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Bitmap bmp1 = returnBitmap(erweima);
                            if (bmp1 != null)
                                saveImageToCamera(bmp1);// 保存图片
                            else
                                ToastUtils.showShort("二维码图片异常");
                        }
                    }).start();
                } else {
                    ActivityCompat.requestPermissions(BanZhangActivity.this, permisssions, 111);
                }


                break;
        }

    }

    private Bitmap returnBitmap(String url) {
        URL fileUrl = null;
        Bitmap bitmap = null;

        try {
            fileUrl = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        try {
            HttpURLConnection conn = (HttpURLConnection) fileUrl
                    .openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;

    }

    private void saveImageToCamera(Bitmap myBitmap) {

        if (myBitmap == null) {
            Toast.makeText(this, "savePicture null !", Toast.LENGTH_SHORT).show();
            return;
        }

        // 首先保存图片
        String fileName = TimeUtils.getNowMills() + "maishoumama.jpg";
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) + "/Camera", fileName);
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
        sendBroadcast(intent);
        handler.sendEmptyMessage(10);
    }

    @Override
    protected String title() {
//        return TextUtils.isEmpty(getIntent().getStringExtra("contact")) ? "添加班长" : getIntent().getStringExtra("contact");
        return "";
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 111:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Bitmap bmp1 = returnBitmap(erweima);
                            if (bmp1 != null)
                                saveImageToCamera(bmp1);// 保存图片
                            else
                                ToastUtils.showShort("二维码图片异常");
                        }
                    }).start();
                } else {
                    Toast.makeText(BanZhangActivity.this, "为了您能正常使用，请开启权限", Toast.LENGTH_SHORT).show();
                }
                break;


            default:
                break;
        }
    }

    private boolean permission(String[] permission) {
        int permissipnNum = 0;
        if (permission.length > 0) {
            for (int i = 0; i < permission.length; i++) {
                if (ContextCompat.checkSelfPermission(BanZhangActivity.this, permission[i]) == PackageManager.PERMISSION_GRANTED) {
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
}
