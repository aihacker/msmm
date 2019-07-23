package com.d2956987215.mow.activity.mine;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.d2956987215.mow.R;
import com.d2956987215.mow.activity.BaseActivity;
import com.d2956987215.mow.adapter.TongXunAdapter;
import com.d2956987215.mow.bean.PhoneDto;
import com.d2956987215.mow.util.PhoneUtil;
import com.d2956987215.mow.util.QRCodeUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import me.bakumon.statuslayoutmanager.library.StatusLayoutManager;

import static com.blankj.utilcode.util.ActivityUtils.startActivity;

public class MyTongXunListActivity extends BaseActivity {


    @BindView(R.id.recycler_hot_product)
    RecyclerView recycler_hot_product;
    @BindView(R.id.tv_couut)
    TextView tv_couut;


    private int p = 1;
    private String type = null;
    private StatusLayoutManager statusLayoutManager;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        statusLayoutManager = new StatusLayoutManager.Builder(recycler_hot_product).setDefaultLayoutsBackgroundColor(Color.WHITE).setEmptyLayout(inflate(R.layout.custom_empty_view))
                .build();

        check();


    }

    @OnClick({R.id.tv_couut, R.id.tv_quxiao})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_couut:
                sendGroupMessage();
                break;
            case R.id.tv_quxiao:
                finish();
                break;

        }
    }

    public void sendGroupMessage() {
        if (StringUtils.isEmpty(name)) {
            ToastUtils.showShort("请选择联系人");
            return;
        }
//        Intent intent = new Intent(Intent.ACTION_VIEW);
//        intent.putExtra("address", name);
////        intent.putExtra("sms_body", "我的邀请码是：" + getIntent().getStringExtra("studentId"));
//        String url = " https://msmmapp-01.kuaizhan.com/?inviteCode=" + getIntent().getStringExtra("studentId");
//        intent.putExtra("sms_body", "你不能不知道的天猫淘宝省钱内幕，薅羊毛首席攻略，就在这里!请点击:" + url);
//        intent.setType("vnd.android-dir/mms-sms");
//        startActivity(intent);
//
        String url = " https://msmmapp-01.kuaizhan.com/?inviteCode=" + getIntent().getStringExtra("studentId");
        Uri smsToUri = Uri.parse("smsto:" + name);
        Intent intent = new Intent(Intent.ACTION_SENDTO, smsToUri);
        intent.putExtra("sms_body", "你不能不知道的天猫淘宝省钱内幕，薅羊毛首席攻略，就在这里!请点击:" + url);
        startActivity(intent);


    }

    /**
     * 检查权限
     */
    private void check() {
        //判断是否有权限
        if (ContextCompat.checkSelfPermission(MyTongXunListActivity.this, Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MyTongXunListActivity.this, new String[]{Manifest.permission.READ_CONTACTS}, 201);
        } else {
            initHotRecycler1();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 201) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                initHotRecycler1();
            }else {
                Toast.makeText(MyTongXunListActivity.this, "为了您能正常使用，请开启权限", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private LayoutInflater inflater;

    private View inflate(@LayoutRes int resource) {
        if (inflater == null) {
            inflater = LayoutInflater.from(this);
        }
        return inflater.inflate(resource, null);
    }


    private List<PhoneDto> phoneDtos;

    private TongXunAdapter hotAdapter;

    private void initHotRecycler1() {
        PhoneUtil phoneUtil = new PhoneUtil(this);
        phoneDtos = phoneUtil.getPhone();

        hotAdapter = new TongXunAdapter(R.layout.item_tongxun, phoneDtos, getIntent().getStringExtra("studentId"));
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recycler_hot_product.setLayoutManager(layoutManager);
        recycler_hot_product.setLayoutManager(layoutManager);
        recycler_hot_product.setHasFixedSize(true);
        recycler_hot_product.setNestedScrollingEnabled(false);
        recycler_hot_product.setAdapter(hotAdapter);
        hotAdapter.setClickButtonListener(new TongXunAdapter.onClickQuxiaoListener() {
            @Override
            public void xuanqu(String a, String size) {
                tv_couut.setText("立即发送(" + size + ")");
                name = a;
            }
        });


    }

    @Override
    protected String title() {
        return "推荐通讯录好友";

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_tongxunlist;
    }

    @Override
    public void show(Object obj) {

    }
}
