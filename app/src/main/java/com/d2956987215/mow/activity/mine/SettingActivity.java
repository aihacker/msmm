package com.d2956987215.mow.activity.mine;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.baichuan.trade.biz.login.AlibcLogin;
import com.alibaba.baichuan.trade.biz.login.AlibcLoginCallback;
import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.bumptech.glide.Glide;
import com.d2956987215.mow.R;
import com.d2956987215.mow.activity.APP;
import com.d2956987215.mow.activity.BaseActivity;
import com.d2956987215.mow.activity.SplashActivity;
import com.d2956987215.mow.activity.login.LoginNewActivity;
import com.d2956987215.mow.constant.Const;
import com.d2956987215.mow.rxjava.Request;
import com.d2956987215.mow.rxjava.Result;
import com.d2956987215.mow.rxjava.RxJavaUtil;
import com.d2956987215.mow.rxjava.response.BaseResponse;
import com.d2956987215.mow.rxjava.response.PersonInfoResponse;
import com.d2956987215.mow.rxjava.response.UpdateResponse;
import com.d2956987215.mow.util.ActivityManager;
import com.d2956987215.mow.util.ActivityUtils;
import com.d2956987215.mow.util.AnimatorEffect;
import com.d2956987215.mow.util.CacheUtil;
import com.d2956987215.mow.util.SP;
import com.d2956987215.mow.util.ToastUtil;
import com.d2956987215.mow.util.User;
import com.d2956987215.mow.widgets.CircleImageView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;
import cn.jpush.android.api.JPushInterface;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


public class SettingActivity extends BaseActivity {

    @BindView(R.id.tv_cache_size)
    TextView tvCacheSize;
    @BindView(R.id.tv_version)
    TextView tvVersion;
    @BindView(R.id.tv_nickname)
    TextView tv_nickname;
    @BindView(R.id.circle_head)
    CircleImageView circle_head;
    @BindView(R.id.tv_xuehao)
    TextView tv_xuehao;
    @BindView(R.id.tv_phone)
    TextView tv_phone;
    @BindView(R.id.tv_taobao)
    TextView tv_taobao;
    @BindView(R.id.tv_zhanghu)
    TextView tv_zhanghu;
    @BindView(R.id.tv_time)
    TextView tv_time;

    @BindView(R.id.tv_wechat)
    TextView tv_wechat;
    @BindView(R.id.tv_wx)
    TextView tvWx;
    @BindView(R.id.rl_change_wx)
    RelativeLayout rlChangeWx;

