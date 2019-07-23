package com.d2956987215.mow.activity.luntan;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
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
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.d2956987215.mow.R;
import com.d2956987215.mow.activity.SplashActivity;
import com.d2956987215.mow.activity.login.LoginNewActivity;
import com.d2956987215.mow.activity.mine.MessageSetActivity;
import com.d2956987215.mow.adapter.ArticleAdapter;
import com.d2956987215.mow.adapter.FuJinErJiAdapter;
import com.d2956987215.mow.adapter.LunTanAdapter;
import com.d2956987215.mow.dialog.BaseDialog;
import com.d2956987215.mow.dialog.ShareDialogView;
import com.d2956987215.mow.listener.IShowData;
import com.d2956987215.mow.rxjava.Request;
import com.d2956987215.mow.rxjava.Result;
import com.d2956987215.mow.rxjava.RxJavaUtil;
import com.d2956987215.mow.rxjava.response.ArticleResponse;
import com.d2956987215.mow.rxjava.response.BaseResponse;
import com.d2956987215.mow.rxjava.response.LanMuListResponse;
import com.d2956987215.mow.rxjava.response.LunTanTitleResponse;
import com.d2956987215.mow.util.ActivityUtils;
import com.d2956987215.mow.util.ConstantUtil;
import com.d2956987215.mow.util.DisplayUtil;
import com.d2956987215.mow.util.ImageUtils;
import com.d2956987215.mow.util.MyLog;
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
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class LunTandetailFragment extends Fragment implements IShowData, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.recycler_hot_product)
    RecyclerView recyclerHotProduct;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.tag_first)
    RecyclerView tag_first;
    Unbinder unbinder;

    private int p = 1;
    //    private List<LanMuListResponse.DataBean> sList = new ArrayList<>();
    private String column_id;
    private LunTandetailFragment baseframent;
    private ArrayList<Uri> mCode = new ArrayList<Uri>();
    private ArrayList<Uri> mTemp = new ArrayList<Uri>();
    private ArrayList<Uri> mCodeShareData = new ArrayList<Uri>();
    private int mShareData;
    private ImageView iv_newpic;
    private boolean mCodeShare = false;
    private List<LanMuListResponse.DataBean.ArticleGoodsBean> mArticleGoods;
    private String shareName, mId;
    private String[] sharePic;
    private LanMuListResponse.DataBean.ArticleGoodsBean shareItem;
    private LunTanAdapter hotAdapter;
    private ArticleAdapter mArticleAdapter;
    private String[] permisssions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};


    public LunTandetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_luntandetail, container, false);
        baseframent = this;
        unbinder = ButterKnife.bind(this, view);

        listtitle();
        refreshLayout.setOnRefreshListener(this);
        showRefresh();
        return view;
    }

    private void showRefresh() {
//        try {
//            refreshLayout.post(new Runnable() {
//                @Override
//                public void run() {
//                    refreshLayout.setRefreshing(true);
//                }
//            });
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        refreshLayout.setRefreshing(true);
    }

    private FuJinErJiAdapter goodAdapter;


    public void listtitle() {

        new Request<LunTanTitleResponse>().request(RxJavaUtil.xApi1().getDayHotHeader(), "论坛头列表", getActivity(), false, new Result<LunTanTitleResponse>() {
            @Override
            public void get(final LunTanTitleResponse response) {
                if (response.getData() != null) {
                    goodAdapter = new FuJinErJiAdapter(R.layout.adapter_textfujin, response.getData());
                    int spanCount = 1; // 只显示一行
                    StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(spanCount, StaggeredGridLayoutManager.HORIZONTAL);
                    tag_first.setLayoutManager(layoutManager);
                    tag_first.setHasFixedSize(true);
                    tag_first.setNestedScrollingEnabled(false);
                    tag_first.setAdapter(goodAdapter);
                    column_id = response.getData().get(0).getCat_id();
                    huoqulist();
                    goodAdapter.setSelectIndex(0);
                    goodAdapter.notifyDataSetChanged();
                    goodAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                            p = 1;
                            goodAdapter.setSelectIndex(position);
                            goodAdapter.notifyDataSetChanged();
                            column_id = response.getData().get(position).getCat_id();
                            showRefresh();
                            if (column_id.equals("4")) {
                                articlelist();
                            } else {
                                huoqulist();
                            }
                        }
                    });

                }


            }
        });


    }

    private void huoqulist() {
        Map<String, String> map = new HashMap<>();
        map.put("cat_id", column_id);
        map.put("user_id", User.uid() + "");
        map.put("page", p + "");
        new Request<LanMuListResponse>().request(RxJavaUtil.xApi1().getDayHotList(map), "每日爆款列表", getActivity(), false, new Result<LanMuListResponse>() {
            @Override
            public void get(LanMuListResponse response) {
                if (p == 1) {
                    refreshLayout.setRefreshing(false);
                } else {
                    hotAdapter.loadMoreComplete();
                }
                if (response.getData() != null && response.getData().size() > 0) {
                    List<LanMuListResponse.DataBean> list = response.getData();

                    if (p == 1) {
                        recyclerHotProduct.setLayoutManager(new LinearLayoutManager(getActivity()));
                        hotAdapter = new LunTanAdapter(R.layout.item_luntan, list, getActivity());
                        recyclerHotProduct.setAdapter(hotAdapter);
                    } else {
                        hotAdapter.addData(response.getData());
                    }
                }

                hotAdapter.setClickButtonListener(new LunTanAdapter.onClickShareListener() {

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
//                            Intent intent = new Intent(getActivity(), SplashActivity.class);
//                            Intent intent = new Intent(getActivity(), LoginNewActivity.class);
//                            getActivity().startActivity(intent);
                            ActivityUtils.startLoginAcitivy(getActivity());
                            return;
                        }

                    }

                    @Override
                    public void shareBitmap(final LanMuListResponse.DataBean.ArticleGoodsBean item, final String name) {
                        mId = String.valueOf(item.getId());
                        shareName = name;
                        shareItem = item;
                        if (User.uid() > 0) {
                            if (permission(permisssions)) {
                                shareBitmapDialog(item, name);
                            } else {
                                requestPermissions(permisssions, 222);
                            }

                        } else {
                            ToastUtil.show(getActivity(), "请先登录");
//                            Intent intent = new Intent(getActivity(), SplashActivity.class);
//                            Intent intent = new Intent(getActivity(), LoginNewActivity.class);
//                            getActivity().startActivity(intent);

                            ActivityUtils.startLoginAcitivy(getActivity());
                            return;
                        }


                    }
                });


                hotAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
                    @Override
                    public void onLoadMoreRequested() {
                        p++;
                        huoqulist();
                    }
                }, recyclerHotProduct);

            }


            @Override
            public void onServerError(String errDesc) {
                super.onServerError(errDesc);
            }

            @Override
            public void onBackErrorMessage(LanMuListResponse response) {
                super.onBackErrorMessage(response);
            }
        });

    }


    private void articlelist() {
        Map<String, String> map = new HashMap<>();
        map.put("cat_id", column_id);
        map.put("user_id", User.uid() + "");
        map.put("page", p + "");
        map.put("page_size", "20");
        new Request<ArticleResponse>().request(RxJavaUtil.xApi1().getDayHotArticleList(map), "每日爆款列表", getActivity(), false, new Result<ArticleResponse>() {
            @Override
            public void get(ArticleResponse response) {
                if (p == 1) {
                    refreshLayout.setRefreshing(false);
                } else {
                    mArticleAdapter.loadMoreComplete();
                }
                if (response.getData() != null && response.getData().size() > 0) {
                    if (p == 1) {
                        recyclerHotProduct.setLayoutManager(new LinearLayoutManager(getActivity()));
                        mArticleAdapter = new ArticleAdapter(R.layout.item_article, response.getData());
                        recyclerHotProduct.setAdapter(mArticleAdapter);
                    } else {
                        mArticleAdapter.addData(response.getData());
                    }

                    if (response.getData().size() == 20) {
                        mArticleAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
                            @Override
                            public void onLoadMoreRequested() {
                                p++;
                                articlelist();
                            }
                        }, recyclerHotProduct);
                    } else {
                        mArticleAdapter.addFooterView(LayoutInflater.from(getActivity()).inflate(R.layout.footer_layout, null));
                    }
                }
            }

        });

    }


    private void showShareDialog(final List<LanMuListResponse.DataBean.ArticleGoodsBean> article_goods) {
        mArticleGoods = article_goods;
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()).setTitle("选择分享")
                .setItems(new String[]{"商品分享", "二维码分享"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mShareData = article_goods.size();

                        if (mCode.size() > 0)
                            mCode.clear();
                        if (mTemp.size() > 0)
                            mTemp.clear();
                        if (mCodeShareData.size() > 0)
                            mCodeShareData.clear();
                        ToastUtils.showShort("正在生成请稍候!");
                        switch (i) {
                            case 0:
                                mCodeShare = false;
                                save(article_goods);
                                break;
                            case 1:
                                mCodeShare = true;
                                save(article_goods);

//                                for (int j = 0; j < article_goods.size(); j++)
//                                    initView(article_goods.get(j));
//                                if (mCode.size() > 0)
//                                    share(mCode);
//                                else
//                                    ToastUtils.showShort("分享失败");
                                break;
                        }
                    }
                }).setPositiveButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

        builder.show();


    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            for (int j = 0; j < mArticleGoods.size(); j++)
                initView(mArticleGoods.get(j), j);
            if (mCodeShareData.size() > 0)
                share(mCodeShareData);
            else
                ToastUtils.showShort("分享失败");
        }
    };


    //保存图片
    private void save(final List<LanMuListResponse.DataBean.ArticleGoodsBean> article_goods) {
        //保存图片必须在子线程中操作，是耗时操作
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int j = 0; j < article_goods.size(); j++) {
                    Bitmap bp = GetImageInputStream(article_goods.get(j).getItempic());
                    saveImageToPhotos(getActivity(), bp);
                }


            }
        }).start();
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


    public static final String ROOTPATH = Environment.getExternalStorageDirectory().getPath();
    public static final String DOWNLOAD_DIR = ROOTPATH + "/download/";

    /**
     * 保存二维码到本地相册
     */
    private void saveImageToPhotos(Context context, Bitmap bmp) {
        // 首先保存图片
        File appDir = new File(DOWNLOAD_DIR, "");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = TimeUtils.getNowMills() + "maishoumama.jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mCode.add(Uri.fromFile(file));
        mTemp.add(Uri.fromFile(file));
        if (mCode.size() == mShareData) {
            if (mCodeShare) {
                handler.sendEmptyMessage(0);
            } else {
                share(mCode);
            }
        }
    }


    private void share(ArrayList<Uri> uriList) {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND_MULTIPLE);
        shareIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uriList);
        shareIntent.setType("image/*");
        startActivity(Intent.createChooser(shareIntent, "分享到"));
        shareRequest();
    }

    private void initView(LanMuListResponse.DataBean.ArticleGoodsBean article_goods, int j) {
        final LanMuListResponse.DataBean.ArticleGoodsBean shareData = article_goods;
        final View view = getActivity().findViewById(R.id.ll_layout);
        iv_newpic = (ImageView) view.findViewById(R.id.iv_newpic);
        iv_newpic.setImageURI(mTemp.get(j));
        ImageView iv_erweimanew = (ImageView) view.findViewById(R.id.iv_erweimanew);
        Bitmap bmp = QRCodeUtil.createQRCodeBitmap(shareData.getEurl(), 400);
        iv_erweimanew.setImageBitmap(bmp);
        TextView tv_name = (TextView) view.findViewById(R.id.tv_name);
        TextView tv_quancount = (TextView) view.findViewById(R.id.tv_quancount);
        TextView tv_yishou = (TextView) view.findViewById(R.id.tv_yishou);
        TextView tv_nowprice = (TextView) view.findViewById(R.id.tv_nowprice);
        TextView tv_total = (TextView) view.findViewById(R.id.tv_total);

        if (article_goods.getShoptype().equals("B"))
            tv_name.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.icon_tianmao, 0, 0, 0);
        else
            tv_name.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.icon_taobao, 0, 0, 0);

        tv_name.setText(shareData.getItemtitle());
        tv_quancount.setText(shareData.getCouponmoney() + "元");
        tv_yishou.setText(shareData.getVolume());
        String nowprice = "券后￥" + shareData.getZk_final_price();
        tv_nowprice.setText(nowprice);
        tv_total.setText("￥" + shareData.getEndprice());
        String endPrice = "券后" + shareData.getEndprice() + "";
        tv_nowprice.setText(endPrice);
        tv_total.setText("￥" + shareData.getZk_final_price());


        final Bitmap bmps = loadBitmapFromView(view);
        savePicture(bmps, shareData.getItemtitle() + "shareimg.png");// 保存图片
        view.destroyDrawingCache(); // 保存过后释放资源
    }

    public void savePicture(Bitmap bm, String fileName) {

        if (bm == null) {
            Toast.makeText(getActivity(), "savePicture null !", Toast.LENGTH_SHORT).show();
            MyLog.e("SSSSSSSSSSS", "savePicture: savePicture null ");
            return;
        }

        File foder = new File("/sdcard/maishoumama");
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
            mCodeShareData.add(Uri.fromFile(myCaptureFile));

        } catch (IOException e) {
            e.printStackTrace();
        }
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


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @SuppressWarnings("unchecked")
    @Override
    public void show(Object obj) {

    }

    @Override
    public void onRefresh() {
        p = 1;
        if (column_id.equals("4")) {
            articlelist();
        } else {
            huoqulist();
        }
    }

    private void shareRequest() {
        Map<String, String> map = new HashMap<>();
        map.put("id", mId);
        new Request<BaseResponse>().request(RxJavaUtil.xApi().buyQuanShareNum(map), "分享回调", getActivity(), false, new Result<BaseResponse>() {
            @Override
            public void get(BaseResponse response) {


            }
        });

    }

    /**
     * 保存到系统相册
     */
    private ArrayList<Uri> mLocalLists = new ArrayList<>();//保存到本地的图片路径
    private int downPic = 0;

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


    private void haibao(LanMuListResponse.DataBean.ArticleGoodsBean goodsBean, final SHARE_MEDIA sharePlatfrom, final int type) {
        final int imgWidth = (DisplayUtil.getScreenWidth(getActivity()) - DisplayUtil.dip2px(getActivity(), 92)) / 3 * 2 - DisplayUtil.dip2px(getActivity(), 14);
        final View view = LayoutInflater.from(getActivity()).inflate(R.layout.layout_haibao, null);
        final ImageView goodsPic = view.findViewById(R.id.goodsPic);
        final ImageView iv_erweimanew = view.findViewById(R.id.iv_erweimanew);
        TextView tv_name = view.findViewById(R.id.tv_name);
        TextView tv_yishou = view.findViewById(R.id.tv_yishou);
        TextView tv_total = view.findViewById(R.id.tv_total);
        TextView tv_quancount = view.findViewById(R.id.tv_quancount);
        TextView tv_nowprice = view.findViewById(R.id.tv_nowprice);
        final ScrollView scroll = view.findViewById(R.id.scroll);
        Bitmap bmp = QRCodeUtil.createQRCodeBitmap(goodsBean.getEurl(), 400);
        iv_erweimanew.setImageBitmap(bmp);

        Resources resources = getActivity().getResources();
        Drawable imgdrawable;
        if (goodsBean.getShoptype().equals("B")) {
            imgdrawable = resources.getDrawable(R.mipmap.icon_tianmao);
        } else {
            imgdrawable = resources.getDrawable(R.mipmap.icon_taobao);
        }
        SpannableString spannableString = new SpannableString("  " + goodsBean.getItemtitle());//textView控件
        imgdrawable.setBounds(0, 0, imgdrawable.getMinimumWidth(), imgdrawable.getMinimumHeight());//左上右下
        ImageSpan imageSpan = new ImageSpan(imgdrawable);
        spannableString.setSpan(imageSpan, 0, 1, ImageSpan.ALIGN_BASELINE);
        tv_name.setText(spannableString);

        tv_quancount.setText(goodsBean.getCouponmoney() + "元");
        tv_yishou.setText("月销：" + goodsBean.getVolume());
        tv_nowprice.setText(goodsBean.getEndprice());
        tv_total.setText("原价￥" + goodsBean.getZk_final_price());

        Glide.with(getActivity()).load(goodsBean.getItempic()).asBitmap().into(new SimpleTarget<Bitmap>() {
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
                    shareBitmapDialog(shareItem, shareName);
                } else {
                    Toast.makeText(getActivity(), "为了您能正常使用，请开启权限", Toast.LENGTH_SHORT).show();
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

    private void shareBitmapDialog(final LanMuListResponse.DataBean.ArticleGoodsBean item, final String name) {
        ShareDialogView shareDialogView = new ShareDialogView(getActivity(), new ShareDialogView.CallBack() {
            @Override
            public void weixinhaoyou() {
                IWXAPI iwxapi1 = WXAPIFactory.createWXAPI(getActivity(), ConstantUtil.WXAPP_ID);
                if (iwxapi1.isWXAppInstalled()) {
                    haibao(item, SHARE_MEDIA.WEIXIN, 1);
                } else {
                    ToastUtil.show(getActivity(), "未安装微信");
                }
            }

            @Override
            public void pengyouquan() {
                IWXAPI iwxapi1 = WXAPIFactory.createWXAPI(getActivity(), ConstantUtil.WXAPP_ID);
                if (iwxapi1.isWXAppInstalled()) {
                    haibao(item, SHARE_MEDIA.WEIXIN_CIRCLE, 1);
                } else {
                    ToastUtil.show(getActivity(), "未安装微信");
                }
            }

            @Override
            public void qqhaoyou() {
                haibao(item, SHARE_MEDIA.QQ, 1);
            }

            @Override
            public void qqkongjian() {
                haibao(item, SHARE_MEDIA.QZONE, 1);
            }

            @Override
            public void xinlangweibo() {
                haibao(item, SHARE_MEDIA.SINA, 1);
            }

            @Override
            public void save() {
                haibao(item, SHARE_MEDIA.WEIXIN, 2);
            }
        }, true);
        shareDialogView.show();
    }

}
