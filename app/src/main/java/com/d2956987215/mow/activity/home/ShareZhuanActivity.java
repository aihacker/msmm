package com.d2956987215.mow.activity.home;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.d2956987215.mow.R;
import com.d2956987215.mow.activity.BaseActivity;
import com.d2956987215.mow.activity.luntan.LunTandetailFragment;
import com.d2956987215.mow.activity.mine.SettingActivity;
import com.d2956987215.mow.adapter.SharePicAdapter;
import com.d2956987215.mow.bean.SharePicBean;
import com.d2956987215.mow.dialog.BaseDialog;
import com.d2956987215.mow.rxjava.Request;
import com.d2956987215.mow.rxjava.Result;
import com.d2956987215.mow.rxjava.RxJavaUtil;
import com.d2956987215.mow.rxjava.response.CopyContentResponse;
import com.d2956987215.mow.util.ConstantUtil;
import com.d2956987215.mow.util.ImageUtils;
import com.d2956987215.mow.util.QRCodeUtil;
import com.d2956987215.mow.util.ToastUtil;
import com.d2956987215.mow.widgets.yulanpic.ImagePagerActivity;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.StatementEvent;

import butterknife.BindView;
import butterknife.OnClick;
import cn.finalteam.galleryfinal.GalleryFinal;

public class ShareZhuanActivity extends BaseActivity {


    //标题
    public static final String TITLE = "title";
    //原价
    public static final String ZK_FINAL_PRICE = "zk_final_price";
    //优惠券价格
    public static final String COUPON_PRICE = "coupon_price";
    //最终价格
    public static final String END_PRICE = "end_price";
    //下单链接
    public static final String RHYURL = "rhyurl";
    //淘口令
    public static final String TKL = "tkl";

    public static final String ROOTPATH = Environment.getExternalStorageDirectory().getPath();
    public static final String DOWNLOAD_DIR = ROOTPATH + "/download/";
    @BindView(R.id.rl_pic)
    RecyclerView rl_pic;
    @BindView(R.id.tv_xuanze)
    TextView tv_xuanze;
    @BindView(R.id.et_content)
    EditText et_content;
    @BindView(R.id.tv_share_money)
    TextView mTvShareMoney;
    @BindView(R.id.checkbox)
    CheckBox mCheckbox;
    @BindView(R.id.et_comment_content)
    EditText mEtCommentContent;


    private ArrayList<String> mlist = new ArrayList<>();
    private SharePicAdapter sharePicAdapter;
    List<String> xuanze;

    //生成
    private String mGenerateName;
    private ArrayList<SharePicBean> mDatas;
    private Intent mIntent;
    private String mTemp;
    private String mTitle;
    private String mOldPrice;
    private String mCouponPrice;
    private String mNowPrice;
    private String mRhyUrl;
    private String mTkl;
    private String Comment;
    private String mExtension;
    private String mStudentId;
    private String myongjin;
    private String mDownloadLink;
    private ArrayList<Uri> mLocalLists;//保存到本地的图片路径
    private int downPic = 0;
    private int shareType = 0;//0：不分享，只保存；1微信好友；2微信朋友圈；3QQ好友；4QQ空间；5新浪
    private int selectListSize = 0;//选中要分享的图片
    private String ShowMoney, ShowMoney_Template;
    private String[] permisssions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
    private WechatCircleShareDialog wechatCircleShareDialog;
    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (SPUtils.getInstance().getBoolean("checkbox")) {
            mCheckbox.setChecked(true);
        } else {
            mCheckbox.setChecked(false);
        }
        initIntent();

        mlist.addAll(getIntent().getStringArrayListExtra("list"));
        mTvShareMoney.setText("你的奖励预计为：￥" + getIntent().getStringExtra("share_yongjin"));


        mDatas = new ArrayList<>();
        mLocalLists = new ArrayList<>();
        for (int i = 0; i < mlist.size(); i++) {
            SharePicBean data = new SharePicBean();
            if (i == 0)
                data.setCheck(true);
            else
                data.setCheck(false);
            data.setUrl(mlist.get(i));
            mDatas.add(data);
        }
        sharePicAdapter = new SharePicAdapter(R.layout.adapter_sharepic, mDatas);
        sharePicAdapter.setSelectedPosition(0);
        tv_xuanze.setText("1");
        int spanCount = 1; // 只显示一行
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(spanCount, StaggeredGridLayoutManager.HORIZONTAL);
        rl_pic.setLayoutManager(layoutManager);
        rl_pic.setHasFixedSize(true);
        rl_pic.setNestedScrollingEnabled(false);
        rl_pic.setAdapter(sharePicAdapter);

