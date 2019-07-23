package com.d2956987215.mow.activity.mine;

import android.Manifest;
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
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.d2956987215.mow.R;
import com.d2956987215.mow.activity.BaseActivity;
import com.d2956987215.mow.activity.home.TaoBaoDetailActivity;
import com.d2956987215.mow.adapter.HomeShouCangAdapter;
import com.d2956987215.mow.dialog.DelCollectDialog;
import com.d2956987215.mow.listener.IShowData;
import com.d2956987215.mow.rxjava.Request;
import com.d2956987215.mow.rxjava.Result;
import com.d2956987215.mow.rxjava.RxJavaUtil;
import com.d2956987215.mow.rxjava.response.BaseResponse;
import com.d2956987215.mow.rxjava.response.MyFavariteResponse;
import com.d2956987215.mow.util.DisplayUtil;
import com.d2956987215.mow.util.ImageUtils;
import com.d2956987215.mow.util.InitRefreshLayout;
import com.d2956987215.mow.util.MoneyUtils;
import com.d2956987215.mow.util.MyLog;
import com.d2956987215.mow.util.QRCodeUtil;
import com.d2956987215.mow.util.StringUtil;
import com.d2956987215.mow.util.ToastUtil;
import com.d2956987215.mow.util.User;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

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
import butterknife.OnClick;

import static com.d2956987215.mow.constant.Const.type;


public class MyShouCangActivity extends BaseActivity implements IShowData, OnRefreshListener, OnLoadmoreListener {
    @BindView(R.id.tv_bianji)
    TextView tv_bianji;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.iv_choose_all)
    ImageView iv_choose_all;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.rl_bottom_yichu)
    LinearLayout rl_bottom_yichu;
    @BindView(R.id.tv_choose_count)
    TextView tvChooseCount;
    @BindView(R.id.ll_nothing)
    LinearLayout mLlNothing;

