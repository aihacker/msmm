package com.d2956987215.mow.activity.home;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.d2956987215.mow.R;
import com.d2956987215.mow.activity.BaseActivity;
import com.d2956987215.mow.presenter.MinePresenter;
import com.d2956987215.mow.util.AnimatorEffect;
import com.d2956987215.mow.util.StringUtil;
import com.d2956987215.mow.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class GoodsDetailFeedBackActivity extends BaseActivity implements TextWatcher {

    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.ll_quan_issue)
    LinearLayout llQuanIssue;
    @BindView(R.id.ll_price_issue)
    LinearLayout llPriceIssue;
    @BindView(R.id.ll_yongjin_issue)
    LinearLayout llYongjinIssue;
    @BindView(R.id.ll_pic_goods_issue)
    LinearLayout llPicGoodsIssue;
    @BindView(R.id.ll_share_quan_issue)
    LinearLayout llShareQuanIssue;
    @BindView(R.id.ll_other_issue)
    LinearLayout llOtherIssue;
    @BindView(R.id.tv_num)
    TextView tvNum;
    @BindView(R.id.bt_submit)
    Button btSubmit;
    @BindView(R.id.cb_quan_issue)
    CheckBox cbQuanIssue;
    @BindView(R.id.cb_price_issue)
    CheckBox cbPriceIssue;
    @BindView(R.id.cb_yongjin_issue)
    CheckBox cbYongjinIssue;
    @BindView(R.id.cb_pic_goods_issue)
    CheckBox cbPicGoodsIssue;
    @BindView(R.id.cb_share_quan_issue)
    CheckBox cbShareQuanIssue;
    @BindView(R.id.cb_other_issue)
    CheckBox cbOtherIssue;

    private MinePresenter presenter;
    private String itemId;
    private List<String> issueType=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new MinePresenter(this);
        itemId=getIntent().getStringExtra("itemId");
        etContent.addTextChangedListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_goods_detail_feed_back;
    }

    @Override
    protected String title() {
        return getString(R.string.feedback);
    }

    @Override
    public void show(Object obj) {

    }

    @OnClick({R.id.bt_submit, R.id.ll_quan_issue, R.id.ll_price_issue, R.id.ll_yongjin_issue, R.id.ll_pic_goods_issue, R.id.ll_share_quan_issue, R.id.ll_other_issue})
    public void onViewClicked(View view) {
        AnimatorEffect.setClickEffect(getApplicationContext(), view);
        switch (view.getId()) {
            case R.id.bt_submit:
                if(StringUtil.isBlank(etContent.getText().toString())){
                    ToastUtil.show(this,"输入的内容不能为空");
                    return;
                }
                presenter.feedBackGoods(etContent.getText().toString(),itemId,issueType);
                break;
            case R.id.ll_quan_issue:
                if(cbQuanIssue.isChecked()&&issueType.contains("优惠券有问题")){
                    issueType.remove("优惠券有问题");
                }else {
                    issueType.add("优惠券有问题");
                }
                cbQuanIssue.setChecked(!cbQuanIssue.isChecked());
                break;
            case R.id.ll_price_issue:
                if(cbPriceIssue.isChecked()&&issueType.contains("优惠券有问题")){
                    issueType.remove("商品价格不符");
                }else {
                    issueType.add("商品价格不符");
                }
                cbPriceIssue.setChecked(!cbPriceIssue.isChecked());
                break;

            case R.id.ll_yongjin_issue:
                if(cbPriceIssue.isChecked()&&issueType.contains("佣金不符")){
                    issueType.remove("佣金不符");
                }else {
                    issueType.add("佣金不符");
                }
                cbYongjinIssue.setChecked(!cbYongjinIssue.isChecked());
                break;
            case R.id.ll_pic_goods_issue:
                if(cbPriceIssue.isChecked()&&issueType.contains("图片与商品不符")){
                    issueType.remove("图片与商品不符");
                }else {
                    issueType.add("图片与商品不符");
                }
                cbPicGoodsIssue.setChecked(!cbPicGoodsIssue.isChecked());
                break;
            case R.id.ll_share_quan_issue:
                if(cbPriceIssue.isChecked()&&issueType.contains("无法分享领取优惠券")){
                    issueType.remove("无法分享领取优惠券");
                }else {
                    issueType.add("无法分享领取优惠券");
                }
                cbShareQuanIssue.setChecked(!cbShareQuanIssue.isChecked());
                break;
            case R.id.ll_other_issue:
                if(cbOtherIssue.isChecked()&&issueType.contains("其他")){
                    issueType.remove("其他");
                }else {
                    issueType.add("其他");
                }
                cbOtherIssue.setChecked(!cbOtherIssue.isChecked());
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (!TextUtils.isEmpty(etContent.getText().toString().trim())) {
            btSubmit.setBackground(getResources().getDrawable(R.drawable.bg_loginred));
            btSubmit.setClickable(true);
            tvNum.setText(etContent.getText().toString().length()+"/150");
        } else {
            btSubmit.setBackground(getResources().getDrawable(R.drawable.bg_login));
            btSubmit.setClickable(false);
            tvNum.setText("0/150");
        }
    }


    @Override
    public void afterTextChanged(Editable editable) {

    }

}