        sharePicAdapter.setOnOtherClickListener(new SharePicAdapter.OnOtherClickListener() {
            @Override
            public void setOtherClick(int position) {

                if (sharePicAdapter.getItem(position).isCheck()) {
                    //取消选中
                    int count = 0;
                    for (int i = 0; i < mDatas.size(); i++) {
                        if (mDatas.get(i).isCheck()) {
                            count++;
                        }
                    }
                    if (count <= 1) {
                        tv_xuanze.setText("1");
                        ToastUtil.show(ShareZhuanActivity.this, "至少要选中一张");
                    } else {
                        sharePicAdapter.getItem(position).setCheck(!sharePicAdapter.getItem(position).isCheck());
                        sharePicAdapter.notifyDataSetChanged();

                        count = 0;
                        for (int i = 0; i < mDatas.size(); i++) {
                            if (mDatas.get(i).isCheck()) {
                                count++;
                            }
                        }
                        tv_xuanze.setText(count + "");
                    }
                } else {
                    sharePicAdapter.getItem(position).setCheck(!sharePicAdapter.getItem(position).isCheck());
                    sharePicAdapter.notifyDataSetChanged();

                    int count = 0;
                    for (int i = 0; i < mDatas.size(); i++) {
                        if (mDatas.get(i).isCheck()) {
                            count++;
                        }
                    }
                    tv_xuanze.setText(count + "");
                }

                int index = -1;
                for (int i = 0; i < mDatas.size(); i++) {
                    if (mDatas.get(i).isCheck()) {
                        index = i;
                        break;
                    }
                }
                sharePicAdapter.setSelectedPosition(index);
                sharePicAdapter.notifyDataSetChanged();

            }

        });
        sharePicAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                String[] picArr = mlist.toArray(new String[mlist.size()]);
                String[] picArr = new String[mDatas.size()];

                for (int i = 0; i < mDatas.size(); i++)
                    picArr[i] = mDatas.get(i).getUrl();

