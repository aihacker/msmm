package com.d2956987215.mow.activity.mine;

import android.os.Bundle;
import android.webkit.WebView;

import com.d2956987215.mow.R;
import com.d2956987215.mow.activity.BaseActivity;
import com.d2956987215.mow.rxjava.RxJavaUtil;
import com.d2956987215.mow.util.WebViewUtils;

import butterknife.BindView;

public class WebviewActivity extends BaseActivity implements WebViewUtils.WebViewInterface {

    @BindView(R.id.web)
    WebView web;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initWeb();
    }

    private void initWeb() {
        new WebViewUtils(web, WebviewActivity.this,this);
        web.loadUrl(RxJavaUtil.HOST + "index.php?m=Api&c=Index&a=aboutUs");
    }

    @Override
    protected String title() {
        return getString(R.string.about_us);
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
}
