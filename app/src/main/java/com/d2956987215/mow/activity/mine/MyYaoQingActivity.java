package com.d2956987215.mow.activity.mine;

import android.Manifest;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.d2956987215.mow.R;
import com.d2956987215.mow.activity.BaseActivity;
import com.d2956987215.mow.dialog.ButtomDialogView;
import com.d2956987215.mow.dialog.ErWeiMaDialog;
import com.d2956987215.mow.rxjava.Request;
import com.d2956987215.mow.rxjava.Result;
import com.d2956987215.mow.rxjava.RxJavaUtil;
import com.d2956987215.mow.rxjava.response.HaiBaoResponse;
import com.d2956987215.mow.util.ConstantUtil;
import com.d2956987215.mow.util.QRCodeUtil;
import com.d2956987215.mow.util.SP;
import com.d2956987215.mow.util.ToastUtil;
import com.d2956987215.mow.util.User;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMWeb;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class MyYaoQingActivity extends BaseActivity {

    @BindView(R.id.tv_haibao)
    TextView tv_haibao;
    @BindView(R.id.tv_yaoqingma)
    TextView tv_yaoqingma;
    @BindView(R.id.iv_erweima)
    ImageView iv_erweima;

    private String lujing;
    private String shareUrl;
    private HaiBaoResponse.DataBean responseData;
    private String[] permisssions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View decorView = getWindow().getDecorView();

            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN

                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;

            decorView.setSystemUiVisibility(option);

            getWindow().setStatusBarColor(Color.TRANSPARENT);

        }
        super.onCreate(savedInstanceState);
        huoqu();
    }

    private void huoqu() {
        Map<String, String> map = new HashMap<>();
        map.put("user_id", User.uid() + "");
        new Request<HaiBaoResponse>().request(RxJavaUtil.xApi().haibao(map, "Bearer " + User.token()), "分享邀请", this, false, new Result<HaiBaoResponse>() {
            @Override
            public void get(final HaiBaoResponse response) {
                responseData = response.getData();
                shareUrl = responseData.getUrl();
                tv_yaoqingma.setText(responseData.getStudentId() + "");
                if (permission(permisssions)) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Bitmap bmp = QRCodeUtil.createQRCodeBitmap(responseData.getEwm(), 400);
                            savePicture(bmp, "邀请码.png");// 保存图片
                        }
                    }).start();
                } else {
                    ActivityCompat.requestPermissions(MyYaoQingActivity.this, permisssions, 111);
                }


            }
        });
    }

    @OnClick({R.id.tv_haibao, R.id.tv_fuzhi, R.id.tv_tongxu, R.id.tv_lianjie, R.id.iv_erweima, R.id.tv_reward_rules})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_haibao:
                if (responseData != null && responseData.getStudentId() != null) {
                    Intent intent = new Intent(this, MyYaoQingHaiBaoActivity.class);
                    intent.putExtra("studentId", responseData.getStudentId() + "");
                    startActivity(intent);
                } else
                    ToastUtils.showShort("数据异常");
                break;
            case R.id.tv_fuzhi:
                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                // 将文本内容放到系统剪贴板里。
                cm.setText(tv_yaoqingma.getText());
                Toast.makeText(this, "复制成功，可以发给朋友们了。", Toast.LENGTH_LONG).show();
                break;
            case R.id.tv_tongxu:
                if (responseData != null && responseData.getStudentId() != null) {
                    Intent intent = new Intent(this, MyTongXunListActivity.class);
                    intent.putExtra("studentId", responseData.getStudentId());
                    startActivity(intent);
                } else
                    ToastUtils.showShort("数据异常");
                break;
            case R.id.tv_lianjie:
                if (responseData != null) {

                } else {
                    huoqu();
                    return;
                }
                ButtomDialogView bottomDialog = new ButtomDialogView(this, new ButtomDialogView.CallBack() {
                    @Override
                    public void weixinhaoyou() {
                        if (responseData != null) {
                            IWXAPI iwxapi = WXAPIFactory.createWXAPI(MyYaoQingActivity.this, ConstantUtil.WXAPP_ID);
                            if (iwxapi.isWXAppInstalled()) {
                                UMWeb web = new UMWeb(shareUrl);//连接地址
                                web.setTitle(responseData.getTitle());//标题
                                web.setDescription(responseData.getDesc());//描述
                                new ShareAction(MyYaoQingActivity.this)
                                        .setPlatform(SHARE_MEDIA.WEIXIN)
                                        .withMedia(web)
                                        .setCallback(new MyListener())
                                        .share();

                            } else {
                                ToastUtil.show(MyYaoQingActivity.this, "未安装微信");
                            }

                        }
                    }

                    @Override
                    public void pengyouquan() {
                        if (responseData != null) {
                            IWXAPI iwxapi = WXAPIFactory.createWXAPI(MyYaoQingActivity.this, ConstantUtil.WXAPP_ID);
                            if (iwxapi.isWXAppInstalled()) {
                                UMWeb web = new UMWeb(shareUrl);//连接地址
                                web.setTitle(responseData.getTitle());//标题
                                web.setDescription(responseData.getDesc());//描述
                                new ShareAction(MyYaoQingActivity.this)
                                        .setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
                                        .withMedia(web)
                                        .setCallback(new MyListener())
                                        .share();

                            } else {
                                ToastUtil.show(MyYaoQingActivity.this, "未安装微信");
                            }

                        }
                    }

                    @Override
                    public void xinlangweibo() {
                        UMWeb web = new UMWeb(shareUrl);//连接地址
                        web.setTitle(responseData.getTitle());//标题
                        web.setDescription(responseData.getDesc());//描述
                        new ShareAction(MyYaoQingActivity.this)
                                .setPlatform(SHARE_MEDIA.SINA)
                                .withMedia(web)
                                .setCallback(new MyListener())
                                .share();

                    }

                    @Override
                    public void qqhaoyou() {
                        if (responseData != null) {
                            UMWeb web = new UMWeb(shareUrl);//连接地址
                            web.setTitle(responseData.getTitle());//标题
                            web.setDescription(responseData.getDesc());//描述
                            new ShareAction(MyYaoQingActivity.this)
                                    .setPlatform(SHARE_MEDIA.QQ)
                                    .withMedia(web)
                                    .setCallback(new MyListener())
                                    .share();
                        }
//                sp.setText(getIntent().getStringExtra("studentId"));
//                        sp.setImageUrl(getIntent().getStringExtra("erweima"));

                    }

                    @Override
                    public void qqkongjian() {

                        if (responseData != null) {
                            UMWeb web = new UMWeb(shareUrl);//连接地址
                            web.setTitle(responseData.getTitle());//标题
                            web.setDescription(responseData.getDesc());//描述
                            new ShareAction(MyYaoQingActivity.this)
                                    .setPlatform(SHARE_MEDIA.QZONE)
                                    .withMedia(web)
                                    .setCallback(new MyListener())
                                    .share();
                        }
                    }


                    @Override
                    public void fuzhi() {
                        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                        // 将文本内容放到系统剪贴板里。
                        cm.setText(shareUrl);
                        Toast.makeText(MyYaoQingActivity.this, "复制成功，可以发给朋友们了。", Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void NO() {

                    }
                });
                bottomDialog.show();
                break;
            case R.id.iv_erweima:
                if (lujing != null) {
                    ErWeiMaDialog erWeiMaDialog = new ErWeiMaDialog(this, lujing, new ErWeiMaDialog.CallBack() {
                        @Override
                        public void NO() {
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    Bitmap bmp = QRCodeUtil.createQRCodeBitmap(responseData.getEwm(), 400);
                                    saveImageToCamera(bmp);// 保存图片
                                }
                            }).start();

                        }
                    });
                    erWeiMaDialog.show();
                }


                break;
            case R.id.tv_reward_rules:
                startActivity(new Intent(this, RewardRulesActivity.class)
                        .putExtra(RewardRulesActivity.URL, responseData.getGuize())
                );
                break;
            default:
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

    public void savePicture(Bitmap bm, String fileName) {

        if (bm == null) {
            ToastUtils.showShort("savePicture null !");
//            Toast.makeText(this, "savePicture null !", Toast.LENGTH_SHORT).show();
            return;
        }

        File foder = new File("/sdcard/maishou/pic");
        if (!foder.exists()) {
            foder.mkdirs();
        }
        File myCaptureFile = new File(foder, fileName);
        try {
            if (!myCaptureFile.exists()) {
                myCaptureFile.createNewFile();
            } else {
                myCaptureFile.delete();
            }
            FileOutputStream fos = new FileOutputStream(myCaptureFile);
            bm.compress(Bitmap.CompressFormat.JPEG, 50, fos);
            fos.flush();
            fos.close();

//
//            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
//            bm.compress(Bitmap.CompressFormat.JPEG, 70, bos);
//            bos.flush();
//            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        lujing = "file://" + myCaptureFile.getAbsolutePath();
        handler.sendEmptyMessage(11);

//        Bitmap bmpDefaultPic = null;
//        if (bmpDefaultPic == null)
//            bmpDefaultPic = BitmapFactory.decodeFile("/sdcard/shareimg.png", null);


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
    protected int getLayoutId() {
        return R.layout.activity_myyaoqing;
    }

    @Override
    public void show(Object obj) {

    }

    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    // Toast.makeText(getApplicationContext(), "分享成功", Toast.LENGTH_LONG).show();
                    break;
                case 2:
                    //Toast.makeText(getApplicationContext(), "取消分享", Toast.LENGTH_LONG).show();
                    break;
                case 3:
                    // Toast.makeText(getApplicationContext(), "分享失败" + "参数错误", Toast.LENGTH_LONG).show();
                    break;
                case 10:
                    Toast.makeText(MyYaoQingActivity.this, "保存成功!", Toast.LENGTH_SHORT).show();
                    break;
                case 11:
                    Glide.with(MyYaoQingActivity.this).load(lujing).error(R.mipmap.da_tu).into(iv_erweima);
                    break;

                default:
                    break;
            }
        }

    };

    private class MyListener implements UMShareListener {

        @Override
        public void onStart(SHARE_MEDIA share_media) {

        }

        @Override
        public void onResult(SHARE_MEDIA share_media) {
            handler.sendEmptyMessage(1);
        }

        @Override
        public void onError(SHARE_MEDIA share_media, Throwable throwable) {
            Message msg = new Message();
            msg.what = 3;
            msg.obj = throwable.getMessage();
            handler.sendMessage(msg);
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media) {
            handler.sendEmptyMessage(2);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 111:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    if (responseData != null) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Bitmap bmp = QRCodeUtil.createQRCodeBitmap(responseData.getEwm(), 400);
                                savePicture(bmp, "邀请码.png");// 保存图片
                            }
                        }).start();
                    }
                } else {
                    Toast.makeText(MyYaoQingActivity.this, "为了您能正常使用，请开启权限", Toast.LENGTH_SHORT).show();
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
                if (ContextCompat.checkSelfPermission(MyYaoQingActivity.this, permission[i]) == PackageManager.PERMISSION_GRANTED) {
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