    private PersonInfoResponse.DataBean data;
    private Dialog dialogSelectImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        data = JSON.parseObject(getIntent().getStringExtra("data"), PersonInfoResponse.DataBean.class);
        try {
            tvCacheSize.setText(CacheUtil.getTotalCacheSize(this));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (data != null) {
            Glide.with(this).load(data.getAvatarUrl()).error(R.mipmap.have_no_head).into(circle_head);
        }
        if (StringUtils.isEmpty(data.getWeixin()))
            SP.putString("wechat_name", "");
        else
            SP.putString("wechat_name", data.getWeixin());

        tv_nickname.setText(User.name());
        tv_xuehao.setText(SP.getString("studentId", null));
        String phone = SP.getString("phone", null);
        String phonetext = phone.substring(0, 3) + "****" + phone.substring(7, 11);
        tv_phone.setText(phonetext);
        String taobaoname = SP.getString("taobaoname", null);
        tv_taobao.setText(taobaoname);
        String alipayname = SP.getString("alipayname", null);
        tv_zhanghu.setText(alipayname);
        String deadline = SP.getString("deadline", null);
        try {


//            String date = DisplayUtil.timeStamp2Date(Integer.parseInt(deadline) / 1000 + "", "yyyy-MM-dd HH:mm:ss");

            if (deadline == null)
                tv_time.setText("");
            else
                tv_time.setText(TimeUtils.millis2String(Integer.parseInt(deadline) * 1000L, new SimpleDateFormat("yyyy-MM-dd")));

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onResume() {
        super.onResume();
        tv_nickname.setText(User.name());
        String alipayname = SP.getString("alipayname", null);
        tv_zhanghu.setText(alipayname);
        String phone = SP.getString("phone", null);
        String phonetext = phone.substring(0, 3) + "****" + phone.substring(7, 11);
        tv_phone.setText(phonetext);
        tv_wechat.setText(SP.getString("wechat_name", ""));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected String title() {
        return getString(R.string.setting);
    }

    @Override
    public void show(Object obj) {

    }

    @OnClick({R.id.rl_change_pass, R.id.rl_clean_cache, R.id.rl_nickname, R.id.rl_wechat, R.id.rl_change_phone, R.id.circle_head,
            R.id.rl_about_us, R.id.rl_version_update, R.id.rl_feedback, R.id.bt_logout, R.id.rl_change_tongzhi, R.id.rl_change_taobao,
            R.id.rl_change_zhanghu, R.id.rl_change_erweima, R.id.rl_change_wx})
    public void onViewClicked(View view) {
        AnimatorEffect.setClickEffect(getApplicationContext(), view);
        switch (view.getId()) {
            case R.id.rl_change_tongzhi:
                startActivity(new Intent(this, MessageSetActivity.class));
                break;
            case R.id.rl_change_zhanghu:
                startActivity(new Intent(this, TiXianSetActivity.class));
                break;
            case R.id.rl_change_erweima:
                startErweima();
                break;

            case R.id.rl_change_phone:
                startActivity(new Intent(this, MyModifyPhoneActivity.class));
                break;
            case R.id.rl_change_pass:
                startActivity(new Intent(this, ChangePassActivity.class));
                break;
            case R.id.rl_clean_cache:
                new AlertDialog
                        .Builder(this)
                        .setTitle("提示")
                        .setMessage("确定清除所有内存吗？")
                        .setNegativeButton("取消", null)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                CacheUtil.clearAllCache(getApplicationContext());
                                try {
                                    tvCacheSize.setText(CacheUtil.getTotalCacheSize(getApplicationContext()));
                                    SP.putString("share_content", "");
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }).show();
                break;
            case R.id.rl_about_us:
                startActivity(new Intent(getApplicationContext(), AboutUsActivity.class));
                break;
            case R.id.rl_version_update:
                new Request<UpdateResponse>().request(RxJavaUtil.xApi().update("android"), "检查是否是最新版本", getApplicationContext(), false, new Result<UpdateResponse>() {
                    @Override
                    public void get(UpdateResponse response) {
                        if (response.getData().getApp_version() == APP.versionCode) {
                            ToastUtil.show(getApplicationContext(), "当前是最新版本");
                        } else {
                            ToastUtil.show(getApplicationContext(), "可更新");
                        }
                    }
                });
                break;
            case R.id.rl_feedback:
                startActivity(new Intent(getApplicationContext(), FeedBackActivity.class));
                break;
            case R.id.bt_logout:
                SP.putInt("userId", 0);
                SP.putString("token", "");
                SP.putString("deadline", "");
                SP.putString("remainAdNum", "");
                SP.putString("roletypes", "0");

                SP.removeString("avatarUrl");
                SP.removeString("openSid");
                SP.removeString("openId");
                SP.removeString("nick");
//                String nick = SP.getString("nick", "");
//                Log.e("tag","nick-->"+nick);
                AlibcLogin login = AlibcLogin.getInstance();
                if (login.isLogin()) {
                    login.logout(new AlibcLoginCallback() {
                        @Override
                        public void onSuccess(int i) {

                        }

                        @Override
                        public void onFailure(int i, String s) {

                        }
                    });
                }
//                EventBus.getDefault().post(new ResetMainActivityNavigation());
                ActivityManager.finishguoAllActivity();
                Intent intent = new Intent(this, SplashActivity.class);
//                Intent intent = new Intent(this, LoginNewActivity.class);
//                startActivity(intent);
                ActivityUtils.startLoginAcitivy(this);
                finish();

                JPushInterface.setAlias(SettingActivity.this, "", null);
                JPushInterface.clearAllNotifications(SettingActivity.this);
                JPushInterface.clearLocalNotifications(SettingActivity.this);
                SPUtils.getInstance().put(Const.ALIAS_TYPE, false);
//                ActivityManager.finishguoAllActivity();
                break;
//            case R.id.rl_person:
//                intent = new Intent(this, PersonalInfoActivity.class);
//                intent.putExtra("name", SP.getString("name", ""));
//                intent.putExtra("head", SP.getString("head", ""));
//                startActivity(intent);
//                break;
            case R.id.rl_nickname:
                intent = new Intent(this, SetNameActivity.class);
                intent.putExtra("name", data.getRealname());
                startActivityForResult(intent, 100);
                break;
            case R.id.rl_wechat:
                intent = new Intent(this, SetWechatNameActivity.class);
                if (data != null && data.getWeixin() != null)
                    intent.putExtra("name", data.getWeixin());
                else
                    intent.putExtra("name", "");
                startActivityForResult(intent, 101);
                break;
            case R.id.circle_head:
                dialogSelectSingleImage();

                break;
            case R.id.rl_change_taobao:
                startBindTB(1);
                break;
            case R.id.rl_change_wx:
                startBindTB(2);
                break;


        }
    }

    private void startBindTB(int type) {
        Intent intent = new Intent(this, BindTBActivity.class);
        intent.putExtra("bindTb", data.getIstaobao());
        intent.putExtra("type", type);
        startActivityForResult(intent, type);
    }

    private void startErweima() {
        Intent intent = new Intent(this, ErWeiMaActivity.class);
        if (data != null) {
            intent.putExtra(ErWeiMaActivity.WXQRCODE, data.getWxqrcode());
        }
        startActivity(intent);
    }

    private FunctionConfig functionConfig;
    private final int CAMERA_REQUEST_CODE = 1;
    private final int GALLERY_REQUEST_CODE = 0;

    private Dialog dialogSelectSingleImage() {
        functionConfig = APP.functionConfigBuilder.build();
        View popupView = LayoutInflater.from(this).inflate(R.layout.dialog_publish_select_pic, null);
        dialogSelectImage = new Dialog(this, R.style.dialogMatchParent);
        popupView.findViewById(R.id.ll_take).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(SettingActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(SettingActivity.this, new String[]{Manifest.permission.CAMERA}, 111);
                } else {
                    GalleryFinal.openCamera(CAMERA_REQUEST_CODE, functionConfig, mOnHanlderResultCallback);
                    dialogSelectImage.dismiss();
                }

            }
        });
        popupView.findViewById(R.id.ll_from_album).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(SettingActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(SettingActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 222);
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

    private GalleryFinal.OnHanlderResultCallback mOnHanlderResultCallback = new GalleryFinal.OnHanlderResultCallback() {
        @Override
        public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
            if (resultList != null) {
                String picture = resultList.get(0).getPhotoPath();
                //上传二维码
                fabuimage(picture);
                Glide.with(getApplicationContext()).load(picture).into(circle_head);
            }

        }

        @Override
        public void onHanlderFailure(int requestCode, String errorMsg) {

        }
    };

