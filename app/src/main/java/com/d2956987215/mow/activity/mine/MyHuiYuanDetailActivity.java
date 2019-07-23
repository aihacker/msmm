package com.d2956987215.mow.activity.mine;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.d2956987215.mow.R;
import com.d2956987215.mow.activity.BaseActivity;
import com.d2956987215.mow.activity.home.TaoBaoDetailActivity;
import com.d2956987215.mow.adapter.HuiYuanDetailAdapter;
import com.d2956987215.mow.adapter.HuiYuanDetailTaoCanAdapter;
import com.d2956987215.mow.bean.PayResult;
import com.d2956987215.mow.dialog.PayDialog;
import com.d2956987215.mow.dialog.UpdateDialog;
import com.d2956987215.mow.dialog.UpdateSuccessDialog;
import com.d2956987215.mow.presenter.MinePresenter;
import com.d2956987215.mow.rxjava.response.CurrentStatusResponse;
import com.d2956987215.mow.rxjava.response.VipDetailResponse;
import com.d2956987215.mow.rxjava.response.VipSubmitResponse;
import com.d2956987215.mow.rxjava.response.VipUpdateResponse;
import com.d2956987215.mow.util.DialogUtil;
import com.d2956987215.mow.util.StringUtil;
import com.d2956987215.mow.util.User;
import com.d2956987215.mow.util.WebViewUtils;
import com.d2956987215.mow.widgets.EmptyRecyclerView;
import com.d2956987215.mow.widgets.ShapedImageView;
import com.d2956987215.mow.widgets.adapters.BaseRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


public class MyHuiYuanDetailActivity extends BaseActivity implements WebViewUtils.WebViewInterface {

    //    @BindView(R.id.tv_maishou_no)
//    TextView tvMaishouNo;
//    @BindView(R.id.tv_tishi)
//    TextView tvTishi;
//    @BindView(R.id.erv_left)
//    EmptyRecyclerView ervLeft;
//    @BindView(R.id.erv_right)
//    EmptyRecyclerView ervRight;
//    @BindView(R.id.rl_back)
//    RelativeLayout rlBack;
//    @BindView(R.id.tv_title)
//    TextView tvTitle;
//    @BindView(R.id.tv_submit)
//    TextView tvSubmit;
//    @BindView(R.id.tv_gouwuche)
//    ImageView tvGouwuche;
//    @BindView(R.id.iv_maishou)
//    ShapedImageView ivMaishou;
//    @BindView(R.id.erv_taocan)
//    EmptyRecyclerView erv_taocan;
    @BindView(R.id.webView)
    WebView mWebView;
    //
    @BindView(R.id.tv_invite)
    TextView tvInvite;
//    //
//    @BindView(R.id.tv_huiyuan_price)
//    TextView tvHuiyuanPrice;
//    //
//    @BindView(R.id.ll_recharge_huiyuan)
//    LinearLayout llRechargeHuiyuan;

    @BindView(R.id.tv_update)
    TextView mTvUpdate;

    @BindView(R.id.tv_title)
    TextView tv_title;

    //    @BindView(R.id.tv_name1)
//    TextView tvName1;
//    @BindView(R.id.tv_name2)
//    TextView tvName2;
//    @BindView(R.id.tv_choose_taocan)
    TextView tvChooseTaocan;
    private String roletype;
    private MinePresenter minePresenter;
    private VipDetailResponse vipDetailResponse;
//    private HuiYuanDetailAdapter adapter1;
//    private HuiYuanDetailAdapter adapter2;
//    private HuiYuanDetailTaoCanAdapter taoCanAdapter;
//    private List<VipDetailResponse.DataBean.Explain.Tq> tqList1=new ArrayList<>();
//    private List<VipDetailResponse.DataBean.Explain.Tq> tqList2=new ArrayList<>();
//    private List<VipDetailResponse.DataBean.SpecOptions> specOptionsList=new ArrayList<>();

    private MyReceiver myReceiver;




