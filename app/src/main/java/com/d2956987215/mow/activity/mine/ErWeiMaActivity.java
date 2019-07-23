package com.d2956987215.mow.activity.mine;

import android.Manifest;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bolex.pressscan.RGBLuminanceSource;
import com.bumptech.glide.Glide;
import com.d2956987215.mow.R;
import com.d2956987215.mow.activity.APP;
import com.d2956987215.mow.activity.BaseActivity;
import com.d2956987215.mow.presenter.MinePresenter;
import com.d2956987215.mow.rxjava.Request;
import com.d2956987215.mow.rxjava.RxJavaUtil;
import com.d2956987215.mow.rxjava.response.UploadFileResponse;
import com.d2956987215.mow.util.AnimatorEffect;
import com.d2956987215.mow.util.QRCodeUtil;
import com.d2956987215.mow.util.ToastUtil;
import com.d2956987215.mow.util.User;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class ErWeiMaActivity extends BaseActivity {
    @BindView(R.id.iv_erweima)
    ImageView iv_erweima;
    private MinePresenter presenter;
    private String picture;
    public static final String WXQRCODE = "wxqrcode";
    private Dialog dialogSelectImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new MinePresenter(this);
        initView();
    }

    private void initView() {
        String imgUrl = getIntent().getStringExtra(WXQRCODE);
        if (!TextUtils.isEmpty(imgUrl)) {
            Glide.with(getApplicationContext()).load(imgUrl).into(iv_erweima);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_shangchuan;
    }

    @Override
    public void show(Object obj) {

    }

    @Override
    protected String title() {
        return "上传微信二维码";
    }

    @OnClick({R.id.bt_next, R.id.iv_erweima})
    public void onViewClicked(View view) {
        AnimatorEffect.setClickEffect(getApplicationContext(), view);
        switch (view.getId()) {

            case R.id.bt_next:
                if (lujing != null) {
                    fabuimage(lujing);
                } else {
                    ToastUtil.show(this, "请选择二维码");
                }
                break;
            case R.id.iv_erweima:
                dialogSelectSingleImage();

                break;
        }
    }

    private FunctionConfig functionConfig;
    private final int CAMERA_REQUEST_CODE = 1;
    private final int GALLERY_REQUEST_CODE = 0;

    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    private Dialog dialogSelectSingleImage() {
        functionConfig = APP.functionConfigBuilder.build();
        View popupView = LayoutInflater.from(this).inflate(R.layout.dialog_publish_select_pic, null);
        dialogSelectImage = new Dialog(this, R.style.dialogMatchParent);
        popupView.findViewById(R.id.ll_take).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(ErWeiMaActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(ErWeiMaActivity.this, new String[]{Manifest.permission.CAMERA}, 111);
                } else {
                    GalleryFinal.openCamera(CAMERA_REQUEST_CODE, functionConfig, mOnHanlderResultCallback);
                    dialogSelectImage.dismiss();
                }

            }
        });
        popupView.findViewById(R.id.ll_from_album).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(ErWeiMaActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(ErWeiMaActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 222);
                } else {
                    GalleryFinal.openGallerySingle(GALLERY_REQUEST_CODE, functionConfig, mOnHanlderResultCallback);
                    dialogSelectImage.dismiss();
                }
            }
        });
        dialogSelectImage.setContentView(popupView);
        dialogSelectImage.getWindow().setGravity(Gravity.CENTER);
        dialogSelectImage.getWindow().setWindowAnimations(R.style.dialogWindowAnim);
        dialogSelectImage.show();
        return dialogSelectImage;
    }

    public static String scanBitmap(Bitmap bitmap) {
        HashMap hints = new HashMap();
        hints.put(DecodeHintType.CHARACTER_SET, "utf-8");
        RGBLuminanceSource rgbLuminanceSource = new RGBLuminanceSource(bitmap);
        BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(rgbLuminanceSource));
        QRCodeReader reader = new QRCodeReader();
        Result result = null;

        try {
            result = reader.decode(binaryBitmap, hints);
        } catch (Exception var7) {
            ;
        }

        return result != null ? result.getText() : "";
    }

    private GalleryFinal.OnHanlderResultCallback mOnHanlderResultCallback = new GalleryFinal.OnHanlderResultCallback() {
        @Override
        public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
            if (resultList != null) {
                picture = resultList.get(0).getPhotoPath();
                FileInputStream fis = null;
                try {
                    fis = new FileInputStream(picture);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                Bitmap bitmap = BitmapFactory.decodeStream(fis);
                //上传二维码
                String ret = scanBitmap(bitmap);
                Bitmap bmp = QRCodeUtil.createQRCodeBitmap(ret.toString(), 400);
                savePicture(bmp, "邀请码.png", "1");// 保存图片
            }

        }

        @Override
        public void onHanlderFailure(int requestCode, String errorMsg) {

        }
    };
    private String lujing;

    public void savePicture(Bitmap bm, String fileName, String laizi) {

        if (bm == null) {
            Toast.makeText(this, "" +
                    "请上传微信二维码!", Toast.LENGTH_SHORT).show();
            return;
        }

        File foder = new File("/sdcard/maishou/otherimage");
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
        lujing = myCaptureFile.getAbsolutePath();
        handler.sendEmptyMessage(10);


//        Bitmap bmpDefaultPic = null;
//        if (bmpDefaultPic == null)
//            bmpDefaultPic = BitmapFactory.decodeFile("/sdcard/shareimg.png", null);


    }


    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {

                case 10:
                    Glide.with(ErWeiMaActivity.this).load("file://" + lujing).error(R.mipmap.da_tu).into(iv_erweima);

                    break;

                default:
                    break;
            }
        }

    };

    private void fabuimage(final String logo) {
        File file = new File(logo);
        RequestBody userbody = RequestBody.create(MediaType.parse("text/plain"), User.uid() + "");
        RequestBody imageBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part imageBodyPart = MultipartBody.Part.createFormData("wxqrcode", file.getName(), imageBody);
        new Request<UploadFileResponse>().request(RxJavaUtil.xApi().wxQRloadPic(imageBodyPart, userbody, "Bearer " + User.token()), "上传中", this, true, new com.d2956987215.mow.rxjava.Result<UploadFileResponse>() {
            @Override
            public void get(UploadFileResponse response) {
                ToastUtil.show(ErWeiMaActivity.this, response.message);
                finish();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 111:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    GalleryFinal.openCamera(CAMERA_REQUEST_CODE, functionConfig, mOnHanlderResultCallback);
                    if (dialogSelectImage != null && dialogSelectImage.isShowing()) {
                        dialogSelectImage.dismiss();
                    }
                } else {
                    Toast.makeText(ErWeiMaActivity.this, "为了您能正常使用，请开启权限", Toast.LENGTH_SHORT).show();
                }
                break;
            case 222:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    GalleryFinal.openGallerySingle(GALLERY_REQUEST_CODE, functionConfig, mOnHanlderResultCallback);
                    if (dialogSelectImage != null && dialogSelectImage.isShowing()) {
                        dialogSelectImage.dismiss();
                    }
                } else {
                    Toast.makeText(ErWeiMaActivity.this, "为了您能正常使用，请开启权限", Toast.LENGTH_SHORT).show();
                }
                break;


            default:
                break;
        }
    }

}