    private void fabuimage(final String logo) {
        File file = new File(logo);
        RequestBody userbody = RequestBody.create(MediaType.parse("text/plain"), User.uid() + "");
        RequestBody imageBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part imageBodyPart = MultipartBody.Part.createFormData("avatar", file.getName(), imageBody);
        new Request<BaseResponse>().request(RxJavaUtil.xApi().touxiangloadPic(imageBodyPart, userbody, "Bearer " + User.token()), "上传中", this, true, new Result<BaseResponse>() {
            @Override
            public void get(BaseResponse response) {
                ToastUtil.show(SettingActivity.this, response.message);

            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 1) {
            finish();
        }
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
                    Toast.makeText(SettingActivity.this, "为了您能正常使用，请开启权限", Toast.LENGTH_SHORT).show();
                }
                break;
            case 222:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    GalleryFinal.openGallerySingle(GALLERY_REQUEST_CODE, functionConfig, mOnHanlderResultCallback);
                    if (dialogSelectImage != null && dialogSelectImage.isShowing()) {
                        dialogSelectImage.dismiss();
                    }
                } else {
                    Toast.makeText(SettingActivity.this, "为了您能正常使用，请开启权限", Toast.LENGTH_SHORT).show();
                }
                break;


            default:
                break;
        }
    }
}
