package com.d2956987215.mow.activity.luntan;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.TimeUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.d2956987215.mow.R;
import com.d2956987215.mow.activity.SplashActivity;
import com.d2956987215.mow.activity.login.LoginNewActivity;
import com.d2956987215.mow.adapter.LunTanAdapter;
import com.d2956987215.mow.adapter.SuCaiAdapter;
import com.d2956987215.mow.adapter.SuCaiErJiAdapter;
import com.d2956987215.mow.dialog.BaseDialog;
import com.d2956987215.mow.dialog.ShareDialogView;
import com.d2956987215.mow.listener.IShowData;
import com.d2956987215.mow.rxjava.Request;
import com.d2956987215.mow.rxjava.Result;
import com.d2956987215.mow.rxjava.RxJavaUtil;
import com.d2956987215.mow.rxjava.response.BaseResponse;
import com.d2956987215.mow.rxjava.response.LanMuListResponse;
import com.d2956987215.mow.rxjava.response.LunTanTitleResponse;
import com.d2956987215.mow.util.ActivityUtils;
import com.d2956987215.mow.util.ConstantUtil;
import com.d2956987215.mow.util.DisplayUtil;
import com.d2956987215.mow.util.ImageUtils;
import com.d2956987215.mow.util.QRCodeUtil;
import com.d2956987215.mow.util.ToastUtil;
import com.d2956987215.mow.util.User;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

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
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.bakumon.statuslayoutmanager.library.StatusLayoutManager;


/**
 * A simple {@link Fragment} subclass.
 */
