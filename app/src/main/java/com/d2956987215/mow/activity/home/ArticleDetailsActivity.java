package com.d2956987215.mow.activity.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.blankj.utilcode.util.StringUtils;
import com.d2956987215.mow.R;
import com.d2956987215.mow.activity.APP;
import com.d2956987215.mow.activity.BaseActivity;

import java.io.IOException;

import butterknife.BindView;

public class ArticleDetailsActivity extends BaseActivity {
    @BindView(R.id.web)
    WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initWeb();
    }


    @SuppressLint("JavascriptInterface")
    private void initWeb() {

        mWebView.setHorizontalScrollBarEnabled(false);
        mWebView.setVerticalScrollBarEnabled(false);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setDatabaseEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setGeolocationEnabled(true);
        String dir = APP.application.getDir("database", Context.MODE_PRIVATE).getPath();
        webSettings.setGeolocationDatabasePath(dir);
        webSettings.setDomStorageEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setSaveFormData(false);
        webSettings.setSavePassword(false);
        String user_agent = webSettings.getUserAgentString();// 获取UserAgent
        webSettings.setUserAgentString(user_agent + "/JINBAO");// 设置UserAgent
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setAppCachePath(APP.application.getCacheDir().getAbsolutePath());
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSettings.setSupportZoom(true);

        mWebView.loadUrl(getIntent().getStringExtra("url"));

    }

    private static final String INJECTION_TOKEN = "**injection**";

    private void initWebClient(WebView webView) {

        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                String url = "";
                //android 7.0系统以上 已经摒弃了shouldOverrideUrlLoading(WebView view, String url)此方法
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    url = request.getUrl().toString();
                } else {
                    url = request.toString();
                }
                Uri uri = Uri.parse(url);
                if (uri.getScheme().equals("js")) {
                    String itemid = Uri.parse(url).getQueryParameter("itemid");
                    String quan_id = Uri.parse(url).getQueryParameter("quan_id");
                    if (!TextUtils.isEmpty(itemid)) {
                        Intent intent = new Intent(getContext(), TaoBaoDetailActivity.class);
                        intent.putExtra("id", itemid);
                        intent.putExtra("quan_id", quan_id);
                        startActivity(intent);
                    }
                } else {
                    view.loadUrl(url);
                }
                return true;
            }

            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
                WebResourceResponse response = super.shouldInterceptRequest(view, url);
                if (url != null && url.contains(INJECTION_TOKEN)) {
                    String assetPath = url.substring(url.indexOf(INJECTION_TOKEN) + INJECTION_TOKEN.length(), url.length());
                    try {
                        response = new WebResourceResponse(
                                "application/javascript",
                                "UTF8",
                                getAssets().open(assetPath)
                        );
                    } catch (IOException e) {
                        e.printStackTrace(); // Failed to load asset file
                    }
                }
                return response;
            }
        });
    }

    @Override
    protected String title() {
        if (StringUtils.isEmpty(getIntent().getStringExtra("name")))
            return "";
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
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mWebView.canGoBack()) {
                mWebView.goBack();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    @Override
    protected void onBack() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
            return;
        } else {
            finish();
        }

    }


}
