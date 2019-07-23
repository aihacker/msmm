package com.d2956987215.mow.widgets.yulanpic;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.d2956987215.mow.R;
import com.d2956987215.mow.activity.mine.MyYaoQingActivity;
import com.d2956987215.mow.util.ConstantUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.finalteam.galleryfinal.widget.zoonview.PhotoViewAttacher;


public class ImageDrawableDetailFragment extends Activity {

    @BindView(R.id.view)
    View mView;
    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.tv_imagepager_indicator)
    TextView mTvImagepagerIndicator;
    @BindView(R.id.tv_save)
    TextView mTvSave;
    @BindView(R.id.image)
    ImageView mImage;
    private PhotoViewAttacher mAttacher;
    private String[] permisssions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.activity_imagedrawable_detail);
        ButterKnife.bind(this);

        if (ConstantUtil.drawable != null) {
            mImage.setImageDrawable(ConstantUtil.drawable);
        }
        mTvImagepagerIndicator.setText("1/1");
        mAttacher = new PhotoViewAttacher(mImage);
        mAttacher.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {

            @Override
            public void onPhotoTap(View arg0, float arg1, float arg2) {
                finish();
            }
        });

        mTvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (permission(permisssions)) {
                    final Bitmap bmp = ((BitmapDrawable) ConstantUtil.drawable).getBitmap();
                    if (bmp != null) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                saveBitmap(bmp);
                            }
                        }).start();
                    }
                } else {
                    ActivityCompat.requestPermissions(ImageDrawableDetailFragment.this, permisssions, 111);
                }


            }
        });

        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void saveBitmap(Bitmap bitmap) {

        if (bitmap == null) {
            ToastUtils.showShort("保存失败");
//            Toast.makeText(this, "savePicture null !", Toast.LENGTH_SHORT).show();
            return;
        }

        File foder = new File("/sdcard/maishoumama/pic");
        if (!foder.exists()) {
            foder.mkdirs();
        }
        File myCaptureFile = new File(foder, 1 + TimeUtils.getNowMills() + ".png");
        try {
            if (!myCaptureFile.exists()) {
                myCaptureFile.createNewFile();
            } else {
                myCaptureFile.delete();
            }
            FileOutputStream fos = new FileOutputStream(myCaptureFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, fos);
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
        //保存图片后发送广播通知更新数据库
        Uri uri = Uri.fromFile(myCaptureFile);
        sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));

        ToastUtils.showShort("保存成功");

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 111:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    final Bitmap bmp = ((BitmapDrawable) ConstantUtil.drawable).getBitmap();
                    if (bmp != null) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                saveBitmap(bmp);
                            }
                        }).start();
                    }
                } else {
                    Toast.makeText(ImageDrawableDetailFragment.this, "为了您能正常使用，请开启权限", Toast.LENGTH_SHORT).show();
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
                if (ContextCompat.checkSelfPermission(ImageDrawableDetailFragment.this, permission[i]) == PackageManager.PERMISSION_GRANTED) {
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