    public static final int SDK_PAY_FLAG = 1;
    public final static String APPLY = "MyHuiYuanDetailActivity_Apply";
    private int payType = 2;
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((String) msg.obj);
                    /**
                     * 同步返回的结果必须放置到服务端进行验证（验证的规则请看https://doc.open.alipay.com/doc2/
                     * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
                     * docType=1) 建议商户依赖异步通知
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息


                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        Intent intent = new Intent(APPLY);
                        intent.putExtra("apply_result", 3);
                        sendBroadcast(intent);

                    } else {
                        // 判断resultStatus 为非"9000"则代表可能支付失败
                        // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            Toast.makeText(MyHuiYuanDetailActivity.this, "支付结果确认中", Toast.LENGTH_SHORT).show();
                        } else if (TextUtils.equals(resultStatus, "6001")) {
                            //包括用户主动取消支付
                            Intent intent = new Intent(APPLY);
                            intent.putExtra("apply_result", 1);
                            sendBroadcast(intent);
                        } else {
                            // 其他值就可以判断为支付失败，或者系统返回的错误
                            Intent intent = new Intent(APPLY);
                            intent.putExtra("apply_result", 2);
                            sendBroadcast(intent);
                        }
                    }
                    break;
                }
                default:
                    break;
            }
        }
    };
    private CurrentStatusResponse.DataBean mVipCurrentStatus;
    private VipUpdateResponse.DataBean mVipUpdate;
    private boolean mLeft;
    private boolean mRight;

    @Override
    public boolean check(WebView webView, String url) {
        return false;
    }

    @Override
    public void JavaScriptObjectMethod(String url) {

    }

    class MyReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            int apply_result = intent.getIntExtra("apply_result", 0);

            switch (apply_result) {
                case 0:  //默认值
                    break;
                case 1:  //用户取消支付
                    toast("用户取消支付");
                    break;
                case 2:  //支付失败
                    if (payType == 1) {
                        toast("请安装最新版的微信");
                    } else if (payType == 2) {
                        toast("请安装最新版的支付宝");
                    } else {
                        toast("账户余额不足");
                    }
                    break;
                case 3:  //支付成功
                    toast("支付成功");
                    minePresenter.currentStatus(roletype);
                    break;
            }
        }
    }

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        minePresenter = new MinePresenter(this);
        roletype = getIntent().getStringExtra("roletype");
        if (StringUtil.isBlank(roletype)) {
            roletype = User.roleType();
        }
        IntentFilter filter = new IntentFilter(APPLY);
        myReceiver = new MyReceiver();
        registerReceiver(myReceiver, filter);
        initView();
        minePresenter.currentStatus(roletype);
//        minePresenter.vipDetail(roletype);

    }

    private void initView() {
        /*if (!StringUtil.isBlank(roletype) && roletype.equals("1")) {//升级买手
            ivMaishou.setImageResource(R.mipmap.img_vip_maishou);
        } else if (!StringUtil.isBlank(roletype) && roletype.equals("2")) {  //升级班长
            ivMaishou.setImageResource(R.mipmap.img_vip_dabanzhang);
        } else if (!StringUtil.isBlank(roletype) && roletype.equals("3")) {  //升级总代
            ivMaishou.setImageResource(R.mipmap.img_vip_zongdai);
        } else {  //升级买手
            ivMaishou.setImageResource(R.mipmap.img_vip_maishou);
        }
        adapter1 = new HuiYuanDetailAdapter(this, tqList1);
        adapter2 = new HuiYuanDetailAdapter(this, tqList2);
        GridLayoutManager gridLayoutManagerLeft=new GridLayoutManager(this,3);
        GridLayoutManager gridLayoutManagerRight=new GridLayoutManager(this,3);
//        ervLeft.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.HORIZONTAL));
        ervLeft.setLayoutManager(gridLayoutManagerLeft);
//        ervRight.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.HORIZONTAL));
        ervRight.setLayoutManager(gridLayoutManagerRight);
        ervLeft.setAdapter(adapter1);
        ervRight.setAdapter(adapter2);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        taoCanAdapter = new HuiYuanDetailTaoCanAdapter(this, specOptionsList);
        erv_taocan.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        erv_taocan.setLayoutManager(linearLayoutManager);
        erv_taocan.setAdapter(taoCanAdapter);
        taoCanAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener<VipDetailResponse.DataBean.SpecOptions>() {
            @Override
            public void onItemClick(View view, int position, VipDetailResponse.DataBean.SpecOptions model) {
                taoCanAdapter.setCheckPosition(position);
                tvHuiyuanPrice.setText("¥ "+specOptionsList.get(position).getPrice());
                taoCanAdapter.notifyDataSetChanged();
            }
        });*/
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_huiyuan_detail;
    }

    @Override
    public void show(Object obj) {
        if (obj != null && obj instanceof VipDetailResponse) {
           /* vipDetailResponse = (VipDetailResponse) obj;
            if(vipDetailResponse.getData()!=null){
                if (vipDetailResponse.getData().getExplain() != null) {
                    tvName1.setText(vipDetailResponse.getData().getExplain().getName1());
                    tvName2.setText(vipDetailResponse.getData().getExplain().getName2());
                    List<VipDetailResponse.DataBean.Explain.Tq> tq = vipDetailResponse.getData().getExplain().getTq();
                    if (tq != null&&tq.size()>0) {
                        tqList1.clear();
                        tqList1.addAll(tq);
                        adapter1.notifyDataSetChanged();
                    }
                    List<VipDetailResponse.DataBean.Explain.Tq> tq1 = vipDetailResponse.getData().getExplain().getTq1();
                    if (tq1 != null&&tq1.size()>0) {
                        tqList2.clear();
                        tqList2.addAll(tq1);
                        adapter2.notifyDataSetChanged();
                    }
                }
                if (vipDetailResponse.getData().getSpec_options() != null) {
                    List<VipDetailResponse.DataBean.SpecOptions> spec_options = vipDetailResponse.getData().getSpec_options();
                    if(spec_options!=null&&spec_options.size()>0){
                        specOptionsList.clear();
                        specOptionsList.addAll(spec_options);
                        taoCanAdapter.notifyDataSetChanged();
                        tvHuiyuanPrice.setText("¥ "+specOptionsList.get(0).getPrice());
                    }
                }
                if(vipDetailResponse.getData().getExplain()!=null){
                    tvTishi.setText("您的会员卡有效期为"+(StringUtil.isBlank(vipDetailResponse.getData().getExplain().getDeadline())?"0天，":vipDetailResponse.getData().getExplain().getDeadline())+
                            "请在到期之后续费，避免积分损失，有任何问题，请联系您的专属大班长，微信号："+vipDetailResponse.getData().getExplain().getBzwx());
                }
                tvMaishouNo.setText("No："+vipDetailResponse.getData().getStudentId());
            }
*/
        }
        if (obj != null && obj instanceof VipSubmitResponse) {
            final VipSubmitResponse vipSubmitResponse = (VipSubmitResponse) obj;
            if (vipSubmitResponse.getData() != null) {
                Runnable payRunnable = new Runnable() {
                    @Override
                    public void run() {
                        // 构造PayTask 对象
                        PayTask alipay = new PayTask(MyHuiYuanDetailActivity.this);
                        // 调用支付接口，获取支付结果
                        String result = alipay.pay(vipSubmitResponse.getData().getPayinfo(), true);
                        android.os.Message msg = new android.os.Message();
                        msg.what = SDK_PAY_FLAG;
                        msg.obj = result;
                        mHandler.sendMessage(msg);
                    }
                };
                // 必须异步调用
                Thread payThread = new Thread(payRunnable);
                payThread.start();
            }
        }
        if (obj != null && obj instanceof CurrentStatusResponse) {
            final CurrentStatusResponse vipSubmitResponse = (CurrentStatusResponse) obj;
            if (vipSubmitResponse.getData() != null) {
                mVipCurrentStatus = vipSubmitResponse.getData();
                new WebViewUtils(mWebView, MyHuiYuanDetailActivity.this, MyHuiYuanDetailActivity.this);
                mWebView.loadUrl(vipSubmitResponse.getData().getH5url());
                tv_title.setText(vipSubmitResponse.getData().getTitle());
                if (vipSubmitResponse.getData().getLeft() == null) {
                    mLeft = true;
                    tvInvite.setVisibility(View.GONE);
                } else {
                    tvInvite.setText(vipSubmitResponse.getData().getLeft().getTitle());
                    tvInvite.setVisibility(View.VISIBLE);
                }
                if (vipSubmitResponse.getData().getRight() == null) {
                    mTvUpdate.setVisibility(View.GONE);
                    mRight = true;
                } else {
                    mTvUpdate.setText(vipSubmitResponse.getData().getRight().getTitle());
                    mTvUpdate.setVisibility(View.VISIBLE);
                }

                if (mLeft)
                    mTvUpdate.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f));
                if (mRight)
                    tvInvite.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f));
            }
        }
        if (obj != null && obj instanceof VipUpdateResponse) {
            final VipUpdateResponse vipUpdateResponse = (VipUpdateResponse) obj;
            if (vipUpdateResponse.getData() != null) {

                if (vipUpdateResponse.getData().getIs_full() == 0) {

                    final UpdateSuccessDialog payDialog = new UpdateSuccessDialog(MyHuiYuanDetailActivity.this, new UpdateSuccessDialog.CallBack() {
                        @Override
                        public void NO() {
                            minePresenter.currentStatus(roletype);
                        }
                    });
                    payDialog.show();
                } else {
                    UpdateDialog payDialog = new UpdateDialog(MyHuiYuanDetailActivity.this, new UpdateDialog.CallBack() {
                        @Override
                        public void NO() {
                            switch (vipUpdateResponse.getData().getClicktype()) {
                                case 0:
                                    break;
                                case 1:
                                    startActivity(new Intent(MyHuiYuanDetailActivity.this, MyYaoQingActivity.class));
                                    break;
                                case 2:
                                    Intent intent1 = new Intent(MyHuiYuanDetailActivity.this, BanZhangActivity.class);
                                    intent1.putExtra("roletype", "2");
                                    intent1.putExtra("contact", "联系班长");
                                    startActivity(intent1);

                                    break;
                                case 3:
                                    Intent intent2 = new Intent(MyHuiYuanDetailActivity.this, BanZhangActivity.class);
                                    intent2.putExtra("roletype", "3");
                                    intent2.putExtra("contact", "联系总代");
                                    startActivity(intent2);

                                    break;
                                case 4:
                                    Intent intent3 = new Intent(MyHuiYuanDetailActivity.this, BanZhangActivity.class);
                                    intent3.putExtra("roletype", "4");
                                    intent3.putExtra("contact", "联系管理员");
                                    startActivity(intent3);

                                    break;
                            }
//                            minePresenter.vipSubmit(roletype);
                        }
                    });
                    payDialog.setMessage(vipUpdateResponse.message);
                    payDialog.setMessage(vipUpdateResponse.message);
                    payDialog.setRightText(vipUpdateResponse.getData().getClicktitle());
                    payDialog.show();
                }
            }
        }


    }

    @OnClick({R.id.tv_invite, R.id.tv_update})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_invite:
                switch (mVipCurrentStatus.getLeft().getClicktype()) {
                    case 0:

                        break;
                    case 1:

                        startActivity(new Intent(MyHuiYuanDetailActivity.this, MyYaoQingActivity.class));

                        break;
                    case 2:
                        Intent intent1 = new Intent(MyHuiYuanDetailActivity.this, BanZhangActivity.class);
                        intent1.putExtra("roletype", "2");
                        intent1.putExtra("contact", "联系班长");
                        startActivity(intent1);

                        break;
                    case 3:
                        Intent intent2 = new Intent(MyHuiYuanDetailActivity.this, BanZhangActivity.class);
                        intent2.putExtra("roletype", "3");
                        intent2.putExtra("contact", "联系总代");
                        startActivity(intent2);

                        break;
                    case 4:
                        Intent intent3 = new Intent(MyHuiYuanDetailActivity.this, BanZhangActivity.class);
                        intent3.putExtra("roletype", "4");
                        intent3.putExtra("contact", "联系管理员");
                        startActivity(intent3);

                        break;
                    case 6:

                        break;
                }

