package com.d2956987215.mow.activity.mine;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.d2956987215.mow.R;
import com.d2956987215.mow.activity.APP;
import com.d2956987215.mow.activity.BaseActivity;
import com.d2956987215.mow.listener.ChangeName;
import com.d2956987215.mow.rxjava.Request;
import com.d2956987215.mow.rxjava.Result;
import com.d2956987215.mow.rxjava.RxJavaUtil;
import com.d2956987215.mow.rxjava.response.BaseResponse;
import com.d2956987215.mow.util.AnimatorEffect;
import com.d2956987215.mow.util.DialogUtil;
import com.d2956987215.mow.util.SP;
import com.d2956987215.mow.util.ToastUtil;
import com.d2956987215.mow.util.User;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class PersonalInfoActivity extends BaseActivity {

    @BindView(R.id.circle_head)
    CircleImageView circleHead;
    @BindView(R.id.tv_nickname)
    TextView tvNickname;
    @BindView(R.id.tv_phone)
    TextView tv_phone;

    private final int CAMERA_REQUEST_CODE = 1;
    private final int GALLERY_REQUEST_CODE = 0;
    private String picture;
    private String name, head;
    private GalleryFinal.OnHanlderResultCallback mOnHanlderResultCallback = new GalleryFinal.OnHanlderResultCallback() {
        @Override
        public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
            if (resultList.size() > 0) {
                picture = resultList.get(0).getPhotoPath();
            }
            //这种写法不对，设置placeholder就会报错，原因未知
            //Glide.with(getApplicationContext()).load(picture).placeholder(R.mipmap.have_no_head).into(circleHead);
            Glide.with(getApplicationContext()).load(picture).into(circleHead);
        }

        @Override
        public void onHanlderFailure(int requestCode, String errorMsg) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent() != null) {
            name = getIntent().getStringExtra("name");
            head = getIntent().getStringExtra("head");
            tvNickname.setText(name);
            Glide.with(getApplicationContext()).load(RxJavaUtil.HOST + head).error(R.mipmap.have_no_head).into(circleHead);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_personal_info;
    }

    @Override
    public void show(Object obj) {

    }

    private FunctionConfig functionConfig;

    private Dialog dialogSelectSingleImage() {
        functionConfig = APP.functionConfigBuilder.build();
        View popupView = LayoutInflater.from(this).inflate(R.layout.dialog_publish_select_pic, null);
        final Dialog dialogSelectImage = new Dialog(this, R.style.dialogMatchParent);
        popupView.findViewById(R.id.ll_take).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GalleryFinal.openCamera(CAMERA_REQUEST_CODE, functionConfig, mOnHanlderResultCallback);
                dialogSelectImage.dismiss();
            }
        });
        popupView.findViewById(R.id.ll_from_album).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GalleryFinal.openGallerySingle(GALLERY_REQUEST_CODE, functionConfig, mOnHanlderResultCallback);
                dialogSelectImage.dismiss();
            }
        });
        dialogSelectImage.setContentView(popupView);
        dialogSelectImage.getWindow().setGravity(Gravity.CENTER);
        dialogSelectImage.getWindow().setWindowAnimations(R.style.dialogWindowAnim);
        dialogSelectImage.show();
        return dialogSelectImage;
    }

    @Override
    protected String title() {
        return getString(R.string.personal_info);
    }

    @OnClick({R.id.rl_head, R.id.rl_nickname, R.id.bt_save})
    public void onViewClicked(View view) {
        AnimatorEffect.setClickEffect(getApplicationContext(), view);
        switch (view.getId()) {
            case R.id.rl_head:
                dialogSelectSingleImage();
                break;
            case R.id.rl_nickname:
                DialogUtil.dialogChangeName(this, new ChangeName() {
                    @Override
                    public void name(String s) {
                        name = s;
                        tvNickname.setText(name);
                    }
                });
                break;
            case R.id.bt_save:
                if (picture != null) {
                    File file = new File(picture);
                    RequestBody requestFile = RequestBody.create(MediaType.parse("image"), file);
                    final MultipartBody.Part part = MultipartBody.Part.createFormData("pic", file.getName(), requestFile);
//                    new Request<PictureResponse>().request(RxJavaUtil.xApi().uploadPic(part), "上传个人头像", getApplicationContext(), false, new Result<PictureResponse>() {
//                        @Override
//                        public void get(PictureResponse response) {
//                            SP.putString("head", response.getPic());
//                            new Request<>().request(RxJavaUtil.xApi().changePerInfo(User.uid(), name, response.getPic()), "修改个人信息", getApplicationContext(), false, new Result<BaseResponse>() {
//                                @Override
//                                public void get(BaseResponse response) {
//                                    ToastUtil.show(getApplicationContext(), response.msg);
//                                    Glide.with(getApplicationContext()).load(picture).placeholder(R.mipmap.have_no_head).into(circleHead);
//                                    ChangeHead changeHead = new ChangeHead();
//                                    changeHead.head = picture;
//                                    EventBus.getDefault().post(changeHead);
//                                    finish();
//                                }
//                            });
                     //   }
                  //  });
                    break;
                } else {
                    new Request<>().request(RxJavaUtil.xApi().changePerInfo(User.uid(), name, head), "修改个人信息", getApplicationContext(), false, new Result<BaseResponse>() {
                        @Override
                        public void get(BaseResponse response) {
                            ToastUtil.show(getApplicationContext(), response.message);
                            SP.putString("name", name);
                            finish();
                        }
                    });
                }
        }
    }
}
