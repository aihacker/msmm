package com.d2956987215.mow.activity.home;

import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebView;

import com.blankj.utilcode.util.StringUtils;
import com.d2956987215.mow.R;
import com.d2956987215.mow.activity.BaseActivity;
import com.d2956987215.mow.util.WebViewUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class WebViewActivity extends BaseActivity implements WebViewUtils.WebViewInterface {

    @BindView(R.id.web)
    WebView web;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        initWeb();
    }


    private void initWeb() {
        new WebViewUtils(web, WebViewActivity.this, this);
        web.loadUrl(getIntent().getStringExtra("url").replace("\\", ""));
    }

    @Override
    protected String title() {
        if (StringUtils.isEmpty(getIntent().getStringExtra("name")))
            return "详情";
        else
            return getIntent().getStringExtra("name");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_webview;
    }

    @Override
    public void show(Object obj) {

    }

    @Override
    public boolean check(WebView webView, String url) {
        return false;
    }

    @Override
    public void JavaScriptObjectMethod(String url) {

    }

    @Override
    protected void onBack() {
        if (web.canGoBack()) {
            web.goBack();
            return;
        } else {
            finish();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (web.canGoBack()) {
                web.goBack();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