//                VipDetailResponse.DataBean.SpecOptions specOptions = specOptionsList.get(taoCanAdapter.getCheckPosition());
//                minePresenter.vipSubmit(roletype,specOptions.getId());
                break;
            case R.id.tv_update:

                switch (mVipCurrentStatus.getRight().getClicktype()) {
                    case 0:

                        break;
                    case 6:

                        PayDialog payDialog = new PayDialog(MyHuiYuanDetailActivity.this, new PayDialog.CallBack() {
                            @Override
                            public void NO() {
                                minePresenter.vipSubmit(roletype);
                            }
                        });
                        payDialog.show();

                        break;
                    case 7:
                        minePresenter.vipUpdate(roletype);
                        break;
                }

                break;
        }

    }


    @Override
    protected String title() {
        return "";
//        if (!StringUtil.isBlank(roletype) && roletype.equals("1")) {//升级买手
//            return "报名买手";
//
//        } else if (!StringUtil.isBlank(roletype) && roletype.equals("2")) {  //升级班长
//            return "报名班长";
//        } else if (!StringUtil.isBlank(roletype) && roletype.equals("3")) {  //升级总代
//            return "报名总代";
//        } else {  //升级买手
//            return "报名买手";
//        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (myReceiver != null) {
            unregisterReceiver(myReceiver);
            myReceiver = null;
        }
    }
}