public class LunTanXuanFragment extends Fragment implements IShowData, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.recycler_hot_product)
    RecyclerView recyclerHotProduct;

    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.tag_first)
    RecyclerView tag_first;


    Unbinder unbinder;

    private int p = 1;
    private List<LanMuListResponse.DataBean> sList = new ArrayList<>();
    private String column_id;
    private String sucaicolumn_id;
    private int type;
    private LunTanXuanFragment baseframent;
    private StatusLayoutManager statusLayoutManager;
    private Bitmap mShareBitmap;
    private String mShareName;
    private String mCopyContent;
    private String shareName, mId;
    private String[] sharePic;
    private String sharePicUrl, shareQrcodeUrl;
    private String[] permisssions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};

    public LunTanXuanFragment() {
        // Required empty public constructor
    }

    public void setType(int type) {

        this.type = type;


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_luntandetail, container, false);
        unbinder = ButterKnife.bind(this, view);
        baseframent = this;
        statusLayoutManager = new StatusLayoutManager.Builder(recyclerHotProduct).setDefaultLayoutsBackgroundColor(Color.WHITE).setEmptyLayout(initEmptyView())
                .build();
        refreshLayout.setOnRefreshListener(this);
        showRefresh();

        listtitle();

        return view;
    }

    private void showRefresh() {
        refreshLayout.post(new Runnable() {
            @Override
            public void run() {
                if(refreshLayout!=null){
                    refreshLayout.setRefreshing(true);
                }

            }
        });
    }


    private SuCaiErJiAdapter sucaiAdapter;

    public void listtitle() {

        new Request<LunTanTitleResponse>().request(RxJavaUtil.xApi().getsucaititlelist(), "素材头列表", getActivity(), false, new Result<LunTanTitleResponse>() {
            @Override
            public void get(final LunTanTitleResponse response) {
                if (response.getData() != null) {
                    sucaiAdapter = new SuCaiErJiAdapter(R.layout.adapter_textfujin, response.getData());
                    int spanCount = 1; // 只显示一行
                    StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(spanCount, StaggeredGridLayoutManager.HORIZONTAL);
                    tag_first.setLayoutManager(layoutManager);
                    tag_first.setHasFixedSize(true);
                    tag_first.setNestedScrollingEnabled(false);
                    tag_first.setAdapter(sucaiAdapter);
                    sucaicolumn_id = response.getData().get(0).getCat_id() + "";
                    huoqusucailist();
                    sucaiAdapter.setSelectIndex(0);
                    sucaiAdapter.notifyDataSetChanged();
                    sucaiAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                            p = 1;
                            showRefresh();
                            sucaiAdapter.setSelectIndex(position);
                            sucaiAdapter.notifyDataSetChanged();
                            sucaicolumn_id = response.getData().get(position).getCat_id() + "";
                            huoqusucailist();

                        }
                    });

                }


            }
        });


    }

    private void huoqusucailist() {
        Map<String, String> map = new HashMap<>();
        map.put("cat_id", sucaicolumn_id);
        map.put("page", p + "");
        map.put("user_id", User.uid() + "");
        new Request<LanMuListResponse>().request(RxJavaUtil.xApi1().getResouseList(map), "每日素材列表", getActivity(), false, new Result<LanMuListResponse>() {
            @Override
            public void onServerError(String errDesc) {
                super.onServerError(errDesc);
            }

            @Override
            public void onBackErrorMessage(LanMuListResponse response) {
                super.onBackErrorMessage(response);
            }

            @Override
            public void get(LanMuListResponse response) {
                refreshLayout.setRefreshing(false);
                if (response.getData() instanceof List && ((List) response.getData()).size() > 0) {
                    statusLayoutManager.showSuccessLayout();
                    List<LanMuListResponse.DataBean> list = response.getData();
                    initHotRecyclersucai(list);
                } else {

                    if (ScAdapter != null) {
                        if (p == 1) {
                            ScAdapter.getData().clear();
                            statusLayoutManager.showEmptyLayout();
                            ScAdapter.notifyDataSetChanged();
                        } else {
                            ScAdapter.loadMoreComplete();
                            ScAdapter.loadMoreEnd();

                        }

                    } else {
                        statusLayoutManager.showEmptyLayout();
                    }
                }

            }
        });

    }

    private SuCaiAdapter ScAdapter;

    private void initHotRecyclersucai(final List<LanMuListResponse.DataBean> list) {
        if (ScAdapter == null) {
            ScAdapter = new SuCaiAdapter(R.layout.item_sucai, list, getActivity());
            ScAdapter.setPreLoadNumber(3);
            LinearLayoutManager manager = new LinearLayoutManager(getContext());
            manager.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerHotProduct.setLayoutManager(manager);
            recyclerHotProduct.setAdapter(ScAdapter);
        }
        if (p == 1) {
            refreshLayout.setRefreshing(false);
            ScAdapter.setNewData(list);
        } else {
            ScAdapter.addData(list);
            ScAdapter.loadMoreComplete();
        }
        ScAdapter.setClickButtonListener(new SuCaiAdapter.onClickShareListener() {

            @Override
            public void share(final String[] hehe, final String name, String id) {
                mId = id;
                sharePic = hehe;
                shareName = name;
                if (User.uid() > 0) {
                    if (permission(permisssions)) {
                        showShareDialog(hehe, name, id);
                    } else {
                        requestPermissions(permisssions, 111);
                    }

                } else {
                    ToastUtil.show(getActivity(), "请先登录");
//                    Intent intent = new Intent(getActivity(), SplashActivity.class);
//                    Intent intent = new Intent(getActivity(), LoginNewActivity.class);
//                    getActivity().startActivity(intent);
                    ActivityUtils.startLoginAcitivy(getActivity());
                    return;
                }

            }

            @Override
            public void shareBitmap(final String picUrl, final String qrcodeUrl, final String name, String id) {
                mId = id;
                sharePicUrl = picUrl;
                shareName = name;
                shareQrcodeUrl = qrcodeUrl;
                if (User.uid() > 0) {
                    if (permission(permisssions)) {
                        shareBitmapDialog(picUrl, qrcodeUrl, name, id);
                    } else {
                        requestPermissions(permisssions, 222);
                    }
                } else {
                    ToastUtil.show(getActivity(), "请先登录");
//                    Intent intent = new Intent(getActivity(), SplashActivity.class);
//                    Intent intent = new Intent(getActivity(), LoginNewActivity.class);
//                    getActivity().startActivity(intent);
                    ActivityUtils.startLoginAcitivy(getActivity());
                    return;
                }


            }
        });

        ScAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                p++;
                huoqusucailist();
            }
        }, recyclerHotProduct);

    }


    private Bitmap mergeBitmap(String erweimaurl, String url1, int whidth, int height, int x, int y) {
        // 获取ImageView上得Bitmap图片


        Bitmap bmp1 = returnBitmap(url1);

//        Bitmap bmp2 = returnBitmap(erweimaurl);
        Bitmap bmp2 = QRCodeUtil.createQRCodeBitmap(erweimaurl, whidth);
//        DisplayMetrics metrics = new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(metrics);


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


    private View initEmptyView() {
        View emptyView = getActivity().getLayoutInflater().inflate(R.layout.custom_empty_view, null);
        ImageView iv = emptyView.findViewById(R.id.empty_retry_view);
        TextView tv = emptyView.findViewById(R.id.empty_view_tv);
        iv.setBackgroundResource(R.drawable.nothing_img_default);
        tv.setText("当前页面暂无内容哦~");
        return emptyView;
    }


    private void huoqulist() {
        Map<String, String> map = new HashMap<>();
        map.put("cat_id", column_id);
        map.put("page", p + "");
        if (User.uid() > 0) {
            map.put("user_id", User.uid() + "");
        }
        new Request<LanMuListResponse>().request(RxJavaUtil.xApi().getdayhot(map), "每日爆款列表", getActivity(), false, new Result<LanMuListResponse>() {
            @Override
            public void get(LanMuListResponse response) {
                refreshLayout.setRefreshing(false);
                if (response.getData() instanceof List && ((List) response.getData()).size() > 0) {
                    List<LanMuListResponse.DataBean> list = response.getData();
                    initHotRecycler(list);
                } else {
                    if (p == 1) {
                        if (sList != null) {
                            sList.clear();
                        }
                        if (hotAdapter != null) {
                            hotAdapter.notifyDataSetChanged();
                        }


                    }

                }

            }
        });

    }


    private LunTanAdapter hotAdapter;

    private void initHotRecycler(final List<LanMuListResponse.DataBean> list) {
        if (hotAdapter == null) {
            hotAdapter = new LunTanAdapter(R.layout.item_luntan, sList, getActivity());
            LinearLayoutManager manager = new LinearLayoutManager(getContext());
            manager.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerHotProduct.setLayoutManager(manager);
            recyclerHotProduct.setHasFixedSize(true);
            recyclerHotProduct.setNestedScrollingEnabled(false);
            recyclerHotProduct.setAdapter(hotAdapter);
        }
        if (p == 1) {
            sList.clear();
        }
        sList.addAll(list);
        hotAdapter.notifyDataSetChanged();

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @SuppressWarnings("unchecked")
    @Override
    public void show(Object obj) {

    }
//
//    @Override
//    public void onRefresh(RefreshLayout refreshlayout) {
//        p = 1;
//        huoqulist();
//        refreshLayout.finishRefresh();
//    }
//
//
//    @Override
//    public void onLoadmore(RefreshLayout refreshlayout) {
//        p++;
//        huoqulist();
//        refreshlayout.finishLoadmore();
//
//    }

//    @Override
//    public void onComplete(Platform arg0, int arg1, HashMap<String, Object> arg2) {//回调的地方是子线程，进行UI操作要用handle处理
//        if (arg0.getName().equals(QQ.NAME)) {// 判断成功的平台是不是QQ
//            handler.sendEmptyMessage(1);
//        }
//        if (arg0.getName().equals(QZone.NAME)) {// 判断成功的平台是不是QQ
//            handler.sendEmptyMessage(1);
//        }
//        if (arg0.getName().equals(SinaWeibo.NAME)) {// 判断成功的平台是不是QQ
//            handler.sendEmptyMessage(1);
//        }
//        if (arg0.getName().equals(WechatMoments.NAME)) {// 判断成功的平台是不是QQ
//            handler.sendEmptyMessage(1);
//        }
//        if (arg0.getName().equals(Wechat.NAME)) {// 判断成功的平台是不是QQ
//            handler.sendEmptyMessage(1);
//        }
//
//
//        shareRequest();
//
//    }

//    @Override
//    public void onError(Platform arg0, int arg1, Throwable arg2) {//回调的地方是子线程，进行UI操作要用handle处理
//        arg2.printStackTrace();
//        Message msg = new Message();
//        msg.what = 3;
//        msg.obj = arg2.getMessage();
//        handler.sendMessage(msg);
//    }
//
//    Handler handler = new Handler() {
//
//        @Override
//        public void handleMessage(Message msg) {
//            switch (msg.what) {
//                case 1:
//                    // Toast.makeText(getApplicationContext(), "分享成功", Toast.LENGTH_LONG).show();
//                    break;
//                case 2:
//                    //Toast.makeText(getApplicationContext(), "取消分享", Toast.LENGTH_LONG).show();
//                    break;
//                case 3:
//                    // Toast.makeText(getApplicationContext(), "分享失败" + "参数错误", Toast.LENGTH_LONG).show();
//                    break;
//                case 10:
//
//                    ButtomDialogView bottomDialog = new ButtomDialogView(getActivity(), new ButtomDialogView.CallBack() {
//                        @Override
//                        public void weixinhaoyou() {
//                            Platform.ShareParams wechat = new Platform.ShareParams();
//                            wechat.setTitle(mShareName);
//                            wechat.setShareType(Platform.SHARE_IMAGE);
//                            wechat.setImageData(mShareBitmap);
//
//                            Platform weixin = ShareSDK.getPlatform(Wechat.NAME);
//                            if (weixin.isClientValid()) {
//                                //System.out.println("安装了微信");
//                                weixin.setPlatformActionListener(baseframent);
//                                weixin.share(wechat);
//                            } else {
//                                //System.out.println("没有安装了微信");
//                                ToastUtil.show(getActivity(), "未安装微信");
//                            }
//
//                        }
//
//                        @Override
//                        public void pengyouquan() {
//                            Platform.ShareParams wechat = new Platform.ShareParams();
//                            wechat.setTitle("我的邀请");
//                            wechat.setShareType(Platform.SHARE_IMAGE);
//                            wechat.setImageData(mShareBitmap);
//
//                            Platform weixinpeng = ShareSDK.getPlatform(WechatMoments.NAME);
//                            if (weixinpeng.isClientValid()) {
//                                //System.out.println("安装了微信");
//                                weixinpeng.setPlatformActionListener(baseframent);
//                                weixinpeng.share(wechat);
//                            } else {
//                                //System.out.println("没有安装了微信");
//                                ToastUtil.show(getActivity(), "未安装微信");
//                            }
//
//                        }
//
//                        @Override
//                        public void xinlangweibo() {
//                            Platform.ShareParams sp = new Platform.ShareParams();
//                            sp.setImageData(mShareBitmap);
//                            Platform qq = ShareSDK.getPlatform(SinaWeibo.NAME);
//                            qq.setPlatformActionListener(baseframent); // 设置分享事件回调
//                            // 执行分享
//                            qq.share(sp);
//                        }
//
//                        @Override
//                        public void qqhaoyou() {
//                            Platform.ShareParams sp = new Platform.ShareParams();
////                sp.setTitle("我的邀请");
////                sp.setText(getIntent().getStringExtra("studentId"));
//                            sp.setImageData(mShareBitmap);
//
//                            // sp.setImageUrl(data.getCommunity_image());
//                            Platform qq = ShareSDK.getPlatform(QQ.NAME);
//                            qq.setPlatformActionListener(baseframent); // 设置分享事件回调
//                            // 执行分享
//                            qq.share(sp);
//                        }
//
//                        @Override
//                        public void qqkongjian() {
//                            Platform.ShareParams kongjian = new Platform.ShareParams();
//                            kongjian.setTitle("我的邀请");
//                            kongjian.setImageData(mShareBitmap);
//                            // sp.setImageUrl(data.getCommunity_image());
//                            Platform qnone = ShareSDK.getPlatform(QZone.NAME);
//                            qnone.setPlatformActionListener(baseframent); // 设置分享事件回调
//                            // 执行分享
//                            qnone.share(kongjian);
//
//
//                        }
//
//
//                        @Override
//                        public void fuzhi() {
//                            ClipboardManager cm = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
//                            // 将文本内容放到系统剪贴板里。
//                            cm.setText(mCopyContent);
//                            Toast.makeText(getActivity(), "复制成功，可以发给朋友们了。", Toast.LENGTH_LONG).show();
//
//
//                        }
//
//                        @Override
//                        public void NO() {
//
//                        }
//                    });
//                    bottomDialog.show();
//
//
//                    break;
//                default:
//                    break;
//            }
//        }
//
//    };

    private void shareRequest() {
        Map<String, String> map = new HashMap<>();
        map.put("id", mId);
        new Request<BaseResponse>().request(RxJavaUtil.xApi().buyQuanShareNum(map), "分享回调", getActivity(), false, new Result<BaseResponse>() {
            @Override
            public void get(BaseResponse response) {


            }
        });

    }

    @Override
    public void onRefresh() {
        p = 1;
        huoqusucailist();
    }

    private ArrayList<Uri> mLocalLists = new ArrayList<>();//保存到本地的图片路径
    private int downPic = 0;

    private class MyUMShareListener implements UMShareListener {

        @Override
        public void onStart(SHARE_MEDIA share_media) {
            ToastUtil.show(getActivity(), "分享开始，请稍后");
            shareRequest();
        }

        @Override
        public void onResult(SHARE_MEDIA share_media) {
            ToastUtil.show(getActivity(), "分享成功");
        }

        @Override

        public void onError(SHARE_MEDIA share_media, Throwable throwable) {
            ToastUtil.show(getActivity(), "分享失败");
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media) {
            ToastUtil.show(getActivity(), "分享取消");
        }
    }

    public static final String ROOTPATH = Environment.getExternalStorageDirectory().getPath();
    public static final String DOWNLOAD_DIR = ROOTPATH + "/download/";

    private void saveImageToCamera(Context context, String picUrl, String[] picList, String name, boolean isOPenWechat) {
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
            mLocalLists.add(getImageContentUri(getActivity(), file));
        } else {
            Uri uri = Uri.fromFile(file);
            mLocalLists.add(uri);
        }
        downPic++;
        if (downPic == picList.length) {
            if (isOPenWechat) {
                this.picList = picList;
                this.name = name;
                notifyHandle.sendEmptyMessage(1);
            } else {
                notifyHandle.sendEmptyMessage(2);
            }
        }

    }

    private String[] picList;
    private String name;

    @SuppressLint("HandlerLeak")
    Handler notifyHandle = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                new WechatCircleShareDialog(getActivity(), picList, name);
            } else if (msg.what == 2) {
                ToastUtil.show(getActivity(), "保存成功");
            }
        }
    };

    private void saveImageToCamera(Context context, Bitmap myBitmap) {
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
            mLocalLists.add(getImageContentUri(getActivity(), file));
        } else {
            Uri uri = Uri.fromFile(file);
            mLocalLists.add(uri);
        }
        ToastUtil.show(getActivity(), "保存成功");
        //通知相册更新
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri uri = Uri.fromFile(file);
        intent.setData(uri);
        context.sendBroadcast(intent);

    }

    private void savePicTo(String picUrl, int type, int picsize) {
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
                mLocalLists.add(getImageContentUri(getActivity(), file));
            } else {
                Uri uri = Uri.fromFile(file);
                mLocalLists.add(uri);
            }
            downPic++;
            if (downPic == picsize) {
                //type:1微信好友；2微信朋友圈；3QQ好友；4QQ空间；5新浪
                switch (type) {
                    case 1:
                        Intent intent1 = new Intent();
                        ComponentName comp1 = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareImgUI");
                        intent1.setComponent(comp1);
                        intent1.setAction(Intent.ACTION_SEND_MULTIPLE);
                        intent1.setType("image/*");
                        ArrayList imageUris1 = new ArrayList();
                        for (Uri f : mLocalLists) {
                            imageUris1.add(f);
                        }
                        intent1.putParcelableArrayListExtra(Intent.EXTRA_STREAM, imageUris1);
                        startActivity(intent1);
                        break;
                    case 2:
                        break;
                    case 3:
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
                        break;
                    case 4:
                        Intent intent4 = new Intent();
                        ComponentName comp4 = new ComponentName("com.tencent.mobileqq", "cooperation.qzone.QzonePublishMoodProxyActivity");// 无用代码
                        // ComponentName comp4 = new ComponentName("com.qzone", "com.qzonex.module.operation.ui.QZonePublishMoodActivity");
                        intent4.setComponent(comp4);
                        intent4.setAction(Intent.ACTION_SEND_MULTIPLE);
                        intent4.setType("image/*");
                        ArrayList imageUris4 = new ArrayList();
                        for (Uri f : mLocalLists) {
                            imageUris4.add(f);
                        }
                        intent4.putParcelableArrayListExtra(Intent.EXTRA_STREAM, imageUris4);
                        startActivity(intent4);
                        break;
                    case 5:
                        break;
                }


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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

    private void haibao(String picUrl, String qrcodeUrl, final SHARE_MEDIA sharePlatfrom, final int type) {
        final View view = LayoutInflater.from(getActivity()).inflate(R.layout.layout_haibao1, null);
        final ImageView goodsPic = view.findViewById(R.id.goodsPic);
        final ImageView iv_erweimanew = view.findViewById(R.id.iv_erweimanew);
        final ScrollView scroll = view.findViewById(R.id.scroll);
        Bitmap bmp = QRCodeUtil.createQRCodeBitmap(qrcodeUrl, 400);
        iv_erweimanew.setImageBitmap(bmp);
        final int imgWidth = (DisplayUtil.getScreenWidth(getActivity()) - DisplayUtil.dip2px(getActivity(), 92)) / 3 * 2 + DisplayUtil.dip2px(getActivity(), 20);
        Glide.with(getActivity()).load(picUrl).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) goodsPic.getLayoutParams();
                params.width = imgWidth;
                goodsPic.setLayoutParams(params);
                goodsPic.setImageBitmap(resource);

                Bitmap bmp = createImage(view, scroll);
                view.destroyDrawingCache(); // 保存过后释放资源

                if (type == 1) {
                    //分享
                    UMImage umImages = new UMImage(getActivity(), bmp);
                    new ShareAction(getActivity())
                            .setPlatform(sharePlatfrom)
                            .withMedias(umImages)
                            .setCallback(new MyUMShareListener())
                            .share();
                } else {
                    //2是保存
                    saveImageToCamera(getActivity(), bmp);
                }
                return;
            }
        });

    }

    private Bitmap createImage(View view, ScrollView scroll) {
        scroll.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        int IMAGE_HEIGHT = scroll.getMeasuredHeight();
        int IMAGE_WIDTH = scroll.getMeasuredWidth();

        //由于直接new出来的view是不会走测量、布局、绘制的方法的，所以需要我们手动去调这些方法，不然生成的图片就是黑色的。
        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(IMAGE_WIDTH, View.MeasureSpec.EXACTLY);
        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(IMAGE_HEIGHT, View.MeasureSpec.EXACTLY);
        view.measure(widthMeasureSpec, heightMeasureSpec);
        view.layout(0, 0, IMAGE_WIDTH, IMAGE_HEIGHT);
        Bitmap bitmap = Bitmap.createBitmap(IMAGE_WIDTH, IMAGE_HEIGHT, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }


    private class WechatCircleShareDialog extends BaseDialog implements View.OnClickListener {
        private Activity activity;
        private TextView tv_cancel, tv_open;
        private String[] hehe;
        private String name;

        public WechatCircleShareDialog(Context context, String[] hehe, String name) {
            super(context);
            activity = (Activity) context;
            this.hehe = hehe;
            this.name = name;
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

    private boolean permission(String[] permission) {
        int permissipnNum = 0;
        if (permission.length > 0) {
            for (int i = 0; i < permission.length; i++) {
                if (ContextCompat.checkSelfPermission(getActivity(), permission[i]) == PackageManager.PERMISSION_GRANTED) {
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 111:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    showShareDialog(sharePic, shareName, mId);
                } else {
                    Toast.makeText(getActivity(), "为了您能正常使用，请开启权限", Toast.LENGTH_SHORT).show();
                }
                break;
            case 222:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    shareBitmapDialog(sharePicUrl, shareQrcodeUrl, shareName, mId);
                } else {
                    Toast.makeText(getActivity(), "为了您能正常使用，请开启权限", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    private void showShareDialog(final String[] hehe, final String name, String id) {
        ShareDialogView shareDialogView = new ShareDialogView(getActivity(), new ShareDialogView.CallBack() {
            @Override
            public void weixinhaoyou() {
                if (hehe != null && hehe.length > 0) {
                    if (hehe.length == 1) {
                        IWXAPI iwxapi1 = WXAPIFactory.createWXAPI(getActivity(), ConstantUtil.WXAPP_ID);
                        if (iwxapi1.isWXAppInstalled()) {
                            UMImage umImages = new UMImage(getActivity(), hehe[0]);
                            new ShareAction(getActivity())
                                    .setPlatform(SHARE_MEDIA.WEIXIN)
                                    .withMedias(umImages)
                                    .setCallback(new MyUMShareListener())
                                    .share();
                        } else {
                            ToastUtil.show(getActivity(), "未安装微信");
                        }


                    } else {
                        //多图分享
                        IWXAPI iwxapi1 = WXAPIFactory.createWXAPI(getActivity(), ConstantUtil.WXAPP_ID);
                        if (iwxapi1.isWXAppInstalled()) {
                            downPic = 0;
                            mLocalLists = new ArrayList<>();
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    for (int i = 0; i < hehe.length; i++) {
                                        savePicTo(hehe[i], 1, hehe.length);
                                    }
                                }
                            }).start();
                        } else {
                            ToastUtil.show(getActivity(), "未安装微信");
                        }
                    }
                }

            }

            @Override
            public void pengyouquan() {

                if (hehe != null && hehe.length > 0) {
                    IWXAPI iwxapi1 = WXAPIFactory.createWXAPI(getActivity(), ConstantUtil.WXAPP_ID);
                    if (iwxapi1.isWXAppInstalled()) {
                        downPic = 0;
                        mLocalLists = new ArrayList<>();

                        ClipboardManager cm = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                        // 将文本内容放到系统剪贴板里。
                        cm.setText(name);
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                for (int i = 0; i < hehe.length; i++) {
                                    saveImageToCamera(getActivity(), hehe[i], hehe, name, true);
                                }
                            }
                        }).start();
                    } else {
                        ToastUtil.show(getActivity(), "未安装微信");
                    }

                }


            }

            @Override
            public void qqhaoyou() {
                if (hehe != null && hehe.length > 0) {
                    if (hehe.length == 1) {
                        UMImage umImages = new UMImage(getActivity(), hehe[0]);
                        new ShareAction(getActivity())
                                .setPlatform(SHARE_MEDIA.QQ)
                                .withMedias(umImages)
                                .setCallback(new MyUMShareListener())
                                .share();
                    } else {
                        //多图分享
                        downPic = 0;
                        mLocalLists = new ArrayList<>();
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                for (int i = 0; i < hehe.length; i++) {
                                    savePicTo(hehe[i], 3, hehe.length);
                                }
                            }
                        }).start();
                    }
                }


            }

            @Override
            public void qqkongjian() {
                ClipboardManager cm = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                // 将文本内容放到系统剪贴板里。
                cm.setText(name);
                if (hehe != null && hehe.length > 0) {
                    if (hehe.length == 1) {
                        UMImage umImages = new UMImage(getActivity(), hehe[0]);
                        new ShareAction(getActivity())
                                .setPlatform(SHARE_MEDIA.QZONE)
                                .withMedias(umImages)
                                .setCallback(new MyUMShareListener())
                                .share();
                    } else {
                        UMImage[] umImages = new UMImage[hehe.length];
                        for (int i = 0; i < umImages.length; i++) {
                            UMImage umImage = new UMImage(getActivity(), hehe[i]);
                            umImages[i] = umImage;
                        }
                        new ShareAction(getActivity())
                                .setPlatform(SHARE_MEDIA.QZONE)//传入平台
                                .withText(name)//分享内容
                                .withMedias(umImages)
                                .setCallback(new MyUMShareListener())//回调监听器
                                .share();
                    }
                }
            }

            @Override
            public void xinlangweibo() {
                if (hehe != null && hehe.length > 0) {
                    if (hehe.length == 1) {
                        UMImage umImages = new UMImage(getActivity(), hehe[0]);
                        new ShareAction(getActivity())
                                .setPlatform(SHARE_MEDIA.SINA)
                                .withMedias(umImages)
                                .withText(name)
                                .setCallback(new MyUMShareListener())
                                .share();
                    } else {
                        //多图分享
                        UMImage[] umImages = new UMImage[hehe.length];
                        for (int i = 0; i < umImages.length; i++) {
                            UMImage umImage = new UMImage(getActivity(), hehe[i]);
                            umImages[i] = umImage;
                        }
                        new ShareAction(getActivity())
                                .setPlatform(SHARE_MEDIA.SINA)//传入平台
                                .withText(name)//分享内容
                                .withMedias(umImages)
                                .setCallback(new MyUMShareListener())//回调监听器
                                .share();
                    }
                }
            }

            @Override
            public void save() {
                if (hehe != null && hehe.length > 0) {
                    downPic = 0;
                    mLocalLists = new ArrayList<>();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            for (int i = 0; i < hehe.length; i++) {
                                saveImageToCamera(getActivity(), hehe[i], hehe, name, false);
                            }
                        }
                    }).start();
                }
            }
        }, true);
        shareDialogView.show();
    }

    private void shareBitmapDialog(final String picUrl, final String qrcodeUrl, final String name, String id) {
        ShareDialogView shareDialogView = new ShareDialogView(getActivity(), new ShareDialogView.CallBack() {
            @Override
            public void weixinhaoyou() {
                IWXAPI iwxapi1 = WXAPIFactory.createWXAPI(getActivity(), ConstantUtil.WXAPP_ID);
                if (iwxapi1.isWXAppInstalled()) {
                    haibao(picUrl, qrcodeUrl, SHARE_MEDIA.WEIXIN, 1);
                } else {
                    ToastUtil.show(getActivity(), "未安装微信");
                }
            }

            @Override
            public void pengyouquan() {
                IWXAPI iwxapi1 = WXAPIFactory.createWXAPI(getActivity(), ConstantUtil.WXAPP_ID);
                if (iwxapi1.isWXAppInstalled()) {
                    haibao(picUrl, qrcodeUrl, SHARE_MEDIA.WEIXIN_CIRCLE, 1);
                } else {
                    ToastUtil.show(getActivity(), "未安装微信");
                }
            }

            @Override
            public void qqhaoyou() {
                haibao(picUrl, qrcodeUrl, SHARE_MEDIA.QQ, 1);
            }

            @Override
            public void qqkongjian() {
                haibao(picUrl, qrcodeUrl, SHARE_MEDIA.QZONE, 1);
            }

            @Override
            public void xinlangweibo() {
                haibao(picUrl, qrcodeUrl, SHARE_MEDIA.SINA, 1);
            }

            @Override
            public void save() {
                haibao(picUrl, qrcodeUrl, SHARE_MEDIA.WEIXIN, 2);
            }
        }, true);
        shareDialogView.show();
    }

}
