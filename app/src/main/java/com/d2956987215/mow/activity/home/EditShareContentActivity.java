package com.d2956987215.mow.activity.home;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.d2956987215.mow.R;
import com.d2956987215.mow.activity.BaseActivity;
import com.d2956987215.mow.rxjava.Request;
import com.d2956987215.mow.rxjava.Result;
import com.d2956987215.mow.rxjava.RxJavaUtil;
import com.d2956987215.mow.rxjava.response.ShopAllCouponResponse;
import com.d2956987215.mow.util.User;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class EditShareContentActivity extends BaseActivity {


    public static final String TEMP = "temp";
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
    //宣传语
    public static final String EXTENSION = "extension";
    //邀请码
    public static final String STUDENTID = "studentId";
    //买手佣金
    public static final String YONGJIN = "yongjin";
    //app下载链接
    public static final String DOWNLOADLINK = "download_link";

    @BindView(R.id.et_content)
    EditText mTvContent;
    private Intent mIntent;
    private String mTemp;
    private String[] mContentStr;
    private String[] mTempContentStr = new String[6];
    private String mTitle;
    private String mOldPrice;
    private String mCouponPrice;
    private String mEndPrice;
    private String mRhyUrl;
    private String mTkl;
    private String mTempContent;
    private String mExtension;
    private String mStudentId;
    private String myongjin;
    private String mDownloadLink;

    @Override

    protected void init() {
        super.init();

        initIntent();

    }

    private void initIntent() {
        mIntent = getIntent();
        mTemp = mIntent.getStringExtra(TEMP);

        mTvContent.setText(mTemp);
        mTitle = mIntent.getStringExtra(TITLE);
        mOldPrice = mIntent.getStringExtra(ZK_FINAL_PRICE);
        mCouponPrice = mIntent.getStringExtra(COUPON_PRICE);
        mEndPrice = mIntent.getStringExtra(END_PRICE);
        mRhyUrl = mIntent.getStringExtra(RHYURL);
        mTkl = mIntent.getStringExtra(TKL);
        mExtension = mIntent.getStringExtra(EXTENSION);
        mStudentId = mIntent.getStringExtra(STUDENTID);
        myongjin = mIntent.getStringExtra(YONGJIN);
        mDownloadLink = mIntent.getStringExtra(DOWNLOADLINK);
    }


    @OnClick({R.id.tv_pre, R.id.tv_confirm})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_pre:
                showPreDialog();
                break;
            case R.id.tv_confirm:
                SPUtils.getInstance().put("share_content", mTvContent.getText().toString());
                ToastUtils.showShort("保存成功");
                break;
        }


    }

    /**
     *
     */
    private void showPreDialog() {


        View view = LayoutInflater.from(EditShareContentActivity.this).inflate(R.layout.dialog_pre_share, null);
        TextView tvContent = view.findViewById(R.id.tv_content);
        TextView close = view.findViewById(R.id.tv_close);
        TextView save = view.findViewById(R.id.tv_save);


        final String content = matchStr(mTvContent.getText().toString());


        tvContent.setText(content);

        AlertDialog.Builder builder = new AlertDialog.Builder(EditShareContentActivity.this).setView(view);
        final AlertDialog alertDialog = builder.show();
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SPUtils.getInstance().put("share_content", mTvContent.getText().toString());
                alertDialog.dismiss();
                ToastUtils.showShort("保存成功");
            }
        });


    }

    private String matchStr(String content) {
//        mContentStr = content.split("\n");
        mTempContent = content;
        if (mTempContent.contains("{标题}"))
            mTempContent = mTempContent.replace("{标题}", mTitle);
        if (mTempContent.contains("{商品原价}"))
            mTempContent = mTempContent.replace("{商品原价}", mOldPrice);
        if (mTempContent.contains("{优惠券价格}"))
            mTempContent = mTempContent.replace("{优惠券价格}", mCouponPrice);
        if (mTempContent.contains("{券后价}"))
            mTempContent = mTempContent.replace("{券后价}", mEndPrice);
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

    private String saveMatch(String content) {
        mContentStr = content.split("\n");
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < mContentStr.length; i++) {

            if (mContentStr[i].contains("{")) {
                if (i == 0) {
                    sb.append(
                            mContentStr[i].replaceAll("(?<=\\{).*?(?=\\})", ""))
                            .append("\n");
                } else if (i == 1) {
                    sb.append(mContentStr[i].replaceAll("(?<=\\{).*?(?=\\})", "")
                    )
                            .append("\n");
                } else if (i == 2) {

                    sb.append(mContentStr[i].replaceAll("(?<=\\{).*?(?=\\})", "")
                    )
                            .append("\n");
                } else if (i == 3) {

                    sb.append(mContentStr[i].replaceAll("(?<=\\{).*?(?=\\})", "")
                    )
                            .append("\n");
                } else if (i == 4) {

                    sb.append(mContentStr[i].replaceAll("(?<=\\{).*?(?=\\})", "")
                    )
                            .append("\n");
                } else if (i == 6) {

                    sb.append(mContentStr[i].replaceAll("(?<=\\{).*?(?=\\})", "")
                    )
                            .append("\n");
                }
            } else {
                sb.append(mContentStr[i]).append("\n");
            }

        }
        return sb.toString();
    }


    @Override
    protected String title() {
        return "编辑模板";

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_edit_share_content;
    }

    @Override
    public void show(Object obj) {

    }

    @Override
    protected void onBack() {
        setResult(0);
        finish();

    }
}
