package com.d2956987215.mow.activity.login;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import com.d2956987215.mow.R;
import com.d2956987215.mow.activity.BaseActivity;
import com.google.zxing.client.result.ParsedResult;
import com.mylhyl.zxing.scanner.OnScannerCompletionListener;
import com.mylhyl.zxing.scanner.ScannerView;

import butterknife.BindView;

public class CaptureActivity extends BaseActivity {

    @BindView(R.id.scanner_view)
    ScannerView mScannerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mScannerView.setOnScannerCompletionListener(new OnScannerCompletionListener() {
            @Override
            public void onScannerCompletion(com.google.zxing.Result rawResult, ParsedResult parsedResult, Bitmap barcode) {
                Intent intent = new Intent();
                intent.putExtra("yaoqingma",rawResult.toString());
                setResult(RESULT_OK, intent);
                finish();
//                mScannerView.onResume();
            }

        });


    }



    @Override
    protected String title() {
        return "扫码";
    }

    @Override
    public void onResume() {
        mScannerView.onResume();
        super.onResume();
    }

    @Override
    public void onPause() {
        mScannerView.onPause();
        super.onPause();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_capture;
    }


    @SuppressWarnings("unchecked")
    @Override
    public void show(Object obj) {
    }


}