//    @BindView(R.id.iv_erweimanew)
//    ImageView iv_erweimanew;
//    @BindView(R.id.iv_newpic)
//    ImageView iv_newpic;
//    @BindView(R.id.tv_name)
//    TextView tv_name;
//    @BindView(R.id.tv_quancount)
//    TextView tv_quancount;
//    @BindView(R.id.tv_yishou)
//    TextView tv_yishou;
//    @BindView(R.id.tv_nowprice)
//    TextView tv_nowprice;
//    @BindView(R.id.tv_total)
//    TextView tv_total;


    private HomeShouCangAdapter hotAdapter;
    private boolean chooseAll = true;
    private int p = 1;
    private String ids;
    private List<MyFavariteResponse.DataBean> goodlist = new ArrayList<>();
    private List<MyFavariteResponse.DataBean> tempGoodlist = new ArrayList<>();

    ArrayList<Uri> mTemp = new ArrayList<>();
    private boolean mCodeShare = false;

    private String[] permisssions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InitRefreshLayout.initRefreshLayout(refreshLayout, MyShouCangActivity.this, true);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setOnLoadmoreListener(this);
        huoqu();


    }

    private View initEmptyView() {
        View emptyView = inflate(R.layout.custom_empty_view);
        ImageView iv = emptyView.findViewById(R.id.empty_retry_view);
        TextView tv = emptyView.findViewById(R.id.empty_view_tv);
        iv.setBackgroundResource(R.drawable.collect_img_default);
        tv.setText("你还没有收藏商品，快去逛逛吧");
        return emptyView;
    }


    private LayoutInflater inflater;

    private View inflate(@LayoutRes int resource) {
        if (inflater == null) {
            inflater = LayoutInflater.from(this);
        }
        return inflater.inflate(resource, null);
    }

    private void huoqu() {
        Map<String, String> map = new HashMap<>();
        map.put("user_id", User.uid() + "");
        map.put("page", p + "");
        new Request<MyFavariteResponse>().request(RxJavaUtil.xApi().shoucanglist(map, "Bearer " + User.token()), "我的收藏", this, false, new Result<MyFavariteResponse>() {
            @Override
            public void get(MyFavariteResponse response) {
                if (p == 1) {
                    refreshLayout.finishRefresh();
                } else {
                    refreshLayout.finishLoadmore();
                }
                if (response.getData() != null && response.getData().size() > 0) {
                    rl_bottom_yichu.setVisibility(View.VISIBLE);
                    List<MyFavariteResponse.DataBean> list = response.getData();
                    if (list != null && list.size() > 0) {
                        initHotRecycler1(list);
                    }

                } else {
                    if (p == 1) {
                        rl_bottom_yichu.setVisibility(View.GONE);
                        refreshLayout.setVisibility(View.GONE);
                        tv_bianji.setVisibility(View.GONE);
                        mLlNothing.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onBackErrorMessage(MyFavariteResponse response) {
                super.onBackErrorMessage(response);
                if (p == 1) {
                    rl_bottom_yichu.setVisibility(View.GONE);
                    refreshLayout.setVisibility(View.GONE);
                    tv_bianji.setVisibility(View.GONE);
                    mLlNothing.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    private void initHotRecycler1(final List<MyFavariteResponse.DataBean> list) {
        if (hotAdapter == null) {
            hotAdapter = new HomeShouCangAdapter(R.layout.adapter_hot_shoucang, goodlist);
            int spanCount = 1; // 只显示一行
            hotAdapter.setClickButtonListener(new HomeShouCangAdapter.onClickQuxiaoListener() {
                @Override
                public void xuanqu(String a, String size) {
                    MyLog.e("tagshuju", a);
                    if (Integer.parseInt(size) > 0 && Integer.parseInt(size) >= goodlist.size()) {
                        Glide.with(MyShouCangActivity.this).load(R.mipmap.xuanzhong).into(iv_choose_all);
                    } else {
                        Glide.with(MyShouCangActivity.this).load(R.mipmap.weixuanzhong).into(iv_choose_all);
                    }
                    tvChooseCount.setText("全选（" + size + "）");

                }
            });
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);

            recyclerView.setLayoutManager(layoutManager);

            recyclerView.setItemViewCacheSize(10);
            recyclerView.setItemAnimator(null);
            hotAdapter.setHasStableIds(true);
            recyclerView.setNestedScrollingEnabled(false);
            recyclerView.setAdapter(hotAdapter);
        }
        if (p == 1) {
            goodlist.clear();
        }
        goodlist.addAll(list);
        hotAdapter.notifyDataSetChanged();
        hotAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(MyShouCangActivity.this, TaoBaoDetailActivity.class);
                intent.putExtra("id", goodlist.get(position).getGoods_id() + "");
                intent.putExtra("quan_id", goodlist.get(position).getQuan_id() + "");
                intent.putExtra("type", type);
                startActivity(intent);


            }
        });
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_myshoucang;
    }

    @Override
    public void show(Object obj) {

    }

    List<String> shopid = new ArrayList<>();
    private String laizi = "1";
    private int geshu = 0;
    private ArrayList<MyFavariteResponse.DataBean> selected;

    @OnClick({R.id.tv_bianji, R.id.ll_choose_all, R.id.tv_share_wechat, R.id.tv_share_friend_cycle})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_bianji:
                ArrayList<String> selectedItem = null;
                if (hotAdapter != null) {
                    selectedItem = hotAdapter.getSelectedItem();
                    if (selectedItem == null || selectedItem.size() == 0) {
                        ToastUtil.show(MyShouCangActivity.this, "请至少选择一项");
                        return;
                    }
                }
                final ArrayList<String> finalSelectedItem = selectedItem;
                DelCollectDialog dialog = new DelCollectDialog(this, new DelCollectDialog.CallBack() {
                    @Override
                    public void NO() {

                        Map<String, String> map = new HashMap<>();
                        map.put("user_id", User.uid() + "");
                        map.put("goods_id", new Gson().toJson(finalSelectedItem));
                        new Request<BaseResponse>().request(RxJavaUtil.xApi().deleteshoucang(map, "Bearer " + User.token()), "删除收藏", MyShouCangActivity.this, false, new Result<BaseResponse>() {
                            @Override
                            public void get(BaseResponse response) {
                                ToastUtil.show(MyShouCangActivity.this, response.message);
                                hotAdapter.clearItemChecked();
                                tvChooseCount.setText("全选");
                                p = 1;
                                huoqu();
                            }
                        });
                    }
                });
                dialog.show();

                break;
            case R.id.ll_choose_all:
                if (goodlist == null || goodlist.size() == 0) {
                    ToastUtil.show(MyShouCangActivity.this, "暂无商品");
                    break;
                }
                if (shopid != null) {
                    shopid.clear();
                }
                chooseAll = !chooseAll;

                if (chooseAll) {
                    Glide.with(this).load(R.mipmap.weixuanzhong).into(iv_choose_all);
                    for (int i = 0; i < goodlist.size(); i++) {
                        for (MyFavariteResponse.DataBean item : goodlist) {
                            item.setChoose(false);
                            hotAdapter.setItemChecked(i, false);

                        }
                    }
                    tvChooseCount.setText("全选");
                } else {
                    Glide.with(this).load(R.mipmap.xuanzhong).into(iv_choose_all);
                    for (int i = 0; i < goodlist.size(); i++) {
                        for (MyFavariteResponse.DataBean item : goodlist) {
                            item.setChoose(true);
                            hotAdapter.setItemChecked(i, true);
                            shopid.add(goodlist.get(i).getId() + "");

                        }
                    }
                    String str = shopid.toString();
                    int len = str.length() - 1;
                    ids = str.substring(1, len).replace(" ", "");
                    tvChooseCount.setText("全选（" + goodlist.size() + "）");
                }

                hotAdapter.notifyDataSetChanged();
                break;
            case R.id.tv_share_friend_cycle:  //分享微信群
            case R.id.tv_share_wechat:  //分享微信群
                if (permission(permisssions)) {
                    showShareDialog();
                } else {
                    ActivityCompat.requestPermissions(MyShouCangActivity.this, permisssions, 111);
                }

                break;
        }

    }

    private void showShareDialog() {
        if (goodlist == null || goodlist.size() == 0) {
            ToastUtil.show(MyShouCangActivity.this, "暂无商品");
            return;
        }
//        if (goodlist.size() > 9) {
//            ToastUtils.showShort("最多分享9张");
//            return;
//        }

        tempGoodlist.clear();
        for (int i = 0; i < goodlist.size(); i++) {
            if (goodlist.get(i).isChoose())
                tempGoodlist.add(goodlist.get(i));

        }
        if (tempGoodlist == null || tempGoodlist.size() == 0) {
            ToastUtil.show(MyShouCangActivity.this, "请选择商品");
            return;
        } else if (tempGoodlist.size() > 9) {
            ToastUtils.showShort("最多分享9张");
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(MyShouCangActivity.this).setTitle("选择分享")
                .setItems(new String[]{"商品分享", "二维码分享"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (mTemp.size() > 0)
                            mTemp.clear();

                        switch (i) {
                            case 0:
                                mCodeShare = false;
                                save(tempGoodlist);
                                break;
                            case 1:
                                mCodeShare = true;
                                save(tempGoodlist);
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


    //保存图片
    private void save(final List<MyFavariteResponse.DataBean> article_goods) {
        if (mCodeShare) {
            handler.sendEmptyMessage(1);
        } else {
            //保存图片必须在子线程中操作，是耗时操作
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int j = 0; j < article_goods.size(); j++) {
                        savePicTo(article_goods.get(j).getGoods_pic(), j);
                    }
                }
            }).start();
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

        mTemp.add(Uri.fromFile(file));
        if (mTemp.size() == tempGoodlist.size()) {
            handler.sendEmptyMessage(0);
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                share(mTemp);
            } else if (msg.what == 1) {
                //二维码保存
                if (tempGoodlist != null && tempGoodlist.size() > 0) {
                    if (tempGoodlist.size() > mTemp.size()) {
                        initView1(tempGoodlist.get(mTemp.size()), mTemp.size());
                    }

                }
            }
        }
    };


    private void share(ArrayList<Uri> uriList) {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND_MULTIPLE);
        shareIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uriList);
        shareIntent.setType("image/*");
        startActivity(Intent.createChooser(shareIntent, "分享到"));
    }

    private void initView(MyFavariteResponse.DataBean article_goods, int j) {

        MyFavariteResponse.DataBean shareData = article_goods;
        final View view = findViewById(R.id.ll_layout);
        ImageView iv_newpic = (ImageView) view.findViewById(R.id.iv_newpic);
        // Glide.with(this).load(article_goods.getGoods_pic()).error(R.mipmap.da_tu).into(iv_newpic);
        ImageView iv_erweimanew = view.findViewById(R.id.iv_erweimanew);
        Bitmap bmp = QRCodeUtil.createQRCodeBitmap(shareData.getEurl(), 400);
        iv_erweimanew.setImageBitmap(bmp);
        TextView tv_name = (TextView) view.findViewById(R.id.tv_name);
        TextView tv_quancount = (TextView) view.findViewById(R.id.tv_quancount);
        TextView tv_yishou = (TextView) view.findViewById(R.id.tv_yishou);
        TextView tv_nowprice = (TextView) view.findViewById(R.id.tv_nowprice);
        TextView tv_total = (TextView) view.findViewById(R.id.tv_total);


        Resources resources = getResources();
        Drawable imgdrawable;
        if (article_goods.getShoptype().equals("B")) {
            imgdrawable = resources.getDrawable(R.mipmap.icon_tianmao);
        } else {
            imgdrawable = resources.getDrawable(R.mipmap.icon_taobao);
        }
        SpannableString spannableString = new SpannableString("  " + shareData.getGoods_title());//textView控件
        imgdrawable.setBounds(0, 0, imgdrawable.getMinimumWidth(), imgdrawable.getMinimumHeight());//左上右下
        ImageSpan imageSpan = new ImageSpan(imgdrawable);
        spannableString.setSpan(imageSpan, 0, 1, ImageSpan.ALIGN_BASELINE);
        tv_name.setText(spannableString);

        tv_quancount.setText(shareData.getGoods_quan() + "元");
        tv_yishou.setText("月销：" + shareData.getGoods_sale());
        tv_nowprice.setText(shareData.getGoods_endprice());
        tv_total.setText("原价￥" + shareData.getGoods_price());
//        showProgress();
        Log.d("zzz", shareData.getGoods_quan() + "--" + shareData.getGoods_sale() + "--" + shareData.getGoods_endprice() + "--" + shareData.getGoods_price());

        final Bitmap bmps = loadBitmapFromView(view);
        savePicture(bmps, DisplayUtil.getCurrTime() + j + ".png");// 保存图片
        view.destroyDrawingCache(); // 保存过后释放资源
    }

    private void initView1(MyFavariteResponse.DataBean article_goods, final int j) {
        final View view = findViewById(R.id.ll_layout);
        final ImageView iv_newpic = (ImageView) findViewById(R.id.iv_newpic);
        ImageView iv_erweimanew = (ImageView) findViewById(R.id.iv_erweimanew);
        Bitmap bmp = QRCodeUtil.createQRCodeBitmap(article_goods.getEurl(), 400);
        iv_erweimanew.setImageBitmap(bmp);
        TextView tv_name = (TextView) findViewById(R.id.tv_name);
        TextView tv_quancount = (TextView) findViewById(R.id.tv_quancount);
        TextView tv_yishou = (TextView) findViewById(R.id.tv_yishou);
        TextView tv_nowprice = (TextView) findViewById(R.id.tv_nowprice);
        TextView tv_total = (TextView) findViewById(R.id.tv_total);

        Resources resources = getResources();
        Drawable imgdrawable;
        if (article_goods.getShoptype().equals("B")) {
            imgdrawable = resources.getDrawable(R.mipmap.icon_tianmao);
        } else {
            imgdrawable = resources.getDrawable(R.mipmap.icon_taobao);
        }
        SpannableString spannableString = new SpannableString("  " + article_goods.getGoods_title());//textView控件
        imgdrawable.setBounds(0, 0, imgdrawable.getMinimumWidth(), imgdrawable.getMinimumHeight());//左上右下
        ImageSpan imageSpan = new ImageSpan(imgdrawable);
        spannableString.setSpan(imageSpan, 0, 1, ImageSpan.ALIGN_BASELINE);
        tv_name.setText(spannableString);


        tv_quancount.setText(article_goods.getGoods_quan() + "元");
        tv_yishou.setText("月销：" + (StringUtil.isBlank(article_goods.getGoods_sale()) ? 0 : MoneyUtils.getMoney(article_goods.getGoods_sale())));
        tv_nowprice.setText(article_goods.getGoods_endprice());
        tv_total.setText("原价￥" + article_goods.getGoods_price());
        Glide.with(this).load(article_goods.getGoods_pic()).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                if (resource != null) {
                    iv_newpic.setImageBitmap(resource);
                    final Bitmap bmps = loadBitmapFromView(view);
                    savePicture(bmps, DisplayUtil.getCurrTime() + String.valueOf(j) + ".jpg");// 保存图片
                    view.destroyDrawingCache(); // 保存过后释放资源
                }
            }
        });
    }


    public void savePicture(Bitmap bm, String fileName) {

        if (bm == null) {
            Toast.makeText(MyShouCangActivity.this, "savePicture null !", Toast.LENGTH_SHORT).show();
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
            bm.compress(Bitmap.CompressFormat.JPEG, 90, fos);
            fos.flush();
            fos.close();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                mTemp.add(getImageContentUri(MyShouCangActivity.this, myCaptureFile));
            } else {
                mTemp.add(Uri.fromFile(myCaptureFile));
            }

            if (mTemp.size() == tempGoodlist.size()) {
                handler.sendEmptyMessage(0);
            } else {
                handler.sendEmptyMessage(1);
            }

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
    public void onLoadmore(RefreshLayout refreshlayout) {
        p++;
        huoqu();
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        p = 1;
        huoqu();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 111:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    showShareDialog();
                } else {
                    Toast.makeText(MyShouCangActivity.this, "为了您能正常使用，请开启权限", Toast.LENGTH_SHORT).show();
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
                if (ContextCompat.checkSelfPermission(MyShouCangActivity.this, permission[i]) == PackageManager.PERMISSION_GRANTED) {
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

    private void savePicTo(String picUrl, int j) {
        try {
            Bitmap myBitmap = ImageUtils.getBitMBitmap(picUrl);
            // 首先保存图片
            File appDir = new File("/sdcard/maishoumama");
            if (!appDir.exists()) {
                appDir.mkdir();
            }
            String fileName = DisplayUtil.getCurrTime() + String.valueOf(j) + ".jpg";
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
                mTemp.add(getImageContentUri(MyShouCangActivity.this, file));
            } else {
                mTemp.add(Uri.fromFile(file));

            }

            if (mTemp.size() == tempGoodlist.size()) {
                handler.sendEmptyMessage(0);
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


}