                Intent intent1 = new Intent(ShareZhuanActivity.this, ImagePagerActivity.class); //
                // 图片url,为了演示这里使用常量，一般从数据库中或网络中获取
                intent1.putExtra(ImagePagerActivity.EXTRA_IMAGE_URLS, picArr);
                intent1.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, position);
                startActivity(intent1);
                overridePendingTransition(R.anim.activity_zoom_open, 0);
            }
        });

        mCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (StringUtils.isEmpty(SPUtils.getInstance().getString("share_comment"))) {
                    if (mCheckbox.isChecked()) {
                        SPUtils.getInstance().put("checkbox", true);
                        mEtCommentContent.setText(getIntent().getStringExtra("Comment") + "\n" + ShowMoney_Template);
                    } else {
                        SPUtils.getInstance().put("checkbox", false);
                        mEtCommentContent.setText(getIntent().getStringExtra("Comment"));
                    }

                } else {
                    if (mCheckbox.isChecked()) {
                        SPUtils.getInstance().put("checkbox", true);
                        mEtCommentContent.setText(matchStr(SPUtils.getInstance().getString("share_comment")) + "\n" + ShowMoney_Template);
                    } else {
                        SPUtils.getInstance().put("checkbox", false);
                        mEtCommentContent.setText(matchStr(SPUtils.getInstance().getString("share_comment")));
                    }

                }
            }
        });
    }


    private void initIntent() {
        mIntent = getIntent();
        mTitle = mIntent.getStringExtra("title");
        mTemp = mIntent.getStringExtra("temp");
        mOldPrice = mIntent.getStringExtra("oldprice");
        mCouponPrice = mIntent.getStringExtra("coupon_price");
        mNowPrice = mIntent.getStringExtra("nowprice");
        mRhyUrl = mIntent.getStringExtra("rhyurl");
        mTkl = mIntent.getStringExtra("tkl");
        Comment = mIntent.getStringExtra("Comment_Template");

        mExtension = mIntent.getStringExtra("extension");
        mStudentId = mIntent.getStringExtra("studentId");
        myongjin = mIntent.getStringExtra("yongjin");
        mDownloadLink = mIntent.getStringExtra("download_link");
        ShowMoney = getIntent().getStringExtra("ShowMoney");
        ShowMoney_Template = getIntent().getStringExtra("ShowMoney_Template");
        if (TextUtils.isEmpty(ShowMoney_Template)) {
            ShowMoney_Template = "";
        }

        if (StringUtils.isEmpty(SPUtils.getInstance().getString("share_content"))) {
            et_content.setText(getIntent().getStringExtra("template"));
        } else {
            et_content.setText(matchStr(SPUtils.getInstance().getString("share_content")));
        }

        if (StringUtils.isEmpty(SPUtils.getInstance().getString("share_comment"))) {
            if (mCheckbox.isChecked()) {
                mEtCommentContent.setText(getIntent().getStringExtra("Comment") + "\n" + ShowMoney_Template);
            } else {
                mEtCommentContent.setText(getIntent().getStringExtra("Comment"));
            }

        } else {
            if (mCheckbox.isChecked()) {
                mEtCommentContent.setText(matchStr(SPUtils.getInstance().getString("share_comment")) + "\n" + ShowMoney_Template);
            } else {
                mEtCommentContent.setText(matchStr(SPUtils.getInstance().getString("share_comment")));
            }

        }
    }

    private String matchStr(String content) {

        String mTempContent = content;
        if (mTempContent.contains("{标题}"))
            mTempContent = mTempContent.replace("{标题}", mTitle);
        if (mTempContent.contains("{商品原价}"))
            mTempContent = mTempContent.replace("{商品原价}", mOldPrice);
        if (mTempContent.contains("{优惠券价格}"))
            mTempContent = mTempContent.replace("{优惠券价格}", mCouponPrice);
        if (mTempContent.contains("{券后价}"))
            mTempContent = mTempContent.replace("{券后价}", mNowPrice);
        if (mTempContent.contains("{下单链接}"))
            mTempContent = mTempContent.replace("{下单链接}", mRhyUrl);
        if (mTempContent.contains("{淘口令}"))
            mTempContent = mTempContent.replace("{淘口令}", mTkl);
        if (mTempContent.contains("宣传语"))
            mTempContent = mTempContent.replace("{宣传语}", mExtension);
        if (mTempContent.contains("邀请码"))
            mTempContent = mTempContent.replace("{邀请码}", mStudentId);
        if (mTempContent.contains("app下载链接"))
            mTempContent = mTempContent.replace("{app下载链接}", mDownloadLink);
        if (mTempContent.contains("买手佣金"))
            mTempContent = mTempContent.replace("{买手佣金}", myongjin);

        return mTempContent;
    }


    @OnClick({R.id.ll_shengcheng,
            R.id.tv_weixin,
            R.id.tv_pengyouquan,
            R.id.tv_qq,
            R.id.tv_kongjian,
            R.id.tv_weibo,
            R.id.tv_save,
            R.id.tv_rule,
            R.id.tv_edit_content,
            R.id.tv_edit_comment,
            R.id.ll_copy_comment
    })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_shengcheng:
                showCopyToast(et_content.getText().toString());
                break;
            case R.id.ll_copy_comment:
                showCopyToast(mEtCommentContent.getText().toString());
                break;
            case R.id.tv_weixin:
                shareType = 1;
                if (permission(permisssions)) {
                    IWXAPI iwxapi = WXAPIFactory.createWXAPI(ShareZhuanActivity.this, ConstantUtil.WXAPP_ID);
                    if (iwxapi.isWXAppInstalled()) {
                        WechatshareMultipleImage();
                    } else {
                        ToastUtil.show(ShareZhuanActivity.this, "未安装微信");
                    }
                } else {
                    ActivityCompat.requestPermissions(ShareZhuanActivity.this, permisssions, 111);
                }
                break;
            case R.id.tv_pengyouquan:
                shareType = 2;
                if (permission(permisssions)) {
                    IWXAPI iwxapi1 = WXAPIFactory.createWXAPI(ShareZhuanActivity.this, ConstantUtil.WXAPP_ID);
                    if (iwxapi1.isWXAppInstalled()) {
                        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                        // 将文本内容放到系统剪贴板里。
                        cm.setText(et_content.getText().toString());
                        WechatPyqshareMultipleImage();
                    } else {
                        ToastUtil.show(ShareZhuanActivity.this, "未安装微信");
                    }
                } else {
                    ActivityCompat.requestPermissions(ShareZhuanActivity.this, permisssions, 111);
                }

                break;
            case R.id.tv_qq:
                shareType = 3;
                if (permission(permisssions)) {
                    WechatshareMultipleImage();
                } else {
                    ActivityCompat.requestPermissions(ShareZhuanActivity.this, permisssions, 111);
                }
                break;
            case R.id.tv_kongjian:
                shareType = 4;
                if (permission(permisssions)) {
                    WechatshareMultipleImage();
                } else {
                    ActivityCompat.requestPermissions(ShareZhuanActivity.this, permisssions, 111);
                }
                break;
            case R.id.tv_weibo:
                shareType = 5;
                if (permission(permisssions)) {
                    WechatshareMultipleImage();
                } else {
                    ActivityCompat.requestPermissions(ShareZhuanActivity.this, permisssions, 111);
                }
                break;
            case R.id.tv_save:
                shareType = 0;
                if (permission(permisssions)) {
                    WechatPyqshareMultipleImage();
                } else {
                    ActivityCompat.requestPermissions(ShareZhuanActivity.this, permisssions, 111);
                }
                break;
            case R.id.tv_rule:
                alertDialog = showDialogLayout();
                break;
            case R.id.tv_edit_content:
                String temp;
                if (StringUtils.isEmpty(SPUtils.getInstance().getString("share_content")))
                    temp = mTemp;
                else
                    temp = SPUtils.getInstance().getString("share_content");
                startActivityForResult(new Intent(ShareZhuanActivity.this, EditShareContentActivity.class)
                                .putExtra(EditShareContentActivity.TEMP, temp)
                                .putExtra(EditShareContentActivity.TITLE, mTitle)
                                .putExtra(EditShareContentActivity.ZK_FINAL_PRICE, mOldPrice)
                                .putExtra(EditShareContentActivity.COUPON_PRICE, mCouponPrice)
                                .putExtra(EditShareContentActivity.END_PRICE, mNowPrice)
                                .putExtra(EditShareContentActivity.RHYURL, mRhyUrl)
                                .putExtra(EditShareContentActivity.TKL, mTkl)
                                .putExtra(EditShareContentActivity.EXTENSION, mExtension)
                                .putExtra(EditShareContentActivity.STUDENTID, mStudentId)
                                .putExtra(EditShareContentActivity.YONGJIN, myongjin)
                                .putExtra(EditShareContentActivity.DOWNLOADLINK, mDownloadLink)
                        , 0);
                break;
            case R.id.tv_edit_comment:
                String temp1;
                if (StringUtils.isEmpty(SPUtils.getInstance().getString("share_comment"))) {
                    temp1 = Comment;

                } else {
                    temp1 = SPUtils.getInstance().getString("share_comment");
                }


                startActivityForResult(new Intent(ShareZhuanActivity.this, EditShareCommentActivity.class)
                                .putExtra(EditShareContentActivity.TEMP, temp1)
                                .putExtra(EditShareContentActivity.TITLE, mTitle)
                                .putExtra(EditShareContentActivity.ZK_FINAL_PRICE, mOldPrice)
                                .putExtra(EditShareContentActivity.COUPON_PRICE, mCouponPrice)
                                .putExtra(EditShareContentActivity.END_PRICE, mNowPrice)
                                .putExtra(EditShareContentActivity.RHYURL, mRhyUrl)
                                .putExtra(EditShareContentActivity.TKL, mTkl)
                                .putExtra(EditShareContentActivity.EXTENSION, mExtension)
                                .putExtra(EditShareContentActivity.STUDENTID, mStudentId)
                                .putExtra(EditShareContentActivity.YONGJIN, myongjin)
                                .putExtra(EditShareContentActivity.DOWNLOADLINK, mDownloadLink)
                        , 0);
                break;


        }
    }


    @Override
    public void onResume() {
        super.onResume();
        if (!StringUtils.isEmpty(SPUtils.getInstance().getString("share_content")))
            et_content.setText(matchStr(SPUtils.getInstance().getString("share_content")));
        else
            et_content.setText(getIntent().getStringExtra("template"));

        if (StringUtils.isEmpty(SPUtils.getInstance().getString("share_comment"))) {
            if (mCheckbox.isChecked()) {
                mEtCommentContent.setText(getIntent().getStringExtra("Comment") + "\n" + ShowMoney_Template);
            } else {
                mEtCommentContent.setText(getIntent().getStringExtra("Comment"));
            }

        } else {
            if (mCheckbox.isChecked()) {
                mEtCommentContent.setText(matchStr(SPUtils.getInstance().getString("share_comment")) + "\n" + ShowMoney_Template);
            } else {
                mEtCommentContent.setText(matchStr(SPUtils.getInstance().getString("share_comment")));
            }
        }

    }


    private void copyContent(String content) {

        Map<String, String> map = new HashMap<>();
        map.put("keyword", content);
        new Request<CopyContentResponse>().request(RxJavaUtil.xApi().getCopyContent(map), "获取拷贝内容", this, false, new Result<CopyContentResponse>() {
            @Override
            public void get(CopyContentResponse response) {

                showCopyToast(response.getData());
            }
        });

    }

    private void showCopyToast(String data) {
        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        // 将文本内容放到系统剪贴板里。
        cm.setText(data);
        Toast.makeText(this, "已复制", Toast.LENGTH_LONG).show();

    }


    //原生分享多张图片
    public void shareMultipleImage() {
        shareType = 3;
        WechatshareMultipleImage();
    }


    /**
     * 微信7.0版本号，兼容处理微信7.0版本分享到朋友圈不支持多图片的问题
     */
    private static final int VERSION_CODE_FOR_WEI_XIN_VER7 = 1380;
    /**
     * 微信包名
     */
    public static final String PACKAGE_NAME_WEI_XIN = "com.tencent.mm";

    //微信分享多张图片
    public void WechatshareMultipleImage() {
        mLocalLists = new ArrayList<>();
        downPic = 0;
        final ArrayList<SharePicBean> tmp = new ArrayList<>();
        for (int i = 0; i < mDatas.size(); i++) {
            if (mDatas.get(i).isCheck()) {
                tmp.add(mDatas.get(i));
            }
        }
        if (tmp.size() > 0) {
            selectListSize = tmp.size();
            //保存二维码
            initView(tmp.get(0).getUrl(), 2, tmp);

        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (alertDialog != null) {
            alertDialog.dismiss();
            alertDialog = null;
        }

        if (wechatCircleShareDialog != null) {
            wechatCircleShareDialog.dismiss();
            wechatCircleShareDialog = null;
        }



    }

    private void savePicTo(String picUrl) {
        try {
            Bitmap myBitmap = ImageUtils.getBitMBitmap(picUrl);
            // 首先保存图片
            File appDir = new File(DOWNLOAD_DIR, "");
            if (!appDir.exists()) {
                appDir.mkdir();
            }
            String fileName = TimeUtils.getNowMills() + "maishoumama.jpg";
            File file = new File(appDir, fileName);
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


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                mLocalLists.add(getImageContentUri(ShareZhuanActivity.this, file));
            } else {
                Uri uri = Uri.fromFile(file);
                mLocalLists.add(uri);
            }
            downPic++;
            mHandler.obtainMessage(SAVE_SUCCESS).sendToTarget();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //微信朋友圈分享多张图片
    public void WechatPyqshareMultipleImage() {
        mLocalLists = new ArrayList<>();
        downPic = 0;
        final ArrayList<SharePicBean> tmp = new ArrayList<>();
        for (int i = 0; i < mDatas.size(); i++) {
            if (mDatas.get(i).isCheck()) {
                tmp.add(mDatas.get(i));
            }
        }
        if (tmp.size() > 0) {
            selectListSize = tmp.size();
            //保存二维码
            initView(tmp.get(0).getUrl(), 1, tmp);

        }
    }

    private int getVersionCode(Context context, String packageName) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(packageName, 0);
            int version = info.versionCode;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }


    /**
     * 获取网络图片
     *
     * @param imageurl 图片网络地址
     * @return Bitmap 返回位图
     */
    public Bitmap GetImageInputStream(String imageurl) {
        URL url;
        HttpURLConnection connection = null;
        Bitmap bitmap = null;
        try {
            url = new URL(imageurl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(6000); //超时设置
            connection.setDoInput(true);
            connection.setUseCaches(true); //设置不使用缓存
            InputStream inputStream = connection.getInputStream();
            bitmap = BitmapFactory.decodeStream(inputStream);
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }


    public Bitmap getBitmapFromPath(String path) {

        if (!new File(path).exists()) {
            System.err.println("getBitmapFromPath: file not exists");
            return null;
        }
        // Bitmap bitmap = Bitmap.createBitmap(1366, 768, Config.ARGB_8888);
        // Canvas canvas = new Canvas(bitmap);
        // Movie movie = Movie.decodeFile(path);
        // movie.draw(canvas, 0, 0);
        //
        // return bitmap;

        byte[] buf = new byte[1024 * 1024];// 1M
        Bitmap bitmap = null;

        try {

            FileInputStream fis = new FileInputStream(path);
            int len = fis.read(buf, 0, buf.length);
            bitmap = BitmapFactory.decodeByteArray(buf, 0, len);
            if (bitmap == null) {
                System.out.println("len= " + len);
                System.err
                        .println("path: " + path + "  could not be decode!!!");
            }
        } catch (Exception e) {
            e.printStackTrace();

        }

        return bitmap;
    }


    /**
     * 保存到系统相册
     */
    private void saveImageToCamera(Context context, String picUrl) {
        Bitmap myBitmap = ImageUtils.getBitMBitmap(picUrl);

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


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mLocalLists.add(getImageContentUri(ShareZhuanActivity.this, file));
        } else {
            Uri uri = Uri.fromFile(file);
            mLocalLists.add(uri);
        }
        downPic++;
        mHandler.obtainMessage(SAVE_SUCCESS).sendToTarget();
        //通知相册更新
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri uri = Uri.fromFile(file);
        intent.setData(uri);
        context.sendBroadcast(intent);

    }

    private void saveImageToCamera(Context context, Bitmap myBitmap, final ArrayList<SharePicBean> tmp) {
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


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mLocalLists.add(getImageContentUri(ShareZhuanActivity.this, file));
        } else {
            Uri uri = Uri.fromFile(file);
            mLocalLists.add(uri);
        }
        downPic++;
        if (tmp.size() > 1) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 1; i < tmp.size(); i++) {
                        saveImageToCamera(ShareZhuanActivity.this, tmp.get(i).getUrl());
                    }
                }
            }).start();
        } else {
            mHandler.obtainMessage(SAVE_SUCCESS).sendToTarget();
        }
        //通知相册更新
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri uri = Uri.fromFile(file);
        intent.setData(uri);
        context.sendBroadcast(intent);

    }

    private static final int SAVE_SUCCESS = 0;//保存图片成功
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SAVE_SUCCESS:
                    if (selectListSize == 0) {
                        return;
                    }
                    if (downPic == selectListSize) {
                        if (shareType == 0) {
                            ToastUtil.show(ShareZhuanActivity.this, "已保存");
                        } else if (shareType == 1) {
                            //微信好友
                            Intent intent = new Intent();
                            ComponentName comp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareImgUI");
                            intent.setComponent(comp);
                            intent.setAction(Intent.ACTION_SEND_MULTIPLE);
                            intent.setType("image/*");
                            ArrayList imageUris = new ArrayList();
                            for (Uri f : mLocalLists) {
                                imageUris.add(f);
                            }
                            intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, imageUris);
                            startActivity(intent);
                        } else if (shareType == 2) {
                            //微信朋友圈
                            if (selectListSize == 1) {
                                //分享
                                if (!TextUtils.isEmpty(getFileByUri(mLocalLists.get(0)))) {
                                    File file = new File(getFileByUri(mLocalLists.get(0)));
                                    UMImage umImages = new UMImage(ShareZhuanActivity.this, file);
                                    new ShareAction(ShareZhuanActivity.this)
                                            .setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
                                            .withMedia(umImages)
                                            .setCallback(new MyUMShareListener())
                                            .share();
                                }
                            } else {
                                wechatCircleShareDialog = new WechatCircleShareDialog(ShareZhuanActivity.this);
                            }
                        } else if (shareType == 3) {
                            Intent intent3 = new Intent();
                            ComponentName comp3 = new ComponentName("com.tencent.mobileqq", "com.tencent.mobileqq.activity.JumpActivity");
                            intent3.setComponent(comp3);
                            intent3.setAction(Intent.ACTION_SEND_MULTIPLE);
                            intent3.setType("image/*");
                            ArrayList imageUris3 = new ArrayList();
                            for (Uri f : mLocalLists) {
                                imageUris3.add(f);
                            }
                            intent3.putParcelableArrayListExtra(Intent.EXTRA_STREAM, imageUris3);
                            startActivity(intent3);
                        } else if (shareType == 4) {
                            UMImage[] umImages = new UMImage[mLocalLists.size()];
                            for (int i = 0; i < umImages.length; i++) {
                                File file = new File(getFileByUri(mLocalLists.get(i)));
                                UMImage umImage = new UMImage(ShareZhuanActivity.this, file);
                                umImages[i] = umImage;
                            }
                            new ShareAction(ShareZhuanActivity.this)
                                    .setPlatform(SHARE_MEDIA.QZONE)//传入平台
                                    .withText(et_content.getText().toString())//分享内容
                                    .withMedias(umImages)
                                    .setCallback(new MyUMShareListener())//回调监听器
                                    .share();
                        } else if (shareType == 5) {
                            UMImage[] umImages = new UMImage[mLocalLists.size()];
                            for (int i = 0; i < umImages.length; i++) {
                                File file = new File(getFileByUri(mLocalLists.get(i)));
                                UMImage umImage = new UMImage(ShareZhuanActivity.this, file);
                                umImages[i] = umImage;
                            }
                            new ShareAction(ShareZhuanActivity.this)
                                    .setPlatform(SHARE_MEDIA.SINA)//传入平台
                                    .withText(et_content.getText().toString())//分享内容
                                    .withMedias(umImages)
                                    .setCallback(new MyUMShareListener())//回调监听器
                                    .share();
                        }
                    }
                    break;
            }
        }
    };


    private void initView(String picUrl, final int type, final ArrayList<SharePicBean> tmp) {
        Log.d("zzz", getIntent().getStringExtra("eurl"));
        final View view = findViewById(R.id.ll_layout);
        final ImageView iv_newpic = (ImageView) findViewById(R.id.iv_newpic);
        ImageView iv_erweimanew = (ImageView) findViewById(R.id.iv_erweimanew);
        Bitmap bmp = QRCodeUtil.createQRCodeBitmap(getIntent().getStringExtra("eurl"), 400);
        iv_erweimanew.setImageBitmap(bmp);
        TextView tv_name = (TextView) findViewById(R.id.tv_name);
        TextView tv_quancount = (TextView) findViewById(R.id.tv_quancount);
        TextView tv_yishou = (TextView) findViewById(R.id.tv_yishou);
        TextView tv_nowprice = (TextView) findViewById(R.id.tv_nowprice);
        TextView tv_total = (TextView) findViewById(R.id.tv_total);

        Resources resources = getResources();
        Drawable imgdrawable;
        if (getIntent().getStringExtra("shop_type").equals("B")) {
            imgdrawable = resources.getDrawable(R.mipmap.icon_tianmao);
        } else {
            imgdrawable = resources.getDrawable(R.mipmap.icon_taobao);
        }
        SpannableString spannableString = new SpannableString("  " + getIntent().getStringExtra("title"));//textView控件
        imgdrawable.setBounds(0, 0, imgdrawable.getMinimumWidth(), imgdrawable.getMinimumHeight());//左上右下
        ImageSpan imageSpan = new ImageSpan(imgdrawable);
        spannableString.setSpan(imageSpan, 0, 1, ImageSpan.ALIGN_BASELINE);
        tv_name.setText(spannableString);


        tv_quancount.setText(getIntent().getStringExtra("coupon_price") + "元");
        tv_yishou.setText("月销：" + getIntent().getStringExtra("yishou"));
        tv_nowprice.setText(getIntent().getStringExtra("nowprice"));
        tv_total.setText("原价￥" + getIntent().getStringExtra("oldprice"));
        Glide.with(this).load(picUrl).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                if (resource != null) {
                    iv_newpic.setImageBitmap(resource);
                    final Bitmap bmp = loadBitmapFromView(view);
                    if (type == 1) {
                        //保存到相册
                        saveImageToCamera(ShareZhuanActivity.this, bmp, tmp);
                    } else {
                        //保存到新建相册
                        savePicture(bmp, tmp);
                    }
                    view.destroyDrawingCache(); // 保存过后释放资源
                }
            }
        });
    }


    private Bitmap loadBitmapFromView(View v) {
        int w = v.getWidth();
        int h = v.getHeight();
        Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.RGB_565);
        Canvas c = new Canvas(bmp);         /** 如果不设置canvas画布为白色，则生成透明 *///        c.drawColor(Color.WHITE);         v.layout(0, 0, w, h);        v.draw(c);
        c.drawColor(getResources().getColor(R.color.white));
        v.layout(0, 0, w, h);
        v.draw(c);
        return bmp;
    }

    public void savePicture(Bitmap bm, final ArrayList<SharePicBean> tmp) {
        try {
            // 首先保存图片
            File appDir = new File(DOWNLOAD_DIR, "");
            if (!appDir.exists()) {
                appDir.mkdir();
            }
            String fileName = TimeUtils.getNowMills() + "maishoumama.jpg";
            File file = new File(appDir, fileName);
            try {
                FileOutputStream fos = new FileOutputStream(file);
                bm.compress(Bitmap.CompressFormat.JPEG, 90, fos);
                fos.flush();
                fos.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                mLocalLists.add(getImageContentUri(ShareZhuanActivity.this, file));
            } else {
                Uri uri = Uri.fromFile(file);
                mLocalLists.add(uri);
            }
            downPic++;

            if (tmp.size() > 1) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 1; i < tmp.size(); i++) {
                            savePicTo(tmp.get(i).getUrl());
                        }
                    }
                }).start();
            } else {
                mHandler.obtainMessage(SAVE_SUCCESS).sendToTarget();
            }
            //通知相册更新
            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri uri = Uri.fromFile(file);
            intent.setData(uri);
            sendBroadcast(intent);
        } catch (Exception e) {
        }

    }


    /**
     * @param context
     * @param imageFile
     * @return content Uri
     */
    public static Uri getImageContentUri(Context context, File imageFile) {
        String filePath = imageFile.getAbsolutePath();
        Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.Media._ID}, MediaStore.Images.Media.DATA + "=? ",
                new String[]{filePath}, null);
        Uri uri = null;

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int id = cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns._ID));
                Uri baseUri = Uri.parse("content://media/external/images/media");
                uri = Uri.withAppendedPath(baseUri, "" + id);
            }
            cursor.close();
        }

        if (uri == null) {
            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.DATA, filePath);
            uri = context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        }
        return uri;
    }

    @Override
    protected String title() {

        return "创建分享";

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_sharezhuan;
    }

    @Override
    public void show(Object obj) {

    }

    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    Toast.makeText(getApplicationContext(), "分享成功", Toast.LENGTH_LONG).show();
                    break;
                case 2:
                    Toast.makeText(getApplicationContext(), "取消分享", Toast.LENGTH_LONG).show();
                    break;
                case 3:
                    Toast.makeText(getApplicationContext(), "分享失败" + "参数错误", Toast.LENGTH_LONG).show();
                    break;
                default:
                    break;
            }
        }

    };


    /**
     *
     */
    private AlertDialog showDialogLayout() {

        View view = LayoutInflater.from(ShareZhuanActivity.this).inflate(R.layout.dialog_rush, null);
        TextView tvTitle = view.findViewById(R.id.tv_title);
        TextView tvContoent = view.findViewById(R.id.tv_content);
        TextView tvOk = view.findViewById(R.id.tv_ok);
        tvTitle.setText("规则说明");
        tvContoent.setText("1、下单支付后会在预估收入里查看(会有不定期的延时)。\n" +
                "\n" +
                "2、订单在确认收货(结算)后才会呈现在结算预估收入里。\n" +
                "\n" +
                "3、当申请售后(维权)成功后会从预估收入及结算预估收入中剔除。\n" +
                "\n" +
                "4、取消订单、退款退货、申请售后维权都会产生预估收入和结算收入的数据变动。\n" +
                "\n" +
                "5、此处展示高佣为卖家设置的佣金，不同用户申请到的佣金不同，最终以实际结算结果为准。\n" +
                "\n");
        AlertDialog.Builder builder = new AlertDialog.Builder(ShareZhuanActivity.this)
                .setView(view);
        final AlertDialog alertDialog = builder.show();
        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        return alertDialog;


    }

    private class WechatCircleShareDialog extends BaseDialog implements View.OnClickListener {
        private TextView tv_cancel, tv_open;

        public WechatCircleShareDialog(Context context) {
            super(context);
            init();
        }

        private void init() {
            setContentView(R.layout.dialog_wechatcircle);
            tv_cancel = findViewById(R.id.tv_cancel);
            tv_open = findViewById(R.id.tv_open);
            tv_cancel.setOnClickListener(this);
            tv_open.setOnClickListener(this);
            show();
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv_cancel:
                    dismiss();
                    break;
                case R.id.tv_open:
                    dismiss();
                    try {
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        ComponentName cmp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.LauncherUI");
                        intent.addCategory(Intent.CATEGORY_LAUNCHER);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.setComponent(cmp);
                        startActivity(intent);
                    } catch (ActivityNotFoundException e) {
                    }
                    break;
            }
        }
    }

    private class MyUMShareListener implements UMShareListener {

        @Override
        public void onStart(SHARE_MEDIA share_media) {
            ToastUtil.show(ShareZhuanActivity.this, "分享开始，请稍后");
        }

        @Override
        public void onResult(SHARE_MEDIA share_media) {
            ToastUtil.show(ShareZhuanActivity.this, "分享成功");
        }

        @Override

        public void onError(SHARE_MEDIA share_media, Throwable throwable) {
            ToastUtil.show(ShareZhuanActivity.this, "分享失败");
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media) {
            ToastUtil.show(ShareZhuanActivity.this, "分享取消");
        }
    }

    private String getFileByUri(Uri uri) {
        try {
            //uri转换成file
            String[] arr = {MediaStore.Images.Media.DATA};
            Cursor cursor = managedQuery(uri, arr, null, null, null);
            int img_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String img_path = cursor.getString(img_index);
            File file = new File(img_path);
            String path = file.getAbsolutePath();
            return path;
        } catch (Exception e) {
        }
        return "";
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 111:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    if (shareType == 1) {
                        IWXAPI iwxapi = WXAPIFactory.createWXAPI(ShareZhuanActivity.this, ConstantUtil.WXAPP_ID);
                        if (iwxapi.isWXAppInstalled()) {
                            WechatshareMultipleImage();
                        } else {
                            ToastUtil.show(ShareZhuanActivity.this, "未安装微信");
                        }
                    } else if (shareType == 2) {
                        IWXAPI iwxapi1 = WXAPIFactory.createWXAPI(ShareZhuanActivity.this, ConstantUtil.WXAPP_ID);
                        if (iwxapi1.isWXAppInstalled()) {
                            ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                            // 将文本内容放到系统剪贴板里。
                            cm.setText(et_content.getText().toString());
                            WechatPyqshareMultipleImage();
                        } else {
                            ToastUtil.show(ShareZhuanActivity.this, "未安装微信");
                        }
                    } else if (shareType == 3) {
                        WechatshareMultipleImage();
                    } else if (shareType == 4) {
                        WechatshareMultipleImage();
                    } else if (shareType == 5) {
                        WechatshareMultipleImage();
                    }
                } else {
                    Toast.makeText(ShareZhuanActivity.this, "为了您能正常使用，请开启权限", Toast.LENGTH_SHORT).show();
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
                if (ContextCompat.checkSelfPermission(ShareZhuanActivity.this, permission[i]) == PackageManager.PERMISSION_GRANTED) {
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
