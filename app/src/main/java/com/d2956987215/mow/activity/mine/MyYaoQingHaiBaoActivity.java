package com.d2956987215.mow.activity.mine;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Toast;

import com.d2956987215.mow.R;
import com.d2956987215.mow.activity.BaseActivity;
import com.d2956987215.mow.activity.home.ShareZhuanActivity;
import com.d2956987215.mow.adapter.InviteAdapter;
import com.d2956987215.mow.adapter.RecAdapter;
import com.d2956987215.mow.rxjava.Request;
import com.d2956987215.mow.rxjava.Result;
import com.d2956987215.mow.rxjava.RxJavaUtil;
import com.d2956987215.mow.rxjava.response.HaiBaoResponse;
import com.d2956987215.mow.util.ConstantUtil;
import com.d2956987215.mow.util.DisplayUtil;
import com.d2956987215.mow.util.MyLog;
import com.d2956987215.mow.util.QRCodeUtil;
import com.d2956987215.mow.util.ToastUtil;
import com.d2956987215.mow.util.User;
import com.d2956987215.mow.widgets.coverflow.CoverFlowLayoutManger;
import com.d2956987215.mow.widgets.coverflow.RecyclerCoverFlow;
import com.d2956987215.mow.widgets.yulanpic.ImagePagerActivity;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class MyYaoQingHaiBaoActivity extends BaseActivity implements InviteAdapter.onItemClick {


    @BindView(R.id.rl_dianying)
    RecyclerCoverFlow rl_dianying;
    private HaiBaoResponse.DataBean dataBean;
    private List<String> mlist = new ArrayList<String>();
    private String fenxiang;
    private String[] permisssions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (permission(permisssions)) {
            huoqu();
        } else {
            ActivityCompat.requestPermissions(MyYaoQingHaiBaoActivity.this, permisssions, 111);
        }

    }


    private void huoqu() {
        showProgress();
        Map<String, String> map = new HashMap<>();
        map.put("user_id", User.uid() + "");
        new Request<HaiBaoResponse>().request(RxJavaUtil.xApi().haibao(map, "Bearer " + User.token()), "分享邀请", this, false, new Result<HaiBaoResponse>() {
            @Override
            public void get(final HaiBaoResponse response) {
                dataBean = response.getData();
                final List<HaiBaoResponse.DataBean.HbimgBean> srt = dataBean.getHbimg();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < response.getData().getHbimg().size(); i++) {
                            final Bitmap bmp = mergeBitmap(response.getData().getUrl(), srt.get(i).getSrc(),
                                    srt.get(i).getW(), srt.get(i).getH(), srt.get(i).getX(), srt.get(i).getY());
                            savePicture(bmp, i + DisplayUtil.getCurrTime() + ".png");
                        }

                    }
                }).start();


            }


        });
    }


    @OnClick({R.id.tv_weixin, R.id.tv_pengyouquan, R.id.tv_weibo, R.id.tv_qq, R.id.tv_kongjian, R.id.tv_xiangce, R.id.tv_reward_rules})
    public void onViewClicked(View view) {
        if (TextUtils.isEmpty(fenxiang)) {
            return;
        }
        UMImage umImages = new UMImage(MyYaoQingHaiBaoActivity.this, new File(fenxiang));
        switch (view.getId()) {
            case R.id.tv_weixin:
                IWXAPI iwxapi = WXAPIFactory.createWXAPI(MyYaoQingHaiBaoActivity.this, ConstantUtil.WXAPP_ID);
                if (iwxapi.isWXAppInstalled()) {
                    new ShareAction(MyYaoQingHaiBaoActivity.this)
                            .setPlatform(SHARE_MEDIA.WEIXIN)
                            .withMedia(umImages)
                            .setCallback(new MyListener())
                            .share();

                } else {
                    ToastUtil.show(MyYaoQingHaiBaoActivity.this, "未安装微信");
                }

                break;
            case R.id.tv_pengyouquan:
                IWXAPI iwxapi1 = WXAPIFactory.createWXAPI(MyYaoQingHaiBaoActivity.this, ConstantUtil.WXAPP_ID);
                if (iwxapi1.isWXAppInstalled()) {
                    new ShareAction(MyYaoQingHaiBaoActivity.this)
                            .setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
                            .withMedia(umImages)
                            .setCallback(new MyListener())
                            .share();

                } else {
                    ToastUtil.show(MyYaoQingHaiBaoActivity.this, "未安装微信");
                }

                break;
            case R.id.tv_weibo:
                new ShareAction(MyYaoQingHaiBaoActivity.this)
                        .setPlatform(SHARE_MEDIA.SINA)
                        .withMedia(umImages)
                        .setCallback(new MyListener())
                        .share();

                break;
            case R.id.tv_qq:
                new ShareAction(MyYaoQingHaiBaoActivity.this)
                        .setPlatform(SHARE_MEDIA.QQ)
                        .withMedia(umImages)
                        .setCallback(new MyListener())
                        .share();

                break;
            case R.id.tv_kongjian:
                new ShareAction(MyYaoQingHaiBaoActivity.this)
                        .setPlatform(SHARE_MEDIA.QZONE)
                        .withMedia(umImages)
                        .setCallback(new MyListener())
                        .share();

                break;
            case R.id.tv_xiangce:
//                Bitmap bmp1 = returnBitmap(fenxiang);
//                savePicture(bmp1, "monitor.png");// 保存图片
                ToastUtil.show(this, "保存成功");

                break;

            case R.id.tv_reward_rules:
                startActivity(new Intent(this, RewardRulesActivity.class)
                        .putExtra(RewardRulesActivity.URL, dataBean.getGuize())
                );
                break;
            default:
                break;


        }
    }

    public void savePicture(Bitmap bm, String fileName) {

        if (bm == null) {
            Toast.makeText(this, "savePicture null !", Toast.LENGTH_SHORT).show();
            return;
        }

        File foder = new File("/sdcard/maishoumama/pic");
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
        MyLog.e("SSSSSSSSSSS", "savePicture: 保存成功");
//        Bitmap bmpDefaultPic = null;
//        if (bmpDefaultPic == null)
//            bmpDefaultPic = BitmapFactory.decodeFile("/sdcard/shareimg.png", null);

        mlist.add("file://" + myCaptureFile.getAbsolutePath());
        handler.sendEmptyMessage(10);
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

    private Bitmap mergeBitmap(String erweimaurl, String url1, int whidth, int height, int x, int y) {
        // 获取ImageView上得Bitmap图片


        Bitmap bmp1 = returnBitmap(url1);

//        Bitmap bmp2 = returnBitmap(erweimaurl);
        Bitmap bmp2 = QRCodeUtil.createQRCodeBitmap(erweimaurl, whidth);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);


        // 创建空得背景bitmap
        // 生成画布图像
        Bitmap resultBitmap = Bitmap.createBitmap(bmp1.getWidth(),
                bmp1.getHeight(), Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(resultBitmap);// 使用空白图片生成canvas
        canvas.drawColor(Color.WHITE);


        // 将bmp1绘制在画布上
        Rect srcRect = new Rect(0, 0, bmp1.getWidth(), bmp1.getHeight());// 截取bmp1中的矩形区域
        Rect dstRect = new Rect(0, 0, resultBitmap.getWidth(),
                resultBitmap.getHeight());// bmp1在目标画布中的位置
        canvas.drawBitmap(bmp1, srcRect, dstRect, null);

        // 将bmp2绘制在画布上
        srcRect = new Rect(0, 0, whidth, height);// 截取bmp1中的矩形区域
        dstRect = new Rect(x, y, x + whidth, height + y);// bmp2在目标画布中的位置
        canvas.drawBitmap(bmp2, srcRect, dstRect, null);

        // 将bmp1,bmp2合并显示
        return resultBitmap;
    }


    private RecAdapter mRecAdapter;

    private void bangding(List<String> hbimglist) {

//        rl_dianying.setFlatFlow(true); //平面滚动
//        rl_dianying.setGreyItem(true); //设置灰度渐变
//        rl_dianying.setAlphaItem(true); //设置半透渐变
        rl_dianying.setAdapter(new InviteAdapter(this, this, hbimglist));
        rl_dianying.setOnItemSelectedListener(new CoverFlowLayoutManger.OnSelected() {
            @Override
            public void onItemSelected(int position) {
//                ((TextView)findViewById(R.id.index)).setText((position+1)+"/"+mList.getLayoutManager().getItemCount());
                if (position == mlist.size())
                    fenxiang = mlist.get(position - 1).toString().replace("file://", "");
                else
                    fenxiang = mlist.get(position).toString().replace("file://", "");

            }
        });


        /*


        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rl_dianying.setLayoutManager(manager);
        int mRecyclerviewWidth;
        ViewGroup.LayoutParams layoutParams = rl_dianying.getLayoutParams();
        if (layoutParams.width == -1) {
            mRecyclerviewWidth = DisplayUtils.getScreenWidth(this);//我这里是全屏幕宽度，根据实际情况定
        } else {
            mRecyclerviewWidth = layoutParams.width;
        }
        mRecAdapter = new RecAdapter(this, mRecyclerviewWidth, rl_dianying, hbimglist);
        rl_dianying.setAdapter(mRecAdapter);

        final LinearSnapHelper helper = new LinearSnapHelper();
        helper.attachToRecyclerView(rl_dianying);
        rl_dianying.addOnScrollListener(new RecyclerView.OnScrollListener() {


            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);


                int childCount = rl_dianying.getChildCount();
                fenxiang = mlist.get(childCount - 2).toString().replace("file://", "");

                int[] location = new int[2];
                for (int i = 0; i < childCount; i++) {
                    View v = rl_dianying.getChildAt(i);
                    v.getLocationOnScreen(location);


                    int recyclerViewCenterX = rl_dianying.getLeft() + rl_dianying.getWidth() / 2;
                    int itemCenterX = location[0] + v.getWidth() / 2;

//                   ★ 两边的图片缩放比例
                    float scale = 0.7f;
//                     ★某个item中心X坐标距recyclerview中心X坐标的偏移量
                    int offX = Math.abs(itemCenterX - recyclerViewCenterX);
//                    ★ 在一个item的宽度范围内，item从1缩放至scale，那么改变了（1-scale），从下列公式算出随着offX变化，item的变化缩放百分比

                    float percent = offX * (1 - scale) / v.getWidth();
//                   ★  取反哟
                    float interpretateScale = 1 - percent;


//                    这个if不走的话，得到的是多级渐变模式
                    if (interpretateScale < scale) {
                        interpretateScale = scale;
                    }
                    v.setScaleX((interpretateScale));
                    v.setScaleY((interpretateScale));

//                    Log.e("qwe", recyclerViewCenterX + "///" + itemCenterX + "///" + interpretateScale + "///" + percent + "///" + i);
//                    Log.e("qwe", "-----");

                }
//                Log.e("qwe", "====================");

            }

        });
*/
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_myhaibao;
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
                    if (mlist.size() == dataBean.getHbimg().size()) {
                        fenxiang = mlist.get(0).toString().replace("file://", "");
                        hideProgress();
                        bangding(mlist);
                    }

                default:
                    break;
            }
        }

    };


    @Override
    public void clickItem(int pos) {
        rl_dianying.smoothScrollToPosition(pos);
        String[] picArr = (String[]) mlist.toArray(new String[mlist.size()]);
        Intent intent1 = new Intent(MyYaoQingHaiBaoActivity.this, ImagePagerActivity.class); //
        // 图片url,为了演示这里使用常量，一般从数据库中或网络中获取
        intent1.putExtra(ImagePagerActivity.EXTRA_IMAGE_URLS, picArr);
        intent1.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, pos);
        MyYaoQingHaiBaoActivity.this.startActivity(intent1);
        MyYaoQingHaiBaoActivity.this.overridePendingTransition(R.anim.activity_zoom_open, 0);
    }

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
                    huoqu();
                } else {
                    Toast.makeText(MyYaoQingHaiBaoActivity.this, "为了您能正常使用，请开启权限", Toast.LENGTH_SHORT).show();
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
                if (ContextCompat.checkSelfPermission(MyYaoQingHaiBaoActivity.this, permission[i]) == PackageManager.PERMISSION_GRANTED) {
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
